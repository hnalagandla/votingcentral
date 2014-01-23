/*
 * Created on Oct 19, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WordFilter {
	private static Log log = LogFactory.getLog(WordFilter.class);

	private static String[] filterWords = { "assole", "asshole", "bastard",
			"bitch", "boob", "boobs", "butt", "cunt", "dumbass", "fart",
			"farts", "fuck", "fucker", "fucked", "fucks", "penis", "piss",
			"vagina" };

	public static boolean isObscene(String str) {
		int length = filterWords.length;
		String temp = str.toLowerCase();
		log.debug("Temp :" + temp);
		for (int i = 0; i < length; i++) {
			if (temp.indexOf(filterWords[i]) != -1)
				return true;
		}
		return false;
	}

}