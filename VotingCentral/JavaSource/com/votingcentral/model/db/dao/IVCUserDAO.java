package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserTO;

public interface IVCUserDAO {

	public VCUserTO getUserByUserName(String loginName) throws SQLException;

	public VCUserTO getUserByEmailAddress(String email) throws SQLException;

	public VCUserTO getUserById(long emailAddressId) throws SQLException;

	public boolean createUser(VCUserTO vto, PersonalConfigTO pto)
			throws SQLException;

	public boolean updateUser(VCUserTO vto, PersonalConfigTO pto)
			throws SQLException;

	public boolean updateUser(VCUserTO vto) throws SQLException;

	public void setAccountStatusByLoginName(String loginName, String status)
			throws SQLException;

	public List getAllPollStartedEmailableUsers() throws SQLException;

	public List getMostRecentlyJoinedUsers(int limit) throws SQLException;

}