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
	public void setContactoActual(String nombre) {
		List<Contacto> contactosUsuario =  usuarioActual.getContactos();
		for (Contacto c: contactosUsuario) {
			if (c.getNombre().equals(nombre)) {
				this.contactoActual = c;
				return;
			}
		}
	}
	public boolean registrarUsuario(String nombre, Date fecha, String telefono, String email,String login, String contraseña, String img) {
		Usuario usuario = new Usuario(nombre,fecha,telefono, email, login, contraseña,img);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	public boolean crearContactoIndividual(String nombre,String telefonoUsuario) {
		Contacto contacto = new ContactoIndividual(nombre,telefonoUsuario);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	public boolean crearGrupo(String nombre,String img,List<ContactoIndividual> participantes, String administrador) {
		Contacto contacto = new Grupo(nombre,img, participantes, administrador);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	public void crearGrupoDesdeUsuario(Usuario user, String nombre, String img,List<ContactoIndividual> contactosSegunUsuario, String administrador) {
		Contacto contacto = new Grupo(nombre, img, contactosSegunUsuario, administrador);
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(user,contacto);
		adaptadorUsuario.modificarUsuario(user);
		
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
}
