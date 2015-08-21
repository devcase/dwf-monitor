<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dwf.devcase.com.br/dwf" prefix="dwf" %>
<html>
	<head>
		<meta name="decorator" content="crud"/>
	</head>
	<body>
		<dwf:editForm>
			<dwf:inputText property="name" required="true"/>
			<dwf:inputText property="healthUrl" required="true"/>
			<dwf:inputNumber property="healthCheckPeriod" required="true"/>
			<dwf:inputNumber property="healthCheckPeriodOnError" required="false"/>
			<dwf:inputText property="notificationAddress" required="true"/>
		</dwf:editForm>
	</body>
</html>