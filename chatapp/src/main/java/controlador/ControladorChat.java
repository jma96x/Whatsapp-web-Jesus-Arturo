package controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;

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
		if (catalogoUsuarios.existLogin(login))
			return false;
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	public boolean crearContactoIndividual(String nombre,String telefonoUsuario, Usuario usuario) {
		Contacto contacto = new ContactoIndividual(nombre,telefonoUsuario,usuario);
		if (catalogoUsuarios.existContacto(this.usuarioActual,contacto))
			return false;
		adaptadorContacto.registrarContacto(contacto);
		catalogoUsuarios.addContacto(this.usuarioActual,contacto);
		adaptadorUsuario.modificarUsuario(this.usuarioActual);
		return true;
	}
	public boolean crearGrupo(String nombre,String img,List<ContactoIndividual> participantes, Usuario administrador) {
		Contacto contacto = new Grupo(nombre,img,participantes, administrador);
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
	public boolean existeUsuario(String telefono) {
		return catalogoUsuarios.existeUsuario(telefono);
	}
	public String getImgUsuario(String tlf) {
		return this.catalogoUsuarios.getImg(tlf);
		
	}
	public Usuario getUsuarioDesdeTelefono(String telefono) {
		return this.catalogoUsuarios.getUsuarioDesdeTelefono(telefono);
	}
	//<------- INFORMACIÓN SOBRE EL USUARIO ACTUAL -------->
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	public List<ContactoIndividual> getContactosIndividualesUsuarioActual() {
		List<Contacto> contactos = this.usuarioActual.getContactos();
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
	public boolean existContactoActual() {
		return this.usuarioActual != null;
	}
	public String getNombreContactoActual() {
		return this.contactoActual.getNombre();
	}
	public String getImgContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return ((ContactoIndividual)contactoActual).getUsuario().getImg();
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
