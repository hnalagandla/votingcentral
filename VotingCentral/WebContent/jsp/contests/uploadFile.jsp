<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<html:html>
<head>
	<title></title>
<SCRIPT language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/textCounter.js"></script>	
<script language="JavaScript">

function submitForm(action) {
	document.forms[1].action = "<%=fullVotingCentralUrl%>" + "/p/contests/showFileUpload.do" + "?" + "action=" + action;
	document.forms[1].submit();	
	return true;
 }
</script>	
</head>
<body>
	<%@ include file="/jsp/include/errorMessages.jsp"%>
    <html:form action="/p/contests/showFileUpload" enctype="multipart/form-data" method="post">
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td class="blueTitle1">Participate in a Voting Central Contest</td>
              </tr>
			  <tr>
                  <td colspan="2" align="left"><bean:message key="reg.newuser.heading.req.fields" /></td>
              </tr>              
              <tr>
                <td>
			    <TABLE border="0" cellspacing="1" cellpadding="2">
			    	<TBODY>
			    		<TR>
							<TD><bean:message key="upload.contest.file.prompt.contest.type" /></TD></TR>
							<bean:define id="cTypes" name="contestForm" property="contestTypes" type="java.util.Collection"/>				
							<TR><TD><html:select
								property="contestType">
								<html:options collection="cTypes" property="value"
									labelProperty="label" />
							</html:select></TD>
						</TR>
					<tr><td><bean:message key="upload.contest.file.prompt.attach.file" /></td></tr>
					<tr><td><html:file property="uploadFileName" size="25"/></td></tr>		
					<TR><TD><bean:message key="upload.contest.file.prompt.keywords" /></TD></TR>					
					<TR><TD><html:text property="keywords" /></TD></TR>					
					<TR><TD><bean:message key="upload.contest.file.prompt.comments" /></TD></TR>
					<TR><TD><html:textarea
								property="userComments" cols="42" rows="3"
								onkeydown="textCounter(this.form.userComments, this.form.remLenDesc, 128);"
								onkeyup="textCounter(this.form.userComments,this.form.remLenDesc,128);" />
					</TD></TR>
					<TR><TD>Characters remaining: <input type=box readonly name=remLenDesc size=3 value=128></TD></TR>		
					<tr>
						<td>
						<br/><html:button property="submitButton" value="Submit" onclick="submitForm('uploadFile'); this.disabled=true;" styleClass="btn"/>&nbsp;		
						</td>
					</tr>		
			    	</TBODY>
			    </TABLE>
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
</body>
</html:html>
