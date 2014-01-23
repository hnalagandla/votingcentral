<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ page import="java.util.List, java.util.ArrayList" %>

<script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<!-- script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/popup.js"></script -->
<script type="text/javascript" language="JavaScript">run_after_body();</script>
<style type="text/css">
.border_b{
   border: 10px solid #808080;
   background-color:#FFFFFF;
   background-image: url('<%=fullVotingCentralImgUrl%>/scripts/img/lloading.gif');
}

.float{
   visibility: hidden;
   position: absolute;
   left: -3000px;
   z-index: 10;
   background-image: url('<%=fullVotingCentralImgUrl%>/scripts/img/lloading.gif');
}
</style>

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
		choices.add("k");
		choices.add("l");
		choices.add("m");
		choices.add("n");
		choices.add("o");
		choices.add("p");

		String answerType = "";
 %>
 

		<bean:define id="showPollAsPreview" name="showPollFormBean" property="showPollAsPreview" type="java.lang.Boolean"/>	          
            <%
				if (showPollAsPreview.booleanValue()) {
			 %>
			<div style="padding:0px 5px 10px 0px;"> 
			<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
				<tr>
					<td align="center" valign="middle" bgcolor="#FFFFFF">
					<div id="blueTitle">Poll Preview</div>
			       	<div id="bulletBox" style="padding-top:10px;">
					<!-- Content Start -->
            <%
				}
			 %>    
                
<%--Enclosing Table //--%>

<html:hidden property="pollId" />
<bean:define id="pwto" name="showPollFormBean" property="pwto" type="com.votingcentral.pres.web.to.PollWTO"/>
<div style="padding:0px 5px 10px 5px;">
	<table border="0" cellpadding="5" cellspacing="1" width="100%" class="darkgreybg">
	<tr>
		<td id="greenTitle"><bean:write name="showPollFormBean" property="pwto.pollName"/>
		<div align="right"><logic:notEmpty name="showPollFormBean" property="editPollUrl"><a href="<bean:write name="showPollFormBean" property="editPollUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btnEdit.gif" alt="Edit Poll" width="16" height="16" border="0" style="padding:0px 5px 5px 5px;">Edit Poll</a></logic:notEmpty></div>
		<div align="right"><logic:notEmpty name="showPollFormBean" property="extendPollUrl"><a href="<bean:write name="showPollFormBean" property="extendPollUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/btnEdit.gif" alt="Extend Poll" width="16" height="16" border="0" style="padding:0px 5px 5px 5px;">Extend Poll</a></logic:notEmpty></div>
		</td>
	</tr>
	<tr>
		<td bgcolor="#F2F2F2" style="padding:5px;">	
            			
	            		<div>
						<table border="0" cellspacing="0" cellpadding="0" width="98%">
						    <tr>
						    	<td>
								<div class="greyText">
			            			<strong>Created by:</strong> <a href="<bean:write name="showPollFormBean"  property="pollsByUserLink"/>"><bean:write name="showPollFormBean" property="pollCreatorUserName"/></a>
			            			&nbsp;&nbsp;<strong>Category:</strong> <bean:write name="showPollFormBean" property="pwto.pollCategory1"/>
			            			&nbsp;&nbsp;<strong>Views:</strong> <bean:write name="showPollFormBean" property="pwto.viewsCount"/>
			            			&nbsp;&nbsp;<strong>Votes:</strong> <bean:write name="showPollFormBean" property="pwto.pollTotalVotes"/>			            		
								</div>
						    	</td>
						    </tr>
						</table>
	            		</div>	
						<div class="greyText">
							<strong>Poll Active From:</strong> <i><bean:write name="showPollFormBean" property="pollStartTime" /></i> To <i><bean:write name="showPollFormBean" property="pollEndTime" /></i>
	            			<bean:define id="keywords" name="showPollFormBean" property="keywordUrls" type="java.util.List"/><br/>				
	            			<strong>Tags:</strong> 
								<logic:iterate id="keyword" name="keywords" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >            	
	            					<a href="<%=keyword.getHref()%>"><%=keyword.getText()%></a> &nbsp;
			            		</logic:iterate><br/>
							<strong>Description:</strong> <bean:write name="showPollFormBean" property="pwto.pollDesc"/>
						</div>
						<!-- Question / Answer section start -->
						<div>
		            	<table width="100%"  border="0" cellspacing="1" cellpadding="2">
		
		            	<tr>
		            		<td colspan="2">
							<bean:define id="showPollAsDisabled" name="showPollFormBean" property="showPollAsDisabled" type="java.lang.Boolean"/>					
							<bean:define id="showNextAsDisabled" name="showPollFormBean" property="showNextAsDisabled" type="java.lang.Boolean"/>								
								<% int imgX = 0;%>	
								<logic:iterate id="qData" name="showPollFormBean" property="questionDataObjects" 
										indexId="i"  type="com.votingcentral.model.polls.QuestionData">
									<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
									<tr>
									<bean:define id="qType" name="qData" property="answerType" type="java.lang.String" scope="page"/>	          
									<% answerType = qType; %>
									<logic:notEmpty name="qData" property="questionImageId">								
									<td valign="top" width="20%">
										<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de">
											<tr>
												<td align="center" bgcolor="#FFFFFF">
												    <% if (!isFacebook) {%> 
														<a href="javascript:void(0);"><img src="<bean:write name="qData" property="minImageUrl"/>" border="0" hspace="0" onMouseOver=show_200("<bean:write name="qData" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a>
													<% } else { %>
														<a href="#"><img src="<bean:write name="qData" property="minImageUrl"/>" border="0" hspace="0"></a>													
													<% }  %>
												</td>
											</tr>
										</table>						
									</td>
									</logic:notEmpty>
									<td class="txtquestion" valign="middle"><bean:write name="qData" property="question" /><html:hidden name="qData" property="questionId" /></td>		
									</TABLE>				
									<DIV><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="10"></DIV>	
									<% int k = 0;%>	
									<logic:notEqual name="qData" property="answerType" value="TEXTAREA">						
										<logic:iterate id="aData" name="qData" property="answerChoices" 
												indexId="j"  type="com.votingcentral.model.polls.AnswerChoice">
											<TABLE width="100%" cellspacing="0" cellpadding="0" border="0">
											<TR
												<%
													if (k % 2 == 0) {
												 %> 
												 bgcolor="#ECECEC"
												 <%
													}
													else {
												%>
												bgcolor="#ECECEC"
												<%
												}
												 %>										 
												 >
												<logic:notEmpty name="aData" property="answerImageId">

													<TD width="20%">
													<% imgX++; %>
													<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de" style="width:160px;height:117px;">
														<tr>
															<td align="center" valign="middle" bgcolor="#FFFFFF">
												    			<% if (!isFacebook) {%> 															
																	<a href="javascript:void(0);"><img src="<bean:write name="aData" property="minImageUrl" />" border="0" hspace="0" onMouseOver=show_200("<bean:write name="aData" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a>
																<% } else { %>
																	<a href="#;"><img src="<bean:write name="aData" property="minImageUrl" />" border="0" hspace="0"></a>																
																<% }  %>	
															</td>
														</tr>
													</table>
													</TD>
												</logic:notEmpty>	
												<TD WIDTH="20" valign="top" class="txtanswer">
													<%=choices.get(k)%>] 
													<% k++; %>
												</TD>	
												<TD  WIDTH="30" valign="top" style="padding-top:5px;">
												<logic:equal name="qData" property="answerType" value="RADIO">
													<html:radio name="showPollFormBean" property="answerId" value="<%=aData.getAnswerId()%>" disabled="<%=showPollAsDisabled.booleanValue()%>" />
												</logic:equal>	
												<logic:equal name="qData" property="answerType" value="CHECKBOX">										
													<html:checkbox name="showPollFormBean" property="answerId" value="<%=aData.getAnswerId()%>" disabled="<%=showPollAsDisabled.booleanValue()%>" style="border:none;"/>
												</logic:equal>	
												</TD>
												<TD valign="top" class="txtanswer">
													<bean:write name="aData" property="answer" />
												</TD>
											</TR>
											</TABLE>	
											<DIV><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="2"></DIV>					
										</logic:iterate>
								</logic:notEqual>
								</logic:iterate>
								<TABLE cellpadding="0" cellspacing="0" width="100%" border="0">	
								<TR>
									<TD><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
								</TR>
								<%-- Show the vote button only if it is checkbox or radio--%>
								<logic:notEqual name="qData" property="answerType" value="TEXTAREA">
								<TR>
									<TD align="center">
									<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
										<html:button property="submitButton" value="Vote" onclick="submitForm('/p/castAVote', 'castAVote'); this.disabled=true;" disabled="<%=showPollAsDisabled.booleanValue()%>" styleClass="btn" style="width:100px;height:30px;"/>
										</td>
										<td align="center" valign="middle" style="padding-left:10px;">
										<html:button property="submitButton" value="Next Poll" onclick="submitForm('/displayPoll', 'nextPoll'); this.disabled=true;" disabled="<%=showNextAsDisabled.booleanValue()%>" styleClass="btn" style="width:100px;height:30px;"/>
										</td>											
										<logic:notEmpty name="showPollFormBean" property="pollResultsUrl">
										<TD align="center" valign="middle" style="padding-left:10px;"><a href="<bean:write name="showPollFormBean"  property="pollResultsUrl"/>" style="font-size:14px;">See Results</a></TD>						
										</logic:notEmpty>									
									</tr>
									</table>
									</TD>		
								</TR>				

								</logic:notEqual>
							</TBODY>					
							</TABLE>
		            		</td>
		            	</tr>
		            	</table>
		            	</div>
						<!-- Question / Answer section end -->
						
						<!-- Comments section start -->
						<div style="padding:0px 0px 10px 0px;">
				  		<table cellspacing="1" cellpadding="0" border="0" width="100%">
				  			<%
								if (answerType.equalsIgnoreCase("TEXTAREA")) {
							 %>
							 <tr>
				  				<td><bean:message key="show.poll.post.answer" /></td>
				  			</tr>
				  			<%
								} else {
							 %>
				  			<tr>
				  				<td class="txtLabel"><bean:message key="show.poll.post.comment" /></td>
				  			</tr>
				  			<%
								}
							 %>
				  			<tr>
				  				<td><html:textarea name="showPollFormBean" property="comment" cols="72" rows="3" disabled="<%=showPollAsDisabled.booleanValue()%>" /></td>
				  			</tr>  
							<TR>
								<TD><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
							</TR>
							<%
								if (answerType.equalsIgnoreCase("TEXTAREA")) {
							 %>	
							<tr>
				  				<td><html:button property="submitButton" value="Post Answer" onclick="submitForm('/p/poll/addComments','fromPoll'); this.disabled=true;" disabled="<%=showPollAsDisabled.booleanValue()%>" styleClass="btn"/></td>
				  			</tr>			 
				         	 <tr>
				            	<td class="blueTitle1"><bean:message key="show.poll.show.answers.heading" /></td>
				          	</tr>  			
				  			<%
								} else {
							 %>
							<tr>
				  				<td align="center"><html:button property="submitButton" value="Comment" onclick="submitForm('/p/poll/addComments','fromPoll'); this.disabled=true;" disabled="<%=showPollAsDisabled.booleanValue()%>" styleClass="btn"/></td>
				  			</tr>			 
							<tr>
								<td class="blueTitle2">
								<% if (!isFacebook) {%>
									<div>"SHARE" This Poll On Your Social Networks. <div>
									<%-- <script type="text/javascript">addthis_pub  = 'votingcentral';</script>
									<a href="http://www.addthis.com/bookmark.php" onmouseover="return addthis_open(this, '', '<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>', '<bean:write name="showPollFormBean" property="pwto.pollName"/>')" onmouseout="addthis_close()" onclick="return addthis_sendto()">
										<img src="http://s9.addthis.com/button1-share.gif" width="125" height="16" border="0" alt="" /></a>
									<script type="text/javascript" src="http://s7.addthis.com/js/152/addthis_widget.js"></script>		--%>
								<% } else { %>
									<fb:share-button class="url" href="<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>" />							
								<% }  %>
							  <a href="<bean:write name="showPollFormBean"  property="tafUrl"/>"><img src="<%=fullVotingCentralImgUrl%>/images/tell-friend.gif" width="19" height="10" hspace="5" vspace="0" border="0" alt="Tell a friend"></a>
							  <a href="<bean:write name="showPollFormBean"  property="tafUrl"/>">Tell a Friend</a>							    
					          <a href="http://twitter.com/home/?status=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>" rel="nofollow" target="_blank" title="Log in to Twitter and post this Poll"><img src="images/bookmark/syn_twitter.gif"></a>
					          <a href="http://www.facebook.com/sharer.php?u=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;t=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Facebook"><img src="images/bookmark/syn_facebook.gif"></a>							  
					          <a href="http://digg.com/submit?phase=2&amp;URL='<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>'&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Digg"><img src="images/bookmark/syn_digman.gif"></a>
					          <a href="http://del.icio.us/post?url=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Del.icio.us"><img src="images/bookmark/syn_delicious.gif"></a>
					          <a href="http://reddit.com/submit?url=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Reddit"><img src="images/bookmark/syn_reddit.gif"></a>
					          <a href="http://www.furl.net/savedialog.jsp?p=1&amp;u=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;t=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Furl"><img src="images/bookmark/syn_furl.gif"></a>
					          <a href="http://www.google.com/bookmarks/mark?op=add&amp;bkmk=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Google"><img src="images/bookmark/syn_google.gif"></a>
					          <a href="http://myweb.yahoo.com/myresults/bookmarklet?&amp;u=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;t=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Yahoo"><img src="images/bookmark/syn_yahoo.gif"></a>
					          <a href="http://myjeeves.ask.com/mysearch/BookmarkIt?v=1.2&amp;t=webpages&amp;url=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Ask"><img src="images/bookmark/syn_ask.gif"></a>
					          <a href="http://slashdot.org/bookmark.pl?url=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>" rel="nofollow" target="_blank" title="Slashdot"><img src="images/bookmark/syn_slashdot.gif"></a>
					          <a href="http://www.blogger.com/blog-this.g?t=&amp;u=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;n=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Blogger"><img src="images/bookmark/syn_blogger.gif"></a>						
					          <a href="http://www.newsvine.com/_tools/user/login?popoff&redirect=<bean:write name="showPollFormBean" property="pwto.displayPollUrl"/>&amp;title=<bean:write name="showPollFormBean" property="pwto.pollName"/>" rel="nofollow" target="_blank" title="Newsvine"><img src="images/bookmark/syn_newsvine.gif"></a>
					          <a href="http://www.myspace.com/" rel="nofollow" target="_blank" title="Log in to MySpace and post this Poll"><img src="images/bookmark/syn_myspace.gif"></a>
							    </td>
							</tr>						 				  			
				         	 <tr>
				            	<td class="blueTitle1"><bean:message key="show.poll.show.comment.heading" /></td>
				          	</tr>   						 
							 <%
								}
							 %> 		  			
						  <logic:present name="showPollFormBean" property="pollComments" >   
				          		<logic:iterate id="pcto" name="showPollFormBean" property="pollComments" type="com.votingcentral.pres.web.to.PollCommentsWTO"> 
				          			<tr>
				            			<td><bean:write name="pcto"  property="comment"/></td>
				          			</tr> 	
				          			<tr>
				            			<td class="greyText" align="right">By <a href="<bean:write name="pcto"  property="userLink"/>"><bean:write name="pcto"  property="userName"/></a> - <bean:write name="pcto"  property="createTimestampStr"/></td>
				          			</tr> 
				  		  		</logic:iterate>
					      </logic:present>					      
				  		</table>
				  		</div>
						<!-- Comments section end -->
						
			</div>
		</td>
	</tr>
	</table>
</div>

<%--Enclosing Table //--%>
            <%
				if (showPollAsPreview.booleanValue()) {
			 %>	            
				<!-- Content End -->
					</div>								
					</td>									
				</tr>
			</table>
			</div> 
			<%
				}
			 %>						