package org.droidpersistence.dao.query.copy;

public class RestrictionIn extends OperatorRestriction
{

	private final Object[] m_values;

	protected RestrictionIn(String p_name, Object... p_value)
	{
		super(p_name);
		this.m_values = p_value;
	}

	@Override
	protected String getSqlOperation()
	{
		return "in";
	}

	@Override
	protected final String innerBuildSql()
	{
		return this.getName() + " " + this.getSqlOperation() + " " + this.getSqlValue();
	}

	protected final String getSqlValue()
	{
		StringBuilder v_builder = new StringBuilder();
		v_builder.append("(");
		for (Object v_value : this.m_values)
		{
			v_builder.append(v_value);
			v_builder.append(", ");
		}
		v_builder.delete(v_builder.length() - 2, v_builder.length());
		v_builder.append(")");
		return v_builder.toString();
	}

}
