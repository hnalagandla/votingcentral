/*
 * Created on May 22, 2007
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

import com.votingcentral.model.db.dao.IVCUserLinksDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCUserLinksTO;
import com.votingcentral.model.enums.VCUserLinkStateEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserLinksDAO extends RdbmsDAO implements IVCUserLinksDAO {

	private static Log log = LogFactory.getLog(VCUserLinksDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getAllLinksByUserId(long)
	 */
	public List getAllLinksByUserId(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.all.links.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);

			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getAcceptedLinksByUserId(long)
	 */
	public List getAcceptedLinksByUserId(long userId) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.accepted.links.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			pps.setLong(2, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getAllLinksInitiatedByUserId(long)
	 */
	public List getAllLinksRequestedByUserId(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.all.links.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);

			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getAllLinksRecievedByUserId(long)
	 */
	public List getAllLinksRecievedByUserId(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.all.links.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);

			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#createLink(com.votingcentral.model.db.dao.to.VCUserLinksTO)
	 */
	public void createLink(VCUserLinksTO vulto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.into.vc.user.links");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, vulto.getSourceUserId());
			pps.setString(2, vulto.getSourceUserEmail());
			pps.setString(3, vulto.getSourceUserName());
			pps.setLong(4, vulto.getDestUserId());
			pps.setString(5, vulto.getDestUserEmail());
			pps.setString(6, vulto.getDestUserName());
			pps.setInt(7, vulto.getLinkStateEnum().getId());
			pps.setString(8, vulto.getLinkComments());
			pps.setString(9, vulto.getAcceptCode());
			pps.setString(10, vulto.getRejectCode());
			int numRows = pps.executeUpdate();
		} catch (SQLException e) {
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
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#updateLink(com.votingcentral.model.db.dao.to.VCUserLinksTO)
	 */
	public void updateLink(VCUserLinksTO vulto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.into.vc.user.links");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, vulto.getDestUserId());
			pps.setString(2, vulto.getDestUserEmail());
			pps.setString(3, vulto.getDestUserName());
			pps.setInt(4, vulto.getLinkStateEnum().getId());
			//where clause
			pps.setLong(5, vulto.getLinkId());
			int numRows = pps.executeUpdate();
		} catch (SQLException e) {
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

	private void fillVCUserLinksTO(ResultSet rs, VCUserLinksTO vulto)
			throws SQLException {
		long linkId = rs.getLong("LINK_ID");
		vulto.setLinkId(linkId);

		long srcUserId = rs.getLong("SOURCE_USER_ID");
		vulto.setSourceUserId(srcUserId);

		String local = rs.getString("SOURCE_USER_NAME");
		vulto.setSourceUserName(local);

		local = rs.getString("SOURCE_USER_EMAIL");
		vulto.setSourceUserEmail(local);

		long destUserId = rs.getLong("DEST_USER_ID");
		vulto.setDestUserId(destUserId);

		local = rs.getString("DEST_USER_NAME");
		vulto.setDestUserName(local);

		local = rs.getString("DEST_USER_EMAIL");
		vulto.setDestUserEmail(local);

		int linkState = rs.getInt("LINK_STATE");
		vulto.setLinkStateEnum(VCUserLinkStateEnum.get(linkState));

		Timestamp pollCreateTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		vulto.setCreateTimestamp(pollCreateTimestamp);

		Timestamp pollModifyTimestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		vulto.setModifyTimestamp(pollModifyTimestamp);

		String comments = rs.getString("LINK_COMMENTS");
		vulto.setLinkComments(comments);

		local = rs.getString("ACCEPT_CODE");
		vulto.setAcceptCode(local);

		local = rs.getString("REJECT_CODE");
		vulto.setRejectCode(local);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getDirectLinkByUserId(long,
	 *      long)
	 */
	public VCUserLinksTO getDirectLinkBetwenUsersByUserId(long userIdA,
			long userIdB) throws SQLException {
		String sql = SQLResources.getSQLResource("get.direct.link.by.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserLinksTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userIdA);
			pps.setLong(2, userIdB);
			pps.setLong(3, userIdB);
			pps.setLong(4, userIdA);
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return vulto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getDirectLinkByUserName(java.lang.String,
	 *      java.lang.String)
	 */
	public VCUserLinksTO getDirectLinkBetwenUsersByUserName(String userNameA,
			String userNameB) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.direct.link.by.user.name");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserLinksTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, userNameA);
			pps.setString(2, userNameB);
			pps.setString(3, userNameB);
			pps.setString(4, userNameA);
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return vulto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getDirectLinkByEmail(java.lang.String,
	 *      java.lang.String)
	 */
	public VCUserLinksTO getDirectLinkBetwenUsersByEmail(String emailA,
			String emailB) throws SQLException {
		String sql = SQLResources.getSQLResource("get.direct.link.by.email");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserLinksTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, emailA);
			pps.setString(2, emailB);
			pps.setString(3, emailB);
			pps.setString(4, emailA);
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return vulto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getLinkByAcceptOrRejectCode(java.lang.String)
	 */
	public VCUserLinksTO getLinkByAcceptOrRejectCode(String code)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.direct.link.by.accept.or.reject.code");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserLinksTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, code);
			pps.setString(2, code);
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return vulto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getPendingLinksRequestedByUserId(long)
	 */
	public List getPendingLinksRequestedByUserId(long userId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.pending.links.requested.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);

			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserLinksDAO#getPendingLinksRecievedByUserId(long)
	 */
	public List getPendingLinksRecievedByUserId(long userId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.pending.links.recieved.by.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);

			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserLinksTO vulto = new VCUserLinksTO();
				fillVCUserLinksTO(rs, vulto);
				results.add(vulto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}
}