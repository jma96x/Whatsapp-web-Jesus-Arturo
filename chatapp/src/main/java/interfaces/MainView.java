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
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ScrollPaneConstants;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Mensaje;
import controlador.ControladorChat;
import dominio.Usuario;

import javax.swing.JList;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private JTextField inputMensaje;
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

	private InterfazGrupo grupo;
	private InterfazPerfilUsuario perfilUsuario;
	private InterfazPerfilContacto perfilContacto;
	private InterfazBuscarMensajes buscarMensaje;
	private InterfazCrearContacto panelCrearContacto;
	private InterfazModificarGrupo panelModificarGrupo;
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
		frmMainWindow.getContentPane().setPreferredSize(new Dimension(2000, 2000));
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
						panelCrearContacto = new InterfazCrearContacto(x, y);
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
						grupo = new InterfazGrupo(x, y);
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
						panelModificarGrupo = new InterfazModificarGrupo(x, y,MainView.this);
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
						panelMostrarContactos = new InterfazMostrarContactos(x, y, MainView.this);
						panelMostrarContactos.setVisible(true);
					}
				});

				JMenuItem premium = new JMenuItem("Hacerse premium");
				popupMenu.add(premium);
				premium.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						showPremiumDone();
						// ControladorChat.getUnicaInstancia().convertirseUsuarioPremium();
					}
				});

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
				estadisticas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					/*	if (!ControladorChat.getUnicaInstancia().isUserPremium()) {
							showErrorPremium();
							return;
						}*/
						// TODO Aqui hay que hacer las interfaces de las estadisticas
					}
				});

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
				eliminarMensajes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!ControladorChat.getUnicaInstancia().existContactoActual()) {
							showChooseContact();
							return;
						}
						String texto = JOptionPane.showInputDialog("Introduce el mensaje que quieres eliminar");
						// ControladorChat.getUnicaInstancia().eliminarMensaje(texto);
					}

				});
				JMenuItem eliminarContacto = new JMenuItem("Eliminar Contacto");
				popupMenu.add(eliminarContacto);
				eliminarContacto.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String nombreContacto = JOptionPane
								.showInputDialog("Introduce el contacto que quieres eliminar");
						// ControladorChat.getUnicaInstancia.eliminarContacto(nombreContacto);
					}
				});
				popupMenu.show(e.getComponent(), 40, -10);
			}
		});

		// Panel izquierdo principal
		panelContactos.setBackground(Color.WHITE);
		panelContactos.setPreferredSize(new Dimension(350, 2000));
		panelContactos.setSize(new Dimension(350, 620));
		panelContactos.setLayout(null);
		// Scroll contactos
		final JScrollPane scrollContactos = new JScrollPane(panelContactos);
		scrollContactos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollContactos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// Lista contactos panel izquierdo
		listContactos = new JList(listModel);
		listContactos.setCellRenderer(new InterfazContactoRenderer());
		listContactos.setPreferredSize(new Dimension(350, 2000));
		listContactos.setBounds(0, 0, 330, 32578);
		listContactos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					// Double-click detected
					int index = list.locationToIndex(evt.getPoint());
					InterfazContacto seleccionado = listModel.getElementAt(index);
					ControladorChat.getUnicaInstancia().setContactoActual(seleccionado.getContacto());
					actualizarContacto();
				}
			}
		});
		panelContactos.add(listContactos);

		frmMainWindow.getContentPane().add(scrollContactos, BorderLayout.WEST);

		actualizarListaContactos();

		btnFotoUsuario.setBounds(10, 11, 64, 64);
		panelArriba.add(btnFotoUsuario);
		String imgUsuario = ControladorChat.getUnicaInstancia().getUsuarioActual().getImg();
		if (imgUsuario.equals("/img/defecto.jpg"))
			setImage(btnFotoUsuario, imgUsuario, 64, 64);
		else 
			setImageAbsoluta(btnFotoUsuario,imgUsuario,64,64);
		btnFotoUsuario.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!seguimientoVentanas[PERFIL_USUARIO]) {
					perfilUsuario = new InterfazPerfilUsuario(MainView.this);
					final JPanel perfil = perfilUsuario.getPerfil();
					JButton btnVolver = new JButton("Volver");
					btnVolver.setBounds(191, 534, 89, 23);
					perfil.add(btnVolver);
					btnVolver.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							seguimientoVentanas[PERFIL_USUARIO] = false;
							frmMainWindow.getContentPane().remove(perfil);
							frmMainWindow.getContentPane().add(scrollContactos, BorderLayout.WEST);
							frmMainWindow.revalidate();
							frmMainWindow.repaint();
						}

					});
					frmMainWindow.getContentPane().remove(scrollContactos);
					frmMainWindow.getContentPane().add(perfil, BorderLayout.WEST);
					seguimientoVentanas[PERFIL_USUARIO] = true;
					frmMainWindow.invalidate();
					frmMainWindow.validate();
				}

			}
		});

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

		// Panel derecho principal
		panelMensajes.setMinimumSize(new Dimension(635, 660));
		panelMensajes.setMaximumSize(new Dimension(635, 660));
		panelMensajes.setPreferredSize(new Dimension(635, 660));
		panelMensajes.setSize(635, 660);
		// Panel de chat principal
		FlowLayout flowLayout = (FlowLayout) chat.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		chat.setMinimumSize(new Dimension(615, 530));
		chat.setMaximumSize(new Dimension(32576, 32576));
		chat.setBackground(new Color(204, 153, 51));
		chat.setPreferredSize(new Dimension(615, 2000));
		chat.setSize(615, 540);
		// Scroll que hace wrap al contenedor de mensajes
		JScrollPane scrollMensajes = new JScrollPane(chat);
		scrollMensajes.setPreferredSize(new Dimension(635, 540));
		scrollMensajes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMensajes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelMensajes.add(scrollMensajes, BorderLayout.NORTH);
		// Panel para introducir los mensajes
		lineaMensajes.setBackground(Color.WHITE);
		lineaMensajes.setPreferredSize(new Dimension(635, 90));
		panelMensajes.add(lineaMensajes, BorderLayout.SOUTH);
		lineaMensajes.setLayout(null);

		inputMensaje = new JTextField();
		inputMensaje.setBounds(82, 0, 419, 79);
		lineaMensajes.add(inputMensaje);
		inputMensaje.setColumns(10);

		btnEmojis.setBounds(0, 0, 82, 70);
		lineaMensajes.add(btnEmojis);
		setImage(btnEmojis, "/img/icono.png", 75, 70);

		btnEmojis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// A todos estos popups hay que añadirles manejador de eventos
				JPopupMenu popupMenu = new JPopupMenu();
				addPopup(panelMensajes, popupMenu);
				for (int i = 1; i < 9; i++) {
					JMenuItem emoji = new JMenuItem();
					emoji.setIcon(getEmoji("/img/emojis/" + i + ".png"));
					popupMenu.add(emoji);
					final int numeroEmoji = i - 1;
					emoji.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (!ControladorChat.getUnicaInstancia().existContactoActual()) {
								showChooseContact();
								return;
							}
							BubbleText burbuja = new BubbleText(chat, numeroEmoji, Color.GREEN,
									ControladorChat.getUnicaInstancia().getUsuarioActual().getNombre(), BubbleText.SENT,
									10);
							chat.add(burbuja);
							ControladorChat.getUnicaInstancia().mandarMensaje(null, numeroEmoji);
							actualizarListaContactos("Emoticono");
						}
					});
				}
				popupMenu.show(e.getComponent(), 0, -280);
			}
		});
		btnEnviarMensaje.setBounds(501, 0, 134, 79);
		btnEnviarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!ControladorChat.getUnicaInstancia().existContactoActual()) {
					showChooseContact();
					return;
				}
				String msj = inputMensaje.getText();
				if (msj.trim().length() == 0) {
					showMensajeVacio();
					return;
				}
				String subMsj;
				if (msj.length() >= 30)
					subMsj = msj.substring(0, 30);
				else
					subMsj = msj;
				BubbleText burbuja = new BubbleText(chat, msj, Color.GREEN,
						ControladorChat.getUnicaInstancia().getUsuarioActual().getNombre(), BubbleText.SENT);
				chat.add(burbuja);
				inputMensaje.setText("");
				ControladorChat.getUnicaInstancia().mandarMensaje(msj, -1);
				actualizarListaContactos(subMsj);

			}
		});
		lineaMensajes.add(btnEnviarMensaje);

		frmMainWindow.getContentPane().add(panelMensajes, BorderLayout.CENTER);
	}
	private void actualizarListaContactos(String mensaje) {
		boolean actualizado = false;
		String imgContacto = ControladorChat.getUnicaInstancia().getImgContactoActual();
		Contacto contactoActual = ControladorChat.getUnicaInstancia().getContactoActual();
		InterfazContacto nueva = null;
		for (int i = 0; i < listModel.getSize(); i++) { // Recorremos los renderers para ver si ya teniamos una
														// conversacion con el
			InterfazContacto aux = listModel.getElementAt(i);
			if (aux.getContacto().equals(contactoActual)) {
				nueva = new InterfazContacto(imgContacto, new Date(), contactoActual, mensaje);
				listModel.remove(i);
				listModel.insertElementAt(nueva, 0);
				actualizado = true;
			}
		}
		if (!actualizado) { // Esto quiere decir que no hemos tenido conversaciones previas con este
							// contacto
			String fotoContacto = ControladorChat.getUnicaInstancia().getImgContactoActual();
			nueva = new InterfazContacto(fotoContacto, new Date(), contactoActual, mensaje);
			listModel.insertElementAt(nueva, 0);
		}
	}
	public void actualizarListaContactos() {
		listModel.clear();
		// Inicializamos a como teniamos antes los contactos con sus conversaciones
				HashMap<Contacto, Mensaje> ultimosMensajes = ControladorChat.getUnicaInstancia().getUltimosMensajes();
				for (Contacto contacto : ultimosMensajes.keySet()) {
					Mensaje mensaje = ultimosMensajes.get(contacto);
					String fotoContacto = ControladorChat.getUnicaInstancia().getImgContacto(contacto);
					String subMsj = null;
					boolean ordenado = false;
					if (mensaje.getTexto() == null) {
						subMsj = "Emoticono";
					} else if (mensaje.getTexto().length() >= 30) {
						subMsj = mensaje.getTexto().substring(0, 30);
					} else {
						subMsj = mensaje.getTexto();
					}
					int tamañoConversaciones = listModel.getSize();
					for (int i = 0; i < tamañoConversaciones; i++) {
						Date nuevaFecha = mensaje.getFecha();
						String fechaConvActualAux = listModel.get(i).getFechaUltimoMensaje();
						SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
						try {
							Date fechaConvActual = parser.parse(fechaConvActualAux);
							if (!ordenado && nuevaFecha.after(fechaConvActual)) {
								InterfazContacto antiguo = new InterfazContacto(fotoContacto, mensaje.getFecha(), contacto,
										subMsj);
								listModel.insertElementAt(antiguo, i);
								ordenado = true;
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					if (!ordenado) {
						InterfazContacto antiguo = new InterfazContacto(fotoContacto, mensaje.getFecha(), contacto, subMsj);
						listModel.addElement(antiguo);
					}
				}
	}
	public void actualizarContacto() {
		String nombreContacto = null;
		String imgContacto = ControladorChat.getUnicaInstancia().getImgContactoActual();
		String tlfUsuario = ControladorChat.getUnicaInstancia().getUsuarioActual().getTelefono();
		btnFotoContacto.setBounds(389, 11, 64, 64);
		panelArriba.add(btnFotoContacto);
		if (imgContacto.equals("/img/defecto.jpg"))
			setImage(btnFotoUsuario, imgContacto, 64, 64);
		else 
			setImageAbsoluta(btnFotoUsuario,imgContacto,64,64);
		lblNombrecontacto.setText(nombreContacto);
		chat.removeAll();
		chat.revalidate();
		chat.repaint();
		List<Mensaje> mensajes = ControladorChat.getUnicaInstancia().getConversacionContactoActual();
		BubbleText burbuja = null;
		for (Mensaje m : mensajes) {
			int destino = BubbleText.SENT;
			if (m.getEmisor().getTelefono().equals(tlfUsuario)) {
				destino = BubbleText.SENT;
				nombreContacto = ControladorChat.getUnicaInstancia().getUsuarioActual().getNombre();
			} else {
				destino = BubbleText.RECEIVED;
				nombreContacto = ControladorChat.getUnicaInstancia().getNombrePropietarioMensaje(m.getEmisor());
			}
			if (m.getEmoticono() == -1) {
				burbuja = new BubbleText(chat, m.getTexto(), Color.GREEN, nombreContacto, destino);
				chat.add(burbuja);
			} else {
				int emoticono = m.getEmoticono();
				burbuja = new BubbleText(chat, emoticono, Color.GREEN, nombreContacto, destino, 10);
				chat.add(burbuja);
			}
		}
	}

	private ImageIcon getEmoji(String emoji) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(emoji));
			img = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
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

	private void showChooseContact() {
		JOptionPane.showMessageDialog(this, "Elige un contacto primero.", "Elegir Contacto", JOptionPane.ERROR_MESSAGE);
	}

	private void showMensajeVacio() {
		JOptionPane.showMessageDialog(this, "Mensaje en blanco no permitido.", "Enviar Mensaje",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showPremiumDone() {
		JOptionPane.showMessageDialog(this, "¡Te has convertido en premium!", "Usuario Premium",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorPremium() {
		JOptionPane.showMessageDialog(this, "Debes ser usuario premium para esta funcionalidad.", "Error Premium",
				JOptionPane.ERROR_MESSAGE);
	}

	public void actualizarFotoContacto() {
		String nuevaFoto = ControladorChat.getUnicaInstancia().getUsuarioActual().getImg();
		setImage(btnFotoContacto, nuevaFoto, 64,64);
	}

}
