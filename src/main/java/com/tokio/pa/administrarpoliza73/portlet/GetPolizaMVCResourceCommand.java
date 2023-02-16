package com.tokio.pa.administrarpoliza73.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.Poliza;
import com.tokio.cotizador.Bean.PolizaResponse;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
					 "mvc.command.name=/emision/buscaPolizaAjax" },
		service = MVCResourceCommand.class
)
public class GetPolizaMVCResourceCommand extends BaseMVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(GetPolizaMVCResourceCommand.class);

	@Reference
	CotizadorService _CotizadorService;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {

		_log.info("GetPolizaMVCActionCommand");
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		
		String poliza = ParamUtil.getString(resourceRequest, "poliza");
		if(poliza.trim().equals("null")){
			poliza="";
		}
		String endoso = ParamUtil.getString(resourceRequest, "endoso");
		Integer estatus = ParamUtil.getInteger(resourceRequest, "estatus");//convertir a int
		Integer tipopago = ParamUtil.getInteger(resourceRequest, "tipopago");
		String inicio = ParamUtil.getString(resourceRequest, "inicio");
		String fin = ParamUtil.getString(resourceRequest, "fin");
		Integer agente = ParamUtil.getInteger(resourceRequest, "agente");
		String asegurado = ParamUtil.getString(resourceRequest, "asegurado");
		Integer aseguradoIdPersona = ParamUtil.getInteger(resourceRequest, "aseguradoIpPersona");
		Integer tipoPoliza = ParamUtil.getInteger(resourceRequest, "tipoPoliza");
		Integer moneda = ParamUtil.getInteger(resourceRequest, "moneda");
		Integer producto = ParamUtil.getInteger(resourceRequest, "producto");
		Integer ramo = ParamUtil.getInteger(resourceRequest, "ramo");
		Integer motcance = ParamUtil.getInteger(resourceRequest, "motCancelacion");
		Integer filtro = ParamUtil.getInteger(resourceRequest, "tipoConsulta");
		
		_log.info("poliza:"+poliza+" endoso:"+endoso+" estatus:"+estatus+" tipopago:"+tipopago+" inicio:"+inicio+" fin:"+fin+" agente:"+agente+" asegurado:"+asegurado+ " aseguradoIdPer:"+aseguradoIdPersona +" tipoPoliza:"+tipoPoliza+" moneda:"+moneda+" producto:"+producto+" ramo:"+ramo);
		
		int cliente = 0; //ver lo del codigo de persona  con el predictivo -> asegurado -> idPersona
		int movimiento = 0; // que es es este campo ?
		int tipoConsulta = ParamUtil.getInteger(resourceRequest, "custId"); 
		// 1 - Historial, 2 - Canceladas, 3 - Vigentes, 4 - Renovación

		int rownum = ParamUtil.getInteger(resourceRequest, "rowNum"); 
		String pag = "CONSULTA DE POLIZAS";
		
		PolizaResponse polizaResp = null;
		List<Poliza> tablaPoliza = null;
		try {
			
			
			polizaResp = _CotizadorService.getPoliza(poliza, estatus, endoso, inicio, fin, aseguradoIdPersona, agente, moneda, tipopago, producto, ramo, tipoPoliza, motcance, tipoConsulta, rownum, usuario, pag);
			

			if( polizaResp.getCode() == 0 ){
				Gson gson = new Gson();
				String stringJson = gson.toJson(polizaResp);
				resourceResponse.getWriter().write(stringJson);
				SessionMessages.add(resourceRequest, "consultaExitosa");
			}else{
				String jsonString = "{ \"code\" : " + polizaResp.getCode() + ", \"msj\" : \"" + polizaResp.getMsg() + "\"}"; 
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(jsonString);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			SessionErrors.add(resourceRequest, "error");
		}finally {
			SessionMessages.add(resourceRequest, PortalUtil.getPortletId(resourceRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		
		resourceRequest.setAttribute("polizaBus", poliza);
		resourceRequest.setAttribute("endosoBus", endoso);
		resourceRequest.setAttribute("estatusBus", estatus);
		resourceRequest.setAttribute("tipopagoBus", tipopago);
		resourceRequest.setAttribute("inicioBus", inicio);
		resourceRequest.setAttribute("finBus", fin);
		resourceRequest.setAttribute("agenteBus", agente);
		resourceRequest.setAttribute("aseguradoBus", asegurado);
		resourceRequest.setAttribute("aseguradoIdPersonaBus", aseguradoIdPersona);
		resourceRequest.setAttribute("tipoPolizaBus", tipoPoliza);
		resourceRequest.setAttribute("monedaBus", moneda);
		resourceRequest.setAttribute("productoBus", producto);
		resourceRequest.setAttribute("ramoBus", ramo);
		resourceRequest.setAttribute("filtro", filtro);
		resourceRequest.setAttribute("motCancelacion", motcance);

		//actionResponse.setRenderParameter("mvcPath", "/busqueda.jsp");
		
	}

}
