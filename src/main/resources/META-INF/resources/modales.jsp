<!-- Modal Ver archivos -->
<div class="modal" id="modal-archives" tabindex="-1" role="dialog" aria-labelledby="archivesLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="archivesLabel">Archivos</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p id="errorArchivos"></p>
				<table id="tableArchivos" class="table simple-data-table table-striped table-bordered" style="width: 80%;">
					<thead>
						<tr>
							<th>Archivo</th>
							<th>Tipo</th>
							<th>Descargar</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Ver archivos -->
<!-- Modal Ver mensajes -->
<div class="modal" id="modal-messages" tabindex="-1" role="dialog" aria-labelledby="messagesLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="messagesLabel">Mensajes</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="comments-wrapper">
					<div class="row px-lg-2 px-2">
						<div class="col-md-12 px-lg-auto px-0">
							<div class="chat-comments">
								<p id="errorMenjases"></p>
								<ul class="list-unstyled" id="listaComentarios">

								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Ver mensajes -->
<!-- Modal Short Help -->
<div class="modal" id="modal-short-help" tabindex="-1" role="dialog" aria-labelledby="helpLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="helpLabel">Ayuda</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col">
							<div class="instruction">
								<h6 class="title font-weight-bold mb-3">
									<i class="far fa-comment pr-2"></i>
									Ver mensajes
								</h6>
								<p>Disponible en todos los estados de esta pantalla para lectura de mensajes. La funcionalidad para crear
									nuevos mensajes esta disponible en los estados: "POR EMITIR"</p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col">
							<div class="instruction">
								<h6 class="title font-weight-bold mb-3">
									<i class="far fa-file-alt pr-2"></i>
									Ver archivos
								</h6>
								<p>Disponible en todos los estados de esta pantalla respetando las reglas de negocio siguientes: Los
									Agentes/Brokers sólo pueden eliminar archivos cargados por otro perfil de agente o por sí mismos y que el
									estado de la poliza sea “EMITIDA”</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Short Help -->


<!-- Modal Pago vencido -->
<div class="modal" id="modalPagoVencido" tabindex="-1" role="dialog" aria-labelledby="modalPagoVencidoLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header orange">
				<h5 class="modal-title text-black-50" id="modalPagoVencido">
				<i class="fas fa-exclamation-triangle"></i>
				No es posible continuar</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!--Body-->
			<div class="modal-body">

				<div class="row">
					<div class="col-12">
						Estimado Cliente, no es posible continuar con el movimiento debido a que una o varias cuotas de 
						su p&oacute;liza est&aacute;n pendientes de pago. P&oacute;ngase en contacto con su Agente o Ejecutivo de Cuenta.
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					
					<div class="col-md-6">
						<button class="btn btn-blue waves-effect waves-light" data-dismiss="modal">Entendido</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Pago vencido -->


<!-- Modal Max ubica -->
<div class="modal" id="modalMaxUbicaciones" tabindex="-1" role="dialog" aria-labelledby="modalMaxUbicacionesLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header orange">
				<h5 class="modal-title text-black-50" id="modalMaxUbicaciones">
				<i class="fas fa-exclamation-triangle"></i>
				No es posible continuar</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<!--Body-->
			<div class="modal-body">

				<div class="row">
					<div class="col-12">
						Estimado Cliente, no es posible continuar con el movimiento debido a que su p&oacute;liza 
						cuenta con el m&aacute;ximo de ubicaciones. P&oacute;ngase en contacto con su Agente o Ejecutivo de Cuenta.
					</div>
				</div>
			</div>

			<!--Footer-->
			<div class="modal-footer justify-content-center">
				<div class="row">
					
					<div class="col-md-6">
						<button class="btn btn-blue waves-effect waves-light" data-dismiss="modal">Entendido</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Modal Max ubica  -->
