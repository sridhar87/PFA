package org.droidpersistence.dao.query;

import java.util.ArrayList;
import java.util.List;

import org.droidpersistence.annotation.Table;
import org.droidpersistence.util.DroidUtils;

public class Criterion
{

	private final String m_tableName;

	private final List<Restriction> m_restrictionsList = new ArrayList<Restriction>();

	public Criterion(Class<?> p_domainClass) throws Exception
	{
		Table v_annotation = p_domainClass.getAnnotation(Table.class);
		String v_tableName = p_domainClass.getSimpleName();
		if (v_annotation instanceof Table)
		{
			if (!DroidUtils.isEmpty(v_annotation.name()))
			{
				v_tableName = v_annotation.name();
			}
		}
		else
		{
			throw new Exception("Class " + p_domainClass.getName() + " doesn't have annotation @Table");
		}
		this.m_tableName = v_tableName;
	}

	public void add(Restriction p_restriction)
	{
		p_restriction.setCriterion(this);
		this.m_restrictionsList.add(p_restriction);
	}

	public String getTableName()
	{
		return this.m_tableName;
	}
}
