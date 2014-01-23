<%@page import="java.util.*,com.votingcentral.util.*" %>
<% 
String fullVotingCentralUrl =  com.votingcentral.util.request.VCRequestHelper
						.getDomainAndContextWithProtocol(request);
%>
<script language="javascript" src="<%=fullVotingCentralUrl%>/scripts/stringUtil.js"></script>
<script language="Javascript">

function addEmoticon(str)
{
	for(var i=0; i<document.forms.length; i++) 
	{
		if(document.forms[i].message)
		{
			document.forms[i].message.value=document.forms[i].message.value + str;
			document.forms[i].message.focus();
			break;
		}
	}
}

function addTag(formObj, tagName, single)
{
	if(single)
	   str = "["+tagName+"]";
	else
	   str = "["+tagName+"][/"+tagName+"]";

	formObj.message.value=formObj.message.value + str;
	formObj.message.focus();
}

</script>
<table border="0" cellspacing="1" cellpadding="0" width="100%" align="center" bgcolor="#6699CC">
	<tr class="thread_message" height="20">
		<td width="25%">&nbsp;</td>
		<td align="center"><b>Have your say</b></td>
	</tr>
	<tr bgcolor="white">
		<td>
			<table cellspacing="0" cellpadding="0" border="0" bgcolor="#6699CC">
		    <%
		          HashMap hm=MessageProcessor.getEmoticonsMap();
			      Iterator it=hm.keySet().iterator();
			      it=hm.keySet().iterator();
			      while(it.hasNext()){
			%> 
			<tr bgcolor="white">
			      <%for(int i=0;i<5;i++){%>
			      	<td align="center">
			            <% if(it.hasNext()){
			               String key=(String)it.next();
			            %>
			               <a onclick="javascript:addEmoticon('<%=hm.get(key)%>');" style="cursor:hand;">
			                   <img src="<%=fullVotingCentralUrl%>/images/emoticons/<%=key%>.gif" alt="<%=key%>" border="0">
			               </a>
			               <%}else{%>&nbsp;<%}%>
			      	</td>
			      <%}%>
			</tr>
			<%}%>
			</table>
		</td>
		<td align="center">
			<input class="vc_button" type="button" value="I" onclick="javascript:addTag(this.form, 'i',false);">
			<input class="vc_button" type="button" value="B" onclick="javascript:addTag(this.form, 'b',false);">
			<input class="vc_button" type="button" value="URL" onclick="javascript:addTag(this.form, 'url',false);">
			<input class="vc_button" type="button" value="IMG" onclick="javascript:addTag(this.form, 'img',false);">
			<input class="vc_button" type="button" value="QUOTE" onclick="javascript:addTag(this.form, 'quote',false);">
			<input class="vc_button" type="button" value="CODE" onclick="javascript:addTag(this.form, 'code',false);">
			<input class="vc_button" type="button" value="HR" onclick="javascript:addTag(this.form, 'hr',true);">
			<input class="vc_button" type="button" value="NOSMILE" onclick="javascript:addTag(this.form, 'nosmile',false);">
			<br>
			<html:textarea property="message" rows="9" cols="65" style="border:#000000 2px solid;"/>
		</td>
	</tr>
     <tr bgcolor="#E0D8E0">
        <td>&nbsp;</td>
        <td align="center">
        	<button class="button" onclick="javascript:saveMessage(this.form);" style="cursor: hand;">Post Message</button>&nbsp;
        </td>
     </tr>
</table>