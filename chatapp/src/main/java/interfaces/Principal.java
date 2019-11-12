package interfaces;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollPane;



import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Point;
import java.awt.Insets;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Principal extends JFrame implements ActionListener {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, Constantes.ventana_x_size, Constantes.ventana_y_size);
		setTitle("Whatsapp Web");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contactos = new JPanel();
		contactos.setPreferredSize(new Dimension(350, 750));
		contactos.setBackground(Color.GRAY);
		FlowLayout fl_contactos = (FlowLayout) contactos.getLayout();
		fl_contactos.setVgap(0);
		fl_contactos.setHgap(0);
		getContentPane().add(contactos, BorderLayout.WEST);
		
		JPanel estado = new JPanel();
		estado.setPreferredSize(new Dimension(350, 80));
		FlowLayout flowLayout_3 = (FlowLayout) estado.getLayout();
		flowLayout_3.setVgap(8);
		flowLayout_3.setHgap(15);
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		estado.setBackground(Color.GREEN);
		estado.setBorder(null);
		contactos.add(estado);
		JButton btnFotoUsuario = new JButton();
		this.setImage(btnFotoUsuario, "/bandera_espanya.png", 64, 64);
		estado.add(btnFotoUsuario);
		btnFotoUsuario.setAlignmentY(Component.TOP_ALIGNMENT);
		btnFotoUsuario.setPreferredSize(new Dimension(64, 64));
		btnFotoUsuario.setActionCommand("FotoUsuario");
		
		JPanel invisibleSpace = new JPanel();
		invisibleSpace.setBackground(Color.GREEN);
		invisibleSpace.setPreferredSize(new Dimension(140, 64));
		FlowLayout fl_invisibleSpace = (FlowLayout) invisibleSpace.getLayout();
		fl_invisibleSpace.setHgap(0);
		fl_invisibleSpace.setVgap(0);
		estado.add(invisibleSpace);
		
		JButton btnEstados = new JButton();
		this.setImage(btnEstados, "/estados.png", 38, 40);
		btnEstados.setPreferredSize(new Dimension(38, 40));
		estado.add(btnEstados);
		
		JButton btnFunciones = new JButton();
		this.setImage(btnFunciones, "/funciones.png", 40, 40);
		btnFunciones.setPreferredSize(new Dimension(40, 40));
		estado.add(btnFunciones);
		
		JPanel listaContactos = new JPanel();
		listaContactos.setPreferredSize(new Dimension(350, 640));
		FlowLayout flowLayout_2 = (FlowLayout) listaContactos.getLayout();
		flowLayout_2.setHgap(0);
		flowLayout_2.setVgap(0);
		listaContactos.setBackground(Color.MAGENTA);
		contactos.add(listaContactos);
		
		JScrollPane scrollListaContactos = new JScrollPane();
		scrollListaContactos.setBorder(null);
		scrollListaContactos.setBackground(Color.BLACK);
		scrollListaContactos.setPreferredSize(new Dimension(350, 640));
		scrollListaContactos.setBounds(new Rectangle(0, 0, 350, 670));
		listaContactos.add(scrollListaContactos);

		JPanel mensajes = new JPanel();
		mensajes.setPreferredSize(new Dimension(650, 750));
		mensajes.setBackground(Color.RED);
		FlowLayout fl_mensajes = (FlowLayout) mensajes.getLayout();
		fl_mensajes.setHgap(0);
		fl_mensajes.setVgap(0);
		getContentPane().add(mensajes, BorderLayout.EAST);	
		
		JPanel infoMensaje = new JPanel();
		infoMensaje.setPreferredSize(new Dimension(650, 80));
		FlowLayout flowLayout = (FlowLayout) infoMensaje.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setVgap(8);
		flowLayout.setHgap(15);
		infoMensaje.setBackground(Color.GREEN);
		mensajes.add(infoMensaje);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GREEN);
		panel_1.setPreferredSize(new Dimension(50, 10));
		infoMensaje.add(panel_1);
		
		JButton btnInfoContacto = new JButton();
		this.setImage(btnInfoContacto, "/contact.png", 64, 64);
		btnInfoContacto.setPreferredSize(new Dimension(64, 64));
		infoMensaje.add(btnInfoContacto);
		
		JLabel lblNombreContacto = new JLabel("Jesus");
		infoMensaje.add(lblNombreContacto);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setPreferredSize(new Dimension(300, 10));
		infoMensaje.add(panel);
		
		JButton btnBuscarMensaje = new JButton();
		this.setImage(btnBuscarMensaje, "/search.png", 40, 40);
		btnBuscarMensaje.setPreferredSize(new Dimension(40, 40));
		infoMensaje.add(btnBuscarMensaje);
		
		JButton btnEliminarMensajes = new JButton();
		this.setImage(btnEliminarMensajes, "/eliminator.png", 40, 40);
		btnEliminarMensajes.setPreferredSize(new Dimension(40, 40));
		infoMensaje.add(btnEliminarMensajes);
		
		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(650, 640));
		FlowLayout flowLayout_1 = (FlowLayout) chat.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		chat.setBackground(Color.GREEN);
		mensajes.add(chat);
		
		JPanel conversacion = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) conversacion.getLayout();
		flowLayout_5.setVgap(0);
		flowLayout_5.setHgap(0);
		conversacion.setBackground(Color.PINK);
		chat.add(conversacion);
		conversacion.setPreferredSize(new Dimension(650, 550));
		
		JScrollPane scrollConversacion = new JScrollPane();
		scrollConversacion.setBorder(null);
		scrollConversacion.setBackground(Color.PINK);
		scrollConversacion.setPreferredSize(new Dimension(650, 550));
		conversacion.add(scrollConversacion);
		
		JPanel mandarMensaje = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) mandarMensaje.getLayout();
		flowLayout_4.setHgap(0);
		mandarMensaje.setBackground(Color.BLUE);
		mandarMensaje.setPreferredSize(new Dimension(650, 90));
		chat.add(mandarMensaje);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void setImage(JButton b, String ruta ,int rx, int ry) {
		 
		  try {
			Image img = ImageIO.read(getClass().getResource(ruta));
			img = img.getScaledInstance(rx,ry, Image.SCALE_DEFAULT);
		    b.setIcon(new ImageIcon(img));
		    b.setContentAreaFilled(false);
		    b.setBorder(null);
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}

}
