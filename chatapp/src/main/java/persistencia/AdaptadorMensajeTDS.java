package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import dominio.Contacto;
import dominio.Mensaje;
import dominio.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS unicaInstancia;

	public static AdaptadorMensajeTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorMensajeTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje;
		boolean existe = true; 
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// crear entidad Cliente
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		String emoticono = String.valueOf(mensaje.getEmoticono());
		String hora = mensaje.getHora();
		String codigoEmisor = String.valueOf(mensaje.getEmisor().getCodigo());
		String codigoReceptor = String.valueOf(mensaje.getDestino().getCodigo());

		eMensaje.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("texto", mensaje.getTexto()),new Propiedad("emoticono", emoticono),
						new Propiedad("hora",hora),new Propiedad("emisor",codigoEmisor),new Propiedad("receptor",codigoReceptor))));
		// registrar entidad cliente
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.getId());
		
	}

	public void borrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
		
	}

	public Mensaje recuperarMensaje(int codigo) {
		String texto; 
		String hora;
		int emoticono; 
		Usuario emisor;
		Contacto receptor;
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		hora = servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora");
		emoticono = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		emisor = obtenerUsuarioDesdeCodigo(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor")));
		receptor = obtenerContactoDesdeCodigo(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor")));

		Mensaje mensaje = new Mensaje(texto,emoticono,emisor,receptor,hora);
		mensaje.setCodigo(codigo);
		return mensaje;
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Entidad> eMensajes = servPersistencia.recuperarEntidades("mensaje");
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}
	// -------------------Funciones auxiliares-----------------------------
	private Usuario obtenerUsuarioDesdeCodigo(int codigo) {
		AdaptadorUsuarioTDS adaptadorU = AdaptadorUsuarioTDS.getUnicaInstancia();
		Usuario usuario = adaptadorU.recuperarUsuario(codigo);
		return usuario;
	}
	private Contacto obtenerContactoDesdeCodigo(int codigo) {
		AdaptadorContactoTDS adaptadorC = AdaptadorContactoTDS.getUnicaInstancia();
		Contacto contacto = adaptadorC.recuperarContacto(codigo);
		return contacto;
	}
}
