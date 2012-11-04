package org.droidpersistence.dao.query;

import java.util.Date;

import org.droidpersistence.util.DroidUtils;

abstract class BinaryOperatorRestriction extends OperatorRestriction
{

	private final Object m_value;

	protected BinaryOperatorRestriction(String p_name, Object p_value)
	{
		super(p_name);
		this.m_value = p_value;
	}

	public Object getValue()
	{
		return this.m_value;
	}

	@Override
	protected String innerBuildSql()
	{
		return this.getName() + " " + this.getSqlOperation() + " " + this.getSqlValue(this.m_value);
	}

	protected final String getSqlValue(Object p_value)
	{
		if (p_value instanceof Date)
		{
			return "datetime('" + DroidUtils.convertDateToString((Date) p_value) + "')";
		}
		return p_value.toString();
	}

}
