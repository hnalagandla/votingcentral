<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@page import="com.votingcentral.model.enums.*" %>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(inAction) {

 	document.forms[0].internalAction.value = inAction;
	document.forms[0].submit();	
	return true;
 }
</SCRIPT>
<html:form action="/admin/poll">
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1"><bean:message key="admin.poll.main.heading" /></td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
					<TR>
						<TD valign="top" width="25%"><bean:message key="create.poll.prompt.name" /></TD>
						<TD valign="bottom">
						<html:text size="35" property="pollName" onkeydown="textCounter(this.form.pollName, this.form.remLenName, 64);" onkeyup="textCounter(this.form.pollName,this.form.remLenName,64);" /><br/>
						<input type=box readonly name=remLenName size=2 value=64 style="border:none;color:gray;"><font color="gray">characters remaining.</font></TD>
					</TR>
					<TR>
						<TD valign="top"><bean:message key="create.poll.prompt.desc" /></TD>
						<TD valign="bottom">
						<html:textarea property="pollDesc" cols="35" rows="3" onkeydown="textCounter(this.form.pollDesc, this.form.remLenDesc, 256);" onkeyup="textCounter(this.form.pollDesc,this.form.remLenDesc,256);" /></br>
						<input type=box readonly name=remLenDesc size=3 value=256 style="border:none;color:gray;"><font color="gray">characters remaining.</font></TD>
					</TR>
					<TR>
						<TD valign="top"><bean:message key="create.poll.prompt.keywords" /></TD>
						<TD valign="bottom">
						<html:text size="35" property="keywords" onkeydown="textCounter(this.form.keywords, this.form.remLenKeywords, 64);" onkeyup="textCounter(this.form.keywords,this.form.remLenKeywords,64);" /><br/>
						<input type=box readonly name=remLenKeywords size=2 value=64 style="border:none;color:gray;"><font color="gray">characters remaining.</font><br>
						<bean:message key="create.poll.prompt.keywords.suggest" /></TD>
					</TR>
					<TR>
						<TD valign="top">
						<bean:message key="create.poll.prompt.category.1" />
						<bean:define id="listOfSuperCats" name="pollAdminFormBean" property="categories" type="java.util.Collection"/>
						</TD>
						<TD valign="top">
						<html:select property="pollCategory1"> 
				        <html:options collection="listOfSuperCats" property="value" labelProperty="label"/>
		        		</html:select>
		        		</TD>
					</TR>
						<TR>
							<TD width="30%" valign="top"><bean:message key="create.poll.prompt.question.type" /></TD>
							<TD valign="top">
							<bean:define id="quesTypes" name="pollAdminFormBean" property="questionTypes" type="java.util.Collection"/>
							<html:select property="questionType" onchange="javascript:userEntered();">
							<html:options collection="quesTypes" property="value" labelProperty="label" />
							</html:select>
							</TD>
						</TR>						
					    <TR>
							<TD valign="top"><bean:message key="create.poll.prompt.question" /></td>
							<td valign="top"><html:text	property="question" size="25"/></TD>
						</TR>						
						<TR>
							  <TD valign="top"><bean:message key="create.poll.prompt.question.image" /></td>
							  <td valign="top" class="greyText"><html:hidden property="questionImageId" />
								<html:file  property="questionImage" size="25"/><br/>Upload image to support question</TD>																											
						</TR>
						<logic:notEmpty name="pollAdminFormBean" property="questionImageId">
							<TR>
									<TD colspan="2"><font color="red"><b><bean:message key="create.poll.prompt.another.image" /></b></font></TD>
							</TR>									
							<TR>
									<TD colspan="2"><font color="red"><b><a href="<bean:write name="pollQuestionsFormBean" property="questionDelImageUrl"/>"><bean:message key="create.poll.prompt.delete.image" /></a></b></font></TD>
							</TR>							
							<TR>
									<TD colspan="2">
											<img src="<%=fullVotingCentralImgUrl%>/ShowImage.do?imageId=<bean:write name="pollQuestionsFormBean" property="questionImageId"/>"/>
									</TD>
							</TR>
						</logic:notEmpty>													
						<tr>
							<td colspan="2" class="greyTitle1">Poll Answer Choices</td>
						</tr>
						<% 
							int k = 0;
						%>
						<logic:iterate name="pollAdminFormBean" property="answerObjects" id="ao" indexId="idx" type="com.votingcentral.forms.polls.PollAnswersSubBean">
							<% 
								k++;
							%>
							<tr>
							  <td valign="top">				
								<bean:message key="create.poll.prompt.answer" /> <%=k%>:</td>
								<td valign="top"><html:text name="ao" property="answer" size="25"/>
								<html:hidden name="ao" property="answerId" />
							 </td>						    
							 </tr>						
							 <tr>
								  <td valign="top">				
									<bean:message key="create.poll.prompt.answer.image" /></td>
									<td valign="top" class="greyText"><html:hidden name="ao" property="answerImageId" />
									<% String fileName="answerImageFile"+ idx.longValue(); %>
									<input type="file" name="<%=fileName%>" value="<%=ao.getAnswerImageFile()%>" size="25">
								</td>	
							</tr>
							<logic:notEmpty name="ao" property="answerImageId">
								<TR>											
									<TD colspan="2"><font color="red"><b><bean:message key="create.poll.prompt.another.image" /></b></font></TD>
								</TR>																		
							<TR>
									<TD colspan="2"><font color="red"><b><a href="<%=ao.getDelImgURL()%>"><bean:message key="create.poll.prompt.delete.image" /></a></b></font></TD>
							</TR>											
								<TR>
											<TD colspan="2">
											<img src="<%=fullVotingCentralImgUrl%>/ShowImage.do?imageId=<bean:write name="ao" property="answerImageId"/>"/>
											</TD>
								</TR>								
							</logic:notEmpty>								
						</logic:iterate>
					<tr>
				    	<td valign="top" width="150"><bean:message key="create.poll.prompt.poll.start.date" /></td>
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
							<tr> 
								<td valign="top">
									<bean:message key="create.poll.prompt.poll.end" />
								</td>
								<td valign="top">
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
							<td valign="top"><bean:define id="blockTimes" name="pollAdminFormBean" property="pollBlockOutTimes" type="java.util.Collection"/> 									
							<html:select property="pollBlockoutPeriodMS"><html:options collection="blockTimes" property="value" labelProperty="label" />
							</html:select>
							</TD>
							</TR>		 		 			
							 <%
								}
							 %>
							<TR>
								<td>&nbsp;</td>
								<TD><html:button property="preview" value="Preview The Poll"
									onclick="submitForm('upsertPollTimes'); this.disabled=true;" styleClass="btn"/></TD>
							</TR> 								 
			</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  
</html:form>
