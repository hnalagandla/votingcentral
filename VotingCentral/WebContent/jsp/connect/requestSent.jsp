<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.requestConnectForm.action = "<%=fullVotingCentralUrl%>" + "/p/connectInvite.do" + "?" + "action=" + method;
	document.requestConnectForm.submit();	
	return true;
 }

</SCRIPT>
<div style="padding:0px 0px 0px 3px"> 
<%@ include file="/jsp/include/errorMessages.jsp"%>
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Invitation To Connect Sent Successfully.</div>
       	<div id="bulletBox">

			<%-- content start--%>
			<html:form action="/p/connectInvite">	
			<div>Invitation To Connect Sent Successfully.</div>		
			</html:form>
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>
