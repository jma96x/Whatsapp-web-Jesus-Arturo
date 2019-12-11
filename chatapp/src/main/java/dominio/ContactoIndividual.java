package dominio;

public class ContactoIndividual extends Contacto {
	private String telefonoUsuario;
	
	public ContactoIndividual(String nombre,String telefonoUsuario) {
		super(nombre);
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
	
	public String toString() {
		return getNombre();
	}
}
