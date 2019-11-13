package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import javax.swing.JTextField;

import tds.BubbleText;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame implements ActionListener{
	private JTextField InputMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
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
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the  
	 */
	private void initialize() {
		
		 setResizable(false);
		 setTitle("Whatsapp Web");
		 setBounds(100,100,1000,750);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panelArriba = new JPanel();
		panelArriba.setBackground(Color.MAGENTA);
		panelArriba.setPreferredSize(new Dimension(1000, 90));
		panelArriba.setSize(new Dimension(70, 70));
		 getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);
		
		JButton btnFotoUsuario = new JButton();
		btnFotoUsuario.setBounds(10, 11, 64, 64);
		panelArriba.add(btnFotoUsuario);
		this.setImage(btnFotoUsuario, "/bandera_espanya.png", 64, 64);
		
		
		JButton btnEstado = new JButton();
		btnEstado.setPreferredSize(new Dimension(38, 40));
		btnEstado.setBounds(180, 30, 38, 40);
		panelArriba.add(btnEstado);
		this.setImage(btnEstado, "/estados.png", 38, 40);
		
		JButton btnFunciones = new JButton();
		btnFunciones.setBounds(250, 30, 40, 40);
		panelArriba.add(btnFunciones);
		btnFunciones.setPreferredSize(new Dimension(40, 40));
		this.setImage(btnFunciones, "/funciones.png", 40, 40);
		
		JButton btnFotoContacto = new JButton();
		btnFotoContacto.setBounds(389, 11, 64, 64);
		panelArriba.add(btnFotoContacto);
		this.setImage(btnFotoContacto, "/contact.png", 64, 64);
		
		JLabel lblNombrecontacto = new JLabel("Jesus");
		lblNombrecontacto.setBounds(482, 30, 82, 30);
		panelArriba.add(lblNombrecontacto);
		
		JButton btnBuscarMensaje = new JButton();
		btnBuscarMensaje.setBounds(789, 30, 40, 40);
		panelArriba.add(btnBuscarMensaje);
		this.setImage(btnBuscarMensaje, "/search.png", 40, 40);
		
		JButton btnEliminarMensaje = new JButton();
		btnEliminarMensaje.setBounds(870, 30, 40, 40);
		panelArriba.add(btnEliminarMensaje);
		this.setImage(btnEliminarMensaje, "/eliminator.png", 40, 40);
		
		JPanel panelContactos = new JPanel();
		panelContactos.setBackground(Color.WHITE);
		panelContactos.setPreferredSize(new Dimension(350, 660));
		 getContentPane().add(panelContactos, BorderLayout.WEST);
		panelContactos.setLayout(null);
		
		//contenedorContactosContactos
		JPanel contenedorContactos = new JPanel();
		contenedorContactos.setLayout(null);
			
		//Scroll contactos
		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		scrollContactos.setPreferredSize(new Dimension(350, 650));
		scrollContactos.setSize(350, 650);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setPreferredSize(new Dimension(64, 64));
		btnNewButton.setBounds(5, 5, 64, 64);
		contenedorContactos.add(btnNewButton);
		
		JLabel label = new JLabel("11/08/2019");
		label.setBounds(71, 29, 81, 17);
		contenedorContactos.add(label);
		
		JLabel lblTenemosQueHablar = new JLabel("Tenemos que hablar");
		lblTenemosQueHablar.setBounds(71, 49, 259, 20);
		contenedorContactos.add(lblTenemosQueHablar);
		
		JLabel lblRamonPerico = new JLabel("Ramon Perico");
		lblRamonPerico.setBounds(151, 29, 81, 17);
		contenedorContactos.add(lblRamonPerico);
		panelContactos.add(scrollContactos);
		
		//Panel derecho principal
		JPanel panelMensajes = new JPanel();
		panelMensajes.setMinimumSize(new Dimension(635, 530));
		panelMensajes.setMaximumSize(new Dimension(635, 530));
		panelMensajes.setPreferredSize(new Dimension(635, 530));
		panelMensajes.setSize(635,530);
		
		//Panel de chat principal
		JPanel chat = new JPanel();
		FlowLayout flowLayout = (FlowLayout) chat.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		chat.setBackground(Color.ORANGE);
		chat.setPreferredSize(new Dimension(635, 540));
		panelMensajes.add(chat, BorderLayout.NORTH);
		
		//Panel para poner mensajes scrollables (Importante .setSize())
		JPanel contenedorMensajes = new JPanel();
		contenedorMensajes.setMinimumSize(new Dimension(615, 530));
		contenedorMensajes.setMaximumSize(new Dimension(615, 530));
		contenedorMensajes.setPreferredSize(new Dimension(615, 540));
		contenedorMensajes.setSize(615,540);
		BubbleText burbuja=new BubbleText(contenedorMensajes,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.SENT); 
		BubbleText burbuja1=new BubbleText(contenedorMensajes,"Hola grupo!!", Color.GREEN, "J.Ramón", BubbleText.RECEIVED); 
		contenedorMensajes.add(burbuja);
		contenedorMensajes.add(burbuja1);
		
		//Scroll que hace wrap al contenedor de mensajes
		JScrollPane scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setPreferredSize(new Dimension(635, 540));
		chat.add(scrollMensajes);
		
		//Panel para introducir los mensajes
		JPanel lineaMensajes = new JPanel();
		lineaMensajes.setBackground(Color.BLUE);
		lineaMensajes.setPreferredSize(new Dimension(635, 90));
		panelMensajes.add(lineaMensajes, BorderLayout.SOUTH);
		lineaMensajes.setLayout(null);
		
		InputMensaje = new JTextField();
		InputMensaje.setBounds(82, 0, 419, 79);
		lineaMensajes.add(InputMensaje);
		InputMensaje.setColumns(10);
		
		JButton btnEmojis = new JButton("New button");
		btnEmojis.setBounds(0, 0, 82, 79);
		lineaMensajes.add(btnEmojis);
		
		JButton btnNewButton_1 = new JButton("Enviar Mensaje");
		btnNewButton_1.setBounds(501, 0, 134, 79);
		lineaMensajes.add(btnNewButton_1);
		
		 getContentPane().add(panelMensajes, BorderLayout.CENTER);
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

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
