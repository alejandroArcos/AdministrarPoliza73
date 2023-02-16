<%@ include file="./init.jsp"%>
<%@ include file="./modales.jsp"%>

<portlet:resourceURL id="/endosos/histoCambios" var="getHistoCambiosURL" cacheability="FULL" />
<portlet:resourceURL id="/emision/obtieneArchivos" var="datosArchivosURL" cacheability="FULL" />
<portlet:resourceURL id="/emision/descargarDocumento" var="descargarDocURL" cacheability="FULL" />
<portlet:resourceURL id="/emision/redirige" var="redirigeURL" cacheability="FULL" />

<c:set var="version" scope="session" value="desa.23062020.1605" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css?v=${version}">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css?v=${version}">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print-pantalla.css?v=${version}">

<fmt:setLocale value="es_MX" />

<section>
	<div class="container">
		<div class="section-heading">
			<h1 class="title text-left">
				<liferay-ui:message key="AdministrarPolizaPortlet.vista360" />
				${v360.poliza}
			</h1>
			<h3 class="title text-left">${v360.cliente}</h3>
			
		</div>
		<div class="form-wrapper">

			<div id="tabs">
				<ul>
					<li class="tb1">
						<a href="#tabs-1">Datos Generales</a>
					</li>
					<li class="tb2 oculP">
						<a href="#tabs-2">
							Ubicaciones <span class="noUbT"> ${totUbicaCotiza} </span>
						</a>
					</li>
					<li class="tb3 oculP">
						<a href="#tabs-3">Historial de cambios</a>
					</li>
					<li class="tb4 oculP">
						<a href="#tabs-4">Modificar</a>
					</li>
				</ul>
				<div id="tabs-1">
					<div class="row">
						<%-- <div class="col-md-12"> --%>
							<div class="col-md-6">
								<div class="md-form form-group">
									<input type="text" id="360generalesRFC" name="360generalesRFC" class="form-control" value="${v360.rfc}"
										disabled>
									<label for="360generalesRFC">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.rfc" />
									</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="md-form form-group">
									<input type="text" id="360generalesCodClient" name="360generalesCodClient" class="form-control"
										value="${v360.codigo}" disabled>
									<label for="360generalesCodClient" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.codClient" />
									</label>
								</div>
							</div>
						<%-- </div> --%>
					</div>
					<hr />
					<div class="row">
						<%-- <div class="col-md-12"> --%>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalestipoMov" name="360generalestipoMov" class="form-control"
										value="${v360.tipoMovimiento}" disabled>
									<label for="360generalestipoMov">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.tipoMov" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesMoneda" name="360generalesMoneda" class="form-control"
										value="${v360.moneda}" disabled>
									<label for="360generalesMoneda" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.moneda" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesVigencia" name="360generalesVigencia" class="form-control"
										value="${v360Vigencia}" disabled>
									<label for="360generalesVigencia" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.vigencia" />
									</label>
								</div>
							</div>
						<%-- </div> --%>
					</div>
					<div class="row">
						<%-- <div class="col-md-12"> --%>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesAgente" name="360generalesAgente" class="form-control"
										value="${v360.agente}" disabled>
									<label for="360generalesAgente">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.agente" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesFormPago" name="360generalesFormPago" class="form-control"
										value="${v360.formaPago}" disabled>
									<label for="360generalesFormPago" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.formaPago" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesExpedicion" name="360generalesExpedicion" class="form-control"
										value="${v360Expedicion}" disabled>
									<label for="360generalesExpedicion" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.expedicion" />
									</label>
								</div>
							</div>
						<%-- </div> --%>
					</div>
					<div class="row" ${isEmpresarial ? '' : 'hidden' }>
						<div class="col-md-12">
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesGiro" name="360generalesGiro" class="form-control" value="${v360.giro}"
										disabled>
									<label for="360generalesGiro">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.giro" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesSubgiro" name="360generalesSubgiro" class="form-control"
										value="${v360.subgiro}" disabled>
									<label for="360generalesSubgiro" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.subgiro" />
									</label>
								</div>
							</div>
							<div class="col-md-4">
								<div class="md-form form-group">
									<input type="text" id="360generalesDetallesubgiro" name="360generalesDetallesubgiro" class="form-control"
										value="${v360.detalleSubgiro}" disabled>
									<label for="360generalesDetallesubgiro" class="active">
										<liferay-ui:message key="AdministrarPolizaPortlet.360Generales.detallesubgiro" />
									</label>
								</div>
							</div>
						</div>
					</div>
					<div id="tbl" class="row">

						<div class="col-md-12">

							<!--Accordion wrapper-->
							<div class="accordion md-accordion" id="accordionEx" role="tablist" aria-multiselectable="true">

								<!-- Accordion card -->
								<div class="card ">

									<!-- Card header -->
									<div class="card-header btn-blue modificado" role="tab" id="headingOne1">
										<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseOne1"
											aria-expanded="false" aria-controls="collapseOne1">
											<h5 class="mb-0">
												<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.titulo" />
												<i class="fas fa-angle-down rotate-icon"></i>
											</h5>
										</a>
									</div>

									<!-- Card body -->
									<div id="collapseOne1" class="collapse " role="tabpanel" aria-labelledby="headingOne1"
										data-parent="#accordionEx">
										<div class="card-body">
											<div class="table-wrapper">
												<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tblCoberturas">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.cob" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.valAse" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.prima" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.coberturas}" var="cob">
															<tr>
																<td>${cob.cobertura}</td>
																<td>
																	<fmt:formatNumber value="${cob.valorAsegrado}" type="currency" />
																</td>
																<td>
																	<fmt:formatNumber value="${cob.prima}" type="currency" />
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<table class="table table-striped table-bordered imp" style="width: 100%; display: none;"
													id="tblCoberturas-imp">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.cob" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.valAse" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.coberturas.prima" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.coberturas}" var="cob">
															<tr>
																<td>${cob.cobertura}</td>
																<td>
																	<fmt:formatNumber value="${cob.valorAsegrado}" type="currency" />
																</td>
																<td>
																	<fmt:formatNumber value="${cob.prima}" type="currency" />
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>

								</div>
								<!-- Accordion card -->

								<!-- Accordion card -->
								<div class="card">

									<!-- Card header -->
									<div class="card-header btn-blue modificado" role="tab" id="headingTwo2">
										<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseTwo2"
											aria-expanded="false" aria-controls="collapseTwo2">
											<h5 class="mb-0">
												<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.titulo" />
												<i class="fas fa-angle-down rotate-icon"></i>
											</h5>
										</a>
									</div>

									<!-- Card body -->
									<div id="collapseTwo2" class="collapse" role="tabpanel" aria-labelledby="headingTwo2"
										data-parent="#accordionEx">
										<div class="card-body">
											<h3>
												<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.tituloPen" />
											</h3>
											<div class="table-wrapper">
												<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tblPagPen">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noDocumento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noRecibo" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.primaTotal" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.fecVencimiento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.Estatus" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.pagosPendientes}" var="cob">
															<tr class="${cob.estatus == 'VENCIDO' ? 'rowVencido' : '' }">
																<td>${cob.noDocumento}</td>
																<td>${cob.noRecibo}</td>
																<td>
																	<fmt:formatNumber value="${cob.primaTotal}" type="currency" />
																</td>
																<td>${cob.moneda}</td>
																<td>${cob.fecVencimiento}</td>
																<td>${cob.estatus}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<table class="table table-striped table-bordered imp" style="width: 100%; display: none;" id="tblPagPen-imp">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noDocumento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noRecibo" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.primaTotal" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.fecVencimiento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.Estatus" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.pagosPendientes}" var="cob">
															<tr class="${cob.estatus == 'VENCIDO' ? 'rowVencido' : '' }">
																<td>${cob.noDocumento}</td>
																<td>${cob.noRecibo}</td>
																<td>
																	<fmt:formatNumber value="${cob.primaTotal}" type="currency" />
																</td>
																<td>${cob.moneda}</td>
																<td>${cob.fecVencimiento}</td>
																<td>${cob.estatus}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
											<br />
											<h3>
												<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.tituloPag" />
											</h3>
											<div class="table-wrapper">
												<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tblPagPen">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noDocumento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noRecibo" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.primaTotal" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.fecVencimiento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.Estatus" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.pagosPagados}" var="cob">
															<tr class="${cob.estatus == 'VENCIDO' ? 'rowVencido' : '' }">
																<td>${cob.noDocumento}</td>
																<td>${cob.noRecibo}</td>
																<td>
																	<fmt:formatNumber value="${cob.primaTotal}" type="currency" />
																</td>
																<td>${cob.moneda}</td>
																<td>${cob.fecVencimiento}</td>
																<td>${cob.estatus}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<table class="table table-striped table-bordered imp" style="width: 100%; display: none;" id="tblPagPen-imp">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noDocumento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.noRecibo" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.primaTotal" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.fecVencimiento" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.pagos.Estatus" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.pagosPagados}" var="cob">
															<tr class="${cob.estatus == 'VENCIDO' ? 'rowVencido' : '' }">
																<td>${cob.noDocumento}</td>
																<td>${cob.noRecibo}</td>
																<td>
																	<fmt:formatNumber value="${cob.primaTotal}" type="currency" />
																</td>
																<td>${cob.moneda}</td>
																<td>${cob.fecVencimiento}</td>
																<td>${cob.estatus}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<!-- Accordion card -->

								<!-- Accordion card -->
								<div class="card" ${isAgente? 'hidden' : '' }>

									<!-- Card header -->
									<div class="card-header btn-blue modificado" role="tab" id="headingThree3">
										<a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseThree3"
											aria-expanded="false" aria-controls="collapseThree3">
											<h5 class="mb-0">
												<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.Titulo" />
												<i class="fas fa-angle-down rotate-icon"></i>
											</h5>
										</a>
									</div>

									<!-- Card body -->
									<div id="collapseThree3" class="collapse" role="tabpanel" aria-labelledby="headingThree3"
										data-parent="#accordionEx">
										<div class="card-body">
											<div class="table-wrapper">
												<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tblPagPen">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.Siniestro" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.ubicacion" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.fecOcurrencia" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.monto" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.Estatus" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.cobertura" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.beneficiario" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.descripcion" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.siniestros}" var="siniestro">
															<tr>
																<td>${siniestro.siniestro}</td>
																<td>${siniestro.ubicacion}</td>
																<td>${siniestro.fecOcurrencia}</td>
																<td>${siniestro.moneda}</td>
																<td>
																	<fmt:formatNumber value="${siniestro.monto}" type="currency" />
																</td>
																<td>${siniestro.estatus}</td>
																<td>${siniestro.cobertura}</td>
																<td>${siniestro.beneficiario}</td>
																<td>${siniestro.descripcion}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<table class="table table-striped table-bordered imp" style="width: 100%; display: none;" id="tblPagPen-imp">
													<thead>
														<tr>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.Siniestro" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.ubicacion" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.fecOcurrencia" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.moneda" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.monto" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.Estatus" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.cobertura" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.beneficiario" />
															</th>
															<th>
																<liferay-ui:message key="AdministrarPolizaPortlet.tbl.siniestros.descripcion" />
															</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${v360.siniestros}" var="siniestro">
															<tr>
																<td>${siniestro.siniestro}</td>
																<td>${siniestro.ubicacion}</td>
																<td>${siniestro.fecOcurrencia}</td>
																<td>${siniestro.moneda}</td>
																<td>
																	<fmt:formatNumber value="${siniestro.monto}" type="currency" />
																</td>
																<td>${siniestro.estatus}</td>
																<td>${siniestro.cobertura}</td>
																<td>${siniestro.beneficiario}</td>
																<td>${siniestro.descripcion}</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
								<!-- Accordion card -->
							</div>
							<!-- Accordion wrapper -->
							<button type="button" id="imprimePantalla" class="btn btn-pink waves-effect waves-light float-right"
								onclick="window.print();">Imprimir</button>
						</div>
					</div>
				</div>
				<div id="tabs-2">
					<div id="tabsUbic">
						<ul>
							<c:forEach var="i" begin="1" end="${totUbicaCotiza}">
								<li>
									<a href="#ubicacion-${i}">
										<i class="fa fa-map-marker" aria-hidden="true"></i>
										&nbsp;${UbicacionesTot.ubicaciones[i-1].idubicacion}
									</a>
								</li>
							</c:forEach>
						</ul>
						<c:set var="noUb" scope="session" value="0" />
						<c:set var="UbicacionesTot" value="${UbicacionesTot}" scope="request" />
						<c:set var="niveles" value="${niveles}" scope="request" />
						<c:forEach items="${htmlUb}" var="ub" varStatus="i">
							<div id="ubicacion-${i.index + 1}">
								<c:choose>
									<c:when test="${isEmpresarial == true}">
										<jsp:include page="ubicacionEmpresarial.jsp" />
									</c:when>
									<c:otherwise>
										<div class="card">
											<c:set var="tpoInmueble" value="${tpoInmueble}" scope="request" />
											<c:set var="tpoUso" value="${tpoUso}" scope="request" />
											<jsp:include page="ubicacionFamiliar.jsp" />
										</div>
									</c:otherwise>
								</c:choose>
								${ub}
							</div>
							<c:set var="noUb" scope="session" value="${noUb + 1}" />
						</c:forEach>
					</div>
					<br />
					<div class="row">
						<div class="col-md-12">
							<div class="row">
								<table id="tblTit" class="table tblTit ml-3">
									<thead class="blue-grey lighten-4">
										<tr class="triTabTot">
											<th>Ubicacion</th>
										</tr>
									</thead>
									<tbody>
										<tr class="trsTabParcial">
											<td scope="row">Prima neta</td>
										</tr>
									</tbody>
								</table>
								<div class="table-wrapper-scroll-table">
									<table class="table">
										<thead class="blue-grey lighten-4">
											<tr class="triTabTot">
												<c:forEach items="${UbicacionesTot.ubicaciones}" var="ub" varStatus="i">
													<th>${i.index + 1}</th>
												</c:forEach>
											</tr>
										</thead>
										<tbody>
											<tr class="trsTabParcial">
												<c:forEach items="${UbicacionesTot.ubicaciones}" var="ub">
													<td scope="row">
														<fmt:formatNumber value="${ub.primaNeta}" type="currency" />
													</td>
												</c:forEach>
											</tr>
										</tbody>
									</table>
								</div>
								<table class="table tblTot">
									<thead class="blue-grey lighten-4">
										<tr class="triTabTot">
											<th>Prima total</th>
										</tr>
									</thead>
									<tbody>
										<tr class="trsTabParcial">
											<td scope="row">
												<fmt:formatNumber value="${UbicacionesTot.primaTotal}" type="currency" />
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div id="tabs-3">
					<div class="row">
						<%-- <div class="col-md-12"> --%>
							<div class="col-md-3">
								<div class="md-form form-group">
									<select name="his_folio" id="his_folio" class="mdb-select form-control-sel colorful-select dropdown-primary">
										<option value="-1"><liferay-ui:message key="AdministrarPolizaPortlet.selectOpDefoult" /></option>
										<c:forEach items="${folios.folios}" var="fl">
											<option value="${fl}">${fl}</option>
										</c:forEach>
									</select>
									<label for="his_folio">
										<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.folio" />
									</label>
								</div>
							</div>
							<div class="col-md-3">
								<div class="md-form form-group">
									<input type="date" id="his_desde" name="his_desde" class="form-control datepicker"
										placeholder="Seleccione una fecha">
									<label for="his_desde">
										<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.desde" />
									</label>
								</div>
							</div>
							<div class="col-md-3">
								<div class="md-form form-group">
									<input type="date" id="his_hasta" name="his_hasta" class="form-control datepicker"
										placeholder="Seleccione una fecha">
									<label for="his_hasta">
										<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.hasta" />
									</label>
								</div>
							</div>
							<div class="col-md-3">
								<div class="md-form form-group">
									<select name="his_tpoMov" id="his_tpoMov" class="mdb-select form-control-sel colorful-select dropdown-primary">
										<option value="-1"><liferay-ui:message key="AdministrarPolizaPortlet.selectOpDefoult" /></option>
										<c:forEach items="${movimientos}" var="mov">
											<option value="${mov.idCatalogoDetalle}">${mov.valor}</option>
										</c:forEach>
									</select>
									<label for="his_tpoMov">
										<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.tpoMovimient" />
									</label>
								</div>
							</div>
						<%-- </div> --%>
					</div>
					<div class="row">
						<div class="col-md-12">
							<button type="button" class="btn btn-pink waves-effect waves-light float-right " id="btnHistCambios"
								name="btnHistCambios">
								<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.btnBuscar" />
							</button>
						</div>
					</div>
					<hr>
					<h3>
						<liferay-ui:message key="AdministrarPolizaPortlet.360.historial.Subtit_movimientos" />
					</h3>
					<div class="table-wrapper">
						<table class="table data-table table-striped table-bordered" style="width: 100%;" id="tblMovRegis">
							<thead>
								<tr>
									<th>#</th>
									<th>Folio</th>
									<th>Tipo de Movimiento</th>
									<th>No de Endoso</th>
									<th>Ubicación</th>
									<th>Fecha de Expedición</th>
									<th>Vigencia Desde</th>
									<th>Vigencia Hasta</th>
									<th>Prima Total</th>
									<th>Comentarios</th>
									<th class="all" data-orderable="false"></th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</div>
				<div id="tabs-4">
					<div class="row">
						<div class="col-md-12" >
						
							<div class="md-form form-group" hiddend>
								<select name="cn_tpoEndoso" id="cn_tpoEndoso"
									class="mdb-select form-control-sel colorful-select dropdown-primary"
									searchable='<liferay-ui:message key="AdministrarPolizaPortlet.buscar" />' ${ puedeEndosar }>
									<option value="-1"><liferay-ui:message key="AdministrarPolizaPortlet.selectOpDefoult" /></option>
									<c:if test="${idProducto != 806 || idProducto != 809}">
										<option value="0">Alta de Ubicación</option>
										<option value="1" ${totUbicaCotiza == 1 ? 'disabled' : ''}>Baja de Ubicación</option>
									</c:if>
									<c:if test="${idProducto == 806 || idProducto == 809}">
										<option value="2">Endoso de Declaración</option>
									</c:if>
								</select>
								<label for="cn_tpoEndoso">
									<liferay-ui:message key="AdministrarPolizaPortlet.360.modificar.titulo" />
								</label>
							</div>
						</div>
	
						<div class="col-md-12">
							<button type="button" class="btn btn-pink waves-effect waves-light float-right" id="btnConTpendoso"
								name="btnConTpendoso" onclick="redirigeCotizacion();" ${ puedeEndosar }>
								<liferay-ui:message key="AdministrarPolizaPortlet.360.modificar.btnContinuar" />
							</button>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</section>

<a id='dwnldLnk' style="display: none;" />
<script src="<%=request.getContextPath()%>/js/main360.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/util.js?v=${version}"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js?v=${version}"></script>

<script type="text/javascript">
	var poliza = '${v360.poliza}';
	var version = '${v360.version}';
	var idcotizacion = '${v360.idcotizacion}';
	var idFolio = '${v360.folio}';
	var noUbicaciones = '${totUbicaCotiza}';
	var isEmpresarial =  ${isEmpresarial}
	var fechMin = '${v360fechMin}';
	var fechMax = '${v360fechMax}';
	var histoCambiosURL = '${getHistoCambiosURL}';
	var datosArchivosURL = '${datosArchivosURL}';
	var descargarDocURL = '${descargarDocURL}';
	var redirigeURL = '${redirigeURL}';
	var infCotizacion = '${infCotizacion}';
	var hayVencidos = ${hayVencidos};
</script>