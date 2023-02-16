package com.tokio.pa.administrarpoliza73.portlet;

import com.google.gson.JsonObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.Persona;
import com.tokio.cotizador.Bean.Poliza;
import com.tokio.cotizador.Bean.Registro;
import com.tokio.cotizador.Exception.CotizadorException;
import com.tokio.cotizador.constants.CotizadorServiceKey;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author urielfloresvaldovinos
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AdministrarPolizaPortlet73",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.private-request-attributes=false"
	},
	service = Portlet.class
)
public class AdministrarPolizaPortlet73Portlet extends MVCPortlet {
	
	@Reference
	CotizadorService _CotizadorService;
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		llenaCatalogos(renderRequest);
		generaFechas(renderRequest);
		validaVieneListCot(renderRequest);
		
		super.doView(renderRequest, renderResponse);
	}
	
	
	private void validaVieneListCot(RenderRequest renderRequest){
		HttpServletRequest originalRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		try {
			String poliza = originalRequest.getParameter("poliza");
			String endoso = originalRequest.getParameter("endoso");
			String tpoCot = originalRequest.getParameter("tpoCot");
			
			if(Validator.isNotNull(poliza)){
				
				int idProd = 0;
				if(tpoCot.toLowerCase().contains("empresarial")){
					idProd = 809;
				}else{
					idProd = 208;
				}
				
			
				Poliza p = new Poliza();
				p.setEndoso(endoso);
				p.setPoliza(poliza);
				p.setIdproducto(idProd);
				System.out.println(p.toString());
//			p.setEndoso("DP014048");
//			p.setPoliza("PHMMX000023700");
//			p.setIdproducto(809);
				
				renderRequest.setAttribute("lcpol", p);			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	

	private void llenaCatalogos(RenderRequest renderRequest) {
		// TODO Auto-generated method stub
		try {
			HttpServletRequest originalRequest = PortalUtil
					.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
			
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String usuario = themeDisplay.getUser().getScreenName();
			
			Integer idUsuario = (Integer) originalRequest.getSession().getAttribute("idUsuario");
			
			List<Registro> catEstado = getCatalogos(CotizadorServiceKey.LISTA_EDOS, usuario);
			List<Registro> catMoneda = getCatalogos(CotizadorServiceKey.LISTA_MONEDA, usuario);
			List<Registro> catProducto = getCatalogos(CotizadorServiceKey.LISTA_PRODUCTO, usuario);
			List<Registro> catRamo = getCatalogos(CotizadorServiceKey.LISTA_RAMO, usuario);
			List<Registro> catPoliza = getCatalogos(CotizadorServiceKey.CAT_TIP_END, usuario);
			List<Registro> catFormPago = getCatalogos(CotizadorServiceKey.FORMA_PAGO, usuario);
			List<Registro> catMotCancelacion = getCatalogos(CotizadorServiceKey.CAT_TIP_MOV_CAN, usuario);
			List<Persona> catRespAgente = _CotizadorService.getListaAgenteUsuario(0, idUsuario, usuario, AdministrarPolizaPortlet73PortletKeys.Pantalla);
			
			renderRequest.setAttribute("catRespEstado", catEstado);
			renderRequest.setAttribute("catRespMoneda", catMoneda);
			renderRequest.setAttribute("catRespProduc", catProducto);
			renderRequest.setAttribute("catRespRamo", catRamo);
			renderRequest.setAttribute("catRespPoliza", catPoliza);
			renderRequest.setAttribute("catTipoPago", catFormPago);
			renderRequest.setAttribute("catMotCancelacion", catMotCancelacion);
			renderRequest.setAttribute("catRespAgente", catRespAgente);
		} catch (Exception e) {
			// TODO: handle exception
			SessionErrors.add(renderRequest, "errorConocido");
			renderRequest.setAttribute("errorMsg", "Error al consultar la informacion");
			SessionMessages.add(renderRequest,
					PortalUtil.getPortletId(renderRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		}
		
	}
	
	private List<Registro> getCatalogos(String codigo, String usuario) throws CotizadorException{
		return _CotizadorService.getCatalogo(0, "B", codigo, 1, usuario, AdministrarPolizaPortlet73PortletKeys.Pantalla);
	}
	
	private void generaFechas(RenderRequest renderRequest) {
		// TODO Auto-generated method stub
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		JsonObject respuesta = new JsonObject();
		respuesta.addProperty("anio", calendar.get(Calendar.YEAR));
		respuesta.addProperty("mes", calendar.get(Calendar.MONTH) );
		respuesta.addProperty("dia", calendar.get(Calendar.DAY_OF_MONTH));
		renderRequest.setAttribute("hoy", respuesta.toString());
		
	}
}