package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controlador.ControladorChat;
import dominio.Mensaje;

@SuppressWarnings("serial")
public class InterfazBorrarMensaje extends JFrame {
	private int x;
	private int y;
	private MainView mainView;
	DefaultListModel<Mensaje> listModel = new DefaultListModel<Mensaje>();

	/**
	 * Create the application.
	 */
	public InterfazBorrarMensaje(int x, int y, MainView mainWindow) {
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
		setTitle("Borrar Mensaje");

		JPanel panelArriba = new JPanel();
		panelArriba.setPreferredSize(new Dimension(600, 50));
		getContentPane().add(panelArriba, BorderLayout.NORTH);
		panelArriba.setLayout(null);

		JLabel lblMensajesDelUsuario = new JLabel("MENSAJES DEL USUARIO");
		lblMensajesDelUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajesDelUsuario.setBounds(164, 11, 242, 28);
		panelArriba.add(lblMensajesDelUsuario);

		JPanel panelMensajes = new JPanel();
		panelMensajes.setBounds(new Rectangle(0, 0, 400, 450));
		panelMensajes.setMinimumSize(new Dimension(400, 550));
		panelMensajes.setSize(new Dimension(400, 450));
		panelMensajes.setPreferredSize(new Dimension(400, 450));
		panelMensajes.setMaximumSize(new Dimension(400, 550));
		getContentPane().add(panelMensajes, BorderLayout.CENTER);

		JPanel contenedorMensajes = new JPanel();
		FlowLayout flowLayout = (FlowLayout) contenedorMensajes.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contenedorMensajes.setBackground(Color.WHITE);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		contenedorMensajes.setPreferredSize(new Dimension(32576, 32576));

		JScrollPane scrollMensajes = new JScrollPane(contenedorMensajes);
		scrollMensajes.setBounds(42, 0, 500, 400);

		final JList<Mensaje> listMensajes = new JList<Mensaje>(listModel);
		panelMensajes.setLayout(null);
		contenedorMensajes.add(listMensajes);
		scrollMensajes.setPreferredSize(new Dimension(500, 400));
		panelMensajes.add(scrollMensajes);

		List<Mensaje> mensajes = ControladorChat.getUnicaInstancia().getMisMensajesConversacionContactoActual();
		for (Mensaje m : mensajes) {
			listModel.addElement(m);
		}

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.GREEN);
		btnAceptar.setBounds(135, 466, 89, 23);
		panelMensajes.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mensaje mensaje = (Mensaje) listMensajes.getSelectedValue();
				if (mensaje == null)
					return;
				ControladorChat.getUnicaInstancia().eliminarMensaje(mensaje, false);
				mainView.actualizarContacto();
				mainView.actualizarListaContactos();
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
		panelMensajes.add(btnCancelar);


	}
}
