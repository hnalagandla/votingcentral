<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ page import="com.votingcentral.pres.web.to.*" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
<script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<script type="text/javascript" language="JavaScript">run_after_body();</script>

<script language="JavaScript">
function submitForm(n) {
	alert ("n is" + n);
	document.forms[n].action = "<%=fullVotingCentralUrl%>" + "/p/c/castAVote" + n + ".do?" + "action=voteFromContest";
	alert ("action is " + document.forms[n].action);
	document.forms[n].submit();	
	return true;
 }
</script>	
<%
		List choices = new ArrayList();
		choices.add("a");
		choices.add("b");
		choices.add("c");
		choices.add("d");
		choices.add("e");
		choices.add("f");
		choices.add("g");
		choices.add("h");
		choices.add("i");
		choices.add("j");
		
		int imgX = 0;
 %>
 
 
<div style="padding:0px 0px 0px 0px"> 
	<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
		<tr>
			<td align="center" valign="middle" bgcolor="#FFFFFF" style="padding:0px 0px 5px 0px;">
			<div id="blueTitle" style="margin-bottom:5px;">Current Contests</div>
	          <!-- content starts here -->
	            <%-- If there are no contests on currently show the upload images links for all contests.--%>
	          	<logic:notPresent name="contestForm" property="currentLiveContests" >              
	          		<div class="txtLabelMedium">				        	
							No Contests currently on. Click on the following links to upload pictures into the respective contests. 
							You will be informed via email when your picture is selected for a contest.
					</div>	          		
	          	</logic:notPresent>
				<logic:present name="contestForm" property="currentLiveContests" >              
	          		<div class="txtLabelMedium">				        	
							Click on the following links to upload pictures into the respective contests.
							You will be informed via email when your picture is selected for a contest.							
					</div>	          		
	          	</logic:present>	          	
				<bean:define id="oUrls" name="contestForm" property="otherUploadUrls" type="java.util.List"/>
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<logic:iterate id="tldto" name="oUrls" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >            	
							<td><a class="boldMediumBlueLinks" href="<%=tldto.getHref()%>"><%=tldto.getText()%></a> &nbsp; &nbsp; </td>
			        	 </logic:iterate>
		 		    </tr>
		         </table>	          	
			    <logic:present name="contestForm" property="currentLiveContests" >              
			    	<% 
			    		int k = 0;
			    	%>
					<logic:iterate id="contest" name="contestForm" property="currentLiveContests">
					<div align="left" style="padding:5px 10px 5px 10px;">
					<table border="0" cellpadding="5" cellspacing="1" width="100%" class="darkgreybg">
					<tr><td id="greenTitle">
						<bean:define id="mapKey" name="contest" property="key" type="java.lang.String"/>	
						<%=mapKey%> Contest	
					</td></tr>
					<tr>
					<td bgcolor="#F2F2F2" style="padding:5px;">									
						<bean:define id="pto" name="contest" property="value" type="com.votingcentral.pres.web.to.ContestWTO" />							
						<div class="greyText"><strong>Contest Active From:</strong> <i><bean:write name="pto" property="startTimestampStr" /></i> To <i><bean:write name="pto" property="endTimestampStr" /></i></div>
						<div class="greyText">
            			<bean:define id="keywords" name="pto" property="kwUrls" type="java.util.List"/>				
            			<strong>Tags:</strong> 
							<logic:iterate id="keyword" name="keywords" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >            	
            					<a href="<%=keyword.getHref()%>"><%=keyword.getText()%></a> &nbsp;
		            		</logic:iterate>
		            	</div>
				        <logic:present name="pto" property="message">
							<div>				        	
								<font color="red"><bean:write name="pto" property="message"/></font>
							</div>
				        </logic:present>												
						<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
							<TR>
            					<bean:define id="pollData" name="pto" property="pollData" type="com.votingcentral.model.polls.PollData"/>		            	
		            			<bean:define id="questionnaire" name="pollData" property="questionnaire" type="com.votingcentral.model.polls.Questionnaire"/>
							 	<bean:define id="fName" name="pto" property="formName" type="java.lang.String"/>		            			
							 	<bean:define id="fId" name="pto" property="formId" type="java.lang.String"/>							 	
		            			 <html:form action="<%=fName%>">
		            			<html:hidden name="pto" property="pollId" />
		            			<bean:define id="showPollAsDisabled" name="pto" property="showPollAsDisabled" type="java.lang.Boolean"/>
								<logic:iterate id="qData" name="questionnaire" property="questions" 
									indexId="i"  type="com.votingcentral.model.polls.QuestionData">
									<% k = 0; %>
		            			<html:hidden name="qData" property="questionId" />									
								    <logic:iterate id="aData" name="qData" property="answerChoices" 
										indexId="j"  type="com.votingcentral.model.polls.AnswerChoice">
										<TD>
											<% imgX++; %>
										<TABLE width="150" cellspacing="0" cellpadding="0" border="0">
											<tr>
												<td style="padding:5px 0px 5px 0px;">
													<table border="0" cellpadding="0" cellspacing="1" class="darkgreybg">
														<tr>
															<td align="center" bgcolor="#FFFFFF">
																<% if (!isFacebook) {%>
																<a href="javascript:void(0);">
																	<img src="<bean:write name="aData" property="minImageUrl" />" border="0" hspace="0" onMouseOver=show_200("<bean:write name="aData" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)>
																</a>
																<%} else { %>
																<a href="#">
																	<img src="<bean:write name="aData" property="minImageUrl" />" border="0" hspace="0">
																</a>																
																<%}%>																
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td align="center">
													<table cellpadding="0" cellspacing="0" border="0">
														<tr>
															<td class="txtanswer" valign="middle"><%=choices.get(k)%>]</td>
															<td valign="middle"><html:radio name="contestForm"
																property="answerId" value="<%=aData.getAnswerId()%>"
																disabled="<%=showPollAsDisabled.booleanValue()%>" style="border:0px;"/></td>
														</tr>
													</table>
											<% k++; %>
												</td>
											</tr>
											<tr>
												<td class="greyText" style="padding-left:5px;"><bean:write name="aData" property="answer" /></td>
											</tr>
										</TABLE>
										</TD>
									</logic:iterate>					
										<td valign="top" style="width:100px;padding:0px 5px 0px 5px;">
											<div class="greyText" style="padding-bottom:5px;padding-top:5px;"><a href="<bean:write name="pto" property="tafUrl"/>">Tell a Friend</a>&nbsp;&nbsp;<a href="<bean:write name="pto" property="tafUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/tell-friend.gif" width="19" height="10" hspace="0" vspace="0" border="0" alt="Tell a friend"></a></div>
				             				<div class="greyText" style="padding-bottom:5px;"><a href="<bean:write name="pto" property="uploadPicturesUrl"/>">Enter this Contest</a></div>
				             				<div class="greyText" style="padding-bottom:20px;"><a href="<bean:write name="pto" property="displayPollUrl"/>">Classic View</a></div>
				             				<%
												String newAction = fullVotingCentralUrl + "/p/c/castAVote" + fId + ".do?" + "action=voteFromContest";
											%>
											<div style="padding-bottom:5px;"><input type="Submit" value="Vote" onclick="this.form.action=<%=newAction%>; this.disabled=true;" class="btn" style="width:70px;height:30px;"
											<%												
												if (showPollAsDisabled.booleanValue()) {
											 %>
												disabled
											<%} %>
											/></div>											
										</td>			
								</logic:iterate>							
							    </html:form>								
								</TR>
							</TABLE>
					</td>
					</tr>
					</table>
	      			</div>							
					</logic:iterate>	
				</logic:present>				
	          <!-- content ends here -->	
			</td>									
		</tr>
	</table>
</div>