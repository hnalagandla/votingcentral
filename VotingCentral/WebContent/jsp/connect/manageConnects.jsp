<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.manageConnectsForm.action = "<%=fullVotingCentralUrl%>" + "/p/connectInvite.do" + "?" + "action=" + method;
	document.manageConnectsForm.submit();	
	return true;
 }

</SCRIPT>
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Manage your Voting Central Connections.</div>
       	<div id="bulletBox">

			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="96%" class="darkgreybg">
			<tr><td id="greenTitle">Pending Connect Requests you Recieved.</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">				
			<logic:present name="manageConnectsForm" property="requestsRecievedPending" >
				<table border="0" cellpadding="2" cellspacing="1" width="100%">
					<logic:iterate id="friend" name="manageConnectsForm" property="requestsRecievedPending" type="com.votingcentral.pres.web.to.VCUserWTO">																
						<tr>
							<td bgcolor="#F2F2F2" style="padding:5px;width:80px;">	
							<%@ include file="/jsp/include/users/friendBadge.jsp"%>
							</td>
							<td bgcolor="#F2F2F2" style="padding:5px;">	
								<div><bean:write name="friend"  property="connectComments"/></div>
								<div>
								<a href="<bean:write name="friend"  property="connectAcceptUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btns/btn-Accept.gif" width="99" height="27" border="0"></a>&nbsp;&nbsp;
								<a href="<bean:write name="friend"  property="connectRejectUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btns/btn-Decline.gif" width="99" height="27" border="0"></a>
								</div>
							</td>																					
						</tr>								
					</logic:iterate>
				</table>
				<!--hr style="margin:5px;" -->								
				</logic:present>
			</td></tr>
			<logic:empty name="manageConnectsForm" property="requestsRecievedPending" >	
				<tr><td bgcolor="#F2F2F2" style="padding:5px;">You don't have any pending Connect Requests you recieved.</td></tr>
			</logic:empty>
			</table>
			</div>

			

			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="96%" class="darkgreybg">
			<tr><td id="greenTitle">Pending Connect Requests you Sent.</td></tr>	
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
			<%-- content start--%>
				<% int k = 1;%>
				<logic:notEmpty name="manageConnectsForm" property="requestsSentPending" >
				<div>
				<table cellpadding=0 cellspacing=0 border=0 width="100%">
				<tr>
				<logic:iterate id="friend" name="manageConnectsForm" property="requestsSentPending" type="com.votingcentral.pres.web.to.VCUserWTO">
						<%
							if (k%8 != 0) {
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
	
			<logic:empty name="manageConnectsForm" property="requestsSentPending" >	
				You don't have any pending Connect Requests you sent. Initiate connections with the fast growing VotingCentral community. 
				        Participate, Educate, Motivate, Connect and Collaborate on VotingCentral.com
			</logic:empty>
			
				</td>
			</tr>		
			</table>
			</div>
			

			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="96%" class="darkgreybg">
			<tr><td id="greenTitle">Friends.</td></tr>	
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
			<%-- content start--%>
				<% int j = 1;%>
				<logic:notEmpty name="manageConnectsForm" property="friends" >
				<div>
				<table cellpadding=0 cellspacing=0 border=0 width="100%">
				<tr>
				<logic:iterate id="friend" name="manageConnectsForm" property="friends" type="com.votingcentral.pres.web.to.VCUserWTO">	
						<%
							if (j%8 != 0) {
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
	
						<%} j++; %>								
						    </logic:iterate>
						</tr>																
						</table>
						</div>						
				</logic:notEmpty>	
			<%-- content end --%>
	
			<logic:empty name="manageConnectsForm" property="friends" >	
				You don't have any Friends. Initiate connections with the fast growing VotingCentral community. 
				        Participate, Educate, Motivate, Connect and Collaborate on VotingCentral.com
			</logic:empty>
			
				</td>
			</tr>		
			</table>
			</div>


						
		</div>							
		</td>									
	</tr>
</table>
</div>