<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="java.util.*, com.votingcentral.model.db.dao.to.*, com.votingcentral.util.keyobjects.*"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<SCRIPT language="JavaScript">

 function submitForm(method) {
	document.forms[1].action = "<%=fullVotingCentralUrl%>" + "/search.do" + "?" + "action=" + method;
	document.forms[1].submit();	
	return true;
 }

 function tellAFriend() {
 	pollId = document.messageBoardForm.pollId.value;
 	var url = "<%=fullVotingCentralUrl%>/taf.do?tafCategory=POLL&tafCategoryId="+pollId;
	location.href=url;
 }
</SCRIPT>


<html:form action="/search">


<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
			<div id="blueTitle">Search Voting Central</div>
       		<div id="bulletBox" style="padding:10px;">
		<!-- Content Start -->		
			<div style="padding:20px 0px 20px 0px;">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%">
							<html:text property="searchString" maxlength="2048" size="55" />
						</td>
						<td style="padding-left:10px;"><html:button property="submitButton" value="Search" onclick="submitForm('search'); this.disabled=true;" styleClass="btn"/>
						</td>
					</tr>
				</table>
			</div>			
			<div>
				<logic:notEmpty name="advSearchFormBean" property="searchString">    	
					<div class="lightgreenbg" style="margin-bottom:20px;"><i>Searched for:</i> <strong><bean:write name="advSearchFormBean" property="searchString" /></strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <i>Result(s):</i> <strong><bean:write name="advSearchFormBean" property="resultsCount" /></strong></div>			
					<TABLE width="100%"  border="0" cellspacing="1" cellpadding="2">
					    <TBODY>
			    		<logic:present name="advSearchFormBean" property="results" >
							<logic:iterate id="searchResults" name="advSearchFormBean" property="results" type="com.votingcentral.model.db.dao.to.SearchResultTO">			
								<bean:define id="pto" name="searchResults" property="pollWto" type="com.votingcentral.pres.web.to.PollWTO"/>	
									<div style="padding-bottom:10px;">
										<div class="lightgreybg"><strong><i><%=pto.getPollType()%>:</i> <a href="<%=pto.getDisplayPollUrl()%>"><%=pto.getPollName()%></a></strong></div>
										<div class="txtStandard"><i>Description:</i> <%=pto.getPollDesc()%></div>
										<div class="txtStandard"><i>Created By:</i> <a href="<%=pto.getCreatorUrl()%>"><%=pto.getCreatorUserName()%></a> &nbsp;&nbsp;|&nbsp;&nbsp; <i>Views:</i><%=pto.getViewsCount()%></div>
										<bean:define id="textLinkDesc" name="searchResults" property="textsLinksDescs" type="java.util.List"/>				
											<logic:iterate id="line" name="textLinkDesc" type="com.votingcentral.model.db.dao.to.TextLinkDescTO" >
												<div class="txtStandard"><i><%=line.getDesc()%>:</i>	<a href="<%=line.getHref()%>"><%=line.getText()%></a></div>
											</logic:iterate>									
									</div>																								    
							</logic:iterate>			
						</logic:present>				
			    		</TBODY>	 
			 		</TABLE>	
				</logic:notEmpty>
			</div>		
		<!-- Content End -->
		</div>								
		</td>									
	</tr>
</table>
</div>
</html:form>
