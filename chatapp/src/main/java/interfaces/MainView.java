package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import tds.BubbleText;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;

import javax.swing.ScrollPaneConstants;

import controlador.ControladorChat;
import dominio.Usuario;

import javax.swing.JList;


@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener {
	private JTextField InputMensaje;
	private JList listContactos;
	private JFrame frmMainWindow;
	final JPanel panelArriba = new JPanel();
	JButton btnEstado = new JButton();
	final JButton btnFunciones = new JButton();
	JButton btnEliminarMensaje = new JButton();
	final JPanel panelContactos = new JPanel();
	JPanel contenedorContactos = new JPanel();
	JButton btnFotoUsuario = new JButton();
	final JPanel panelMensajes = new JPanel();
	JButton btnBuscarMensaje = new JButton();
	JButton btnFotoContacto = new JButton();
	JLabel lblNombrecontacto;
	JPanel chat = new JPanel();
	JPanel contenedorMensajes = new JPanel();
	JPanel lineaMensajes = new JPanel();
	JButton btnEmojis = new JButton();
	JButton btnEnviarMensaje = new JButton("Enviar");
	
	DefaultListModel<InterfazContacto> listModel = new DefaultListModel<InterfazContacto>();
	
	private boolean seguimientoVentanas[] = new boolean[3];
	private static final int PERFIL_USUARIO = 0;
	private static final int PERFIL_CONTACTO = 1;
	private static final int BUSQUEDA_MENSAJES = 2;
	
	private InterfazGrupo grupo ;
	private InterfazPerfilUsuario perfilUsuario;
	private InterfazPerfilContacto perfilContacto;
	private InterfazBuscarMensajes buscarMensaje;
	private InterfazCrearContacto panelCrearContacto ;
	private InterfazModificarGrupo panelModificarGrupo ;
	private InterfazMostrarContactos panelMostrarContactos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.mostrarVentana();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void mostrarVentana() {
		frmMainWindow.setVisible(true);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setResizable(false);
		frmMainWindow.setTitle("Whatsapp Web");
		frmMainWindow.setBounds(100, 100, 1000, 750);
		frmMainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Usuario usuarioActual = ControladorChat.getUnicaInstancia().getUsuarioActual();
		panelArriba.setBackground(Color.LIGHT_GRAY);
		panelArriba.setPreferredSize(new Dimension(1000, 90));
		panelArriba.setSize(new Dimension(70, 70));
		frmMainWindow.getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);

		btnEstado.setPreferredSize(new Dimension(38, 40));
		btnEstado.setBounds(180, 30, 38, 40);
		panelArriba.add(btnEstado);
		this.setImage(btnEstado, "/img/estados.png", 38, 40);

		btnFunciones.setBounds(250, 30, 40, 40);
		panelArriba.add(btnFunciones);
		btnFunciones.setPreferredSize(new Dimension(40, 40));
		this.setImage(btnFunciones, "/img/funciones.png", 40, 40);
		btnFunciones.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// A todos estos popups hay que añadirles manejador de eventos
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(panelArriba, popupMenu);

				JMenuItem crearContacto = new JMenuItem("Crear contacto");
				popupMenu.add(crearContacto);
				crearContacto.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminateOtherWindows();
						int x = frmMainWindow.getX();
						int y = frmMainWindow.getY();
						/* String code = JOptionPane.showInputDialog(
								 	frmMainWindow, 
							        "Crear contacto nuevo", 
							        "Secret code needed (title)", 
							        JOptionPane.QUESTION_MESSAGE);
						 System.out.println(code);*/
						panelCrearContacto = new InterfazCrearContacto(x,y);
						panelCrearContacto.mostrarVentana();
					}
				});

				JMenuItem crearGrupo = new JMenuItem("Crear grupo");
				popupMenu.add(crearGrupo);
				crearGrupo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminateOtherWindows();
						int x = frmMainWindow.getX();
						int y = frmMainWindow.getY();
						grupo = new InterfazGrupo(x,y);
						grupo.setVisible(true);
					}
				});

				JMenuItem modificarGrupo = new JMenuItem("Modificar grupo");
				popupMenu.add(modificarGrupo);
				modificarGrupo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminateOtherWindows();
						int x = frmMainWindow.getX();
						int y = frmMainWindow.getY();
						panelModificarGrupo = new InterfazModificarGrupo(x,y);
						panelModificarGrupo.setVisible(true);
					}
				});

				JMenuItem mostrarContactos = new JMenuItem("Mostrar contactos");
				popupMenu.add(mostrarContactos);
				mostrarContactos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminateOtherWindows();
						int x = frmMainWindow.getX();
						int y = frmMainWindow.getY();
						panelMostrarContactos = new InterfazMostrarContactos(x,y,getInstanciaActual());
						panelMostrarContactos.setVisible(true);
					}
				});

				JMenuItem premium = new JMenuItem("Hacerse premium");
				popupMenu.add(premium);

				JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
				popupMenu.add(cerrarSesion);
				cerrarSesion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						eliminateOtherWindows();
						Login nuevo = new Login();
						nuevo.mostrarVentana();
						frmMainWindow.dispose();
					}
				});

				JMenuItem estadisticas = new JMenuItem("Estadísticas");
				popupMenu.add(estadisticas);

				popupMenu.show(e.getComponent(), 40, -10);
			}
		});

		btnEliminarMensaje.setBounds(870, 30, 40, 40);
		panelArriba.add(btnEliminarMensaje);
		this.setImage(btnEliminarMensaje, "/img/eliminator.png", 40, 40);
		btnEliminarMensaje.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// A todos estos popups hay que añadirles manejador de eventos
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(panelArriba, popupMenu);

				JMenuItem eliminarMensajes = new JMenuItem("Eliminar Mensajes");
				popupMenu.add(eliminarMensajes);

				JMenuItem eliminarContacto = new JMenuItem("Eliminar Contacto");
				popupMenu.add(eliminarContacto);
				popupMenu.show(e.getComponent(), 40, -10);
			}
		});

		// Panel izquierdo principal
		panelContactos.setBackground(Color.WHITE);
		panelContactos.setPreferredSize(new Dimension(350, 620));
		panelContactos.setLayout(null);
		// contenedorContactosContactos
		contenedorContactos.setLayout(null);
		contenedorContactos.setPreferredSize(new Dimension(350, 620));
		// Scroll contactos
		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		
		//Lista contactos panel izquierdo
		InterfazContacto prueba = new InterfazContacto("/img/contact.png", new Date(), "ramon", "tenemos que hablar");
		listModel.addElement(prueba);
		listContactos = new JList(listModel);
		listContactos.setCellRenderer(new InterfazContactoRenderer());
		listContactos.setPreferredSize(new Dimension(350, 620));
		listContactos.setBounds(0, 0, 330, 620);

		contenedorContactos.add(listContactos);
		scrollContactos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollContactos.setPreferredSize(new Dimension(350, 620));
		scrollContactos.setSize(350, 620);
		
		
		panelContactos.add(scrollContactos);

		frmMainWindow.getContentPane().add(panelContactos, BorderLayout.WEST);

		btnFotoUsuario.setBounds(10, 11, 64, 64);
		panelArriba.add(btnFotoUsuario);
		String img = usuarioActual.getImg();
		setImage(btnFotoUsuario, img, 64, 64);
		btnFotoUsuario.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!seguimientoVentanas[PERFIL_USUARIO]) {
					perfilUsuario = new InterfazPerfilUsuario();
					final JPanel perfil = perfilUsuario.getPerfil();
					JButton btnVolver = new JButton("Volver");
					btnVolver.setBounds(191, 534, 89, 23);
					perfil.add(btnVolver);
					btnVolver.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							seguimientoVentanas[PERFIL_USUARIO] = false;
							frmMainWindow.getContentPane().remove(perfil);
							frmMainWindow.getContentPane().add(panelContactos, BorderLayout.WEST);
							frmMainWindow.revalidate();
							frmMainWindow.repaint();

						}

					});
					frmMainWindow.getContentPane().remove(panelContactos);
					frmMainWindow.getContentPane().add(perfil, BorderLayout.WEST);
					seguimientoVentanas[PERFIL_USUARIO] = true;
					frmMainWindow.invalidate();
					frmMainWindow.validate();
				}

			}
		});

		// Panel derecho principal
		panelMensajes.setMinimumSize(new Dimension(635, 660));
		panelMensajes.setMaximumSize(new Dimension(635, 660));
		panelMensajes.setPreferredSize(new Dimension(635, 660));
		panelMensajes.setSize(635, 660);

		// Boton Buscar Mensajes del contacto
		btnBuscarMensaje.setBounds(789, 30, 40, 40);
		panelArriba.add(btnBuscarMensaje);
		this.setImage(btnBuscarMensaje, "/img/search.png", 40, 40);
		btnBuscarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!seguimientoVentanas[BUSQUEDA_MENSAJES]) {
					buscarMensaje = new InterfazBuscarMensajes();
					final JPanel buscar = buscarMensaje.getBuscar();
					JButton btnVolver = new JButton();
					btnVolver.setBounds(10, 11, 40, 40);
					setImage(btnVolver, "/img/close.png", 40, 40);
					buscar.add(btnVolver);

					btnVolver.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							seguimientoVentanas[BUSQUEDA_MENSAJES] = false;
							frmMainWindow.getContentPane().remove(buscar);
							frmMainWindow.getContentPane().add(panelMensajes, BorderLayout.CENTER);
							frmMainWindow.revalidate();
							frmMainWindow.repaint();

						}

					});
					// Esto es para que no se solapen las ventanas del perfil de contacto y la
					// busqueda del mensaje
					if (seguimientoVentanas[PERFIL_CONTACTO]) {
						seguimientoVentanas[PERFIL_CONTACTO] = false;
						frmMainWindow.getContentPane().remove(perfilContacto.getPerfilContacto());
					}
					frmMainWindow.getContentPane().remove(panelMensajes);
					frmMainWindow.getContentPane().add(buscar, BorderLayout.CENTER);
					seguimientoVentanas[BUSQUEDA_MENSAJES] = true;
					frmMainWindow.invalidate();
					frmMainWindow.validate();
				}
			}
		});

		// Boton perfil contacto
		btnFotoContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!seguimientoVentanas[PERFIL_CONTACTO]) {
					perfilContacto = new InterfazPerfilContacto();
					final JPanel perfil = perfilContacto.getPerfilContacto();
					JButton btnVolver = new JButton();
					btnVolver.setBounds(10, 11, 40, 40);
					setImage(btnVolver, "/img/close.png", 40, 40);
					perfil.add(btnVolver);
					System.out.println("hola");
					btnVolver.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							seguimientoVentanas[PERFIL_CONTACTO] = false;
							frmMainWindow.getContentPane().remove(perfil);
							frmMainWindow.getContentPane().add(panelMensajes, BorderLayout.CENTER);
							frmMainWindow.revalidate();
							frmMainWindow.repaint();

						}

					});
					// Esto es para que no se solapen las ventanas del perfil de contacto y la
					// busqueda del mensaje
					if (seguimientoVentanas[BUSQUEDA_MENSAJES]) {
						seguimientoVentanas[BUSQUEDA_MENSAJES] = false;
						frmMainWindow.getContentPane().remove(buscarMensaje.getBuscar());
					}
					frmMainWindow.getContentPane().remove(panelMensajes);
					frmMainWindow.getContentPane().add(perfil, BorderLayout.CENTER);
					seguimientoVentanas[PERFIL_CONTACTO] = true;
					frmMainWindow.invalidate();
					frmMainWindow.validate();
					
				}
			}
		});
		lblNombrecontacto = new JLabel();
		lblNombrecontacto.setBounds(482, 30, 82, 30);
		panelArriba.add(lblNombrecontacto);
		// Panel de chat principal
		FlowLayout flowLayout = (FlowLayout) chat.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		chat.setBackground(Color.ORANGE);
		chat.setPreferredSize(new Dimension(635, 540));
		panelMensajes.add(chat, BorderLayout.NORTH);

		// Panel contenedor para poner mensajes scrollables (Importante .setSize())
		contenedorMensajes.setBackground(new Color(204, 153, 51));
		contenedorMensajes.setMinimumSize(new Dimension(615, 530));
		contenedorMensajes.setMaximumSize(new Dimension(615, 530));
		contenedorMensajes.setPreferredSize(new Dimension(615, 540));
		contenedorMensajes.setSize(615, 540);
		/*BubbleText burbuja = new BubbleText(contenedorMensajes, "Hola grupo!!", Color.GREEN, "J.Ramón",
				BubbleText.SENT);
		BubbleText burbuja1 = new BubbleText(contenedorMensajes, "Hola grupo!!", Color.GREEN, "J.Ramón",
				BubbleText.RECEIVED);
		contenedorMensajes.add(burbuja);
		contenedorMensajes.add(burbuja1);*/

		// Scroll que hace wrap al contenedor de mensajes
		JScrollPane scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setPreferredSize(new Dimension(635, 540));
		chat.add(scrollMensajes);

		// Panel para introducir los mensajes
		lineaMensajes.setBackground(Color.WHITE);
		lineaMensajes.setPreferredSize(new Dimension(635, 90));
		panelMensajes.add(lineaMensajes, BorderLayout.SOUTH);
		lineaMensajes.setLayout(null);

		InputMensaje = new JTextField();
		InputMensaje.setBounds(82, 0, 419, 79);
		lineaMensajes.add(InputMensaje);
		InputMensaje.setColumns(10);

		btnEmojis.setBounds(0, 0, 82, 70);
		lineaMensajes.add(btnEmojis);
		setImage(btnEmojis,"/img/icono.png",75,70);
		
		btnEmojis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// A todos estos popups hay que añadirles manejador de eventos
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(panelMensajes, popupMenu);
				JMenuItem emoji_1 = new JMenuItem();
				emoji_1.setIcon(getEmoji("/img/emojis/1.png"));
				popupMenu.add(emoji_1);
				
				JMenuItem emoji_2 = new JMenuItem();
				emoji_2.setIcon(getEmoji("/img/emojis/2.png"));
				popupMenu.add(emoji_2);
				
				JMenuItem emoji_3 = new JMenuItem();
				emoji_3.setIcon(getEmoji("/img/emojis/3.png"));
				popupMenu.add(emoji_3);
				
				JMenuItem emoji_4 = new JMenuItem();
				emoji_4.setIcon(getEmoji("/img/emojis/4.png"));
				popupMenu.add(emoji_4);
				
				JMenuItem emoji_5 = new JMenuItem();
				emoji_5.setIcon(getEmoji("/img/emojis/5.png"));
				popupMenu.add(emoji_5);
				
				JMenuItem emoji_6 = new JMenuItem();
				emoji_6.setIcon(getEmoji("/img/emojis/6.png"));
				popupMenu.add(emoji_6);
				
				JMenuItem emoji_7 = new JMenuItem();
				emoji_7.setIcon(getEmoji("/img/emojis/7.png"));
				popupMenu.add(emoji_7);
				
				JMenuItem emoji_8 = new JMenuItem();
				emoji_8.setIcon(getEmoji("/img/emojis/8.png"));
				popupMenu.add(emoji_8);
				
				popupMenu.show(e.getComponent(), 0, -280);
			}
		});
		btnEnviarMensaje.setBounds(501, 0, 134, 79);
		lineaMensajes.add(btnEnviarMensaje);

		frmMainWindow.getContentPane().add(panelMensajes, BorderLayout.CENTER);
	}
	
	public MainView getInstanciaActual() {
		return this;
	}
	@SuppressWarnings("deprecation")
	public void actualizarContacto() {
		String nombreContacto = ControladorChat.getUnicaInstancia().getNombreContactoActual();
		String img = ControladorChat.getUnicaInstancia().getImgContactoActual();
		btnFotoContacto.setBounds(389, 11, 64, 64);
		panelArriba.add(btnFotoContacto);
		setImage(btnFotoContacto,img,64,64);
		lblNombrecontacto.setText(nombreContacto);
	}
	private ImageIcon getEmoji(String emoji) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(emoji));
			img = img.getScaledInstance(30,30, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
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
	private void eliminateOtherWindows() {
		if (panelCrearContacto != null && panelCrearContacto.isDisplayable()) {
			panelCrearContacto.dispose();
		}
		if (grupo != null && grupo.isDisplayable()) {
			grupo.dispose();
		}
		if (panelModificarGrupo != null && panelModificarGrupo.isDisplayable()) {
			panelModificarGrupo.dispose();
		}
		if (panelMostrarContactos != null && panelMostrarContactos.isDisplayable()) {
			panelMostrarContactos.dispose();
		}
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
