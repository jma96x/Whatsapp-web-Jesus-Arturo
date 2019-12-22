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
	public HashMap<Contacto, Mensaje> getLastMensajes() {
		HashMap<Contacto, Mensaje> mensajes = new HashMap<Contacto, Mensaje>();
		for (Contacto contacto: this.mensajesEnviados.keySet())
		{
			List<Mensaje> mensajesRecibidos = this.mensajesRecibidos.get(contacto);
			List<Mensaje> mensajesEnviados = this.mensajesEnviados.get(contacto);
			Mensaje lastMensajeRecibido = null; Mensaje lastMensajeEnviado = null;
			if (mensajesRecibidos != null && mensajesRecibidos.size() > 0)
				lastMensajeRecibido = mensajesRecibidos.get(mensajesRecibidos.size()-1);
			if (mensajesEnviados.size() > 0)
				lastMensajeEnviado = mensajesEnviados.get(mensajesEnviados.size()-1);
			if (lastMensajeRecibido == null && lastMensajeEnviado == null)
				continue;
			else if (lastMensajeRecibido == null && lastMensajeEnviado != null)
				mensajes.put(contacto, lastMensajeEnviado);
			else if (lastMensajeRecibido != null && lastMensajeEnviado == null)
				mensajes.put(contacto, lastMensajeRecibido);
			else {
				if (lastMensajeEnviado.getFecha().compareTo(lastMensajeRecibido.getFecha()) > 0)
					mensajes.put(contacto, lastMensajeRecibido);
				else
					mensajes.put(contacto, lastMensajeEnviado);
			}
		}
		
		for (Contacto contacto: this.mensajesRecibidos.keySet())
		{
			List<Mensaje> mensajesRecibidos = this.mensajesRecibidos.get(contacto);
			
			if (mensajesRecibidos.size() > 0) {
				Mensaje mensaje = mensajesRecibidos.get(mensajesRecibidos.size()-1);
				mensajes.put(contacto, mensaje);	
			}
		}
		
		for (Contacto contacto: mensajes.keySet())
		{
			System.out.println(contacto.getCodigo()+" "+contacto.getNombre()+" "+contacto.hashCode());
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}
	
}
