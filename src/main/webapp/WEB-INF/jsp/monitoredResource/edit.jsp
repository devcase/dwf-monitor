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
			<dwf:inputNumber property="healthCheckTimeout" required="false"/>
			<dwf:inputNumber property="expectedHttpCode" required="false"/>
			<dwf:inputText property="expectedText" required="false"/>
			<dwf:inputNumber property="downtimeAlert" required="true"/>
			<dwf:inputNumber property="newAlertPeriod" required="true"/>
			<dwf:inputText property="alertMentions" required="false"/>
			<dwf:inputDayOfWeekTimeSchedule property="scheduledTimes" />
		</dwf:editForm>
	</body>
</html>