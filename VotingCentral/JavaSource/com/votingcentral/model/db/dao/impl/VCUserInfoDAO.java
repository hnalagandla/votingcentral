package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCUserInfoDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.enums.userinfo.DrinkTypeEnum;
import com.votingcentral.model.enums.userinfo.EducationEnum;
import com.votingcentral.model.enums.userinfo.IdeologyEnum;
import com.votingcentral.model.enums.userinfo.MaritalStatusEnum;
import com.votingcentral.model.enums.userinfo.NumChildrenEnum;
import com.votingcentral.model.enums.userinfo.PartyEnum;
import com.votingcentral.model.enums.userinfo.RaceEnum;
import com.votingcentral.model.enums.userinfo.ReligionEnum;
import com.votingcentral.model.enums.userinfo.SalaryRangeEnum;
import com.votingcentral.model.enums.userinfo.SexualOrientationEnum;
import com.votingcentral.model.enums.userinfo.SmokeTypeEnum;
import com.votingcentral.model.enums.userinfo.TShirtSizeEnum;
import com.votingcentral.model.enums.userinfo.UnionMembershipEnum;
import com.votingcentral.model.enums.userinfo.ZodiacEnum;

public class VCUserInfoDAO extends RdbmsDAO implements IVCUserInfoDAO {

	private static Log log = LogFactory.getLog(VCUserInfoDAO.class);

	/**
	 *  
	 */
	public void createUser(VCUserInfoTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.new.vc.user.info");
		Connection conn = null;
		PreparedStatement pps = null;
		int rows = 0;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);

			pps.setLong(1, vto.getUserId());

			if (vto.getProfilePrivacyLevelEnum() != null) {
				pps.setInt(2, vto.getProfilePrivacyLevelEnum().getId());
			} else {
				pps.setInt(2, VCPrivacyLevelEnum.PUBLIC.getId());
			}

			if (vto.getRaceEnum() != null) {
				pps.setInt(3, vto.getRaceEnum().getId());
			} else {
				pps.setNull(3, Types.INTEGER);
			}
			if (vto.getRacePrivacyLevelEnum() != null) {
				pps.setInt(4, vto.getRacePrivacyLevelEnum().getId());
			} else {
				pps.setNull(4, Types.INTEGER);
			}

			if (vto.getSalaryRange() != null) {
				pps.setLong(5, vto.getSalaryRange().getId());
			} else {
				pps.setNull(5, Types.NUMERIC);
			}
			if (vto.getSalaryPrivacyLevelEnum() != null) {
				pps.setInt(6, vto.getSalaryPrivacyLevelEnum().getId());
			} else {
				pps.setNull(6, Types.INTEGER);
			}

			if (vto.getUnionMember() != null) {
				pps.setInt(7, vto.getUnionMember().getId());
			} else {
				pps.setNull(7, Types.INTEGER);
			}

			if (vto.getUnionPrivacyLevelEnum() != null) {
				pps.setInt(8, vto.getUnionPrivacyLevelEnum().getId());
			} else {
				pps.setNull(8, Types.INTEGER);
			}

			if (vto.getEducation() != null) {
				pps.setInt(9, vto.getEducation().getId());
			} else {
				pps.setNull(9, Types.NUMERIC);
			}
			if (vto.getEducationPrivacyLevelEnum() != null) {
				pps.setInt(10, vto.getEducationPrivacyLevelEnum().getId());
			} else {
				pps.setNull(10, Types.INTEGER);
			}

			if (vto.getPartyEnum() != null) {
				pps.setInt(11, vto.getPartyEnum().getId());
			} else {
				pps.setNull(11, Types.NUMERIC);
			}
			if (vto.getPartyPrivacyLevelEnum() != null) {
				pps.setInt(12, vto.getPartyPrivacyLevelEnum().getId());
			} else {
				pps.setNull(12, Types.INTEGER);
			}

			if (vto.getIdeologyEnum() != null) {
				pps.setInt(13, vto.getIdeologyEnum().getId());
			} else {
				pps.setNull(13, Types.NUMERIC);
			}
			if (vto.getIdeologyPrivacyLevelEnum() != null) {
				pps.setInt(14, vto.getIdeologyPrivacyLevelEnum().getId());
			} else {
				pps.setNull(14, Types.INTEGER);
			}

			if (vto.getReligionEnum() != null) {
				pps.setInt(15, vto.getReligionEnum().getId());
			} else {
				pps.setNull(15, Types.NUMERIC);
			}
			if (vto.getReligionPrivacyLevelEnum() != null) {
				pps.setInt(16, vto.getReligionPrivacyLevelEnum().getId());
			} else {
				pps.setNull(16, Types.INTEGER);
			}

			if (vto.getZodiacEnum() != null) {
				pps.setInt(17, vto.getZodiacEnum().getId());
			} else {
				pps.setNull(17, Types.NUMERIC);
			}
			if (vto.getZodiacPrivacyLevelEnum() != null) {
				pps.setInt(18, vto.getZodiacPrivacyLevelEnum().getId());
			} else {
				pps.setNull(18, Types.INTEGER);
			}

			if (vto.getMaritalStatusEnum() != null) {
				pps.setInt(19, vto.getMaritalStatusEnum().getId());
			} else {
				pps.setNull(19, Types.NUMERIC);
			}
			if (vto.getMaritalPrivacyLevelEnum() != null) {
				pps.setInt(20, vto.getMaritalPrivacyLevelEnum().getId());
			} else {
				pps.setNull(20, Types.INTEGER);
			}

			if (vto.getImageId() != null) {
				pps.setString(21, vto.getImageId());
			} else {
				pps.setNull(21, Types.VARCHAR);
			}
			if (vto.getImagePrivacyLevelEnum() != null) {
				pps.setInt(22, vto.getImagePrivacyLevelEnum().getId());
			} else {
				pps.setNull(22, Types.INTEGER);
			}

			if (vto.getNumChildren() != null) {
				pps.setInt(23, vto.getNumChildren().getId());
			} else {
				pps.setNull(23, Types.NUMERIC);
			}
			if (vto.getNumChildPrivacyLevelEnum() != null) {
				pps.setInt(24, vto.getNumChildPrivacyLevelEnum().getId());
			} else {
				pps.setNull(24, Types.INTEGER);
			}

			if (vto.getSmokeTypeEnum() != null) {
				pps.setInt(25, vto.getSmokeTypeEnum().getId());
			} else {
				pps.setNull(25, Types.NUMERIC);
			}
			if (vto.getSmokePrivacyLevelEnum() != null) {
				pps.setInt(26, vto.getSmokePrivacyLevelEnum().getId());
			} else {
				pps.setNull(26, Types.INTEGER);
			}

			if (vto.getDrinkTypeEnum() != null) {
				pps.setInt(27, vto.getDrinkTypeEnum().getId());
			} else {
				pps.setNull(27, Types.NUMERIC);
			}
			if (vto.getDrinkPrivacyLevelEnum() != null) {
				pps.setInt(28, vto.getDrinkPrivacyLevelEnum().getId());
			} else {
				pps.setNull(28, Types.INTEGER);
			}

			if (vto.getFavQuote() != null) {
				pps.setString(29, vto.getFavQuote());
			} else {
				pps.setNull(29, Types.VARCHAR);
			}
			if (vto.getFavUrl() != null) {
				pps.setString(30, vto.getFavUrl());
			} else {
				pps.setNull(30, Types.VARCHAR);
			}
			if (vto.getSexOrientationEnum() != null) {
				pps.setInt(31, vto.getSexOrientationEnum().getId());
			} else {
				pps.setNull(31, Types.NUMERIC);
			}
			if (vto.getSexOrientPrivacyLevelEnum() != null) {
				pps.setInt(32, vto.getSexOrientPrivacyLevelEnum().getId());
			} else {
				pps.setNull(32, Types.INTEGER);
			}

			pps.setLong(33, vto.getProfileViewsCount());

			if (vto.getAboutMe() != null && vto.getAboutMe().length() > 0) {
				pps.setInt(34, vto.getSexOrientPrivacyLevelEnum().getId());
			} else {
				pps.setNull(34, Types.VARCHAR);
			}

			if (vto.getTShirtSizeEnum() != null) {
				pps.setInt(35, vto.getTShirtSizeEnum().getId());
			} else {
				pps.setNull(35, Types.NUMERIC);
			}
			if (vto.getTShirtSizePrivacyLevelEnum() != null) {
				pps.setInt(36, vto.getTShirtSizePrivacyLevelEnum().getId());
			} else {
				pps.setNull(36, Types.INTEGER);
			}
			rows = pps.executeUpdate();
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
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	private void fillVCUserInfoTO(ResultSet rs, VCUserInfoTO vto)
			throws SQLException {
		vto.setUserId(rs.getLong("user_id"));

		int value = 0;

		value = rs.getInt("profile_public");
		VCPrivacyLevelEnum privacy = VCPrivacyLevelEnum.get(value);
		if (privacy != null) {
			vto.setProfilePrivacyLevelEnum(privacy);
		}

		value = rs.getInt("race");
		vto.setRaceEnum(RaceEnum.get(value));

		value = rs.getInt("race_public");
		vto.setRacePrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("salary");
		vto.setSalaryRange(SalaryRangeEnum.get(value));

		value = rs.getInt("salary_public");
		vto.setSalaryPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("union_member");
		vto.setUnionMember(UnionMembershipEnum.get(value));

		value = rs.getInt("um_public");
		vto.setUnionPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("education");
		vto.setEducation(EducationEnum.get(value));

		value = rs.getInt("education_public");
		vto.setEducationPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("party_id");
		vto.setPartyEnum(PartyEnum.get(value));

		value = rs.getInt("party_public");
		vto.setPartyPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("ideology");
		vto.setIdeologyEnum(IdeologyEnum.get(value));

		value = rs.getInt("ideology_public");
		vto.setIdeologyPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("religion");
		vto.setReligionEnum(ReligionEnum.get(value));

		value = rs.getInt("religion_public");
		vto.setReligionPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("zodiac");
		vto.setZodiacEnum(ZodiacEnum.get(value));

		value = rs.getInt("zodiac_public");
		vto.setZodiacPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("marital_status");
		vto.setMaritalStatusEnum(MaritalStatusEnum.get(value));

		value = rs.getInt("marital_public");
		vto.setMaritalPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		String imageId = rs.getString("image_id");
		vto.setImageId(imageId);

		value = rs.getInt("image_public");
		vto.setImagePrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("children");
		vto.setNumChildren(NumChildrenEnum.get(value));

		value = rs.getInt("children_public");
		vto.setNumChildPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("smoke");
		vto.setSmokeTypeEnum(SmokeTypeEnum.get(value));

		value = rs.getInt("smoke_public");
		vto.setSmokePrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		value = rs.getInt("drink");
		vto.setDrinkTypeEnum(DrinkTypeEnum.get(value));

		value = rs.getInt("drink_public");
		vto.setDrinkPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		String favQuote = rs.getString("favorite_quote");
		vto.setFavQuote(favQuote);

		String url = rs.getString("url");
		vto.setFavUrl(url);

		Timestamp d = rs.getTimestamp("CREATE_TIMESTAMP");
		if (d != null) {
			vto.setCreateTimestamp(d);
		}
		d = rs.getTimestamp("MODIFY_TIMESTAMP");
		if (d != null) {
			vto.setModifyTimestamp(d);
		}
		value = rs.getInt("sex_orient");
		vto.setSexOrientationEnum(SexualOrientationEnum.get(value));

		value = rs.getInt("sex_orient_public");
		vto.setSexOrientPrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

		long viewsCount = rs.getLong("profile_view_count");
		vto.setProfileViewsCount(viewsCount);

		String aboutMe = rs.getString("about_me");
		vto.setAboutMe(aboutMe);

		value = rs.getInt("t_shirt_size");
		vto.setTShirtSizeEnum(TShirtSizeEnum.get(value));

		value = rs.getInt("t_shirt_size_public");
		vto.setTShirtSizePrivacyLevelEnum(VCPrivacyLevelEnum.get(value));

	}

	/**
	 *  
	 */
	public VCUserInfoTO getUserById(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.vcuser.info.by.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserInfoTO vto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserInfoTO();
				fillVCUserInfoTO(rs, vto);
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

	/**
	 *  
	 */
	public boolean updateUser(VCUserInfoTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.vc.user.info");
		Connection conn = null;
		PreparedStatement pps = null;
		int rows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);

			if (vto.getProfilePrivacyLevelEnum() != null) {
				pps.setInt(1, vto.getProfilePrivacyLevelEnum().getId());
			} else {
				pps.setInt(1, VCPrivacyLevelEnum.PUBLIC.getId());
			}

			if (vto.getRaceEnum() != null) {
				pps.setInt(2, vto.getRaceEnum().getId());
			} else {
				pps.setNull(2, Types.INTEGER);
			}
			if (vto.getRacePrivacyLevelEnum() != null) {
				pps.setInt(3, vto.getRacePrivacyLevelEnum().getId());
			} else {
				pps.setNull(3, Types.INTEGER);
			}

			if (vto.getSalaryRange() != null) {
				pps.setLong(4, vto.getSalaryRange().getId());
			} else {
				pps.setNull(4, Types.NUMERIC);
			}
			if (vto.getSalaryPrivacyLevelEnum() != null) {
				pps.setInt(5, vto.getSalaryPrivacyLevelEnum().getId());
			} else {
				pps.setNull(5, Types.INTEGER);
			}

			if (vto.getUnionMember() != null) {
				pps.setInt(6, vto.getUnionMember().getId());
			} else {
				pps.setNull(6, Types.INTEGER);
			}

			if (vto.getUnionPrivacyLevelEnum() != null) {
				pps.setInt(7, vto.getUnionPrivacyLevelEnum().getId());
			} else {
				pps.setInt(7, Types.INTEGER);
			}

			if (vto.getEducation() != null) {
				pps.setInt(8, vto.getEducation().getId());
			} else {
				pps.setNull(8, Types.NUMERIC);
			}
			if (vto.getEducationPrivacyLevelEnum() != null) {
				pps.setInt(9, vto.getEducationPrivacyLevelEnum().getId());
			} else {
				pps.setInt(9, Types.INTEGER);
			}

			if (vto.getPartyEnum() != null) {
				pps.setInt(10, vto.getPartyEnum().getId());
			} else {
				pps.setNull(10, Types.NUMERIC);
			}
			if (vto.getPartyPrivacyLevelEnum() != null) {
				pps.setInt(11, vto.getPartyPrivacyLevelEnum().getId());
			} else {
				pps.setNull(11, Types.NUMERIC);
			}

			if (vto.getIdeologyEnum() != null) {
				pps.setInt(12, vto.getIdeologyEnum().getId());
			} else {
				pps.setNull(12, Types.NUMERIC);
			}
			if (vto.getIdeologyPrivacyLevelEnum() != null) {
				pps.setInt(13, vto.getIdeologyPrivacyLevelEnum().getId());
			} else {
				pps.setNull(13, Types.NUMERIC);
			}

			if (vto.getReligionEnum() != null) {
				pps.setInt(14, vto.getReligionEnum().getId());
			} else {
				pps.setNull(14, Types.NUMERIC);
			}
			if (vto.getReligionPrivacyLevelEnum() != null) {
				pps.setInt(15, vto.getReligionPrivacyLevelEnum().getId());
			} else {
				pps.setNull(15, Types.INTEGER);
			}

			if (vto.getZodiacEnum() != null) {
				pps.setInt(16, vto.getZodiacEnum().getId());
			} else {
				pps.setNull(16, Types.NUMERIC);
			}
			if (vto.getZodiacPrivacyLevelEnum() != null) {
				pps.setInt(17, vto.getZodiacPrivacyLevelEnum().getId());
			} else {
				pps.setNull(17, Types.INTEGER);
			}

			if (vto.getMaritalStatusEnum() != null) {
				pps.setInt(18, vto.getMaritalStatusEnum().getId());
			} else {
				pps.setNull(18, Types.NUMERIC);
			}
			if (vto.getMaritalPrivacyLevelEnum() != null) {
				pps.setInt(19, vto.getMaritalPrivacyLevelEnum().getId());
			} else {
				pps.setNull(19, Types.INTEGER);
			}

			if (vto.getNumChildren() != null) {
				pps.setInt(20, vto.getNumChildren().getId());
			} else {
				pps.setNull(20, Types.NUMERIC);
			}
			if (vto.getNumChildPrivacyLevelEnum() != null) {
				pps.setInt(21, vto.getNumChildPrivacyLevelEnum().getId());
			} else {
				pps.setNull(21, Types.INTEGER);
			}

			if (vto.getSmokeTypeEnum() != null) {
				pps.setInt(22, vto.getSmokeTypeEnum().getId());
			} else {
				pps.setNull(22, Types.NUMERIC);
			}
			if (vto.getSmokePrivacyLevelEnum() != null) {
				pps.setInt(23, vto.getSmokePrivacyLevelEnum().getId());
			} else {
				pps.setNull(23, Types.INTEGER);
			}

			if (vto.getDrinkTypeEnum() != null) {
				pps.setInt(24, vto.getDrinkTypeEnum().getId());
			} else {
				pps.setNull(24, Types.NUMERIC);
			}
			if (vto.getDrinkPrivacyLevelEnum() != null) {
				pps.setInt(25, vto.getDrinkPrivacyLevelEnum().getId());
			} else {
				pps.setNull(25, Types.INTEGER);
			}

			if (vto.getFavQuote() != null) {
				pps.setString(26, vto.getFavQuote());
			} else {
				pps.setNull(26, Types.VARCHAR);
			}
			if (vto.getFavUrl() != null) {
				pps.setString(27, vto.getFavUrl());
			} else {
				pps.setNull(27, Types.VARCHAR);
			}
			if (vto.getSexOrientationEnum() != null) {
				pps.setInt(28, vto.getSexOrientationEnum().getId());
			} else {
				pps.setNull(28, Types.NUMERIC);
			}
			if (vto.getSexOrientPrivacyLevelEnum() != null) {
				pps.setInt(29, vto.getSexOrientPrivacyLevelEnum().getId());
			} else {
				pps.setNull(29, Types.INTEGER);
			}

			if (vto.getAboutMe() != null && vto.getAboutMe().length() > 0) {
				pps.setString(30, vto.getAboutMe());
			} else {
				pps.setNull(30, Types.VARCHAR);
			}

			if (vto.getTShirtSizeEnum() != null) {
				pps.setInt(31, vto.getTShirtSizeEnum().getId());
			} else {
				pps.setNull(31, Types.NUMERIC);
			}
			if (vto.getTShirtSizePrivacyLevelEnum() != null) {
				pps.setInt(32, vto.getTShirtSizePrivacyLevelEnum().getId());
			} else {
				pps.setNull(32, Types.INTEGER);
			}

			pps.setLong(33, vto.getUserId());
			rows = pps.executeUpdate();

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
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return rows > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserInfoDAO#incrementProfileViewsCount()
	 */
	public void incrementProfileViewsCount(long userId) throws SQLException {
		String sql1 = SQLResources
				.getSQLResource("increment.user.info.profile.view.count");
		String sql2 = SQLResources
				.getSQLResource("insert.user.info.profile.view.count");

		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql1);
			pps.setLong(1, userId);
			int rows = pps.executeUpdate();
			// if the row does not exist, create it.
			if (rows == 0) {
				pps = conn.prepareStatement(sql2);
				pps.setLong(1, userId);
				rows = pps.executeUpdate();
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
	 * @see com.votingcentral.model.db.dao.IVCUserInfoDAO#setImageId(java.lang.String)
	 */
	public void setImageIdByUser(long userId, String imageId,
			boolean isImagePublic) throws SQLException {
		String sql1 = SQLResources.getSQLResource("update.user.info.image.id");
		String sql2 = SQLResources.getSQLResource("insert.user.info.image.id");
		Connection conn = null;
		PreparedStatement pps1 = null;
		PreparedStatement pps2 = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql1);
			if (imageId != null && imageId.length() > 0) {
				pps1.setString(1, imageId);
				pps1.setInt(2, isImagePublic ? 1 : 0);
			} else {
				pps1.setNull(1, Types.VARCHAR);
				pps1.setNull(2, Types.INTEGER);
			}
			pps1.setLong(3, userId);
			int rows = pps1.executeUpdate();
			//if the record does not exist, insert it.
			if (rows == 0) {
				pps2 = conn.prepareStatement(sql2);
				pps2.setLong(1, userId);
				if (imageId != null && imageId.length() > 0) {
					pps2.setString(2, imageId);
					pps2.setInt(3, isImagePublic ? 1 : 0);
				} else {
					pps2.setNull(2, Types.VARCHAR);
					pps2.setNull(3, Types.INTEGER);
				}
				rows = pps2.executeUpdate();
			}
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
				if (pps2 != null) {
					pps2.close();
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
}