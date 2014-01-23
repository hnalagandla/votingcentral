package com.votingcentral.util;

/**
 * @author breddy
 */
import java.text.DateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilDateConverter implements Converter 
{ 
	private Log log = LogFactory.getLog(UtilDateConverter.class);

	/**
	 * 
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
	 		log.error("convert:: dateString parse Exception: ",pe);
	 		throw new ConversionException(pe.toString());
		}
				
		return d;			
	}
	
}
