package org.droidpersistence.dao.query.copy;

public class RestrictionNotEq extends BinaryOperatorRestriction
{

	protected RestrictionNotEq(String p_name, Object p_value)
	{
		super(p_name, p_value);
	}

	@Override
	protected String getSqlOperation()
	{
		return "<>";
	}

}
