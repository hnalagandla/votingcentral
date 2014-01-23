/*
 * Created on Apr 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.VCUserInfoTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCUserInfoDAO {
	public VCUserInfoTO getUserById(long userId) throws SQLException;

	public void createUser(VCUserInfoTO vto) throws SQLException;

	public boolean updateUser(VCUserInfoTO vto) throws SQLException;

	public void incrementProfileViewsCount(long userId) throws SQLException;

	public void setImageIdByUser(long userId, String imageId,
			boolean isImagePublic) throws SQLException;
}