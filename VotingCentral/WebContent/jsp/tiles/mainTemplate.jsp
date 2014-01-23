<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>
</head>
<tiles:insert name="header" />
<tiles:insert name="leftSideBar" />
<tiles:insert name="rightSideBar" />
<tiles:insert name="pageContent" />
<tiles:insert name="footer" />
