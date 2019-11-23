package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controlador.ControladorChat;
import dominio.Usuario;

import javax.swing.JButton;
import java.awt.Color;

public class InterfazModificarGrupo extends JFrame {
	private JTextField textField;
	private String nombreGrupo;
	private InterfazGrupo grupo;
	private int x ;
	private int y;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazModificarGrupo window = new InterfazModificarGrupo();
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
	public InterfazModificarGrupo(int x, int y) {
		this.x = x;
		this.y = y;
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		setBounds(x, y, 450, 200);
		setTitle("Modificar Grupo");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNombreDelGrupo = new JLabel("NOMBRE DEL GRUPO A MODIFICAR");
		lblNombreDelGrupo.setBounds(110, 11, 292, 21);
		getContentPane().add(lblNombreDelGrupo);
		
		textField = new JTextField();
		textField.setBounds(110, 46, 188, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.setBounds(73, 90, 89, 23);
		getContentPane().add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				nombreGrupo = textField.getText();
				Usuario usuarioActual = ControladorChat.getUnicaInstancia().getUsuarioActual();
				if (ControladorChat.getUnicaInstancia().isAdmin(usuarioActual,nombreGrupo)) {
					grupo = new InterfazGrupo(x,y,nombreGrupo);
					grupo.setVisible(true);
					dispose();
				}else {
					showErrorAdmin();
				}
				
			}
			
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(242, 90, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(btnCancelar);
	}
	private void showErrorAdmin() {
		JOptionPane.showMessageDialog(this, "No eres administrador de este grupo", "Error", JOptionPane.ERROR_MESSAGE);
	}

}
