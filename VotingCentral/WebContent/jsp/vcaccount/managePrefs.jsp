<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
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
	document.managePrefsForm.action = "<%=fullVotingCentralUrl%>" + "/p/user/managePrefs.do" + "?" + "action=" + method;
	document.managePrefsForm.submit();	
	return true;
 }

</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/user/managePrefs" enctype="multipart/form-data" method="post">
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Manage your Preferences</div>		
      	<div id="bulletNone" style="padding-top:10px;padding-bottom:10px;">
		<!-- Content Start -->
		<table cellpadding=2 cellspacing=1 border=0>
		<tr>
			<td align="right" class="txtLabel">Receive Daily Digest of New Poll(s) via Email ?</td>			
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="pollStartedEmail" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;Yes&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="pollStartedEmail" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;No&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>				
			<td colspan=2 style="padding-top:10px;">
			<!-- html:button property="viewProfileUrl" value="View Profile" onclick="javascript:viewProfile();" styleClass="btn"/>&nbsp;&nbsp; -->
			<html:button property="update" value="Save" onclick="submitForm('update'); this.disabled=true;" styleClass="btn"/>
			</td>
		</table>
		<!-- Content End -->
		</div>			
		</td>									
	</tr>
</table>
</div>
</html:form>

