<%@page language="java"
	import="com.votingcentral.util.url.*, com.votingcentral.util.request.*"%>

	<%
		String domContext = VCRequestHelper.getDomainAndContext(request);
		String regUrl = VCURLHelper.getRegistrationUrl(domContext);
		String createPoll = VCURLHelper.getCreatePollUrl(domContext);
		String contestUrl = VCURLHelper.getContestsUploadUrl(domContext);
		String connectRequestUrl = VCURLHelper.getInviteToConnectNewUserUrl(domContext);
		String manageConnectsUrl = VCURLHelper.getManageConnectionsUrl(domContext);
		String manageRegInfoUrl = VCURLHelper.getMyRegInfoUrl(domContext);
		String manageProfileUrl = VCURLHelper.getMyProfileUrl(domContext);
		String vcWinnersUrl = VCURLHelper.getVacoWinnersUrl(domContext);
		String vcLeadersUrl = VCURLHelper.getVCPointsLeadersUrl(domContext);
		String prizesTAndCUrl = VCHelpPageURLHelper.getInstance().getPrizesTAndCUrl(domContext);
		String votingCentralUserUrl = VCURLHelper.getVCUserPublicProfileUrl(domContext, "VotingCentral");

		int activityDays = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getActivityPeriodDays();
		int weeks = activityDays / 7;
	
		String start = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getPeriodStartDateAsString();
		String end = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getPeriodEndDateAsString();

		int vcPointsRegister = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_REGISTER);
		int vcPointsVoting = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_VOTING);
		int vcPointsCreatePoll = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_CREATE_POLL);
		int vcPointsComments = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_COMMENTS);
		int vcPointsUploadPicture = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_UPLOAD_PICTURE);
		int vcPointsTAF = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_TELL_A_FRIEND);
		int vcPointsConnect = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_VC_CONNECT);
		int vcPointsAcceptReject = com.votingcentral.model.bo.vaco.VCVacoPointsBO.getInstance().getPointsByActivity(com.votingcentral.model.enums.VCActivityTypeEnum.VC_POINTS_CONNECT_ACCEPT_REJECT);

		boolean shouldCheckMinPoints = com.votingcentral.env.EnvProps.getProperty("prizes.win.should.check.minimum.points").equalsIgnoreCase("true");
		int MINIMUM_POINTS_FOR_WINNING = Integer.parseInt(com.votingcentral.env.EnvProps.getProperty("prizes.win.minimum.points.for.winning"));
	%>
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">VotingCentral Prizes FAQ</div>
       	<div id="bulletBox">
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">What prize am I going to win?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					VotingCentral is kicking it up a notch! We have offered T-Shirts as 
					the prize thus far, but for a limited time, we will be offering some hot items.
					 The prize for the current winning period is a <b>"New iPod shuffle 4GB"</b>!!
					We have other cool Apple products that we will be offering in future. 
					Check back for new offerings every <b><%=weeks %></b> weeks.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">How can I win prizes on VotingCentral?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				    Winning is very easy!
							Perform various activities on the site and for each activity
							you perform, you will gain pre-determined set of VC points. The user who earns the highest number of VC points
							during the <b>"Winning Period"</b> becomes the winner. Very simple! 
					<%--	<li>
							You can also become a prize winner, if you upload a picture into our various contests and the VotingCentral
							user community, votes your picture as the winner. There is a prize winner for each category in the Contests.
							So all you have to do is upload some great quality pictures and have various users vote for your picture.
						</li>						
					 --%>	
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->
			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">What is the current "Winning Period"?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					VC winners are announced every <strong><%=weeks %></strong> weeks. The current period starts from 
					<strong><%=start %></strong> and ends on <strong><%=end %></strong>. Only VC Points earned during the <b>"Winning Period"</b> 
					will be used in deriving the VC Winner. All Time VC Points earned will not be used in deriving the winner.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">What are the various site activities I can perform and how many VC points do I gain for these?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				<table border="1" cellpadding="5" cellspacing="1" width="100%">
					<tr>
						<td>
							Successfully Joining the VotingCentral Community. <strong><a class="boldBlueLinks" href="<%=regUrl%>">Click Here</a> to Join</strong>.
						</td>					
						<td width="20%">
							<%=vcPointsRegister %> VC point(s).
						</td>						
					</tr>					
					<tr>
						<td>
							"Tell a Friend" about a Poll or a Photo Contest.
						</td>					
						<td>
							<%=vcPointsTAF %> VC point(s).
						</td>						
					</tr>							
					<tr>
						<td>
							Voting in a Poll or a Photo Contest.
						</td>					
						<td>
							<%=vcPointsVoting %> VC point(s).
						</td>						
					</tr>						
					<tr>
						<td>
							Creating a Poll. <strong><a class="boldBlueLinks" href="<%=createPoll%>">Click Here</a> to Create a Poll.</strong>
						</td>					
						<td>
							<%=vcPointsCreatePoll %> VC point(s).
						</td>						
					</tr>											
					<tr>
						<td>
							Commenting on a Poll or a Photo Contest.
						</td>					
						<td>
							<%=vcPointsComments %> VC point(s).
						</td>						
					</tr>											
					<tr>
						<td>
							Uploading a picture into a Photo Contest. <br><strong><a class="boldBlueLinks" href="<%=contestUrl%>">Click Here</a> to upload a picture into a contest.</strong>
						</td>					
						<td>
							<%=vcPointsUploadPicture %> VC point(s).
						</td>						
					</tr>																			
					<tr>
						<td>
							Sending out a VC Connect request to another VC user or a new user. <br><strong><a class="boldBlueLinks" href="<%=connectRequestUrl%>">Click Here</a> to send out a connect request.</strong>
						</td>					
						<td>
							<%=vcPointsConnect %> VC point(s).
						</td>						
					</tr>											
					<tr>
						<td>
							Act on a VC Connect request, by either accepting/rejecting the request. <br><strong><a class="boldBlueLinks" href="<%=manageConnectsUrl%>">Click Here</a> to manage your pending connect requests.</strong>
						</td>					
						<td>
							<%=vcPointsAcceptReject %> VC point(s).
						</td>						
					</tr>											
				</table>					
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">Is there a way to know if I am close to winning the prize?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				The TOP 10 VotingCentral users with highest number of VC points in a given winning period are shown at 
				<a class="boldBlueLinks" href="<%=vcLeadersUrl%>">VCPoints Leaders</a> page. If you show up on
				this page, you are in the potential winners circle. But you are not guaranteed to win unless you have the highest number of VC points 
				at the time <b>"Winning Period"</b> ends.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			<% if (shouldCheckMinPoints) {%>			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">Is there a minimum number of VC Points one has to earn for winning the prize?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					A minimum of <%=MINIMUM_POINTS_FOR_WINNING %> points are required for becoming a winner. So if no one earns the minimum numbers of
					points, it is possible that we may have no winner of the prize.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->	
			<%} %>			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">How will I be informed if I become a winner?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					An email will be sent to the email address that you used during the registration, announcing that
					you are the "VotingCentral Winner!". Hence make sure, the email address is always current. The subject of the Winner email will be "VotingCentral Winner!". The sender of
					the email will be <b>info@votingcentral.com</b>. Make sure you add <b>info@votingcentral.com</b> to your address book in 
					order to assure correct delivery of emails.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">What should I do to claim my prize if I become a winner?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					An email will be sent to the email address that you used during the registration, announcing that
					you are the "VotingCentral Winner!". The subject of the email will be "VotingCentral Winner!".
					You are then required to fill your mailing address at <strong><a class="boldBlueLinks" href="<%=manageRegInfoUrl%>">Manage My Reg. Info</a></strong>, within 5 days
					of recieving the "VotingCentral Winner!" email.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">Do I have to pay for the Shipping and Handling or any other fees to receive my prize?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				No. We cover all costs of shipping and handling and other expenses, if any. The prize will be
				mailed to you at no cost. It is really free!
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">Are users from all over the world eligible for participation?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Yes. Users from ALL the countries in the world are eligible to participate.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->						
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">By the way, who are some past winners ?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				<strong><a class="boldBlueLinks" href="<%=vcWinnersUrl%>">Click Here</a> to look at some previous winners</strong>.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->				
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">How can I know the VC Points of a given VotingCentral User?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Click on the link of a VotingCentral User Name, for e.g. <b><a class="boldBlueLinks" href="<%=votingCentralUserUrl%>">VotingCentral</a></b> and you will be shown that user's page. Look for the <b>"Current VC Points:"</b>. These are the
				points that the user earned during the <b>"Winning Period"</b>. Only the <b>"Current VC Points:"</b> will be used in deriving the VC Winner. 
				The <b>"All Time VC Points:"</b> will not be used in deriving the winner. 
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle">What are the Terms and Conditions of this Prize Give Away?</td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				<strong><a class="boldBlueLinks" href="<%=prizesTAndCUrl%>">Click Here</a> for official Terms and Conditions that govern the prize give away. </strong>.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->						
		</div>							
		</td>									
	</tr>
</table>
</div>