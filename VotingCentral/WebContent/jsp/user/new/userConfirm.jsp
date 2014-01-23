<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%
	String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
 %>
<div style="padding:0px 0px 0px 3px"> 
<%@ include file="/jsp/include/errorMessages.jsp"%>
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">About Us</div>
       	<div id="bulletBox">

			<%-- content start--%>
			<html:form action="/regNewUser/confirmed">
			<bean:define id="email" name="regUserFormBean" property="emailAddress" />
			<bean:define id="userName" name="regUserFormBean" property="userName" />			
				<font size="2">
				<bean:message 
					key="reg.newuser.confirm.heading" arg0="<%=(String)email%>" arg1="<%=(String)userName%>" />
				<br><br><bean:message 
					key="reg.newuser.confirm.choices" />
				</font>
				<TABLE border="0" cellspacing="1" cellpadding="1">
					<TBODY>
						<TR>
							<TD>
							<ul>
								<bean:define id="textLinkDesc" name="regUserFormBean" property="successPageUrls" type="java.util.List"/>				
										<logic:iterate id="line" name="textLinkDesc" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >																				
											<li><a href="<%=line.getHref()%>"><%=line.getText()%></a></li>
										</logic:iterate>	
							</ul>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</html:form>
			
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>