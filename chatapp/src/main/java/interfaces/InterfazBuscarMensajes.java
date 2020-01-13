package interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorChat;
import dominio.Mensaje;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class InterfazBuscarMensajes {

	private JPanel buscar;
	DefaultListModel<Mensaje> listModel = new DefaultListModel<Mensaje>();
	JList<Mensaje> listMensajes = new JList<Mensaje>(listModel);
	MainView mainView;

	/**
	 * Create the application.
	 */
	public InterfazBuscarMensajes(MainView mainView) {
		this.mainView = mainView;
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

		JLabel lblFecha1 = new JLabel("Fecha1");
		lblFecha1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha1.setBounds(304, 190, 97, 23);
		buscar.add(lblFecha1);
		
		JLabel lblFecha2 = new JLabel("Fecha2");
		lblFecha2.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha2.setBounds(450, 190, 97, 23);
		buscar.add(lblFecha2);

		JTextField inputNombre = new JTextField();
		inputNombre.setBounds(101, 215, 138, 23);
		buscar.add(inputNombre);
		inputNombre.setColumns(10);

		JDateChooser inputFecha = new JDateChooser();
		inputFecha.setBounds(304, 215, 97, 29);
		buscar.add(inputFecha);
		
		JDateChooser inputFecha2 = new JDateChooser();
		inputFecha2.setBounds(450, 215, 97, 29);
		buscar.add(inputFecha2);

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
		contenedorMensajes.setMinimumSize(new Dimension(513, 240));
		contenedorMensajes.setSize(new Dimension(513, 240));
		contenedorMensajes.setPreferredSize(new Dimension(1000, 32767));
		contenedorMensajes.setBackground(Color.lightGray);
		contenedorMensajes.setBounds(55, 351, 513, 240);
		contenedorMensajes.add(listMensajes);
		JScrollPane scrollPane = new JScrollPane(contenedorMensajes);
		scrollPane.setBounds(55, 351, 513, 240);
		scrollPane.setPreferredSize(new Dimension(513, 240));
		mensajes.add(scrollPane);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(253, 284, 89, 23);
		buscar.add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ControladorChat.getUnicaInstancia().getContactoActual() == null) {
					showChooseContact();
				}
				listModel.clear();
				String mensaje = inputMensaje.getText();
				String nombreUsuario = inputNombre.getText();
				Date fecha1 = inputFecha.getDate();
				Date fecha2 = inputFecha2.getDate();
				//1º Grupos : -El campo nombreUsuario se refiere al participante de ese grupo
				// 			  - Si no pone participante hay que buscar el texto y la fecha para todos los participantes 
				//			  - Si no pone la fecha hay que buscar el texto y el usuario
				//			  - Si no pone el texto todos los mensajes del participante en esa fecha
				//			  - Si no pone ni el participante ni la fecha, todos los mensajes del grupo que cumplan el texto.
				//		      - etc...
				// 2º ContactosIndividuales: + de lo mismo en estos siempre el campo nombreUsuario es vacio asique solo hay que mirar si fecha o texto son vacios.
				List<Mensaje> mensajesEncontrados = ControladorChat.getUnicaInstancia().getMensajesEncontrados(mensaje,nombreUsuario,fecha1, fecha2);
				for (Mensaje m : mensajesEncontrados) {
					listModel.addElement(m);
				}
			}
		});
	}
	private void showChooseContact() {
		JOptionPane.showMessageDialog(mainView, "Elige un contacto primero.", "Elegir Contacto", JOptionPane.ERROR_MESSAGE);
	}
	public JPanel getBuscar() {
		return buscar;
	}
}
