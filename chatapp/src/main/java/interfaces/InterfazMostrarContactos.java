package interfaces;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.ControladorChat;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;

import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class InterfazMostrarContactos extends JFrame {

	private int x;
	private int y;
	DefaultListModel<Contacto> listModel = new DefaultListModel<Contacto>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazMostrarContactos window = new InterfazMostrarContactos(100, 100);
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
	public InterfazMostrarContactos(int x, int y) {
		this.x = x;
		this.y = y;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(x, y, 600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Mostrar Contactos");

		JPanel panelArriba = new JPanel();
		panelArriba.setPreferredSize(new Dimension(600, 50));
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);

		JLabel lblContactosDelUsuario = new JLabel("CONTACTOS DEL USUARIO: ");
		lblContactosDelUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblContactosDelUsuario.setBounds(164, 11, 242, 28);
		panelArriba.add(lblContactosDelUsuario);

		JPanel panelContactos = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelContactos.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panelContactos.setBounds(new Rectangle(0, 0, 400, 450));
		panelContactos.setMinimumSize(new Dimension(400, 550));
		panelContactos.setSize(new Dimension(400, 450));
		panelContactos.setPreferredSize(new Dimension(400, 450));
		panelContactos.setMaximumSize(new Dimension(400, 550));
		getContentPane().add(panelContactos, BorderLayout.CENTER);

		JPanel contenedorContactos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) contenedorContactos.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		contenedorContactos.setPreferredSize(new Dimension(500, 400));

		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);

		JList listContactos = new JList();
		listContactos = new JList(listModel);
		listContactos.setPreferredSize(new Dimension(500, 380));
		contenedorContactos.add(listContactos);
		scrollContactos.setPreferredSize(new Dimension(500, 400));
		panelContactos.add(scrollContactos);

		List<Contacto> contactos = ControladorChat.getUnicaInstancia().getContactosUsuarioActual();
		for (Contacto c : contactos) {
			listModel.addElement(c);
			/*if (c instanceof ContactoIndividual) {
				listModel.addElement(contacto);
			} else if (c instanceof Grupo) {
				Grupo g = (Grupo) c;
				JLabel nombreGrupo = new JLabel("Nombre grupo: " + g.getNombre());
				panel.add(nombreGrupo);
				for (ContactoIndividual ci : g.getParticipantes()) {
					JLabel contacto = new JLabel("Nombre: " + ci.getNombre() + " Teléfono: " + ci.getTelefonoUsuario());
					panel.add(contacto);
				}
			}*/
		}

	}
}
