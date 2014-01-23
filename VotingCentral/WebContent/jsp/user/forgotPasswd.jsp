<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<script language="javascript" src="<%=fullVotingCentralUrl%>/scripts/email.js"></script>	
<SCRIPT language="JavaScript">
 function submitForm(action) {
 		document.forms[1].action = "<%=fullVotingCentralUrl%>/remindPasswd.do?action="+action;
		document.forms[1].submit();
 }
</SCRIPT>	
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form  action="/remindPasswd">

	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1"><bean:message key="forgot.password.heading" /></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2"><bean:message key="forgot.password.instruction" /></td>
              </tr>
			  <tr>
                <td colspan="2" align="left"><bean:message key="reg.newuser.heading.req.fields" /></td>
              </tr>              
              <tr>
                <td><bean:message key="reg.newuser.prompt.username" /></td> 
                <td><html:text name="passwdReminderForm" property="userName" /></td>
              </tr>
              <tr>
                <td><bean:message key="reg.newuser.prompt.birth.year" /> </td>
                <td><html:text name="passwdReminderForm" property="birthYear" /></td>
              </tr>              
              <tr>
                <td><bean:message key="reg.newuser.prompt.email" /> </td>
                <td><html:text name="passwdReminderForm" property="emailAddress" /></td>
              </tr>                            
            <%--  <tr>
                <td><bean:message key="reg.newuser.prompt.zip" /> </td>
                <td><html:text name="passwdReminderForm" property="zipCode" /></td>
              </tr>              --%>
				<tr><td colspan="2" align="center" ><html:button property="submitButton" value="Send Temp Password" onclick="submitForm('sendTempPswd'); this.disabled=true;" styleClass="btn"/></td>			              
              <tr>
                <td>&nbsp;</td>
              </tr>
			</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>

</html:form>
