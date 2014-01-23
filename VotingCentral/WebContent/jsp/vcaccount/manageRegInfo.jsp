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

	document.regUserFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/myVCAccount.do" + "?" + "action=" + method;
	document.regUserFormBean.submit();	
	return true;
 }

 function onCountryChange() {
	submitForm('getStates');	
 }
</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/myVCAccount">
<table width="100%"  border="0" cellspacing="0" cellpadding="1">
  <tr>
    <td class="lightbluebg">
      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td bgcolor="#FFFFFF">
		<table width="100%"  border="0" cellspacing="1" cellpadding="2">
                  <tr>
                    <td colspan="2" class="blueTitle1"><bean:message key="edit.user.heading" /></td>
                  </tr>
                  <tr>
                    <td colspan="2" align="left" class="greyText"><i><bean:message key="create.poll.req.fields" /></i><br/><br/></td>
                  </tr>
			<TR>
				<TD align="right" width="30%" class="txtLabel"><bean:message key="edit.user.prompt.username" /></TD>
				<TD align="left"><html:text property="userName" disabled="true"/>&nbsp;&nbsp;<bean:message
					key="edit.user.prompt.username.suggest" /></TD>
			</TR>                  
			<TR>
				<TD align="right" class="txtLabel">&nbsp;<bean:message key="edit.user.prompt.email" /></TD>
				<TD align="left"><html:text property="emailAddress" disabled="true"/>&nbsp;&nbsp;<bean:message
					key="edit.user.prompt.email.suggest" /></TD>
			</TR>
			<%--
			<TR>
				<TD align="right" class="txtLabel"><bean:message
					key="edit.user.prompt.sec.question" /></TD>
				<TD align="left"><html:select
					property="securityQuestion">
					<bean:define id="secQuestions" name="regUserFormBean" property="listOfSecurityQuestions" type="java.util.Collection"/>					
					<html:options collection="secQuestions" property="value"
						labelProperty="label" />
				</html:select></TD>
			</TR> 
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="edit.user.prompt.sec.answer" /></TD>
				<TD align="left"><html:text
					property="securityAnswer" />&nbsp;&nbsp;<bean:message
					key="edit.user.prompt.sec.answer.suggest" /></TD>
			</TR>
			--%>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.firstname" /></TD>
				<TD align="left"><html:text
					property="firstName" /></TD>
			</TR>
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.mi" /></TD>
				<TD align="left"><html:text
					property="middleInitial" /></TD>
			</TR>
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.lastname" /></TD>
				<TD align="left"><html:text
					property="lastName" /></TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.address1" /></TD>
				<TD align="left"><html:text
					property="mailingAddress1" /></TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.address2" /></TD>
				<TD align="left"><html:text
					property="mailingAddress2" /></TD>
			</TR>						
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.city" /></TD>
				<TD align="left"><html:text property="city" /></TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.state" /></TD>
				<bean:define id="states" name="regUserFormBean" property="statesForCountry" type="java.util.Collection"/>				
				<TD align="left"><html:select
					property="stateId">
					<html:options collection="states" property="value"
						labelProperty="label" />
				</html:select></TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.zip" /></TD>
				<TD align="left"><html:text
					property="zipCode1" />-<html:text property="zipCode2" /></TD>
			</TR>
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="reg.newuser.prompt.country" /></TD>
				<bean:define id="countries" name="regUserFormBean" property="listOfCountries" type="java.util.Collection"/>								
				<TD align="left"><html:select
					property="countryId"  onchange="onCountryChange();">
					<html:options collection="countries" property="value"
						labelProperty="label" />
				</html:select></TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="edit.user.prompt.gender" /></TD>
				<TD align="left">
				<table cellpadding="0" cellspacing="0" border="0">
				<tr>
				<td><html:radio property="gender" value="M" /></td>
				<td>&nbsp;<bean:message key="edit.user.prompt.gender.male" />&nbsp;&nbsp;&nbsp;</td>
				<td><html:radio property="gender" value="F" /></td>
				<td>&nbsp;<bean:message key="edit.user.prompt.gender.female" /></td>
				</tr>
				</table>
				</TD>
			</TR>			
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="edit.user.prompt.birth.year" /></TD>
				<TD align="left"><html:text
					property="birthYear" /></TD>
			</TR>
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="edit.user.prompt.birth.month" /></TD>
				<TD align="left"><html:text
					property="birthMonth" /></TD>
			</TR>
			<TR>
				<TD align="right" class="txtLabel"><bean:message key="edit.user.prompt.birth.day" /></TD>
				<TD align="left"><html:text
					property="birthDay" /></TD>
			</TR>			
			<TR>				
				<TD></TD>
				<TD><html:button property="submitButton" value="Done"
					onclick="submitForm('done'); this.disabled=true;" styleClass="btn"/>&nbsp;<html:button property="submitButton" value="Submit" onclick="submitForm('editAccount'); this.disabled=true;"  styleClass="btn"/></TD>					
			</TR>			
		</table>
          </td>
        </tr>
    </table>
    </td>
  </tr>
</table>					
</html:form>

