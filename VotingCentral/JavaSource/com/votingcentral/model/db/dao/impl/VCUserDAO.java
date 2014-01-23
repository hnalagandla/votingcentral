package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCUserDAO;
import com.votingcentral.model.db.dao.IVCUserRolesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserTO;

public class VCUserDAO extends RdbmsDAO implements IVCUserDAO {

	private static Log log = LogFactory.getLog(VCUserDAO.class);

	public VCUserTO getUserByUserName(String loginName) throws SQLException {
		String sql = SQLResources.getSQLResource("get.vcuser.by.username");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserTO vto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, loginName);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserTO();
				fillVCUserTO(rs, vto);
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
		return vto;
	}

	public VCUserTO getUserByEmailAddress(String email) throws SQLException {

		String sql = SQLResources.getSQLResource("get.vcuser.by.email.address");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserTO vto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, email);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserTO();
				fillVCUserTO(rs, vto);
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
		return vto;

	}

	/**
	 *  
	 */
	public boolean createUser(VCUserTO vto, PersonalConfigTO pto)
			throws SQLException {

		String sql1 = SQLResources.getSQLResource("insert.new.vc.user");
		String sql2 = SQLResources.getSQLResource("insert.new.personal.config");
		String sql3 = SQLResources.getSQLResource("insert.new.user.roles");
		//
		// How many times do you want to retry the transaction
		// (or at least _getting_ a connection)?
		//
		int retryCount = 5;
		boolean transactionCompleted = false;
		boolean vcUserInsert = false;
		boolean pcInsert = false;

		Connection conn = null;
		PreparedStatement pps1 = null;
		PreparedStatement pps2 = null;
		int rows = 0;
		do {
			try {
				retryCount = 0;
				conn = VCDAOFactory.getConnection();
				conn.setAutoCommit(false);

				pps1 = conn.prepareStatement(sql1);

				if (vto.getFirstName() != null) {
					pps1.setString(1, vto.getFirstName());
				} else {
					pps1.setNull(1, Types.VARCHAR);
				}

				if (vto.getLastName() != null) {
					pps1.setString(2, vto.getLastName());
				} else {
					pps1.setNull(2, Types.VARCHAR);
				}

				if (vto.getMiddleInitial() != null) {
					pps1.setString(3, vto.getMiddleInitial());
				} else {
					pps1.setNull(3, Types.VARCHAR);
				}

				if (vto.getMiddleName() != null) {
					pps1.setString(4, vto.getMiddleName());
				} else {
					pps1.setNull(4, Types.VARCHAR);
				}

				pps1.setString(5, vto.getEmailAddress());

				if (vto.getBirthDay() != null
						&& vto.getBirthDay().trim().length() > 0) {
					pps1.setInt(6, new Integer(vto.getBirthDay()).intValue());
				} else {
					pps1.setNull(6, Types.INTEGER);
				}

				if (vto.getBirthMonth() != null
						&& vto.getBirthMonth().trim().length() > 0) {
					pps1.setInt(7, new Integer(vto.getBirthMonth()).intValue());
				} else {
					pps1.setNull(7, Types.INTEGER);
				}

				pps1.setInt(8, new Integer(vto.getBirthYear()).intValue());

				pps1.setString(9, vto.getGender());

				pps1.setString(10, vto.getUserName());
				pps1.setString(11, vto.getDisplayUserName());
				pps1.setString(12, vto.getMailingAddress1());
				pps1.setString(13, vto.getMailingAddress2());
				pps1.setString(14, vto.getCity());
				pps1.setInt(15, vto.getStateId());
				pps1.setString(16, vto.getZipCode1());
				pps1.setString(17, vto.getZipCode2());
				pps1.setInt(18, vto.getCountryId());
				pps1.setString(19, vto.getPhoneCountryCode());
				pps1.setString(20, vto.getPhoneAreaCode());
				pps1.setString(21, vto.getPhoneNum1());
				pps1.setString(22, vto.getPhoneNum2());
				pps1.setString(23, vto.getAccountStatus());
				rows = pps1.executeUpdate();
				if (rows == 1) {
					vcUserInsert = true;
				}

				Statement stmt = conn.createStatement();
				ResultSet rs = null;
				long autoIncKeyFromFunc = -1;
				rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
				if (rs.next()) {
					autoIncKeyFromFunc = rs.getLong(1);
					vto.setUserId(autoIncKeyFromFunc);
				} else {
					// throw an exception from here
				}
				log.debug("The autoincrement id inserted now is :"
						+ autoIncKeyFromFunc);

				pps2 = conn.prepareStatement(sql2);
				pps2.setLong(1, autoIncKeyFromFunc);
				pps2.setString(2, pto.getUserName());
				pps2.setString(3, pto.getEmailConfCode());
				pps2.setString(4, pto.getSecurityQuestion());
				pps2.setString(5, pto.getSecurityAnswer());
				pps2.setString(6, pto.getEncryptedPassword());
				// the first temp pswd is TEMP.
				pps2.setString(7, "TEMP");
				rows = pps2.executeUpdate();
				if (rows == 1) {
					pcInsert = true;
				}
				// The third sql starts here
				List userRoles = vto.getUserRoles();
				if (userRoles != null) {
					for (int i = 0; i < userRoles.size(); i++) {
						DAOFactory dao = DAOFactory.getDAOFactory();
						IVCUserRolesDAO vdao = dao.getVCUserRolesDAO();
						vdao.addUserRole(vto.getDisplayUserName(), userRoles
								.get(i).toString());
					}
				}
				transactionCompleted = true;
				conn.commit();
				conn.setAutoCommit(true);
				conn.close();
				conn = null;
			} catch (SQLException e) {
				//
				// The two SQL states that are 'retry-able' are 08S01
				// for a communications error, and 41000 for deadlock.
				//
				// Only retry if the error was due to a stale connection,
				// communications problem or deadlock
				//
				handleSQLException(e, pps1);
				handleSQLException(e, pps2);
				String sqlState = e.getSQLState();

				if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
					retryCount--;
				} else {
					retryCount = 0;
					throw e;
				}
			} finally {
				try {
					if (pps1 != null) {
						pps1.close();
						pps1 = null;
					}
					if (pps2 != null) {
						pps2.close();
						pps2 = null;
					}

				} catch (SQLException e) {
					handleSQLException(e, pps1);
					handleSQLException(e, pps2);
					throw e;
				}
				if (conn != null) {
					try {
						//
						// If we got here, and conn is not null, the
						// transaction should be rolled back, as not
						// all work has been done
						try {
							conn.rollback();
						} finally {
							conn.close();
						}
					} catch (SQLException sqlEx) {
						//
						// If we got an exception here, something
						// pretty serious is going on, so we better
						// pass it up the stack, rather than just
						// logging it. . .

						throw sqlEx;
					}
				}
			}
		} while (!transactionCompleted && (retryCount > 0));

		return transactionCompleted;
	}

	public void setAccountStatusByLoginName(String loginName, String status)
			throws SQLException {

		String sql = SQLResources
				.getSQLResource("set.vcuser.account.status.by.username");
		log.debug(sql);
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, status);
			pps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			pps.setString(3, loginName);
			numRows = pps.executeUpdate();
		} catch (SQLException e) {
			log.fatal("setAccountStatusByLoginName :", e);
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	private void fillVCUserTO(ResultSet rs, VCUserTO vto) throws SQLException {

		vto.setUserId(rs.getLong("USER_ID"));

		String value = "";

		value = rs.getString("FIRST_NAME");
		if (value != null) {
			vto.setFirstName(value);
		}

		value = rs.getString("LAST_NAME");
		if (value != null) {
			vto.setLastName(value);
		}

		value = rs.getString("MIDDLE_INITIAL");
		if (value != null) {
			vto.setMiddleInitial(value);
		}

		value = rs.getString("MIDDLE_NAME");
		if (value != null) {
			vto.setMiddleName(value);
		}

		int dob = rs.getInt("DOB");
		vto.setBirthDay(Integer.toString(dob));

		int mob = rs.getInt("MOB");
		vto.setBirthMonth(Integer.toString(mob));

		int yob = rs.getInt("YOB");
		vto.setBirthYear(Integer.toString(yob));

		value = rs.getString("GENDER");
		if (value != null) {
			vto.setGender(value);
		}

		value = rs.getString("EMAIL_ADDRESS");
		if (value != null) {
			vto.setEmailAddress(value);
		}

		value = rs.getString("USER_NAME");
		if (value != null) {
			vto.setUserName(value);
		}

		value = rs.getString("DISPLAY_USER_NAME");
		if (value != null) {
			vto.setDisplayUserName(value);
		}

		value = rs.getString("MAILING_ADDRESS1");
		if (value != null) {
			vto.setMailingAddress1(value);
		}

		value = rs.getString("MAILING_ADDRESS2");
		if (value != null) {
			vto.setMailingAddress2(value);
		}

		value = rs.getString("CITY");
		if (value != null) {
			vto.setCity(value);
		}

		int cid = rs.getInt("STATE");
		if (value != null) {
			vto.setStateId(cid);
		}

		value = rs.getString("ZIP_CODE1");
		if (value != null) {
			vto.setZipCode1(value);
		}

		value = rs.getString("ZIP_CODE2");
		if (value != null) {
			vto.setZipCode2(value);
		}

		value = rs.getString("PHONE_COUNTRY_CODE");
		if (value != null) {
			vto.setPhoneCountryCode(value);
		}

		value = rs.getString("PHONE_AREA_CODE");
		if (value != null) {
			vto.setPhoneAreaCode(value);
		}

		value = rs.getString("PHONE_NUM_1");
		if (value != null) {
			vto.setPhoneNum1(value);
		}

		value = rs.getString("PHONE_NUM_2");
		if (value != null) {
			vto.setPhoneNum2(value);
		}

		int c = rs.getInt("COUNTRY");
		vto.setCountryId(c);

		value = rs.getString("ACCOUNT_STATUS");
		if (value != null) {
			vto.setAccountStatus(value);
		}

		Timestamp d = rs.getTimestamp("CREATE_TIMESTAMP");
		if (d != null) {
			vto.setCreateTimestamp(d);
		}

		d = rs.getTimestamp("MODIFY_TIMESTAMP");

		if (d != null) {
			vto.setModifyTimestamp(d);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserDAO#getUserByEmailAddressId(long)
	 */
	public VCUserTO getUserById(long userId) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.vcuser.by.email.address.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserTO vto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserTO();
				fillVCUserTO(rs, vto);
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
		return vto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserDAO#updateUser(com.votingcentral.model.db.dao.to.VCUserTO,
	 *      com.votingcentral.model.db.dao.to.PersonalConfigTO)
	 */
	public boolean updateUser(VCUserTO vto, PersonalConfigTO pto)
			throws SQLException {
		String sql1 = SQLResources.getSQLResource("update.vc.user");
		String sql2 = SQLResources.getSQLResource("update.personal.config");

		//
		// How many times do you want to retry the transaction
		// (or at least _getting_ a connection)?
		//
		int retryCount = 5;
		boolean transactionCompleted = false;
		boolean vcUserUpdate = false;
		boolean pcUpdate = false;

		Connection conn = null;
		PreparedStatement pps1 = null;
		PreparedStatement pps2 = null;
		int rows = 0;
		do {
			try {
				retryCount = 0;
				conn = VCDAOFactory.getConnection();
				conn.setAutoCommit(false);

				pps1 = conn.prepareStatement(sql1);

				if (vto.getFirstName() != null
						&& vto.getFirstName().length() > 0) {
					pps1.setString(1, vto.getFirstName());
				} else {
					pps1.setNull(1, Types.VARCHAR);
				}

				if (vto.getLastName() != null && vto.getLastName().length() > 0) {
					pps1.setString(2, vto.getLastName());
				} else {
					pps1.setNull(2, Types.VARCHAR);
				}

				if (vto.getMiddleInitial() != null
						&& vto.getMiddleInitial().length() > 0) {
					pps1.setString(3, vto.getMiddleInitial());
				} else {
					pps1.setNull(3, Types.VARCHAR);
				}

				if (vto.getMiddleName() != null
						&& vto.getMiddleName().length() > 0) {
					pps1.setString(4, vto.getMiddleName());
				} else {
					pps1.setNull(4, Types.VARCHAR);
				}

				pps1.setString(5, vto.getEmailAddress());

				if (vto.getBirthDay() != null
						&& vto.getBirthDay().trim().length() > 0) {
					pps1.setInt(6, new Integer(vto.getBirthDay()).intValue());
				} else {
					pps1.setNull(6, Types.INTEGER);
				}

				if (vto.getBirthMonth() != null
						&& vto.getBirthMonth().trim().length() > 0) {
					pps1.setInt(7, new Integer(vto.getBirthMonth()).intValue());
				} else {
					pps1.setNull(7, Types.INTEGER);
				}

				pps1.setInt(8, new Integer(vto.getBirthYear()).intValue());

				pps1.setString(9, vto.getGender());

				pps1.setString(10, vto.getUserName());
				pps1.setString(11, vto.getDisplayUserName());
				pps1.setString(12, vto.getMailingAddress1());
				pps1.setString(13, vto.getMailingAddress2());
				pps1.setString(14, vto.getCity());
				pps1.setInt(15, vto.getStateId());
				pps1.setString(16, vto.getZipCode1());
				pps1.setString(17, vto.getZipCode2());
				pps1.setInt(18, vto.getCountryId());
				pps1.setString(19, vto.getPhoneCountryCode());
				pps1.setString(20, vto.getPhoneAreaCode());
				pps1.setString(21, vto.getPhoneNum1());
				pps1.setString(22, vto.getPhoneNum2());
				pps1.setString(23, vto.getAccountStatus());
				// for the where clause.
				pps1.setLong(24, vto.getUserId());
				rows = pps1.executeUpdate();
				if (rows == 1) {
					vcUserUpdate = true;
				}
				pps2 = conn.prepareStatement(sql2);

				pps2.setString(1, pto.getSecurityQuestion());
				pps2.setString(2, pto.getSecurityAnswer());
				pps2.setString(3, pto.getEncryptedPassword());
				// for the where clause
				pps2.setLong(4, pto.getUserId());
				rows = pps2.executeUpdate();
				if (rows == 1) {
					pcUpdate = true;
				}
				transactionCompleted = true;
				conn.commit();
				conn = null;
			} catch (SQLException e) {
				//
				// The two SQL states that are 'retry-able' are 08S01
				// for a communications error, and 41000 for deadlock.
				//
				// Only retry if the error was due to a stale connection,
				// communications problem or deadlock
				//
				log.fatal("SQLException: " + e.getMessage());
				log.fatal("SQLState: " + e.getSQLState());
				log.fatal("VendorError: " + e.getErrorCode());
				String sqlState = e.getSQLState();

				if ("08S01".equals(sqlState) || "41000".equals(sqlState)) {
					retryCount--;
				} else {
					retryCount = 0;
					throw e;
				}
			} finally {
				try {
					if (pps1 != null) {
						pps1.close();
						pps1 = null;
					}

				} catch (SQLException e) {
					log.fatal("Problem closing the prepared statements", e);
					throw e;
				}
				if (conn != null) {
					try {
						//
						// If we got here, and conn is not null, the
						// transaction should be rolled back, as not
						// all work has been done
						try {
							conn.rollback();
						} finally {
							conn.close();
						}
					} catch (SQLException sqlEx) {
						//
						// If we got an exception here, something
						// pretty serious is going on, so we better
						// pass it up the stack, rather than just
						// logging it. . .

						throw sqlEx;
					}
				}
			}
		} while (!transactionCompleted && (retryCount > 0));

		return transactionCompleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserDAO#updateUser(com.votingcentral.model.db.dao.to.VCUserTO)
	 */
	public boolean updateUser(VCUserTO vto) throws SQLException {
		String sql1 = SQLResources.getSQLResource("update.vc.user");
		Connection conn = null;
		PreparedStatement pps1 = null;
		int rows = 0;

		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql1);

			if (vto.getFirstName() != null && vto.getFirstName().length() > 0) {
				pps1.setString(1, vto.getFirstName());
			} else {
				pps1.setNull(1, Types.VARCHAR);
			}

			if (vto.getLastName() != null && vto.getLastName().length() > 0) {
				pps1.setString(2, vto.getLastName());
			} else {
				pps1.setNull(2, Types.VARCHAR);
			}

			if (vto.getMiddleInitial() != null
					&& vto.getMiddleInitial().length() > 0) {
				pps1.setString(3, vto.getMiddleInitial());
			} else {
				pps1.setNull(3, Types.VARCHAR);
			}

			if (vto.getMiddleName() != null && vto.getMiddleName().length() > 0) {
				pps1.setString(4, vto.getMiddleName());
			} else {
				pps1.setNull(4, Types.VARCHAR);
			}

			pps1.setString(5, vto.getEmailAddress());

			if (vto.getBirthDay() != null
					&& vto.getBirthDay().trim().length() > 0) {
				pps1.setInt(6, new Integer(vto.getBirthDay()).intValue());
			} else {
				pps1.setNull(6, Types.INTEGER);
			}

			if (vto.getBirthMonth() != null
					&& vto.getBirthMonth().trim().length() > 0) {
				pps1.setInt(7, new Integer(vto.getBirthMonth()).intValue());
			} else {
				pps1.setNull(7, Types.INTEGER);
			}

			pps1.setInt(8, new Integer(vto.getBirthYear()).intValue());

			pps1.setString(9, vto.getGender());

			pps1.setString(10, vto.getUserName());
			pps1.setString(11, vto.getDisplayUserName());
			pps1.setString(12, vto.getMailingAddress1());
			pps1.setString(13, vto.getMailingAddress2());
			pps1.setString(14, vto.getCity());
			pps1.setInt(15, vto.getStateId());
			pps1.setString(16, vto.getZipCode1());
			pps1.setString(17, vto.getZipCode2());
			pps1.setInt(18, vto.getCountryId());
			pps1.setString(19, vto.getPhoneCountryCode());
			pps1.setString(20, vto.getPhoneAreaCode());
			pps1.setString(21, vto.getPhoneNum1());
			pps1.setString(22, vto.getPhoneNum2());
			pps1.setString(23, vto.getAccountStatus());
			// for the where clause.
			pps1.setLong(24, vto.getUserId());
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
		return rows > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserDAO#getAllPollStartedEmailableUsers()
	 */
	public List getAllPollStartedEmailableUsers() throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.all.poll.started.emailable.users");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserTO vto = null;
		List users = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserTO();
				fillVCUserTO(rs, vto);
				users.add(vto);
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
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserDAO#getMostRecentlyJoinedUsers()
	 */
	public List getMostRecentlyJoinedUsers(int limit) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.most.recently.joined.users");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserTO vto = null;
		List users = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, limit);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserTO();
				fillVCUserTO(rs, vto);
				users.add(vto);
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
		return users;
	}
}