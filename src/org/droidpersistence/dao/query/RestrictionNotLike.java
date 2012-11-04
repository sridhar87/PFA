package org.droidpersistence.dao.query;

public class RestrictionNotLike extends BinaryOperatorRestriction
{

	protected RestrictionNotLike(String p_name, Object p_value)
	{
		super(p_name, p_value);

	}

	@Override
	protected String getSqlOperation()
	{

		return "not like";
	}

}
