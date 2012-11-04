package org.droidpersistence.dao.query.copy;

class RestrictionAnd extends LogicalRestriction
{

	protected RestrictionAnd(Restriction[] p_restrictions)
	{
		super(p_restrictions);
	}

	@Override
	protected String getOperatorCode()
	{
		return " and ";
	}

	@Override
	protected String innerBuildSql()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
