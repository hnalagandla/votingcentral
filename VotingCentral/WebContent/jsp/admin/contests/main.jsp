<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<html:html>
<head>
	<title></title>
<SCRIPT language="JavaScript">

 function submitForm(inAction) { 	
	document.forms[1].submit();	
	return true;
 }
</SCRIPT>	
</head>
<body>
    <html:form action="/p/admin/contests/main">
    
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1">XYZ???</td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
			</table>
			
			    <TABLE border="0" cellspacing="1" cellpadding="1">
			<TBODY>
						<TR>
							<TD><html:radio property="adminChoice" value="approveRejectContests" /></TD>
							<TD><bean:message key="admin.contests.main.prompt.choice.approve.reject" /></TD>
						</TR>
						<TR>
							<TD><html:radio property="adminChoice" value="editContests" /></TD>
							<TD><bean:message key="admin.contests.main.prompt.choice.edit.contest" /></TD>
						</TR>
						<TR>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
						</TR>
						<TR>
							<TD></TD>
							<TD align="center"><html:button property="submitButton"
								value="Continue" onclick="submitForm(); this.disabled=true" /></TD>
						</TR>
					</TBODY>
				</TABLE>
			
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  
 
    </html:form>
</body>
</html:html>
