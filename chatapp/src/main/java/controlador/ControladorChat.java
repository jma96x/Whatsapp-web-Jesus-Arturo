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
	
	private ControladorChat() {
		inicializarAdaptadores();
		//eliminarBaseDatos();
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}
	private void eliminarBaseDatos() {
		adaptadorUsuario.borrarTodosUsuarios();
	}
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	public static ControladorChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorChat();
		return unicaInstancia;
	}
	public boolean registrarUsuario(String nombre, Date fecha, String telefono, String email,String login, String contraseña) {
		Usuario usuario = new Usuario(nombre,fecha,telefono, email, login, contraseña);
		if (catalogoUsuarios.existLogin(login))
			return false;
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	public boolean crearContactoIndividual(String nombre, String telefonoUsuario) {
		Contacto contacto = new ContactoIndividual(nombre,telefonoUsuario);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	public boolean crearGrupo(String nombre,List<ContactoIndividual> participantes) {
		Contacto contacto = new Grupo(nombre,participantes);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	public boolean loginUsuario(String login, String contraseña) {
		return catalogoUsuarios.existeUsuario(login,contraseña);
	}
	public Usuario recuperarUsuariodesdeLogin(String login) {
		return catalogoUsuarios.getUsuario(login);
	}
	public boolean esUsuarioRegistrado(String login) {
		return recuperarUsuariodesdeLogin(login) != null;
	}
	
	//<------- INFORMACIÓN SOBRE EL USUARIO ACTUAL -------->
	public List<Contacto> getContactosUsuarioActual() {
		return this.usuarioActual.getContactos();
	}
	public String getNombreUsuarioActual() {
		return this.usuarioActual.getLogin();
	}
	public List<ContactoIndividual> getContactosIndividualesUsuarioActual() {
		List<Contacto> contactos = getContactosUsuarioActual();
		List<ContactoIndividual> contactosIndividuales = new LinkedList<ContactoIndividual>();
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				ContactoIndividual ci = (ContactoIndividual) c;
				contactosIndividuales.add(ci);
			}
		}
		return contactosIndividuales;
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
