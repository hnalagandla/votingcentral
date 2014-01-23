/*
 * Created on Aug 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserTO {

	private VCUserTO vto;
	private PersonalConfigTO pto;
	
	
	/**
	 * @param vto
	 * @param pto
	 */
	public UserTO(VCUserTO vto, PersonalConfigTO pto) {
		super();
		this.vto = vto;
		this.pto = pto;
	}
	
	
	/**
	 * @return Returns the pto.
	 */
	public PersonalConfigTO getPto() {
		return pto;
	}
	/**
	 * @param pto The pto to set.
	 */
	public void setPto(PersonalConfigTO pto) {
		this.pto = pto;
	}
	/**
	 * @return Returns the vto.
	 */
	public VCUserTO getVto() {
		return vto;
	}
	/**
	 * @param vto The vto to set.
	 */
	public void setVto(VCUserTO vto) {
		this.vto = vto;
	}
}

