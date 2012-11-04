package org.droidpersistence.dao.query.copy;

public class RestrictionLike extends BinaryOperatorRestriction
{

	protected RestrictionLike(String p_name, Object p_value)
	{
		super(p_name, p_value);
	}

	@Override
	protected String getSqlOperation()
	{

		return "like";
	}

}
