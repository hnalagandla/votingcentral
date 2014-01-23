<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ page import="com.votingcentral.env.EnvProps"%>
<html>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="keywords" content="<tiles:insert attribute="metakeywords"/>">
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<link href="<%=fullVotingCentralUrl%>/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
 <tiles:insert attribute="pageContent" />
<% if (EnvProps.isProd()) {%>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-500184-1";
urchinTracker();
</script>
<%} %>
</body>
</html>