<table width="100%"  border="0" cellspacing="0" cellpadding="2">
	<% 
		int year = com.votingcentral.model.polls.PollTimeHelper.getInstance().getCurrentYear();
		String domContext = com.votingcentral.util.request.VCRequestHelper.getDomainAndContext(request);
		String privPolicyUrl = com.votingcentral.util.url.VCHelpPageURLHelper.getInstance().getPrivacyPolicyUrl(domContext);
	%>
  <tr>
    <td class="footer">&nbsp;&nbsp;<a href="<%=privPolicyUrl%>" class="footer">Privacy Policy</a> </td>
    <td align="right" class="footer">Copyright &copy; <%=year %> VotingCentral.com Inc. All rights reserved.&nbsp;&nbsp;</td>
  </tr>
</table>