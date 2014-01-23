<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<div style="padding:0px 0px 20px 0px"> 

<table border="0" cellpadding="0" cellspacing="1" class="lightbluebg" width="100%">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Newest VC Winner</div>
       	<div id="bulletBox" style="margin:10px 10px 10px 200px;">
       	
			<%-- content start--%>
				<div style="width:220px;background:#E2F0E2;padding:10px;" align="center">
				<logic:notEmpty name="vcWinnersForm" property="currWinner">
				<bean:define id="user" name="vcWinnersForm" property="currWinner" type="com.votingcentral.pres.web.to.VCUserWTO"/>				

								<%@ include file="/jsp/include/users/userBadge.jsp"%>	
				
				</logic:notEmpty>
				</div>
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>

<div style="padding:0px 0px 20px 0px"> 
<table border="0" cellpadding="0" cellspacing="1" class="lightbluebg" width="100%">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Past VC Winners</div>
       	<div id="bulletBox" style="margin:10px;">
			<%-- content start--%>
				<% int k = 1;%>
				<logic:notEmpty name="vcWinnersForm" property="prevWinners">
				<div>
				<table cellpadding=0 cellspacing=0 border=0 width="100%">
				<tr>
				<logic:iterate id="user" name="vcWinnersForm" property="prevWinners" type="com.votingcentral.pres.web.to.VCUserWTO">
						<%
							if (k%3 != 0) {
					    %>
								<td align="center">	
								<%@ include file="/jsp/include/users/userBadge.jsp"%>
								</td>
						<% } else {
						 %>
							<td align="center">	
								<%@ include file="/jsp/include/users/userBadge.jsp"%>
								</td>
							</tr>
					 		<tr>
	
						<%} k++; %>								
						    </logic:iterate>
						</tr>																
						</table>
						</div>						
				</logic:notEmpty>	
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>		

