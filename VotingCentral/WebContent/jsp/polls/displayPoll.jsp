<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
<SCRIPT language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/utils.js"></script>
<SCRIPT language="JavaScript">

 function submitForm(action, method) {
	document.showPollFormBean.action = "<%=fullVotingCentralUrl%>" + action + ".do" + "?" + "action=" + method;
	document.showPollFormBean.submit();	
	return true;
 }

</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/displayPoll">
<%-- <jsp:include page="/jsp/include/polls/showPoll.jsp"/> --%>
<%@ include file="/jsp/include/polls/showPoll.jsp"%>	
</html:form>