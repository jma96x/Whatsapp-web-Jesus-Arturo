package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import java.awt.Cursor;

public class InterfazGrupo extends JFrame {

	private JTextField textField;
	private String nombreGrupoModificar;
	private int x ;
	private int y;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrupo window = new InterfazGrupo();
					window. setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public InterfazGrupo(int x, int y) {
		this.x = x;
		this.y = y;
		initialize();
	}
	//Este constructor esta para cuando la acción sea la de modificar el grupo
	public InterfazGrupo(int x, int y,String nombre) {
		this.x = x;
		this.y = y;
		this.nombreGrupoModificar = nombre;
		initialize();
	}
	/**
	 * Initialize the contents of the  
	 */
	private void initialize() {
		
		 setTitle("Ventana Grupo");
		 setBounds(x, y, 700, 600);
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//PANEL ARRIBA
		JPanel panelArriba = new JPanel();
		panelArriba.setPreferredSize(new Dimension(700, 80));
		 getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);
		
		JButton btnFotoUsuario = new JButton("New button");
		btnFotoUsuario.setRequestFocusEnabled(false);
		btnFotoUsuario.setBounds(10, 8, 64, 64);
		panelArriba.add(btnFotoUsuario);
		
		JLabel lblNombreusuario = new JLabel("NombreUsuario");
		lblNombreusuario.setBounds(84, 28, 122, 24);
		panelArriba.add(lblNombreusuario);
		
		JButton btnBuscar = new JButton("New button");
		btnBuscar.setBounds(547, 32, 40, 40);
		panelArriba.add(btnBuscar);
		
		JButton btnEliminar = new JButton("New button");
		btnEliminar.setBounds(619, 32, 40, 40);
		panelArriba.add(btnEliminar);
		
		//PANEL ABAJO
		JPanel panelAbajo = new JPanel();
		panelAbajo.setPreferredSize(new Dimension(700, 50));
		 getContentPane().add(panelAbajo, BorderLayout.SOUTH);
		panelAbajo.setLayout(null);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptar.setBounds(220, 16, 98, 23);
		panelAbajo.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.RED);
		btnCancelar.setBounds(345, 16, 98, 23);
		panelAbajo.add(btnCancelar);
		
		//PANEL IZQUIERDA
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
		contenedorContactos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactos.setPreferredSize(new Dimension(181, 327));
		JScrollPane scrollContactos = new JScrollPane(contenedorContactos);
		
		JList listaContactos = new JList();
		listaContactos.setBackground(new Color(244, 164, 96));
		listaContactos.setPreferredSize(new Dimension(181, 300));
		contenedorContactos.add(listaContactos);
		scrollContactos.setPreferredSize(new Dimension(181, 327));
		scrollContactos.setBounds(new Rectangle(22, 64, 181, 327));
		panelContactos.add(scrollContactos);
		
		//PANEL DERECHA
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
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		contenedorContactosAñadidos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactosAñadidos.setPreferredSize(new Dimension(181, 327));
		
		JScrollPane scrollContactosAñadidos = new JScrollPane(contenedorContactosAñadidos);
		
		JList listaContactosAñadidos = new JList();
		listaContactosAñadidos.setBackground(new Color(0, 255, 0));
		listaContactosAñadidos.setPreferredSize(new Dimension(181, 305));
		listaContactosAñadidos.setBounds(new Rectangle(22, 64, 181, 327));
		contenedorContactosAñadidos.add(listaContactosAñadidos);
		scrollContactosAñadidos.setPreferredSize(new Dimension(181, 327));
		contactosAñadidos.add(scrollContactosAñadidos);
		
		//PANEL CENTRO
		JPanel panelCentro = new JPanel();
		panelCentro.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		panelCentro.setToolTipText("");
		panelCentro.setPreferredSize(new Dimension(240, 470));
		 getContentPane().add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Nombre grupo");
		textField.setBounds(25, 80, 166, 31);
		panelCentro.add(textField);
		textField.setColumns(10);
		
		JLabel lblNombreDelGrupo = new JLabel("Nombre del grupo");
		lblNombreDelGrupo.setBounds(22, 39, 150, 18);
		panelCentro.add(lblNombreDelGrupo);
		
		JButton btnNewButton = new JButton("-->");
		btnNewButton.setBounds(43, 178, 121, 39);
		panelCentro.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("<--");
		btnNewButton_1.setBounds(43, 264, 121, 39);
		panelCentro.add(btnNewButton_1);
	}
	public String setNombreGrupoModificar(String nombre) {
		return this.nombreGrupoModificar = nombre;
	}
}
