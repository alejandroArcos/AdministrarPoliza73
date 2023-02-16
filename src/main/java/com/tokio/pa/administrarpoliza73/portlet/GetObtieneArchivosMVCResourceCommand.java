package com.tokio.pa.administrarpoliza73.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.DocumentoResponse;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
					 "mvc.command.name=/emision/obtieneArchivos" },
		service = MVCResourceCommand.class
)

public class GetObtieneArchivosMVCResourceCommand extends BaseMVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(GetObtieneArchivosMVCResourceCommand.class);
	
	@Reference
	CotizadorService _CotizadorService;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("GetObtieneArchivosMVCResourceCommand....");
		

		
		 int rowNum = 0; // 0 para esta vista
		 int idCarpeta =  ParamUtil.getInteger(resourceRequest, "idCarpeta");
		 _log.info("idCarpeta" + idCarpeta);
		 int idDocumento = 0; // 0 para esta vista
		 int idCatalogoDetalle = 0; // 0 para esta vista
		 String tipo = ""; // null para esta vista
		 int activo = -1; // -1 para esta vista
		 ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		 String usuario = themeDisplay.getUser().getScreenName();
		 String pantalla = "Emision";
		 String parametros = null; // idCarpeta para esta vista
		
		DocumentoResponse docResp = null; 
		docResp = _CotizadorService.getListaDocumentos(rowNum, idCarpeta, idDocumento, idCatalogoDetalle, tipo, activo, parametros, usuario, pantalla);
		//pasar a string json  
		Gson gson = new Gson();
		String stringJson = gson.toJson(docResp);
		resourceResponse.getWriter().write(stringJson);
	}

}
