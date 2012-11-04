package org.droidpersistence.dao.query;

class RestrictionIsNull extends OperatorRestriction
{

	protected RestrictionIsNull(String p_name)
	{
		super(p_name);
	}

	@Override
	protected String getSqlOperation()
	{
		return "is null";
	}

}
