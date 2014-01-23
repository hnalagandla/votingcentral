<%@page language="java"
	import="com.votingcentral.util.url.*, com.votingcentral.util.request.*, java.util.*, com.votingcentral.model.db.dao.to.*"%>
<td width="160" valign="top" style="padding:10px 0px 10px 0px;width:160px;height:100%;">
<% 
		String domContext = VCRequestHelper.getDomainAndContext(request);
%>
<table border="0" cellpadding="0" cellspacing="1" width="100%" height="100%" class="lightbluebg">
	<tr>
		<td align="center" valign="top" bgcolor="#FFFFFF">
			<div id="blueTitle">Poll Categories </div>
       					<div id="bulletBox">
				              <%
									List pollCatsUrls = VCURLHelper.getPollsByCategoriesURLs(domContext);
									for (Iterator itr = pollCatsUrls.iterator(); itr.hasNext();) {
											TextLinkDescTO tldto = (TextLinkDescTO) itr.next();
							  %>
							  			<div id="bulletSquareBlue"><a class="boldBlueLinks" href="<%=tldto.getHref()%>"><b><%=tldto.getText()%></b></a></div>
							  <%
									}
							   %>
      					</div>
		</td>									
	</tr>
</table>	
</td>