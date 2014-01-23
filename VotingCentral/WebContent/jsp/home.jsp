<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<%@page language="java" import="java.util.*, com.votingcentral.model.db.dao.to.*, com.votingcentral.model.polls.*"%>
<%@page language="java" import="java.util.List, java.util.ArrayList"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>

<!-- Paste Scripts here -->
<!-- link rel="stylesheet" type="text/css" href="<%=fullVotingCentralUrl%>/css/tabcontent.css?v=1.0" /-->
<style type="text/css">
<%@ include file="/css/tabcontent.css"%>
</style>
<script type="text/javascript" language="JavaScript">
<%@ include file="/scripts/tabcontent.js"%>
<%@ include file="/scripts/utils.js"%>
<%@ include file="/scripts/imgPreview.js"%>
run_after_body();
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
 %>

<SCRIPT language="JavaScript">

 function submitForm(action, method) {
	document.homeFormBean.action = "<%=fullVotingCentralUrl%>" + action + ".do" + "?" + "action=" + method;
	document.homeFormBean.submit();	
	return true;
 }
<% if (!isFacebook) {%>
setInterval('rotatePoll();',8000);
<%} %>
</SCRIPT>
<style type="text/css">
<% if (!isFacebook) {%>
.tabcontentstyle{ /*style of tab content oontainer*/
border: 1px solid #CCDEF1;
width: 642px;
margin-bottom: 10px;
padding: 10px;
}
<% } else { %>
.tabcontentstyle{ /*style of tab content container*/
border: 1px solid #CCDEF1;
width: 592px;
margin-bottom: 10px;
padding: 10px;
}
<% } %>
</style>
<CENTER>

			<%@ include file="/jsp/include/errorMessages.jsp"%>
			<logic:notEmpty name="homeFormBean"  property="tafHomePageUrl"> 
				<div style="padding:0px 0px 10px 0px;">
				<table border="0" cellpadding="5" cellspacing="1" width="100%" class="lightbluebg">
					<tr>
						<td align="right" valign="middle" bgcolor="#FFFFFF">
			       			<div style="text-align:right;">	
					            <table border="0" cellspacing="0" cellpadding="0">
						            <tr>
						            	<td><a href="<bean:write name="homeFormBean"  property="tafHomePageUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/tell-friend.gif" width="19" height="10" hspace="5" vspace="0" border="0" alt="Tell a friend"></a></td>
						            	<td><a href="<bean:write name="homeFormBean"  property="tafHomePageUrl"/>"><bean:message key="tell.a.friend" /></a></td>
						            </tr>
					            </table>
			      			</div>
						</td>									
					</tr>
				</table>
				</div>
			</logic:notEmpty>
			
			
          	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          	
          	
          	
          	
          	
          	
          	
          	
	          	<tr>
	          		<td colspan="2" bgcolor="#FFFFFF">
						<div style="padding:0px 0px 0px 0px;">
							<ul id="maintab" class="shadetabs">
							    <% int selected = 0; %>
							    <logic:present name="homeFormBean" property="featuredPolls" >
							        <logic:iterate id="pto" name="homeFormBean" property="featuredPolls" type="com.votingcentral.pres.web.to.PollWTO">
							            <li
							            <% if (selected == 0) {%>
							            class="selected"
							            <%} %>
							             onClick="javascript:nCurrentPoll=<%=selected%>;">
							            <a href="#" rel="<bean:write name="pto"  property="tabContentId"/>"><bean:write name="pto"  property="tabHeading"/></a>
							            </li>
							            <% selected++;%>
							        </logic:iterate>   
									<!-- li><a href="javascript:expandtab('maintab',3);">Tab 3</a></li -->      
							    </logic:present>    
							</ul>
							<div class="tabcontentstyle">
								<logic:present name="homeFormBean" property="featuredPolls" >
									<% int imgX = 0;%>
									<logic:iterate id="pto" name="homeFormBean" property="featuredPolls" type="com.votingcentral.pres.web.to.PollWTO" scope="request">
										<div id="<bean:write name="pto"  property="tabContentId"/>" class="tabcontent">
											<%@ include file="/jsp/include/polls/miniPoll.jsp"%>
										</div>
									</logic:iterate>
								</logic:present>
							</div>
						</div>
					</td>
				</tr>
				
				
				
				
				
					<script type="text/javascript">
					<%-- Start Tab Content script for UL with id="maintab" Separate multiple ids each with a comma. --%>
						initializetabcontent("maintab")
					</script>				
	            <tr valign="top">
	              <td bgcolor="#FFFFFF" width="50%" style="padding:0px 5px 0px 0px">
		            <%-- Recently started polls start --%>
		            <div style="padding:0px 0px 10px 0px">  
						<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
							<tr>
								<td valign="middle" bgcolor="#FFFFFF">
									<div id="blueTitle"><bean:message key="index.page.recently.started" /></div>
				       				<div id="bulletBox" style="padding-left:0px;padding-top:5px;margin-bottom:5px">
					       				<table cellpadding="0" cellspacing="0" border="0" width="96%">
						       				<% long imgX = 0; %>
											<logic:iterate id="pto" name="homeFormBean" property="recStartedPolls" type="com.votingcentral.pres.web.to.PollWTO">		
											<%		imgX++; %>
											<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>									 
											</logic:iterate>
										</table>	
				      				</div>
				      				<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="homeFormBean"  property="recStartedMoreUrl"/>"><strong>More ></strong></a></div>
								</td>									
							</tr>
						</table>
					</div>
	              <%-- Recently started polls end --%>
	              <%-- Recently ended polls start --%>
	            	<div style="padding:0px 0px 0px 0px">  
						<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
							<tr>
								<td align="center" valign="middle" bgcolor="#FFFFFF">
									<div id="blueTitle"><bean:message key="index.page.recently.ended" /></div>
									<div id="bulletBox" style="padding-left:0px;padding-top:5px;margin-bottom:5px">
				       					<table cellpadding="0" cellspacing="0" border="0" width="96%">								    
											<logic:iterate id="pto" name="homeFormBean" property="recEndedPolls" type="com.votingcentral.pres.web.to.PollWTO">		
											<% imgX++; %>
												<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>
											</logic:iterate>
										</table>	
				      				</div>	
									<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="homeFormBean"  property="recEndedMoreUrl"/>"><strong>More ></strong></a></div>									
								</td>									
							</tr>
						</table>
					</div>
	              <%-- Recently ended polls end --%>
	            </td>
	            <td bgcolor="#FFFFFF" width="50%" style="padding:0px 0px 0px 5px">
				  <%-- Most popular polls start --%>
					<div style="padding:0px 0px 10px 0px"> 
						<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
							<tr>
								<td align="center" valign="middle" bgcolor="#FFFFFF">
									<div id="blueTitle"><bean:message key="index.page.most.voted.polls" /></div>
									<div id="bulletBox" style="padding-left:0px;padding-top:5px;margin-bottom:5px">
				       					<table cellpadding="0" cellspacing="0" border="0" width="96%">
											<logic:iterate id="pto" name="homeFormBean" property="mostVotedPolls" type="com.votingcentral.pres.web.to.PollWTO">															
												<%
													imgX++;
												%>
												<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>									 									
											</logic:iterate>		
										</table>
			      					</div>
									<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="homeFormBean"  property="mostVotedMoreUrl"/>"><strong>More ></strong></a></div>
								</td>									
							</tr>
						</table>
					</div>
	              <%-- Most popular polls end --%>
				  <%-- Most emailed polls start --%>
					<div style="padding:0px 0px 0px 0px"> 
						<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
							<tr>
								<td align="center" valign="middle" bgcolor="#FFFFFF">
									<div id="blueTitle"><bean:message key="index.page.most.emailed.polls" /></div>
									<div id="bulletBox" style="padding-left:0px;padding-top:5px;margin-bottom:5px">
						       			<table cellpadding="0" cellspacing="0" border="0" width="96%">
											<logic:iterate id="pto" name="homeFormBean" property="mostEmailedPolls" type="com.votingcentral.pres.web.to.PollWTO">															
											<%
												imgX++;
											%>
												<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>									 																		
											</logic:iterate>	
										</table>
				      				</div>
									<div style="text-align:right;padding-right:5px;margin-bottom:5px;"><a href="<bean:write name="homeFormBean"  property="mostEmailedMoreUrl"/>"><strong>More ></strong></a></div>
								</td>									
							</tr>
						</table>
					</div>
	              <%-- Most emailed polls end --%>
	              </td>
	            </tr>
	        </table>	
	</CENTER>

