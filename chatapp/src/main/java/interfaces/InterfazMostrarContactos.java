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
import java.awt.Color;

import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import javax.swing.JButton;

public class InterfazMostrarContactos extends JFrame {

	private int x;
	private int y;
	private MainView mainView;
	DefaultListModel<Contacto> listModel = new DefaultListModel<Contacto>();

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { InterfazMostrarContactos window = new
	 * InterfazMostrarContactos(100, 100,frmMainWindow); window.setVisible(true); }
	 * catch (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public InterfazMostrarContactos(int x, int y, MainView mainWindow) {
		this.x = x;
		this.y = y;
		initialize();
		this.mainView = mainWindow;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(x, y, 600, 650);
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
		panelContactos.setBounds(new Rectangle(0, 0, 400, 450));
		panelContactos.setMinimumSize(new Dimension(400, 550));
		panelContactos.setSize(new Dimension(400, 450));
		panelContactos.setPreferredSize(new Dimension(400, 450));
		panelContactos.setMaximumSize(new Dimension(400, 550));
		getContentPane().add(panelContactos, BorderLayout.CENTER);

		JPanel contenedorContactos = new JPanel();
		FlowLayout flowLayout = (FlowLayout) contenedorContactos.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contenedorContactos.setBackground(Color.WHITE);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		contenedorContactos.setPreferredSize(new Dimension(500, 400));

		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		scrollContactos.setBounds(42, 0, 500, 400);

		final JList listContactos = new JList(listModel);
		panelContactos.setLayout(null);
		contenedorContactos.add(listContactos);
		scrollContactos.setPreferredSize(new Dimension(500, 400));
		panelContactos.add(scrollContactos);

		List<Contacto> contactos = ControladorChat.getUnicaInstancia().getContactosUsuarioActual();
		for (Contacto c : contactos) {
			listModel.addElement(c);
		}

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.setBounds(135, 466, 89, 23);
		panelContactos.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacto contacto = (Contacto) listContactos.getSelectedValue();
				if (contacto == null)
					return;
				
				ControladorChat.getUnicaInstancia().setContactoActual(contacto);
				mainView.actualizarContacto();
				dispose();
			}
			
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(344, 466, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		panelContactos.add(btnCancelar);


	}
}
