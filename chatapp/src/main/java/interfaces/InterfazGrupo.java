package interfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JTextField;

import controlador.ControladorChat;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;

import java.awt.Cursor;

@SuppressWarnings("serial")
public class InterfazGrupo extends JFrame {

	private JTextField nombreGrupo = new JTextField();
	private String nombreGrupoModificar;
	private Grupo grupoModificar;
	private List<ContactoIndividual> participantesAntiguos;
	private String imgGrupo;
	private int x;
	private int y;

	DefaultListModel<Contacto> listModel = new DefaultListModel<Contacto>();
	JList<Contacto> listaContactos = new JList<Contacto>(listModel);
	DefaultListModel<Contacto> listModel1 = new DefaultListModel<Contacto>();
	JList<Contacto> listaContactosAñadidos = new JList<Contacto>(listModel1);

	/**
	 * Create the application.
	 */
	public InterfazGrupo(int x, int y) {
		this.x = x;
		this.y = y;
		this.nombreGrupoModificar = null;
		initialize();
	}

	// Este constructor esta para cuando la acción sea la de modificar el grupo
	public InterfazGrupo(int x, int y, String nombre) {
		this.x = x;
		this.y = y;
		this.nombreGrupoModificar = nombre;
		initialize();
	}

	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
		final Usuario usuarioActual = ControladorChat.getUnicaInstancia().getUsuarioActual();
		final List<ContactoIndividual> contactosUsuarioActual = ControladorChat.getUnicaInstancia()
				.getContactosIndividuales(usuarioActual);
		setTitle("Ventana Grupo");
		setBounds(x, y, 700, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		String imgUsuario = usuarioActual.getImg();
		// PANEL ARRIBA
		JPanel panelArriba = new JPanel();
		panelArriba.setPreferredSize(new Dimension(700, 80));
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);

		JButton btnFotoUsuario = new JButton();
		btnFotoUsuario.setRequestFocusEnabled(false);
		btnFotoUsuario.setBounds(10, 8, 64, 64);
		setImage(btnFotoUsuario, imgUsuario, 64, 64);
		panelArriba.add(btnFotoUsuario);
		String nombreUsuario = usuarioActual.getLogin();
		JLabel lblNombreusuario = new JLabel(nombreUsuario);
		lblNombreusuario.setBounds(84, 28, 122, 24);
		panelArriba.add(lblNombreusuario);

		JButton btnBuscar = new JButton();
		btnBuscar.setBounds(547, 32, 40, 40);
		setImage(btnBuscar, "/img/search.png", 40, 40);
		panelArriba.add(btnBuscar);

		JButton btnEliminar = new JButton();
		btnEliminar.setBounds(619, 32, 40, 40);
		setImage(btnEliminar, "/img/eliminator.png", 40, 40);
		panelArriba.add(btnEliminar);

		// PANEL ABAJO
		JPanel panelAbajo = new JPanel();
		panelAbajo.setPreferredSize(new Dimension(700, 50));
		getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		panelAbajo.setLayout(null);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<ContactoIndividual> contactosFinales = new LinkedList<ContactoIndividual>();
				String groupName = nombreGrupo.getText();
				if (groupName.isEmpty()) {
					showErrorGrupoSinNombre();
					return;
				}
				
				// CONTACTOS DEL USUARIO
				for (int i = 0; i < listaContactosAñadidos.getModel().getSize(); i++) {
					contactosFinales.add((ContactoIndividual)listaContactosAñadidos.getModel().getElementAt(i));
				}
				if (contactosFinales.isEmpty()) {
					showErrorGrupoVacio();
					return;
				}

				// Crear grupo del usuarioActual
				if (nombreGrupoModificar == null) {
					int codigoGrupo = ControladorChat.getUnicaInstancia().crearGrupo(usuarioActual, groupName, contactosFinales);
					if (codigoGrupo == -1) {
						showErrorGrupoRepetido();
						return;
					}
				} else { // Modificar grupo del usuarioActual
					Grupo nuevo = new Grupo(groupName, imgGrupo, contactosFinales, usuarioActual.getTelefono());
					ControladorChat.getUnicaInstancia().modificarGrupoDesdeUsuario(usuarioActual, nombreGrupoModificar,
							nuevo);
				}
				//Registramos o modificamos el grupo en los participantes
				//ControladorChat.getUnicaInstancia().registrarGrupoenParticipantes(contactosFinales,
				//		nombreGrupoModificar, participantesAntiguos, groupName, imgGrupo);
				showGrupoActualizado();
				dispose();
			}
		});
		btnAceptar.setBounds(220, 16, 98, 23);
		panelAbajo.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(345, 16, 98, 23);
		panelAbajo.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// PANEL IZQUIERDA
		JPanel panelIzquierda = new JPanel();
		panelIzquierda.setPreferredSize(new Dimension(230, 470));
		getContentPane().add(panelIzquierda, BorderLayout.WEST);
		panelIzquierda.setLayout(null);

		JLabel lblContactos = new JLabel("Contactos");
		lblContactos.setBounds(22, 39, 81, 14);
		panelIzquierda.add(lblContactos);

		JPanel panelContactos = new JPanel();
		panelContactos.setPreferredSize(new Dimension(181, 327));
		panelContactos.setBounds(new Rectangle(22, 64, 181, 327));
		FlowLayout flowLayout = (FlowLayout) panelContactos.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panelIzquierda.add(panelContactos);

		JPanel contenedorContactos = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) contenedorContactos.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		contenedorContactos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactos.setBackground(new Color(244, 164, 96));
		contenedorContactos.setPreferredSize(new Dimension(181, 327));

		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		scrollContactos.setPreferredSize(new Dimension(181, 327));
		scrollContactos.setBounds(new Rectangle(22, 64, 181, 327));

		listaContactos.setBackground(new Color(244, 164, 96));
		contenedorContactos.add(listaContactos);
		panelContactos.add(scrollContactos);
		// Añadir contactos del usuario a la jlist
		if (nombreGrupoModificar == null) {
			for (Contacto c : contactosUsuarioActual) {
				listModel.addElement(c);
			}
		} else {
			// añadir a los posible contactos a añadir aquellos que no esten ya añadidos y
			// los que están añadidos en la lista de contactos añadidos
			nombreGrupo.setText(nombreGrupoModificar);
			List<Grupo> grupos = ControladorChat.getUnicaInstancia().getGrupos(usuarioActual);
			for (Grupo g : grupos) {
				if (usuarioActual.getTelefono().equals(g.getTlfAdministrador())
						&& g.getNombre().equals(nombreGrupoModificar)) {
					grupoModificar = g;
					imgGrupo = g.getImg();
					participantesAntiguos = g.getParticipantes();
					List<ContactoIndividual> participantes = g.getParticipantes();
					List<ContactoIndividual> todosContactos = ControladorChat.getUnicaInstancia()
							.getContactosIndividuales(usuarioActual);
					for (ContactoIndividual c : todosContactos) {
						if (!participantes.contains(c))
							listModel.addElement(c);
						else
							listModel1.addElement(c);
					}
				}
			}
		}

		// PANEL DERECHA
		JPanel panelDerecha = new JPanel();
		panelDerecha.setPreferredSize(new Dimension(230, 470));
		getContentPane().add(panelDerecha, BorderLayout.EAST);
		panelDerecha.setLayout(null);

		JLabel lblContactosAadidos = new JLabel("Contactos Añadidos");
		lblContactosAadidos.setBounds(22, 39, 173, 23);
		panelDerecha.add(lblContactosAadidos);

		JPanel contactosAñadidos = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) contactosAñadidos.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		contactosAñadidos.setBounds(22, 64, 181, 327);
		panelDerecha.add(contactosAñadidos);

		JPanel contenedorContactosAñadidos = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) contenedorContactosAñadidos.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		contenedorContactosAñadidos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactosAñadidos.setPreferredSize(new Dimension(181, 327));

		JScrollPane scrollContactosAñadidos = new JScrollPane(contenedorContactosAñadidos);

		listaContactosAñadidos.setBackground(new Color(0, 255, 0));
		listaContactosAñadidos.setPreferredSize(new Dimension(181, 305));
		listaContactosAñadidos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactosAñadidos.add(listaContactosAñadidos);
		scrollContactosAñadidos.setPreferredSize(new Dimension(181, 327));
		contactosAñadidos.add(scrollContactosAñadidos);

		// PANEL CENTRO
		JPanel panelCentro = new JPanel();
		panelCentro.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		panelCentro.setToolTipText("");
		panelCentro.setPreferredSize(new Dimension(240, 470));
		getContentPane().add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);

		nombreGrupo.setToolTipText("Nombre grupo");
		nombreGrupo.setBounds(25, 80, 166, 31);
		panelCentro.add(nombreGrupo);
		nombreGrupo.setColumns(10);

		JLabel lblNombreDelGrupo = new JLabel("Nombre del grupo");
		lblNombreDelGrupo.setBounds(22, 39, 150, 18);
		panelCentro.add(lblNombreDelGrupo);

		JButton btnAñadirContacto = new JButton("-->");
		btnAñadirContacto.setBounds(43, 178, 121, 39);
		panelCentro.add(btnAñadirContacto);
		btnAñadirContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Contacto> contactosSeleccionados = (List<Contacto>) listaContactos.getSelectedValuesList();
				for (Contacto s : contactosSeleccionados) {
					listModel1.addElement(s);
					listModel.removeElement(s);
				}
			}
		});
		JButton btnEliminarContacto = new JButton("<--");
		btnEliminarContacto.setBounds(43, 264, 121, 39);
		panelCentro.add(btnEliminarContacto);
		btnEliminarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Contacto> contactosSeleccionados = (List<Contacto>) listaContactosAñadidos.getSelectedValuesList();
				for (Contacto s : contactosSeleccionados) {
					listModel.addElement(s);
					listModel1.removeElement(s);
				}
			}
		});
	}

	public String setNombreGrupoModificar(String nombre) {
		return this.nombreGrupoModificar = nombre;
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

	private void showGrupoActualizado() {
		JOptionPane.showMessageDialog(this, "Grupo actualizado correctamente", "Modificar Grupo",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorGrupoRepetido() {
		JOptionPane.showMessageDialog(this, "Grupo repetido, intente otro", "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showErrorGrupoVacio() {
		JOptionPane.showMessageDialog(this, "Grupo vacío, ínvalido.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showErrorGrupoSinNombre() {
		JOptionPane.showMessageDialog(this, "Grupo sin nombre.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
