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
	public void addUsuario(Usuario u) {
		usuarios.put(u.getTelefono(),u);
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
	//comprueba que exista un contacto en la lista de contactos de un usuario
	public boolean existContactoIndividual(Usuario usuario, ContactoIndividual contacto) {
		List<Contacto> contactos = usuario.getContactos();
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual && ((ContactoIndividual)c).getTelefonoUsuario().equals(contacto.getTelefonoUsuario()))
				return true;	
		}
		return false;
	}
	
	public ContactoIndividual getContactoIndividual(Usuario usuario, String telefono) {
		List<Contacto> contactos = usuario.getContactos();
		for (Contacto c : contactos) {
			System.out.println("maricon");
			if (c instanceof ContactoIndividual) {
				System.out.println(((ContactoIndividual)c).getTelefonoUsuario()+ " test "+ telefono);
			}
			
			if (c instanceof ContactoIndividual && ((ContactoIndividual)c).getTelefonoUsuario().equals(telefono)) {
				
				return (ContactoIndividual)c;	
			}
		}
		return null;
	}
	
	public ContactoIndividual getContactoIndividual(Usuario usuario, ContactoIndividual contacto) {
		return getContactoIndividual(usuario, contacto.getTelefonoUsuario());	
	}
	
	public boolean existGrupo(Usuario usuario, Grupo contacto) {
		List<Contacto> contactos = usuario.getContactos();
		for (Contacto c : contactos) {
			if (c instanceof Grupo && ((Grupo)c).getAdministrador().equals(contacto.getAdministrador()) && ((Grupo)c).getNombre().equals(contacto.getNombre()) )
				return true;	
		}
		return false;
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
	public void modificarGrupo(Usuario user, Grupo g) {
		for (Contacto c : user.getContactos())
			if (c instanceof Grupo && ((Grupo)c).getCodigo() == g.getCodigo()) {
				((Grupo)c).setParticipantes(g.getParticipantes());
				((Grupo)c).setNombre(g.getNombre());
			}		
	}
	/*Recupera todos los usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			 usuarios.put(u.getTelefono(), u);
	}

	public void eliminarGrupo(String telefono, String nombreGrupo, Usuario admin) {
		Usuario u = usuarios.get(telefono);
		for (Contacto c: u.getContactos()) {
			if (c instanceof Grupo && c.getNombre().equals(nombreGrupo) && ((Grupo) c).getAdministrador().equals(admin))
				u.borrarContacto(c);
		}
		
	}




}
