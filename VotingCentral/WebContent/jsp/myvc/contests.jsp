<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="java.util.*, 
com.votingcentral.model.db.dao.to.*, com.votingcentral.model.enums.*, com.votingcentral.util.request.*"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/imgPreview.js"></script>
<!-- script type="text/javascript" language="JavaScript" src="<%=fullVotingCentralUrl%>/scripts/popup.js"></script -->
<script type="text/javascript" language="JavaScript">run_after_body();</script>
    <html:form action="/p/myVC/allContests">
    
	<table width="100%"  border="0" cellspacing="0" cellpadding="1">
	  <tr>
	    <td class="lightbluebg">
	      <table width="100%"  border="0" cellspacing="0" cellpadding="5">
	        <tr>
	          <td bgcolor="#FFFFFF">
			<table width="100%"  border="0" cellspacing="1" cellpadding="2">
              <tr>
                <td class="blueTitle1"><bean:message key="my.vc.polls.contest.files" /></td>
              </tr>
              <tr>
                <td>
					<logic:present name="myVCForm" property="<%=RequestParameterObjects.CONTEST_FILES%>" >					
					<TABLE border="0" cellspacing="1" cellpadding="2">
					<% int imgX = 0; %>					
					<logic:iterate id="cto" name="myVCForm" property="<%=RequestParameterObjects.CONTEST_FILES%>" type="com.votingcentral.model.db.dao.to.ContestEntryTO"> 									
						<tr><td><bean:write name="cto"  property="contestType"/></td></tr>
						<TR>									
							<TD>
								<table border="0" cellpadding="5" cellspacing="1" bgcolor="#e5e1de">
									<tr>
										<td align="center" bgcolor="#FFFFFF"><a href="javascript:void(0);"><img src="<bean:write name="cto" property="minImageUrl" />" border="0" hspace="0" onMouseOver=show_200("<bean:write name="cto" property="maxImageUrl" />","<%=imgX%>",450,321) onMouseOut=hide_div() onerror=gag(this,100)></a></td>
									</tr>
								</table>
							</TD>
							<logic:present name="cto" property="pollId" >
								<TD>
									<a href="<%=fullVotingCentralUrl%>/displayPoll.do?pollId=<bean:write name="cto" property="pollId"/>">Contest Link </a>
								</TD>
							</logic:present>
						</TR>
					<% imgX++; %>	
					</logic:iterate>
					</TABLE>								
					</logic:present>	                
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
