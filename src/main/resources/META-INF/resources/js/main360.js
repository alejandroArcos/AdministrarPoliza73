$( document ).ready( function() {
	
	$( "#tabs" ).tabs();
	$( "#tabsUbic" ).tabs();

	$( "#tabs-2 :input" ).prop( "disabled", true );

	$( '.datepicker' ).pickadate( {
		format : 'dd-mm-yyyy', /* 'dd/mm/yyyy', */
		formatSubmit : 'dd-mm-yyyy',
		min : fechMin,
		max : fechMax
	} );
	formatoMoneda();
} );

const maxUbicaciones = {
		familiar : 5,
		empresarial : 20
};

const formatter = new Intl.NumberFormat('en-US', {
	  style: 'currency',
	  currency: 'USD',
	  minimumFractionDigits: 2
});

function formatoMoneda(){
	$.each($('#tabs .moneda'),function(i, campo) {
		if(!valIsNullOrEmpty($(campo).val())){
			$(campo).val(formatter.format($(campo).val()));			
		}
	});
}

$( '#his_desde' ).change( function(e) {
	var seleccionado = $( this ).pickadate();
	var select_picker = seleccionado.pickadate( 'picker' );
	var modificar = $( '#his_hasta' ).pickadate();
	var mod_picker = modificar.pickadate( 'picker' );
	if (select_picker.get( 'select' ) == null) {
		mod_picker.set( 'min', fechMin );
	} else {
		mod_picker.set( 'min', select_picker.get( 'select' ) );
	}
} );


$( '#btnHistCambios' ).click(
		function(e) {
			showLoader();
			$.post( histoCambiosURL, {
				poliza : poliza,
				folio : $( '#his_folio' ).val(),
				desde : $( '#his_desde' ).val(),
				hasta : $( '#his_hasta' ).val(),
				movimiento : $( '#his_tpoMov' ).val()
			} ).done(
					function(data) {
						var response = jQuery.parseJSON( data );
						var tbl = $( '#tblMovRegis' ).DataTable();
						tbl.clear();
						for (var i = 0; i < response.length; i++) {
							tbl.row.add( [ (i + 1), "" + response[i].folio, "" + response[i].tipoMovimiento,
									"" + response[i].noEndoso, "" + response[i].ubicacion,
									"" + response[i].fechaExpedicion.split( " " )[0],
									"" + response[i].inicioVigencia.split( " " )[0],
									"" + response[i].finVigencia.split( " " )[0], "$" + response[i].primaTotal,
									"" + response[i].comentarios, generaBotonArchivos( response[i].idCarpeta ) ] );
						}
						tbl.destroy();
						generatbl( '#tblMovRegis' );
						hideLoader();
					} );
		} );

function generaBotonArchivos(idCarpeta) {
	var btn = '<form id="formCargaArchivo' + idCarpeta + '" action="' + datosArchivosURL + '" method="POST"> '
			+ '<a class="dropdown-item" data-toggle="modal" href="#modal-archives" ' + 'onclick="obtieneDocs(\''
			+ poliza + '\',\'' + idCarpeta + '\');"> ' + '<i class="far fa-file-alt mr-2"></i> '
			+ '<span>Ver archivos</span> </a> </form> ';
	return btn;
}

function generatbl(idTabla) {
	$( idTabla ).DataTable( {
		responsive : true,
		dom : 'fBrltip',
		buttons : [ {
			extend : 'excelHtml5',
			text : '<a class="btn-floating btn-sm teal waves-effect waves-light py-2 my-0">XLS</a>',
			titleAttr : 'Exportar XLS',
			className : "btn-unstyled",
			exportOptions : {
				columns : ':not(:last)',
				format : {
					body : function(data, row, column, node) {
						return data.replace( /[$,]/g, '' );
					}
				}

			}
		} ],
		columnDefs : [ {
			targets : '_all',
			className : "py-2"
		} ],
		lengthChange : true,
		language : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrando _MENU_ registros por página",
			"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sSearch" : "Filtrar:",
			"sLoadingRecords" : "Cargando...",
			"oPaginate" : {
				"sFirst" : "<i class='fa fa-angle-double-left'>first_page</i>",
				"sLast" : "<i class='fa fa-angle-double-right'>last_page</i>",
				"sNext" : "<i class='fa fa-angle-right' aria-hidden='false'></i>",
				"sPrevious" : "<i class='fa fa-angle-left' aria-hidden='false'></i>"
			},
		},
		lengthMenu : [ [ 5, 10, 15 ], [ 5, 10, 15 ] ],
		pageLength : 10
	} );
}

function obtieneDocs(poliza, idCarpeta) {
	console.log( "obtiene docs" );
	showLoader();
	$
			.ajax(
					{
						url : datosArchivosURL,
						type : 'POST',
						data : {
							poliza : poliza,
							idCarpeta : idCarpeta
						},
						success : function(data) {
							var archivo = JSON.parse( data );

							$( "#tableArchivos tbody" ).html( "" );
							if (archivo.msg == "OK") {
								console.log( "mensjae:" + archivo.msg );
								console.log( "archivo:" + archivo.listaDocumento );
								$.each(archivo.listaDocumento,function(i, stringJson) {

									var htmlTabla;
									var btnHtml = '<button id="btnDescargaArchivo" type="button" class="btn btn-blue btn-sm waves-effect waves-light" onclick="descargarDocumento('
											+ stringJson.idCarpeta
											+ ","
											+ stringJson.idDocumento
											+ ","
											+ stringJson.idCatalogoDetalle + ')">' + 'Descargar'
									'</button>';
	
									htmlTabla = "<tr>"
	
									+ "<td>" + stringJson.nombre + "." + stringJson.extension + "</td>"
											+ "<td>" + stringJson.tipo + "</td>" + "<td>" + btnHtml
											+ "</td>" + "</tr>";
	
									$( '#tableArchivos tbody' ).append( htmlTabla );
	
								} );
							} else {
								$( "#errorArchivos" ).text( archivo.msg );
							}

						}
					} ).always( function() {
				hideLoader();
			} );
}

function descargarDocumento(idCarpeta, idDocumento, idCatalogoDetalle) {
	/* llamar a descargar documento */

	console.log( "obtiene docs" );
	showLoader();
	$.ajax( {
		url : descargarDocURL,
		type : 'POST',
		data : {
			idCarpeta : idCarpeta,
			idDocumento : idDocumento,
			idCatalogoDetalle : idCatalogoDetalle
		},
		success : function(data) {

			var archivo = JSON.parse( data );

			if (archivo.code == 0) {

				if(detectIEEdge()){
					fileAux = 'data:application/octet-stream;base64,'+archivo.documento
					var dlnk = document.getElementById('dwnldLnk');
					dlnk.href = fileAux;
					dlnk.download = archivo.nombre+'.'+archivo.extension;
					location.href=document.getElementById("dwnldLnk").href;
					/*dlnk.click();*/
				}else{
					/*
					 * downloadDocument('archivo base 64' , 'nombre.extension' );
					 */
					downloadDocument(archivo.documento, archivo.nombre+'.'+archivo.extension);
				}
		
				hideLoader();

			} else {
				showMessageError( ".navbar", archivo.msg, 0 );
			}

		}
	} ).always( function() {
		hideLoader();
	} );
}

function redirigeCotizacion() {
	showLoader()
	if(hayVencidos){
		console.log("vencida");
		hideLoader();
		$("#modalPagoVencido").modal("show");
	}else{
		if(excedeMaxUbicaciones()){
			console.log("excede");
			hideLoader();
			$("#modalMaxUbicaciones").modal("show");
		}else{			
			continuaCotizacion();
		}
	}
}

function excedeMaxUbicaciones(){
	var excede = false;
	if($( "#cn_tpoEndoso" ).val() == "0"){
		if(isEmpresarial){
			if(maxUbicaciones.empresarial <= noUbicaciones){
				excede = true;
			}
		}else{
			if(maxUbicaciones.familiar <= noUbicaciones){
				excede = true;
			}
		}		
	}
	return excede;
}


function continuaCotizacion(){
	if ($( "#cn_tpoEndoso" ).val() == "-1") {
		showMessageError("#tabs-4", "Seleccione una opcion", 0);
		hideLoader();
	} else {
		$.post( redirigeURL, {
			tipoEndoso : $( "#cn_tpoEndoso" ).val(),
			noUbicaciones : noUbicaciones,
			infcot : infCotizacion
		} ).done(function(data) {
			var response = jQuery.parseJSON( data );
			console.log(response);
			
			if(response.code > 0){
				showMessageError( ".navbar", response.msj, 0 );
			}else{
				/*showMessageSuccess(".navbar", "Redireccionando", 0)*/
				window.location.href = response.url ;
			}
			
			/*
			if(response.code > 0){
				showMessageError( ".navbar", response.msj, 0 );
			}else{
				showMessageSuccess(".navbar", "Redireccionando", 0)
				var search = "";
				if (isEmpresarial) {
					search = '?cotizacionEmpresarial=' + idcotizacion +
					'&versionEmpresarial=' + version +
					'&tipoEndoso=' + $( "#cn_tpoEndoso" ).val() +
					'&noUbicaciones=' + noUbicaciones +
					'&isEndoso=' + true;			
				} else {
					search = '?cotizacionFamiliar=' + idcotizacion +
					'&versionFamiliar=' + version +
					'&tipoEndoso=' + $( "#cn_tpoEndoso" ).val() +
					'&noUbicaciones=' + noUbicaciones +
					'&isEndoso=' + true;
				}
				window.location.href = response.url + search;
			}
			//hideLoader();
			
			*/
		});
		
	}
}





