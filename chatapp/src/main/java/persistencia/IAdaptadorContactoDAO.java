package persistencia;

import java.util.List;
import dominio.Contacto;

public interface IAdaptadorContactoDAO {

	public void registrarContacto(Contacto contacto);
	public void borrarContacto(Contacto contacto);
	public void modificarContacto(Contacto contacto);
	public Contacto recuperarContacto(int codigo);
	public List<Contacto> recuperarTodosContactos();
}