package interfaces;

import java.awt.Dimension;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controlador.ControladorChat;

public class InterfazPerfilContacto {

	private JPanel perfilContacto;

	/**
	 * Create the application.
	 */
	public InterfazPerfilContacto() {
		perfilContacto = new JPanel();
		perfilContacto.setPreferredSize(new Dimension(635, 660));
		perfilContacto.setLayout(null);
		String nombreContacto = ControladorChat.getUnicaInstancia().getNombreContactoActual();
		String imgContacto = ControladorChat.getUnicaInstancia().getImgContactoActual();
		JButton fotoContacto = new JButton();
		fotoContacto.setBounds(183, 63, 256, 256);
		perfilContacto.add(fotoContacto);
		if (imgContacto.equals("/img/defecto.jpg"))
			setImage(fotoContacto, imgContacto, 256, 256);
		else 
			setImageAbsoluta(fotoContacto,imgContacto,256,256);

		JLabel lblNombrecontacto = new JLabel("NOMBRE DEL CONTACTO");
		lblNombrecontacto.setBounds(36, 330, 209, 34);
		perfilContacto.add(lblNombrecontacto);

		JLabel lblNombreTexto = new JLabel(nombreContacto);
		lblNombreTexto.setBounds(64, 365, 181, 21);
		perfilContacto.add(lblNombreTexto);
		
		String telefonoContactoActual = ControladorChat.getUnicaInstancia().getTelefonoContactoActual();
		if (telefonoContactoActual != null) {
			JLabel lblNumeroDeTelefono = new JLabel("NUMERO DE TELEFONO");
			lblNumeroDeTelefono.setBounds(460, 335, 165, 24);
			perfilContacto.add(lblNumeroDeTelefono);
			JLabel lblNumero = new JLabel(telefonoContactoActual);
			lblNumero.setBounds(492, 367, 92, 17);
			perfilContacto.add(lblNumero);
		}else {
			lblNombrecontacto.setBounds(250, 330, 209, 34);
			lblNombreTexto.setBounds(300, 365, 181, 21);
		}


		JLabel lblEstado = new JLabel("ESTADO");
		lblEstado.setBounds(297, 444, 129, 14);
		perfilContacto.add(lblEstado);

		JLabel lblTextoestado = new JLabel("textoEstado inserte frase filosofica aqui");
		lblTextoestado.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextoestado.setBounds(128, 470, 367, 43);
		perfilContacto.add(lblTextoestado);
	}

	public JPanel getPerfilContacto() {
		return perfilContacto;
	}

	void setImage(JButton b, String ruta, int rx, int ry) {

		try {
			Image img = ImageIO.read(getClass().getResource(ruta));
			img = img.getScaledInstance(rx, ry, Image.SCALE_DEFAULT);
			b.setIcon(new ImageIcon(img));
			b.setContentAreaFilled(false);
			b.setBorder(null);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	private void setImageAbsoluta(JButton b, String ruta, int rx, int ry) {

		try {
			File fichero = new File(ruta);
			Image img = ImageIO.read(fichero);
			img = img.getScaledInstance(rx, ry, Image.SCALE_DEFAULT);
			b.setIcon(new ImageIcon(img));
			b.setContentAreaFilled(false);
			b.setBorder(null);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
