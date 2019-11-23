package controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import dominio.CatalogoUsuarios;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
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
	//para poner desde el nombre del contacto el contacto en la interfaz del chat.
	public boolean setContactoActual(String nombre) {
		List<Contacto> contactosUsuario =  usuarioActual.getContactos();
		for (Contacto c: contactosUsuario) {
			if (c.getNombre().equals(nombre)) {
				this.contactoActual = c;
				return true;
			}
		}
		this.contactoActual = null;
		return false;
	}
	public boolean registrarUsuario(String nombre, Date fecha, String telefono, String email,String login, String contraseña, String img) {
		Usuario usuario = new Usuario(nombre,fecha,telefono, email, login, contraseña,img);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	public boolean crearContactoIndividual(String nombre,String telefonoUsuario) {
		ContactoIndividual contacto = new ContactoIndividual(nombre,telefonoUsuario);
		if (catalogoUsuarios.existContactoIndividual(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	/*public boolean crearGrupo(String nombre,String img,List<ContactoIndividual> participantes, String administrador) {
		Contacto contacto = new Grupo(nombre,img, participantes, administrador);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}*/
	/*public void modificarGrupo(Grupo g) {
		adaptadorContacto.modificarContacto(g);
		catalogoUsuarios.addContacto(this.usuarioActual, g);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
	}*/
	public int crearGrupoDesdeUsuario(Usuario user, String nombre, String img,List<ContactoIndividual> contactosSegunUsuario, String administrador) {
		Grupo contacto = new Grupo(nombre, img, contactosSegunUsuario, administrador);
		if (catalogoUsuarios.existGrupo(user,contacto))
			return -1;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(user,contacto);
		adaptadorUsuario.modificarUsuario(user);
		return contacto.getCodigo();
	}
	public void eliminarGrupoDesdeUsuario(Usuario user, String nombreGrupo, String tlfAdministrador) {
		List<Grupo> grupos = this.getGrupos(user);
		Grupo auxiliar = null;
		for (Grupo g : grupos) {
			if (g.getTlfAdministrador().equals(tlfAdministrador) && g.getNombre().equals(nombreGrupo)) {
				auxiliar = g;
			}
		}
		catalogoUsuarios.eliminarGrupo(user.getTelefono(),nombreGrupo,tlfAdministrador);
		adaptadorContacto.borrarContacto(auxiliar);
		adaptadorUsuario.modificarUsuario(user);
	}
	public void modificarGrupoDesdeUsuario(Usuario user, String nombreGrupo, Grupo nuevo) {
		List<Grupo> grupos = this.getGrupos(user);
		for (Grupo g : grupos) {
			if (g.getTlfAdministrador().equals(nuevo.getTlfAdministrador()) && g.getNombre().equals(nombreGrupo)) {
				nuevo.setCodigo(g.getCodigo());
				g = nuevo;
				adaptadorContacto.modificarContacto(g);
				catalogoUsuarios.modificarGrupo(user,g);
				adaptadorUsuario.modificarUsuario(user);
				return;
			}	 
		}
	}
	//para clase login
	public boolean loginUsuario(String login, String contraseña) {
		return catalogoUsuarios.existeUsuario(login,contraseña);
	}
	//Para poner el usuario actual desde el login
	public Usuario recuperarUsuariodesdeLogin(String login) {
		return catalogoUsuarios.getUsuarioDesdeLogin(login);
	}
	//para clase registro
	public boolean esUsuarioRegistrado(String login, String telefono) {
		return catalogoUsuarios.existLoginTelefono(login,telefono);
	}
	//para clase crear contacto
	public boolean existeUsuario(String telefono) {
		return catalogoUsuarios.existeUsuario(telefono);
	}
	//para añadir a los usuarios de un grupo el contacto grupo
	public Usuario getUsuario(String telefonoUsuario) {
		return catalogoUsuarios.getUsuarioDesdeTelefono(telefonoUsuario);
	}
	//para saber si un usuario es admin de un grupo
	public boolean isAdmin(Usuario usuario, String nombreGrupo) {
		for (Contacto c : usuario.getContactos()) {
			if (c instanceof Grupo && c.getNombre().equals(nombreGrupo) ) {
				Grupo g = (Grupo) c;
				System.out.println("Usuario "+usuario.getTelefono() +" "+ "admin " +g.getTlfAdministrador());
				return g.getTlfAdministrador().equals(usuario.getTelefono());
					
			}
		}
		return false;
	}
	//<------- INFORMACIÓN SOBRE EL USUARIO ACTUAL -------->
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	//Para saber los contactos individuales a la hora de crear un grupo
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
	//Para saber los grupos a la hora de modificar uno
	public List<Grupo> getGrupos (Usuario usuario){
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
	//<------- INFORMACIÓN SOBRE EL CONTACTO ACTUAL -------->
	//Para mostrar el telefono en el perfil del contacto
	public String getTelefonoContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return ((ContactoIndividual) contactoActual).getTelefonoUsuario();
		return null;
	}
	//para mostrar el nombre del contacto
	public String getNombreContactoActual() {
		return this.contactoActual.getNombre();
	}
	//para mostrar la imagen del contacto
	public String getImgContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return catalogoUsuarios.getImg(getTelefonoContactoActual());
		else if (contactoActual instanceof Grupo)
			return ((Grupo)contactoActual).getImg();
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
	public void registrarGrupoenParticipantes(List<ContactoIndividual> contactosFinales, String nombreGrupoModificar,
			List<ContactoIndividual> participantesAntiguos, String groupName, String imgGrupo) {
		//Eliminamos el grupo de los que fueron borrados en la actualizacion del grupo
		if (nombreGrupoModificar != null) {
			for (ContactoIndividual antiguo : participantesAntiguos) {
				if (!contactosFinales.contains(antiguo)) {
					Usuario user = ControladorChat.getUnicaInstancia().getUsuario(antiguo.getTelefonoUsuario());
					ControladorChat.getUnicaInstancia().eliminarGrupoDesdeUsuario(user, nombreGrupoModificar,
							usuarioActual.getTelefono());
				}
			}
		}
		// Aqui que ya sabemos que el grupo es registrado en los contactos del
		// administrador (usuarioActual) tenemos que añadir a los contactos de los
		// integrantes del grupo el contacto del grupo
		for (ContactoIndividual cAdmin : contactosFinales) {
			// a este usuario hay que añadirle el grupo segun sus contactos no los del
			// administrador
			Usuario user = ControladorChat.getUnicaInstancia().getUsuario(cAdmin.getTelefonoUsuario());
			// lista vacia para ver que contactos del grupo tiene este usuario registrados
			// (será un subconjunto o el conjunto entero del grupo principal)
			List<ContactoIndividual> contactosSegunUsuario = new LinkedList<ContactoIndividual>();
			List<ContactoIndividual> contactosIndUser = ControladorChat.getUnicaInstancia()
					.getContactosIndividuales(user);
			// Añadir al administrador
			ContactoIndividual administrador;
			for (ContactoIndividual ci : contactosIndUser) {
				if (ci.getTelefonoUsuario().equals(usuarioActual.getTelefono())) {
					contactosSegunUsuario.add(ci);
				}
			}
			// Para cada contacto del usuario no administrador tenemos que comprobar si
			// tiene algun contacto registrado de los usuarios que integran el grupo nuevo.
			for (ContactoIndividual cu : contactosIndUser) {
				for (ContactoIndividual ci : contactosFinales) {
					// Esto quiere decir que ese contacto del grupo este usuario lo tiene en su
					// lista de contactos
					if (cu.getTelefonoUsuario().equals(ci.getTelefonoUsuario()))
						contactosSegunUsuario.add(cu);
				}
			}
			if (nombreGrupoModificar == null) {
				String imgAdmin = usuarioActual.getImg();
				ControladorChat.getUnicaInstancia().crearGrupoDesdeUsuario(user, groupName, imgAdmin,
						contactosSegunUsuario, usuarioActual.getTelefono());
			}
			else {
				// Si se añade un contacto nuevo al grupo
				if (participantesAntiguos.contains(cAdmin)) {
					Grupo nuevoGrupo = new Grupo(groupName, imgGrupo, contactosSegunUsuario, usuarioActual.getTelefono());
					ControladorChat.getUnicaInstancia().modificarGrupoDesdeUsuario(user, nombreGrupoModificar,
							nuevoGrupo);
				}else {
					ControladorChat.getUnicaInstancia().crearGrupoDesdeUsuario(user, groupName, imgGrupo,
							contactosSegunUsuario, usuarioActual.getTelefono());
				}
			}
		}
		
	}
}
