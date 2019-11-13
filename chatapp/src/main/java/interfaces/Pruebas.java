package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;

public class Pruebas {

	private JFrame frame;
	private JTextField inputMensaje;
	private JTextField inputNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pruebas window = new Pruebas();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Pruebas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 635, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(635, 660));
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(null);
		
		JLabel lblBusquedaDeMensajes = new JLabel("BUSQUEDA DE MENSAJES");
		lblBusquedaDeMensajes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusquedaDeMensajes.setBounds(120, 6, 365, 51);
		panel.add(lblBusquedaDeMensajes);
		
		inputMensaje = new JTextField();
		inputMensaje.setBounds(208, 93, 193, 51);
		panel.add(inputMensaje);
		inputMensaje.setColumns(10);
		
		JLabel lblMensaje = new JLabel("Mensaje");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(204, 59, 199, 33);
		panel.add(lblMensaje);
		
		JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreUsuario.setBounds(112, 185, 107, 33);
		panel.add(lblNombreUsuario);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setBounds(406, 190, 97, 23);
		panel.add(lblFecha);
		
		inputNombre = new JTextField();
		inputNombre.setBounds(101, 215, 138, 23);
		panel.add(inputNombre);
		inputNombre.setColumns(10);
		
		JDateChooser inputFecha = new JDateChooser();
		inputFecha.setBounds(406, 215, 97, 29);
		panel.add(inputFecha);
		
		JPanel mensajes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) mensajes.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		mensajes.setPreferredSize(new Dimension(513, 240));
		mensajes.setBounds(47, 296, 513, 240);
		panel.add(mensajes);
		
		JPanel contenedorMensajes = new JPanel();
		contenedorMensajes.setPreferredSize(new Dimension(513, 240));
		contenedorMensajes.setBounds(47, 296, 513, 240);
		contenedorMensajes.setPreferredSize(new Dimension(513, 240));
		
		JScrollPane scrollPane = new JScrollPane(contenedorMensajes);
		scrollPane.setPreferredSize(new Dimension(513, 240));
		
		
		mensajes.add(scrollPane);
		
		JButton btnVolver = new JButton("New button");
		btnVolver.setBounds(10, 11, 40, 40);
		panel.add(btnVolver);
		
	}
}
