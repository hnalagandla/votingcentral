<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="java.util.*, com.votingcentral.util.request.*, 
com.votingcentral.model.db.dao.to.*, com.votingcentral.model.enums.*"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>
<html:html>
<head>
	<title></title>
<SCRIPT language="JavaScript">	

 function submitForm(action) { 	
	document.forms[1].action = "<%=fullVotingCentralUrl%>/p/admin/contests/approveReject.do?action=" + action;
	document.forms[1].submit();	
	return true;
 }
 
 function approveAll() { 	
	var i = 0;
	var objRadios  = document.forms[1].elements["fileStatus"];
	var countobjRadios = objRadios.length;
	if( countobjRadios.length != null) {  
	alert("Count of radios :" + countobjRadios);
		for(i = 0; i < countobjRadios; i++){
			var theValue = document.forms[1].elements["fileStatus"][i].value;
		 	alert("value is " + theValue);		 	
			document.forms[1].elements["fileStatus"][i].value = "<%=ContestFileStatusEnum.APPROVED%>";
 		}
 	} else {
 		document.forms[1].elements["fileStatus"].value = "<%=ContestFileStatusEnum.APPROVED%>";;
 	} 
 }
 
  function rejectAll(labelAndDesc, vob) {
	var i = 0;
	var objRadios  = document.forms[1].elements["fileStatus"];
	var countobjRadios = objRadios.length;
	if( countobjRadios.length != null) {  
	alert("Count of radios :" + countobjRadios);
		for(i = 0; i < countobjRadios; i++){
			var theValue = document.forms[1].elements["fileStatus"][i].value;
		 	alert("value is " + theValue);		 	
			document.forms[1].elements["fileStatus"][i].value = "<%=ContestFileStatusEnum.REJECTED%>";
 		}
 	} else {
 		document.forms[1].elements["fileStatus"].value = "<%=ContestFileStatusEnum.REJECTED%>";;
 	} 
 }	
</SCRIPT>
</head>
<body>
    <html:form action="/p/admin/contests/approveReject">

	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td colspan="2" class="blueTitle1">Welcome to the Create Poll Panel</td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
			</table>
			
		    <TABLE  colspan="5">
			  <logic:present name="contestForm" property="<%=RequestParameterObjects.ALL_UPLOADED_FILES%>" >
			  	 <TR>
			  	 	<TD colspan="5"><A href="#" onclick="approveAll(); return false;">Approve All</A> 
			  	 		 <A href="#" onclick="rejectAll(); return false;">Reject All</A>
			  	 	</TD>
			  	 </TR>	
		       <logic:iterate id="cto" name="contestForm" property="<%=RequestParameterObjects.ALL_UPLOADED_FILES%>" type="com.votingcentral.model.db.dao.to.ContestFileTO"> 
		         <TR>
		         <TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/ShowImage.do?GZIP_NOT_ALLOWED=Y&iid=<bean:write name="cto"  property="fileId"/>&it=poll&pt=<%=PollTypeEnum.CONTEST%>"  height="200" width="200"/></TD>
				 <html:hidden name="cto"  property="fileId"/>         
		         <TD><bean:write name="cto"  property="userComments"/></TD>
		         <TD><bean:write name="cto"  property="contestType"/></TD>
		         <TD><bean:write name="cto"  property="mimeType"/></TD>         
		         <TD>
		         	<html:select property="fileStatus">
						 <html:optionsCollection property="contestFileStatuses" value="value" label="label" />						
					</html:select>
		         </TD>           
		         </TR> 
		       </logic:iterate> 
			  </logic:present>		
			  <TR>
				<TD></TD>
				<TD align="center"><html:button property="submitButton"
					value="Continue" onclick="submitForm('approveRejectFiles');" /></TD>
				<TD align="center"><html:button property="submitButton"
					value="Done" onclick="submitForm('approveRejectFilesDone');" /></TD>			
				</TR>		
			</TABLE>  
			
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>    
    </html:form>    
</body>
</html:html>
