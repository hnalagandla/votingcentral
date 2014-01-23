/*
 * Created on Jun 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.votingcentral.model.db.dao.to.VCContentTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCContentDAO {

	public void insertContent(VCContentTO vto) throws SQLException;

	public int deleteContent(VCContentTO vto) throws SQLException;

	public boolean updateContent(VCContentTO vto) throws SQLException;

	public VCContentTO getContent(VCContentTO vto) throws SQLException;

	public List search(String searchStr, long afterMaxId) throws SQLException;

}