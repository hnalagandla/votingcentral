<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl = com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
boolean isFacebook = com.votingcentral.util.request.VCRequestHelper.isFacebook(request);

%>
<script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<!-- script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/popup.js"></script -->
<script type="text/javascript" language="JavaScript">run_after_body();</script>
<style type="text/css">
.border_b{
   border: 10px solid #808080;
   background-color:#FFFFFF;
   background-image: url('<%=fullVotingCentralUrl%>/scripts/img/lloading.gif');
}

.float{
   visibility: hidden;
   position: absolute;
   left: -3000px;
   z-index: 10;
   background-image: url('<%=fullVotingCentralUrl%>/scripts/img/lloading.gif');
}
</style>

<SCRIPT language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/textCounter.js"></script>	
<script language="JavaScript">

function submitForm(action) {
	document.forms[1].action = "<%=fullVotingCentralUrl%>" + "/p/contests/uploadByType.do" + "?" + "action=" + action;
	document.forms[1].submit();	
	return true;
 }
</script>	
	<%@ include file="/jsp/include/errorMessages.jsp"%>
    <html:form action="/p/contests/uploadByType" enctype="multipart/form-data" method="post">    
	<html:hidden property="contestType" />
	
	
<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<bean:define id="cType" name="contestForm" property="contestType" type="java.lang.String"/>
		<div id="blueTitle">Enter your picture into "<b><%=(String) cType%></b>" Contest</div>
		
       	<div id="bulletBox">
       		<div style="text-align:right;"><bean:message key="reg.newuser.heading.req.fields" /></div>
		<!-- Content Start -->
			    <TABLE border="0" cellspacing="1" cellpadding="2">
					<tr><td><bean:message key="upload.contest.file.prompt.attach.file" /></td></tr>
					<tr><td><html:file property="uploadFileName" size="25"/></td></tr>		
					<TR><TD><bean:message key="upload.contest.file.prompt.keywords" /></TD></TR>					
					<TR><TD><html:text property="keywords" /></TD></TR>					
					<TR><TD><bean:message key="upload.contest.file.prompt.comments" /></TD></TR>
					<TR><TD><html:textarea
								property="userComments" cols="35" rows="2"
								onkeydown="textCounter(this.form.userComments, this.form.remLenDesc, 128);"
								onkeyup="textCounter(this.form.userComments,this.form.remLenDesc,128);" />
					</TD></TR>
					<TR><TD>Characters remaining: <input type=box readonly name=remLenDesc size=3 value=128></TD></TR>		
					<tr>
						<td>
						<html:button property="submitButton" value="Upload Image" onclick="submitForm('uploadFile'); this.disabled=true;" styleClass="btn"/>&nbsp;		
						</td>
					</tr>
					<% int imgX = 0;%>					
					<logic:present name="contestForm" property="userEntries" >
					<tr>
						<td>Pictures you uploaded, not selected for the contest yet.</td>						
					</tr>										
					<tr>						
						<td>						
						<bean:define id="uEntries" name="contestForm" property="userEntries" type="java.util.List"/>
						<table cellpadding="0" cellspacing="0" border="0">
						<tr><td>
						<logic:iterate id="cto" name="uEntries" type="com.votingcentral.model.db.dao.to.ContestEntryTO" >            	            					
            					<table border="0" cellpadding="5" cellspacing="1" bgcolor="#F0F4D7" style="width:160px;height:117px;">
									<tr>
										<td align="center" bgcolor="#FFFFFF"><a href="javascript:void(0);"><img src="<bean:write name="cto" property="minImageUrl"/>" border="0" hspace="0" onMouseOver=show_200("<bean:write name="cto" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>&nbsp;&nbsp;&nbsp;									
									</tr>
								</table>
						<% imgX++;%>            					
		            	</logic:iterate>
		            	</td></tr>
		            	</table>
		            	</td>
					</tr>							
					</logic:present>					
					<logic:present name="contestForm" property="competeitorEntries" >
					<tr>
						<td>Pictures that you could be potentially competing with.</td>						
					</tr>										
					<tr>						
						<td>					
						<bean:define id="cEntries" name="contestForm" property="competeitorEntries" type="java.util.List"/>	
						<table cellpadding="0" cellspacing="0" border="0">
						<tr><td>
						<logic:iterate id="cto" name="cEntries" type="com.votingcentral.model.db.dao.to.ContestEntryTO" >            	
            					<table border="0" cellpadding="5" cellspacing="1" bgcolor="#F0F4D7" style="width:160px;height:117px;">
									<tr>
										<td align="center" bgcolor="#FFFFFF"><a href="javascript:void(0);"><img src="<bean:write name="cto" property="minImageUrl"/>" border="0" hspace="0" onMouseOver=show_200("<bean:write name="cto" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>&nbsp;&nbsp;&nbsp;		
									</tr>
								</table>
						<% imgX++;%>            					
		            	</logic:iterate>
		            	</td></tr>
		            	</table>
		            	</td>
					</tr>							
					</logic:present>
					<tr>
						<td>To enter pictures into other contests click on the following.</td>						
					</tr>					
					<tr>
						<td>
						<bean:define id="oUrls" name="contestForm" property="otherUploadUrls" type="java.util.List"/>
						<table cellpadding="0" cellspacing="0" border="0">
						<tr>
						<logic:iterate id="tldto" name="oUrls" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >            	
            					<td><a href="<%=tldto.getHref()%>"><%=tldto.getText()%></a> &nbsp; &nbsp; </td>
		            	</logic:iterate>
		            	</tr>
		            	</table>
		            	</td>
					</tr>		
			    </TABLE>
		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div> 
</html:form>