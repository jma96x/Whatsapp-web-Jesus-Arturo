package dominio;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto {
	private List<ContactoIndividual> participantes;
	private String img;
	private Usuario administrador;
	public Grupo(String nombre, String img, List<ContactoIndividual> participantes, Usuario admin) {
		super(nombre);
		this.img = img;
		this.administrador = admin;
		this.participantes = participantes;
	}
	
	public List<ContactoIndividual> getParticipantes() {
		return new LinkedList<ContactoIndividual>(participantes);
	}
	public String getImg() {
		return img;
	}
	public Usuario getAdministrador() {
		return administrador;
	}
	public int getCodigoAdministrador() {
		return this.administrador.getCodigo();
	}
	public void setParticipantes(List<ContactoIndividual> participantes) {
		this.participantes = participantes;
	}

	public String toString() {
		int contador = 1;
		String msg = "*GRUPO* " +getNombre()+": ";
		for (ContactoIndividual ci : getParticipantes()) {
			msg += "Participante "+ contador + ": "+ ci.getNombre() + " - " + ci.getTelefonoUsuario() + ", ";
			contador++;
		}
		return msg;
	}

	public int modificarMiembro(ContactoIndividual nuevo) {
		int contador = 0;
		for (ContactoIndividual ci : participantes) {
			if (ci.getTelefonoUsuario().equals(nuevo.getTelefonoUsuario())) {
				System.out.println("hola" + ci.getNombre());
				nuevo.setCodigo(ci.getCodigo());
				participantes.set(contador, nuevo);
				System.out.println("hola" + ci.getNombre());
				return nuevo.getCodigo();
			}
			contador++;
		}
		return -1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((administrador == null) ? 0 : administrador.hashCode());
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
		Grupo other = (Grupo) obj;
		if (administrador == null) {
			if (other.administrador != null)
				return false;
		} else if (!administrador.equals(other.administrador))
			return false;
		return true;
	}

	public String getImgGrupo() {
		return this.administrador.getImg();
	}
	
	
}
