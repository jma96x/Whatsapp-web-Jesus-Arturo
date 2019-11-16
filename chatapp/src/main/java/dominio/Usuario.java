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
	private Date fechaNacimiento;
	private List<Contacto> contactos;
	private boolean premium;
	private String rutaImg;
	
	public Usuario(int codigo, String email, String nombre, String contraseña, String telefono, Date fecha) {
		this.codigo = codigo;
		this.email = email;
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.telefono = telefono;
		this.fechaNacimiento = fecha;
		this.contactos = new LinkedList<Contacto>();
		this.premium = false;
		this.rutaImg = null;
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
	public String getRutaImg() {
		return rutaImg;
	}
	public List<Contacto> getContactos() {
		return new LinkedList<Contacto>(this.contactos);
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public void setRutaImg(String rutaImg) {
		this.rutaImg = rutaImg;
	}
	public boolean isPremium() {
		return this.premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
}
