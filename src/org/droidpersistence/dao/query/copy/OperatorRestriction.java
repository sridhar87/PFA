package org.droidpersistence.dao.query.copy;

abstract class OperatorRestriction extends Restriction
{

	private final String m_name;

	protected OperatorRestriction(String p_name)
	{
		this.m_name = p_name;
	}

	@Override
	protected String innerBuildSql()
	{
		return this.getName() + " " + this.getSqlOperation() + " ";
	}

	public String getName()
	{
		return this.m_name;
	}

	protected abstract String getSqlOperation();

}
