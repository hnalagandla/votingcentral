<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
  	<div  style="text-align:center;margin:0px 5px 20px 0px;">
  		<table cellpadding="0" cellspacing="0" width="100%">
  			<tr>
  				<td align="center"><a href="<bean:write name="user" property="userProfileUrl"/>">
  				<logic:notEmpty name="user"  property="maxImageUrl">
  					<img src="<bean:write name="user" property="maxImageUrl"/>"  width="200" height="150" style="border:solid 1px #000;"/>
  				</logic:notEmpty>
				<logic:empty name="user"  property="maxImageUrl"> 
					<bean:define id="gender" name="user" property="gender" type="java.lang.String"/>
					<% 
						if (gender.equalsIgnoreCase("M")) {
					%>
						<img src="<%=fullVotingCentralImgUrl%>/images/icon_male_100x100.gif" width="100" height="100">
					<%
						} else {
					 %>	
						<img src="<%=fullVotingCentralImgUrl%>/images/icon_female_100x100.gif" width="100" height="100">	
					<%
						}
					 %>									 
				</logic:empty>								
  				
  				</a></td>
  			</tr>
  			<tr>
  				<td align="center" class="txtLabel"><a href="<bean:write name="user" property="userProfileUrl"/>"><bean:write name="user" property="displayUserName"/></a></td>
  			</tr>
  		</table>
  	</div>

