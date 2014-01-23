/*
 * Created on Nov 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util;

import java.util.ArrayList;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ColorAlternator {
	private ArrayList colors;

	public ColorAlternator()
	{
		colors = new ArrayList();
	}
	
	public void setColor(String color)
	{
		colors.add(color);
	}
	
	public String getNext()
	{
		if(colors==null || colors.isEmpty())
			return null;
		
		String color = colors.remove(0).toString();
		colors.add(color);
		return color;
	}

}
