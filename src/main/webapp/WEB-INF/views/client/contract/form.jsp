<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.contract.form.label.code" path="code" />
	<acme:input-textbox code="client.contract.form.label.providerName" path="providerName" />
	<acme:input-textbox code="client.contract.form.label.customerName" path="customerName" />
	<acme:input-moment code="client.contract.form.label.instantiationMoment" path="instantiationMoment" />
	<acme:input-money code="client.contract.form.label.budget" path="budget" />
	<acme:input-textbox code="client.contract.form.label.goals" path="goals" />

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true }">
		<acme:input-select code="client.contract.form.label.project" path="project" choices="${projects}" readonly="true" />
		<acme:input-textbox code="client.contract.form.label.draftMode" path="draftMode" readonly="true"/>
			<acme:submit code="client.contract.form.button.update" action="/client/contract/update" />
			<acme:submit code="client.contract.form.button.delete" action="/client/contract/delete" />
			<acme:submit code="manager.project.form.button.publish" action="/client/contract/publish" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
		<acme:input-select code="client.contract.form.label.project" path="project" choices="${projects}" />
			<acme:submit code="client.contract.form.button.create" action="/client/contract/create" />
		</jstl:when>
	</jstl:choose>
</acme:form>
<acme:button code="client.contract.form.button.progress-logs" action="/client/progress-log/list?contractId=${id}"/>