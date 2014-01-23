/*
 * Created on Jun 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCContentDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCContentTO;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCContentDAO extends RdbmsDAO implements IVCContentDAO {
	private static Log log = LogFactory.getLog(VCContentDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCContentDAO#insertContent(com.votingcentral.model.db.dao.to.VCContentTO)
	 */
	public void insertContent(VCContentTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.into.vccontent");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, vto.getTableName());
			pps.setString(2, vto.getColumnName());
			pps.setString(3, vto.getWhereId1());
			pps.setString(4, vto.getWhereValue1());
			pps.setString(5, vto.getWhereId2());
			pps.setString(6, vto.getWhereValue2());
			pps.setString(7, vto.getWhereId3());
			pps.setString(8, vto.getWhereValue3());
			pps.setString(9, vto.getContent());
			int numRows = pps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (pps != null) {
					pps.close();
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
	 * @see com.votingcentral.model.db.dao.IVCContentDAO#deleteContent(com.votingcentral.model.db.dao.to.VCContentTO)
	 */
	public int deleteContent(VCContentTO vto) throws SQLException {
		String deleteSQL = getDeleteSQL(vto);
		Connection conn = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(deleteSQL);
			numRows = pps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCContentDAO#search(java.lang.String)
	 */
	public List search(String searchStr, long afterMaxId) throws SQLException {
		String sql = SQLResources.getSQLResource("search.for.text");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		log.debug("Executing search SQL :" + sql);
		log.debug("1 : " + searchStr + " 2:" + afterMaxId);
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, searchStr);
			pps1.setLong(2, afterMaxId);
			rs = pps1.executeQuery();
			while (rs.next()) {
				VCContentTO vto = new VCContentTO();
				fillVCContentTO(rs, vto);
				results.add(vto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps1);
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
		return results;
	}

	private void fillVCContentTO(ResultSet rs, VCContentTO vto)
			throws SQLException {
		long id = rs.getLong("ID");

		String local = rs.getString("TABLE_NAME");
		vto.setTableName(local);

		local = rs.getString("COLUMN_NAME");
		vto.setColumnName(local);

		local = rs.getString("WHERE_ID1");
		vto.setWhereId1(local);

		local = rs.getString("WHERE_VALUE1");
		vto.setWhereValue1(local);

		local = rs.getString("WHERE_ID2");
		vto.setWhereId2(local);

		local = rs.getString("WHERE_VALUE2");
		vto.setWhereId3(local);

		local = rs.getString("WHERE_ID3");
		vto.setWhereId3(local);

		local = rs.getString("WHERE_VALUE3");
		vto.setWhereValue3(local);

		local = rs.getString("CONTENT");
		vto.setContent(local);

		Timestamp createTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		vto.setCreateTimestamp(createTimestamp);

		Timestamp modifyTimestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		vto.setCreateTimestamp(modifyTimestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCContentDAO#updateContent(com.votingcentral.model.db.dao.to.VCContentTO)
	 */
	public boolean updateContent(VCContentTO vto) throws SQLException {
		String updateSQL = getUpdateSQL(vto);
		Connection conn = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(updateSQL);
			pps.setString(1, vto.getContent());
			numRows = pps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return numRows > 0;
	}

	private String getDeleteSQL(VCContentTO vto) {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append("delete from vc_content where ").append(" where_id1 = '")
				.append(vto.getWhereId1()).append("' and where_value1 = '")
				.append(vto.getWhereValue1()).append("'");

		if (vto.getWhereId2() != null && vto.getWhereId2().length() > 0) {
			buffer.append("' and where_id2 = '").append(vto.getWhereId2())
					.append("' and where_value2 = '").append(
							vto.getWhereValue2()).append("'");
		}
		if (vto.getWhereId3() != null && vto.getWhereId3().length() > 0) {
			buffer.append("' and where_id3 = '").append(vto.getWhereId3())
					.append("' and where_value3 = '").append(
							vto.getWhereValue3()).append("'");
		}

		return buffer.toString();
	}

	private String getUpdateSQL(VCContentTO vto) {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append("update vc_content set content = ? where table_name = '")
				.append(vto.getTableName()).append("' and column_name = '")
				.append(vto.getColumnName()).append("' and where_id1 = '")
				.append(vto.getWhereId1()).append("' and where_value1 = '")
				.append(vto.getWhereValue1()).append("'");

		if (vto.getWhereId2() != null && vto.getWhereId2().length() > 0) {
			buffer.append(" and where_id2 = '").append(vto.getWhereId2())
					.append("' and where_value2 = '").append(
							vto.getWhereValue2()).append("'");
		}
		if (vto.getWhereId3() != null && vto.getWhereId3().length() > 0) {
			buffer.append(" and where_id3 = '").append(vto.getWhereId3())
					.append("' and where_value3 = '").append(
							vto.getWhereValue3()).append("'");
		}
		return buffer.toString();
	}

	private String getReadSQL(VCContentTO vto) {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append("select * from vc_content where table_name = '").append(
				vto.getTableName()).append("' and column_name = '").append(
				vto.getColumnName()).append(" and where_id1 = '").append(
				vto.getWhereId1()).append("' and where_value1 = '").append(
				vto.getWhereValue1()).append("'");

		if (vto.getWhereId2() != null && vto.getWhereId2().length() > 0) {
			buffer.append(" and  where_id2 = '").append(vto.getWhereId2())
					.append("' and where_value2 = '").append(
							vto.getWhereValue2()).append("'");
		}
		if (vto.getWhereId3() != null && vto.getWhereId3().length() > 0) {
			buffer.append(" and where_id3 = '").append(vto.getWhereId3())
					.append("' and where_value3 = '").append(
							vto.getWhereValue3()).append("'");
		}

		buffer.append(" where id = ").append(vto.getId());
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCContentDAO#getContent(com.votingcentral.model.db.dao.to.VCContentTO)
	 */
	public VCContentTO getContent(VCContentTO vto) throws SQLException {
		String sql = getReadSQL(vto);
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		VCContentTO res = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			rs = pps1.executeQuery();
			while (rs.next()) {
				res = new VCContentTO();
				fillVCContentTO(rs, res);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps1);
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
		return res;
	}
}