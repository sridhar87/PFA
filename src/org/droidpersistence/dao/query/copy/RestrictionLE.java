package org.droidpersistence.dao.query.copy;

public class RestrictionLE extends BinaryOperatorRestriction
{

	protected RestrictionLE(String p_name, Object p_value)
	{
		super(p_name, p_value);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSqlOperation()
	{

		return "<=";
	}

}
