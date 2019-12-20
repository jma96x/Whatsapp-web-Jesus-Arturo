package dominio;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Usuario {
	private int codigo;
	private String email;
	private String nombre;
	private String contraseña;
	private String telefono;
	private String login;
	private String img;
	private Date fechaNacimiento;
	private List<Contacto> contactos;
	private Map<Contacto,List<Mensaje>> mensajesEnviados;
	private Map<Contacto,List<Mensaje>> mensajesRecibidos;
	private boolean premium;
	
	public Usuario(String nombre, Date fecha, String telefono, String email,String login, String contraseña, String img) {
		this.email = email;
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.telefono = telefono;
		this.fechaNacimiento = fecha;
		this.login = login;
		this.img = img;
		this.contactos = new LinkedList<Contacto>();
		this.mensajesEnviados = new HashMap<Contacto,List<Mensaje>>();
		this.mensajesRecibidos = new HashMap<Contacto,List<Mensaje>>();
		this.premium = false;
	}
	public int getCodigo() {
		return codigo;
	}
	public String getContraseña() {
		return contraseña;
	}
	public String getEmail() {
		return email;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public String getNombre() {
		return nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getLogin() {
		return login;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public boolean isPremium() {
		return this.premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	//Contactos
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(this.contactos);
	}
	public void addContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	public void addContacto(Contacto contacto) {
		this.contactos.add(contacto);
	}
	public void borrarContacto(Contacto c) {
		this.contactos.remove(c);
	}
	public Grupo getGrupo(String grupoAntiguo, Usuario administrador) {
		for(Contacto c : contactos) {
			if (c instanceof Grupo && ((Grupo)c).getNombre().equals(grupoAntiguo) &&  ((Grupo) c).getAdministrador().equals(administrador)) {
				return (Grupo) c;
			}
		}
		return null;
	}
	public List<Grupo> getGrupos (){
		LinkedList<Grupo> grupos = new LinkedList<Grupo>();
		for (Contacto c : contactos) {
			if (c instanceof Grupo) {
				grupos.add((Grupo)c);
			}
		}
		return grupos;
	}
	//--------------
	//Mensajes
	public void añadirMensajesEnviados(Contacto c, List<Mensaje> mensajes) {
		this.mensajesEnviados.put(c, mensajes);
	}
	public void añadirMensajesRecibido(Contacto c, List<Mensaje> mensajes) {
		this.mensajesRecibidos.put(c, mensajes);
	}
	public List<Mensaje> getMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		for (List<Mensaje> mensaje : this.mensajesEnviados.values()) {
			mensajes.addAll(mensaje);
		}
		for (List<Mensaje> mensaje : this.mensajesRecibidos.values()) {
			mensajes.addAll(mensaje);
		}
		return mensajes;
	}
	public List<Mensaje> getMensajes(Contacto contacto) {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Mensaje> mensajesEnviados = this.mensajesEnviados.get(contacto);
		if (mensajesEnviados != null)
				mensajes.addAll(mensajesEnviados);

		List<Mensaje> mensajeRecibidos = this.mensajesRecibidos.get(contacto);
		if (mensajeRecibidos != null)
			mensajes.addAll(mensajeRecibidos);

		return mensajes;
	}
	public List<Mensaje> getLastMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		for (List<Mensaje> mensaje : this.mensajesEnviados.values()) {
			if (mensaje != null && mensaje.size() > 0)
				mensajes.add(mensaje.get(mensaje.size()-1));
		}
		for (List<Mensaje> mensaje : this.mensajesRecibidos.values()) {
			if (mensaje != null && mensaje.size() > 0)
				mensajes.add(mensaje.get(mensaje.size()-1));
		}
		return mensajes;
	}
	public Map<Contacto,List<Mensaje>> getMensajesEnviados() {
		return new HashMap<Contacto,List<Mensaje>>(this.mensajesEnviados);
	}
	public Map<Contacto,List<Mensaje>> getMensajesRecibidos() {
		return new HashMap<Contacto,List<Mensaje>>(this.mensajesRecibidos);
	}	
	private void añadirMensajeEnviado(Contacto c, Mensaje mensaje) {
		List<Mensaje> mensajes = this.mensajesEnviados.get(c);
		if (mensajes != null)
			mensajes.add(mensaje);
		else
		{
			mensajes = new LinkedList<Mensaje>();
			mensajes.add(mensaje);
			this.mensajesEnviados.put(c, mensajes);
		}
	}
	private void añadirMensajeRecibido(Contacto c, Mensaje mensaje) {
		List<Mensaje> mensajes = this.mensajesRecibidos.get(c);
		if (mensajes != null)
			mensajes.add(mensaje);
		else
		{
			mensajes = new LinkedList<Mensaje>();
			mensajes.add(mensaje);
			this.mensajesRecibidos.put(c, mensajes);
		}
	}
	public void addMessage(Mensaje mensaje) {
		if (mensaje.getEmisor().getTelefono() == this.telefono)
			añadirMensajeEnviado(mensaje.getDestino(), mensaje);
		else
			añadirMensajeRecibido(mensaje.getDestino(), mensaje);
	}
	public void addMessages(List<Mensaje> mensajes) {
		for (Mensaje mensaje : mensajes) {
			addMessage(mensaje);
		}
	}
	//--------------
	@Override
	public boolean equals(Object u) {
		//TODO equals en contacto
		if (u instanceof Usuario) {
			Usuario user = (Usuario) u; 
			return this.nombre.equals(user.getNombre()) && this.telefono.equals(user.getTelefono()) 
			&& this.contraseña.equals(user.getContraseña()) && this.codigo == user.getCodigo() && this.login.equals(user.getLogin());
		}
		return false;
	}
	
}
