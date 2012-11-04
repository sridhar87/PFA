package org.droidpersistence.dao.query.copy;

abstract class TernaryOperatorRestriction extends BinaryOperatorRestriction
{

	private final Object m_secondValue;

	protected TernaryOperatorRestriction(String p_name, Object p_firstValue, Object p_secondValue)
	{
		super(p_name, p_firstValue);
		this.m_secondValue = p_secondValue;
	}

	@Override
	protected String innerBuildSql()
	{
		return this.getName() + " " + this.getSqlOperation() + " " + this.getSqlValue(this.getValue()) + " and "
				+ this.getSqlValue(this.m_secondValue);
	}

}
