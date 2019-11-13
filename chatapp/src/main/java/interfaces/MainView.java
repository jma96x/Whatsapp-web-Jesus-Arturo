package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import tds.BubbleText;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

public class MainView extends JFrame implements ActionListener {
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
		setBounds(100, 100, 1000, 750);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JPanel panelArriba = new JPanel();
		panelArriba.setBackground(Color.MAGENTA);
		panelArriba.setPreferredSize(new Dimension(1000, 90));
		panelArriba.setSize(new Dimension(70, 70));
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);

		JButton btnEstado = new JButton();
		btnEstado.setPreferredSize(new Dimension(38, 40));
		btnEstado.setBounds(180, 30, 38, 40);
		panelArriba.add(btnEstado);
		this.setImage(btnEstado, "/estados.png", 38, 40);

		final JButton btnFunciones = new JButton();
		btnFunciones.setBounds(250, 30, 40, 40);
		panelArriba.add(btnFunciones);
		btnFunciones.setPreferredSize(new Dimension(40, 40));
		this.setImage(btnFunciones, "/funciones.png", 40, 40);
		btnFunciones.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// A todos estos popups hay que añadirles manejador de eventos
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(panelArriba, popupMenu);

				JMenuItem crearContacto = new JMenuItem("Crear contacto");
				popupMenu.add(crearContacto);

				JMenuItem crearGrupo = new JMenuItem("Crear grupo");
				popupMenu.add(crearGrupo);

				JMenuItem modificarGrupo = new JMenuItem("Modificar grupo");
				popupMenu.add(modificarGrupo);

				JMenuItem mostrarContactos = new JMenuItem("Mostrar contactos");
				popupMenu.add(mostrarContactos);

				JMenuItem premium = new JMenuItem("Hacerse premium");
				popupMenu.add(premium);

				JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
				popupMenu.add(cerrarSesion);

				JMenuItem estadisticas = new JMenuItem("Estadísticas");
				popupMenu.add(estadisticas);

				popupMenu.show(e.getComponent(), 40, -10);
			}
		});

		JButton btnFotoContacto = new JButton();
		btnFotoContacto.setBounds(389, 11, 64, 64);
		panelArriba.add(btnFotoContacto);
		this.setImage(btnFotoContacto, "/contact.png", 64, 64);

		JLabel lblNombrecontacto = new JLabel("Jesus");
		lblNombrecontacto.setBounds(482, 30, 82, 30);
		panelArriba.add(lblNombrecontacto);

		JButton btnEliminarMensaje = new JButton();
		btnEliminarMensaje.setBounds(870, 30, 40, 40);
		panelArriba.add(btnEliminarMensaje);
		this.setImage(btnEliminarMensaje, "/eliminator.png", 40, 40);

		// Panel izquierdo principal
		final JPanel panelContactos = new JPanel();
		panelContactos.setBackground(Color.WHITE);
		panelContactos.setPreferredSize(new Dimension(350, 620));
		panelContactos.setLayout(null);

		// contenedorContactosContactos
		JPanel contenedorContactos = new JPanel();
		contenedorContactos.setLayout(null);
		contenedorContactos.setPreferredSize(new Dimension(350, 620));
		// Scroll contactos
		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		scrollContactos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollContactos.setPreferredSize(new Dimension(350, 620));
		scrollContactos.setSize(350, 620);

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

		getContentPane().add(panelContactos, BorderLayout.WEST);

		JButton btnFotoUsuario = new JButton();
		btnFotoUsuario.setBounds(10, 11, 64, 64);
		panelArriba.add(btnFotoUsuario);
		this.setImage(btnFotoUsuario, "/bandera_espanya.png", 64, 64);
		btnFotoUsuario.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final JPanel perfil = new JPanel();
				perfil.setPreferredSize(new Dimension(350, 620));
				perfil.setLayout(null);
				
				JButton foto = new JButton("New button");
				foto.setBounds(45, 61, 256, 256);
				perfil.add(foto);
				new MainView().setImage(foto, "/bandera_espanya.png", 256, 256);

				JLabel lblNombre = new JLabel("Nombre");
				lblNombre.setBounds(27, 358, 152, 30);
				perfil.add(lblNombre);

				JLabel lblNombreTexto = new JLabel("Arturo Lorenzo");
				lblNombreTexto.setBounds(27, 399, 206, 26);
				perfil.add(lblNombreTexto);

				JLabel lblEstado = new JLabel("Estado");
				lblEstado.setBounds(27, 443, 82, 30);
				perfil.add(lblEstado);

				JLabel lblEstadoTexto = new JLabel("soy un alfa");
				lblEstadoTexto.setBounds(27, 484, 274, 39);
				perfil.add(lblEstadoTexto);
				
				JButton btnVolver = new JButton("Volver");
				btnVolver.setBounds(191, 534, 89, 23);
				perfil.add(btnVolver);
				btnVolver.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						getContentPane().remove(perfil);
						getContentPane().add(panelContactos, BorderLayout.WEST);
		                revalidate();
		                repaint();
						
					}
					
				});
				getContentPane().remove(panelContactos);
				getContentPane().add(perfil, BorderLayout.WEST);
				invalidate();
                validate();

			}
		});
		
		// Panel derecho principal
		final JPanel panelMensajes = new JPanel();
		panelMensajes.setMinimumSize(new Dimension(635, 660));
		panelMensajes.setMaximumSize(new Dimension(635, 660));
		panelMensajes.setPreferredSize(new Dimension(635, 660));
		panelMensajes.setSize(635, 660);
		
		JButton btnBuscarMensaje = new JButton();
		btnBuscarMensaje.setBounds(789, 30, 40, 40);
		panelArriba.add(btnBuscarMensaje);
		this.setImage(btnBuscarMensaje, "/search.png", 40, 40);
		btnBuscarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JPanel buscar = new JPanel();
				buscar.setPreferredSize(new Dimension(635, 660));
				buscar.setLayout(null);
				
				JLabel lblBusquedaDeMensajes = new JLabel("BUSQUEDA DE MENSAJES");
				lblBusquedaDeMensajes.setHorizontalAlignment(SwingConstants.CENTER);
				lblBusquedaDeMensajes.setBounds(120, 6, 365, 51);
				buscar.add(lblBusquedaDeMensajes);
				
				JTextField inputMensaje = new JTextField();
				inputMensaje.setBounds(208, 93, 193, 51);
				buscar.add(inputMensaje);
				inputMensaje.setColumns(10);
				
				JLabel lblMensaje = new JLabel("Mensaje");
				lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
				lblMensaje.setBounds(204, 59, 199, 33);
				buscar.add(lblMensaje);
				
				JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
				lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
				lblNombreUsuario.setBounds(112, 185, 107, 33);
				buscar.add(lblNombreUsuario);
				
				JLabel lblFecha = new JLabel("Fecha");
				lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
				lblFecha.setBounds(406, 190, 97, 23);
				buscar.add(lblFecha);
				
				JTextField inputNombre = new JTextField();
				inputNombre.setBounds(101, 215, 138, 23);
				buscar.add(inputNombre);
				inputNombre.setColumns(10);
				
				JDateChooser inputFecha = new JDateChooser();
				inputFecha.setBounds(406, 215, 97, 29);
				buscar.add(inputFecha);
				
				JLabel lblMensajesEncontrados = new JLabel("MENSAJES ENCONTRADOS");
				lblMensajesEncontrados.setBounds(60, 318, 341, 23);
				buscar.add(lblMensajesEncontrados);
				
				JPanel mensajes = new JPanel();
				FlowLayout flowLayout = (FlowLayout) mensajes.getLayout();
				flowLayout.setVgap(0);
				flowLayout.setHgap(0);
				mensajes.setPreferredSize(new Dimension(513, 240));
				mensajes.setBounds(55, 351, 513, 240);
				buscar.add(mensajes);
				
				JPanel contenedorMensajes = new JPanel();
				contenedorMensajes.setPreferredSize(new Dimension(513, 240));
				contenedorMensajes.setBounds(55, 351, 513, 240);
				contenedorMensajes.setPreferredSize(new Dimension(513, 240));
				
				JScrollPane scrollPane = new JScrollPane(contenedorMensajes);
				scrollPane.setPreferredSize(new Dimension(513, 240));
				
				
				mensajes.add(scrollPane);
				
				JButton btnVolver = new JButton();
				btnVolver.setBounds(10, 11, 40, 40);
				new MainView().setImage(btnVolver, "/close.png", 40, 40);
				buscar.add(btnVolver);
				
				btnVolver.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						getContentPane().remove(buscar);
						getContentPane().add(panelMensajes, BorderLayout.CENTER);
		                revalidate();
		                repaint();
						
					}
					
				});
				getContentPane().remove(panelMensajes);
				getContentPane().add(buscar, BorderLayout.CENTER);
				invalidate();
                validate();
			}
		});

		// Panel de chat principal
		JPanel chat = new JPanel();
		FlowLayout flowLayout = (FlowLayout) chat.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		chat.setBackground(Color.ORANGE);
		chat.setPreferredSize(new Dimension(635, 540));
		panelMensajes.add(chat, BorderLayout.NORTH);

		// Panel contenedor para poner mensajes scrollables (Importante .setSize())
		JPanel contenedorMensajes = new JPanel();
		contenedorMensajes.setMinimumSize(new Dimension(615, 530));
		contenedorMensajes.setMaximumSize(new Dimension(615, 530));
		contenedorMensajes.setPreferredSize(new Dimension(615, 540));
		contenedorMensajes.setSize(615, 540);
		BubbleText burbuja = new BubbleText(contenedorMensajes, "Hola grupo!!", Color.GREEN, "J.Ramón",
				BubbleText.SENT);
		BubbleText burbuja1 = new BubbleText(contenedorMensajes, "Hola grupo!!", Color.GREEN, "J.Ramón",
				BubbleText.RECEIVED);
		contenedorMensajes.add(burbuja);
		contenedorMensajes.add(burbuja1);

		// Scroll que hace wrap al contenedor de mensajes
		JScrollPane scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setPreferredSize(new Dimension(635, 540));
		chat.add(scrollMensajes);

		// Panel para introducir los mensajes
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

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
