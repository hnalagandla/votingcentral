/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.votingcentral.model.enums.VCCategoryTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ICategoryDAO {
	public List getMostPopularCategories() throws SQLException;

	public List getDistinctSuperCategories() throws SQLException;

	public List getDistinctSuperCategoriesByType(VCCategoryTypeEnum type) throws SQLException;
	
	public List getDistinctCategories() throws SQLException;

	public List getDistinctSubCategories() throws SQLException;
}