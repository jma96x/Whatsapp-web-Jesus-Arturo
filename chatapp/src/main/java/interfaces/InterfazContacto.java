package interfaces;

import java.awt.Dimension;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InterfazContacto {
	String imgContacto;
	String fechaUltimoMensaje;
	String nombreContacto;
	String ultimoMensaje;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public InterfazContacto(String imgContacto, Date fechaUltimoMensaje, String nombreContacto, String ultimoMensaje) {
		this.imgContacto = imgContacto;
		this.fechaUltimoMensaje = formatter.format(fechaUltimoMensaje);
		this.nombreContacto = nombreContacto;
		this.ultimoMensaje = ultimoMensaje;
	}
	public String getFechaUltimoMensaje() {
		return fechaUltimoMensaje;
	}
	public String getImgContacto() {
		return imgContacto;
	}
	public String getNombreContacto() {
		return nombreContacto;
	}
	public String getUltimoMensaje() {
		return ultimoMensaje;
	}
	public void setUltimoMensaje(String subMsj) {
		this.ultimoMensaje = subMsj;
		
	}
}
