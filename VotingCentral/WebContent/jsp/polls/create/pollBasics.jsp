<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean" prefix="bean"%>
<%@taglib uri="/WEB-INF/tld/struts-logic" prefix="logic"%>
<%@page language="java"
	import="java.util.*, com.votingcentral.model.db.dao.to.*, com.votingcentral.util.session.*"%>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
String fullVotingCentralImgUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocolForImg(request);
%>	
<SCRIPT language="JavaScript">

 function submitForm(method) {

	document.pollBasicsFormBean.action = "<%=fullVotingCentralUrl%>" + "/p/createPoll/pollBasics.do" + "?" + "action=" + method;
	document.pollBasicsFormBean.submit();	
	return true;
 }

 function textCounter(field, countfield, maxlimit) {
  	if (field.value.length > maxlimit) {
		field.value = field.value.substring(0, maxlimit);
	} else {
		countfield.value = maxlimit - field.value.length;
	}
 }
	

</SCRIPT>
<%@ include file="/jsp/include/errorMessages.jsp"%>

<html:form action="/p/createPoll/pollBasics">
<html:hidden property="pollId"/>

<div style="padding:0px 0px 10px 0px;"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td align="center" valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle"><bean:message key="create.poll.heading" /></div>
       	<div id="bulletBox">
				<table width="642"  border="0" cellspacing="1" cellpadding="2">
                  <tr>
                    <td colspan="2" align="left" class="greyText"><i><bean:message key="create.poll.req.fields" /></i></td>
                  </tr>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
					<TR>
						<TD valign="top" width="25%"><bean:message key="create.poll.prompt.name" /></TD>
						<TD valign="bottom">
						<html:text size="35" property="pollName" onkeydown="textCounter(this.form.pollName, this.form.remLenName, 64);" onkeyup="textCounter(this.form.pollName,this.form.remLenName,64);" /><br/>
						<input type=box readonly name=remLenName size=2 value=64 style="border:none;color:gray;"><font color="gray">characters remaining.</font></TD>
					</TR>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
					<TR>
						<TD valign="top"><bean:message key="create.poll.prompt.desc" /></TD>
						<TD valign="bottom">
						<html:textarea property="pollDesc" cols="35" rows="3" onkeydown="textCounter(this.form.pollDesc, this.form.remLenDesc, 512);" onkeyup="textCounter(this.form.pollDesc,this.form.remLenDesc,512);" /></br>
						<input type=box readonly name=remLenDesc size=3 value=512 style="border:none;color:gray;"><font color="gray">characters remaining.</font></TD>
					</TR>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
					<TR>
						<TD valign="top"><bean:message key="create.poll.prompt.keywords" /></TD>
						<TD valign="bottom">
						<html:text size="35" property="keywords" onkeydown="textCounter(this.form.keywords, this.form.remLenKeywords, 255);" onkeyup="textCounter(this.form.keywords,this.form.remLenKeywords,255);" /><br/>
						<input type=box readonly name=remLenKeywords size=2 value=255 style="border:none;color:gray;"><font color="gray">characters remaining.</font><br>
						<bean:message key="create.poll.prompt.keywords.suggest" /></TD>
					</TR>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
					<TR>
						<TD valign="top">
						<bean:message key="create.poll.prompt.category.1" />
						<bean:define id="listOfSuperCats" name="pollBasicsFormBean" property="categories" type="java.util.Collection"/>
						</TD>
						<TD valign="top">
						<html:select property="pollCategory1"> 
				        <html:options collection="listOfSuperCats" property="value" labelProperty="label"/>
		        		</html:select>
		        		</TD>
					</TR>
					<TR>
						<TD colspan="2"><img src="<%=fullVotingCentralImgUrl%>/images/s.gif" width="1" height="5"></TD>
					</TR>
					<TR>
						<TD>&nbsp;</TD>
						<TD><html:button property="preview" value="Next" onclick="submitForm('upsertPoll'); this.disabled=true;" styleClass="btn"/></TD>
					</TR>
				</table>
		</div>								
		</td>									
	</tr>
</table>
</div>

</html:form>
