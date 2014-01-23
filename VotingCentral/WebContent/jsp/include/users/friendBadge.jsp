
  	<div  style="width:60px;height:60px;text-align:center;padding:5px 0px 15px 0px;" align="center">
  		<table cellpadding="0" cellspacing="0" width="100%">
  			<tr>
  				<td align="center"><a href="<bean:write name="friend" property="userProfileUrl"/>">
  				<logic:notEmpty name="friend"  property="minImageUrl">
  					<img src="<bean:write name="friend" property="minImageUrl"/>" style="border:solid 1px #000;"/>
  				</logic:notEmpty>
				<logic:empty name="friend"  property="minImageUrl"> 
					<bean:define id="gender" name="friend" property="gender" type="java.lang.String"/>
					<% 
						if (gender.equalsIgnoreCase("M")) {
					%>
						<img src="<%=fullVotingCentralImgUrl%>/images/icon_male_40x40.gif" width="40" height="40">
					<%
						} else {
					 %>	
						<img src="<%=fullVotingCentralImgUrl%>/images/icon_female_40x40.gif" width="40" height="40">	
					<%
						}
					 %>									 
				</logic:empty>								
  				
  				</a></td>
  			</tr>
  			<tr>
  				<td align="center" class="txtSmall"><a href="<bean:write name="friend" property="userProfileUrl"/>"><bean:write name="friend" property="displayUserName"/></a></td>
  			</tr>
  		</table>
  	</div>

