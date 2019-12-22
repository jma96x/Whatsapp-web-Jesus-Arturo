package controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import dominio.CatalogoUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorChat {
	private static ControladorChat unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoDAO adaptadorContacto;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private Usuario usuarioActual;
	private Contacto contactoActual;

	private ControladorChat() {
		inicializarAdaptadores();
	    //eliminarBaseDatos();
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	public static ControladorChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorChat();
		return unicaInstancia;
	}

	private void eliminarBaseDatos() {
		adaptadorUsuario.borrarTodosUsuarios();
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	// para poner desde el nombre del contacto el contacto en la interfaz del chat.
	public void setContactoActual(Contacto contacto) {
		this.contactoActual = contacto;
	}

	// para clase registro
	public boolean registrarUsuario(String nombre, Date fecha, String telefono, String email, String login,
			String contraseña, String img) {
		if (catalogoUsuarios.existLoginTelefono(login, telefono))
			return false;

		Usuario usuario = new Usuario(nombre, fecha, telefono, email, login, contraseña, img);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}

	private void actualizarContactosDesconocidos(ContactoIndividual nuevo) {
		List<Grupo> grupos = this.usuarioActual.getGrupos();
		int codigo = -1;
		for (Grupo g : grupos) {
			if ((codigo = g.modificarMiembro(nuevo))!= -1) {
				nuevo.setCodigo(codigo);
				adaptadorContacto.modificarContacto(nuevo);
			}
		}
	}

	// para clase crear contacto
	public boolean crearContactoIndividual(String nombre, String telefonoUsuario) {
		Usuario usuario = catalogoUsuarios.getUsuarioDesdeTelefono(telefonoUsuario);
		if (usuario == null)
			return false;

		ContactoIndividual contacto = new ContactoIndividual(nombre, telefonoUsuario, usuario);
		if (catalogoUsuarios.existContactoIndividual(usuarioActual, contacto))
			return false;

		actualizarContactosDesconocidos(contacto);
		adaptadorContacto.registrarContacto(contacto);
		// catalogoUsuarios.addContacto(usuarioActual, contacto);
		usuarioActual.addContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return true;
	}

	public boolean crearContactoDesconocido(ContactoIndividual desconocido) {
		adaptadorContacto.registrarContacto(desconocido);
		// catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		return true;
	}

	private List<ContactoIndividual> obtenerContactosParaMiembro(Usuario user, Grupo grupo, Usuario administrador) {
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();

		ContactoIndividual cAdmin = catalogoUsuarios.getContactoIndividual(user, administrador.getTelefono());
		// crear el admin desconocido si no lo conoces
		if (cAdmin == null) {
			cAdmin = new ContactoIndividual(administrador.getTelefono(), administrador.getTelefono(), administrador);
			crearContactoDesconocido(cAdmin);
		}

		contactos.add(cAdmin);
		// registrar los participantes si no los conoce o viceversa
		for (ContactoIndividual e : grupo.getParticipantes()) {
			if (e.getCodigoUsuario() != user.getCodigo()) {
				ContactoIndividual contactoMio = catalogoUsuarios.getContactoIndividual(user, e);
				if (contactoMio != null) {
					contactos.add(contactoMio);
				} else {
					ContactoIndividual desconocido = new ContactoIndividual(e.getTelefonoUsuario(),
							e.getTelefonoUsuario(), null);
					crearContactoDesconocido(desconocido);
					contactos.add(desconocido);
				}
			}
		}
		return contactos;
	}

	private void crearGrupoParaMiembro(ContactoIndividual contactoUsuario, Grupo grupo, Usuario administrador) {
		Usuario user = ControladorChat.getUnicaInstancia().getUsuarioTLF(contactoUsuario.getTelefonoUsuario());

		List<ContactoIndividual> misContactos = obtenerContactosParaMiembro(user, grupo, administrador);
		Grupo grupoContacto = new Grupo(grupo.getNombre(), grupo.getImg(), misContactos, grupo.getAdministrador());

		user.addContacto(grupoContacto);
		adaptadorContacto.registrarContacto(grupoContacto);

		adaptadorUsuario.modificarUsuario(user);
	}

	private void modificarGrupoParaMiembro(ContactoIndividual contactoUsuario, Grupo antiguo, Grupo nuevo) {
		Usuario user = ControladorChat.getUnicaInstancia().getUsuarioTLF(contactoUsuario.getTelefonoUsuario());
		boolean añadido = false;
		int modificado = 0;
		boolean eliminado = false;
		for (ContactoIndividual ci : antiguo.getParticipantes()) {
			if (ci.getTelefonoUsuario().equals(user.getTelefono())) {
				modificado = 1; // Esto significa que estaba en el grupo antiguo
			}
		}
		for (ContactoIndividual ci : nuevo.getParticipantes()) {
			if (ci.getTelefonoUsuario().equals(user.getTelefono()) && modificado == 0) {
				añadido = true; // Esto significa que el usuario es nuevo en el grupo
			} else if (ci.getTelefonoUsuario().equals(user.getTelefono()) && modificado == 1) {
				modificado = 2; // Esto significa que permanece en el grupo nuevo
			}
		}
		if (!añadido && modificado == 1) { // Si no soy nuevo y no estoy en el nuevo es que me han eliminado
			eliminado = true;
		}
		if (modificado == 2) { // Tengo que actualizar mi grupo
			List<ContactoIndividual> misContactos = obtenerContactosParaMiembro(user, nuevo, nuevo.getAdministrador());
			Grupo actualizado = new Grupo(nuevo.getNombre(), nuevo.getImg(), misContactos, nuevo.getAdministrador());
			Grupo g = user.getGrupo(antiguo.getNombre(), antiguo.getAdministrador());
			actualizado.setCodigo(g.getCodigo());
			g = actualizado;
			adaptadorContacto.modificarContacto(g);
			//catalogoUsuarios.modificarGrupo(user, g);
			adaptadorUsuario.modificarUsuario(user);

		} else if (añadido) { // Tengo que crear nuevo grupo
			crearGrupoParaMiembro(contactoUsuario, nuevo, nuevo.getAdministrador());
		} else if (eliminado) { // Tengo que borrar el grupo
			eliminarGrupoParaMiembro(user, antiguo.getNombre(), antiguo.getAdministrador());
		}

	}

	public int crearGrupo(Usuario user, String nombre, List<ContactoIndividual> contactos) {
		String img = user.getImg();
		Grupo grupo = new Grupo(nombre, img, contactos, user);
		if (catalogoUsuarios.existGrupo(user, grupo))
			return -1;

		usuarioActual.addContacto(grupo);
		adaptadorContacto.registrarContacto(grupo);

		adaptadorUsuario.modificarUsuario(user);

		for (ContactoIndividual contacto : contactos) {
			crearGrupoParaMiembro(contacto, grupo, usuarioActual);
		}

		return grupo.getCodigo();
	}

	// TODO quitar la eliminación del catálogo e implementarla en este método.
	public void eliminarGrupoParaMiembro(Usuario user, String nombreGrupo, Usuario admin) {
		List<Grupo> grupos = this.getGrupos(user);
		Grupo auxiliar = null;
		for (Grupo g : grupos) {
			if (g.getAdministrador().equals(admin) && g.getNombre().equals(nombreGrupo)) {
				auxiliar = g;
			}
		}
		catalogoUsuarios.eliminarGrupo(user.getTelefono(), nombreGrupo, admin);
		adaptadorContacto.borrarContacto(auxiliar);
		adaptadorUsuario.modificarUsuario(user);
	}

	public void modificarGrupo(Usuario user, String nombreGrupo, Grupo nuevo) {
		List<Grupo> grupos = this.getGrupos(user);
		for (Grupo g : grupos) {
			if (g.getAdministrador().equals(nuevo.getAdministrador()) && g.getNombre().equals(nombreGrupo)) {
				// Este bucle elimina los grupos de los contactos caidos en combate
				for (ContactoIndividual contacto : g.getParticipantes()) {
					modificarGrupoParaMiembro(contacto, g, nuevo);
				}
				// Este bucle añade o actualiza a los contactos que siguen en la lucha
				for (ContactoIndividual contacto : nuevo.getParticipantes()) {
					if (contacto.getCodigoUsuario() != user.getCodigo())
						modificarGrupoParaMiembro(contacto, g, nuevo);
				}
				nuevo.setCodigo(g.getCodigo());
				g = nuevo;
				adaptadorContacto.modificarContacto(g);
				catalogoUsuarios.modificarGrupo(user, g);
				adaptadorUsuario.modificarUsuario(user);
				return;
			}
		}
	}

	// para clase login
	public boolean loginUsuario(String login, String contraseña) {
		usuarioActual = catalogoUsuarios.existeUsuario(login, contraseña);
		if (usuarioActual != null) {
			return true;
		}
		return false;
	}

	// para añadir a los usuarios de un grupo el contacto grupo
	public Usuario getUsuarioTLF(String telefonoUsuario) {
		return catalogoUsuarios.getUsuarioDesdeTelefono(telefonoUsuario);
	}
	// para saber si un usuario es admin de un grupo
	public boolean isAdmin(Usuario usuario, String nombreGrupo) {
		for (Contacto c : usuario.getContactos()) {
			if (c instanceof Grupo && c.getNombre().equals(nombreGrupo)) {
				Grupo g = (Grupo) c;
				// System.out.println("Usuario "+usuario.getTelefono() +" "+ "admin "
				// +g.getAdministrador().getTelefono());
				return g.getAdministrador().equals(usuario);

			}
		}
		return false;
	}

	// <------- INFORMACIÓN SOBRE EL USUARIO ACTUAL -------->
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public List<Contacto> getContactosUsuarioActual() {
		return usuarioActual.getContactos();
	}
	// Para saber los contactos individuales a la hora de crear un grupo
	public List<ContactoIndividual> getContactosIndividuales(Usuario usuario) {
		List<Contacto> contactos = usuario.getContactos();
		List<ContactoIndividual> contactosIndividuales = new LinkedList<ContactoIndividual>();
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				contactosIndividuales.add(ci);
			}
		}
		return contactosIndividuales;
	}

	// Para saber los grupos a la hora de modificar uno
	public List<Grupo> getGrupos(Usuario usuario) {
		List<Contacto> contactos = usuario.getContactos();
		List<Grupo> grupos = new LinkedList<Grupo>();
		for (Contacto c : contactos) {
			if (c instanceof Grupo) {
				Grupo ci = (Grupo) c;
				grupos.add(ci);
			}
		}
		return grupos;
	}

	// <------- INFORMACIÓN SOBRE EL CONTACTO ACTUAL -------->
	// Para mostrar el telefono en el perfil del contacto
	public Contacto getContactoActual() {
		return this.contactoActual;
	}
	public boolean existContactoActual() {
		return this.contactoActual != null;
	}
	public String getTelefonoContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return ((ContactoIndividual) contactoActual).getTelefonoUsuario();
		return null;
	}

	// para mostrar el nombre del contacto
	public String getNombreContactoActual() {
		return this.contactoActual.getNombre();
	}

	// para mostrar la imagen del contacto
	public String getImgContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return catalogoUsuarios.getImg(getTelefonoContactoActual());
		else if (contactoActual instanceof Grupo)
			return ((Grupo) contactoActual).getImg();
		return null;
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContacto = factoria.getContactoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	
	//Mandar Mensaje
	private void mandarMensajeContacto(ContactoIndividual contacto, Mensaje mensaje)
	{
		Usuario user = getUsuarioTLF(contacto.getTelefonoUsuario());

		ContactoIndividual contactoMio = catalogoUsuarios.getContactoIndividual(user, contacto);
		if (contactoMio == null) {
			ContactoIndividual desconocido = new ContactoIndividual(contacto.getTelefonoUsuario(), contacto.getTelefonoUsuario(), null);
			crearContactoDesconocido(desconocido);
			user.addContacto(desconocido);
		}
		
		user.addMessage(mensaje);

		adaptadorUsuario.modificarUsuario(user);
	}
	
	public void mandarMensaje(String sMensaje, int emoticono)
	{
		Mensaje mensaje = new Mensaje(sMensaje,emoticono, usuarioActual, contactoActual);
		
		adaptadorMensaje.registrarMensaje(mensaje);
		
		if (contactoActual instanceof ContactoIndividual)
			mandarMensajeContacto((ContactoIndividual)contactoActual,mensaje);
		else if (contactoActual instanceof Grupo)
		{
			Grupo grupo = (Grupo) contactoActual;
			for (ContactoIndividual contacto: grupo.getParticipantes()) {
				if (contacto.getTelefonoUsuario() != usuarioActual.getTelefono())
					mandarMensajeContacto(contacto, mensaje);
			}
		}
		usuarioActual.addMessage(mensaje);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}

	public List<Mensaje> getConversacionContactoActual() {
		List<Mensaje> mensajes = usuarioActual.getMensajes(contactoActual);
		return mensajes;
	}

	public HashMap<Contacto, Mensaje> getUltimosMensajes() {
		HashMap<Contacto, Mensaje> mensajes = usuarioActual.getLastMensajes();
		return mensajes;
	}

	public String getImgContacto(Contacto contacto) {
		if (contacto instanceof ContactoIndividual) {
			return ((ContactoIndividual) contacto).getImgUsuario();
		}else if (contacto instanceof Grupo) {
			return ((Grupo) contacto).getImgGrupo();
		}
		return null;
	}


}
