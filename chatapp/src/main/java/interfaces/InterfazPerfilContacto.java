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
import javax.swing.SwingConstants;

public class InterfazPerfilContacto {

	private JPanel perfilContacto;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazPerfilContacto window = new InterfazPerfilContacto();
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
	public InterfazPerfilContacto() {
		perfilContacto = new JPanel();
		perfilContacto.setPreferredSize(new Dimension(635, 660));
		perfilContacto.setLayout(null);

		JButton fotoContacto = new JButton();
		fotoContacto.setBounds(183, 63, 256, 256);
		perfilContacto.add(fotoContacto);
		setImage(fotoContacto, "/contact.png", 256, 256);

		JLabel lblNombrecontacto = new JLabel("NOMBRE DEL CONTACTO");
		lblNombrecontacto.setBounds(36, 330, 209, 34);
		perfilContacto.add(lblNombrecontacto);

		JLabel lblNombreTexto = new JLabel("pepe Perico");
		lblNombreTexto.setBounds(64, 365, 181, 21);
		perfilContacto.add(lblNombreTexto);

		JLabel lblNumeroDeTelefono = new JLabel("NUMERO DE TELEFONO");
		lblNumeroDeTelefono.setBounds(460, 335, 165, 24);
		perfilContacto.add(lblNumeroDeTelefono);

		JLabel lblNumero = new JLabel("601012173");
		lblNumero.setBounds(492, 367, 92, 17);
		perfilContacto.add(lblNumero);

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


}
