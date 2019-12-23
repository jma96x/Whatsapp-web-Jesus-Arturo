package dominio;

import java.util.LinkedList;

public abstract class Contacto {
	private String nombre;
	private int codigo;
	private LinkedList<Mensaje> mensajes;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		this.setMensajes(new LinkedList<Mensaje>());
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public abstract String toString();
	//Mensajes
	public LinkedList<Mensaje> getMensajes() {
		return mensajes;
	}
	public void setMensajes(LinkedList<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}
	public Mensaje getLastMensaje() {
		if (mensajes.size()>0)
			return mensajes.get(mensajes.size()-1);
		return null;
	}
	//------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Contacto other = (Contacto) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
}
