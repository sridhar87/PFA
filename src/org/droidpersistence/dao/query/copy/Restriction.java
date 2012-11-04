package org.droidpersistence.dao.query.copy;

public abstract class Restriction
{

	private Criterion m_criterion;

	public static Restriction and(Restriction... p_restrictions)
	{
		return new RestrictionAnd(p_restrictions);
	}

	public static Restriction between(String p_fieldName, Object p_firstValue, Object p_secondValue)
	{
		return new RestrictionBetween(p_fieldName, p_firstValue, p_secondValue);
	}

	public static Restriction or(Restriction... p_restrictions)
	{
		return new RestrictionOr(p_restrictions);
	}

	public static Restriction isNull(String p_fieldName)
	{
		return new RestrictionIsNull(p_fieldName);
	}

	public static Restriction isNotNull(String p_fieldName)
	{
		return new RestrictionIsNotNull(p_fieldName);
	}

	public static Restriction eq(String p_fieldName, Object p_value)
	{
		return new RestrictionEq(p_fieldName, p_value);
	}

	public static Restriction notEq(String p_fieldName, Object p_value)
	{
		return new RestrictionNotEq(p_fieldName, p_value);
	}

	public static Restriction GE(String p_fieldName, Object p_value)
	{
		return new RestrictionGE(p_fieldName, p_value);
	}

	public static Restriction GT(String p_fieldName, Object p_value)
	{
		return new RestrictionGT(p_fieldName, p_value);
	}

	public static Restriction LE(String p_fieldName, Object p_value)
	{
		return new RestrictionLE(p_fieldName, p_value);
	}

	public static Restriction LT(String p_fieldName, Object p_value)
	{
		return new RestrictionLT(p_fieldName, p_value);
	}

	public static Restriction In(String p_fieldName, Object... p_value)
	{
		return new RestrictionIn(p_fieldName, p_value);

	}

	public static Restriction NotIn(String p_fieldName, Object... p_value)
	{
		return new RestrictionNotIn(p_fieldName, p_value);

	}

	public static Restriction Like(String p_fieldName, Object p_value)
	{
		return new RestrictionLike(p_fieldName, p_value);
	}

	public static Restriction NotLike(String p_fieldName, Object p_value)
	{
		return new RestrictionNotLike(p_fieldName, p_value);
	}

	protected Restriction()
	{
	}

	protected Criterion getCriterion()
	{
		return this.m_criterion;
	}

	protected void setCriterion(Criterion p_criterion)
	{
		this.m_criterion = p_criterion;
	}

	protected final String buildSql()
	{
		return this.m_criterion.getTableName() + "." + this.innerBuildSql();
	}

	protected abstract String innerBuildSql();

}
