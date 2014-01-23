/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.io.IOException;
import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.VCFileTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCFilesDAO {

	public VCFileTO getFileByFileId(String fileId) throws SQLException,
			IOException;

	public void addFile(VCFileTO cto) throws IOException, SQLException;

	public void updateFileForFileId(VCFileTO cto) throws IOException,
			SQLException;

	public int deleteFileForFileId(VCFileTO cto) throws SQLException;
}