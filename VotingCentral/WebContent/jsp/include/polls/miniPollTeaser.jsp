<% 
	double num = Math.random();
%>
 <tr style="background:#F2F2F2;">
 	<td style="padding: 0px 0px 5px 0px;">		
		<div style="text-align:center;width:60px;height:40px;border:1px solid;border-color:#666666;background:#FFFFFF">
			<logic:present name="pto" property="defaultImageUrlMax">											
				<%if(! isFacebook){ %>
					<a href="javascript:void(0);">
					<img src="<bean:write name="pto" property="defaultImageUrlThumb" />" border="0" hspace="0" onMouseOver=show_200("<bean:write name="pto" property="defaultImageUrlMax" />","<%=num%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)>
					</a>
				<%}else{ %>
					<a href="#"><img src="<bean:write name="pto" property="defaultImageUrlThumb" />" border="0" hspace="0"></a>
				<%}%>
			</logic:present>	
			<logic:notPresent name="pto" property="defaultImageUrlMax">
				<div style="color:#CCC;font-weight:bold;padding-top:5px;line-height:14px;">No<br>Image</div>
			</logic:notPresent>												
		</div>
	</td>
	<td style="padding:0px 0px 5px 0px;">
		<div id="bulletNone">
			<div>
				<a href="<bean:write name="pto"  property="displayPollUrl"/>" class="boldBlueLinks"><bean:write name="pto"  property="pollName"/></a>
			</div>
			<div>
				<table cellpadding="0" cellspacing="0" border="0" width="96%">
					<tr>
						<td align="left">- by 
							<a href="<bean:write name="pto" property="creatorUrl"/>"><bean:write name="pto" property="creatorUserName"/></a>
						</td>					
						<%--This line only show up if there is a time string exists, in case of recently ended polls, recently, started polls --%>
						<logic:present name="pto" property="timeAgoStr">
							<td  align="right"><bean:write name="pto"  property="timeAgoStr"/>&nbsp;ago</td>
						</logic:present>
					</tr>
				</table>
			</div>	
		</div>
	</td>
</tr>						
