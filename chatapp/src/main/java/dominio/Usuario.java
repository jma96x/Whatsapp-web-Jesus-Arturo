package dominio;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	private List<Grupo> grupos;
	private List<ContactoIndividual> contactosIndividual;
	private boolean premium;
	private Descuento descuento;
	
	@SuppressWarnings("deprecation")
	public Usuario(String nombre, Date fecha, String telefono, String email,String login, String contraseña, String img) {
		this.email = email;
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.telefono = telefono;
		this.fechaNacimiento = fecha;
		this.login = login;
		this.img = img;
		this.contactos = new LinkedList<Contacto>();
		this.contactosIndividual = new LinkedList<ContactoIndividual>();
		this.grupos = new LinkedList<Grupo>();
		this.premium = false;
		
		if (fecha.getYear()+1900 >= 1998) {
			this.descuento = new DescuentoJovenes();
		}else {
			this.descuento = new DescuentoFijo();
		}
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
	public Descuento getDescuento() {
		return descuento;
	}
	public String getTipoDescuento() {
		if (descuento instanceof DescuentoFijo)
			return "0";
		return "1"; 
	}
	//Contactos
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(this.contactos);
	}
	public ContactoIndividual getContactoIndividual(String telefono) {
		for (ContactoIndividual contacto : contactosIndividual) {
			if (contacto.getTelefonoUsuario().equals(telefono))
				return contacto;
		}
		return null;
	}
	public Grupo getGrupo(Grupo grupo) {
		String nombre = grupo.getNombre();
		Usuario administrador = grupo.getAdministrador();
		for (Grupo grupoMio : grupos) {
			if (grupoMio.getAdministrador().getTelefono().equals(administrador.getTelefono()) && nombre.equals(grupoMio.getNombre()))
				return grupoMio;	
		}
		return null;
	}
	
	public void addContactos(List<Contacto> contactos) {
		this.contactosIndividual = new LinkedList<ContactoIndividual>();
		this.grupos = new LinkedList<Grupo>();
		for(Contacto c : contactos) {
			if (c instanceof Grupo) {
				this.grupos.add((Grupo)c);
			}else {
				this.contactosIndividual.add((ContactoIndividual)c);
			}
		}
		this.contactos = contactos;
	}
	private boolean existContacto(Contacto c) {
		if (c instanceof Grupo) {
			for (Grupo grupo : grupos) {
				if (grupo.getAdministrador().getTelefono().equals(telefono) && grupo.getNombre().equals(c.getNombre()))
					return true;
			}
		}else {
			for (ContactoIndividual contacto : contactosIndividual) {
				if (contacto.getTelefonoUsuario().equals(((ContactoIndividual) c).getTelefonoUsuario()))
					return true;
			}
		}
		return false;
	}
	
	public boolean addContacto(Contacto c) {
		if (existContacto(c))
			return false;
		if (c instanceof Grupo) {
			grupos.add((Grupo)c);
		}else {
			contactosIndividual.add((ContactoIndividual)c);
		}
		this.contactos.add(c);
		return true;
	}
	public void borrarContacto(Contacto c) {
		if (c instanceof Grupo) {
			this.grupos.remove((Grupo)c);
		}else {
			this.contactosIndividual.remove((ContactoIndividual)c);
		}
		this.contactos.remove(c);
	}
	public Grupo getGrupo(String nombreGrupo, Usuario administrador) {
		for(Grupo c : grupos) {
			if (c.getNombre().equals(nombreGrupo) && c.getAdministrador().equals(administrador)) {
				return c;
			}
		}
		return null;
	}
	public List<Grupo> getGrupos() {
		return new LinkedList<Grupo>(this.grupos);
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
				if (contactosIguales == nombresContactos.size()-1)
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
	public void setTipoDescuento(String tipoDescuento) {
		if (tipoDescuento.equals("0"))
			this.descuento = new DescuentoFijo();
		else {
			this.descuento = new DescuentoJovenes();
		}
	}
	public double calcularPrecio() {
		return this.descuento.calcDescuento();
	}
	
}
