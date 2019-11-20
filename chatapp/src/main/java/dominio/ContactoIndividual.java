package dominio;

public class ContactoIndividual extends Contacto {
	String telefonoUsuario;
	
	public ContactoIndividual(String nombre, String img, String telefonoUsuario) {
		super(nombre, img);
		this.telefonoUsuario = telefonoUsuario;
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
	@Override
	public boolean equals(Object ci) {
		if (ci instanceof ContactoIndividual) {
			Contacto c = (Contacto) ci ; 
			return super.equals(c) && this.telefonoUsuario.equals(((ContactoIndividual) ci).getTelefonoUsuario());
		}
		return false;
	}
}
