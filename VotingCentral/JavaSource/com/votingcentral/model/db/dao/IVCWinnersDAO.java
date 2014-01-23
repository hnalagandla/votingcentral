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

import com.votingcentral.model.db.dao.to.VCWinnersTO;
import com.votingcentral.model.enums.VCWinTypeCodeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCWinnersDAO {
	public void addWinner(VCWinnersTO wto) throws SQLException;

	public VCWinnersTO getLastWinnerByWinType(VCWinTypeCodeEnum winTypeCode)
			throws SQLException;

	public VCWinnersTO getWinnerRecordByTimeRange(
			VCWinTypeCodeEnum winTypeCode, Date start, Date end)
			throws SQLException;

	public List getAllWinners(int limit) throws SQLException;
}