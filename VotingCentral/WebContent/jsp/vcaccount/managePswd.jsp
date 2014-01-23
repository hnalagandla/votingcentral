<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">
 function submitForm(method) {

	document.managePswdForm.action = "<%=fullVotingCentralUrl%>" + "/p/user/managePswd.do" + "?" + "action=" + method;
	document.managePswdForm.submit();	
	return true;
 }

</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/user/managePswd">
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Reset Your Password</div>
		<div id="bulletNone" style="padding-top:10px;padding-bottom:10px;">
			<%-- content start --%>	
	          <div>
	            <i><bean:message key="create.poll.req.fields" /></i><br/><br/>
	          </div>
	          <div>
				<table cellpadding=2 cellspacing=1 border=0>
				<tr>
					<td valign="top" style="padding-top:3px;" class="txtLabel"><bean:message key="edit.user.prompt.pswd" /></td>
					<td>
						<html:password property="password" /><br/>
						<bean:message key="edit.user.prompt.pswd.suggest" />
					</td>
				</tr>
				<tr>
					<td class="txtLabel"><bean:message key="reg.newuser.prompt.pswd.renter" /></td>
					<td><html:password property="renteredPassword" /></td>
				</tr>
				<tr>
					<td>
					
					</td>
					<td style="padding-top:10px;">
						<html:button property="submitButton" value="Change Password" onclick="submitForm('change'); this.disabled=true;"  styleClass="btn"/>
					</td>
				</tr>
				</table>
	          </div>
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>
</html:form>