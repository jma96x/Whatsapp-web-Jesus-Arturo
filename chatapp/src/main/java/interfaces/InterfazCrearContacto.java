package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class InterfazCrearContacto extends JFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazCrearContacto window = new InterfazCrearContacto();
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
	public InterfazCrearContacto() {
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setTitle("Crear Contacto");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel crearContacto = new JPanel();
		getContentPane().add(crearContacto, BorderLayout.CENTER);
		crearContacto.setLayout(null);
		
		JLabel lblCrearNuevoContacto = new JLabel("CREAR NUEVO CONTACTO");
		lblCrearNuevoContacto.setBounds(150, 21, 236, 36);
		crearContacto.add(lblCrearNuevoContacto);
		
		JLabel lblNombre = new JLabel("Nombre Contacto");
		lblNombre.setBounds(40, 68, 127, 36);
		crearContacto.add(lblNombre);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(281, 68, 90, 36);
		crearContacto.add(lblTelfono);
		
		textField = new JTextField();
		textField.setBounds(40, 103, 127, 20);
		crearContacto.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(281, 103, 127, 20);
		crearContacto.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.setBounds(103, 190, 89, 23);
		crearContacto.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(221, 190, 89, 23);
		crearContacto.add(btnCancelar);
	}
}
