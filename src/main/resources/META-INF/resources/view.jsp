<%@ include file="./init.jsp"%>
<%@ include file="./modales.jsp"%>


<portlet:resourceURL id="/emision/obtieneArchivos" var="datosArchivos" />
<portlet:resourceURL id="/emision/obtieneMensajes" var="datosMensajes" />
<portlet:resourceURL id="/emision/descargarDocumento" var="descargarDocumentoURL">

</portlet:resourceURL>
<portlet:resourceURL id="/emision/autoCompletado" var="autoCompletadoAsegurado" />
<portlet:resourceURL id="/emision/buscaPolizaAjax" var="buscaPolizaAjax" />

<portlet:actionURL var="buscaPolizaURL" name="/endoso/buscaPoliza" />
<portlet:actionURL var="poliza360URL" name="/endoso/poliza360" />

<liferay-ui:success key="exitoBusqueda" message="AdministrarPolizaPortlet.MjsExito" />
<liferay-ui:error key="error" message="AdministrarPolizaPortlet.MjsError" />
<liferay-ui:error key="errorConocido" message="${errorMsg}" />


<section>
	<div class="container">
		<div class="section-heading">
			<h1 class="title text-left">
				<liferay-ui:message key="AdministrarPolizaPortlet.tituloPrincipal" />
			</h1>

		</div>
		<div class="form-wrapper">
			<form class="mb-4" action="${buscaPolizaURL}" method="post" id="search-form">


				<div class="row">
					<div class="col-sm-6 col-lg-3">
						<div class="md-form form-group">
							<input id="poliza" type="text" name="poliza" class="form-control" value="${polizaBus}" >
							<label for="poliza">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDePoliza" />
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3">
						<div class="md-form form-group">
							<input type="text" name="endoso" class="form-control" value="${endosoBus}">
							<label for="endoso">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeEndoso" />
							</label>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3">

						<c:set var="estatusDisabled" value="-1" />
						<c:forEach items="${idsSelect}" var="idSelect">
							<c:set var="selectorTemp" value="${fn:split(idSelect, ',')}" />
							<c:if test="${selectorTemp[0] == filtro}">
								<c:set var="estatusDisabled" value="${selectorTemp[1]}" />
							</c:if>
						</c:forEach>
						<div class="md-form form-group">
							<select name="estatus" class="mdb-select">
								<c:set var="estatusAnterior" value="" />
								<c:if test="${estatusDisabled != -1}">
									<c:set var="estatusAnterior" value="disabled" />
								</c:if>
								<option value="0" ${estatusAnterior}>Todos</option>

								<c:forEach items="${catRespEstado}" var="estado">
									<c:set var="estatusAnterior" value="" />
									<c:if test="${estado.idCatalogoDetalle == estatusBus}">
										<c:set var="estatusAnterior" value="selected" />
									</c:if>
									<c:if test="${estatusDisabled != -1}">
										<c:set var="pagoAnterior" value="selected" />
										<c:if test="${estatusDisabled != estado.idCatalogoDetalle}">
											<c:set var="estatusAnterior" value="${estatusAnterior} disabled" />
										</c:if>
									</c:if>
									<option value="${estado.idCatalogoDetalle}" ${estatusAnterior}>${estado.valor}</option>
									<c:set var="estatusAnterior" value="" />
								</c:forEach>
							</select>
							<label for="estatus" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeEstatus" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="tipopago" class="mdb-select">
								<option value="0">Todos</option>
								<c:set var="pagoAnterior" value="" />
								<c:forEach items="${catTipoPago}" var="pago">
									<c:if test="${pago.idCatalogoDetalle == tipopagoBus}">
										<c:set var="pagoAnterior" value="selected" />
									</c:if>
									<option value="${pago.idCatalogoDetalle}" ${pagoAnterior}>${pago.valor}</option>
									<c:set var="pagoAnterior" value="" />
								</c:forEach>
							</select>
							<label for="tipopago" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeTipoPago" />
							</label>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-sm-12 col-lg-6">
						<div class="md-form form-group">
							<div class="row">
								<label for="inicio" class="active">
									<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeFechas" />
								</label>
								<div class="col">
									<input name="inicio" placeholder="Desde" type="text" id="creationDateInicio" class="form-control datepicker"
										value="${inicioBus}">
								</div>
								<div class="col">
									<input name="fin" placeholder="Hasta" type="text" id="creationDateFin" class="form-control datepicker"
										value="${finBus}">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="agente" class="mdb-select"
								searchable='<liferay-ui:message key="AdministrarPolizaPortlet.buscar" />'>
								<c:if test="${catRespAgente.size() > 1}">
									<option value="0">Todos</option>
								</c:if>
								<c:set var="estatusAnterior" value="" />
								<c:forEach items="${catRespAgente}" var="agente">
									<c:if test="${agente.idPersona == agenteBus}">
										<c:set var="agenteAnterior" value="selected" />
									</c:if>
									<option value="${agente.idPersona}" ${agenteAnterior }>${agente.nombre}</option>
									<c:set var="agenteAnterior" value="" />
								</c:forEach>
							</select>
							<label for="agente" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeAgente" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-3">
						<div class="md-form form-group">
							<input type="text" name="asegurado" id="aseguradoSiniestro" class="form-control" value="${aseguradoBus}">
							<input type="hidden" id="aseguradoIpPersona" name="aseguradoIpPersona" value="${aseguradoIdPersonaBus}">
							<label for="asegurado">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeAsegurado" />
							</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="tipoPoliza" class="mdb-select">
								<option value="0">Todos</option>
								<c:set var="tipoPolizaAnterior" value="" />
								<c:forEach items="${catRespPoliza}" var="poliza">
									<c:if test="${poliza.idCatalogoDetalle == tipoPolizaBus}">
										<c:set var="tipoPolizaAnterior" value="selected" />
									</c:if>
									<option value="${poliza.idCatalogoDetalle}" ${tipoPolizaAnterior}>${poliza.valor}</option>
									<c:set var="tipoPolizaAnterior" value="" />
								</c:forEach>
							</select>
							<label for="tipoPoliza" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeTipoDePol" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="moneda" class="mdb-select">
								<option value="0">Todas</option>
								<c:set var="monedaAnterior" value="" />
								<c:forEach items="${catRespMoneda}" var="moneda">
									<c:if test="${moneda.idCatalogoDetalle == monedaBus}">
										<c:set var="monedaAnterior" value="selected" />
									</c:if>
									<option value="${moneda.idCatalogoDetalle}" ${monedaAnterior}>${moneda.valor}</option>
									<c:set var="monedaAnterior" value="" />
								</c:forEach>
							</select>
							<label for="moneda" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeMoneda" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="producto" class="mdb-select">
								<option value="0">Todas</option>
								<c:set var="productoAnterior" value="" />
								<c:forEach items="${catRespProduc}" var="producto">
									<c:if test="${producto.idCatalogoDetalle == productoBus}">
										<c:set var="productoAnterior" value="selected" />
									</c:if>
									<option value="${producto.idCatalogoDetalle}" ${productoAnterior }>${producto.valor}</option>
									<c:set var="productoAnterior" value="" />
								</c:forEach>
							</select>
							<label for="producto" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeProducto" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-3">

						<div class="md-form form-group">
							<select name="ramo" class="mdb-select">
								<option value="0">Todos</option>
								<c:set var="ramoAnterior" value="" />
								<c:forEach items="${catRespRamo}" var="ramo">
									<c:if test="${ramo.idCatalogoDetalle == ramoBus}">
										<c:set var="ramoAnterior" value="selected" />
									</c:if>
									<option value="${ramo.idCatalogoDetalle}" ${ramoAnterior }>${ramo.valor}</option>
									<c:set var="ramoAnterior" value="" />
								</c:forEach>
							</select>
							<label for="ramo" class="mdb-main-label">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeRamo" />
							</label>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-sm-6 col-lg-3">
						<c:set var="isCancel" value="${filtro == 3 ? '' : 'hidden'}" />
						<div id="divMotCancelacion" class="md-form form-group" ${ isCancel }>
							<select name="motCancelacion" class="mdb-select">
								<option value="0">Todos</option>
								<c:forEach items="${catMotCancelacion}" var="cancel">
									<c:set var="seleccionado" value="${cancel.idCatalogoDetalle == motCancelacion ? 'selected' : ''}" />
									<option value="${cancel.idCatalogoDetalle}" ${seleccionado }>${cancel.valor}</option>
								</c:forEach>
							</select>
							<label for="ramo">
								<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeMotCancel" />
							</label>
						</div>

					</div>
					<div class="col-sm-6 col-lg-9">
						<a class="btn btn-pink float-right waves-effect waves-light" id="btnBuscar">
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloDeBuscar" />
						</a>
					</div>
				</div>
			</form>
		</div>


		<fmt:setLocale value="es_MX" />
		<div class="table-wrapper">
			<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tablaEmisiones">
				<thead>
					<tr>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDePoliza" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeAsegurado" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeProducto" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeEndoso" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeTipoDePoliza" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeEstatus" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeInicioVigencia" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeFinVigencia" />
						</th>
						
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeAgente" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeRamo" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeMoneda" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeFormaPago" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeFechaEmison" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDePrimaNeta" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeDerechos" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeRecargos" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDeIVA" />
						</th>
						<th>
							<liferay-ui:message key="AdministrarPolizaPortlet.tituloTablaDePrimaTotal" />
						</th>

						<th class="all" data-orderable="false"></th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${tablaPoliza}" var="pol">
						<tr>
							<td>${pol.poliza}</td>
							<td>${pol.cliente}</td>
							<td>${pol.producto}</td>
							<td>${pol.endoso}</td>
							<td>${pol.tipoPoliza}</td>
							<td>${pol.estado}</td>

							<td class="fecha" data-order="${pol.fecInicio}">${pol.fecInicio}</td>
							<td class="fecha" data-order="${pol.fecFin}">${pol.fecFin}</td>
							<td>${pol.agente}</td>
							<td>${pol.ramo}</td>
							<td>${pol.moneda}</td>
							<td>${pol.formaPago}</td>
							<td class="fecha" data-order="${pol.fecEmision}">${pol.fecEmision}</td>
							<td>
								<fmt:formatNumber value="${pol.primaNeta}" type="currency" />
							</td>
							<td>
								<fmt:formatNumber value="${pol.derechos}" type="currency" />
							</td>
							<td>
								<fmt:formatNumber value="${pol.recargos}" type="currency" />
							</td>
							<td>
								<fmt:formatNumber value="${pol.iva}" type="currency" />
							</td>
							<td>
								<fmt:formatNumber value="${pol.primaTotal}" type="currency" />
							</td>


							<td>
								<div class="actions-container dropleft">
									<button type="button" class="btn btn-outline-pink dropdown-menu-right px-3 py-2 waves-effect waves-light"
										data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
									</button>
									<div class="dropdown-menu animated fadeIn">
										<c:url var="myURL" value="${poliza360URL}">
											<c:param name="idproducto" value="${pol.idproducto}" />
											<c:param name="poliza" value="${pol.poliza}" />
											<c:param name="endoso" value="${pol.endoso}" />
										</c:url>
										<a id="pol360${pol.poliza}" class="dropdown-item" href="${myURL}" onclick="showLoader()">
											<i class="fas fa-user-edit mr-2"></i>
											<span>Administrar Póliza</span>
										</a>

										<form id="formCargaArchivo${pol.poliza}" action="${datosMensajes}" method="POST">
											<a class="dropdown-item" data-toggle="modal" href="#modal-messages"
												onclick="obtieneMensajes('${pol.poliza}','${pol.endoso}');">
												<i class="far fa-comment mr-2"></i>
												<span>Ver mensajes</span>
											</a>
										</form>
										<form id="formCargaArchivo${pol.poliza}" action="${datosArchivos}" method="POST">
											<a class="dropdown-item" data-toggle="modal" href="#modal-archives"
												onclick="obtieneDocs('${pol.poliza}','${pol.idCarpeta}');">
												<i class="far fa-file-alt mr-2"></i>
												<span>Ver archivos</span>
											</a>
										</form>
										<a class="dropdown-item" data-toggle="modal" href="#modal-short-help">
											<i class="far fa-question-circle mr-2"></i>
											<span>Ayuda</span>
										</a>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</section>


<div class="d-none">
	<c:url var="LCmyURL" value="${poliza360URL}">
		<c:param name="idproducto" value="${lcpol.idproducto}" />
		<c:param name="poliza" value="${lcpol.poliza}" />
		<c:param name="endoso" value="${lcpol.endoso}" />
	</c:url>
	<a id="LCpol360${lcpol.poliza}" class="dropdown-item" href="${LCmyURL}" onclick="showLoader()">
		<i class="fas fa-user-edit mr-2"></i>
		<span>Administrar Póliza</span>
	</a>

</div>

<script src="<%=request.getContextPath()%>/js/main.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/util.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js?v=${version}"></script>
<a id='dwnldLnk' style="display: none;" />
<script type="text/javascript">
	fechaHoy = ${hoy};

	var autoCompletadoAsegurado = '${autoCompletadoAsegurado}';
	var datosArchivos = '${datosArchivos}';
	var datosMensajes = '${datosMensajes}';
	var descargarDocumentoURL = '${descargarDocumentoURL}';
	var polizaLisCot = '${lcpol.poliza}';
</script>