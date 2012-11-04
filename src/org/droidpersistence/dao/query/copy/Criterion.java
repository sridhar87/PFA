package org.droidpersistence.dao.query.copy;

import java.util.ArrayList;
import java.util.List;

public class Criterion
{

	private final String m_tableName;

	private final List<Restriction> m_restrictionsList = new ArrayList<Restriction>();

	public Criterion(String p_tableName)
	{
		this.m_tableName = p_tableName;
	}

	public void add(Restriction p_restriction)
	{
		p_restriction.setCriterion(this);
		this.m_restrictionsList.add(p_restriction);
	}

	public String getTableName()
	{
		return this.m_tableName;
	}
}
