<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.requestConnectForm.action = "<%=fullVotingCentralUrl%>" + "/p/connectInvite.do" + "?" + "action=" + method;
	document.requestConnectForm.submit();	
	return true;
 }

 function textCounter(field, countfield, maxlimit) {
  	if (field.value.length > maxlimit) {
		field.value = field.value.substring(0, maxlimit);
	} else {
		countfield.value = maxlimit - field.value.length;
	}
 }
</SCRIPT>
<div style="padding:0px 0px 0px 3px"> 
<bean:define id="buttonDisabled" name="requestConnectForm" property="buttonDisabled" type="java.lang.Boolean"/>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Invite To Connect</div>
       	<div id="bulletBox">

			<%-- content start--%>
			<html:form action="/p/connectInvite">			
			<logic:present name="requestConnectForm" property="connectToUserName" >
				<div>Invite '<b><bean:write name="requestConnectForm" property="connectToUserName"/></b>' to Connect with you on VotingCentral.</div>
			</logic:present>
			<html:hidden property="connectToUserName" />
			<logic:notPresent name="requestConnectForm" property="connectToUserName" >              
          		<div>				        	
					Invite to Connect to a VotingCentral User	
				</div>	          		
          		<div>				        	
					 Enter a VotingCentral UserName: <html:text size="35" property="connectToUserName"/>
				</div>	          						
          		<div>				        	
					 	OR
				</div>	
          		<div>				        	
					 Enter an Email Address: <html:text size="35" property="connectToEmail"/>
				</div>	          										          										
	        </logic:notPresent>			
			<div>Include a Personal Note.(Optional)</div>	        
			<div>
				<html:textarea name="requestConnectForm" property="connectComments" cols="35" rows="3" onkeydown="textCounter(this.form.connectComments, this.form.remLenDesc, 256);" onkeyup="textCounter(this.form.connectComments,this.form.remLenDesc,256);" /></br>
				<input type=box readonly name=remLenDesc size=3 value=256 style="border:none;color:gray;"><font color="gray">characters remaining.</font>
			</div>	        
			<div><html:button property="send" value="Send Invitation" onclick="submitForm('send'); this.disabled=true;" disabled="<%=buttonDisabled.booleanValue()%>" styleClass="btn"/></div>
			</html:form>
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>
