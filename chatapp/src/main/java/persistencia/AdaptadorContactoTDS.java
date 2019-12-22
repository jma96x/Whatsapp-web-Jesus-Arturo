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
					new Propiedad("telefono", c.getTelefonoUsuario()),
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
		if (contacto instanceof ContactoIndividual) {
			ContactoIndividual c = (ContactoIndividual) contacto;
			servPersistencia.eliminarPropiedadEntidad(eContacto, "usuario");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "nombre");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "telefono");
			servPersistencia.anadirPropiedadEntidad(eContacto, "usuario", String.valueOf(c.getCodigoUsuario()));
			servPersistencia.anadirPropiedadEntidad(eContacto, "nombre", c.getNombre());
			servPersistencia.anadirPropiedadEntidad(eContacto, "telefono", c.getTelefonoUsuario());
		} else if (contacto instanceof Grupo) {
			Grupo g = (Grupo) contacto;
			servPersistencia.eliminarPropiedadEntidad(eContacto, "nombre");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "administrador");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "participantes");
			servPersistencia.eliminarPropiedadEntidad(eContacto, "imagen");
			
			servPersistencia.anadirPropiedadEntidad(eContacto, "nombre", g.getNombre());
			servPersistencia.anadirPropiedadEntidad(eContacto, "administrador", String.valueOf(g.getCodigoAdministrador()));
			String contactosIndividuales = obtenerCodigosContactosIndividuales(g.getParticipantes());
			servPersistencia.anadirPropiedadEntidad(eContacto, "participantes", contactosIndividuales);
			servPersistencia.anadirPropiedadEntidad(eContacto, "imagen", g.getImg());
		}

	}

	public Contacto recuperarContacto(int codigo) {
		Entidad eContacto = servPersistencia.recuperarEntidad(codigo);
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
		String telefono = "";
		telefono = servPersistencia.recuperarPropiedadEntidad(eContacto, "telefono");
		Contacto contacto;
		if (telefono == null) {
			String contactos = servPersistencia.recuperarPropiedadEntidad(eContacto, "participantes");
			List<ContactoIndividual> listaContactos = obtenerContactosIndividualesDesdeCodigos(contactos);
			String codigoAdminCast = servPersistencia.recuperarPropiedadEntidad(eContacto, "administrador");
			int codigoAdmin = Integer.valueOf((String) codigoAdminCast);
			Usuario admin = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuario(codigoAdmin);
			String img = servPersistencia.recuperarPropiedadEntidad(eContacto, "imagen");
			contacto = new Grupo(nombre, img, listaContactos, admin);
			contacto.setCodigo(codigo);
		} else {
			int cUsuario = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario"));
			Usuario user = AdaptadorUsuarioTDS.getUnicaInstancia().recuperarUsuario(cUsuario);
			contacto = new ContactoIndividual(nombre, telefono, user);
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
