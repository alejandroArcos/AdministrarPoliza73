/**
 * 
 */
package com.tokio.pa.administrarpoliza73.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.PolizaResponse;
import com.tokio.cotizador.Exception.CotizadorException;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author jonathanfviverosmoreno
 *
 */

@Component(
	    immediate = true,
	    property = {
	    	"javax.portlet.init-param.copy-request-parameters=true",
	        "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
	        "mvc.command.name=/endoso/buscaPoliza"
	    },
	    service = MVCActionCommand.class
	)
public class BuscarPolizaActionCommand extends BaseMVCActionCommand {
	
	@Reference
	CotizadorService _CotizadorService;

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand#doProcessAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
	 */
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub		
		buscaPolizas(actionRequest);
		enviaParametros(actionRequest);		
		
	}
	
	private void enviaParametros(ActionRequest actionRequest) {
		// TODO Auto-generated method stub
		actionRequest.setAttribute("polizaBus", ParamUtil.getString(actionRequest, "poliza"));
		actionRequest.setAttribute("endosoBus", ParamUtil.getString(actionRequest, "endoso"));
		actionRequest.setAttribute("estatusBus", ParamUtil.getString(actionRequest, "estatus"));
		actionRequest.setAttribute("tipopagoBus", ParamUtil.getString(actionRequest, "tipopago"));
		actionRequest.setAttribute("inicioBus", ParamUtil.getString(actionRequest, "inicio"));
		actionRequest.setAttribute("finBus", ParamUtil.getString(actionRequest, "fin"));
		actionRequest.setAttribute("agenteBus", ParamUtil.getString(actionRequest, "agenteB"));
		actionRequest.setAttribute("aseguradoBus", ParamUtil.getString(actionRequest, "asegurado"));
		actionRequest.setAttribute("aseguradoIdPersonaBus", ParamUtil.getString(actionRequest, "aseguradoIpPersona"));
		actionRequest.setAttribute("tipoPolizaBus", ParamUtil.getString(actionRequest, "tipoPoliza"));
		actionRequest.setAttribute("monedaBus", ParamUtil.getString(actionRequest, "moneda"));
		actionRequest.setAttribute("productoBus", ParamUtil.getString(actionRequest, "producto"));
		actionRequest.setAttribute("ramoBus", ParamUtil.getString(actionRequest, "ramo"));
	}

	private void buscaPolizas(ActionRequest actionRequest) throws CotizadorException {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		System.out.println("antes de consumir el servicio --------------------------");
		
		int idAsegurado = ParamUtil.getInteger(actionRequest, "aseguradoIpPersona");
		String nombreAseg = ParamUtil.getString(actionRequest, "asegurado");
		System.out.println( "idAsegurado : " + idAsegurado +  " - -  nombreAseg : " + nombreAseg );
		
		if(idAsegurado == 0 && nombreAseg.length() > 0){
			SessionErrors.add(actionRequest, "errorConocido");
			actionRequest.setAttribute("errorMsg", "Consulta sin información");
			SessionMessages.add(actionRequest,
					PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}else{
			PolizaResponse polizas = _CotizadorService.GetAdministrarPoliza(
					ParamUtil.getString(actionRequest, "poliza"),
					ParamUtil.getInteger(actionRequest, "estatus"),
					ParamUtil.getString(actionRequest, "endoso"),
					ParamUtil.getString(actionRequest, "inicio_submit"),
					ParamUtil.getString(actionRequest, "fin_submit"),
					ParamUtil.getInteger(actionRequest, "aseguradoIpPersona"),
					ParamUtil.getInteger(actionRequest, "agente"),
					ParamUtil.getInteger(actionRequest, "moneda"),
					ParamUtil.getInteger(actionRequest, "tipopago"),
					ParamUtil.getInteger(actionRequest, "producto"),
					ParamUtil.getInteger(actionRequest, "ramo"),
					ParamUtil.getInteger(actionRequest, "tipoPoliza"),
					ParamUtil.getInteger(actionRequest, "motCancelacion"),
					ParamUtil.getInteger(actionRequest, "tipoConsulta"),
					0, usuario, AdministrarPolizaPortlet73PortletKeys.Pantalla);
			
			
			System.out.println(polizas.toString());
			
			actionRequest.setAttribute("tablaPoliza", polizas.getListaPoliza());
			
			if(polizas.getTotalRow() > 0){
				SessionMessages.add(actionRequest, "exitoBusqueda");					
			}else{
				SessionErrors.add(actionRequest, "errorConocido");
				actionRequest.setAttribute("errorMsg", "Consulta sin información");
				SessionMessages.add(actionRequest,
						PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
			}
		}
	}
	
	

}
