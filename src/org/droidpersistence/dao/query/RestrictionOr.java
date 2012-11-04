package org.droidpersistence.dao.query;

class RestrictionOr extends LogicalRestriction
{

	protected RestrictionOr(Restriction[] p_restrictions)
	{
		super(p_restrictions);
	}

	@Override
	protected String getOperatorCode()
	{
		return " or ";
	}

	@Override
	protected String innerBuildSql()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
