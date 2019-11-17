package dominio;

public class CatalogoUsuarios {
	private static CatalogoUsuarios unicaInstancia;
	
	private CatalogoUsuarios() {
		
	}
	
	public static CatalogoUsuarios getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new CatalogoUsuarios();
		return unicaInstancia;
	}

}
