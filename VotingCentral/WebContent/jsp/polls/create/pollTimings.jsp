<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="com.votingcentral.model.enums.*"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">
 function submitForm(method) {

	document.pollTimingsFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/createPoll/pollTimes.do" + "?" + "action=" + method;
	document.pollTimingsFormBean.submit();	
	return true;
 }
</SCRIPT>		
<script type='text/JavaScript' src='<%=fullVotingCentralUrl%>/scripts/scw.js'></script>

	<%@ include file="/jsp/include/errorMessages.jsp"%>
    <html:form action="/p/createPoll/pollTimes">
	<div style="padding:0px 0px 10px 0px;"> 
	<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
		<tr>
			<td align="center" valign="middle" bgcolor="#FFFFFF">
			<div id="blueTitle"><bean:message key="create.poll.heading3" /></div>
	       	<div id="bulletBox">
			<!-- Content Start -->
				<table width="98%"  border="0" cellspacing="1" cellpadding="2">
	              <tr>
	                <td>

				    <html:hidden property="pollId"/>
				    <table border="0" cellspacing="1" cellpadding="2">
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>	
				    <tr>
				    	<td valign="top"><bean:message key="create.poll.prompt.poll.start.date" /></td>
				    	<td valign="top" >
				    	<table border="0" cellspacing="0" cellpadding="0">
				    	<tr>
				    		<td valign="middle"><input name="pollStartDate" id='pollStartDate' type='text' value=' '/></td>
				    		<td valign="middle"><img src='<%=fullVotingCentralImgUrl%>/images/scw.gif'title='Click Here' alt='Click Here' onclick="scwShow(document.getElementById('pollStartDate'),this);" hspace="5"/></td>
				    	</tr>
				    	</table>
				    	</td>
				    </tr>
				    <tr>
				    		<td valign="top" ><bean:message key="create.poll.prompt.poll.start.time" /></td>
				    		<td valign="top" >
								<select name="pollStartTime">
								<option value="Select Time">- Select -</option>
								<option value="00:00">00:00</option>
								<option value="01:00">01:00</option>								
								<option value="02:00">02:00</option>								
								<option value="03:00">03:00</option>								
								<option value="04:00">04:00</option>								
								<option value="05:00">05:00</option>								
								<option value="06:00">06:00</option>								
								<option value="07:00">07:00</option>								
								<option value="08:00">08:00</option>								
								<option value="09:00">09:00</option>								
								<option value="10:00">10:00</option>								
								<option value="11:00">11:00</option>								
								<option value="12:00">12:00</option>								
								<option value="13:00">13:00</option>								
								<option value="14:00">14:00</option>								
								<option value="15:00">15:00</option>																																																																																																																								
								<option value="16:00">16:00</option>								
								<option value="17:00">17:00</option>								
								<option value="18:00">18:00</option>								
								<option value="19:00">19:00</option>								
								<option value="20:00">20:00</option>								
								<option value="21:00">21:00</option>								
								<option value="22:00">22:00</option>								
								<option value="23:00">23:00</option>																																																																								
								</select>
				    	</td>
				    </tr>
						<%-- <TR>
							<TD valign="top" width="30%">
							<bean:message key="create.poll.prompt.poll.start" /></td>
							<td valign="top">
							<bean:define id="startTimes" name="pollTimingsFormBean" property="pollStartTimes" type="java.util.Collection"/>
							<html:select property="pollStartTimestamp"><html:options collection="startTimes" property="value" labelProperty="label" /></html:select>
							</TD>
						</TR> --%>
							<TR>
								<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
							</TR>		 
							<tr> 
								<td valign="top">
									<bean:message key="create.poll.prompt.poll.end" />
								</td>
								<td valign="top">
									<bean:define id="endTimes" name="pollTimingsFormBean" property="pollEndTimes" type="java.util.Collection"/>
									<html:select property="pollEndTimestamp"><html:options collection="endTimes" property="value" labelProperty="label" /></html:select>
								</td>
							</tr>							
							<%
								if (request.isUserInRole(VCUserRolesEnum.ADMIN.getName())) {
							 %>
							<TR>
								<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
							</TR>
							<tr>		 
							<TD valign="top">
							<bean:message key="create.poll.prompt.poll.blockout" /></td>
							<td valign="top"><bean:define id="blockTimes" name="pollTimingsFormBean" property="pollBlockOutTimes" type="java.util.Collection"/> 									
							<html:select property="pollBlockoutPeriodMS"><html:options collection="blockTimes" property="value" labelProperty="label" />
							</html:select>
							</TD>
							</TR>		 		 			
							 <%
								}
							 %>
							<TR>
								<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
							</TR>
							<TR>
								<td>&nbsp;</td>
								<TD><html:button property="preview" value="Preview The Poll"
									onclick="submitForm('upsertPollTimes'); this.disabled=true;" styleClass="btn"/></TD>
							</TR> 			 						
					    </TABLE>		
	                </td>
	              </tr>
				</table>

			<!-- Content End -->
			</div>								
			</td>									
		</tr>
	</table>
	</div> 
      
    </html:form>
