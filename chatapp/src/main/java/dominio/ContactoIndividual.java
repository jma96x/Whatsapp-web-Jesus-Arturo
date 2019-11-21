package dominio;

import controlador.ControladorChat;

public class ContactoIndividual extends Contacto {
	private String telefonoUsuario;
	private Usuario usuario;
	
	public ContactoIndividual(String nombre,String telefonoUsuario, Usuario usuario) {
		super(nombre);
		this.telefonoUsuario = usuario.getTelefono();
		this.usuario = usuario;
	}
	
	@Override
	public int getCodigo() {
		return super.getCodigo();
	}
	@Override
	public String getNombre() {
		return super.getNombre();
	}
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	@Override
	public boolean equals(Object ci) {
		if (ci instanceof ContactoIndividual) {
			Contacto c = (Contacto) ci ; 
			return super.equals(c) && this.telefonoUsuario.equals(((ContactoIndividual) ci).getTelefonoUsuario());
		}
		return false;
	}
}
