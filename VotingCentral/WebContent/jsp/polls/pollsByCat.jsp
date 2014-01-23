<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>

    <html:form action="/pollsByCat">	
	<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<bean:define id="cat" name="pollsByCatFormBean" property="categoryName" type="java.lang.String"/>	
		<div id="blueTitle"><bean:message key="polls.by.category" arg0='<%=cat%>'/></div>
       	<div id="bulletBox">
		<!-- Content Start -->
				<logic:present name="pollsByCatFormBean" property="pollsByCat" >
				<table width="100%" cellspacing="0" cellpadding="0" border="0">											
				<logic:iterate id="pto" name="pollsByCatFormBean" property="pollsByCat" type="com.votingcentral.model.db.dao.to.PollTO">																
					<%@ include file="/jsp/include/polls/pollTeaser.jsp"%>
				</logic:iterate>						
				</table>										
				</logic:present>	
		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div> 	
    </html:form>