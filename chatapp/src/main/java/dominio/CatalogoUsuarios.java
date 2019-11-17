package dominio;

import java.util.ArrayList;
import java.util.HashMap;
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
	public List<Usuario> getClientes(){
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
	public Usuario getUsuario(String telefono) {
		return usuarios.get(telefono); 
	}
	
	public void addCliente(Usuario u) {
		usuarios.put(u.getTelefono(),u);
	}
	public void removeCliente (Usuario u) {
		usuarios.remove(u.getTelefono());
	}
	
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			 usuarios.put(u.getTelefono(),u);
	}
	

}
