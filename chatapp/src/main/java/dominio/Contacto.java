package dominio;

public abstract class Contacto {
	private String nombre;
	private int codigo;
	//private Usuario usuario;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	@Override
	public boolean equals(Object c) {
		if (c instanceof Contacto)
			return this.nombre.equals(((Contacto) c).getNombre());
		return false;
	}
}
