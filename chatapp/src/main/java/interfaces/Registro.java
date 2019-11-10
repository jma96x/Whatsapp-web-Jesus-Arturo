package interfaces;

	import java.awt.BorderLayout;
	import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JPasswordField;
	import javax.swing.JTabbedPane;
	import javax.swing.JTextField;
	import java.awt.GridBagLayout;
	import java.awt.GridBagConstraints;
	import java.awt.Insets;
import com.toedter.calendar.JDateChooser;
	

	@SuppressWarnings("serial")
	public class Registro extends JFrame {	
		private JFrame ventana;
		private JButton btnRegistrar;
		private JButton btnCancelar;
		private JPanel jpanelAnterior;
		private JLabel lblNombre;
		private JLabel lblEdad;
		private JLabel lblEmail;
		private JLabel lblUsuario;
		private JLabel lblClave;
		private JTextField txtNombre;
		private JTextField txtEdad;
		private JTextField txtEmail;
		private JTextField txtUsuario;
		private JLabel lblRepite;
		private JPasswordField txtClave;
		private JPasswordField txtClave2;
		private JLabel warningAll;
		private JLabel warningEdadNumerica;
		private JLabel warningEmailMovil;
		private JLabel warningClave2;
		private JLabel warningExiste;
		private JLabel warningNombre;
		private JLabel warningApellidos;
		private JLabel warningDNIEdad;
		private JLabel warningUsuario;
		private JLabel warningClave;
		private JLabel lblMovil;
		private JTextField txtMovil;
		private JDateChooser dateChooser;
		
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Registro window = new Registro();
						window.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		public Registro() {
			initialize();
		}
		public void initialize(){
			//ventana=frame;
			//jpanelAnterior = (JPanel) ventana.getContentPane();
			
			getContentPane().setLayout(new BorderLayout());
			setBounds(100, 100, 458, 301);
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			
			JPanel datosPersonales = new JPanel ();
			tabbedPane.addTab("Datos de Registro", null, datosPersonales, null);
			GridBagLayout gbl_datosPersonales = new GridBagLayout();
			gbl_datosPersonales.columnWidths = new int[]{30, 0, 0, 0, 0, 0, 0};
			gbl_datosPersonales.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0};
			gbl_datosPersonales.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_datosPersonales.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			datosPersonales.setLayout(gbl_datosPersonales);
			
			lblNombre = new JLabel("Nombre : ");
			GridBagConstraints gbc_lblNombre = new GridBagConstraints();
			gbc_lblNombre.anchor = GridBagConstraints.EAST;
			gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
			gbc_lblNombre.gridx = 1;
			gbc_lblNombre.gridy = 1;
			datosPersonales.add(lblNombre, gbc_lblNombre);
			
			txtNombre = new JTextField();
			GridBagConstraints gbc_txtNombre = new GridBagConstraints();
			gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtNombre.gridwidth = 3;
			gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
			gbc_txtNombre.gridx = 2;
			gbc_txtNombre.gridy = 1;
			datosPersonales.add(txtNombre, gbc_txtNombre);
			txtNombre.setColumns(20);
			
			warningNombre = new JLabel("*");
			warningNombre.setForeground(Color.RED);
			GridBagConstraints gbc_warningNombre = new GridBagConstraints();
			gbc_warningNombre.anchor = GridBagConstraints.WEST;
			gbc_warningNombre.insets = new Insets(0, 0, 5, 0);
			gbc_warningNombre.gridx = 5;
			gbc_warningNombre.gridy = 1;
			datosPersonales.add(warningNombre, gbc_warningNombre);
			
			lblMovil = new JLabel("Tel\u00E9fono: ");
			GridBagConstraints gbc_lblMovil = new GridBagConstraints();
			gbc_lblMovil.anchor = GridBagConstraints.EAST;
			gbc_lblMovil.insets = new Insets(0, 0, 5, 5);
			gbc_lblMovil.gridx = 1;
			gbc_lblMovil.gridy = 2;
			datosPersonales.add(lblMovil, gbc_lblMovil);
			
			txtMovil = new JTextField();
			GridBagConstraints gbc_txtMovil = new GridBagConstraints();
			gbc_txtMovil.gridwidth = 2;
			gbc_txtMovil.insets = new Insets(0, 0, 5, 5);
			gbc_txtMovil.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtMovil.gridx = 2;
			gbc_txtMovil.gridy = 2;
			datosPersonales.add(txtMovil, gbc_txtMovil);
			txtMovil.setColumns(10);
			
			warningApellidos = new JLabel("*");
			warningApellidos.setForeground(Color.RED);
			GridBagConstraints gbc_warningApellidos = new GridBagConstraints();
			gbc_warningApellidos.anchor = GridBagConstraints.WEST;
			gbc_warningApellidos.insets = new Insets(0, 0, 5, 0);
			gbc_warningApellidos.gridx = 5;
			gbc_warningApellidos.gridy = 2;
			datosPersonales.add(warningApellidos, gbc_warningApellidos);
			
			lblEdad = new JLabel("Fecha Nacimiento :");
			GridBagConstraints gbc_lblEdad = new GridBagConstraints();
			gbc_lblEdad.anchor = GridBagConstraints.EAST;
			gbc_lblEdad.insets = new Insets(0, 0, 5, 5);
			gbc_lblEdad.gridx = 1;
			gbc_lblEdad.gridy = 3;
			datosPersonales.add(lblEdad, gbc_lblEdad);
			
			txtEdad = new JTextField();
			GridBagConstraints gbc_txtEdad = new GridBagConstraints();
			gbc_txtEdad.gridwidth = 2;
			gbc_txtEdad.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEdad.insets = new Insets(0, 0, 5, 5);
			gbc_txtEdad.gridx = 2;
			gbc_txtEdad.gridy = 3;
			datosPersonales.add(txtEdad, gbc_txtEdad);
			txtEdad.setColumns(8);
			
			dateChooser = new JDateChooser();
			GridBagConstraints gbc_dateChooser = new GridBagConstraints();
			gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
			gbc_dateChooser.fill = GridBagConstraints.BOTH;
			gbc_dateChooser.gridx = 4;
			gbc_dateChooser.gridy = 3;
			datosPersonales.add(dateChooser, gbc_dateChooser);
			
			warningDNIEdad = new JLabel("*");
			warningDNIEdad.setForeground(Color.RED);
			GridBagConstraints gbc_warningDNIEmail = new GridBagConstraints();
			gbc_warningDNIEmail.anchor = GridBagConstraints.WEST;
			gbc_warningDNIEmail.insets = new Insets(0, 0, 5, 0);
			gbc_warningDNIEmail.gridx = 5;
			gbc_warningDNIEmail.gridy = 3;
			datosPersonales.add(warningDNIEdad, gbc_warningDNIEmail);
			
			lblEmail = new JLabel("Email :");
			GridBagConstraints gbc_lblEmail = new GridBagConstraints();
			gbc_lblEmail.anchor = GridBagConstraints.EAST;
			gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
			gbc_lblEmail.gridx = 1;
			gbc_lblEmail.gridy = 4;
			datosPersonales.add(lblEmail, gbc_lblEmail);
			
			txtEmail = new JTextField();
			GridBagConstraints gbc_txtEmail = new GridBagConstraints();
			gbc_txtEmail.gridwidth = 2;
			gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
			gbc_txtEmail.gridx = 2;
			gbc_txtEmail.gridy = 4;
			datosPersonales.add(txtEmail, gbc_txtEmail);
			txtEmail.setColumns(10);
			
			warningEmailMovil = new JLabel("*");
			warningEmailMovil.setForeground(Color.RED);
			GridBagConstraints gbc_warningMovil = new GridBagConstraints();
			gbc_warningMovil.anchor = GridBagConstraints.WEST;
			gbc_warningMovil.insets = new Insets(0, 0, 5, 0);
			gbc_warningMovil.gridx = 5;
			gbc_warningMovil.gridy = 4;
			datosPersonales.add(warningEmailMovil, gbc_warningMovil);
			
			lblUsuario = new JLabel("Usuario : ");
			GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
			gbc_lblUsuario.anchor = GridBagConstraints.EAST;
			gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsuario.gridx = 1;
			gbc_lblUsuario.gridy = 5;
			datosPersonales.add(lblUsuario, gbc_lblUsuario);
			
			txtUsuario = new JTextField();
			GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
			gbc_txtUsuario.gridwidth = 2;
			gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
			gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUsuario.gridx = 2;
			gbc_txtUsuario.gridy = 5;
			datosPersonales.add(txtUsuario, gbc_txtUsuario);
			txtUsuario.setColumns(10);
			
			warningUsuario = new JLabel("*");
			warningUsuario.setForeground(Color.RED);
			GridBagConstraints gbc_warningUsurio = new GridBagConstraints();
			gbc_warningUsurio.anchor = GridBagConstraints.WEST;
			gbc_warningUsurio.insets = new Insets(0, 0, 5, 0);
			gbc_warningUsurio.gridx = 5;
			gbc_warningUsurio.gridy = 5;
			datosPersonales.add(warningUsuario, gbc_warningUsurio);
			
			lblClave = new JLabel("Clave : ");
			GridBagConstraints gbc_lblClave = new GridBagConstraints();
			gbc_lblClave.anchor = GridBagConstraints.EAST;
			gbc_lblClave.insets = new Insets(0, 0, 5, 5);
			gbc_lblClave.gridx = 1;
			gbc_lblClave.gridy = 6;
			datosPersonales.add(lblClave, gbc_lblClave);
			
			txtClave = new JPasswordField();
			txtClave.setColumns(10);
			GridBagConstraints gbc_txtClave = new GridBagConstraints();
			gbc_txtClave.insets = new Insets(0, 0, 5, 5);
			gbc_txtClave.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtClave.gridx = 2;
			gbc_txtClave.gridy = 6;
			datosPersonales.add(txtClave, gbc_txtClave);
			
			lblRepite = new JLabel("Repite : ");
			GridBagConstraints gbc_lblRepite = new GridBagConstraints();
			gbc_lblRepite.anchor = GridBagConstraints.EAST;
			gbc_lblRepite.insets = new Insets(0, 0, 5, 5);
			gbc_lblRepite.gridx = 3;
			gbc_lblRepite.gridy = 6;
			datosPersonales.add(lblRepite, gbc_lblRepite);
			
			txtClave2 = new JPasswordField();
			GridBagConstraints gbc_txtClave2 = new GridBagConstraints();
			gbc_txtClave2.insets = new Insets(0, 0, 5, 5);
			gbc_txtClave2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtClave2.gridx = 4;
			gbc_txtClave2.gridy = 6;
			datosPersonales.add(txtClave2, gbc_txtClave2);
			
			warningClave = new JLabel("*");
			warningClave.setForeground(Color.RED);
			GridBagConstraints gbc_warningClave = new GridBagConstraints();
			gbc_warningClave.anchor = GridBagConstraints.WEST;
			gbc_warningClave.insets = new Insets(0, 0, 5, 0);
			gbc_warningClave.gridx = 5;
			gbc_warningClave.gridy = 6;
			datosPersonales.add(warningClave, gbc_warningClave);
			
			btnRegistrar= new JButton("Registrar");
			GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
			gbc_btnRegistrar.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnRegistrar.insets = new Insets(10, 0, 5, 5);
			gbc_btnRegistrar.gridx = 2;
			gbc_btnRegistrar.gridy = 7;
			datosPersonales.add(btnRegistrar, gbc_btnRegistrar);
			/*btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkFields()) {
							boolean registrado=false;
							registrado = ControladorAsistentes.getUnicaInstancia().registrarAsistente(
											txtNombre.getText(),
											Integer.parseInt(txtEdad.getText()),
											txtMovil.getText(),
											txtEmail.getText(),
											txtUsuario.getText(),
											new String(txtClave.getPassword()));
							if (registrado) {
								JOptionPane.showMessageDialog(
											ventana,
											"Asistente registrado correctamente.",
											"Registro",
											JOptionPane.INFORMATION_MESSAGE);
								ventana.setContentPane(jpanelAnterior);
								ventana.revalidate();
							} else JOptionPane.showMessageDialog(ventana,
									"No se ha podido llevar a cabo el registro.\n",
									"Registro",
									JOptionPane.ERROR_MESSAGE);
							ventana.setTitle("Login Gestor Eventos");	
					}
				} 
			}); */
			
			btnCancelar= new JButton("Cancelar");
			GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
			gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnCancelar.insets = new Insets(10, 0, 5, 5);
			gbc_btnCancelar.gridx = 4;
			gbc_btnCancelar.gridy = 7;
			datosPersonales.add(btnCancelar, gbc_btnCancelar);
			/*btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ventana.setContentPane(jpanelAnterior);
					ventana.setTitle("Login Gestor Eventos");	
					ventana.revalidate();
				}
			});*/
			
			warningAll = new JLabel("* Las campos indicados son obligatorios");
			warningAll.setForeground(Color.RED);
			GridBagConstraints gbc_warningAll = new GridBagConstraints();
			gbc_warningAll.gridwidth = 3;
			gbc_warningAll.anchor = GridBagConstraints.WEST;
			gbc_warningAll.insets = new Insets(5, 0, 5, 5);
			gbc_warningAll.gridx = 2;
			gbc_warningAll.gridy = 8;
			datosPersonales.add(warningAll, gbc_warningAll);
			
			warningEdadNumerica = new JLabel("* La edad debe ser un número");
			warningEdadNumerica.setForeground(Color.RED);
			GridBagConstraints gbc_warningEdadNumerica = new GridBagConstraints();
			gbc_warningEdadNumerica.gridwidth = 3;
			gbc_warningEdadNumerica.anchor = GridBagConstraints.WEST;
			gbc_warningEdadNumerica.insets = new Insets(0, 0, 5, 5);
			gbc_warningEdadNumerica.gridx = 2;
			gbc_warningEdadNumerica.gridy = 9;
			datosPersonales.add(warningEdadNumerica, gbc_warningEdadNumerica);
			
			warningClave2 = new JLabel("* Las dos claves deben coincidir");
			warningClave2.setForeground(Color.RED);
			GridBagConstraints gbc_warningClave2 = new GridBagConstraints();
			gbc_warningClave2.gridwidth = 3;
			gbc_warningClave2.anchor = GridBagConstraints.WEST;
			gbc_warningClave2.insets = new Insets(0, 0, 5, 5);
			gbc_warningClave2.gridx = 2;
			gbc_warningClave2.gridy = 10;
			datosPersonales.add(warningClave2, gbc_warningClave2);
			
			warningExiste = new JLabel("* El usuario ya existe");
			warningExiste.setForeground(Color.RED);
			GridBagConstraints gbc_warningExiste = new GridBagConstraints();
			gbc_warningExiste.gridwidth = 3;
			gbc_warningExiste.anchor = GridBagConstraints.WEST;
			gbc_warningExiste.insets = new Insets(0, 0, 5, 5);
			gbc_warningExiste.gridx = 2;
			gbc_warningExiste.gridy = 11;
			datosPersonales.add(warningExiste, gbc_warningExiste);
			
			ocultarErrores();
			/*ventana.setContentPane(this);

			ventana.revalidate(); redibujar con el nuevo JPanel
			ventana.repaint();*/
					
		} /*constructor*/
		
		/**
		 * Comprueba que los campos de registro estan bien
		 */
		/*
		private boolean checkFields() {
			boolean ok=true;
//			borrar todos los errores en pantalla
			ocultarErrores();

			if (txtNombre.getText().trim().isEmpty()) {
				warningNombre.setVisible(true); 
				ok=false;
			}
			if (txtApellidos.getText().trim().isEmpty()) {
				warningApellidos.setVisible(true); 
				ok=false;
			}

			if (txtDNI.getText().trim().isEmpty() || txtEdad.getText().trim().isEmpty()) {
				warningDNIEdad.setVisible(true); 
				ok=false;
			}
			if (txtEmail.getText().trim().isEmpty() || txtMovil.getText().trim().isEmpty()) {
				warningEmailMovil.setVisible(true); 
				ok=false;
			}
			if (txtUsuario.getText().trim().isEmpty()) {
				warningUsuario.setVisible(true); 
				ok=false;
			}
			
			String password = new String(txtClave.getPassword());
			String password2 = new String(txtClave2.getPassword());
			
			if (password.equals("")) {
				warningClave.setVisible(true); 
				ok=false;
			} 
			if (!ok) warningAll.setVisible(true);
			
			if (ok && (!(txtEdad.getText().matches("[0-9]+")))) {
				warningEdadNumerica.setVisible(true);
				ok=false;
			}
			
			if (ok && !password.equals(password2)) {
				warningClave.setVisible(true);
				warningClave2.setVisible(true);
				ok=false;
			}
//			Comprobar que no exista otro usuario con igual login 
			if (ControladorAsistentes.getUnicaInstancia().esAsistenteRegistrado(txtUsuario.getText())) {
				warningExiste.setVisible(true); 
				ok=false;		
			}
			return ok;
		}
		*/
		/**
		 * Oculta todos los errores que pueda haber en la pantalla
		 */
		private void ocultarErrores() {
			warningAll.setVisible(false);
			warningApellidos.setVisible(false);
			warningClave.setVisible(false);
			warningClave2.setVisible(false);
			warningDNIEdad.setVisible(false);
			warningEdadNumerica.setVisible(false);
			warningExiste.setVisible(false);
			warningEmailMovil.setVisible(false);
			warningNombre.setVisible(false);
			warningUsuario.setVisible(false);
		}
	
}
