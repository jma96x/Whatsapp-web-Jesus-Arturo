package dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Mensaje {
	int codigo;
	private String texto;
	private int emoticono;
	private Date fecha;
	private Usuario emisor;
	private Contacto destino;
	SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat parser2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, Date fecha) {
		this.texto = texto;
		this.emoticono = emoticono;
		this.fecha =  fecha;
		this.emisor = emisor;
		this.destino = destino;
	}
	
	public Mensaje (String texto, int emoticono, Usuario emisor, Contacto destino, String fecha) {
		this(texto, emoticono, emisor, destino, new Date());
		Date dFecha = null;
		try {
			dFecha = parser2.parse(fecha);
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
		return parser.format(fecha);
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha =  fecha;
	}
	@Override
	public String toString() {
		String contenido = "Emoticono";
		if (texto == null)
			contenido = "Emoticono" + " " + String.valueOf(emoticono);
		else 
			contenido = texto;
		return "Destino: " + destino.toString() + " " + contenido + " " + getFechaFormat();
	}
}
