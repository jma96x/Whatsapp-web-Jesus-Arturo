package dominio;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		for (Contacto contacto : contactos) {
			System.out.println(contacto);
		}
		return new LinkedList<Contacto>(this.contactos);
	}
	public ContactoIndividual getContacto(String telefono) {
		for (Contacto contacto : contactos) {
			if (contacto instanceof ContactoIndividual && ((ContactoIndividual)contacto).getTelefonoUsuario().equals(telefono))
				return (ContactoIndividual)contacto;
		}
		return null;
	}
	public void addContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	public void addContacto(Contacto contacto) {
		System.out.println("addcontacto: "+ contacto.toString());
		this.contactos.add(contacto);
	}
	public void borrarContacto(Contacto c) {
		this.contactos.remove(c);
	}
	public Grupo getGrupo(String nombreGrupo, Usuario administrador) {
		for(Contacto c : contactos) {
			if (c instanceof Grupo && ((Grupo)c).getNombre().equals(nombreGrupo) &&  ((Grupo) c).getAdministrador().equals(administrador)) {
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
	public HashMap<Contacto, Mensaje> getLastMensajes() {
		HashMap<Contacto, Mensaje> mensajes = new HashMap<Contacto, Mensaje>();
		for (Contacto contacto : contactos) {
			Mensaje mensaje = contacto.getLastMensaje();
			if (mensaje != null)
				mensajes.put(contacto, mensaje);
		}
		return mensajes;
	}
	public List<ContactoIndividual> getContactosIndividuales() {
		LinkedList<ContactoIndividual> ci = new LinkedList<ContactoIndividual>();
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				ci.add((ContactoIndividual)c);
			}
		}
		return ci;
	}
	public Contacto hasContact(Set<String> nombres) {
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual && nombres.contains(c.getNombre())) {
				return c;
			}
		}
		return null;
	}
	public Contacto hasGroup(Set<String> nombresContactos) {
		//Como es imposible saber si un grupo que tiene los mismo integrantes diferenciar el grupo pues el primero que cumpla los participantes es el que añade mensajes.
		int contactosIguales = 0;
		for (Contacto c : contactos ) {
			contactosIguales = 0;
			if (c instanceof Grupo) {
				Grupo g  = (Grupo) c;
				for (ContactoIndividual ci : g.getParticipantes()) {
					if (nombresContactos.contains(ci.getNombre())){
						contactosIguales++;
					}
				}
				if (contactosIguales == nombresContactos.size())
					return c;
			}
		}
		return null;
	}
	public ContactoIndividual getContactoWithNombre(String nombreContacto) {
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual && nombreContacto.equals(c.getNombre())) {
				return (ContactoIndividual)c;
			}
		}
		return null;
	}
	public List<Mensaje> getAllMisMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		for (Contacto contacto: contactos) {
			for (Mensaje mensaje : contacto.getMensajes()) {
				if (mensaje.getEmisor() == this) {
					mensajes.add(mensaje);
				}
			}
		}
		return mensajes;
	}
	
}
