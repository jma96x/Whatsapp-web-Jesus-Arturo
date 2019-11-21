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
import java.lang.reflect.Array;
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
import dominio.Usuario;

import java.awt.Cursor;

public class InterfazGrupo extends JFrame {

	private JTextField nombreGrupo;
	private String nombreGrupoModificar;
	private int x;
	private int y;

	List<ContactoIndividual> contactosUsuarioActual = ControladorChat.getUnicaInstancia()
			.getContactosIndividualesUsuarioActual();
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList listaContactos = new JList(listModel);
	DefaultListModel<String> listModel1 = new DefaultListModel<String>();
	JList listaContactosAñadidos = new JList(listModel1);
	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { InterfazGrupo window = new
	 * InterfazGrupo(); window. setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public InterfazGrupo(int x, int y) {
		this.x = x;
		this.y = y;
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
		setImage(btnFotoUsuario,imgUsuario,64,64);
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
				List<String> nombresFinales = new LinkedList<String>();
				String groupName = nombreGrupo.getText();
			    if (groupName.isEmpty()) {
					showErrorGrupoSinNombre();
					return;
				}
				//RECUPERAMOS LOS NOMBRES DE LA LISTA DE CONTACTOS AÑADIDOS AL GRUPO
				for (int i = 0; i < listaContactosAñadidos.getModel().getSize(); i++) {
					nombresFinales.add((String) listaContactosAñadidos.getModel().getElementAt(i));
				}
				if (nombresFinales.isEmpty()) {
					showErrorGrupoVacio();
					return;
				}
				//CONSTRUIMOS EL SUBCONJUNTO DE CONTACTOS QUE FORMAN EL GRUPO DEL CONJUNTO DE CONTACTOS DEL USUARIO
				List<ContactoIndividual> contactosFinales = new LinkedList<ContactoIndividual>();
				for (String nombreContactoAñadido : nombresFinales) {
					for (ContactoIndividual contactoUsuario : contactosUsuarioActual) {
						if (contactoUsuario.getNombre().equals(nombreContactoAñadido)) {
							contactosFinales.add(contactoUsuario);
						}
					}
				}
				String img = usuarioActual.getImg();
				if (!ControladorChat.getUnicaInstancia().crearGrupo(groupName,img, contactosFinales, usuarioActual)) {
					showErrorGrupoRepetido();
					return;
				}
				else { 
					dispose();
				}
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
		for (Contacto c : contactosUsuarioActual) {
			listModel.addElement(c.getNombre());
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

		nombreGrupo = new JTextField();
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
				@SuppressWarnings("unchecked")
				List<String> contactosSeleccionados = (List<String>) listaContactos.getSelectedValuesList();
				for (String s : contactosSeleccionados) {
					if (!listModel1.contains(s))
						listModel1.addElement(s);
				}
			}
		});
		JButton btnEliminarContacto = new JButton("<--");
		btnEliminarContacto.setBounds(43, 264, 121, 39);
		panelCentro.add(btnEliminarContacto);
		btnEliminarContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				List<String> contactosSeleccionados = (List<String>) listaContactosAñadidos.getSelectedValuesList();
				for (String s : contactosSeleccionados) {
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
	private void showErrorGrupoRepetido() {
		JOptionPane.showMessageDialog(this,
				"Grupo repetido, intente otro", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	private void showErrorGrupoVacio() {
		JOptionPane.showMessageDialog(this,
				"Grupo vacío, ínvalido.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	private void showErrorGrupoSinNombre() {
		JOptionPane.showMessageDialog(this,
				"Grupo sin nombre.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}
