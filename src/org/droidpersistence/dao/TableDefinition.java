/**
 * @author Douglas Cavalheiro (doug.cav@ig.com.br)
 */

package org.droidpersistence.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.ForeignKey;
import org.droidpersistence.annotation.Index;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.model.FieldModel;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public abstract class TableDefinition<T> {

    private String TABLE_NAME;

    private String PK = "";

    private StringBuilder COLUMNS;

    private String[] ARRAY_COLUMNS;

    private Field[] FIELD_DEFINITION;

    private StringBuilder CREATE_STATEMENT;

    private StringBuilder FOREIGN_KEY;

    private List<String> INDEXES;

    private Class<T> model;

    private Class OBJECT;

    private TableDefinition<T> singleton;

    private List<FieldModel> LIST_FIELD_MODEL;

    /** Creates a new instance setting a model class to build a definition table */
    public TableDefinition(Class<T> model) {
        this.singleton = this;
        this.model = model;
        try {
            OBJECT = Class.forName(model.getName());
            createTableDefinition();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Build a DDL instruction for create table with fields */
    @SuppressWarnings({ "unchecked", "null" })
    public void createTableDefinition() throws Exception {

        if (OBJECT.isAnnotationPresent(Table.class)) {
            Annotation annotation = OBJECT.getAnnotation(Table.class);
            Method method = annotation.getClass().getMethod("name");
            Object object = method.invoke(annotation);

            TABLE_NAME = object.toString().toUpperCase();

            CREATE_STATEMENT = new StringBuilder();
            FOREIGN_KEY = new StringBuilder();
            COLUMNS = new StringBuilder();
            INDEXES = new ArrayList<String>();
            LIST_FIELD_MODEL = new ArrayList<FieldModel>();

        } else {
            CREATE_STATEMENT = null;
            throw new Exception("Annotation @Table not declared in class " + OBJECT.getSimpleName());
        }

        FIELD_DEFINITION = OBJECT.getDeclaredFields();

        ARRAY_COLUMNS = new String[FIELD_DEFINITION.length];

        Map<String, List<String>> indexes = new HashMap<String, List<String>>();
        for (int i = 0; i < FIELD_DEFINITION.length; i++) {
            Field field = FIELD_DEFINITION[i];
            Annotation annotation = null;
            Method methodName = null;
            Object objectName = null;
            String type;
            String primaryKeyText = "";

            if (field.isAnnotationPresent(Column.class)) {
                annotation = field.getAnnotation(Column.class);
                methodName = annotation.getClass().getMethod("name");
                objectName = methodName.invoke(annotation);
                if (objectName == null || objectName.toString() == "") {
                    objectName = field.getName();
                }
            } else {
                CREATE_STATEMENT = null;
                throw new Exception("Annotation @Column not declared in the field --> " + field.getName());
            }

            if (field.isAnnotationPresent(Index.class)) {
                Annotation indexAnnotation = field.getAnnotation(Index.class);
                String column = objectName.toString();
                methodName = indexAnnotation.getClass().getMethod("name");
                String indexName = (String) methodName.invoke(indexAnnotation);
                if (indexName != null || !indexName.toString().equals("")) {
                    if (!indexes.containsKey(indexName)) {
                        indexes.put(indexName, new ArrayList<String>());
                    }
                    indexes.get(indexName).add(column);
                }
            }

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                PK = objectName.toString();

                Annotation pKey_annotation = field.getAnnotation(PrimaryKey.class);
                Method pkey_methodAutoIncrement = pKey_annotation.getClass().getMethod("autoIncrement");
                Object pkey_autoIncrement = pkey_methodAutoIncrement.invoke(pKey_annotation);

                primaryKeyText = " PRIMARY KEY ";

                if (Boolean.valueOf(pkey_autoIncrement.toString())) {
                    primaryKeyText = primaryKeyText + " AUTOINCREMENT ";
                }

            }

            if (field.isAnnotationPresent(ForeignKey.class)) {
                Annotation fkey_annotation = field.getAnnotation(ForeignKey.class);
                Method fkey_methodTableReference = fkey_annotation.getClass().getMethod("tableReference");
                Object fkey_tableReferenceName = fkey_methodTableReference.invoke(fkey_annotation);

                Method fkey_methodOnUpCascade = fkey_annotation.getClass().getMethod("onUpdateCascade");
                Object fkey_OnUpCascadeValue = fkey_methodOnUpCascade.invoke(fkey_annotation);

                Method fkey_methodOnDelCascade = fkey_annotation.getClass().getMethod("onDeleteCascade");
                Object fkey_OnDelCascadeValue = fkey_methodOnDelCascade.invoke(fkey_annotation);

                Method fkey_methodColumnReference = fkey_annotation.getClass().getMethod("columnReference");
                Object fkey_columnReference = fkey_methodColumnReference.invoke(fkey_annotation);

                String columnReference = fkey_columnReference.toString();
                if (columnReference == "") {
                    columnReference = "_id";
                }

                FOREIGN_KEY.append(", FOREIGN KEY (" + objectName.toString() + ") REFERENCES "
                        + fkey_tableReferenceName.toString().toUpperCase() + " (" + columnReference + ")");

                if (Boolean.valueOf(fkey_OnUpCascadeValue.toString())) {
                    FOREIGN_KEY.append(" ON UPDATE CASCADE ");
                }
                if (Boolean.valueOf(fkey_OnDelCascadeValue.toString())) {
                    FOREIGN_KEY.append(" ON DELETE CASCADE ");
                }

            }

            if (field.getType() == int.class || field.getType() == Integer.class || field.getType() == Long.class
                    || field.getType() == long.class) {
                type = " INTEGER ";
            } else if (field.getType() == String.class || field.getType() == char.class) {
                type = " TEXT ";
            } else if (field.getType() == Double.class || field.getType() == Float.class
                    || field.getType() == double.class) {
                type = " REAL ";
            } else if (field.getType() == BigDecimal.class) {
                type = " NUMERIC ";
            } else if (field.getType() == Date.class) {
                type = " TIMESTAMP ";
            } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                type = " BOOLEAN ";
            } else {
                type = " NONE ";
            }

            if (i == FIELD_DEFINITION.length - 1) {
                if (objectName != null) {
                    CREATE_STATEMENT.append(objectName.toString() + " " + type + primaryKeyText);
                    COLUMNS.append(objectName.toString());
                } else {
                    CREATE_STATEMENT = null;
                    throw new Exception("Property 'name' not declared in the field --> " + field.getName());
                }
            } else {
                if (objectName != null) {
                    CREATE_STATEMENT.append(objectName.toString() + " " + type + primaryKeyText + ", ");
                    COLUMNS.append(objectName.toString() + " , ");
                } else {
                    CREATE_STATEMENT = null;
                    throw new Exception("Property 'name' not declared in the field --> " + field.getName());
                }
            }
            ARRAY_COLUMNS[i] = objectName.toString();
            if (!primaryKeyText.contains("AUTOINCREMENT")) {
                FieldModel fieldModel = new FieldModel();
                fieldModel.setColumnName(objectName.toString());
                fieldModel.setFieldName(field.getName());
                LIST_FIELD_MODEL.add(fieldModel);
            }
        }

        if (FOREIGN_KEY.toString() != "") {
            CREATE_STATEMENT.append(FOREIGN_KEY);
        }

        CREATE_STATEMENT.append(");");

        if (indexes.size() > 0) {
            for (String indexName : indexes.keySet()) {
                StringBuilder indexQuery = new StringBuilder();
                indexQuery.append("CREATE INDEX IF NOT EXISTS ");
                indexQuery.append(indexName + " ON ");
                indexQuery.append(TABLE_NAME + "( ");
                int i = 0;
                for (String colName : indexes.get(indexName)) {
                    if (i != 0) {
                        indexQuery.append(", ");
                    }
                    i++;
                    indexQuery.append(colName);
                }
                indexQuery.append(");");
                INDEXES.add(indexQuery.toString());
            }
        }

        if (getPK() == "") {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (");
            sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sb.append(CREATE_STATEMENT);

            String[] columns = new String[ARRAY_COLUMNS.length + 1];
            columns[0] = BaseColumns._ID;
            for (int i = 0; i < ARRAY_COLUMNS.length; i++) {
                columns[i + 1] = ARRAY_COLUMNS[i];
            }

            ARRAY_COLUMNS = columns;
            CREATE_STATEMENT = sb;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (");
            sb.append(CREATE_STATEMENT);
            CREATE_STATEMENT = sb;
        }
    }

    /** Uses the create DLL to create table */
    public void onCreate(SQLiteDatabase db) throws Exception {
        if (CREATE_STATEMENT != null) {
            db.execSQL(CREATE_STATEMENT.toString());
            Log.i("TableDefinition", CREATE_STATEMENT.toString());
            if (INDEXES != null && INDEXES.size() > 0) {
                for (String indexSQL : INDEXES) {
                    db.execSQL(indexSQL);
                    Log.i("TableDefinition", indexSQL);
                }
            }
        } else {
            throw new Exception("Table not created, the Create DDL not found");
        }
    }

    /** Upgrade (if necessary) the table */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public StringBuilder getColumns() {
        return COLUMNS;
    }

    public void setColumns(StringBuilder columns) {
        this.COLUMNS = columns;
    }

    public String[] getArrayColumns() {
        return ARRAY_COLUMNS;
    }

    public Field[] getFieldDefinition() {
        return FIELD_DEFINITION;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String pk) {
        this.PK = pk;
    }

    public TableDefinition<T> getInstance() {
        return singleton;
    }

    public List<FieldModel> getLIST_FIELD_MODEL() {
        return LIST_FIELD_MODEL;
    }

    public void setLIST_FIELD_MODEL(List<FieldModel> lIST_FIELD_MODEL) {
        LIST_FIELD_MODEL = lIST_FIELD_MODEL;
    }

}
