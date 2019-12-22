package dominio;

public class ContactoIndividual extends Contacto {
	private Usuario usuario;

	public ContactoIndividual(String nombre, Usuario usuario) {
		super(nombre);
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
	public Usuario getUsuario() {
		return usuario;
	}
	public int getCodigoUsuario() {
		return this.usuario.getCodigo();
	}
	public String getTelefonoUsuario() {
		return usuario.getTelefono();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((usuario.getTelefono() == null) ? 0 : usuario.getTelefono().hashCode());
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
		if (usuario.getTelefono() == null) {
			if (other.usuario.getTelefono() != null)
				return false;
		} else if (!usuario.getTelefono().equals(other.usuario.getTelefono()))
			return false;
		return true;
	}
	public String toString() {
		return getNombre();
	}
	public String getImgUsuario() {
		return this.usuario.getImg();
	}
}
