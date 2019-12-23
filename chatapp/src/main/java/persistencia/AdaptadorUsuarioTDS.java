package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.Usuario;
import dominio.Contacto;
import dominio.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;
	private SimpleDateFormat dateFormat;
	
	//singleton
	public static AdaptadorUsuarioTDS getUnicaInstancia() { 
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}
	private AdaptadorUsuarioTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}
	/* cuando se registra un cliente se le asigna un identificador único */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true; 
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		// crear entidad Cliente
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
							new Propiedad("fecha_nacimiento",dateFormat.format(usuario.getFechaNacimiento())),
							new Propiedad("telefono", usuario.getTelefono()),
							new Propiedad("email",usuario.getEmail()),
							new Propiedad("login",usuario.getLogin()),
							new Propiedad("imagen",usuario.getImg()),
							new Propiedad("contraseña",usuario.getContraseña()))));
		// registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 
	}
	public void borrarUsuario(Usuario usuario) {
		//hay que comprobar restricciones de integridad con contactos y grupos (más adelante)
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.borrarEntidad(eUsuario);
		
	}
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fecha_nacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fecha_nacimiento",dateFormat.format(usuario.getFechaNacimiento()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "telefono");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "telefono", usuario.getTelefono());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "email");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "email", usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contraseña");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contraseña", usuario.getContraseña());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImg());
		String contactos = obtenerCodigosContactos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", contactos);
	}
	public Usuario recuperarUsuario(int codigo) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		String nombre; 
		String fecha;
		String telefono; 
		String email;
		String login;
		String contraseña ;
		String imagen;
		List<Contacto> contactos;
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		fecha = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fecha_nacimiento");
		telefono = servPersistencia.recuperarPropiedadEntidad(eUsuario, "telefono");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		login = servPersistencia.recuperarPropiedadEntidad(eUsuario, "login");
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date fechaNacimiento= null;
		try {
			fechaNacimiento = formatter.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Usuario usuario = new Usuario(nombre,fechaNacimiento,telefono,email,login,contraseña,imagen);
		usuario.setCodigo(codigo);
		
		// IMPORTANTE:añadir el cliente al pool antes de llamar a otros adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);

		//Aqui hay que recuperar los contactos "Funcion obtenerContactosDesdeCódigo" 
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		usuario.addContactos(contactos);
		
		System.out.println(nombre);
		
		return usuario;
	}
	public List<Usuario> recuperarTodosUsuarios() {
		List<Entidad> eUsuarios = new LinkedList<Entidad>();
		eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}
	
	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosContactos(List<Contacto> listaContactos) {
		String aux = "";
		for (Contacto c : listaContactos) {
			aux += c.getCodigo() + " ";
		}
		return aux.trim();
	}
	
	private List<Contacto> obtenerContactosDesdeCodigos(String contactos) { 
		List<Contacto> listaContactos = new LinkedList<Contacto>();
		//Si el usuario no tiene contactos
		if (contactos == null)
			return listaContactos;
		StringTokenizer strTok = new StringTokenizer(contactos, " ");
		AdaptadorContactoTDS adaptadorC = AdaptadorContactoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaContactos.add(adaptadorC.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaContactos;
	}
	
	public void borrarTodosUsuarios() {
		List<Entidad> eUsuarios = new LinkedList<Entidad>();
		eUsuarios = servPersistencia.recuperarEntidades("usuario");
		for (Entidad eUsuario : eUsuarios) {
			servPersistencia.borrarEntidad(eUsuario);
		}
	}
}
