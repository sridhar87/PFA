package org.droidpersistence.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DroidUtils
{

	public static Date convertStringToDate(String date)
	{
		Date result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try
		{
			result = format.parse(date);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static String convertDateToString(Date date)
	{
		String result = "";

		// SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try
		{
			result = format.format(date);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static boolean isEmpty(Object p_value)
	{
		if (p_value == null)
		{
			return true;
		}
		if ((p_value instanceof String) && "".equals(((String) p_value).trim()))
		{
			return true;
		}
		return false;
	}
}
