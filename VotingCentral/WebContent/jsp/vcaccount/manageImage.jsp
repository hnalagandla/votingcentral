<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<SCRIPT language="JavaScript">
 function submitForm(method) {
	document.manageMyImageForm.action = "<%=fullVotingCentralUrl%>" + "/p/user/manageImage.do" + "?" + "action=" + method;
	document.manageMyImageForm.submit();	
	return true;
 }
</SCRIPT>
<script type="text/javascript" language="JavaScript">run_after_body();</script>
<style type="text/css">
.border_b{
   border: 10px solid #808080;
   background-color:#FFFFFF;
   background-image: url('<%=fullVotingCentralImgUrl%>/scripts/img/lloading.gif');
}

.float{
   visibility: hidden;
   position: absolute;
   left: -3000px;
   z-index: 10;
   background-image: url('<%=fullVotingCentralImgUrl%>/scripts/img/lloading.gif');
}
</style>
<%@ include file="/jsp/include/errorMessages.jsp"%>
<html:form action="/p/user/manageImage" enctype="multipart/form-data" method="post">
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Profile Photo</div>		
		<!-- Content Start -->
				<div id="bulletNone" style="padding:20px 10px 10px 40px;">
				<logic:notEmpty name="manageMyImageForm" property="deleteImageUrl">
						<div style="text-align:center;" align="center">
						<table cellpadding=0 cellspacing=0 border=0 width="80%">
							<tr>
								<td width="30%" align="center"><a href="javascript:void(0);"><img src="<bean:write name="manageMyImageForm" property="minImageUrl"/>" border="0" hspace="0" onMouseOver=show_200("<bean:write name="manageMyImageForm" property="maxImageUrl" />","1",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>
								<td>
									<font color="red"><b>TIP:</b> To change profile photo, simply upload new photo.</font><br>
									<font color="red"><b><a href="<bean:write name="manageMyImageForm" property="deleteImageUrl"/>">Click Here to delete current profile photo.</a></b>
								</td>
							</tr>
						</table>
						</div>						
				</logic:notEmpty>
						<div style="text-align:center;padding-top:30px;">
						<table cellpadding="0" cellspacing="0" border="0" width="80%">
						<tr>
							<td class="txtLabel" width="30%" align="right">Upload photo: &nbsp;</td>
							<td>
						 <html:hidden property="imageId" />
						 <html:file  property="uploadFileName" size="50"/>
							</td>
						</tr>
						</table>
						 </div>
						 <div style="padding:10px 10px 10px 250px;">
						 <html:button property="upload" value="Upload" onclick="submitForm('upload'); this.disabled=true;" styleClass="btn"/>
						</div>
				</div>				
		<!-- Content End -->		
		</td>									
	</tr>
</table>
</div>
</html:form>

