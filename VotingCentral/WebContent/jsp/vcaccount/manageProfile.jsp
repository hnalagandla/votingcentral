<%@ taglib uri="/WEB-INF/tld/struts-html" prefix="html"%>
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
	document.manageMyProfileForm.action = "<%=fullVotingCentralUrl%>" + "/p/user/manageProfile.do" + "?" + "action=" + method;
	document.manageMyProfileForm.submit();	
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
<html:form action="/p/user/manageProfile" enctype="multipart/form-data" method="post">
<div style="padding:0px 0px 0px 3px"> 
<table border="0" cellpadding="0" cellspacing="1" width="100%" class="lightbluebg">
	<tr>
		<td valign="middle" bgcolor="#FFFFFF">
		<div id="blueTitle">Manage your Profile</div>		
      	<div id="bulletNone" style="padding-top:10px;padding-bottom:10px;">
		<!-- Content Start -->
		<table cellpadding=2 cellspacing=1 border=0>
		<tr>
			<td align="right" style="width:130px;padding-top:3px;" valign="top">About Me</td>
			<td colspan="2">
					<html:textarea property="aboutMe" cols="35" rows="3" onkeydown="textCounter(this.form.aboutMe, this.form.remLenDesc, 512);" onkeyup="textCounter(this.form.aboutMe,this.form.remLenDesc,512);" /></br>
					<input type=box readonly name=remLenDesc size=3 value=512 style="border:none;color:gray;"><font color="gray">characters remaining.</font>
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-top:3px;" valign="top" class="txtLabel">Favorite Quote</td>
			<td colspan="2">
					<html:textarea property="favQuote" cols="35" rows="1" onkeydown="textCounter(this.form.favQuote, this.form.remLenDesc, 128);" onkeyup="textCounter(this.form.favQuote,this.form.remLenDesc,128);" /></br>
					<input type=box readonly name=remLenDesc size=3 value=128 style="border:none;color:gray;"><font color="gray">characters remaining.</font>
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-top:3px;" valign="top" class="txtLabel">Favorite URL</td>
			<td colspan="2">					<html:textarea property="favUrl" cols="35" rows="1" onkeydown="textCounter(this.form.favUrl, this.form.remLenDesc, 128);" onkeyup="textCounter(this.form.favUrl,this.form.remLenDesc,128);" /></br>
					<input type=box readonly name=remLenDesc size=3 value=128 style="border:none;color:gray;"><font color="gray">characters remaining.</font></td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Race</td>
			<td>
				<bean:define id="raceList" name="manageMyProfileForm" property="listOfRaces" type="java.util.Collection"/>								
				<html:select property="race">
					<html:options collection="raceList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="racePrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="racePrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Salary</td>
			
			<td>
				<bean:define id="salaryList" name="manageMyProfileForm" property="listOfSalaries" type="java.util.Collection"/>								
				<html:select property="salary">
					<html:options collection="salaryList" property="value" labelProperty="label" />
				</html:select>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="salaryPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="salaryPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Union Member</td>
			<td>
				<bean:define id="umList" name="manageMyProfileForm" property="listOfUnionMemberShip" type="java.util.Collection"/>								
				<html:select property="unionMember">
					<html:options collection="umList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="unionPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="unionPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Education</td>
			<td>
				<bean:define id="eduList" name="manageMyProfileForm" property="listOfEducation" type="java.util.Collection"/>								
				<html:select property="education">
					<html:options collection="eduList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="educationPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="educationPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Party</td>
			<td>
				<bean:define id="pList" name="manageMyProfileForm" property="listOfParties" type="java.util.Collection"/>								
				<html:select property="party">
					<html:options collection="pList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="partyPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="partyPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Ideology</td>
			<td>
				<bean:define id="iList" name="manageMyProfileForm" property="listOfIdeologies" type="java.util.Collection"/>								
				<html:select property="ideology">
					<html:options collection="iList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="ideologyPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="ideologyPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Religion</td>
			<td>
				<bean:define id="rList" name="manageMyProfileForm" property="listOfReligions" type="java.util.Collection"/>								
				<html:select property="religion">
					<html:options collection="rList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="religionPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="religionPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Zodiac</td>
			<td>
				<bean:define id="zList" name="manageMyProfileForm" property="listOfZodiacs" type="java.util.Collection"/>								
				<html:select property="zodiac">
					<html:options collection="zList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="zodiacPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="zodiacPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Marital Status</td>
			<td>
				<bean:define id="mList" name="manageMyProfileForm" property="listOfMaritalStatus" type="java.util.Collection"/>								
				<html:select property="maritalStatus">
					<html:options collection="mList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="maritalStatusPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="maritalStatusPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Number of Children</td>
			<td>
				<bean:define id="cList" name="manageMyProfileForm" property="listOfNumChildren" type="java.util.Collection"/>								
				<html:select property="numChildren">
					<html:options collection="cList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="numChildrenPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="numChildrenPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Smoker</td>
			<td>
				<bean:define id="sList" name="manageMyProfileForm" property="listOfSmokeTypes" type="java.util.Collection"/>								
				<html:select property="smokeType">
					<html:options collection="sList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="smokeTypePrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="smokeTypePrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Drinker</td>
			<td>
				<bean:define id="dList" name="manageMyProfileForm" property="listOfDrinkTypes" type="java.util.Collection"/>								
				<html:select property="drinkType">
					<html:options collection="dList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="drinkTypePrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="drinkTypePrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">Sexual Orientation</td>
			<td>
				<bean:define id="sexList" name="manageMyProfileForm" property="listOfSexOrient" type="java.util.Collection"/>								
				<html:select property="sexOrient">
					<html:options collection="sexList" property="value" labelProperty="label" />
				</html:select>
				<%-- The value of race privacy level should match the strings in --%>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="sexOrientPrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="sexOrientPrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" class="txtLabel">T-Shirt Size</td>
			<td>
				<bean:define id="shirtList" name="manageMyProfileForm" property="listOfShirtSizes" type="java.util.Collection"/>								
				<html:select property="shirtSize">
					<html:options collection="shirtList" property="value" labelProperty="label" />
				</html:select>
			</td>
			<td>
				<table cellpadding=0 cellspacing=0 border=0>
				<tr>
					<td valign="middle" style="padding-left:20px;"><html:radio property="shirtSizePrivacyLevel" value="PUBLIC" /></td>
					<td valign="middle">&nbsp;public&nbsp;&nbsp;</td>
					<td valign="middle" style="padding-left:20px;"><html:radio property="shirtSizePrivacyLevel" value="PRIVATE" /></td>
					<td valign="middle">&nbsp;private&nbsp;&nbsp;</td>
				</tr>
				</table>
			</td>
		</tr>		
		<tr>
			<td></td>				
			<td colspan=2 style="padding-top:10px;">
			<!-- html:button property="viewProfileUrl" value="View Profile" onclick="javascript:viewProfile();" styleClass="btn"/>&nbsp;&nbsp; -->
			<html:button property="update" value="Update" onclick="submitForm('update'); this.disabled=true;" styleClass="btn"/>
	
			</td>
		</table>
		<!-- Content End -->
		</div>			
		</td>									
	</tr>
</table>
</div>
</html:form>

