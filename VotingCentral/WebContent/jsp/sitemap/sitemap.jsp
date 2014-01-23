<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="com.votingcentral.util.url.*, com.votingcentral.util.request.*"%>
	<%
		String domContext = VCRequestHelper.getDomainAndContext(request);
		String regUrl = VCURLHelper.getRegistrationUrl(domContext);
		String myVCUrl = VCURLHelper.getMyVCMainUrl(domContext);
		//String editAccountUrl = VCURLHelper.getEditAccountInfoUrl(domContext);
		String homeUrl = VCURLHelper.getHomePageUrl(domContext);
		String recEndedPollsUrl = VCURLHelper.getRecEndedPollsUrl(domContext);
		String mostVotedPollsUrl = VCURLHelper.getMostVotedPollsUrl(domContext);
		String createAPollUrl = VCURLHelper.getCreatePollUrl(domContext);
		String contestsUrl = VCURLHelper.getContestsMainUrl(domContext);
		String contestsUploadUrl = VCURLHelper.getContestsUploadUrl(domContext);
		String faqUrl = VCHelpPageURLHelper.getInstance().getFAQUrl(domContext);
		String contactUsUrl = VCHelpPageURLHelper.getInstance().getContactUsUrl(domContext);
		String aboutUsUrl = VCHelpPageURLHelper.getInstance().getAboutUsUrl(domContext);
		String forgotPasswdUrl = VCURLHelper.getForgotPasswordUrl(domContext);
		String searchUrl = VCURLHelper.getSearchUrl(domContext);
		String tAndCUrl = VCHelpPageURLHelper.getInstance().getTermsAndConditionsUrl(domContext);
		String privacyPolicyUrl = VCHelpPageURLHelper.getInstance().getPrivacyPolicyUrl(domContext);
		String rssUrl = VCURLHelper.getRSSUrl(domContext);
		String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
		boolean isFacebook = VCRequestHelper.isFacebook(request);
	 %>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>	 
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">VotingCentral SiteMap</div>
		
      	<div id="bulletBox">
		<!-- Content Start -->
		<div style="padding:10px 10px 10px 0px;">
			<div id="blueTitle2">General</div>
			<div id="bulletSquareBlue2"><a href="<%=homeUrl%>">Home</a></div>
			<div id="bulletSquareBlue2"><a href="<%=searchUrl%>">Search</a></div>
			<div id="bulletSquareBlue2"><a href="<%=tAndCUrl%>">Terms And Conditions</a></div>
			<div id="bulletSquareBlue2"><a href="<%=privacyPolicyUrl%>">Privacy Policy</a></div>			
			<div id="bulletSquareBlue2"><a href="<%=searchUrl%>">Search</a></div>			
		</div>		
		<div style="padding:10px 10px 10px 0px;">
			<div id="blueTitle2">Polls</div>
			<div id="bulletSquareBlue2"><a href="<%=createAPollUrl%>">Create a poll</a></div>
			<div id="bulletSquareBlue2"><a href="<%=mostVotedPollsUrl%>">Most voted polls</a></div>
			<div id="bulletSquareBlue2"><a href="<%=recEndedPollsUrl%>">Recently ended polls</a></div>
			<% if (!isFacebook) {%>
				<div id="bulletSquareBlue2"><a href="<%=rssUrl%>"><img src="<%=fullVotingCentralImgUrl%>/images/feed-icon-12x12-orange.gif" border="0" alt="RSS Feeds from Voting Central"></a>&nbsp;&nbsp;<a href="<%=rssUrl%>">RSS Feeds of Polls</a></div>			
			<% }%>	
		</div>
		
		<div style="padding:10px 10px 10px 0px;">
			<div id="blueTitle2">Contests</div>
			<div id="bulletSquareBlue2"><a href="<%=contestsUrl%>">Current Contests</a></div>
			<div id="bulletSquareBlue2"><a href="<%=contestsUploadUrl%>">Enter Contest</a></div>
		</div>
		
		<div style="padding:10px 10px 10px 0px;">
			<div id="blueTitle2">VotingCentral Account</div>
			<div id="bulletSquareBlue2"><a href="<%=regUrl%>">Join VotingCentral Community</a></div>
			<div id="bulletSquareBlue2"><a href="<%=forgotPasswdUrl%>">Forgot Password</a></div>
			<div id="bulletSquareBlue2"><a href="<%=myVCUrl%>">My VotingCentral</a></div>
			<%-- <div id="bulletSquareBlue2"><a href="<%=editAccountUrl%>">Edit Account Info</a></div>			--%>
		</div>
		<!-- Content End -->
		</div>	
		</td>									
	</tr>
</table>
</div>
