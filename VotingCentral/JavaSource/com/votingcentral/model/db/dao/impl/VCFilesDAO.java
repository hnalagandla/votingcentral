/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCFilesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCFileTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCFilesDAO extends RdbmsDAO implements IVCFilesDAO {

	private static Log log = LogFactory.getLog(VCFilesDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCFilesDAO#getFileByFileId(java.lang.String)
	 */
	public VCFileTO getFileByFileId(String fileId) throws SQLException,
			IOException {
		String sql = SQLResources.getSQLResource("get.vc.file.by.file.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		VCFileTO vto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, fileId);
			rs = pps1.executeQuery();
			while (rs.next()) {
				vto = new VCFileTO();
				fillVCFileTO(rs, vto);
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return vto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCFilesDAO#addFile(com.votingcentral.model.db.dao.to.VCFileTO)
	 */
	public void addFile(VCFileTO vto) throws IOException, SQLException {
		String sql = SQLResources.getSQLResource("add.vc.file");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, vto.getFileId());
			pps1.setLong(2, vto.getUserId());
			pps1.setString(3, vto.getFileType());
			pps1.setString(4, vto.getFileName());
			pps1.setString(5, vto.getFileMimeType());
			//setup stream for blob
			ByteArrayInputStream inStream = new ByteArrayInputStream(vto
					.getFileContent());
			pps1.setBinaryStream(6, inStream, inStream.available());
			int rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCFilesDAO#updateFileForFileId(com.votingcentral.model.db.dao.to.VCFileTO)
	 */
	public void updateFileForFileId(VCFileTO vto) throws IOException,
			SQLException {
		String sql = SQLResources.getSQLResource("update.vc.file.by.file.id");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, vto.getFileId());
			pps1.setLong(2, vto.getUserId());
			pps1.setString(3, vto.getFileType());
			pps1.setString(4, vto.getFileName());
			pps1.setString(5, vto.getFileMimeType());

			//setup stream for blob
			ByteArrayInputStream inStream = new ByteArrayInputStream(vto
					.getFileContent());
			pps1.setBinaryStream(6, inStream, inStream.available());
			pps1.setString(7, vto.getFileId());

			int rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}

	}

	private void fillVCFileTO(ResultSet rs, VCFileTO vto) throws SQLException,
			IOException {

		String value = rs.getString("FILE_ID");
		vto.setFileId(value);

		long creatorEmailAddressId = rs.getLong("USER_ID");
		vto.setUserId(creatorEmailAddressId);

		value = rs.getString("FILE_TYPE");
		vto.setFileType(value);

		value = rs.getString("FILE_NAME");
		vto.setFileName(value);

		value = rs.getString("FILE_MIME_TYPE");
		vto.setFileMimeType(value);

		Blob fileContent = rs.getBlob("FILE_CONTENT");
		// setup the streams
		InputStream input = fileContent.getBinaryStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		// set read buffer size
		byte[] rb = new byte[1024];
		int ch = 0;
		// process blob
		while ((ch = input.read(rb)) != -1) {
			output.write(rb, 0, ch);
		}
		byte[] b = output.toByteArray();
		input.close();
		output.close();
		vto.setFileContent(b);

		Timestamp create = rs.getTimestamp("CREATE_TIMESTAMP");
		vto.setCreateTimestamp(create);

		Timestamp modify = rs.getTimestamp("MODIFY_TIMESTAMP");
		vto.setModifyTimestamp(modify);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCFilesDAO#deleteFileForFileId(com.votingcentral.model.db.dao.to.VCFileTO)
	 */
	public int deleteFileForFileId(VCFileTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("delete.vc.file.by.file.id");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		int rows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, vto.getFileId());
			rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return rows;
	}
}