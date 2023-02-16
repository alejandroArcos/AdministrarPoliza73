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
import com.tokio.cotizador.Bean.PersonaResponse;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true, 
		property = { "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
					 "mvc.command.name=/emision/autoCompletado" },
		service = MVCResourceCommand.class
)

public class GetAutoCompletadoAseguradoMVCResoruceCommand extends BaseMVCResourceCommand{
	private static Log _log = LogFactoryUtil.getLog(GetAutoCompletadoAseguradoMVCResoruceCommand.class);
	
	@Reference
	private CotizadorService _CotizadorService;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("GetAutoCompletadoAseguradoMVCResoruceCommand.....");
		
		String nombrecliente= ParamUtil.getString(resourceRequest, "term");
		_log.info("letras: "+nombrecliente);
		int tipo = 1;
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		String pantalla = AdministrarPolizaPortlet73PortletKeys.Pantalla;
		
		PersonaResponse personaRes = null;
		
		personaRes = _CotizadorService.getListaPersonas(nombrecliente, tipo, usuario, pantalla);
		
		Gson gson = new Gson();
		String stringJsonComen = gson.toJson(personaRes.getListaPersona());
		resourceResponse.getWriter().write(stringJsonComen);
		//_log.info("gson: "+stringJsonComen);
		
	}

}
