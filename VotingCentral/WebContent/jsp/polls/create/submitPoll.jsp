<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);	
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);					
%>

<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.submitPollFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/submitPoll.do" + "?" + "action=" + method;
	document.submitPollFormBean.submit();	
	return true;
 }
</SCRIPT>

<html:form action="/p/submitPoll">
<%@ include file="/jsp/include/errorMessages.jsp"%>
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td align="center" valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle"><bean:message key="create.poll.submit.poll.sucess.congrats" /></div>
       	<div id="bulletBox">
		<!-- Content Start -->
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2">
                <hr>
                	<div class="txtLabelMediumBlue">Here is the Poll Link and URL.</div>
                <hr>	
                	<div>&nbsp;</div>                       			        
                	<div class="txtLabel"><bean:message key="create.poll.submit.poll.sucess.message1" /></div>                		                	
                	<div><a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="displayPollUrl" />"><bean:write name="submitPollFormBean" property="pollName" /></a></div>
                	<div>&nbsp;</div>                             
                	<div class="txtLabel"><bean:message key="create.poll.submit.poll.sucess.message2" /></div>
                	<div><html:text	name="submitPollFormBean" property="displayPollUrl" size="90"/></div>
                	<div>&nbsp;</div>     
                <hr>	
                	<div class="txtLabelMediumBlue">Share this poll on your Social Networks</div>
                <hr>	
                	<div>&nbsp;</div>     
                	<div>
						       <a  class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="facebookShareUrl" />">Click Here to Post this Poll on your Facebook Page</a>
					</div>
					<div> 	       
						       <a href="<bean:write name="submitPollFormBean" property="facebookShareUrl" />">
						       <img src="<%=fullVotingCentralImgUrl%>/images/facebook-share.jpg" hspace="5" vspace="0" border="0" alt="VotingCentral Poll on Facebook" height="30" width="125"></a>
					</div>
                	<div>&nbsp;</div>  
                	<div>
						       <a  class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="twitterUpdateUrl" />">Click Here to Post this Poll on your Twitter Page</a>
					</div>
					<div> 	       
						       <a href="<bean:write name="submitPollFormBean" property="twitterUpdateUrl" />">
						       <img src="<%=fullVotingCentralImgUrl%>/images/twitter_logo.png" hspace="5" vspace="0" border="0" alt="VotingCentral on Twitter" height="30" width="125"></a>
					</div>
					<div>&nbsp;</div>                  	
                	<div>
				       <a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="yahooBuzzUpUrl" />">Click Here to Buzz UP this Poll on Yahoo. Share this poll with all your friends on Yahoo.</a>
				    </div>
				    <div>   
				       <a href="<bean:write name="submitPollFormBean" property="yahooBuzzUpUrl" />">
				       <img src="<%=fullVotingCentralImgUrl%>/images/yahoo-buzz-up.png" hspace="5" vspace="0" border="0" alt="Buzz Up This VotingCentral Poll on Yahoo"></a>
					</div>			              	   	                	
					<div>&nbsp;</div>  
					<div class="txtLabel">Is your favorite Social Networking site not listed above ? No worries! Publish this poll to 50+ Social Networking and Content sites across the internet. Click on the following "SHARE" button and publish this poll across multiple sites.</div>
					<div style="padding:5px 5px 5px 5px;">						
						<% if (!isFacebook) {%>
							<script type="text/javascript">addthis_pub  = 'votingcentral';</script>
							<a href="http://www.addthis.com/bookmark.php" onmouseover="return addthis_open(this, '', '<bean:write name="submitPollFormBean" property="displayPollUrl"/>', '<bean:write name="submitPollFormBean" property="pollName"/>')" onmouseout="addthis_close()" onclick="return addthis_sendto()"><img src="	http://s9.addthis.com/button1-share.gif" width="125" height="16" border="0" alt="" /></a><script type="text/javascript" src="http://s7.addthis.com/js/152/addthis_widget.js"></script>		
						<% } else { %>
							<fb:share-button class="url" href="<bean:write name="displayPollUrl" property="displayPollUrl"/>" />							
						<% }  %>						
					</div>
					<div>&nbsp;</div>
					<hr>  			
                	<div class="txtLabelMediumBlue"><bean:message key="create.poll.submit.poll.sucess.do.next" /></div>
                	<hr>
			       <TABLE CELLPADDING="2" CELLSPACING="1" border="0">			       
			            <TR>
							<td colspan="2"><a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="createMorePollsUrl" />">Create More Polls</a></td>
						</TR>			       
			            <TR>
							<td colspan="2"><a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="contestsMainUrl" />">Checkout contests on VotingCentral</a></td>				
						</TR>
			       
			            <TR>
							<td colspan="2"><a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="myVCUrl" />">My VotingCentral</a></td>
						</TR>
			            <TR>
							<td colspan="2"><a class="boldBlueLinks" href="<bean:write name="submitPollFormBean" property="homePageUrl" />">VotingCentral Home Page</a></td>
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
