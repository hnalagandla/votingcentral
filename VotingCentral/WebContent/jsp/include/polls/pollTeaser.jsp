<tr>
	<%
		long imgX = System.currentTimeMillis();
	%>
	<logic:present name="pto" property="defaultImageUrlMax">
		<td valign="top" width="20%" style="padding:5px 5px 5px 0px;">
			<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de"	style="width:160px;height:117px;">
				<tr>
					<td align="center" bgcolor="#FFFFFF">
					<%if(! isFacebook){ %>
						<a href="javascript:void(0);">
							<img src="<bean:write name="pto" property="defaultImageUrlMin" />" 
							border="0" hspace="0" onMouseOver=show_200(
							"<bean:write name="pto" property="defaultImageUrlMax" />","<%=imgX%>",450,321) onMouseOut=hide_div() 
							onerror=gag(this,100)>
						</a>
					<% } else { %>
						<a href="#">
							<img src="<bean:write name="pto" property="defaultImageUrlMin" />" border="0" hspace="0">						
						</a>	
					<% } %>						
					</td>
				</tr>
			</table>
		</td>
	</logic:present>	
	<logic:notPresent name="pto" property="defaultImageUrlMax">
		<td valign="top" width="20%" style="padding:5px 5px 5px 0px;">
		<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de"
			style="width:160px;height:117px;">
			<tr>
				<td align="center" bgcolor="#FFFFFF"><img src="<%=fullVotingCentralImgUrl%>/images/blank.gif" width="150" height="112"></td>
			</tr>
		</table>
		</td>
	</logic:notPresent>	
	
	<td valign="top" style="padding:5px 0px 5px 0px;">
	<div><a
		href="<bean:write name="pto"  property="displayPollUrl"/>" class="boldBlueLinks" style="font-size:14px;"><bean:write
		name="pto" property="pollName" /><logic:present name="pto"
		property="timeAgoStr">-<bean:write name="pto"
			property="timeAgoStr" />&nbsp;ago</logic:present> </a>- by <a href="<bean:write name="pto"  property="creatorUrl"/>"><bean:write name="pto" property="creatorUserName"/></a></div>
	<div class="txtquestion"><bean:write name="pto"
		property="defaultQuestion" /></div>
	<div style="padding:2px 5px 2px 5px;"><bean:write name="pto"
		property="pollDesc" /></div>
	</td>
</tr>
