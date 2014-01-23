/*
 * Created on Sep 9, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DateComparator implements Comparator{

	String sortAttribute;
	
	public DateComparator(String param)
	{
		sortAttribute = param;
	}
	
	public int compare(Object obj1, Object obj2)
	{
		try 
		{
			Method m1 = obj1.getClass().getMethod("get"+sortAttribute, null);
			Method m2 = obj2.getClass().getMethod("get"+sortAttribute, null);
			
			java.util.Date d1 = (java.util.Date) m1.invoke(obj1, null);
			java.util.Date d2 = (java.util.Date) m2.invoke(obj2, null);
			
			if(d1.after(d2))  return -1; 
			else if (d2.after(d1))  return 1;
			else return 0;
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}		
		
		return 0;
	}
	
}