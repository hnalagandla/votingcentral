<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>

<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.forms[1].action = "<%=fullVotingCentralUrl%>" + "/regNewUser/w/confAcct.do" + "?" + "action=" + method;
	document.forms[1].submit();	
	return true;
 }
 
</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/regNewUser/w/confAcct">
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1">User Registration: Step 2 of 2</td>
              </tr>
              <tr>
                <td colspan="2">
	<html:hidden property="emailAddress" />
	<html:hidden property="userName" />
	<bean:define id="email" name="regUserFormBean" property="emailAddress" />
	<bean:define id="userId" name="regUserFormBean" property="userName" />
	<p><bean:message key="reg.newuser.confirm.code.email.heading" arg0="<%=(String)email%>" arg1="<%=(String)userId%>"/></p>
	<logic:present name="regUserFormBean" property="resendConfCodeUrl" > 
			<div>		
				<b>To request the confirmation email to be re-sent <a href="<bean:write name="regUserFormBean" property="resendConfCodeUrl"/>">Click Here</a></b>
			</div>
		</logic:present>
	<table border="0" cellspacing="1" cellpadding="2">
		<tr>		
			<td><b>Confirmation Code:</b></td>
			<td><input type="text" name="confirmCode"> &nbsp;<html:button property="submitButton" value="Submit" onclick="submitForm('checkCode'); this.disabled=true;" styleClass="btn"/></td>				
		</tr>		
		<%--
		<tr>
		<td>&nbsp;</td>			
		<td><html:button property="submitButton" value="Submit" onclick="submitForm('confirmUser'); this.disabled=true;" styleClass="btn"/></td>			
		</tr>
		--%>
	</table>
	
	<bean:message key="reg.newuser.confirm.code.bulk.folder"/>
                </td>
              </tr>

			</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  
</html:form>