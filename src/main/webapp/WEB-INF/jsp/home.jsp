<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dwf.devcase.com.br/dwf" prefix="dwf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>
	<h1>Home</h1>
		<a href="${appPath}/baseUser/" class="btn btn-lg btn-default home-menu-link"><spring:message code="domain.baseUser.plural"/> </a>
		<a href="${appPath}/monitoredResource/" class="btn btn-lg btn-default home-menu-link"><spring:message code="domain.monitoredResource.plural"/> </a>
</body>
</html>
