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

	document.adminFormBean.action = "<%=fullVotingCentralUrl%>" + "/admin/main.do" + "?" + "action=" + method;
	document.adminFormBean.submit();	
	return true;
 }
</SCRIPT>	
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/admin/main">
	<H4><bean:message key="admin.user.pick.choice" /></H4>
	<TABLE border="0" cellspacing="1" cellpadding="1">
		<TBODY>										
			<TR>				
				<TD><bean:message key="admin.main.prompt.choice.polls" /></TD>				
			</TR>
			<TR>				
				<TD>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Poll Id: <html:text property="pollId" size="36"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
			</TR>	
			<TR>
			 <TD>
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="edit" value="Edit" onclick="submitForm('editPoll'); this.disabled=true;" styleClass="btn"/></div>				 				
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="mFeatured" value="Make Featured" onclick="submitForm('makeFeatured'); this.disabled=true;" styleClass="btn"/></div>				 
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="rFeatured" value="Remove Featured" onclick="submitForm('removeFeatured'); this.disabled=true;" styleClass="btn"/></div>								
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="extend" value="Extend Poll" onclick="submitForm('extendPoll'); this.disabled=true;" styleClass="btn"/></div>
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="delete" value="Delete Poll" onclick="submitForm('deletePoll'); this.disabled=true;" styleClass="btn"/></div>
			 <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="twitter" value="Poll to Twitter" onclick="submitForm('pollToTwitter'); this.disabled=true;" styleClass="btn"/></div>
			 </TD>
		    </TR>	 
			<TR>
				<TD><bean:message key="admin.main.prompt.choice.user" /></TD>
			</TR>
			<TR>				
				<TD>User Name: <html:text property="userName"/></TD>				
			</TR>			
			<TR>							
			<td>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="assign" value="Assign Admin" onclick="submitForm('assignAdmin'); this.disabled=true;" styleClass="btn"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="revoke" value="Revoke Admin" onclick="submitForm('revokeAdmin'); this.disabled=true;" styleClass="btn"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="edit" value="Edit User" onclick="submitForm('editUser'); this.disabled=true;" styleClass="btn"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="confirm" value="Confirm User" onclick="submitForm('confirmUser'); this.disabled=true;" styleClass="btn"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="resend" value="Resend Conf Code" onclick="submitForm('reConfirm'); this.disabled=true;" styleClass="btn"/>			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<html:button property="stopDailyEmail" value="Stop Daily Email" onclick="submitForm('stopDailyEmail'); this.disabled=true;" styleClass="btn"/>			
			</td>
			</TR>						
			
			<TR>				
				<TD>
				      Text To Twitter: <html:text property="textToTwitter"/>
				      <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:button property="textToTwitter" value="Text To Twitter" onclick="submitForm('textToTwitter'); this.disabled=true;" styleClass="btn"/></div>
				</TD>				
			</TR>	
			
		</TBODY>
	</TABLE>
</html:form>
