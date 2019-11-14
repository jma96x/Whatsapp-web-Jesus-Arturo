package interfaces;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

public class InterfazBuscarMensajes {

	private JPanel buscar;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazBuscarMensajes window = new InterfazBuscarMensajes();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public InterfazBuscarMensajes() {
		buscar = new JPanel();
		buscar.setPreferredSize(new Dimension(635, 660));
		buscar.setLayout(null);

		JLabel lblBusquedaDeMensajes = new JLabel("BUSQUEDA DE MENSAJES");
		lblBusquedaDeMensajes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusquedaDeMensajes.setBounds(120, 6, 365, 51);
		buscar.add(lblBusquedaDeMensajes);

		JTextField inputMensaje = new JTextField();
		inputMensaje.setBounds(208, 93, 193, 51);
		buscar.add(inputMensaje);
		inputMensaje.setColumns(10);

		JLabel lblMensaje = new JLabel("Mensaje");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(204, 59, 199, 33);
		buscar.add(lblMensaje);

		JLabel lblNombreUsuario = new JLabel("Nombre Usuario");
		lblNombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreUsuario.setBounds(112, 185, 107, 33);
		buscar.add(lblNombreUsuario);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setBounds(406, 190, 97, 23);
		buscar.add(lblFecha);

		JTextField inputNombre = new JTextField();
		inputNombre.setBounds(101, 215, 138, 23);
		buscar.add(inputNombre);
		inputNombre.setColumns(10);

		JDateChooser inputFecha = new JDateChooser();
		inputFecha.setBounds(406, 215, 97, 29);
		buscar.add(inputFecha);

		JLabel lblMensajesEncontrados = new JLabel("MENSAJES ENCONTRADOS");
		lblMensajesEncontrados.setBounds(60, 318, 341, 23);
		buscar.add(lblMensajesEncontrados);

		JPanel mensajes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) mensajes.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		mensajes.setPreferredSize(new Dimension(513, 240));
		mensajes.setBounds(55, 351, 513, 240);
		buscar.add(mensajes);

		JPanel contenedorMensajes = new JPanel();
		contenedorMensajes.setPreferredSize(new Dimension(513, 240));
		contenedorMensajes.setBounds(55, 351, 513, 240);
		contenedorMensajes.setPreferredSize(new Dimension(513, 240));

		JScrollPane scrollPane = new JScrollPane(contenedorMensajes);
		scrollPane.setPreferredSize(new Dimension(513, 240));

		mensajes.add(scrollPane);
	}
	public JPanel getBuscar() {
		return buscar;
	}

}
