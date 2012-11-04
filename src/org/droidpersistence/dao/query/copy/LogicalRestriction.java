package org.droidpersistence.dao.query.copy;

import java.util.ArrayList;
import java.util.List;

abstract class LogicalRestriction extends Restriction
{

	private final List<Restriction> m_restrictionList = new ArrayList<Restriction>();

	protected LogicalRestriction(Restriction[] p_restrictions)
	{

		for (Restriction v_restriction : p_restrictions)
		{
			this.m_restrictionList.add(v_restriction);
		}
	}

	protected abstract String getOperatorCode();

}
