<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<fb:iframe src=<%=fullVotingCentralUrl%>/home.do" frameborder='0' scrolling="auto" style="width:760px" resizable="true"></fb:iframe>
