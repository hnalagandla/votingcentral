<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>

<%@ page import="com.votingcentral.env.EnvProps"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<%
	boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);    
	String vcwidth = "984";
 %>
 <% if (!isFacebook) {%>
<html>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="keywords" content="<tiles:insert attribute="metakeywords"/>">
<link href="<%=fullVotingCentralUrl%>/css/style.css" rel="stylesheet" type="text/css">
</head>
<body background="<%=fullVotingCentralUrl%>/images/bg_center.gif">
<% } else {
		vcwidth = "760";
%>
<style type="text/css">
<%@ include file="/css/style.css"%>
</style>	
<%} %>
<div id="wrap"> 
<table width="<%=vcwidth%>" height="100%"  border="0" cellpadding="0" cellspacing="0" align="center"> 
  <tr> 
    <td valign="top" bgcolor="#FFFFFF">
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    	        <%-- Header include starts here //--%>
				<tiles:insert attribute="header" />
            	<%-- Header include ends here //--%>
      		<tr>
        		<td>
        			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          				<tr>
							<%-- Left Nav include starts here //--%> 
							<tiles:insert attribute="leftSideBar" />					
							<%-- Left nav include ends here //--%>
							<% if (!isFacebook) {%>
								<td valign="top" style="padding:10px;">							
							<%} else { %>							
            					<td valign="top" style="padding:10px; width:600px;">
							<%} %>							            				
								<%-- Main content include starts here //--%> 
								<tiles:insert attribute="pageContent" />
								<%-- Main content include ends here //--%> 
							</td>
							<% if (!isFacebook) {%>
								<%-- Right side include starts here //--%> 				
	       						<tiles:insert attribute="rightSideBar" />			
								<%-- Right side include ends here //--%>
							<%} %>
          				</tr>
        			</table>
        		</td>
      		</tr>
    	</table>
    </td> 
  </tr>
  <tr>
    <td valign="bottom" bgcolor="#FFFFFF">
		<%-- Footer include starts here //--%> 
		<tiles:insert attribute="footer" />
		<%-- Footer include ends here //--%> 
	</td>
  </tr> 
</table> 
</div>
<% if (EnvProps.isProd()){
       if (!isFacebook) {
%>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-500184-1";
urchinTracker();
</script>
<%} else {%>
	<fb:google-analytics uacct="UA-500184-1" />
<%}
}
%>
<% if (!isFacebook) {%>
</body>
</html>
<%} %>