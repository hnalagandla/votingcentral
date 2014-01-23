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
<SCRIPT language="JavaScript">
 function vcHome() {
	document.showResultsFormBean.action="<%=fullVotingCentralUrl%>/home.do";
	document.showResultsFormBean.submit();
 }
 function showNextPoll(method){
	document.showResultsFormBean.action="<%=fullVotingCentralUrl%>/p/displayPollResults.do" + "?" + "action=" + method;;
	document.showResultsFormBean.submit();
 }

 function submitForm(action, method) {
	document.showResultsFormBean.action = "<%=fullVotingCentralUrl%>" + action + ".do" + "?" + "action=" + method;
	document.showResultsFormBean.submit();	
	return true;
 }

</SCRIPT>	
<%@ include file="/jsp/include/errorMessages.jsp"%>
    <html:form action="/p/displayPollResults">
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
				<table width="100%"  border="0" cellspacing="1" cellpadding="2">
	              <tr>
	                <td colspan="2" class="blueTitle1">Poll Results</td>
	              </tr>
	              <tr>
	                <td colspan="2"><jsp:include page="/jsp/include/polls/showPollResults.jsp"/></td>
	              </tr>
	              <tr>
	                <td colspan="2">&nbsp;</td>
	              </tr>
				</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  		
</html:form>
