package dominio;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto {
	private List<ContactoIndividual> participantes;
	private String img;
	private String tlfAdministrador;
	public Grupo(String nombre, String img, List<ContactoIndividual> participantes, String tlfAdministrador) {
		super(nombre);
		this.img = img;
		this.tlfAdministrador = tlfAdministrador;
		this.participantes = participantes;
	}
	
	public List<ContactoIndividual> getParticipantes() {
		return new LinkedList<ContactoIndividual>(participantes);
	}
	public String getImg() {
		return img;
	}
	public String getTlfAdministrador() {
		return tlfAdministrador;
	}
	@Override
	public boolean equals(Object g) {
		//TODO equals en contacto
		if (g instanceof Grupo) {
			Contacto c = (Contacto) g; 
			return super.equals(c) && this.participantes.equals(((Grupo) g).getParticipantes()) && this.tlfAdministrador.equals(((Grupo) g).getTlfAdministrador()) 
					&& this.img.equals(((Grupo) g).getImg());	
		}
		return false;
	}

	public void setParticipantes(List<ContactoIndividual> participantes) {
		this.participantes = participantes;
	}

	public String toString() {
		String msg = "*GRUPO* " +getNombre()+": ";
		for (ContactoIndividual ci : getParticipantes()) {
			msg += ci.getNombre() + " - " + ci.getTelefonoUsuario() + ", ";
		}
		return msg;
	}
	
}
