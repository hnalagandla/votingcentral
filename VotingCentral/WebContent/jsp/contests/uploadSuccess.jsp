<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
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

<script language="JavaScript">

function submitForm(){
	document.forms[1].action = "<%=fullVotingCentralUrl%>/p/contestsDone.do?action=uploadFileDone";
	document.forms[1].submit();
	return true;
}
</script>

    <html:form action="/p/contestsDone">

	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td class="blueTitle1">Uploading Picture into Voting Central Contests Successful.</td>
              </tr>
              <tr>
                <td>
                	<p><bean:message key="upload.contest.file.success" /></p>
				    <TABLE border="0" cellspacing="1" cellpadding="2">
				    	<TR>			
				    		<td align="center" bgcolor="#FFFFFF"><a href="javascript:void(0);"><img src="<bean:write name="contestForm" property="minImageUrl"/>" border="0" hspace="0" onMouseOver=show_200("<bean:write name="contestForm" property="maxImageUrl" />","1",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>
				    	</TR>
						<TR>
							<td colspan="2"><a href="<bean:write name="contestForm" property="contestsMainURL" />"><bean:message key="upload.contest.file.success.contests.main" /></a></td>
						</TR>																
						<TR>
							<td colspan="2"><a href="<bean:write name="contestForm" property="vcHomePageURL" />"><bean:message key="upload.contest.file.success.go.home" /></a></td>
						</TR>			
						<tr>
							<td>To upload pictures into other contests click on the following.</td>						
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
                </td>
              </tr>

			</table>
	          </td>
	        </tr>
	    </table>
	    </td>
	  </tr>
	</table>  
    </html:form>