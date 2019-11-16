package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import dominio.Contacto;
import dominio.Usuario;

public class AdaptadorContactoTDS implements IAdaptadorContactoDAO {
	// Usa un pool para evitar problemas doble referencia con cliente

	private static ServicioPersistencia servPersistencia;

	private SimpleDateFormat dateFormat; // para formatear la fecha de venta en
											// la base de datos

	private static AdaptadorContactoTDS unicaInstancia;

	public static AdaptadorContactoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorContactoTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorContactoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	/* cuando se registra una venta se le asigna un identificador unico */
	public void registrarContacto(Contacto contacto) {
		Entidad eContacto;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		// registrar primero los atributos que son objetos
		// registrar lineas de venta
		/*AdaptadorLineaVentaTDS adaptadorLV = AdaptadorLineaVentaTDS.getUnicaInstancia();
		for (LineaVenta ldv : venta.getLineasVenta())
			adaptadorLV.registrarLineaVenta(ldv);
		// registrar cliente
		AdaptadorClienteTDS adaptadorCliente = AdaptadorClienteTDS.getUnicaInstancia();
		adaptadorCliente.registrarCliente(venta.getCliente());*/

		// Crear entidad venta
		eContacto = new Entidad();

		eContacto.setNombre("venta");
		eContacto.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("cliente", String.valueOf(contacto.getCliente().getCodigo())),
						new Propiedad("fecha", dateFormat.format(contacto.getFecha())),
						new Propiedad("lineasventa", obtenerCodigosLineaVenta(contacto.getLineasVenta())))));
		// registrar entidad venta
		eContacto = servPersistencia.registrarEntidad(eContacto);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		contacto.setCodigo(eContacto.getId()); 	
	}

	public void borrarContacto(Contacto contacto) {
		// No se comprueban restricciones de integridad con Cliente
		Entidad eContacto;
		AdaptadorLineaVentaTDS adaptadorLV = AdaptadorLineaVentaTDS.getUnicaInstancia();

		for (LineaVenta lineaVenta : contacto.getLineasVenta()) {
			adaptadorLV.borrarLineaVenta(lineaVenta);
		}
		eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		servPersistencia.borrarEntidad(eContacto);

	}

	public void modificarContacto(Contacto contacto) {
		Entidad eContacto;

	}

	public Contacto recuperarContacto(int codigo) {
		// Si la entidad estï¿½ en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Contacto) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		// recuperar entidad
		Entidad eContacto = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		// fecha
		/*Date fecha = null;
		try {
			fecha = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eVenta, "fecha"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Venta venta = new Venta(fecha);
		venta.setCodigo(codigo);*/

		// IMPORTANTE:aï¿½adir la venta al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, venta);

		// recuperar propiedades que son objetos llamando a adaptadores
		// cliente
		AdaptadorUsuarioTDS adaptadorCliente = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoCliente = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContacto, "cliente"));
		
		Usuario usuario  = adaptadorCliente.recuperarUsuario(codigoCliente);
		codigo.setCliente(usuario);
		// lineas de venta
		List<LineaVenta> lineasVenta = obtenerLineasVentaDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eVenta, "lineasventa"));

		for (LineaVenta lv : lineasVenta)
			venta.addLineaVenta(lv);

		// devolver el objeto venta
		return venta;
	}

	public List<Contacto> recuperarTodosContactos() {
		List<Contacto> contactos = new LinkedList<Contacto>();
		List<Entidad> eContactos = servPersistencia.recuperarEntidades("contacto");

		for (Entidad eVenta : eContactos) {
			contactos.add(recuperarContacto(eVenta.getId()));
		}
		return contactos;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String obtenerCodigosLineaVenta(List<LineaVenta> lineasVenta) {
		String lineas = "";
		for (LineaVenta lineaVenta : lineasVenta) {
			lineas += lineaVenta.getCodigo() + " ";
		}
		return lineas.trim();

	}

	private List<LineaVenta> obtenerLineasVentaDesdeCodigos(String lineas) {

		List<LineaVenta> lineasVenta = new LinkedList<LineaVenta>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorLineaVentaTDS adaptadorLV = AdaptadorLineaVentaTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			lineasVenta.add(adaptadorLV.recuperarLineaVenta(Integer.valueOf((String) strTok.nextElement())));
		}
		return lineasVenta;
	}

}
