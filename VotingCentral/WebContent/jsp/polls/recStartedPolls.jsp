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
<script type="text/javascript" language="JavaScript"
	src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<script type="text/javascript" language="JavaScript">run_after_body();</script>	
    <html:form action="/recStartedPolls">
    
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle"><bean:message key="rec.started.polls.heading"/></div>
       	<div id="bulletBox">
		<!-- Content Start -->
				<logic:present name="recStartedPollsFormBean" property="recStartedPolls" >											
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<logic:iterate id="pto" name="recStartedPollsFormBean" property="recStartedPolls" type="com.votingcentral.pres.web.to.PollWTO">																					
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