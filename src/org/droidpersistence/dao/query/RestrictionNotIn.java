package org.droidpersistence.dao.query;

public class RestrictionNotIn extends RestrictionIn
{

	protected RestrictionNotIn(String p_name, Object... p_value)
	{
		super(p_name, p_value);
	}

	@Override
	protected String getSqlOperation()
	{
		return "not in";
	}

}
