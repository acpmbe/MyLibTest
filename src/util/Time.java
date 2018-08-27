package util;

import java.util.*;

public class Time
{

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Date GetDate()
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return ca.getTime();
	}

}
