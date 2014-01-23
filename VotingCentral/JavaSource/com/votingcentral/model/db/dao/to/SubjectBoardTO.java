/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SubjectBoardTO implements DataTransferObject{
	private Collection subjects;
		
	public SubjectBoardTO() {
		super();
		this.subjects = null;
	}
	
	/**
	 * @return Returns the subjects.
	 */
	public Collection getSubjects() {
		return subjects;
	}
	/**
	 * @param subjects The subjects to set.
	 */
	public void setSubjects(Collection subjects) {
		this.subjects = subjects;
	}
	/**
	 * @param messages The messages to set.
	 */
	public void addSubject(SubjectTO subject) {
		if(this.subjects==null)	this.subjects = new ArrayList();
		this.subjects.add(subject);
	}

}