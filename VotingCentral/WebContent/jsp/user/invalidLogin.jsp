<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Invalid Username and/or Password</div>
       	<div id="bulletBox">

			<%-- content start--%>
			<div><a href="<bean:write name="loginForm" property="loginUrl" />">Click Here, to try again....</a></div>		
			<br>
			<br>
		<div><a
			href="<bean:write name="loginForm" property="forgotPswdUrl" />">Forgot
		Password?</a></div>
		<div><a
			href="<bean:write name="loginForm" property="registrationUrl" />">Create
		a new Account</a></div>			
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>