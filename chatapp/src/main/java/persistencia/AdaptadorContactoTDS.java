package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;

public class AdaptadorContactoTDS implements IAdaptadorContactoDAO {

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
	
	public void registrarContacto(Contacto contacto) {
		Entidad eContacto;
		boolean existe = true;
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		eContacto = new Entidad();
		eContacto.setNombre("contacto");
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual c = (ContactoIndividual) contacto;
			eContacto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
					new Propiedad("nombre", c.getNombre()), 
					new Propiedad("usuario", String.valueOf(c.getCodigoUsuario())))));
		} else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			String codigosParticipantes = obtenerCodigosContactosIndividuales(g.getParticipantes());
			eContacto.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
					new Propiedad("administrador", String.valueOf(g.getCodigoAdministrador())),
					new Propiedad("nombre", g.getNombre()), 
					new Propiedad("imagen", g.getImg()), 
					new Propiedad("participantes", codigosParticipantes))));
		}
		eContacto = servPersistencia.registrarEntidad(eContacto);
		System.out.println("Se registra el contacto: "+ contacto.getNombre() + " con el codigo "+ eContacto.getId());
		contacto.setCodigo(eContacto.getId());
	}

	public void borrarContacto(Contacto contacto) {
		//TODO Aqui hay que comprobar que cuando borres un grupo no borres los contactos xd
		Entidad eContacto;
		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContacto);

	}

	public void modificarContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		
		servPersistencia.eliminarPropiedadEntidad(eContacto, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContacto, "nombre", contacto.getNombre());
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual c = (ContactoIndividual) contacto;
			servPersistencia.eliminarPropiedadEntidad(eContacto, "usuario");
			servPersistencia.anadirPropiedadEntidad(eContacto, "usuario", String.valueOf(c.getCodigoUsuario()));
		} else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			servPersistencia.eliminarPropiedadEntidad(eContacto, "administrador");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "participantes");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "imagen");

			servPersistencia.anadirPropiedadEntidad(eContacto, "administrador", String.valueOf(g.getCodigoAdministrador()));
			String contactosIndividuales = obtenerCodigosContactosIndividuales(g.getParticipantes());
			servPersistencia.anadirPropiedadEntidad(eContacto, "participantes", contactosIndividuales);
			servPersistencia.anadirPropiedadEntidad(eContacto, "imagen", g.getImg());
		}
		
		String mensajes = obtenerCodigosMensajes(contacto.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContacto, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContacto, "mensajes", mensajes);
	}

	public Contacto recuperarContacto(int codigo) {
		Entidad eContacto = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
		String administrador = "";
		administrador = servPersistencia.recuperarPropiedadEntidad(eContacto, "administrador");
		Contacto contacto;
		if (administrador != null) {
			String contactos = servPersistencia.recuperarPropiedadEntidad(eContacto, "participantes");
			List<ContactoIndividual> listaContactos = obtenerContactosIndividualesDesdeCodigos(contactos);
			int codigoAdmin = Integer.valueOf((String) administrador);
			Usuario admin = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuario(codigoAdmin);
			String img = servPersistencia.recuperarPropiedadEntidad(eContacto, "imagen");
			contacto = new Grupo(nombre, img, listaContactos, admin);
			contacto.setCodigo(codigo);
		} else {
			int cUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario"));
			Usuario user = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuario(cUsuario);
			contacto = new ContactoIndividual(nombre, user);
			contacto.setCodigo(codigo);
		}

		return contacto;
	}

	public List<Contacto> recuperarTodosContactos() {
		List<Contacto> contactos = new LinkedList<Contacto>();
		List<Entidad> eContactos = servPersistencia.recuperarEntidades("contacto");

		for (Entidad c : eContactos) {
			contactos.add(recuperarContacto(c.getId()));
		}
		return contactos;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosMensajes(List<Mensaje> listaMensajes) {
		String aux = "";
		for (Mensaje c : listaMensajes) {
			aux += c.getCodigo() + " ";
		}
		System.out.println(aux.trim());
		return aux.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String mensajes) { 
		List<Mensaje> listaMensaje = new LinkedList<Mensaje>();
		//Si el usuario no tiene contactos
		System.out.println("llego");
		if (mensajes == null)
			return listaMensaje;
		System.out.println("llego2");
		StringTokenizer strTok = new StringTokenizer(mensajes, " ");
		AdaptadorMensajeTDS adaptadorM = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			int id = Integer.valueOf((String) strTok.nextElement());
			System.out.println("mensaje id: "+id);
			listaMensaje.add(adaptadorM.recuperarMensaje(id));
		}
		return listaMensaje;
	}

	private String obtenerCodigosContactosIndividuales(List<ContactoIndividual> listaContactos) {
		String aux = "";
		for (ContactoIndividual c : listaContactos) {
			aux += c.getCodigo() + " ";
		}
		return aux.trim();
	}

	private List<ContactoIndividual> obtenerContactosIndividualesDesdeCodigos(String contactos) {
		List<ContactoIndividual> listaContactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(contactos, " ");
		AdaptadorContactoTDS adaptadorC = AdaptadorContactoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaContactos.add(
					(ContactoIndividual) adaptadorC.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaContactos;
	}
}
