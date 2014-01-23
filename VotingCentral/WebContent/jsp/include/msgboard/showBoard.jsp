<%@ page language="java" import="com.votingcentral.forms.msgboard.*, com.votingcentral.util.session.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<link rel="stylesheet" type="text/css" href="<%=fullVotingCentralUrl%>/css/vc.css" />
<script language="JavaScript">
function arcSubject(subjectId)
{
   if(confirm("Are you sure you want to archive this thread?"))
   {
	document.subjectBoardForm.action="<%=fullVotingCentralUrl%>/msgboard/admin/archiveSubject.do?subjectId="+subjectId;
	document.subjectBoardForm.submit();
   }
}

function subjectMessages(subjectId)
{
	document.subjectBoardForm.action="<%=fullVotingCentralUrl%>/msgboard/showDiscussBoard.do?subjectId="+subjectId;
	document.subjectBoardForm.submit();
}

function createSubject()
{
	document.subjectBoardForm.action="<%=fullVotingCentralUrl%>/msgboard/createSubject.do"
	document.subjectBoardForm.submit();
}

</script>
<html:form action="/msgboard/showSubjectBoard.do" method="post">
<html:hidden property="pollId"/>
<table border=0 width="100%">
	<!--tr><td align="center"><b>Poll Description:<bean:write name="subjectBoardForm" property="pollsTO.pollDesc"/></b></td></tr-->
	<tr><td class="blueTitle1">Discussions:</td></tr>
	<tr>
		<td>
		<jsp:useBean id="clr" class="com.votingcentral.util.ColorAlternator"/>
		<jsp:setProperty name="clr" property="color" value="#E0D8E0" />
		<jsp:setProperty name="clr" property="color" value="white" />
		<table cellspacing="1" cellpadding="0" border="0" width="100%" bgcolor="#6699CC">
			<tr class="heading">
				<td>&nbsp;</td>
				<td>Subject</td>
				<td>Posts</td>
				<td>Created on</td>
				<td>Last Post</td>
				<td>Last Posted by</td>					
			</tr>
			<logic:notEmpty name="subjectBoardForm" property="subjects">
				<logic:iterate name="subjectBoardForm" property="subjects" id="sub" type="com.votingcentral.model.db.dao.to.SubjectTO">
					<tr class="thread_message" bgcolor="<jsp:getProperty name='clr' property='next'/>" >
						<bean:define id="subId" name="sub" property="subjectId" />
						<td>
						 <%if(request.isUserInRole("ADMIN")) { %>
							<a href="javascript:arcSubject('<%=subId%>');">
							<img src="<%=fullVotingCentralUrl%>/images/trash.gif" border="0" alt="Delete Subject"></a>
							<% } %>
						</td>
						<td><a href="javascript:subjectMessages('<%=subId%>');"><bean:write name="sub" property="subject" /></a></td>
						<td align="right"><bean:write name="sub" property="msgCount" format="#,###" /></td>
						<td><bean:write name="sub" property="createTimeStamp" format="dd MMM yyyy, HH:ss a" /></td>
						<td><bean:write name="sub" property="lastPostTimeStamp" format="dd MMM yyyy, HH:ss a" /></td>
						<td><bean:write name="sub" property="lastPostLoginName" /></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			<tr class="heading">
				<td colspan="3" align="left"></td>
				<td colspan="3" align="right"><a href="javascript:createSubject();" style="color:white;" >Start a New Subject</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br><br>
</html:form>