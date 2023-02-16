package com.tokio.pa.administrarpoliza73.portlet;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.tokio.cotizador.CotizadorService;
import com.tokio.cotizador.Bean.CotizacionEmpresarialResponse;
import com.tokio.cotizador.Bean.CotizacionFamiliarResponse;
import com.tokio.cotizador.Bean.FoliosPorPoliza360;
import com.tokio.cotizador.Bean.P360PagosPendientes;
import com.tokio.cotizador.Bean.P360Vista360Poliza;
import com.tokio.cotizador.Bean.Registro;
import com.tokio.cotizador.Bean.Ubicacion;
import com.tokio.cotizador.Exception.CotizadorException;
import com.tokio.cotizador.constants.CotizadorServiceKey;
import com.tokio.cotizador.jsonformservice.JsonFormService;
import com.tokio.pa.cotizadorModularServices.Bean.InfoCotizacion;
import com.tokio.pa.cotizadorModularServices.Enum.TipoCotizacion;
import com.tokio.pa.cotizadorModularServices.Util.CotizadorModularUtil;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;
import com.tokio.pa.administrarpoliza73.interfaces.PermisosParaEndosar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;

@Component(
		configurationPid = 
			"com.tokio.endosos.interfaces.permisosEndosos",
		immediate = true,
		property = { 
				"javax.portlet.init-param.copy-request-parameters=true",
				"javax.portlet.name=" + AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73,
				"mvc.command.name=/endoso/poliza360"
				},
		service = MVCActionCommand.class
		)

public class GeneraPoliza360ActionCommand extends BaseMVCActionCommand {

	@Reference
	CotizadorService _CotizadorService;

	@Reference
	JsonFormService _JsonFormService;
	
	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
			configuracionPermisosEndosos = ConfigurableUtil.createConfigurable(
            		PermisosParaEndosar.class, properties);
    }

	ThemeDisplay themeDisplay = null;
	String usuario = null;
	HttpServletRequest originalRequest = null;
	int idPerfilUser = 0;
	boolean isEmpresarial = false;
	String folio = null;
	int cotizacion = 0;
	int version = 0;
	int idproducto = 0;
	
	private volatile PermisosParaEndosar configuracionPermisosEndosos;
	
	


	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		inicializaGlobales(actionRequest);
		llenaDatosGenerales(actionRequest);
		generaUbicaciones(actionRequest);
		generaHistorialCambios(actionRequest);
		llenaObjetoGenerico(actionRequest);
		permisosEndososUsuario(actionRequest);
		
		actionResponse.setRenderParameter("jspPage", "/poliza360.jsp");
//		actionResponse.setRenderParameter("jspPage", "/poliza360.jsp");
	}

	void inicializaGlobales(ActionRequest actionRequest) {
		themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		usuario = themeDisplay.getUser().getScreenName();
		originalRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(actionRequest));
		idPerfilUser = (int) originalRequest.getSession().getAttribute("idPerfil");
		idproducto = ParamUtil.getInteger(actionRequest, "idproducto");
		isEmpresarial = (idproducto == AdministrarPolizaPortlet73PortletKeys.EMPRESARIAL) ? true : false;
		
		actionRequest.setAttribute("idProducto", idproducto);
	}

	void llenaDatosGenerales(ActionRequest actionRequest) throws CotizadorException {
		boolean isAgente = (idPerfilUser == AdministrarPolizaPortlet73PortletKeys.PERFIL_AGENTE) ? true : false;
		String poliza = ParamUtil.getString(actionRequest, "poliza");
		String endoso = ParamUtil.getString(actionRequest, "endoso");

		
		P360Vista360Poliza vista360 = _CotizadorService.vista360Poliza(poliza, endoso,
				AdministrarPolizaPortlet73PortletKeys.Pantalla, usuario);
		

		folio = vista360.getFolio();
		cotizacion = vista360.getIdcotizacion();
		version = vista360.getVersion();
		
		boolean hayVencidos = hayPagosVencidos(vista360.getPagosPendientes());

		String[] fIni = vista360.getFecInicio().split(" ");
		String[] fFin = vista360.getFecFin().split(" ");
		String[] fExpide = vista360.getFecEmision().split(" ");
		String vigfencia = fIni[0] + " - " + fFin[0];

		
		String[] fechmin =  fIni[0].split("/");
		String[] fechmax =  fFin[0].split("/");
		String[] fechMinInv = {fechmin[2], (Integer.parseInt(fechmin[1])-1)+"", fechmin[0]};
		String[] fechMaxInv = {fechmax[2], (Integer.parseInt(fechmax[1])-1)+"", fechmax[0]};
		
		actionRequest.setAttribute("isAgente", isAgente);
		actionRequest.setAttribute("v360", vista360);
		actionRequest.setAttribute("v360Vigencia", vigfencia);
		actionRequest.setAttribute("v360fechMin", Arrays.toString(fechMinInv));
		actionRequest.setAttribute("v360fechMax", Arrays.toString(fechMaxInv));
		actionRequest.setAttribute("v360Expedicion", fExpide[0]);
		actionRequest.setAttribute("isEmpresarial", isEmpresarial);
		actionRequest.setAttribute("hayVencidos", hayVencidos);
	}

	

	void generaUbicaciones(ActionRequest actionRequest) throws CotizadorException {
		if (isEmpresarial) {
			generaUbicacionesEmpresarial(actionRequest);
		} else {
			generaUbicacionesFaniliar(actionRequest);
		}
	}

	void generaUbicacionesEmpresarial(ActionRequest actionRequest) throws CotizadorException {
		CotizacionEmpresarialResponse cotEmpresarial = _CotizadorService.ConsultaUbicacionEmpresarial(cotizacion,
				version, AdministrarPolizaPortlet73PortletKeys.Pantalla, usuario);
		ArrayList<String> htmlUb = new ArrayList<String>();

		for (Ubicacion ubicacion : cotEmpresarial.getUbicaciones()) {

			String jsonformfields = "{\"fields\":" + ubicacion.getField() + "}";
			String jsonDataProviders = "{\"dataProviders\":" + cotEmpresarial.getDataProvider() + "}";
			String htmlAppend = _JsonFormService.parse(jsonformfields, ubicacion.getLayouts(), jsonDataProviders,
					(ubicacion.getIdubicacion() + ""));

			htmlUb.add(htmlAppend);
		}

		ArrayList<String> niveles = generaNiveles(cotEmpresarial.getUbicaciones());
		
		int totUbicaCotiza = cotEmpresarial.getUbicaciones().size();

		actionRequest.setAttribute("UbicacionesTot", cotEmpresarial);
		actionRequest.setAttribute("htmlUb", htmlUb);
		actionRequest.setAttribute("niveles", niveles);
		actionRequest.setAttribute("totUbicaCotiza", totUbicaCotiza);
	}

	void generaUbicacionesFaniliar(ActionRequest actionRequest) throws CotizadorException {
		CotizacionFamiliarResponse cotFamiliar = _CotizadorService.ConsultaUbicacionFamiliar(cotizacion, version,
				AdministrarPolizaPortlet73PortletKeys.Pantalla, usuario);
		ArrayList<String> htmlUb = new ArrayList<String>();

		for (Ubicacion ubicacion : cotFamiliar.getListaUbicacion()) {

			String jsonformfields = "{\"fields\":" + ubicacion.getField() + "}";
			String jsonDataProviders = "{\"dataProviders\":" + cotFamiliar.getDataProvider() + "}";
			String htmlAppend = _JsonFormService.parse(jsonformfields, ubicacion.getLayouts(), jsonDataProviders,
					(ubicacion.getIdubicacion() + ""));

			htmlUb.add(htmlAppend);
		}
		ArrayList<String> niveles = generaNiveles(cotFamiliar.getListaUbicacion());
		ArrayList<String> tpoInmueble = generaTipoImbueble(cotFamiliar.getListaUbicacion());
		ArrayList<String> tpoUso = generaTipoUso(cotFamiliar.getListaUbicacion());
	
		int totUbicaCotiza = cotFamiliar.getListaUbicacion().size();
		
		actionRequest.setAttribute("UbicacionesTot", cotFamiliar);
		actionRequest.setAttribute("htmlUb", htmlUb);
		actionRequest.setAttribute("niveles", niveles);
		actionRequest.setAttribute("tpoInmueble", tpoInmueble);
		actionRequest.setAttribute("tpoUso", tpoUso);
		actionRequest.setAttribute("totUbicaCotiza", totUbicaCotiza);
	}

	ArrayList<String> generaNiveles(List<Ubicacion> ub) {
		ArrayList<String> listNiveles = new ArrayList<String>();
		try {
			List<Registro> niveles = _CotizadorService.getCatalogo(0, "B", CotizadorServiceKey.TIPO_NIVELES, 1, usuario,
					AdministrarPolizaPortlet73PortletKeys.Pantalla);

			for (Ubicacion u : ub) {
				Registro r = niveles.stream().filter(niveles2 -> u.getNiveles() == niveles2.getIdCatalogoDetalle())
						.findAny().orElse(niveles.get(0));
				listNiveles.add(r.getDescripcion());
			}
		} catch (CotizadorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listNiveles;

	}

	ArrayList<String> generaTipoImbueble(List<Ubicacion> ub) {
		ArrayList<String> listInmuebles = new ArrayList<String>();
		try {

			List<Registro> inmu = _CotizadorService.getCatalogo(0, "B", CotizadorServiceKey.TIPO_INMUEBLE, 1, usuario,
					AdministrarPolizaPortlet73PortletKeys.Pantalla);

			for (Ubicacion u : ub) {
				System.out.println(u.toString());
				System.out.println("tipo inmueble: " +u.getTipoinmueble());
				Registro r = inmu.stream().filter(inmu2 -> u.getTipoInmueble() == inmu2.getIdCatalogoDetalle())
						.findAny().orElse(inmu.get(0));
				listInmuebles.add(r.getValor());
			}
		} catch (CotizadorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listInmuebles;

	}

	ArrayList<String> generaTipoUso(List<Ubicacion> ub) {
		ArrayList<String> listuso = new ArrayList<String>();
		try {

			List<Registro> uso = _CotizadorService.getCatalogo(0, "B", CotizadorServiceKey.TIPO_USO, 1, usuario,
					AdministrarPolizaPortlet73PortletKeys.Pantalla);

			for (Ubicacion u : ub) {
				Registro r = uso.stream().filter(uso2 -> u.getTipoUso() == uso2.getIdCatalogoDetalle()).findAny()
						.orElse(uso.get(0));
				listuso.add(r.getValor());
			}
		} catch (CotizadorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listuso;

	}
	
	void generaHistorialCambios(ActionRequest actionRequest) throws CotizadorException{
		String poliza = ParamUtil.getString(actionRequest, "poliza");
		FoliosPorPoliza360 folios = _CotizadorService.GetConsultaFoliosPoliza(poliza, AdministrarPolizaPortlet73PortletKeys.Pantalla, usuario);
		
		List<Registro> movimientos = _CotizadorService.getCatalogo(0, "B", "CATTIPMOV", 1, usuario,
				AdministrarPolizaPortlet73PortletKeys.Pantalla);
		
		actionRequest.setAttribute("folios", folios);
		actionRequest.setAttribute("movimientos", movimientos);
	}
	
	
	void llenaObjetoGenerico(ActionRequest actionRequest){
		InfoCotizacion inf = new InfoCotizacion();
		inf.setCotizacion(cotizacion);
		inf.setFolio(Long.parseLong(folio));
		inf.setVersion(version);
		TipoCotizacion tpoC = isEmpresarial ? TipoCotizacion.EMPRESARIAL : TipoCotizacion.FAMILIAR;
		
		if(idproducto == 806 || idproducto == 809) {
			inf.setTipoCotizacion(TipoCotizacion.TRANSPORTES);
		}
		else {
			inf.setTipoCotizacion(tpoC);
		}
		
		String infC = CotizadorModularUtil.objtoJson(inf);
		
		actionRequest.setAttribute("infCotizacion", infC);
	}
	
	boolean hayPagosVencidos(List<P360PagosPendientes> list){
		System.out.println("---------------------------------------------------->");
		boolean result = false;
		System.out.println(list.toString());
		for (P360PagosPendientes pagosPendientes : list) {
			System.out.println(pagosPendientes.getEstatus().toLowerCase());
			if(pagosPendientes.getEstatus().toLowerCase().equals("vencido")){
				result = true;
				break;
			}
		}
		return result;
	}
	
	void permisosEndososUsuario(ActionRequest actionRequest){
		String puedeEndosar = "disabled";
		
		try {
			/*
			if(configuracionPermisosEndosos.configuracionActiva()){
				String[] usuariosPermitidos = configuracionPermisosEndosos.emailPermitido();
				String usuarioLog = themeDisplay.getUser().getEmailAddress();
				System.out.println("usuarioLog : "+ usuarioLog);
				for (String string : usuariosPermitidos) {
					System.out.println("email : " + string);
					if(string.equals(usuarioLog)){
						puedeEndosar = "";
						break;
					}
				}
			}else{
				puedeEndosar = "";
			}
			*/
			List<Role> listRoles = themeDisplay.getUser().getRoles();
			for (Role role : listRoles) {
//				System.err.println("Role----------> " + role.getName());
				if( role.getName().equals("TMX-ENDOSOS") ){
//					System.err.println("<-----------Encontre---------->");
					puedeEndosar = "";
					break;
				};
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		System.out.println("puedeEndosar : " + puedeEndosar);
		actionRequest.setAttribute("puedeEndosar", puedeEndosar);
	}

}
