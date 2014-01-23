package com.votingcentral.util;

/**
 * @author breddy
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SqlDateConverter implements Converter 
{ 
	private Log log = LogFactory.getLog(SqlDateConverter.class);

	/**
	 * Converts a Date in String format to java.sql.Date object in yyyy-MM-dd format.
	 */
	public Object convert(Class type, Object value)
	{
		if(value == null || value.toString().length() < 1) 
			return null;
		
		java.util.Date d=null;
		DateFormat dt=DateFormat.getDateInstance(DateFormat.SHORT);
		try
		{	
			d = dt.parse(value.toString());
		}
		catch(java.text.ParseException pe)
		{
	 		log.error("convert: dateString parse Exception: ",pe);
	 		throw new ConversionException(pe.toString());
		}
				
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		return java.sql.Date.valueOf(sf.format(d));	
	}
	
}
