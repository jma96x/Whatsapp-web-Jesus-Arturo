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
	@Override
	public boolean equals(Object g) {
		//TODO equals en contacto
		if (g instanceof Grupo) {
			Contacto c = (Contacto) g; 
			return super.equals(c) && this.participantes.equals(((Grupo) g).getParticipantes()) && this.administrador.equals(((Grupo) g).getAdministrador()) 
					&& this.img.equals(((Grupo) g).getImg());	
		}
		return false;
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
	
}
