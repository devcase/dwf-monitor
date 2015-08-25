<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dwf.devcase.com.br/dwf" prefix="dwf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<dwf:resolveEL el="${entityName}" var="entity" />
<html>
<head>
<meta name="decorator" content="crud" />
<title>${service}</title>
</head>
<body>
	<dwf:viewPanel>
 		<dwf:outputText property="name"/>
 		<dwf:outputText property="healthUrl"/>
 		<dwf:outputText property="healthCheckPeriod"/>
 		<dwf:outputText property="healthCheckPeriodOnError"/>
 		<dwf:outputText property="healthCheckTimeout"/>
 	</dwf:viewPanel>
	<dwf:viewPanel title="none">
 		<dwf:outputText property="lastHealthCheck"/>
 		<dwf:outputText property="nextHealthCheck"/>
 		<dwf:outputText property="healthCheckResult"/>
 		<dwf:outputText property="lastError"/>
 		<dwf:outputText property="lastErrorDuration"/>
 		<dwf:outputText property="lastHttpCode"/>
 	</dwf:viewPanel>
</body>
</html>
