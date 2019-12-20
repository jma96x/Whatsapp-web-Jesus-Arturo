package dominio;

import java.time.LocalDateTime;


public class Mensaje {
	int codigo;
	private String texto;
	private int emoticono;
	private LocalDateTime fecha;
	private Usuario emisor;
	private Contacto destino;
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, LocalDateTime fecha) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fecha =  fecha;
		this.emisor = emisor;
		this.destino = destino;
	}
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, String fecha) {
		this(texto, emoticono, emisor, destino, LocalDateTime.parse(fecha));
	}
	
	public Mensaje(String texto, int emoticono, Usuario emisor, Contacto destino) {
		this(texto, emoticono, emisor, destino, LocalDateTime.now());
	}

	public Contacto getDestino() {
		return destino;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	public String getHora() {
		return fecha.getHour()+":"+fecha.getMinute();
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

	public LocalDateTime getFecha() {
		return fecha;
	}
}
