/**
 * 
 */
package com.tokio.pa.administrarpoliza73.portlet;

import com.google.gson.Gson;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.ModoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.TipoCotizacion;
import com.tokio.pa.cotizadorModularServices.Util.CotizadorModularUtil;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import java.io.PrintWriter;
import java.util.Base64;

import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author jonathanfviverosmoreno
 *
 */
@Component(immediate = true, property = {
		"javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
		"mvc.command.name=/emision/redirige" }, service = MVCResourceCommand.class)
public class RedirigeCotizadorResourceCommand extends BaseMVCResourceCommand {

	@Reference
	CotizadorService _CotizadorService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand#
	 * doServeResource(javax.portlet.ResourceRequest,
	 * javax.portlet.ResourceResponse)
	 */
	@Override
	protected void doServeResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		// TODO Auto-generated method stub
		try {

			int tipoEndoso = ParamUtil.getInteger(resourceRequest, "tipoEndoso");
			int noUbicaciones = ParamUtil.getInteger(resourceRequest, "noUbicaciones");
			String infCS = ParamUtil.getString(resourceRequest, "infcot");

			
			System.out.println("noUbicaciones : " + noUbicaciones);
			
			Gson gson = new Gson();

			HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(resourceRequest));
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
					.getAttribute(WebKeys.THEME_DISPLAY);

			InfoCotizacion infCot = gson.fromJson(infCS, InfoCotizacion.class);
			
		
			infCot.setModo(seleccionaTipo(tipoEndoso));
			infCot.setNoUbicaciones(noUbicaciones);

			
			System.err.println("-----------------------------------------------------");
			System.out.println(infCot.toString());
			
			String parametro = "?infoCotizacion=" + CotizadorModularUtil.encodeURL(infCot);

			String tipo = infCot.getTipoCotizacion().equals(TipoCotizacion.EMPRESARIAL)
					? "/paquete-empresarial" : "/paquete-familiar";
			
			switch(infCot.getTipoCotizacion()) {
			
				case EMPRESARIAL:
					tipo = "/paquete-empresarial";
					break;
				case FAMILIAR: 
					tipo = "/paquete-familiar";
					break;
				case TRANSPORTES:
					tipo = "/cotizador-transportes";
					break;
				default:
					break;
			}

			final long GROUP_ID = themeDisplay.getLayout().getGroupId();
			Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(GROUP_ID, true, tipo);
			String urlCotizador = layout.getRegularURL(originalRequest);

			
			String jsonString = "{ \"code\" : 0, \"url\" : \"" + urlCotizador + parametro + "\"}";
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(jsonString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			String jsonString = "{ \"code\" : 1, \"msj\" : \"Error en el proceso\"}";
			PrintWriter writer = resourceResponse.getWriter();
			writer.write(jsonString);
		}

	}

	private ModoCotizacion seleccionaTipo(int tipo) {
		/*
		if (tipo == 0) {
			return ModoCotizacion.ALTA_ENDOSO;
		}
		return ModoCotizacion.BAJA_ENDOSO;
		*/
		
		switch(tipo) {
			case 0:
				return ModoCotizacion.ALTA_ENDOSO;
			case 1:
				return ModoCotizacion.BAJA_ENDOSO;
			case 2:
				return ModoCotizacion.DECLARACION_ENDOSO;
			default:
				return ModoCotizacion.ERROR;
		}
	}

}
