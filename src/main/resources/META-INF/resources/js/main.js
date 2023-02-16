

$( document ).ready( function() {
	showLoader();	
	inicializaCampos();
	if(valIsNullOrEmpty(polizaLisCot)){
		hideLoader();
	}else{
		window.location.href = $("#LCpol360" + polizaLisCot).attr("href");
	}
	
		
} );

function inicializaCampos(){
	
	$( '.datepicker' ).pickadate( {
		today : 'Hoy',
		clear : 'Limpiar',
		close : 'Cerrar',
		format : 'dd-mmm-yyyy', /* 'dd/mm/yyyy', */
		formatSubmit : 'yyyy-mm-dd',
		min : [ (fechaHoy.anio - 2), fechaHoy.mes, fechaHoy.dia ],
		max : [ fechaHoy.anio, fechaHoy.mes, fechaHoy.dia ]
	} );

	$( ".fecha" ).each( function() {
		value = $( this ).text();
		value = new Date( parseInt( value.replace( "/Date(", "" ).replace( ")/", "" ), 10 ) );
		month = value.getMonth() + 1;

		if (month <= 9) {
			month = "0" + month;
		}
		$( this ).text( value.getDate() + "/" + month + "/" + value.getFullYear() )
	} );
	
	
	 $( "#aseguradoSiniestro" ).autocomplete({
			minLength: 3,
			source: autoCompletadoAsegurado,
			focus : function(event, ui) {
				$("#aseguradoSiniestro").val(ui.item.nombrepersona);
				return false;
			},
			select : function(event, ui) {
				$("#aseguradoSiniestro").val(ui.item.nombre);
				$("#aseguradoIpPersona").val(ui.item.idPersona);
				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
			return $("<li class=\"list-group-item\">").append("<div>" + item.nombre + "</div>")
			.appendTo(ul);
		};
}

$( "#btnBuscar" ).click( function(e) {
	showLoader();
	e.preventDefault();
	var table = $('#tablaEmisiones').DataTable();
	 
	table.clear().draw();
	$( "#search-form" ).submit();
	
	
} );

function agregaRequerido() {
	$( '#poliza' ).addClass( 'invalid' );
	$( '#poliza' )
			.parent()
			.append(
					"<div class=\"alert alert-danger\"> <span class=\"glyphicon glyphicon-ban-circle\"></span> La poliza es requerida </div>" );
}

function quitaRequeridos() {
	$( '#poliza' ).removeClass( 'invalid' );
	$( ".alert-danger" ).remove();
}

$( "#aseguradoSiniestro" ).keyup(function() {
	if($( "#aseguradoSiniestro" ).val().length <= 1){
		$("#aseguradoIpPersona").val("0");
	}
	
});


function obtieneDocs(poliza , idCarpeta){
	console.log("obtiene docs");
	showLoader();
	$.ajax({
        url: datosArchivos,
        type: 'POST',
        data: {poliza: poliza, idCarpeta: idCarpeta},
       	success: function(data){
       		var archivo = JSON.parse(data);
       		
       		$("#tableArchivos tbody").html("");
       		if(archivo.msg == "OK"){
       			console.log("mensjae:"+ archivo.msg);
       			console.log("archivo:"+ archivo.listaDocumento);
       			$.each(archivo.listaDocumento, function(i, stringJson) { 
           			
                    var htmlTabla;
                    var btnHtml = 
		                    '<button id="btnDescargaArchivo" type="button" class="btn btn-blue btn-sm waves-effect waves-light" onclick="descargarDocumento(' + stringJson.idCarpeta + "," + stringJson.idDocumento + "," + stringJson.idCatalogoDetalle + ')">' +
		                		'Descargar'
		               		'</button>';
                    
                    htmlTabla = "<tr>"
                	  
                      +"<td>"+stringJson.nombre+"."+stringJson.extension+"</td>"
                	  +"<td>"+stringJson.tipo+"</td>"
                	  +"<td>"+btnHtml+"</td>"
                	  +"</tr>";
                	  
    				
    				  $('#tableArchivos tbody').append(htmlTabla);
                	      	 
                  });
       		}else{
       			$("#errorArchivos").text(archivo.msg);
       		}
       		
       		
	       	}	            
    }).always( function() {
        hideLoader();
    } );
}

function obtieneMensajes(poliza,endoso){
	console.log("obtiene mensajes");
	showLoader();
	$.ajax({
        url: datosMensajes,
        type: 'POST',
        data: {poliza: poliza,
        	   endoso: endoso},
       	success: function(data){
       		var comentario = JSON.parse(data);
       		
       		$("#listaComentarios").html("");
       		if(comentario.code == 0){
			$.each(comentario.listaComentario, function(i, stringJson) { 
       			
                var lista;
                var nom = stringJson.usuario;
                var iniciales = nom.substring(0, 1);
                lista = "<li class='justify-content-between mb-3'>"
            	  
                  +"<div class='comment-body white p-3 z-depth-1'>"
                  +"<div class='header'>"
                  +"<div class='user-acronym btn-floating btn-sm light-blue darken-4 waves-effect waves-light'>"
                  +"<span>"+iniciales.toUpperCase()+"</span>"
                  +"</div>"
                  +"<strong class='primary-font'>"+ stringJson.usuario +"</strong>"
            	  +"<small class='pull-right text-muted'><i class='fa fa-clock-o'></i>"+ stringJson.fecha +"</small>"
            	  +"</div>"
				  +"<hr class='w-100'>"
                  +"<h6 class='font-weight-bold mb-2'><strong>"+stringJson.idcomentario+"</strong></h6>"
                  +"<p class='mb-0'>" +stringJson.comentario  + "</p>"
				  +"</div>"
 
            	  +"</li>";
				
            	  $("#listaComentarios").append(lista);
            	      	 
              });
       		}else{
       			$("#errorMenjases").text(comentario.msg);
       		}
       		
       	}	        
    }).always( function() {
        hideLoader();
    } );
}


function  descargarDocumento( idCarpeta,idDocumento,idCatalogoDetalle) {
	/* llamar a descargar documento */
	
	console.log("obtiene docs");
	showLoader();
	$.ajax({
        url: descargarDocumentoURL,
        type: 'POST',
        data: {idCarpeta: idCarpeta,idDocumento:idDocumento,idCatalogoDetalle:idCatalogoDetalle},
       	success: function(data){

       		var archivo = JSON.parse(data);
       		
       		if( archivo.code ==0 ){
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
       			
       		}else{
				showMessageError(".navbar",archivo.msg, 0);
       		}
       		
       	}	            
    }).always( function() {
        hideLoader();
    } );	
}

