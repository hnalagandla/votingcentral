<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
String domContext = com.votingcentral.util.request.VCRequestHelper.getDomainAndContext(request);
String prizesUrl = com.votingcentral.util.url.VCHelpPageURLHelper.getInstance().getPrizesFAQUrl(domContext);
%>
<td width="160" valign="top" style="padding:10px 0px 10px 0px;width:160px;height:100%;">
	<table border="0" cellpadding="0" cellspacing="1" width="100%" height="100%" class="lightbluebg">
		<tr>
			<td align="center" valign="top" bgcolor="#FFFFFF">
				<!-- div id="blueTitle">Manage Your Account</div -->
				<a href ="<%=prizesUrl%>" >
					<img src="<%=fullVotingCentralImgUrl%>/images/win-free-ipod.jpg" width="158" height="551" border="0"/>
				</a>
			</td>									
		</tr>
	</table>	
</td>
