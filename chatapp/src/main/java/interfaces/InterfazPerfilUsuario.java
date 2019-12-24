package interfaces;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorChat;
import dominio.Usuario;

public class InterfazPerfilUsuario {

	private JPanel perfil;
	private MainView mainView;
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
	public InterfazPerfilUsuario(final MainView ventanaPrincipal) {
		this.mainView = ventanaPrincipal;
	    perfil = new JPanel();
		perfil.setPreferredSize(new Dimension(350, 620));
		perfil.setLayout(null);
		Usuario usuarioActual = ControladorChat.getUnicaInstancia().getUsuarioActual();
		String imgUsuario = usuarioActual.getImg();
		String login = usuarioActual.getLogin();
		final JButton foto = new JButton();
		foto.setBounds(45, 61, 256, 256);
		if (imgUsuario.equals("/img/defecto.jpg"))
			setImage(foto, imgUsuario, 256, 256);
		else 
			setImageAbsoluta(foto,imgUsuario,256,256);
		perfil.add(foto);
		foto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(filter);
				File workingDirectory = new File(System.getProperty("user.dir"));
				fileChooser.setCurrentDirectory(workingDirectory);
				int result = fileChooser.showOpenDialog(perfil);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    String rutaFichero = selectedFile.getAbsolutePath();
				    ControladorChat.getUnicaInstancia().cambiarFotoUsuario(rutaFichero);
				    mainView.actualizarFotoContacto();
				    setImageAbsoluta(foto,rutaFichero,256,256);
				}
			}
		});
		
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
	private void setImage(JButton b, String ruta, int rx, int ry) {

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
