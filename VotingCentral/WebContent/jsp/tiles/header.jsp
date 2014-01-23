<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@page language="java"
	import="com.votingcentral.util.session.*, com.votingcentral.util.url.*, com.votingcentral.util.request.*"%>
	
	<%
		String domContext = VCRequestHelper.getDomainAndContext(request);
		String regUrl = VCURLHelper.getRegistrationUrl(domContext);
		String myVCUrl = VCURLHelper.getMyVCMainUrl(domContext);
		String homeUrl = VCURLHelper.getHomePageUrl(domContext);
		String recEndedPollsUrl = VCURLHelper.getRecEndedPollsUrl(domContext);
		String mostVotedPollsUrl = VCURLHelper.getMostVotedPollsUrl(domContext);
		String createAPollUrl = VCURLHelper.getCreatePollUrl(domContext);
		String contestsUrl = VCURLHelper.getContestsMainUrl(domContext);
		String siteMapUrl = VCURLHelper.getSiteMapUrl(domContext);

		String faqUrl = VCHelpPageURLHelper.getInstance().getFAQUrl(domContext);
		String contactUsUrl = VCHelpPageURLHelper.getInstance().getContactUsUrl(domContext);
		String aboutUsUrl = VCHelpPageURLHelper.getInstance().getAboutUsUrl(domContext);
		String prizesFaqUrl = VCHelpPageURLHelper.getInstance().getPrizesFAQUrl(domContext);
        String user = VCRequestHelper.getUser(request);  
		String rssUrl = VCURLHelper.getRSSUrl(domContext);  
		String loginUrl = VCURLHelper.getLoginUrl(domContext);   
		String winnersUrl = VCURLHelper.getVacoWinnersUrl(domContext);   
		String vcTwitterUrl = VCURLHelper.getVCTwitterUrl();   
        String pointsLeadersUrl = VCURLHelper.getVCPointsLeadersUrl(domContext);
		String fullVotingCentralUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
		String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
		boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);    
	 %>
	 
    <tr>
        <td>
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				          <tr>
				            <td width="40%" align="left" valign="top"><a href="<%=homeUrl%>"><img src="<%=fullVotingCentralImgUrl%>/images/vc-logo-beta.gif" width="279" height="91" hspace="5" vspace="0" border="0" style="padding-bottom:10px;" alt="Voting Central"></a></td>
				            <td width="20%" align="right" valign="top"><a href="<%=vcTwitterUrl%>"><img src="<%=fullVotingCentralImgUrl%>/images/vc-on-twitter.gif" hspace="5" vspace="0" border="0" style="padding-bottom:10px;" alt="Follow VotingCentral on Twitter"></a></td>
				            <td align="right" valign="top">
				            	<table  border="0" cellspacing="1" cellpadding="2">
				              		<tr>
				                		<td><a class="boldBlueLinks" href="<%=siteMapUrl%>">Site Map</a>|<a class="boldBlueLinks" href="<%=faqUrl%>">FAQ</a>|<a class="boldBlueLinks" href="<%=contactUsUrl%>">Contact Us</a>|<a class="boldBlueLinks" href="<%=aboutUsUrl%>">About</a>                
								              <% 
												if (!isFacebook) {
													if (!user.equalsIgnoreCase("guest")) {
								              %>
								              			|&nbsp; Hi <%=user%>&nbsp;<a class="boldBlueLinks" href="<%=fullVotingCentralUrl%>/logout.do">Logout</a>&nbsp;
								              <%
													} else {
											  %>
											  			|<a class="boldBlueLinks" href="<%=regUrl%>">Join VotingCentral</a>|<a class="boldBlueLinks" href="<%=loginUrl%>">Login</a>
											  <%
													}
												}
											   %>
				               			</td>
				              		</tr>
				              		<tr>
				              			<td><br/>
					            			<table  border="0" cellspacing="0" cellpadding="0">
					              				<tr>
					              					<form name="advSearchFormBean" method="post" action="<%=fullVotingCentralUrl%>/search.do?action=search">
					                					<td>Search:&nbsp;</td>
					                					<td><input type="text" name="searchString" maxlength="2048" size="15" value="">&nbsp;</td>
					                					<td><input type="image" src="<%=fullVotingCentralImgUrl%>/images/btnGo.gif" width="27" height="18" border="0"  value="Search" style="border:0px;"></td>
					                				</form>						                					
					                			</tr>
					            			</table>
				              			</td>
				              		</tr>
				            	</table>
				            </td>
				          </tr>
				</table>
		</td>
      </tr>
      <tr>
        <td align="center" class="bluebg">
	        <div id="topmenubar" name="topmenubar">
	        &nbsp;<a href="<%=homeUrl%>">Home</a>&nbsp;| 
	        &nbsp;<a href="<%=createAPollUrl%>">Create Poll</a>&nbsp;| 
	        &nbsp;<a href="<%=myVCUrl%>">My VotingCentral</a>&nbsp;| 
	        &nbsp;<a href="<%=mostVotedPollsUrl%>">Most Voted</a>&nbsp;| 
	        &nbsp;<a href="<%=recEndedPollsUrl%>">Recently Ended</a>&nbsp;| 
	        &nbsp;<a href="<%=contestsUrl%>">Photo Contests</a>&nbsp;| 
	        &nbsp;<a href="<%=prizesFaqUrl%>" style="color:#E41B17">WIN Prizes!</a>&nbsp;| 
	        &nbsp;<a href="<%=winnersUrl%>" style="color:#E41B17">Winners!</a>&nbsp;|  	      
	        &nbsp;<a href="<%=pointsLeadersUrl%>">VCPoints Leaders</a>&nbsp;  
	        <% if (!isFacebook) {%>
	        	|&nbsp;&nbsp;<a href="<%=rssUrl%>">RSS</a><a href="<%=rssUrl%>">&nbsp;<img src="<%=fullVotingCentralImgUrl%>/images/feed-icon-12x12-orange.gif" alt="RSS Feeds from Voting Central" border="0"></a>&nbsp;&nbsp; 
	        <% } %>	
	        </div>
        </td>
      </tr>
