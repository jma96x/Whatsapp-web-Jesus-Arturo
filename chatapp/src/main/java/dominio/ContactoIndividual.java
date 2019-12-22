package dominio;

public class ContactoIndividual extends Contacto {
	private String telefonoUsuario;
	private Usuario usuario;

	public ContactoIndividual(String nombre, String telefonoUsuario, Usuario usuario) {
		super(nombre);
		this.telefonoUsuario = telefonoUsuario;
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
	public int getCodigoUsuario() {
		return this.usuario.getCodigo();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((telefonoUsuario == null) ? 0 : telefonoUsuario.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoIndividual other = (ContactoIndividual) obj;
		if (telefonoUsuario == null) {
			if (other.telefonoUsuario != null)
				return false;
		} else if (!telefonoUsuario.equals(other.telefonoUsuario))
			return false;
		return true;
	}
	public String toString() {
		return getNombre();
	}
}
