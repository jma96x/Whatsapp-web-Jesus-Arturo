package dominio;

import java.time.LocalDateTime;


public class Mensaje {
	int codigo;
	private String texto;
	private boolean emoticono;
	private String hora ;
	private Usuario emisor;
	private Contacto destino;
	
	public Mensaje(String texto, boolean isEmoticono, Usuario emisor, Contacto destino) {
		this.texto = texto;
		this.emoticono = isEmoticono;
		this.hora =  LocalDateTime.now().getHour() + " : " + LocalDateTime.now().getMinute();
		this.emisor = emisor;
		this.destino = destino;
	}
	public Mensaje (String texto, boolean isEmoticono, Usuario emisor, Contacto destino, String hora) {
		this.texto = texto;
		this.emoticono = isEmoticono;
		this.hora =  hora;
		this.emisor = emisor;
		this.destino = destino;
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
	public boolean isEmoticono() {
		return emoticono;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getCodigo() {
		return codigo;
	}
}
