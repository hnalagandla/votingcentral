<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ page import="com.votingcentral.model.enums.VCChartTypeEnum"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);
%>
	<CENTER>
	<TABLE cellpadding="2" cellspacing="1" border="0">
	<TBODY>
		<html:hidden property="cct"/>
		<html:hidden property="ccp"/>		
  		<html:hidden property="pollId" />		
	<bean:define id="cct" name="showResultsFormBean" property="cct" />
		<% 
			String ct = (String) cct;
			VCChartTypeEnum currChart = VCChartTypeEnum.get(ct);
		%>		
		<%--
		<TR>			
			<TD><h3><bean:write name="showResultsFormBean" property="pollName"/></h3></TD>
		</TR>
		 --%>	
		<TR>			
			<TD>
			<table cellpadding="1" cellspacing="1" width="100%">
			<tr>
			<td><img src="<bean:write name="showResultsFormBean" property="chartImageUrl"/>"/></td>
			<td valign="top">
			<img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="26"><br/>
		        <div class="box1" style="width:120px;">
		        	Choose Chart Style:<br>
		        	<table cellpadding="0" cellspacing="0" border="0">      				
					<%
						 if (VCChartTypeEnum.PIE == currChart) {
					 %>
					 <tr>
					 <td nowrap><a href="<bean:write name="showResultsFormBean" property="defaultBarURL"/>"><img src="<%=fullVotingCentralImgUrl%>/images/ico-bar-01.gif" width="15" height="15" border="0"></a></td>
					 <td nowrap>&nbsp;<a href="<bean:write name="showResultsFormBean" property="defaultBarURL"/>">Bar</a>&nbsp;&nbsp;</td>
					 <td nowrap><img src="<%=fullVotingCentralImgUrl%>/images/ico-pie-02.gif" width="15" height="15"></td>
					 <td nowrap>&nbsp;Pie</td>
					 </tr> 		
					<%
						} else {
					 %>			
					 <tr>
					 <td nowrap><img src="<%=fullVotingCentralImgUrl%>/images/ico-bar-02.gif" width="15" height="15"></td>
					 <td nowrap>&nbsp;Bar&nbsp;&nbsp;</td>
					 <td nowrap><a href="<bean:write name="showResultsFormBean" property="defaultPieURL"/>"><img src="<%=fullVotingCentralImgUrl%>/images/ico-pie-01.gif" width="15" height="15" border="0"></a></td>
					 <td nowrap>&nbsp;<a href="<bean:write name="showResultsFormBean" property="defaultPieURL"/>">Pie</a></td>
					 </tr>
					<%
						}
					 %>
					 </table>
		        </div> 
		        <img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/>
		        <div class="box1" style="width:120px;">
		        	<table cellpadding="0" cellspacing="0" border="0"> 
						<TR>	
							<TD><a class="boldMediumBlueLinks" href="<bean:write name="showResultsFormBean" property="bigSmallUrl"/>" target="_blank"><bean:write name="showResultsFormBean" property="bigSmallUrlDesc"/></a></TD>						
						</TR>     				
						<logic:present name="showResultsFormBean" property="textDescAndUrls" >
							<logic:iterate id="urlAndDesc" name="showResultsFormBean" property="textDescAndUrls" type="com.votingcentral.model.db.dao.to.TextLinkDescTO"> 			
									<TR>	
											<TD><a class="boldMediumBlueLinks" href="<%=urlAndDesc.getHref()%>"><%=urlAndDesc.getText()%></a></TD>						
									</TR>
								</logic:iterate>
						</logic:present>	
					</table>
		        </div>
		        <img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/>
		        <%-- <div class="box1" style="width:120px;">
		        	Download Results:<br>
		        	<table cellpadding="0" cellspacing="0" border="0">      				
						<tr>	
							<td><a href="<bean:write name="showResultsFormBean" property="downloadAsTxtUrl"/>">Text</a></td>							
						</tr>
						<tr>	
							<td><a href="<bean:write name="showResultsFormBean" property="downloadAsCSVUrl"/>">CSV</a></td>						
						</tr>												
					 </table>
		        </div> --%>
		        <img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/>						        
		        <html:button property="submitButton"	value="Back To Poll" onclick="submitForm('/p/displayPollResults','showPoll'); this.disabled=true;" styleClass="btn"/><br/>		        
		        <img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/>						        
		        <html:button property="submitButton"	value="Next Poll" onclick="submitForm('/p/displayPollResults','nextPoll'); this.disabled=true;" styleClass="btn"/><br/>
		        <img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/>
		        <html:button property="submitButton" value="Home" onclick="vcHome(); this.disabled=true;" styleClass="btn"/><br/>
			</td>
			</tr>
			</table>			
			</TD>
		</TR>
		<%--TR>	
			<TD><a href="<bean:write name="showResultsFormBean" property="bigSmallUrl"/>" target="_blank"><bean:write name="showResultsFormBean" property="bigSmallUrlDesc"/></a></TD>						
		</TR>
		
			<logic:present name="showResultsFormBean" property="textDescAndUrls" >
				<logic:iterate id="urlAndDesc" name="showResultsFormBean" property="textDescAndUrls" type="com.votingcentral.model.db.dao.to.TextLinkDescTO"> 			
						<TR>	
								<TD><a href="<%=urlAndDesc.getHref()%>"><%=urlAndDesc.getText()%></a></TD>						
						</TR>
					</logic:iterate>
			</logic:present>				
		<TR>
			<TD>
			<TABLE CELLPADDING="2" CELLSPACING="1">
			<TD align="left"><html:button property="submitButton"	value="Next Poll" onclick="submitForm('/p/displayPollResults','nextPoll'); this.disabled=true;" styleClass="btn"/>
			</TD>			
			<TD align="left"><html:button property="submitButton" value="Home" onclick="vcHome(); this.disabled=true;" styleClass="btn"/>
			</TD>
			</TABLE>
			</TD>								
		</TR //--%>		

  <tr>
  	<td>
  		<table cellspacing="1" cellpadding="0" border="0">
  			<tr>
  				<td class="txtLabel"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"><br/><bean:message key="show.poll.post.comment" /></td>
  			</tr>
  			<tr>
  				<td><html:textarea name="showResultsFormBean" property="comment" cols="72" rows="3"/></td>
  			</tr>  
			<TR>
				<TD><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
			</TR>	
			<tr>
  				<td align="center"><html:button property="submitButton" value="Comment" onclick="submitForm('/p/results/addComments','fromResults'); this.disabled=true;" styleClass="btn"/></td>
  			</tr>
  			<tr>
							<td class="blueTitle2">
							<% if (!isFacebook) {%>
								"SHARE" the Poll Results On Your Social Networks. 
								<script type="text/javascript">addthis_pub  = 'votingcentral';</script>
								<a href="http://www.addthis.com/bookmark.php" onmouseover="return addthis_open(this, '', '<bean:write name="showResultsFormBean" property="displayPollResultsUrl"/>', '<bean:write name="showResultsFormBean" property="pollName"/>')" onmouseout="addthis_close()" onclick="return addthis_sendto()">
									<img src="http://s9.addthis.com/button1-share.gif" width="125" height="16" border="0" alt="" /></a>
								<script type="text/javascript" src="http://s7.addthis.com/js/152/addthis_widget.js"></script>		
							<% } else { %>
								<fb:share-button class="url" href="<bean:write name="showResultsFormBean" property="displayPollResultsUrl"/>" />							
							<% }  %>
						    <a href="<bean:write name="showResultsFormBean"  property="tafUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/tell-friend.gif" width="19" height="10" hspace="5" vspace="0" border="0" alt="Tell a friend"></a>
						    <a class="boldBlueLinks" href="<bean:write name="showResultsFormBean"  property="tafUrl"/>">Tell a Friend</a>
						    </td>
						</tr>			
		  <logic:present name="showResultsFormBean" property="pollComments" >   
          <tr>
            <td class="blueTitle1"><bean:message key="show.poll.show.comment.heading" /></td>
          </tr>
          <logic:iterate id="pcto" name="showResultsFormBean" property="pollComments" type="com.votingcentral.pres.web.to.PollCommentsWTO"> 
          <tr>
            <td><bean:write name="pcto"  property="comment"/></td>
          </tr> 	
          <tr>
            <td class="greyText" align="right">By <a href="<bean:write name="pcto"  property="userLink"/>"><bean:write name="pcto"  property="userName"/></a> - <bean:write name="pcto"  property="createTimestampStr"/></td>
          </tr> 
  		  </logic:iterate>
	      </logic:present>				
  		</table>
  	</td>
  </tr>		
	</TBODY>
	</TABLE>
	</CENTER>
