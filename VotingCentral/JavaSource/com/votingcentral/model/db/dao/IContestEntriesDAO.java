/*
 * Created on Jan 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.votingcentral.model.db.dao.to.ContestEntryTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IContestEntriesDAO {
	public ContestEntryTO getContestEntryByFileId(String fileId)
			throws SQLException, IOException;

	public List getContestEntriesByTypeAndStatus(String contestTypeEnum,
			String contestFileStatusEnum) throws IOException, SQLException;

	public List getContestEntriesByTypeStatusUserId(String contestTypeEnum,
			String contestFileStatusEnum, long userId) throws IOException,
			SQLException;

	public List getContestEntriesByFileStatus(String contestFileStatusEnum)
			throws IOException, SQLException;

	public List getOldestContestEntriesByTypeAndFileStatus(
			String contestTypeEnum, String fileStatusEnum, int numFiles)
			throws IOException, SQLException;

	public void addContestEntry(ContestEntryTO cto) throws IOException,
			SQLException;

	public int deleteContestEntriesByPollId(ContestEntryTO cto)
			throws SQLException;

	public void updateContestEntryForFileId(ContestEntryTO cto)
			throws IOException, SQLException;

	public void updateContestEntryStatusByFileId(String fileId,
			String contestsFileStatusEnum) throws IOException, SQLException;

	public List getContestEntriesByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws IOException, SQLException;

	public List getContestEntriesByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws IOException, SQLException;

	public List getContestEntriesNotInUserByType(long emailAddressId,
			String contestType, int limit) throws IOException, SQLException;

}