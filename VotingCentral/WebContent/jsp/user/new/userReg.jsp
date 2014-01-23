
<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>

<SCRIPT language="JavaScript">

 function submitForm(method) {
	document.forms[1].action = "<%=fullVotingCentralUrl%>" + "/regNewUser.do" + "?" + "action=" + method;
	document.forms[1].submit();	
	return true;
 }

 function onCountryChange() {
	submitForm('getStates');	
 }

</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/regNewUser" method="POST">
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1"><bean:message key="reg.newuser.heading" /></td>
              </tr>
                  <tr>
                    <td colspan="2" align="left"><bean:message key="reg.newuser.heading.req.fields" /></td>
                  </tr>
                  
<%-- commenting out the image check		
			<TR>
				<TD align="right"><bean:message
					key="reg.newuser.prompt.show.confirmcode" /></TD>
				<TD align="left">&nbsp;&nbsp;&nbsp;&nbsp;<img
					src="<c:url value="ConfirmCode.do">
                  					  <c:param name="GZIP_NOT_ALLOWED" value="Y"/>
                                   </c:url>" />&nbsp;&nbsp;</TD>
			</TR>

			<TR>
				<TD align="right"><bean:message
					key="reg.newuser.prompt.enter.confirmcode" /></TD>
				<TD align="left">&nbsp;&nbsp;&nbsp;&nbsp;<html:text
					property="confirmCode" size="12" maxlength="10" />&nbsp;&nbsp;</TD>
			</TR>
--%>		
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.username" /></TD>
				<TD align="left" valign="top"><html:text property="userName" /> <bean:message key="reg.newuser.prompt.username.suggest" /></TD>
			</TR>		
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.pswd" /></TD>
				<TD align="left" valign="top"><html:password property="password" /><bean:message key="reg.newuser.prompt.pswd.suggest" /></TD>
			</TR>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.pswd.renter" /></TD>
				<TD align="left" valign="top"><html:password property="renteredPassword" /></TD>
			</TR>		
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.email" /></TD>
				<TD width="60%" valign="top"><html:text property="emailAddress" /></TD>
			</TR>
			<%--			 
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.sec.question" /></TD>
				<TD align="left" valign="top"><html:select property="securityQuestion"><bean:define id="secQuestions" name="regUserFormBean" property="listOfSecurityQuestions" type="java.util.Collection"/><html:options collection="secQuestions" property="value" labelProperty="label" />
				</html:select></TD>
			</TR>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.sec.answer" /></TD>
				<TD align="left" valign="top"><html:text property="securityAnswer" /></TD>
			</TR>
			--%>			
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.birth.year" /></TD>
				<TD align="left" valign="top"><html:text property="birthYear" size="4"/>
				<%--&nbsp;&nbsp;
				<bean:message key="reg.newuser.prompt.birth.month" />
				<html:text property="birthMonth" size="2"/>&nbsp;&nbsp;<bean:message key="reg.newuser.prompt.birth.day" />&nbsp;<html:text property="birthDay" size="2"/>
				--%>
				</TD>
			</TR>
			<%--
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.birth.month" /></TD>
				<TD align="left" valign="top"><html:text property="birthMonth" size="2"/>&nbsp;&nbsp;<bean:message key="reg.newuser.prompt.birth.day" />&nbsp;<html:text property="birthDay" size="2"/></TD>
			</TR>
			--%>
<%--			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.birth.day" /></TD>
				<TD align="left" valign="top"><html:text property="birthDay" /></TD>
			</TR>
			--%>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.gender" /></TD>
				<TD align="left" valign="top"><html:radio property="gender" value="M" />
				&nbsp;&nbsp;<bean:message key="reg.newuser.prompt.gender.male" />&nbsp;&nbsp;
				<html:radio property="gender" value="F" />&nbsp;&nbsp;&nbsp;
				<bean:message key="reg.newuser.prompt.gender.female" />
				</TD>
			</TR>
			<bean:define id="showLocationInfoOnForm" name="regUserFormBean" property="showLocationInfoOnForm" type="java.lang.Boolean"/>	 
			<% 
				if (showLocationInfoOnForm.booleanValue()) {
			%>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.city" /></TD>
				<TD align="left" valign="top"><html:text property="city" /></TD>
			</TR>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.zip" /></TD>
				<TD align="left" valign="top"><html:text property="zipCode1" size="5"/>-<html:text property="zipCode2" size="4"/></TD>
			</TR>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.country" /></TD>
				<bean:define id="countries" name="regUserFormBean" property="listOfCountries" type="java.util.Collection"/>								
				<TD align="left" valign="top">
				<html:select property="countryId" onchange="onCountryChange();">
					<html:options collection="countries" property="value" labelProperty="label" />
				</html:select>
				</TD>
			</TR>
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.state" /></TD>
				<bean:define id="states" name="regUserFormBean" property="statesForCountry" type="java.util.Collection"/>				
				<TD align="left" valign="top">
				<html:select property="stateId">
					<html:options collection="states" property="value" labelProperty="label" />
				</html:select>
				</TD>
			</TR>
			<%
				}
			 %>
			 <%--			
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.firstname" /></TD>
				<TD align="left" valign="top"><html:text property="firstName" />&nbsp;&nbsp;
				<bean:message key="reg.newuser.prompt.mi" />
				<html:text property="middleInitial" size="1"/>
				</TD>
			</TR> --%>
			<%--
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.mi" /></TD>
				<TD align="left" valign="top"><html:text property="middleInitial" /></TD>
			</TR>
			--%>
			<%--
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.lastname" /></TD>
				<TD align="left" valign="top"><html:text property="lastName" /></TD>
			</TR>
			--%>
			<%--
			<TR>
				<TD align="right" valign="top"><bean:message key="reg.newuser.prompt.nickname" /></TD>
				<TD align="left" valign="top"><html:text property="nickName" />&nbsp;<bean:message key="reg.newuser.prompt.nickname.suggest" /></TD>
			</TR>
			 --%>
			<TR>
		  	  <TD align="center" valign="top" colspan="2"><input type="checkbox" name="tc" onclick="(regUserFormBean.tc.value='ACCEPT')">&nbsp;<a href="<%=fullVotingCentralUrl%>/tAndC.do" target="_blank"><bean:message key="reg.newuser.terms.and.conditions" /></a>
			 </TD>
			</TR>			
			<TR>
				<TD></TD>
				<TD><html:button property="submitButton" value="Submit" onclick="submitForm('regNewUser'); this.disabled=true;" styleClass="btn"/>
			</TR>

			</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table> 
	

</html:form>