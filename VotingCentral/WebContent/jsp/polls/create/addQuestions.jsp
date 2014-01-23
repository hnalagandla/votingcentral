<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">
 function submitForm(method) {

	document.pollQuestionsFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/createPoll/addQuestions.do" + "?" + "action=" + method;
	document.pollQuestionsFormBean.submit();	
	return true;
 }
 function textCounter(field, countfield, maxlimit) {
  	if (field.value.length > maxlimit) {
		field.value = field.value.substring(0, maxlimit);
	} else {
		countfield.value = maxlimit - field.value.length;
	}
 }
 function userEntered()
 {
	var selInd = 0;
	var OptionsLen = document.pollQuestionsFormBean.answerType.length;
	var showHide = "block";

	for (var i = 0; i < OptionsLen; i++)	//find selected option
	{
		if (document.pollQuestionsFormBean.answerType.options[i].selected)
		{
			selInd = i;
		}
	}
	if (selInd == 3)	//userTyped answer
	{
		showHide = "none";
	}

	document.getElementById("answerChoices").style.display= showHide;		
 }	

</SCRIPT>	
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/createPoll/addQuestions" enctype="multipart/form-data" method="post">
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="642" class="lightbluebg">
	<tr>
		<td align="center" valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle"><bean:message key="create.poll.heading2" /></div>
       	<div id="bulletBox">
		<!-- Content Start -->

                
			    <html:hidden property="questionId"/>
				<html:hidden property="pollId"/>	
			    <TABLE border="0" cellspacing="1" cellpadding="2" width="98%">
					<TBODY>
						<tr>
							<td colspan="2" class="greyTitle1">Poll Question &nbsp;&nbsp;<font size="1"><i><bean:message key="create.poll.req.fields" /></i></font></td>
						</tr>
						<tr>
							<td colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></td>
						</tr>
						<TR>
							<TD width="35%" valign="top"><bean:message key="create.poll.prompt.question.type" /></TD>
							<TD valign="top">
							<bean:define id="ansTypes" name="pollQuestionsFormBean" property="answerTypes" type="java.util.Collection"/>
							<html:select property="answerType" onchange="javascript:userEntered();">
							<html:options collection="ansTypes" property="value" labelProperty="label" />
							</html:select>
							<br/><i class="greyText">In the answer choices you offer below, how many answer choices can a voter vote for? In other words, among the answer choices you offer below, can they vote for "Only One Answer Choice' or vote for "One or More Answer Choices".</i>
							</TD>
						</TR>
						<%--<TR>
							<TD colspan="2"><img src="/images/s.gif" width="1" height="1"></TD>
						</TR>--%>
						<TR>							
						</TR>
					    <TR>
							<TD valign="top"><bean:message key="create.poll.prompt.question" /></td>
							<td valign="top"><html:text	property="question" size="25"/></TD>
						</TR>
						<%--<TR>
							<TD colspan="2"><img src="/images/s.gif" width="1" height="1"></TD>
						</TR>--%>
						<TR>
							  <TD valign="top"><bean:message key="create.poll.prompt.question.image" /></td>
							  <td valign="top" class="greyText"><html:hidden property="questionImageId" />
								<html:file  property="questionImage" size="25"/><br/>Upload image to support question</TD>																											
						</TR>
						<logic:notEmpty name="pollQuestionsFormBean" property="questionImageId">
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
						<TR>
							<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="2"></TD>
						</TR>
						</table>
						<div id="answerChoices">	
						<TABLE border="0" cellspacing="1" cellpadding="2" width="98%">
						<tr>
							<td colspan="2" class="greyTitle1">Poll Answer Choices</td>
						</tr>
						<tr>
							<td colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></td>
						</tr>	
						<% 
							int k = 0;
						%>
						<logic:iterate name="pollQuestionsFormBean" property="answerObjects" id="ao" indexId="idx" type="com.votingcentral.forms.polls.PollAnswersSubBean">
							<% 
								k++;
							%>
							<tr>
							  <td valign="top" width="35%">				
								<bean:message key="create.poll.prompt.answer" /> <%=k%>:</td>
								<td valign="top"><html:text name="ao" property="answer" size="25"/>
								<html:hidden name="ao" property="answerId" />
							 </td>						    
							 </tr>
						<%-- %><TR>
							<TD colspan="2"><img src="/images/s.gif" width="1" height="5"></TD>
						</TR>--%>
							 <tr>
								  <td valign="top">				
									<bean:message key="create.poll.prompt.answer.image" /></td>
									<td valign="top" class="greyText"><html:hidden name="ao" property="answerImageId" />
									<% String fileName="answerImageFile"+ idx.longValue(); %>
									<input type="file" name="<%=fileName%>" value="<%=ao.getAnswerImageFile()%>" size="25">
									<%--html:file  name="ao" property="<%=fileName%>" size="15"/--%>																		
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
						<TR>
							<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
						</TR>	
						</logic:iterate>
						<TR><TD></TD><TD><html:button property="preview" value="Next" onclick="submitForm('upsertQuestion'); this.disabled=true;" styleClass="btn"/></TD></TR>
						</table>
						</div>	
						<table>				
	
						</TBODY>
					</TABLE>

		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div> 



</html:form>