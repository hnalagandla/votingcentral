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
<html:form action="/mostEmailedPolls">
    
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle"><bean:message key="most.emailed.polls.heading"/></div>
       	<div id="bulletBox">
		<!-- Content Start -->
				<logic:present name="mostEmailedPollsFormBean" property="mostEmailedPolls" >											
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<logic:iterate id="pto" name="mostEmailedPollsFormBean" property="mostEmailedPolls" type="com.votingcentral.pres.web.to.PollWTO">																					
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