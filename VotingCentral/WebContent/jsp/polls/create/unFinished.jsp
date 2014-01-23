<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.processPollFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/createPoll.do" + "?" + "action=" + method;
	document.processPollFormBean.submit();	
	return true;
 }

</SCRIPT>	

	<CENTER>
<%@ include file="/jsp/include/errorMessages.jsp"%>	
    <html:form action="/p/createPoll">
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">You have unfinished polls</div>
       	<div id="bulletBox">
		<!-- Content Start -->
              <logic:present name="processPollFormBean" property="unFinishedPolls" >
              <div style="margin:10px 0px 30px 10px;background:#ECECEC;width:95%;">
              <table cellpadding="2" cellspacing="1" border="0" width="100%">
				<logic:iterate name="processPollFormBean" property="unFinishedPolls" id="ufPolls" type="com.votingcentral.pres.web.to.PollWTO">
					<bean:define id="deletePollUrl" name="ufPolls" property="deletePollUrl" type="java.lang.String"/>
					<tr>
						<td width="70%" style="padding-right:10px;background:#FFF;">
					<div id="bulletSquareBlue">
					<a href="<%=fullVotingCentralUrl%>/p/createPoll/pollBasics.do?action=pollBasics&pollId=<bean:write name="ufPolls" property="pollId" />"> 
					<bean:write name="ufPolls" property="pollName" /></a></div>
						</td>
						<td style="background:#FFF;" align="center"><div class="btn" style="width:60px;text-align:center;"><a href="<bean:write name="ufPolls" property="deletePollUrl"/>" style="color:#FFF;text-decoration:none;">Delete</a></div></td>
					</tr>
				</logic:iterate>
				</table>
				</div>
			</logic:present>
			<div>Click on a poll above to complete it &nbsp;&nbsp;<b>OR</b>&nbsp;&nbsp; <html:button property="createNewPoll" value="Create a New Poll" onclick="submitForm('createNewPoll'); this.disabled=true;" styleClass="btn"/></div>
		
		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div> 	
    </html:form>
	</CENTER>    

