<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>

<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Tell a Friend Success</div>
       	<div id="bulletBox">

			<%-- content start--%>
			<body bgcolor="White" text="3" link="3" vlink="3" alink="3 topmargin="0" leftmargin="0" marginwidth="0" marginheight="0">
			<p align="center">
			<br>An email was sent to your friend. <br>They should be recieving it in the next few minutes.
			<br><br>
			<a href="<bean:write name="tafForm"  property="tafUrl"/>">Click here to go back to original page.</a>
			<br><br>
			<a href="<bean:write name="tafForm"  property="orignalPageUrl"/>">Click here to send this page to another friend.</a>
			</p>
			<%-- content end --%>			
		</div>							
		</td>									
	</tr>
</table>
</div>