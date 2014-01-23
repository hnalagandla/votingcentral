/*
 * Created on Aug 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.votingcentral.model.db.dao.to.VCVacoPointsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCVacoPointsDAO {
	public void createPointsByUserId(VCVacoPointsTO vpto) throws SQLException;

	public void updatePointsByUserId(VCVacoPointsTO vpto) throws SQLException;

	public List getPointsByUserId(long userId, Date from, Date to)
			throws SQLException;

	public List getPointsByUserId(long userId) throws SQLException;

	public List getUsersAndPointsDuringPeriod(Date from, Date to)
			throws SQLException;
}