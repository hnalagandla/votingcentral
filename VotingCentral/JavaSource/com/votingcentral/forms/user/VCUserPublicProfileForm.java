/*
 * Created on Oct 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.pres.web.to.VCUserWTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserPublicProfileForm extends VCBaseFormBean {

	private String userName;

	private List pollsByUser;

	private List friends;

	private VCUserWTO userWTO;

	private String currVacoPoints;

	private String allTimeVacoPoints;

	private String pollsMoreUrl;

	private String allFriendsUrl;

	private String editProfileUrl;

	private String editImageUrl;

	private boolean showEditLinks;

	/**
	 * @return Returns the pollsByUser.
	 */
	public List getPollsByUser() {
		return pollsByUser;
	}

	/**
	 * @param pollsByUser
	 *            The pollsByUser to set.
	 */
	public void setPollsByUser(List pollsByUser) {
		this.pollsByUser = pollsByUser;
	}

	/**
	 * @return Returns the friends.
	 */
	public List getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            The friends to set.
	 */
	public void setFriends(List friends) {
		this.friends = friends;
	}

	/**
	 * @return Returns the userWTO.
	 */
	public VCUserWTO getUserWTO() {
		return userWTO;
	}

	/**
	 * @param userWTO
	 *            The userWTO to set.
	 */
	public void setUserWTO(VCUserWTO userInfoWTO) {
		this.userWTO = userInfoWTO;
	}

	/**
	 * @return Returns the allTimeVacoPoints.
	 */
	public String getAllTimeVacoPoints() {
		return allTimeVacoPoints;
	}

	/**
	 * @param allTimeVacoPoints
	 *            The allTimeVacoPoints to set.
	 */
	public void setAllTimeVacoPoints(String allTimeVacoPoints) {
		this.allTimeVacoPoints = allTimeVacoPoints;
	}

	/**
	 * @return Returns the currVacoPoints.
	 */
	public String getCurrVacoPoints() {
		return currVacoPoints;
	}

	/**
	 * @param currVacoPoints
	 *            The currVacoPoints to set.
	 */
	public void setCurrVacoPoints(String currVacoPoints) {
		this.currVacoPoints = currVacoPoints;
	}

	/**
	 * @return Returns the pollsMoreUrl.
	 */
	public String getPollsMoreUrl() {
		return pollsMoreUrl;
	}

	/**
	 * @param pollsMoreUrl
	 *            The pollsMoreUrl to set.
	 */
	public void setPollsMoreUrl(String pollsMoreUrl) {
		this.pollsMoreUrl = pollsMoreUrl;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the allFriendsUrl.
	 */
	public String getAllFriendsUrl() {
		return allFriendsUrl;
	}

	/**
	 * @param allFriendsUrl
	 *            The allFriendsUrl to set.
	 */
	public void setAllFriendsUrl(String allFriendsUrl) {
		this.allFriendsUrl = allFriendsUrl;
	}

	/**
	 * @return Returns the editProfileUrl.
	 */
	public String getEditProfileUrl() {
		return editProfileUrl;
	}

	/**
	 * @param editProfileUrl
	 *            The editProfileUrl to set.
	 */
	public void setEditProfileUrl(String editProfileUrl) {
		this.editProfileUrl = editProfileUrl;
	}

	/**
	 * @return Returns the showEditLinks.
	 */
	public boolean isShowEditLinks() {
		return showEditLinks;
	}

	/**
	 * @param showEditLinks
	 *            The showEditLinks to set.
	 */
	public void setShowEditLinks(boolean showEditLinks) {
		this.showEditLinks = showEditLinks;
	}

	/**
	 * @return Returns the editImageUrl.
	 */
	public String getEditImageUrl() {
		return editImageUrl;
	}

	/**
	 * @param editImageUrl
	 *            The editImageUrl to set.
	 */
	public void setEditImageUrl(String editImageUrl) {
		this.editImageUrl = editImageUrl;
	}
}