<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
<html:form action="/allPollsCreated">
    
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<bean:define id="user" name="pollsCreatedForm" property="userName" type="java.lang.String"/>		
		<div id="blueTitle"><bean:message key="all.polls.created.by.a.user" arg0='<%=user %>'/></div>
       	<div id="bulletBox">
		<!-- Content Start -->
				<logic:present name="pollsCreatedForm" property="allPollsCreated" >											
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<logic:iterate id="pto" name="pollsCreatedForm" property="allPollsCreated" type="com.votingcentral.pres.web.to.PollWTO">																					
						<%@ include file="/jsp/include/polls/pollTeaser.jsp"%>				
				</logic:iterate>								
				</table>
				</logic:present>
		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div> 
    </html:form>