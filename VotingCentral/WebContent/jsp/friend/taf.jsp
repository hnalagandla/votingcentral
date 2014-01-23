<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/stringUtil.js"></script>
<SCRIPT language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/email.js"></script>
<SCRIPT>
<!--
function taf(){		
	document.tafForm.action="<%=fullVotingCentralUrl%>/p/doTaf.do?action=sendEmail";
	document.tafForm.submit();
}

function back(){		
	//location.href = document.tafForm.tafUrl;
	history.go(-1);
}

//-->
</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/doTaf" method="POST">
<html:hidden property="subject"/>
<html:hidden property="tafUrl"/>
<html:hidden property="type"/>
<center>

	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1">Tell a Friend</td>
              </tr>
                  <tr>
                    <td colspan="2" align="left" class="greyText"><i><bean:message key="create.poll.req.fields" /></i><br/><br/></td>
                  </tr>
			    <tr>
			      <td nowrap align="right" width="30%">Your E-mail:&nbsp;</td>
				  <td><bean:write name="tafForm" property="fromEmail"/></td>
			    </tr>
			    <tr>
			      <td nowrap align="right"><font color="red"><b>*</b></font> Friend's E-mail:&nbsp;</td>
			      <td><html:text property="toEmail" name="tafForm" size="35"/></td>
			    </tr>
			    <tr>
			      <td nowrap align="right">Friend's E-mail:&nbsp;</td>
			      <td><html:text property="toEmail" name="tafForm" size="35"/></td>
			    </tr>
			    <tr>
			      <td nowrap align="right">Friend's E-mail:&nbsp;</td>
			      <td><html:text property="toEmail" name="tafForm" size="35"/></td>
			    </tr>
			    <tr>
			      <td nowrap align="right">Friend's E-mail:&nbsp;</td>
			      <td><html:text property="toEmail" name="tafForm" size="35"/></td>
			    </tr>
			    <tr>
			      <td nowrap align="right">Friend's E-mail:&nbsp;</td>
			      <td><html:text property="toEmail" name="tafForm" size="35"/></td>
			    </tr>				
			    <tr> 
			      <td nowrap align=right>Subject:&nbsp;</td>
			      <td><bean:write name="tafForm" property="subject"/></td>
			    </tr>			    
			    <tr>
			      <td nowrap align="right" valign="top">Message:&nbsp;</td>
			      <td><html:textarea name="tafForm" property="body" rows="5" cols="35"/></td>
			    </tr>
				<tr>
			  	  <td align="right"><input type="checkbox" name="copy" onClick="(form.copy.value='self')"></td>
			  	  <td>Send a copy to yourself</td>
				</tr>
				<tr>
					<td><br/><br/></td>
					<td>
    				<input type="button" value="Submit" onclick="javascript:taf(); this.disabled=true;" class="btn">&nbsp;&nbsp;
    				<input type="button" value="Back" onclick="javascript:back(); this.disabled=true;" class="btn">
					</td>
				</tr>
			  </table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  
  </center>
</html:form>
