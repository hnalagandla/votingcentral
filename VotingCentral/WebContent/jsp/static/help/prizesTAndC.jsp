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
		String prizesFaqUrl = VCHelpPageURLHelper.getInstance().getPrizesFAQUrl(domContext);
		int activityDays = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getActivityPeriodDays();
		int weeks = activityDays / 7;
	
		String start = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getPeriodStartDateAsString();
		String end = com.votingcentral.model.bo.vcwinners.VCWinnersBO.getInstance().getPeriodEndDateAsString();
	%>
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">VotingCentral Prizes Terms And Conditions </div>
       	<div id="bulletBox">

			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				   The "VotingCentral Prizes" contest ("Contest") is organized by VotingCentral.com Inc. ("Organizers")					
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->
			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					Participation in Contest is purely voluntary. There is no 
					participation fee. To be eligible for the participation in 
					the Contest, participant must be a confirmed VotingCentral.com
					account holder.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					Organizers have the sole and absolute right to make changes in the 
					rules and/or the programs described at any time with or 
					without prior notice.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				To be eligible for prizes, users will have to register with VotingCentral.com 
				and perform various activites desribed in section 
				"What are the various site activities that I can perform to accumulate VC points?" 
				on the page <a href="<%=prizesFaqUrl%>">VotingCentral Prizes FAQ</a>. 
				To be eligible for the prizes, users would need to comply with the rules as set forth 
				herein. 
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					The VotingCentral.com database will keep a record of every user’s VC Score
				and determine the winner based on the scores in the database.
				The Organizers shall not be responsible for any 
				technical errors therein. Hackers will be disqualified. 
				If it is determined that you are creating fake profiles in 
				order to increase your VC Score, or if you are caught 
				cheating in any way, you will be disqualified. 
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					The prize will vary from time to time and will be sent to the mailing address 
					provided by each of the winners. Winners must update their VotingCentral.com accounts
					with valid mailing address, within 2 weeks of notification, 
					in order to recieve the prize. 
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
					Organizers' decision in regard to Winners shall be final and 
					binding. No communication or objection shall be entertained 
					in this regard.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Prizes received under Contest shall not be transferable and cannot 
				be exchanged for cash or otherwise. Prizes cannot be substituted 
				for other articles or gifts.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Organizers shall not be responsible for any cost, expense or 
				other liability whatsoever in relation to, arising from or 
				connected with Prizes. Organizers shall not be responsible for any 
				injury, loss, damage or other consequence whatsoever arising from, 
				suffered or incurred by Winner from or in relation to Prizes.
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->			
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Organizers offer no guarantee or warranty in respect of any of the 
				prizes. The manufacturers, services providers, supplier of the 
				Prizes alone shall be liable for any guarantee or warranty, if any.				
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->						
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				All costs and expenses for collecting, redeeming or utilizing the 
				Prize/s will be borne by the respective Winner and no claim of 
				whatsoever nature will be entertained by the Organizers in this 
				regard. Apart from the entitlement to the prize, the winner or 
				his/her legal heirs will have no other rights or claims against 
				the Organizers.				
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->						
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Any disputes arising from or in relation to Contest shall be 
				subject exclusively to jurisdiction in the state of Tamilnadu, India				
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->		
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Participation in this Contest implies acceptance of all the terms and 
				conditions of the Contest.				
				</td>
			</tr>
			</table>
			</div>
			<!-- QA ends here -->									
			<!-- QA starts here -->
			<div style="padding:10px 5px 0px 5px;">
			<table border="0" cellpadding="5" cellspacing="1" width="98%" class="darkgreybg">
			<tr><td id="greenTitle"></td></tr>
			<tr>
				<td bgcolor="#F2F2F2" style="padding:5px;">	
				Organizers will purchase the prizes from third party vendors and will 
				not be responsible for any defects therein.
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