package dominio;

public abstract class Contacto {
	private String nombre;
	private int codigo;
	private String img;
	//private Usuario usuario;
	
	public Contacto(String nombre, String img) {
		//Poner img por defecto
		if (img == null)
			img = "/img/contact.img";
		this.nombre = nombre;
		this.img = img;
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
	public void setImg(String img) {
		this.img = img;
	}
	public String getImg() {
		return img;
	}
	/*public Usuario getUsuario() {
		return usuario;
	}*/
	/*public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}*/
	@Override
	public boolean equals(Object c) {
		if (c instanceof Contacto)
			return this.nombre.equals(((Contacto) c).getNombre()) && this.img.equals(((Contacto) c).getImg());
		return false;
	}
}
