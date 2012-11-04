package org.droidpersistence.dao.query;

public class RestrictionGE extends BinaryOperatorRestriction
{

	protected RestrictionGE(String p_name, Object p_value)
	{
		super(p_name, p_value);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSqlOperation()
	{

		return ">=";
	}

}
