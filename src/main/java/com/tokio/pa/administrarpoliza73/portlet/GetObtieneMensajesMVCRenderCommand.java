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
import com.tokio.cotizador.Bean.ComentarioResponse;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
					 "mvc.command.name=/emision/obtieneMensajes" },
		service = MVCResourceCommand.class
)
public class GetObtieneMensajesMVCRenderCommand extends BaseMVCResourceCommand{

	private static Log _log = LogFactoryUtil.getLog(GetObtieneArchivosMVCResourceCommand.class);
	
	@Reference
	CotizadorService _CotizadorService;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		_log.info("GetObtieneMensajesMVCRenderCommand....");
		
		String poliza = ParamUtil.getString(resourceRequest, "poliza");		// poliza
		String certificado = ParamUtil.getString(resourceRequest, "endoso"); // endoso
		_log.info("poliza: " + poliza + " endoso: "+certificado);
		
		String folio = ""; //vacio para esta vista
		String cotizacion = ""; // vacio para esta vista
		
		int version = 1; // 1 para esta vista
		int tipo = 2; // 2 para esta vista
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		String pantalla = "Emision";
		 
		ComentarioResponse comResp = null;
		comResp = _CotizadorService.getComentario(folio, cotizacion, version, poliza, certificado, tipo, usuario, pantalla);
		
		Gson gson = new Gson();
		String stringJsonComen = gson.toJson(comResp);
		resourceResponse.getWriter().write(stringJsonComen);
	}
}
