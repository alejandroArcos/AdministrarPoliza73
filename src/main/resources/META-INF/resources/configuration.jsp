<%@ include file="./init.jsp"%>
<%@ page import="com.liferay.portal.kernel.util.Constants"%>


<liferay-portlet:actionURL portletConfiguration="<%=true%>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%=true%>" var="configurationRenderURL" />

<aui:form action="<%=configurationActionURL%>" method="post" name="fm">
	<aui:input name="<%=Constants.CMD%>" type="hidden" value="<%=Constants.UPDATE%>" />

	<aui:input name="redirect" type="hidden" value="<%=configurationRenderURL%>" />


	<div class="container">
		<div class="col-md-12">
			<div class="row ">

				<div class="form-group form-inline input-checkbox-wrapper">
					<label for="swichConfiguracionActiva">
						<input class="field toggle-switch" id="swichConfiguracionActiva" name="swichConfiguracionActiva" type="checkbox">
						<span class="toggle-switch-label">
							<h3 class="text-primary">Seleccione la configuración para los usuarios que pueden endosar</h3>
						</span>
						<span aria-hidden="true" class="toggle-switch-bar">
							<span class="toggle-switch-handle" data-label-off="Todos los usuarios" data-label-on="Usuarios de esta lista"> </span>
						</span>
					</label>
				</div>

			</div>
			<div class="row ">
				<div class="col-md-12">
					<div class="md-form">
						<label for="modalPolizaEnviarCorreo">
							<h3 class="text-primary">Agregar correo a la lista</h3>
						</label>
						<input type="email" name="modalPolizaEnviarCorreo" id="modalPolizaEnviarCorreo" class="form-control">
					</div>
				</div>
				<div class="col-md-11"></div>
				<div class="col-md-1">
					<button type="button" class="btn btn-success float-right">Agregar</button>
				</div>
			</div>
			<ul>
				<c:forEach items="${listEmails}" var="email">
					<li>${ email }</li>
				</c:forEach>
			</ul>
		</div>

		<aui:button-row>
			<aui:button type="submit"></aui:button>
		</aui:button-row>
</aui:form>