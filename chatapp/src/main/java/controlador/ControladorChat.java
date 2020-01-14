package controlador;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import componenteMensajes.IMensajesListener;
import componenteMensajes.MensajeWhatsApp;
import componenteMensajes.MensajesEvent;
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

public class ControladorChat implements IMensajesListener {
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

	/*private void eliminarBaseDatos() {
		adaptadorUsuario.borrarTodosUsuarios();
	}*/
	
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

	// <------- SET USUARIO Y CONTACTO ACTUAL -------->
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public void setContactoActual(Contacto contacto) {
		this.contactoActual = contacto;
	}
	//------------------------------------------------
	
	//<------- REGISTRO -------->
	public boolean registrarUsuario(String nombre, Date fecha, String telefono, String email, String login, String contraseña, String img) {
		if (catalogoUsuarios.existLoginTelefono(login, telefono))
			return false;

		Usuario usuario = new Usuario(nombre, fecha, telefono, email, login, contraseña, img);
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}
	//------------------------------------------------
	//<------- LOGIN -------->
	public boolean loginUsuario(String login, String contraseña) {
		usuarioActual = catalogoUsuarios.existeUsuario(login, contraseña);
		if (usuarioActual != null) {
			return true;
		}
		return false;
	}
	//------------------------------------------------
	
	// <------- INFORMACIÓN SOBRE EL USUARIO ACTUAL -------->
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public List<Contacto> getContactosUsuarioActual() {
		return usuarioActual.getContactos();
	}
	
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
	public void cambiarFotoUsuario(String rutaFichero) {
		this.usuarioActual.setImg(rutaFichero);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	public void convertirseUsuarioPremium() {
		this.usuarioActual.setPremium(true);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		
	}
	public boolean isUserPremium() {
		return this.usuarioActual.isPremium();
	}
	// <----- END INFORMACIÓN SOBRE EL USUARIO ACTUAL ------>

	// <------- INFORMACIÓN SOBRE EL CONTACTO ACTUAL -------->
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
	public String getNombreContactoActual() {
		return this.contactoActual.getNombre();
	}
	public String getImgContactoActual() {
		if (contactoActual instanceof ContactoIndividual)
			return catalogoUsuarios.getImg(getTelefonoContactoActual());
		else if (contactoActual instanceof Grupo)
			return ((Grupo) contactoActual).getImgGrupo();
		return null;
	}
	// <----- END INFORMACIÓN SOBRE EL CONTACTO ACTUAL ------>
	
	//<------- CONTACTOS -------->
	//<------- CONTACTOS INDIVIDUAL -------->
	public boolean crearContactoIndividual(String nombre, String telefonoUsuario) {
		Usuario usuario = catalogoUsuarios.getUsuarioDesdeTelefono(telefonoUsuario);
		if (usuario == null)
			return false;

		ContactoIndividual contacto = new ContactoIndividual(nombre, usuario);
		if (catalogoUsuarios.existContactoIndividual(usuarioActual, contacto))
			return modificarContactoIndividual(nombre, telefonoUsuario);

		actualizarContactosDesconocidos(contacto);
		adaptadorContacto.registrarContacto(contacto);
		usuarioActual.addContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return true;
	}
	
	public boolean modificarContactoIndividual(String nombre, String telefono)
	{
		ContactoIndividual contacto = catalogoUsuarios.getContactoIndividual(usuarioActual, telefono);
		if (contacto == null) { return false; }
		contacto.setNombre(nombre);
		actualizarContactosDesconocidos(contacto);
		adaptadorContacto.modificarContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return true;
	}
	
	public void eliminarContacto(String nombreContacto) {
		ContactoIndividual contacto = usuarioActual.getContactoWithNombre(nombreContacto);
		if (contacto == null)
			return;

		String telefono = contacto.getTelefonoUsuario();
		modificarContactoIndividual(telefono, telefono);
		eliminarMensajesContacto(contacto);
		usuarioActual.borrarContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	//<----- END CONTACTOS INDIVIDUAL ------>
	//<------- GRUPOS -------->
	public int crearGrupo(Usuario user, String nombre, List<ContactoIndividual> contactos) {
		String img = "/img/defecto.jpg";
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
	
	private void crearGrupoParaMiembro(ContactoIndividual contactoUsuario, Grupo grupo, Usuario administrador) {
		Usuario user = getUsuarioTLF(contactoUsuario.getTelefonoUsuario());

		List<ContactoIndividual> misContactos = obtenerContactosParaMiembro(user, grupo, administrador);
		Grupo grupoContacto = new Grupo(grupo.getNombre(), grupo.getImgGrupo(), misContactos, grupo.getAdministrador());

		user.addContacto(grupoContacto);
		adaptadorContacto.registrarContacto(grupoContacto);

		adaptadorUsuario.modificarUsuario(user);
	}
	
	public void modificarGrupo(Usuario user, String nombreGrupo, Grupo nuevo) {
		List<Grupo> grupos = this.getGrupos(user);
		for (Grupo antiguo : grupos) {
			if (antiguo.getAdministrador().equals(nuevo.getAdministrador()) && antiguo.getNombre().equals(nombreGrupo)) {

				// Este bucle elimina a los miembros que ya no estan en el grupo
				for (ContactoIndividual contacto : antiguo.getParticipantes()) {
					if (nuevo.isParticipante(contacto) == false)
						eliminarGrupoParaMiembro(contacto, antiguo);
				}
				
				//Creacion o modificacion del grupo
				for (ContactoIndividual contacto : nuevo.getParticipantes()) {
					if (antiguo.isParticipante(contacto) == true)
						modificarGrupoParaMiembro(contacto, antiguo, nuevo);
					else
						crearGrupoParaMiembro(contacto, nuevo, user);
				}

				nuevo.setCodigo(antiguo.getCodigo());
				nuevo.setMensajes(antiguo.getMensajes());
				user.borrarContacto(antiguo);
				user.addContacto(nuevo);
				
				adaptadorContacto.modificarContacto(nuevo);
				adaptadorUsuario.modificarUsuario(user);
				
				return;
			}
		}
		setContactoActual(nuevo);
	}
	
	private void eliminarGrupoParaMiembro(ContactoIndividual contactoUsuario, Grupo antiguo) {
		Usuario user = getUsuarioTLF(contactoUsuario.getTelefonoUsuario());
		String nombreGrupo = antiguo.getNombre();
		Usuario admin = antiguo.getAdministrador();
		Grupo grupo = user.getGrupo(nombreGrupo, admin);
		if (grupo != null) {
			eliminarGrupo(user, grupo);
		}
	}
	
	private void eliminarGrupo(Usuario usuario, Grupo grupo) {
		usuario.borrarContacto(grupo);
		adaptadorContacto.borrarContacto(grupo);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	
	public void eliminarGrupo(String nombreGrupoModificar) {
		Grupo grupo = usuarioActual.getGrupo(nombreGrupoModificar, usuarioActual);
		if (grupo == null || !grupo.getAdministrador().getTelefono().equals(usuarioActual.getTelefono()))
			return;
		
		// Este bucle elimina a los miembros del grupo
		for (ContactoIndividual contacto : grupo.getParticipantes()) {
			eliminarGrupoParaMiembro(contacto, grupo);
		}
				
		eliminarGrupo(usuarioActual, grupo);
	}
	
	private void modificarGrupoParaMiembro(ContactoIndividual contactoUsuario, Grupo antiguo, Grupo nuevo) {
		Usuario user = getUsuarioTLF(contactoUsuario.getTelefonoUsuario());
		Contacto antiguoMio = user.getGrupo(antiguo.getNombre(), antiguo.getAdministrador());

		List<ContactoIndividual> misContactos = obtenerContactosParaMiembro(user, nuevo, nuevo.getAdministrador());
		Grupo nuevoMio = new Grupo(nuevo.getNombre(), nuevo.getImgGrupo(), misContactos, nuevo.getAdministrador());
		
		nuevoMio.setCodigo(antiguoMio.getCodigo());
		nuevoMio.setMensajes(antiguoMio.getMensajes());
		
		user.borrarContacto(antiguoMio);
		user.addContacto(nuevoMio);
		adaptadorContacto.modificarContacto(nuevoMio);
		adaptadorUsuario.modificarUsuario(user);
	}
	
	//Función para obtener tu lista de contactos para crear el grupo
	private List<ContactoIndividual> obtenerContactosParaMiembro(Usuario user, Grupo grupo, Usuario administrador) {
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();

		ContactoIndividual cAdmin = catalogoUsuarios.getContactoIndividual(user, administrador.getTelefono());
		// crear el admin si es desconocido
		if (cAdmin == null) {
			cAdmin = new ContactoIndividual(administrador.getTelefono(), administrador);
			adaptadorContacto.registrarContacto(cAdmin);
		}

		contactos.add(cAdmin);
		// registrar los participantes si no los conoce o viceversa
		for (ContactoIndividual e : grupo.getParticipantes()) {
			if (e.getCodigoUsuario() != user.getCodigo()) {
				ContactoIndividual contactoMio = catalogoUsuarios.getContactoIndividual(user, e);
				if (contactoMio != null) {
					contactos.add(contactoMio);
				} else {
					ContactoIndividual desconocido = new ContactoIndividual(e.getTelefonoUsuario(), e.getUsuario());
					adaptadorContacto.registrarContacto(desconocido);
					contactos.add(desconocido);
				}
			}
		}
		return contactos;
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
				return g.getAdministrador().equals(usuario);

			}
		}
		return false;
	}
	//<----- END GRUPOS ------>
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
	public String getImgContacto(Contacto contacto) {
		if (contacto instanceof ContactoIndividual) {
			return ((ContactoIndividual) contacto).getImgUsuario();
		}else if (contacto instanceof Grupo) {
			return ((Grupo) contacto).getImgGrupo();
		}
		return null;
	}
	//<----- END CONTACTOS ------>
	
	//<------- MENSAJES -------->
	private void mandarMensajeContacto(ContactoIndividual contacto, Mensaje mensaje, Usuario mensajero)
	{
		Usuario user = contacto.getUsuario();

		ContactoIndividual contactoMio = catalogoUsuarios.getContactoIndividual(user, mensajero.getTelefono());
		if (contactoMio == null) {
			ContactoIndividual desconocido = new ContactoIndividual(mensajero.getTelefono(), mensajero);
			adaptadorContacto.registrarContacto(desconocido);;
			user.addContacto(desconocido);
			contactoMio = desconocido;
			adaptadorUsuario.modificarUsuario(user);
		}
		contactoMio.addMensaje(mensaje);
		adaptadorContacto.modificarContacto(contactoMio);
	}
	
	private void mandarMensajeGrupo(Grupo grupo, ContactoIndividual contacto, Mensaje mensaje, Usuario mensajero)
	{
		Usuario user = contacto.getUsuario();
		Grupo grupoMio = catalogoUsuarios.getGrupo(user, grupo);
		
		grupoMio.addMensaje(mensaje);
		adaptadorContacto.modificarContacto(grupoMio);
	}
	
	public void mandarMensaje(String sMensaje, int emoticono)
	{
		Mensaje mensaje = new Mensaje(sMensaje,emoticono, usuarioActual, contactoActual);
		
		adaptadorMensaje.registrarMensaje(mensaje);
		if (contactoActual instanceof ContactoIndividual)
			mandarMensajeContacto((ContactoIndividual)contactoActual,mensaje, usuarioActual);
		else if (contactoActual instanceof Grupo)
		{
			Grupo grupo = (Grupo) contactoActual;
			for (ContactoIndividual contacto: grupo.getParticipantes()) {
				if (contacto.getTelefonoUsuario() != usuarioActual.getTelefono())
					mandarMensajeGrupo(grupo, contacto, mensaje, usuarioActual);
			}
		}
		contactoActual.addMensaje(mensaje);
		adaptadorContacto.modificarContacto(contactoActual);
	}
	
	private void mandarMensajeGrupo(Usuario user, Grupo grupo, Mensaje mensaje)
	{
		adaptadorMensaje.registrarMensaje(mensaje);
		for (ContactoIndividual contacto: grupo.getParticipantes()) {
			//if (contacto.getTelefonoUsuario() != user.getTelefono())
			mandarMensajeGrupo(grupo, contacto, mensaje, user);
		}
		
		grupo.addMensaje(mensaje);
		adaptadorContacto.modificarContacto(grupo);
	}
	
	private void mandarMensajeContacto(Usuario user, ContactoIndividual contacto, Mensaje mensaje)
	{
		adaptadorMensaje.registrarMensaje(mensaje);
		
		mandarMensajeContacto(contacto, mensaje, user);

		contacto.addMensaje(mensaje);
		adaptadorContacto.modificarContacto(contacto);
	}

	public List<Mensaje> getConversacionContactoActual() {
		List<Mensaje> mensajes = contactoActual.getMensajes();
		return mensajes;
	}
	
	public List<Mensaje> getMisMensajesConversacionContactoActual() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		
		for (Mensaje mensaje : contactoActual.getMensajes()) {
			if (mensaje.getEmisor() == usuarioActual) {
				mensajes.add(mensaje);
			}
		};
		
		return mensajes;
	}

	public HashMap<Contacto, Mensaje> getUltimosMensajes() {
		HashMap<Contacto, Mensaje> mensajes = usuarioActual.getLastMensajes();
		return mensajes;
	}
	public String getNombrePropietarioMensaje(Usuario emisor) {
		if (contactoActual instanceof ContactoIndividual) {
			return contactoActual.getNombre();
		}
		else if (contactoActual instanceof Grupo) {
			Grupo g = (Grupo) contactoActual;
			for (ContactoIndividual ci : g.getParticipantes()) {
				if (ci.getUsuario().equals(emisor)) {
					return ci.getNombre();
				}
			}
		}
		return emisor.getTelefono();
	}
	private void eliminarMensaje(Contacto contacto, Mensaje mensaje) {
		contacto.removeMensaje(mensaje);
		adaptadorContacto.modificarContacto(contacto);
	}
	private void eliminarMensajeContacto(ContactoIndividual contacto, Mensaje mensaje) {
		Usuario user = contacto.getUsuario();
		Contacto contactoMio = user.getContacto(mensaje.getEmisor().getTelefono());
		eliminarMensaje(contactoMio, mensaje);
	}
	private void eliminarMensajeGrupo(Grupo grupo, ContactoIndividual contacto, Mensaje mensaje) {
		Usuario user = contacto.getUsuario();
		Contacto contactoMio = user.getGrupo(grupo.getNombre(), grupo.getAdministrador());
		eliminarMensaje(contactoMio, mensaje);
	}
	public void eliminarMensaje(Mensaje mensaje, boolean isEliminarParaMi) {
		if (isEliminarParaMi == false) {
			if (contactoActual instanceof Grupo) {
				Grupo grupo = (Grupo) contactoActual;
				for (ContactoIndividual contacto: grupo.getParticipantes()) {
					if (contacto.getTelefonoUsuario() != usuarioActual.getTelefono())
						eliminarMensajeGrupo(grupo, contacto, mensaje);
				}			
			}else {
				eliminarMensajeContacto((ContactoIndividual)contactoActual, mensaje);
			}
			adaptadorMensaje.borrarMensaje(mensaje);
		}

		eliminarMensaje(contactoActual, mensaje);
	}
	private void eliminarMensajesContacto(ContactoIndividual contacto) {
		for (Mensaje mensaje : contacto.getMensajes()) {
			eliminarMensaje(contacto, mensaje);
		}
	}
	//<------- END MENSAJES -------->
	public void nuevosMensajes(MensajesEvent e) {
		Set<String> nombresContactos = new HashSet<String>();
		for (MensajeWhatsApp m : e.getMensajes()) {
			nombresContactos.add(m.getAutor());
		}
		Contacto c;
		//la conversacion es de un grupo
		if (nombresContactos.size() > 2 && (c=usuarioActual.hasGroup(nombresContactos)) != null) {
			for (MensajeWhatsApp m : e.getMensajes()) {
				Grupo g = (Grupo) c;
				Usuario emisor = g.getParticipante(m.getAutor());
				Date fecha = Date.from(m.getFecha().atZone(ZoneId.systemDefault()).toInstant());
				Mensaje mensaje = new Mensaje (m.getTexto(),-1, emisor, c, fecha);
				mandarMensajeGrupo(emisor, g, mensaje);
			}
		}
		//la conversacion es con un contacto individual
		else if (nombresContactos.size() == 2 && (c=usuarioActual.hasContact(nombresContactos))!=null) {
			for (MensajeWhatsApp m : e.getMensajes()) {
				Usuario emisor = null;
				Contacto receptor = null;
				//aqui diferenciamos si el mensaje lo ha mandado el usuario actual o el contacto con el que esta hablando
				if (m.getAutor().equals(usuarioActual.getNombre())){
					emisor = usuarioActual;
					receptor = c;
				}else {
					emisor = ((ContactoIndividual)c).getUsuario();
					receptor = emisor.getContacto(usuarioActual.getTelefono());
				}
				Date fecha = Date.from(m.getFecha().atZone(ZoneId.systemDefault()).toInstant());
				Mensaje mensaje = new Mensaje(m.getTexto(),-1,emisor,c,fecha);
				mandarMensajeContacto(emisor, (ContactoIndividual)receptor, mensaje);
			}
		}
	}
	//conseguir el número de mensajes del usuario actual por mes DEL AÑO ACTUAL, si no tiene en un mes poner 0.
	public int[] getNumeroMensajesPorMeses() {
		int numero[] = new int [12];
		List<Mensaje> mensajes = usuarioActual.getAllMisMensajes();
		LocalDate fechaHoy = LocalDate.now();
		for (Mensaje mensaje : mensajes) {
			LocalDate localDate = mensaje.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (localDate.getYear() == fechaHoy.getYear())
				numero[localDate.getMonthValue()-1]++;
		}
		return numero;
	}
	//Un mapa indexado por los 6 grupos con mas mensajes, y como valor el porcentaje de mensajes que han sido del usuarioActual.
	public HashMap<Grupo, Double> getGruposMasPesados() {
		HashMap<Grupo, Double> gruposMasPesados = new HashMap<Grupo, Double>();
		
		List<Grupo> grupos = usuarioActual.getGrupos();
		grupos.sort((g1, g2) -> Integer.compare(g1.getMensajesCount(), g2.getMensajesCount()));
		int numeroGrupos = 6;
		if (grupos.size() < numeroGrupos)
			numeroGrupos = grupos.size();
		for (int i = 0; i < numeroGrupos; i++) {
			Grupo grupo = grupos.get(i);
			double porcentaje = ((double)grupo.getMensajesCount(usuarioActual)/(double)grupo.getMensajesCount()) * 100;
			gruposMasPesados.put(grupo, porcentaje);
		}
		return gruposMasPesados;
	}
	private String getRealLoginName(String nombreUsuario) {
		ContactoIndividual contacto = usuarioActual.getContactoWithNombre(nombreUsuario);
		if (contacto == null)
			return null;
		Usuario usuario = contacto.getUsuario();
		if (usuario != null)
			return usuario.getLogin();
		return null;
	}
	public List<Mensaje> getMensajesEncontrados(String mensaje, String nombreUsuario, Date f1, Date f2) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		if (contactoActual instanceof Grupo) {
			for (Mensaje mensaje2 : contactoActual.getMensajes()) {
				if (nombreUsuario != null && nombreUsuario.isEmpty() == false) {
					String userLogin = getRealLoginName(nombreUsuario);
					if (userLogin != null && mensaje2.getEmisor().getNombre().equals(userLogin) == false)
						continue;
				}
				if (mensaje != null && mensaje.isEmpty() == false && mensaje2.getTexto().contains(mensaje) == false)
					continue;

				if (f1 != null && f2 != null) {
					LocalDate fecha1 = f1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate fecha2 = f2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate fechaMensaje = mensaje2.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (fechaMensaje.isBefore(fecha1) || fechaMensaje.isAfter(fecha2))
						continue;
				}
				mensajes.add(mensaje2);
			}
		}
		else {
			for (Mensaje mensaje2 : contactoActual.getMensajes()) {
				if (mensaje != null && mensaje2.getTexto().contains(mensaje) == false)
					continue;

				if (f1 != null && f2 != null) {
					LocalDate fecha1 = f1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate fecha2 = f2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate fechaMensaje = mensaje2.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (fechaMensaje.isBefore(fecha1) || fechaMensaje.isAfter(fecha2))
						continue;
				}
				
				mensajes.add(mensaje2);
			}			
		}
		return mensajes;
	}
	public double getPrecioPremium() {
		return this.usuarioActual.calcularPrecio();
	}

}
