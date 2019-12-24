package interfaces;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class InterfazContactoRenderer extends JPanel implements ListCellRenderer<InterfazContacto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Component getListCellRendererComponent(JList<? extends InterfazContacto> list, InterfazContacto value,
			int index, boolean isSelected, boolean cellHasFocus) {
		this.setLayout(null);
		this.setSize(new Dimension(350, 70));
		this.setMaximumSize(new Dimension(350, 70));
		this.setPreferredSize(new Dimension(350, 70));
		JButton btnImgContacto = new JButton();
		btnImgContacto.setPreferredSize(new Dimension(64, 64));
		btnImgContacto.setBounds(0, 5, 64, 64);
		String imgContacto = value.getImgContacto();
		if (imgContacto.equals("/img/defecto.jpg"))
			setImage(btnImgContacto, imgContacto, 64, 64);
		else 
			setImageAbsoluta(btnImgContacto,imgContacto,64,64);
		JLabel lblFechaUltimoMensaje = new JLabel(value.getFechaUltimoMensaje());
		lblFechaUltimoMensaje.setBounds(71, 29, 81, 17);

		JLabel lblUltimoMensaje = new JLabel(value.getUltimoMensaje());
		lblUltimoMensaje.setBounds(71, 49, 259, 20);

		JLabel lblNombreContacto = new JLabel(value.getContacto().getNombre());
		lblNombreContacto.setBounds(151, 29, 81, 17);
		
		
		this.removeAll();
		
		this.add(btnImgContacto);
		this.add(lblFechaUltimoMensaje);
		this.add(lblNombreContacto);
		this.add(lblUltimoMensaje);
	
		return this;
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
