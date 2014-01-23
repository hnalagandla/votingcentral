<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<html:html>
<head>
<title></title>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.showPollFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/previewPoll.do" + "?" + "action=" + method;
	document.showPollFormBean.submit();	
	return true;
 }
</SCRIPT>
</head>
<body>
<html:form action="/p/previewPoll">	
<%@ include file="/jsp/include/errorMessages.jsp"%>
<CENTER>
<!-- H4><bean:message key="create.poll.preview.heading" /></H4 -->
<%@ include file="/jsp/include/polls/showPoll.jsp"%>			
	<TABLE cellpadding="0" cellspacing="0" width="100%" border="0">
	<TBODY>
			<TR>
				<TD><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"></TD>
			</TR>
		 <TR>
				<TD align="right">
				<html:button property="submitButton" value="Submit Poll" onclick="submitForm('submitPoll');" styleClass="btn"/>&nbsp;&nbsp;
				<html:button property="editButton" value="Edit Poll" onclick="submitForm('editPoll');" styleClass="btn"/>
					</TD>					
			</TR>

	</TBODY>
	</TABLE>
	</CENTER>
</html:form>
</body>
</html:html>
