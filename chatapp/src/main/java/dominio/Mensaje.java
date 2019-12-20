package dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Mensaje {
	int codigo;
	private String texto;
	private int emoticono;
	private LocalDate fecha;
	private Usuario emisor;
	private Contacto destino;
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, LocalDate fecha) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fecha =  fecha;
		this.emisor = emisor;
		this.destino = destino;
	}
	
	public Mensaje(String texto, int emoticono, Usuario emisor, Contacto destino) {
		this(texto, emoticono, emisor, destino, LocalDate.now());
	}

	public Contacto getDestino() {
		return destino;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	public String getHora() {
		//TODO
		return null;
	}
	public String getTexto() {
		return texto;
	}
	public int getEmoticono() {
		return emoticono;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigo() {
		return codigo;
	}

	public LocalDate getFecha() {
		return fecha;
	}
}
