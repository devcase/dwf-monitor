<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dwf.devcase.com.br/dwf" prefix="dwf"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta name="decorator" content="${!empty param.decorator ? param.decorator : 'crud' }" />
</head>
<body>
<div dwf-toggle="autoreload" autoreload-href="${appPath}/${entityName}/list?decorator=table">
<dwf:dataGrid columns="name,healthCheckResult" var="entity">
<tr>
	<td class="dwf-ellipsis" style="max-width: 300px">
		<a href="${appPath}/${entityName}/${entity.id}"><dwf:autoFormat value="${entity}"/></a>
	</td>
	<td class="dwf-ellipsis" style="max-width: 300px">
		<span class="label ${entity.healthCheckResult ? 'label-success' : 'label-danger'}"><dwf:yesNo value="${entity.healthCheckResult}" /></span>
	</td>
</tr>
</dwf:dataGrid>
</div>

</body>
</html>