package dominio;

import java.time.LocalDateTime;


public class Mensaje {
	int codigo;
	private String texto;
	private int emoticono;
	private String hora ;
	private Usuario emisor;
	private Contacto destino;
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, String hora) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.hora =  hora;
		this.emisor = emisor;
		this.destino = destino;
	}
	
	public Mensaje(String texto, int emoticono, Usuario emisor, Contacto destino) {
		this(texto, emoticono, emisor, destino, LocalDateTime.now().getHour() + " : " + LocalDateTime.now().getMinute());
	}

	public Contacto getDestino() {
		return destino;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	public String getHora() {
		return hora;
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
}
