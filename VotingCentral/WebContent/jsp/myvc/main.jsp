<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);	
String formValue = fullVotingCentralUrl + "/p/myVC";
%>
<% if (!isFacebook){ %>
<script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<!-- script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/popup.js"></script -->
<script type="text/javascript" language="JavaScript">
run_after_body();
</script>
<%} %>
<%@page language="java"
	import="java.util.*, 
com.votingcentral.model.db.dao.to.*, com.votingcentral.model.enums.*, com.votingcentral.util.request.*"%>
    <html:form action="/p/myVC">
          <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          	<!-- tr>
          		<td colspan="2" align="right" bgcolor="#FFFFFF">
		          <table border="0" cellspacing="0" cellpadding="0">
        			  <tr>
				          <td><a href='<bean:write name="myVCForm"  property="editAccountInfoUrl"/>'><img src="<%=fullVotingCentralUrl%>/images/edit-profile.gif" width="16" height="10" hspace="5" vspace="0" border="0" alt="Edit Profile"></a></td>
          				  <td><a href='<bean:write name="myVCForm"  property="editAccountInfoUrl"/>'>Edit Profile</a></td>
          			 </tr>
          		  </table>
          		</td>
          	</tr -->
            <tr valign="top">
              <td width="50%" bgcolor="#FFFFFF">
				<div style="padding:0px 5px 10px 0px;"> 
				<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
					<tr>
						<td valign="middle" bgcolor="#FFFFFF">
						<div id="blueTitle"><bean:message key="my.vc.polls.voted" /></div>
				       	<div id="bulletBox">
						<!-- Content Start -->
									<logic:present name="myVCForm" property="pollsVoted" >								
						       			<table cellpadding="0" cellspacing="0" border="0" width="96%">	
										<logic:iterate id="pto" name="myVCForm" property="pollsVoted" type="com.votingcentral.pres.web.to.PollWTO">
											<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>								
										</logic:iterate>
										</table>								
									</logic:present>
									<div style="text-align:right;padding:5px 0px 5px 0px;"><a href="<%=fullVotingCentralUrl%>/p/myVC.do?action=allPollsVoted">More</a> ></div>
						<!-- Content End -->
						</div>								
						</td>									
					</tr>
				</table>
				</div> 
				</td>
              <td width="50%" bgcolor="#FFFFFF">
				<div style="padding:0px 0px 10px 5px;"> 
				<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
					<tr>
						<td valign="middle" bgcolor="#FFFFFF">
						<div id="blueTitle"><bean:message key="my.vc.polls.created" /></div>
				       	<div id="bulletBox">
						<!-- Content Start -->
									<logic:present name="myVCForm" property="pollsCreated" >			
						       			<table cellpadding="0" cellspacing="0" border="0" width="96%">	
										<logic:iterate id="pto" name="myVCForm" property="pollsCreated" type="com.votingcentral.pres.web.to.PollWTO"> 																															    
											<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>									 										
										</logic:iterate>								
										</table>
									</logic:present>
									<div style="text-align:right;padding:5px 0px 5px 0px;"><a href="<%=fullVotingCentralUrl%>/p/myVC.do?action=allPollsCreated">More</a> ></div>	
						<!-- Content End -->
						</div>								
						</td>									
					</tr>
				</table>
				</div> 
			</td>
            </tr>
            <tr valign="top">
              <td bgcolor="#FFFFFF">
			<div style="padding:0px 5px 10px 0px;"> 
			<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
				<tr>
					<td valign="middle" bgcolor="#FFFFFF">
					<div id="blueTitle"><bean:message key="my.vc.polls.contest.files" /></div>
			       	<div id="bulletBox">
					<!-- Content Start -->
									<logic:present name="myVCForm" property="contestFiles" >									
									<TABLE>
										<% int imgX = 0; %>
										<logic:iterate id="cto" name="myVCForm" property="contestFiles" type="com.votingcentral.model.db.dao.to.ContestEntryTO"> 									
											<TR>
												<TD><bean:write name="cto"  property="contestType"/></TD>										
												<TD>
													<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de">
														<tr>
															<td align="center" bgcolor="#FFFFFF"><a href="javascript:void(0);"><img src="<bean:write name="cto" property="minImageUrl" />" border="0" hspace="0" onMouseOver=show_200("<bean:write name="cto" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>
														</tr>
													</table>
												</TD>
												<logic:present name="cto" property="pollId" >
												<TD>																
													<a href="<%=fullVotingCentralUrl%>/displayPoll.do?action=showPoll&pollId=<bean:write name="cto" property="pollId"/>">Contest Link	</a>						
												</TD>
												</logic:present>
											</TR>
											<% imgX++; %>
										</logic:iterate>								
									</TABLE>	
									</logic:present>
									<div style="text-align:right;padding:5px 0px 5px 0px;"><a href="<%=fullVotingCentralUrl%>/p/myVC.do?action=allContestFiles">More</a> ></div>	
					
					<!-- Content End -->
					</div>								
					</td>									
				</tr>
			</table>
			</div> 
			</td>
              <td bgcolor="#FFFFFF">
				<div style="padding:0px 0px 10px 5px;"> 
				<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
					<tr>
						<td valign="middle" bgcolor="#FFFFFF">
						<div id="blueTitle"><bean:message key="my.vc.poll.comments" /></div>
				       	<div id="bulletBox">
						<!-- Content Start -->
									<logic:present name="myVCForm" property="pollComments" >
						       			<table cellpadding="0" cellspacing="0" border="0" width="96%">	
										<logic:iterate id="pto" name="myVCForm" property="pollComments" type="com.votingcentral.pres.web.to.PollWTO">															
											<%@ include file="/jsp/include/polls/miniPollTeaser.jsp"%>		
										</logic:iterate>								
										</table>																																						
									</logic:present>
									<div style="text-align:right;padding:5px 0px 5px 0px;"><a href="<%=fullVotingCentralUrl%>/p/myVC.do?action=allPollComments">More</a> ></div>	
						<!-- Content End -->
						</div>								
						</td>									
					</tr>
				</table>
				</div> 
 			</td>
            </tr>
        </table>
</html:form>
