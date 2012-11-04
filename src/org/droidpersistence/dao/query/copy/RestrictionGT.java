package org.droidpersistence.dao.query.copy;

public class RestrictionGT extends BinaryOperatorRestriction
{

	protected RestrictionGT(String p_name, Object p_value)
	{
		super(p_name, p_value);

	}

	@Override
	protected String getSqlOperation()
	{

		return ">";
	}

}
