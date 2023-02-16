<%@ include file="./init.jsp"%>
<!-- <div class="card text-uppercase"> -->
<div class="row">
	<%-- <div class="col-md-12"> --%>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubCodPostal${noUb}" name="ubCodPostal${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].cpData.cp}" disabled>
				<label for="ubCodPostal${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.cp" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubCalle${noUb}" name="ubCalle${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].calle}" disabled>
				<label for="ubCalle${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.calle" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubNumero${noUb}" name="ubNumero${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].numero}" disabled>
				<label for="ubNumero${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.numero" />
				</label>
			</div>
		</div>
	<%-- </div> --%>
</div>
<div class="row">
	<%-- <div class="col-md-12"> --%>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubColonia${noUb}" name="ubColonia${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].cpData.colonia}" disabled>
				<label for="ubColonia${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.colonia" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubDelegacion${noUb}" name="ubDelegacion${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].cpData.delegacion}" disabled>
				<label for="ubDelegacion${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.delegacion" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubEdo${noUb}" name="ubEdo${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].cpData.estado}" disabled>
				<label for="ubEdo${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.edo" />
				</label>
			</div>
		</div>
	<%-- </div> --%>
</div>
<div class="row">
	<%-- <div class="col-md-12"> --%>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubNiveles${noUb}" name="ubNiveles${noUb}" class="form-control"
					value="${niveles[noUb]}" disabled>
				<label for="ubNiveles${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.niveles" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubTpsConstruc${noUb}" name="ubTpsConstruc${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].descripcionConstruccion}" disabled>
				<label for="ubTpsConstruc${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.tpsConstruc" />
				</label>
			</div>
		</div>
		<div class="col-md-4">
			<div class="md-form form-group">
				<input type="text" id="ubMdasSeg${noUb}" name="ubMdasSeg${noUb}" class="form-control"
					value="${UbicacionesTot.ubicaciones[noUb].descripcionMedidaSeguridad}" disabled>
				<label for="ubMdasSeg${noUb}">
					<liferay-ui:message key="AdministrarPolizaPortlet.360.ubicaciones.mdasSeg" />
				</label>
			</div>
		</div>
	<%-- </div> --%>
</div>
<!-- </div> -->
