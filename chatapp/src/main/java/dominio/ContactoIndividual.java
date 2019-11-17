package dominio;

public class ContactoIndividual extends Contacto {
	String telefonoUsuario;
	
	public ContactoIndividual(String nombre, String telefonoUsuario) {
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
}
