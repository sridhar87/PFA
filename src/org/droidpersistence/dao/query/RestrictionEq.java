package org.droidpersistence.dao.query;

public class RestrictionEq extends BinaryOperatorRestriction
{

	protected RestrictionEq(String p_name, Object p_value)
	{
		super(p_name, p_value);
	}

	@Override
	protected String getSqlOperation()
	{
		return "=";
	}

}
