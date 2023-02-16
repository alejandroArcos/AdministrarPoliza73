/**
 * 
 */
package com.tokio.pa.administrarpoliza73.interfaces;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author jonathanfviverosmoreno
 *
 */

//@ExtendedObjectClassDefinition(
//category = "TMX", scope = ExtendedObjectClassDefinition.Scope.GROUP
//)

@Meta.OCD(
		id = "com.tokio.endosos.interfaces.permisosEndosos",
		description = "permisos para configurar los endosos"
)
public interface PermisosParaEndosar {
	@Meta.AD(
    		name = "Solo los usuarios de la lista pueden endosar",
    		description = "Si desactiva esta opcion, todos los usuarios con permisos en el modulo pueden endosar ",
    		deflt = "true",
    		required = false
    		)
    public boolean configuracionActiva();
	
	@Meta.AD(
			name = "Usuario con permiso de generar endosos",
	       deflt = "ricardo_sanchez@tokiomarine.com.mx",
	       required = false
	    )
	    public String[] emailPermitido();

	    
}
