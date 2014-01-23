<html>
<title>Welcome to VotingCentral!</title>
<head>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<META HTTP-EQUIV="REFRESH" CONTENT="0; URL=<%=fullVotingCentralUrl%>/home.do">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP_EQUIV="Expires" CONTENT="-1">
</head>
<body BGCOLOR="#FFFFFF" TEXT="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
</body>
</html>
