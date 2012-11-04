package org.droidpersistence.dao.query.copy;

public class RestrictionBetween extends TernaryOperatorRestriction
{

	protected RestrictionBetween(String p_name, Object p_firstValue, Object p_secondValue)
	{
		super(p_name, p_firstValue, p_secondValue);

	}

	@Override
	protected String getSqlOperation()
	{
		return "between";
	}

}
