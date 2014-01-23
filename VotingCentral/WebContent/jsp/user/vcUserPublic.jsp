<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
    <html:form action="/vcUserPublic">
	<bean:define id="user" name="vcUserPPForm" property="userName" type="java.lang.String"/>		    
	<bean:define id="showEditLinks" name="vcUserPPForm" property="showEditLinks" type="java.lang.Boolean"/>	
    <div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="top">
		<div class="lightbluebgBox" style="width:100%;">
			<div class="blueTitle1">
				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="blueTitle1" style="color:#000;border-bottom:0px;"><%=user%></td>
						<%
							if (showEditLinks.booleanValue()) {
						%>
						<td align="right"><a href="<bean:write name="vcUserPPForm" property="editImageUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btnEdit.gif" alt="Edit" width="16" height="16" border="0" style="padding:0px 5px 5px 5px;"></a></td>
						<%
							}
						 %>
					</tr>
				</table>
			</div>
		<bean:define id="userWTO" name="vcUserPPForm" property="userWTO" type="com.votingcentral.pres.web.to.VCUserWTO"/>
			<div>
				<table border="0" cellpadding="2" cellspacing="0" width="100%">
					<tr>
						<td colspan="2" align="center" valign="middle">
						<div style="text-align:center;width:200px;height:150px;border:solid 1px #000;">
								<logic:notEmpty name="vcUserPPForm"  property="userWTO.maxImageUrl"> 
									<img src="<bean:write name="vcUserPPForm" property="userWTO.maxImageUrl"/>" width="200" height="150">
								</logic:notEmpty>	
								<logic:empty name="vcUserPPForm"  property="userWTO.maxImageUrl"> 
									<bean:define id="gender" name="vcUserPPForm" property="userWTO.gender" type="java.lang.String"/>
									<% 
										if (gender.equalsIgnoreCase("M")) {
									%>
										<img src="<%=fullVotingCentralImgUrl%>/images/icon_male_100x100.gif" width="100" height="100">
									<%
										} else {
									 %>	
										<img src="<%=fullVotingCentralImgUrl%>/images/icon_female_100x100.gif" width="100" height="100">	
									<%
										}
									 %>									 
								</logic:empty>								
						</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<div>
							Member Since: 
							<span class="txtInfo" style="padding-bottom:10px;"><bean:write name="vcUserPPForm" property="userWTO.memberSince"/></span><br/>
							
							Current VC Points: 
							<span class="txtInfo" style="padding-bottom:10px;"><bean:write name="vcUserPPForm" property="currVacoPoints"/></span><br/>

							All Time VC Points: 
							<span class="txtInfo" style="padding-bottom:10px;"><bean:write name="vcUserPPForm" property="allTimeVacoPoints"/></span><br/>
							
							</div>
							
<div align="center" style="margin:5px;"><a href="<bean:write name="vcUserPPForm" property="userWTO.connectToUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btns/btn-Connect.gif" width="100" height="28" border="0"/></a></div>
						</td>
					</tr>
				</table>
			</div>
			<div></div>
		</div>

		<div class="lightbluebgBox" style="width:100%;">
			<div class="blueTitle1">Friends</div>		
			<%-- content start--%>
				<% int k = 1;%>
				<logic:notEmpty name="vcUserPPForm" property="friends">
				<div>
				<table cellpadding=2 cellspacing=1 border=0 width="100%">
				<tr>
				<logic:iterate id="friend" name="vcUserPPForm" property="friends" type="com.votingcentral.pres.web.to.VCUserWTO">
						<%
							if (k%3 != 0) {
					    %>
								<td align="center">	
								<%@ include file="/jsp/include/users/friendBadge.jsp"%>	
								</td>
						<% } else {
						 %>
							<td align="center">	
								<%@ include file="/jsp/include/users/friendBadge.jsp"%>	
								</td>
							</tr>
					 		<tr>
	
						<%} k++; %>								
						    </logic:iterate>
						</tr>																
						</table>
						</div>						
				</logic:notEmpty>	
			<%-- content end --%>

			<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="vcUserPPForm" property="allFriendsUrl"/>"><strong>More ></strong></a></div>
		</div>
		</td>
		<td width="60%" valign="top" style="padding-left:20px;padding-right:0px;">

		<div class="lightbluebgBox">
			<div class="blueTitle1">
				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="blueTitle1" style="border-bottom:0px;">User Information</td>
						<%
							if (showEditLinks.booleanValue()) {
						%>						
						<td align="right"><a href="<bean:write name="vcUserPPForm" property="editProfileUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btnEdit.gif" alt="Edit" width="16" height="16" border="0" style="padding:0px 5px 5px 5px;"></a></td>
						<%
							}
						 %>
					</tr>
				</table>
			</div>
				<div>
				<table border="0" cellpadding="2" cellspacing="0" width="96%" >			
				<logic:notEmpty name="userWTO" property="vcUserInfoTo">
					<bean:define id="vcUserInfoTo" name="userWTO" property="vcUserInfoTo" type="com.votingcentral.model.db.dao.to.VCUserInfoTO"/>					

						<logic:notEmpty name="userWTO" property="userInfoNameValues">
							<logic:iterate id="userInfoNameValues" name="userWTO" property="userInfoNameValues" type="com.votingcentral.model.db.dao.to.TextLinkDescTO">		
								<tr>
									<td width="30%" class="txtLabel" valign="top"><bean:write name="userInfoNameValues" property="desc" />:</td>
									<td valign="top" class="txtInfo"><bean:write name="userInfoNameValues" property="text" /></td>
								</tr>									 
							</logic:iterate>
						</logic:notEmpty>
						<logic:notEmpty name="userWTO"  property="vcUserInfoTo.aboutMe"> 
							<tr>
								<td width="30%" class="txtLabel" valign="top">About:</td>
								<td valign="top" class="txtInfo"><bean:write name="userWTO" property="vcUserInfoTo.aboutMe"/></td>
							</tr>
						</logic:notEmpty>
						<logic:notEmpty name="userWTO"  property="vcUserInfoTo.favQuote"> 
							<tr>
								<td valign="top" class="txtLabel">Fav Quote:</td>
								<td valign="top" class="txtInfo"><bean:write name="userWTO" property="vcUserInfoTo.favQuote"/></td>
							</tr>
						</logic:notEmpty>
						<logic:notEmpty name="userWTO"  property="vcUserInfoTo.favUrl"> 
							<tr>
								<td valign="top" class="txtLabel">Fav URL:</td>
								<td valign="top" class="txtInfo"><bean:write name="userWTO" property="vcUserInfoTo.favUrl"/></td>
							</tr>
						</logic:notEmpty>	
				</logic:notEmpty>										
				</table>
				</div>
		</div>
		
		<div class="lightbluebgBox">
			<div class="blueTitle1"><bean:message key="vc.user.public.profile.heading" arg0='<%=user%>'/></div>
				<div style="padding:0px 0px 10px 0px">  
				       			<div id="bulletBox" style="padding-left:0px;padding-top:5px;margin-bottom:5px">
				       				<table cellpadding="0" cellspacing="0" border="0" width="90%">								    
				       				<%--<% long imgX = 0; %> --%>
									<logic:iterate id="pto" name="vcUserPPForm" property="pollsByUser" type="com.votingcentral.pres.web.to.PollWTO">		
									<%--<%
										imgX++;
									 %> --%>
									<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>									 
									</logic:iterate>
									</table>	
				      			</div>
				      	<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="vcUserPPForm"  property="pollsMoreUrl"/>"><strong>More ></strong></a></div>
				</div>
			
		</div>

		</td>
	</tr>
	</table>
    </div>
    </html:form>