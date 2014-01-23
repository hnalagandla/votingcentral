/*
 * Created on Aug 13, 2005
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
public class CategoryTO {
	
	private String superCategory;
	private String category;
	private String subCategory;
	
	public CategoryTO () {
		super();
		this.superCategory = "";
		this.category = "";
		this.subCategory = "";
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return Returns the subCategory.
	 */
	public String getSubCategory() {
		return subCategory;
	}
	/**
	 * @param subCategory The subCategory to set.
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	/**
	 * @return Returns the superCategory.
	 */
	public String getSuperCategory() {
		return superCategory;
	}
	/**
	 * @param superCategory The superCategory to set.
	 */
	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}
}
