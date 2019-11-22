 package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controlador.ControladorChat;
import dominio.Usuario;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class InterfazCrearContacto extends JFrame {

	private JFrame frmCrearContacto;
	private JTextField nombreContacto;
	private JTextField telefono;
	private int x ;
	private int y;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazCrearContacto window = new InterfazCrearContacto(x,y);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public InterfazCrearContacto(int x, int y) {
		this.x = x;
		this.y = y;
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		frmCrearContacto = new JFrame();
		frmCrearContacto.setResizable(false);
		frmCrearContacto.setBounds(x, y, 450, 300);
		frmCrearContacto.setTitle("Crear Contacto");
		frmCrearContacto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		final Usuario usuarioActual = ControladorChat.getUnicaInstancia().getUsuarioActual();
		JPanel crearContacto = new JPanel();
		frmCrearContacto.getContentPane().add(crearContacto, BorderLayout.CENTER);
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
		
		nombreContacto = new JTextField();
		nombreContacto.setBounds(40, 103, 127, 20);
		crearContacto.add(nombreContacto);
		nombreContacto.setColumns(10);
		
		telefono = new JTextField();
		telefono.setBounds(281, 103, 127, 20);
		crearContacto.add(telefono);
		telefono.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.setBounds(103, 190, 89, 23);
		crearContacto.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = nombreContacto.getText();
				String tlf = telefono.getText();
				if (!ControladorChat.getUnicaInstancia().existeUsuario(tlf)) {
					showErrorContactoNoExiste();
					return;
				}
				if (nombre.isEmpty() || tlf.isEmpty()) {
					showErrorContactoVacio();
					return;
				}
				if (usuarioActual.getTelefono().equals(tlf)){
					showErrorContactoIgualUsuario();
					return;
				}
				if (!ControladorChat.getUnicaInstancia().crearContactoIndividual(nombre,tlf)) {
					showErrorContactoRepetido();
					return;
				}
				frmCrearContacto.dispose();
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCrearContacto.dispose();
			}
		});
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(221, 190, 89, 23);
		crearContacto.add(btnCancelar);
	}
	private void showErrorContactoIgualUsuario() {
		JOptionPane.showMessageDialog(this,
				"Ese contacto eres tú!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	private void showErrorContactoNoExiste() {
		JOptionPane.showMessageDialog(this,
				"Ese contacto no existe", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	private void showErrorContactoVacio() {
		JOptionPane.showMessageDialog(this,
				"No deje campos vacíos", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showErrorContactoRepetido() {
		JOptionPane.showMessageDialog(this,
				"Contacto repetido, intente otro", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	public void mostrarVentana() {
		frmCrearContacto.setVisible(true);
	}
}
