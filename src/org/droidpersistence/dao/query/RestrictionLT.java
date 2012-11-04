package org.droidpersistence.dao.query;

public class RestrictionLT extends BinaryOperatorRestriction
{

	protected RestrictionLT(String p_name, Object p_value)
	{
		super(p_name, p_value);

	}

	@Override
	protected String getSqlOperation()
	{
		return "<";
	}

}
