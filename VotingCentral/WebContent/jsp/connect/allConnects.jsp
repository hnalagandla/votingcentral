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

	document.manageConnectsForm.action = "<%=fullVotingCentralUrl%>" + "/p/connectInvite.do" + "?" + "action=" + method;
	document.manageConnectsForm.submit();	
	return true;
 }

</SCRIPT>

<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
			<div id="blueTitle">All Friends of <bean:write name="manageConnectsForm" property="userName"/></div>
			<div style="padding:10px 5px 0px 5px;">
				<table border="0" cellpadding="5" cellspacing="1" width="100%">
					<logic:present name="manageConnectsForm" property="friends" >
							<logic:iterate id="friend" name="manageConnectsForm" property="friends" type="com.votingcentral.pres.web.to.VCUserWTO">																
								<tr>
									<td bgcolor="#F2F2F2" style="padding:5px;">	
									<%@ include file="/jsp/include/users/friendBadge.jsp"%>
									</td>
								</tr>								
							</logic:iterate>								
					</logic:present>
				</table>
			</div>						
		</td>									
	</tr>
</table>
</div>