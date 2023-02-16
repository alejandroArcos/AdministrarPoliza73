/**
 * 
 */
package com.tokio.pa.administrarpoliza73.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.HistorialMovivimentosPoliza;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author jonathanfviverosmoreno
 *
 */

@Component(immediate = true, property = {
		"javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
		"mvc.command.name=/endosos/histoCambios" 
		}, service = MVCResourceCommand.class)

public class GetHistorialCambiosResourceCommand extends BaseMVCResourceCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand#
	 * doServeResource(javax.portlet.ResourceRequest,
	 * javax.portlet.ResourceResponse)
	 */
	@Reference
	CotizadorService _CotizadorService;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		// TODO Auto-generated method stub
		String poliza = ParamUtil.getString(resourceRequest, "poliza");
		int folio = ParamUtil.getInteger(resourceRequest, "folio");
		String desde = ParamUtil.getString(resourceRequest, "desde");
		String hasta = ParamUtil.getString(resourceRequest, "hasta");
		int movimiento = ParamUtil.getInteger(resourceRequest, "movimiento");

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String usuario = themeDisplay.getUser().getScreenName();
		
		HistorialMovivimentosPoliza historico = _CotizadorService.GetHistorialMovimientosPoliza(
				poliza, folio, desde, hasta, 
				"", movimiento, AdministrarPolizaPortlet73PortletKeys.Pantalla, usuario);
		
		Gson gson = new Gson();
		String stringJsonComen = gson.toJson(historico.getMovimientos());
		resourceResponse.getWriter().write(stringJsonComen);
	}

}
