/**
 * 
 */
package com.tokio.pa.administrarpoliza73.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.DocumentoResponse;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author josemigueltomastrejo
 *
 */

@Component(immediate = true, property = { "javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
		"mvc.command.name=/emision/descargarDocumento" }, service = MVCResourceCommand.class)

public class DescargaDocumentoMVCResourceCommand extends BaseMVCResourceCommand {

	@Reference
	CotizadorService cotizadorService;

	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		try {
			int rowNum = 0;
			String tipTransaccion = "B";
			int activo = 1;
			String tipo = "COTIZACIONES";
			String parametros = "";
			int idAsigna = ParamUtil.getInteger(resourceRequest, "idAsigna"); 
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			String usuario = themeDisplay.getUser().getScreenName();
			String pantalla = "Cotizaciones";
			StringBuilder builder = new StringBuilder();

			String idCarpeta = ParamUtil.getString(resourceRequest, "idCarpeta");
			String idDocumento = ParamUtil.getString(resourceRequest, "idDocumento");
			String idCatalogoDetalle = ParamUtil.getString(resourceRequest, "idCatalogoDetalle");

			builder.append("{");
			builder.append("\"idCarpeta\":" + idCarpeta + ",");
			builder.append("\"idDocumento\":" + idDocumento + ",");
			builder.append("\"idCatalogoDetalle\":" + idCatalogoDetalle + ",");
			builder.append("\"documento\":\"\",");
			builder.append("\"nombre\":\"\",");
			builder.append("\"extension\":\"\"");
			builder.append("}");

			String jsonDocumentos = builder.toString();

			DocumentoResponse response = cotizadorService.wsDocumentos(rowNum, tipTransaccion, jsonDocumentos, activo,
					tipo, idAsigna, parametros, usuario, pantalla);

			System.out.println("regreso");
			System.out.println(response);
			int code = 0;
			if (!response.getMsg().equalsIgnoreCase("ok")) {
				code = 1;
			}

			response.getListaDocumento().get(0).getNombre();

			StringBuilder stringJson = new StringBuilder();
			stringJson.append("{");
			stringJson.append("\"code\":" + code);
			stringJson.append(",\"msg\":\"" + response.getMsg());
			stringJson.append("\",\"documento\":\"" + response.getListaDocumento().get(0).getDocumento() + "\"");
			stringJson.append(",\"nombre\":\"" + response.getListaDocumento().get(0).getNombre() + "\"");
			stringJson.append(",\"extension\":\"" + response.getListaDocumento().get(0).getExtension() + "\"");
			stringJson.append(",\"mimeType\":\""
					+ MimeTypesUtil.getExtensionContentType(response.getListaDocumento().get(0).getExtension()) + "\"");
			stringJson.append("}");
			System.out.println(stringJson.toString());
			resourceResponse.getWriter().write(stringJson.toString());

		} catch (Exception e) {
			// TODO: handle exception
			StringBuilder stringJson = new StringBuilder();
			stringJson.append("{");
			stringJson.append("\"code\": 2");
			stringJson.append(",\"msg\":\"Error al consultar la informaci??n\"" );
			stringJson.append("}");
			System.out.println(stringJson.toString());
			resourceResponse.getWriter().write(stringJson.toString());
		}

	}

}
