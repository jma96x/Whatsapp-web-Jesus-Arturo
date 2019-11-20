package dominio;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
	private List<Mensaje> mensajesEnviados;
	private List<Mensaje> mensajesRecibidos;
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
		this.mensajesEnviados = new LinkedList<Mensaje>();
		this.mensajesRecibidos = new LinkedList<Mensaje>();
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
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(this.contactos);
	}
	public List<Mensaje> getMensajesEnviados() {
		return new LinkedList<Mensaje>(this.mensajesEnviados);
	}
	public List<Mensaje> getMensajesRecibidos() {
		return new LinkedList<Mensaje>(this.mensajesRecibidos);
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public void addContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	public boolean isPremium() {
		return this.premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	public void addContacto(Contacto contacto) {
		this.contactos.add(contacto);
		
	}
}
