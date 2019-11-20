package dominio;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto {
	private List<ContactoIndividual> participantes;
	
	public Grupo(String nombre, String img, List<ContactoIndividual> participantes) {
		super(nombre,img);
		this.participantes = participantes;
	}
	
	public List<ContactoIndividual> getParticipantes() {
		return new LinkedList<ContactoIndividual>(participantes);
	}
	@Override
	public boolean equals(Object g) {
		if (g instanceof Grupo) {
			Contacto c = (Contacto) g; 
			return super.equals(c) && this.participantes.equals(((Grupo) g).getParticipantes());	
		}
		return false;
	}
}
