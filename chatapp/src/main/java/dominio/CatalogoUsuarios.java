package dominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	private static CatalogoUsuarios unicaInstancia;
	//Indexado por el login
	private HashMap<String, Usuario> usuarios;
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}
	/*devuelve todos los usuarios*/
	public List<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c: usuarios.values()) 
			lista.add(c);
		return lista;
	}
	
	public Usuario getUsuario(int codigo) {
		for (Usuario u: usuarios.values()) {
			if (u.getCodigo()==codigo) return u;
		}
		return null;
	}
	public Usuario getUsuario(String login) {
		return usuarios.get(login); 
	}
	
	public void addUsuario(Usuario u) {
		usuarios.put(u.getTelefono(),u);
	}
	public void removeCliente (Usuario u) {
		usuarios.remove(u.getTelefono());
	}
	
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			 usuarios.put(u.getLogin(),u);
	}

	public void addContacto(Usuario u, Contacto contacto) {
		u.addContacto(contacto);
		usuarios.put(u.getTelefono(),u);	
	}

	public boolean existLogin(String login) {
		return usuarios.get(login) != null;
	}

	public boolean existContacto(Usuario usuario, Contacto contacto) {
		List<Contacto> contactos = usuario.getContactos();
		for (Contacto c : contactos) {
			if (c.equals(contacto))
				return true;
		}
		return false;
	}

	public boolean existeUsuario(String login, String contraseña) {
		boolean existe = false;
		Collection<Usuario> usuarios = this.usuarios.values();
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login) && u.getContraseña().equals(contraseña)) {
				return existe = true;
			}
		}
		return existe;
		
	}

	public boolean existeUsuario(String telefono) {
		List<Usuario> users = this.getUsuarios();
		for(Usuario u : users) {
			if (u.getTelefono().equals(telefono))
				return true;
		}
		return false;
	}

	public String getImg(String tlf) {
		List<Usuario> users = this.getUsuarios();
		for(Usuario u : users) {
			if (u.getTelefono().equals(tlf))
				return u.getImg();
		}
		return null;
	}


}
