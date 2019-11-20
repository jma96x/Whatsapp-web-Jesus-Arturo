package interfaces;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.ControladorChat;

public class InterfazPerfilUsuario {

	private JPanel perfil;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazPerfilUsuario window = new InterfazPerfilUsuario();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public InterfazPerfilUsuario() {
	    perfil = new JPanel();
		perfil.setPreferredSize(new Dimension(350, 620));
		perfil.setLayout(null);
		String imgUsuario = ControladorChat.getUnicaInstancia().getImgUsuarioActual();
		String login = ControladorChat.getUnicaInstancia().getNombreUsuarioActual();
		JButton foto = new JButton();
		foto.setBounds(45, 61, 256, 256);
		perfil.add(foto);
		setImage(foto, imgUsuario, 256, 256);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(27, 358, 152, 30);
		perfil.add(lblNombre);

		JLabel lblNombreTexto = new JLabel(login);
		lblNombreTexto.setBounds(27, 399, 206, 26);
		perfil.add(lblNombreTexto);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(27, 443, 82, 30);
		perfil.add(lblEstado);

		JLabel lblEstadoTexto = new JLabel("soy un alfa");
		lblEstadoTexto.setBounds(27, 484, 274, 39);
		perfil.add(lblEstadoTexto);
	}
	public JPanel getPerfil() {
		return perfil;
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
}
