<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">
 function submitForm() {
 		document.forms[1].action = "<%=fullVotingCentralUrl%>/login/loginUser.do?action=submit";
		document.forms[1].submit();
 }
</SCRIPT>	
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form  action="/login/loginUser">
<div style="padding:0px 0px 10px 0px;"> 
<html:hidden property="lsru" />
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td align="center" valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Please Sign In with your VotingCentral Username and Password</div>
       	<div id="bulletBox">
				  <table cellspacing="1" cellpadding="2" border="0">
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
				    <tr>
				      <td>Username:</td>
				      <td><input type="text" name="userName" size="20"></td>
				    </tr>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
				    <tr>
				      <td>Password:</td>
				      <td><input type="password" name="password" size="20"></td>
				    </tr>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
				    <tr>
				      <td>&nbsp;</td>
				      <td><html:button property="submitButton" value="Sign In" onclick="submitForm();" styleClass="btn"/></td>
				    </tr>
				    <tr>
				    <td>&nbsp;</td>
				    <td>
				    	<a href="<bean:write name="loginForm" property="forgotPswdUrl" />">Forgot Password?</a><br/>
				    	<a href="<bean:write name="loginForm" property="registrationUrl" />">Join VotingCentral</a>				    	
				    </td>
				  </table>
		</div>								
		</td>									
	</tr>
</table>
</div>
</html:form>