package dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


public class Mensaje {
	int codigo;
	private String texto;
	private int emoticono;
	private Date fecha;
	private Usuario emisor;
	private Contacto destino;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, Date fecha) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fecha =  fecha;
		this.emisor = emisor;
		this.destino = destino;
	}
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, String fecha) {
		this(texto, emoticono, emisor, destino, new Date());
		Date dFecha = new Date();
		try {
			dFecha = formatter.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setFecha(dFecha);
	}
	
	public Mensaje(String texto, int emoticono, Usuario emisor, Contacto destino) {
		this(texto, emoticono, emisor, destino, new Date());
	}

	public Contacto getDestino() {
		return destino;
	}
	public Usuario getEmisor() {
		return emisor;
	}
	/*public String getHora() {
		return fecha.getHour()+":"+fecha.getMinute();
	}*/
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
	public String getFechaFormat() {
		return formatter.format(fecha);
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha =  fecha;
	}
}
