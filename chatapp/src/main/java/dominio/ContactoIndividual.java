package dominio;

public class ContactoIndividual extends Contacto {
	private String telefonoUsuario;
	private int cUsuario;

	public ContactoIndividual(String nombre, String telefonoUsuario, int cUsuario) {
		super(nombre);
		this.telefonoUsuario = telefonoUsuario;
		this.cUsuario = cUsuario;
	}
	
	public ContactoIndividual(Usuario usuario) {
		this(usuario.getNombre(), usuario.getTelefono(), usuario.getCodigo());
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
	public int getCodigoUsuario() {
		return cUsuario;
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
