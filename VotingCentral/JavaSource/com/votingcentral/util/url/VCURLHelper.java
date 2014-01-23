/*
 * Created on Aug 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.url;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.votingcentral.cookies.VCCookie;
import com.votingcentral.cookies.VCCookieConstants;
import com.votingcentral.cookies.VCCookieHandler;
import com.votingcentral.cookies.VCCookielet;
import com.votingcentral.cookies.VCCookieletEnum;
import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.enums.VCChartPerspectiveEnum;
import com.votingcentral.model.enums.VCChartSizeEnum;
import com.votingcentral.model.enums.VCChartTypeEnum;
import com.votingcentral.model.enums.VCDownloadFileTypeEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCURLHelper {
	public static final String protectedPage = "/p/";

	public static final String adminPage = "/admin/";

	public static String URL_DESC_FOR_GRAPH_PIE = "Pie";

	public static String URL_DESC_FOR_GRAPH_ST = "Bar";

	public static String URL_DESC_FOR_GRAPH_BY_AGE = "By Age";

	public static String URL_DESC_FOR_GRAPH_BY_LOC = "By Location";

	public static String URL_DESC_FOR_GRAPH_BY_GENDER = "By Gender";

	public static String URL_DESC_FOR_GRAPH_BY_TIME = "Time Based Trends";

	public static List sUrls = null;

	private static VCURLHelper urlHelper = null;

	static {
		List secureUrls = new ArrayList();
		secureUrls.add("/login/loginUser." + PresentationConstants.WEB_EXTN);
		secureUrls.add("/regNewUser." + PresentationConstants.WEB_EXTN);
		secureUrls.add("/p/myVCAccount." + PresentationConstants.WEB_EXTN);
		secureUrls.add("/p/user/manageProfile."
				+ PresentationConstants.WEB_EXTN);
		secureUrls.add("/p/user/managePswd." + PresentationConstants.WEB_EXTN);
		secureUrls.add("/admin/main." + PresentationConstants.WEB_EXTN);
		sUrls = Collections.unmodifiableList(secureUrls);

	}

	public static boolean isSecureUrl(String url) {
		return sUrls.contains(url);
	}

	// checks if the url passed in starts with "https://" prefix and
	// removes it
	//attach the parameters back to create the full URL
	public static String prependHttpsPrefix(String url, Map params) {
		String prefixed = "";
		if (null != url
				&& url.trim().toLowerCase().startsWith(
						PresentationConstants.UN_SECURE_SCHEME)) {
			prefixed = StringUtils.replace(url.trim(),
					PresentationConstants.UN_SECURE_SCHEME,
					PresentationConstants.SECURE_SCHEME);
		} else {
			prefixed = url;
		}
		UnSyncStringBuffer buff = new UnSyncStringBuffer();
		buff.append(prefixed);
		buff.append(getRequestParamsAsString(params));
		return buff.toString();
	}

	public static String getRequestParamsAsString(Map params) {
		UnSyncStringBuffer buff = new UnSyncStringBuffer();
		buff.append(PresentationConstants.QUESTION_MARK);
		if (params != null && params.size() != 0) {
			for (Iterator itr = params.keySet().iterator(); itr.hasNext();) {
				String key = (String) itr.next();
				String[] values = (String[]) params.get(key);
				if (values != null && values.length > 0) {
					for (int i = 0; i < values.length; i++) {
						buff.append(PresentationConstants.AMPERSAND);
						buff.append(key);
						buff.append(PresentationConstants.EQUALS);
						buff.append(values[i]);
					}
				}
			}
		}
		return buff.toString();
	}

	public static String getDisplayImageUrl(String domainContext,
			String imageId, int width, int height) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		String fbContext = EnvProps.getProperty("facebook.apps.url");
		if (domainContext.indexOf(fbContext) != -1) {
			domainContext = EnvProps
					.getProperty("full.app.server.url.without.protocol");
		}
		url.append(domainContext).append("/ShowImage.").append(
				PresentationConstants.WEB_EXTN).append("?");
		url.append(PresentationConstants.AMPERSAND);
		url.append("imageId").append(PresentationConstants.EQUALS).append(
				imageId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("w").append(PresentationConstants.EQUALS).append(width);
		url.append(PresentationConstants.AMPERSAND);
		url.append("h").append(PresentationConstants.EQUALS).append(height);
		return url.toString();
	}

	public static String getVCTwitterUrl() {
		return "http://twitter.com/votingcentral";
	}

	public static String getTwitterUpdateUrl(String pollName, String bitlyUrl) {
		String status = "Check out " + pollName + " " + bitlyUrl;
		return "http://twitter.com/?status=" + FastURLEncoder.encode(status);
	}

	public static String getDisplayPollUrl(String domainContext, String pollId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/displayPoll.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		return url.toString();
	}

	public static String getDeletePollUrl(String domainContext, String pollId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/deletePoll.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		return url.toString();
	}

	public static String getExtendPollUrl(String domainContext, String pollId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/extendPoll.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append("action=extendPoll");
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		return url.toString();
	}

	public static String getEditPollUrl(String domainContext, String pollId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/editPoll.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append("action=editPoll");
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		return url.toString();
	}

	public static String getDisplayPollResultsUrl(String domainContext,
			String pollId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/displayPollResults.").append(
				PresentationConstants.WEB_EXTN).append("?");
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		return url.toString();
	}

	public static List getPollsByCategoriesURLs(String domainContext) {
		List pollsByCategoriesURLs = new ArrayList();
		try {
			List categoryTos = CategoryBO.getInstance()
					.getListOfSuperCategories(VCCategoryTypeEnum.POLL);
			for (Iterator itr = categoryTos.iterator(); itr.hasNext();) {
				CategoryTO cto = (CategoryTO) itr.next();
				String url = getPollsByCategoryUrl(domainContext, cto
						.getSuperCategory());
				List supercatPolls = PollBO.getInstance().getPollIdsByCategory(
						cto.getSuperCategory());
				int count = supercatPolls == null ? 0 : supercatPolls.size();
				TextLinkDescTO tldto = new TextLinkDescTO();
				tldto.setText(cto.getSuperCategory() + "(" + count + ")");
				tldto.setDesc(cto.getSuperCategory() + "(" + count + ")");
				tldto.setHref(url);
				pollsByCategoriesURLs.add(tldto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollsByCategoriesURLs;
	}

	public static List getManageMyInfoURLs(String domainContext) {
		List myInfoURLs = new ArrayList();

		String url = "";
		//don't append this, if being accessed in facebook.
		if (domainContext.indexOf(EnvProps.getProperty("facebook.apps.url")) == -1) {
			url = getMyRegInfoUrl(domainContext);
			TextLinkDescTO reg = new TextLinkDescTO();
			reg.setText("Reg. Info");
			reg.setDesc("Reg. Info");
			reg.setHref(url);
			myInfoURLs.add(reg);
		}

		url = getMyImageUrl(domainContext);
		TextLinkDescTO img = new TextLinkDescTO();
		img.setText("Image");
		img.setDesc("Image");
		img.setHref(url);
		myInfoURLs.add(img);

		url = getMyProfileUrl(domainContext);
		TextLinkDescTO prof = new TextLinkDescTO();
		prof.setText("Profile");
		prof.setDesc("Profile");
		prof.setHref(url);
		myInfoURLs.add(prof);

		//don't append this, if being accessed in facebook.
		if (domainContext.indexOf(EnvProps.getProperty("facebook.apps.url")) == -1) {
			url = getMyPasswordUrl(domainContext);
			TextLinkDescTO pswd = new TextLinkDescTO();
			pswd.setText("Password");
			pswd.setDesc("Password");
			pswd.setHref(url);
			myInfoURLs.add(pswd);
		}

		url = getMyPreferencesUrl(domainContext);
		TextLinkDescTO prefs = new TextLinkDescTO();
		prefs.setText("Preferences");
		prefs.setDesc("Preferences");
		prefs.setHref(url);
		myInfoURLs.add(prefs);

		url = getMyFriendsUrl(domainContext);
		TextLinkDescTO friends = new TextLinkDescTO();
		friends.setText("Friends");
		friends.setDesc("Friends");
		friends.setHref(url);
		myInfoURLs.add(friends);

		return myInfoURLs;
	}

	public static String getPollsByCategoryUrl(String domainContext,
			String superCategory) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/pollsByCat.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append("categoryName").append(PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(superCategory));
		return url.toString();
	}

	public static String getHomePageUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/home.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getInsuffcientAccessUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/403.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getLogoutUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/logout.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getLoginUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		if (EnvProps.isProd()) {
			appendSecureScheme(url);
		} else {
			appendUnSecureScheme(url);
		}
		url.append(domainContext).append("/login/loginUser.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getRegistrationUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/regNewUser.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getResendConfCodeUrl(String domainContext,
			String userName) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/regNewUser/resendConfCode.").append(
				PresentationConstants.WEB_EXTN).append("?action=reConfirm");
		if (userName != null && userName.length() > 0) {
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.USER_NAME);
			url.append(PresentationConstants.EQUALS);
			url.append(userName);
		}
		return url.toString();
	}

	public static String getForgotPasswordUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/forgotPasswd.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getSiteMapUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/sitemap.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/contests/uploadByType.do
	 * 
	 * @param pollId
	 * @return
	 */
	public static String getContestsUploadUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/contests/uploadByType.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/contests/uploadByType.do
	 * 
	 * @param pollId
	 * @return
	 */
	public static String getContestsUploadByTypeUrl(String domainContext,
			String contestType) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/contests/uploadByType.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append("contestType");
		url.append(PresentationConstants.EQUALS);
		url.append(FastURLEncoder.encode(contestType));
		return url.toString();
	}

	/**
	 * /p/contests.do?action=mainView
	 * 
	 * @return
	 */
	public static String getContestsMainUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/contests/main.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getRecEndedPollsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/recEndedPolls.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getRecStartedPollsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/recStartedPolls.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getUserRegConfCodeUrlInEmail(String domainContext,
			String userName, String code) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/regNewUser/e/confAcct.").append(
				PresentationConstants.WEB_EXTN).append("?action=checkCode")
				.append(PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.USER_NAME).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(userName)).append(
						PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.CONFIRMATION_CODE).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(code));
		return url.toString();
	}

	public static String getUserRegConfCodeUrlInWeb(String domainContext,
			String userName, String code) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/regNewUser/w/confAcct.").append(
				PresentationConstants.WEB_EXTN).append("?action=confirmUser")
				.append(PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.USER_NAME).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(userName)).append(
						PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.CONFIRMATION_CODE).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(code));
		return url.toString();
	}

	/**
	 * /p/myVC.do?action=mainView
	 * 
	 * @return
	 */
	public static String getMyVCMainUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/myVC.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/manageProfile.do
	 * 
	 * @return
	 */
	public static String getMyProfileUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/p/user/manageProfile.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/manageImage.do
	 * 
	 * @return
	 */
	public static String getMyImageUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/user/manageImage.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/managePswd.do
	 * 
	 * @return
	 */
	public static String getMyPasswordUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/p/user/managePswd.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/manageConnects.do
	 * 
	 * @return
	 */
	public static String getMyFriendsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/user/manageConnects.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/managePrefs.do
	 * 
	 * @return
	 */
	public static String getMyPreferencesUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/p/user/managePrefs.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/user/managePrefs.do
	 * 
	 * @return
	 */
	public static String getTurnOffDailyEmailUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/p/user/managePrefs.").append(
				PresentationConstants.WEB_EXTN).append(
				PresentationConstants.QUESTION_MARK).append("action").append(
				PresentationConstants.EQUALS).append("turnOffDailyEmail");
		return url.toString();
	}

	/**
	 * /p/user/allConnects.do
	 * 
	 * @return
	 */
	public static String getAllConnectionsByUserUrl(String domainContext,
			String userName) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/allConnects.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append(RequestParameterObjects.USER_NAME);
		url.append(PresentationConstants.EQUALS);
		url.append(userName);
		return url.toString();
	}

	/**
	 * /allPollsCreated.do?userName=harishn
	 * 
	 * @return
	 */
	public static String getAllPollsCreatedByUserUrl(String domainContext,
			String userName) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/allPollsCreated.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append(RequestParameterObjects.USER_NAME);
		url.append(PresentationConstants.EQUALS);
		url.append(userName);
		return url.toString();
	}

	/**
	 * /p/myVCAccount?action=showAccountInfo
	 * 
	 * @return
	 */
	public static String getMyRegInfoUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendSecureScheme(url);
		url.append(domainContext).append("/p/myVCAccount.").append(
				PresentationConstants.WEB_EXTN).append(
				"?action=showAccountInfo");
		return url.toString();
	}

	public static String getMostVotedPollsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/mostVotedPolls.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getMostEmailedPollsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/mostEmailedPolls.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getActiveUsersUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/activeUsers.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getRecentUsersUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/recentUsers.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	/**
	 * /p/createPoll.do?action=createPoll
	 * 
	 * @return
	 */
	public static String getCreatePollUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/createPoll.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	// This is the actual URL that renders the GIF.
	public static String getURLForGraphImage(String domainContext,
			VCChartTypeEnum ct, VCChartPerspectiveEnum cp, VCChartSizeEnum cs,
			String pollId, String questionId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/showChart.").append(
				PresentationConstants.WEB_EXTN).append("?action=show");
		url.append(PresentationConstants.AMPERSAND);
		url.append("cct").append(PresentationConstants.EQUALS).append(
				ct.getName());
		url.append(PresentationConstants.AMPERSAND);
		url.append("ccp").append(PresentationConstants.EQUALS).append(
				cp.getName());
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionId").append(PresentationConstants.EQUALS).append(
				questionId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("chartSize");
		if (cs == VCChartSizeEnum.BIG) {
			url.append(PresentationConstants.EQUALS).append(
					VCChartSizeEnum.BIG.getName());
		} else {
			url.append(PresentationConstants.EQUALS).append(
					VCChartSizeEnum.SMALL.getName());
		}

		return url.toString();
	}

	private static String getUrlForGraph(String domainContext,
			VCChartTypeEnum ct, VCChartPerspectiveEnum cp) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/displayPollResults.").append(
				PresentationConstants.WEB_EXTN).append("?action=showResults");
		url.append(PresentationConstants.AMPERSAND);
		url.append("cct").append(PresentationConstants.EQUALS).append(
				ct.getName());
		// perspective can be null for Pie Chart.
		if (cp != null) {
			url.append(PresentationConstants.AMPERSAND);
			url.append("ccp").append(PresentationConstants.EQUALS).append(
					cp.getName());
		}
		return url.toString();
	}

	public static TextLinkDescTO getUrlAndDescForPieChart(String domainContext,
			String pollId, String questionId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		// appendUnSecureScheme(url);
		url.append(getUrlForGraph(domainContext, VCChartTypeEnum.PIE, null));
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionId").append(PresentationConstants.EQUALS).append(
				questionId);
		TextLinkDescTO to = new TextLinkDescTO();
		to.setHref(url.toString());
		to.setText(URL_DESC_FOR_GRAPH_PIE);
		return to;
	}

	public static TextLinkDescTO getUrlAndDescForBarGraphByPerspective(
			String domainContext, VCChartPerspectiveEnum persp, String pollId,
			String questionId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		// no need to append the schema here since each URL will have
		// the schema by itself.
		// appendUnSecureScheme(url);
		TextLinkDescTO to = new TextLinkDescTO();
		if (persp == VCChartPerspectiveEnum.ACX) {
			url.append(getUrlForGraph(domainContext, VCChartTypeEnum.BAR,
					VCChartPerspectiveEnum.ACX));
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.POLL_ID).append(
					PresentationConstants.EQUALS).append(pollId);
			url.append(PresentationConstants.AMPERSAND);
			url.append("questionId").append(PresentationConstants.EQUALS)
					.append(questionId);
			to.setText(URL_DESC_FOR_GRAPH_BY_AGE);
		} else if (persp == VCChartPerspectiveEnum.GCX) {
			url.append(getUrlForGraph(domainContext, VCChartTypeEnum.BAR,
					VCChartPerspectiveEnum.GCX));
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.POLL_ID).append(
					PresentationConstants.EQUALS).append(pollId);
			url.append(PresentationConstants.AMPERSAND);
			url.append("questionId").append(PresentationConstants.EQUALS)
					.append(questionId);
			to.setText(URL_DESC_FOR_GRAPH_BY_GENDER);
		} else if (persp == VCChartPerspectiveEnum.LCX) {
			url.append(getUrlForGraph(domainContext, VCChartTypeEnum.BAR,
					VCChartPerspectiveEnum.LCX));
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.POLL_ID).append(
					PresentationConstants.EQUALS).append(pollId);
			url.append(PresentationConstants.AMPERSAND);
			url.append("questionId").append(PresentationConstants.EQUALS)
					.append(questionId);
			to.setText(URL_DESC_FOR_GRAPH_BY_LOC);
		} else if (persp == VCChartPerspectiveEnum.TTX) {
			url.append(getUrlForGraph(domainContext, VCChartTypeEnum.BAR,
					VCChartPerspectiveEnum.TTX));
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.POLL_ID).append(
					PresentationConstants.EQUALS).append(pollId);
			url.append(PresentationConstants.AMPERSAND);
			url.append("questionId").append(PresentationConstants.EQUALS)
					.append(questionId);
			to.setText(URL_DESC_FOR_GRAPH_BY_TIME);
		} else if (persp == VCChartPerspectiveEnum.SIMPLE_TOTALS) {
			url.append(getUrlForGraph(domainContext, VCChartTypeEnum.BAR,
					VCChartPerspectiveEnum.SIMPLE_TOTALS));
			url.append(PresentationConstants.AMPERSAND);
			url.append(RequestParameterObjects.POLL_ID).append(
					PresentationConstants.EQUALS).append(pollId);
			url.append(PresentationConstants.AMPERSAND);
			url.append("questionId").append(PresentationConstants.EQUALS)
					.append(questionId);
			to.setText(URL_DESC_FOR_GRAPH_ST);
		}
		to.setHref(url.toString());
		return to;
	}

	public static String getDeleteAnswerImageUrl(String domainContext,
			String pollId, String questionId, String answerId,
			String answerImageId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/createPoll/addQuestions.").append(
				PresentationConstants.WEB_EXTN).append("?action=deleteAImage");
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionId").append(PresentationConstants.EQUALS).append(
				questionId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("answerIdDel").append(PresentationConstants.EQUALS).append(
				answerId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("answerImageDel").append(PresentationConstants.EQUALS)
				.append(answerImageId);
		return url.toString();
	}

	public static String getDeleteMyImageUrl(String domainContext,
			String imageId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/user/manageImage.").append(
				PresentationConstants.WEB_EXTN).append("?action=deleteImage");
		url.append(PresentationConstants.AMPERSAND);
		url.append("imageId").append(PresentationConstants.EQUALS).append(
				imageId);
		return url.toString();
	}

	public static String getDeleteQuestionImageUrl(String domainContext,
			String pollId, String questionId, String questionImageId) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/createPoll/addQuestions.").append(
				PresentationConstants.WEB_EXTN).append("?action=deleteQImage");
		url.append(PresentationConstants.AMPERSAND);
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionId").append(PresentationConstants.EQUALS).append(
				questionId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionImageId").append(PresentationConstants.EQUALS)
				.append(questionImageId);
		return url.toString();
	}

	public static String getVCUserPublicProfileUrl(String domainContext,
			String userName) {
		if (userName.equalsIgnoreCase(ContestsBO.CONTEST_CREATOR_USER)) {
			return getContestsMainUrl(domainContext);
		}
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/vcUser.").append(
				PresentationConstants.WEB_EXTN);
		url.append("?");
		url.append(RequestParameterObjects.USER_NAME).append(
				PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(userName));
		return url.toString();
	}

	/**
	 * Taf friend URL.
	 * 
	 * @param pollId
	 * @param questionId
	 * @param questionImageId
	 * @return
	 */
	public static String getTAFUrl(String domainContext, String pageUrl,
			String subject, TafTypeEnum type) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/taf.").append(
				PresentationConstants.WEB_EXTN);
		url.append("?action=showTaf");
		url.append(PresentationConstants.AMPERSAND);
		url.append("tafUrl").append(PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(pageUrl));
		url.append(PresentationConstants.AMPERSAND);
		url.append("subject").append(PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(subject));
		url.append(PresentationConstants.AMPERSAND);
		url.append("type").append(PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(String.valueOf(type.getId())));
		return url.toString();
	}

	public static String getSearchUrlForSearchString(String domainContext,
			String searchStr) {
		searchStr = searchStr.trim();
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(getSearchUrl(domainContext));
		url.append("?action=search");
		url.append(PresentationConstants.AMPERSAND);
		url.append("searchString").append(PresentationConstants.EQUALS).append(
				FastURLEncoder.encode(searchStr));
		return url.toString();
	}

	public static String getSearchUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/search.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	protected static void appendUnSecureScheme(UnSyncStringBuffer url) {
		if (!url.toString().startsWith(PresentationConstants.UN_SECURE_SCHEME)) {
			url.append(PresentationConstants.UN_SECURE_SCHEME);
		}
	}

	protected static void appendSecureScheme(UnSyncStringBuffer url) {
		if (!url.toString().startsWith(PresentationConstants.SECURE_SCHEME)) {
			url.append(PresentationConstants.SECURE_SCHEME);
		}
	}

	/**
	 * Check to make sure that the secure URLs are being indeed accessed via
	 * https.
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isSecureUrlSecure(HttpServletRequest request) {
		boolean flag = true;
		// fix this later for MultipartRequestWrapper cases.
		if (request.getRequestURL() == null) {
			return flag;
		}
		String url = request.getRequestURL().toString();
		String suburl = url.substring(url
				.indexOf(PresentationConstants.WEB_CONTEXT)
				+ PresentationConstants.WEB_CONTEXT.length());
		if (isSecureUrl(suburl) && !request.isSecure() && EnvProps.isProd()) {
			flag = false;
		}
		return flag;
	}

	public static boolean isAdminUrl(String url) {
		return url.indexOf(adminPage) != -1;
	}

	public static boolean isProtectedUrl(String url) {
		return url.indexOf(protectedPage) != -1;
	}

	public static boolean isRedirectToLoginRequired(HttpServletRequest request) {
		boolean flag = false;
		// fix this later for MultipartRequestWrapper cases.
		if (request.getRequestURL() == null) {
			return flag;
		}
		String url = request.getRequestURL().toString();
		String suburl = url.substring(url
				.indexOf(PresentationConstants.WEB_CONTEXT)
				+ PresentationConstants.WEB_CONTEXT.length());
		boolean isProtected = isProtectedUrl(suburl);
		boolean isAdmin = isAdminUrl(suburl);

		if (isProtected || isAdmin) {
			//if this is a protected page, check if the user is logged in.
			Cookie[] vcCookies = request.getCookies();
			if (vcCookies == null || vcCookies.length == 0) {
				// login is required and the user does not have login cookies
				// redirect them to login.
				flag = true;
			} else {
				VCCookie vcCookie = VCCookieHandler.getCookie(request);
				VCCookielet vcCookielet = vcCookie
						.getCookielet(VCCookieletEnum.LOGIN.getName());
				VCCookielet loginIp = vcCookie
						.getCookielet(VCCookieletEnum.LOGIN_IP.getName());
				if (vcCookielet == null || loginIp == null) {
					flag = true;
				} else {
					if ((vcCookielet.getExpiryTime().before(PollTimeHelper
							.getInstance().getCurrentDate()))
							|| (!vcCookielet
									.getValue()
									.equals(
											VCCookieConstants.LOGIN_SUCCESS_COOKIELET_VALUE))
							|| (!loginIp.getValue().equals(
									request.getRemoteAddr()))) {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	public static String getInviteToConnectVCUserUrl(String domainContext,
			String userName) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/connectInvite.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		//this should be same as declared in RequestToLinkForm
		url.append("connectToUserName");
		url.append(PresentationConstants.EQUALS);
		url.append(FastURLEncoder.encode(userName));
		return url.toString();

	}

	public static String getInviteToConnectNewUserUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/connectInvite.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();

	}

	public static String getManageConnectionsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/user/manageConnects.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();

	}

	public static String getVacoWinnersUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/vcWinners.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();

	}

	public static String getVCPointsLeadersUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/vcLeaders.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();

	}

	public static String getRSSUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/rss.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();

	}

	public static String getVCConnectAcceptedOrRejectedUrl(
			String domainContext, String linkCode) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/connect.").append(
				PresentationConstants.WEB_EXTN);
		url.append(PresentationConstants.QUESTION_MARK);
		url.append("code");
		url.append(PresentationConstants.EQUALS);
		url.append(FastURLEncoder.encode(linkCode));
		return url.toString();
	}

	/**
	 * 
	 * @param domainContext
	 * @param pollId
	 * @param fileType
	 * @return
	 */
	public static String getDownloadPollResultsUrl(String domainContext,
			String pollId, String questionId, VCDownloadFileTypeEnum fileType) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		appendUnSecureScheme(url);
		url.append(domainContext).append("/p/downloadPollResults.").append(
				PresentationConstants.WEB_EXTN).append("?");
		url.append(RequestParameterObjects.POLL_ID).append(
				PresentationConstants.EQUALS).append(pollId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("questionId").append(PresentationConstants.EQUALS).append(
				questionId);
		url.append(PresentationConstants.AMPERSAND);
		url.append("dfType").append(PresentationConstants.EQUALS).append(
				fileType.getName());
		return url.toString();
	}

	/**
	 * Derive the domain and context for batch processes.
	 * 
	 * @return
	 */
	public static String getDomainContext() {
		String domain = EnvProps.getProperty("app.server.url");
		String context = EnvProps.getProperty("web.context");

		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(domain);
		if (!EnvProps.isProd()) {
			url.append(PresentationConstants.COLON);
			url.append("8080");
		}
		url.append(PresentationConstants.SLASH);
		url.append(context);
		return url.toString();
	}

	/**
	 * @param displayPollUrl
	 * @return
	 */
	public static String getYahooBuzzUpUrl(String displayPollUrl) {
		return "http://buzz.yahoo.com/buzz?targetUrl="
				+ FastURLEncoder.encode(displayPollUrl);
	}

	/**
	 * @param displayPollUrl
	 * @return
	 */
	public static String getFacebookShareUrl(String displayPollUrl,
			String pollName) {
		return "http://www.facebook.com/sharer.php?u="
				+ FastURLEncoder.encode(displayPollUrl) + "&t="
				+ FastURLEncoder.encode(pollName);
	}
}