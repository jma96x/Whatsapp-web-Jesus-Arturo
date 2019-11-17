package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;

public class AdaptadorContactoTDS implements IAdaptadorContactoDAO {
	// Usa un pool para evitar problemas doble referencia con cliente

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoTDS unicaInstancia;

	public static AdaptadorContactoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorContactoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorContactoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra una venta se le asigna un identificador unico */
	public void registrarContacto(Contacto contacto) {
		Entidad eContacto;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		// Crear entidad venta
		eContacto = new Entidad();

		eContacto.setNombre("contacto");
		
		eContacto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("usuario", String.valueOf(contacto.getUsuario().getCodigo())),
						new Propiedad("nombre", contacto.getNombre()))));
		// registrar entidad venta
		eContacto = servPersistencia.registrarEntidad(eContacto);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContacto.getId()); 	
	}

	public void borrarContacto(Contacto contacto) {
		// No se comprueban restricciones de integridad con Cliente
		Entidad eContacto;
		//AdaptadorLineaVentaTDS adaptadorLV = AdaptadorLineaVentaTDS.getUnicaInstancia();

		/*for (LineaVenta lineaVenta : contacto.getLineasVenta()) {
			adaptadorLV.borrarLineaVenta(lineaVenta);
		}*/
		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContacto);

	}

	public void modificarContacto(Contacto contacto) {
		Entidad eContacto;

		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eContacto, "usuario");
		servPersistencia.anadirPropiedadEntidad(eContacto, "usuario", String.valueOf(contacto.getUsuario().getCodigo()));
		servPersistencia.eliminarPropiedadEntidad(eContacto, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContacto, "nombre", contacto.getNombre());
	}

	public Contacto recuperarContacto(int codigo) {
		// Si la entidad estï¿½ en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Contacto) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eContacto = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		// nombre
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
		
		// telefono
		String telefono = "";
		telefono = servPersistencia.recuperarPropiedadEntidad(eContacto, "telefono");
		
		Contacto contacto;
		if (telefono.isEmpty()) {
			contacto = new Grupo(nombre, null);
			contacto.setCodigo(codigo);
		}else {
			contacto = new ContactoIndividual(nombre, "123");
			contacto.setCodigo(codigo);
		}

		//IMPORTANTE:aï¿½adir la venta al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, contacto);

		// recuperar propiedades que son objetos llamando a adaptadores
		// cliente
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario"));
		
		Usuario usuario  = adaptadorUsuario.recuperarUsuario(codigoUsuario);
		contacto.setUsuario(usuario);

		return contacto;
	}

	public List<Contacto> recuperarTodosContactos() {
		List<Contacto> contactos = new LinkedList<Contacto>();
		List<Entidad> eContactos = servPersistencia.recuperarEntidades("contacto");

		for (Entidad eVenta : eContactos) {
			contactos.add(recuperarContacto(eVenta.getId()));
		}
		return contactos;
	}

	// -------------------Funciones auxiliares-----------------------------

}
