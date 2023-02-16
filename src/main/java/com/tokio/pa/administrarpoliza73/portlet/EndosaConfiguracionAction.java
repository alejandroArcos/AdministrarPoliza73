/**
 * 
 */
package com.tokio.pa.administrarpoliza73.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.tokio.pa.administrarpoliza73.constants.AdministrarPolizaPortlet73PortletKeys;
import com.tokio.pa.administrarpoliza73.interfaces.PermisosParaEndosar;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;


/**
 * @author jonathanfviverosmoreno
 *
 */

@Component(
        configurationPid = "com.tokio.endosos.interfaces.permisosEndosos",
        configurationPolicy = ConfigurationPolicy.OPTIONAL, 
        immediate = true,
        property = {
            "javax.portlet.name="+ AdministrarPolizaPortlet73PortletKeys.ADMINISTRARPOLIZAPORTLET73
        },
        service = ConfigurationAction.class
    )

public class EndosaConfiguracionAction extends DefaultConfigurationAction {
	
	@Override
    public void include(PortletConfig portletConfig, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {

        httpServletRequest.setAttribute(PermisosParaEndosar.class.getName(), configuracionPermisosEndosos);

        super.include(portletConfig, httpServletRequest, httpServletResponse);
    }

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        

        String configActiva = ParamUtil.getString(actionRequest, "swichConfiguracionActiva");
        String unit = ParamUtil.getString(actionRequest, "unit");  
        

        
        System.out.println("configActiva : " + configActiva);
        
        setPreference(actionRequest, "configActiva", configActiva);
        setPreference(actionRequest, "unit", unit);
    

        super.processAction(portletConfig, actionRequest, actionResponse);
    }

    /**
     * 
     * (1)If a method is annoted with @Activate then the method will be called at the time of activation of the component
     *  so that we can perform initialization task
     *  
     * (2) This class is annoted with @Component where we have used configurationPid with id com.proliferay.configuration.DemoConfiguration
     * So if we modify any configuration then this method will be called. 
     */
    @Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
        _log.info("#####Calling activate() method######");
        
        configuracionPermisosEndosos = Configurable.createConfigurable(PermisosParaEndosar.class, properties);
        //_demoConfiguration = ConfigurableUtil.createConfigurable(DemoConfiguration.class, properties);
    }

    private static final Log _log = LogFactoryUtil.getLog(EndosaConfiguracionAction.class);

    private volatile PermisosParaEndosar configuracionPermisosEndosos; 

}
