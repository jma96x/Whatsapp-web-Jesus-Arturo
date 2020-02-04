package dominio;


import java.util.Collection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	private static CatalogoUsuarios unicaInstancia;
	//Indexado por el telefono
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
		List<Usuario> lista = new LinkedList<Usuario>();
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
	public boolean addUsuario(Usuario u) {
		if (existLoginTelefono(u.getLogin(), u.getTelefono()))
			return false;
		usuarios.put(u.getTelefono(),u);
		return true;
	}
	public void removeCliente (Usuario u) {
		usuarios.remove(u.getTelefono());
	}
	public void addContacto(Usuario u, Contacto contacto) {
		usuarios.put(u.getTelefono(),u);	
	}
	public Usuario getUsuarioDesdeLogin(String login) {
		List<Usuario> usuarios = this.getUsuarios();
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login))
				return u;
		}
		return null;
	}
	//Para el registro
	public boolean existLoginTelefono(String login, String telefono) {
		return usuarios.get(telefono) != null || getUsuarioDesdeLogin(login) != null;
	}
	
	//Comprueba que exista un usuario en la app
	public Usuario existeUsuario(String login, String contraseña) {
		Collection<Usuario> usuarios = this.usuarios.values();
		for (Usuario u : usuarios) {
			if (u.getLogin().equals(login) && u.getContraseña().equals(contraseña)) {
				return u;
			}
		}
		return null;
	}
	//Para comprobar si el contacto que un usuario quiere crear existe o no
	public boolean existeUsuario(String telefono) {
		return this.usuarios.get(telefono) != null;
	}
	//mostrar imágenes en interfaz del contacto actual
	public String getImg(String tlf) {
		return this.usuarios.get(tlf).getImg();
	}
	public Usuario getUsuarioDesdeTelefono(String telefonoUsuario) {
		return usuarios.get(telefonoUsuario);
	}
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			 usuarios.put(u.getTelefono(), u);
	}




}
