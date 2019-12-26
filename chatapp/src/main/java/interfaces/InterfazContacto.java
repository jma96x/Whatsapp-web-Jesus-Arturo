package interfaces;

import java.awt.Dimension;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import dominio.Contacto;

public class InterfazContacto {
	String imgContacto;
	String fechaUltimoMensaje;
	Contacto contacto;
	String ultimoMensaje;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public InterfazContacto(String imgContacto, Date fechaUltimoMensaje, Contacto contacto, String ultimoMensaje) {
		this.imgContacto = imgContacto;
		this.fechaUltimoMensaje = formatter.format(fechaUltimoMensaje);
		this.contacto = contacto;
		this.ultimoMensaje = ultimoMensaje;
	}
	public String getFechaUltimoMensaje() {
		return fechaUltimoMensaje;
	}
	public String getImgContacto() {
		return imgContacto;
	}
	public Contacto getContacto() {
		return contacto;
	}
	public String getUltimoMensaje() {
		return ultimoMensaje;
	}
	public void setUltimoMensaje(String subMsj) {
		this.ultimoMensaje = subMsj;
		
	}
}
