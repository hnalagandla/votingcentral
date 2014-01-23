<html:hidden name="pto" property="pollId" />

	<bean:define id="pollData" name="pto" property="pollData"
	type="com.votingcentral.model.polls.PollData" /> <bean:define
	id="questionnaire" name="pollData" property="questionnaire"
	type="com.votingcentral.model.polls.Questionnaire" /> <bean:define
	id="fName" name="pto" property="formName" type="java.lang.String" /> <bean:define
	id="fId" name="pto" property="formId" type="java.lang.String" /> <bean:define
	id="totalFpCount" name="pto" property="totalFeaturedPollCount"
	type="java.lang.String" />
	
	<table border="0" cellpadding="5" cellspacing="1" width="100%" class="darkgreybg">		
			<tr>
					<td bgcolor="#F2F2F2" style="padding:5px;">
						<!-- content starts here -->
						<table width="100%" border="0" cellspacing="1" cellpadding="2">
								<html:form action="<%=fName%>">
										<tr>
											<td colspan="2">
												<CENTER>
															<html:hidden name="pto" property="pollId" /> 
															<logic:iterate id="qData" name="questionnaire" property="questions" indexId="i"
																type="com.votingcentral.model.polls.QuestionData">
																<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
																	<%imgX++;%>
																	<tr>
																		<logic:notEmpty name="qData" property="questionImageId">
																			<td valign="middle" width="10%">
																				<table border="0" cellpadding="0" cellspacing="1"
																					bgcolor="#e5e1de" style="width:112px;height:80px;">
																					<tr>
																						<td align="center" valign="middle" bgcolor="#FFFFFF">
																						<%if (!isFacebook) {
																						%>
																						<a href="javascript:void(0);"><img
																							src="<bean:write name="qData" property="minImageUrl"/>"
																							border="0" hspace="0" onMouseOver=show_200(
																							"<bean:write name="qData" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div()
																							onerror=gag(this,100)></a> 
																						<%} else {
																						%> 
																						<a href="#"><img src="<bean:write name="qData" property="minImageUrl"/>" border="0" hspace="0"></a> 
																						<%}%>
																						</td>
																					</tr>
																				</table>
																			</td>
																		</logic:notEmpty>
																		<td class="txtquestion" valign="middle"
																			style="font-size:11px;line-height:14px;">
																			<bean:write name="qData" property="question" /> 
																			<html:hidden name="qData" property="questionId" />
																		</td>
																	</tr>
																</TABLE>
																<DIV><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="2"></DIV>
																<% int k = 0; %>
																<html:hidden name="qData" property="questionId" />
																<logic:notEqual name="qData" property="answerType" value="TEXTAREA">
																	<logic:iterate id="aData" name="qData" property="answerChoices"
																		indexId="j" type="com.votingcentral.model.polls.AnswerChoice">
																		<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
																			<TR bgcolor="#ECECEC">
																				<logic:notEmpty name="aData" property="answerImageId">
																					<TD width="20%"><% imgX++; %>
																						<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de" style="width:160px;height:117px;">
																							<tr>
																								<td align="center" valign="middle" bgcolor="#FFFFFF">
																									<%if (!isFacebook) {%>
																									<a href="javascript:void(0);"><img
																										src="<bean:write name="aData" property="minImageUrl" />"
																										border="0" hspace="0" onMouseOver=show_200(
																										"<bean:write name="aData" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div()
																										onerror=gag(this,100)></a> 
																									<%} else {%> 
																									<a href="#"><img
																										src="<bean:write name="aData" property="minImageUrl"/>"
																										border="0" hspace="0"></a> 
																									<%} %>
																								</td>
																							</tr>
																						</table>
																					</TD>
																				</logic:notEmpty>
																				<TD WIDTH="20" valign="top" class="txtanswer"><%=choices.get(k)%>]
																					<% k++; %>
																				</TD>
																				<TD WIDTH="30" valign="top" style="padding-top:5px;">
																						<logic:equal
																							name="qData" property="answerType" value="RADIO">
																							<%--<html:radio name="homeFormBean" property="answerId" value="<%=aData.getAnswerId()%>" onclick="pausePoll(<%=totalFpCount%>);"/>--%>
																							<input type="radio" name="answerId"
																								value="<%=aData.getAnswerId()%>"
																								onclick="pausePoll(<%=totalFpCount%>);">
																						</logic:equal> 
																						<logic:equal name="qData" property="answerType"	value="CHECKBOX">
																							<%--<html:checkbox name="homeFormBean" property="answerId" value="<%=aData.getAnswerId()%>" onclick='pausePoll(<%=totalFpCount%>);' style="border:none;"/>--%>
																							<input type="checkbox" name="answerId"
																								value="<%=aData.getAnswerId()%>"
																								onclick="pausePoll(<%=totalFpCount%>);">
																						</logic:equal>
																				</TD>
																				<TD valign="top" class="txtanswer"
																					style="font-size:11px;line-height:14px;">
																					<bean:write name="aData" property="answer" />
																				</TD>
																			</TR>
																		</TABLE>
																		<DIV><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="2"></DIV>
																	</logic:iterate>
																</logic:notEqual>
															</logic:iterate> 
														<%		String newAction = fullVotingCentralUrl + "/p/h/castAVote" + fId + ".do?" + "action=voteFromHome"; %> 
														<div style="padding-top:5px;">
																<table cellpadding="0" cellspacing="0" border="0" width="100%">
																	<tr>
																		<td valign="middle"><a
																			href="<bean:write name="pto" property="displayPollUrl"/>"
																			class="boldLinks">Full Poll View</a>
																		</td>
																		<td valign="middle" align="right">
																		   <img style="cursor:hand;"
																			onclick="javascript:expandtab('maintab', --nCurrentPoll);"
																			src="<%=fullVotingCentralImgUrl%>/images/btns/btn-previous-26x24.gif"
																			width="26" height="24" border="0"
																			style="margin:0px;padding-bottom:2px;"> 
																			<img
																			style="cursor:hand;"
																			onclick="javascript:togglePoll(<%=totalFpCount%>, '<%=fullVotingCentralImgUrl%>');"
																			id='<bean:write name="pto" property="btnPauseNumber"/>'
																			src="<%=fullVotingCentralImgUrl%>/images/btns/btn-pause-26x24.gif"
																			width="26" height="24" border="0"
																			style="margin:0px;padding-bottom:2px;"> 
																			<img
																			style="cursor:hand;"
																			onclick="javascript:expandtab('maintab', ++nCurrentPoll);"
																			src="<%=fullVotingCentralImgUrl%>/images/btns/btn-next-26x24.gif"
																			width="26" height="24" border="0"
																			style="margin:0px;padding-bottom:2px;"> 
																			<input type="image"
																			src="<%=fullVotingCentralImgUrl%>/images/btns/btn-Vote-62x27.gif"
																			value="Vote"
																			onclick="this.form.action=<%=newAction%>; this.disabled=true;"
																			width="62" height="27" border="0"
																			style="border:0px;margin:0px;padding:0px 0px 0px 20px;" />
																		</td>
																	</tr>
																</table>
														</div>
												</CENTER>
											</td>
										</tr>
								</html:form>
						</table>
					<!-- content ends here -->
					</td>
			</tr>
	</table>

