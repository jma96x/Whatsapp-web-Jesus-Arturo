package componenteMensajes;

import java.util.List;

public class MensajesEvent {
	protected List<MensajeWhatsApp> mensajes;
	
	public MensajesEvent(List<MensajeWhatsApp> mensajes) {
		this.mensajes = mensajes;
	}
	public List<MensajeWhatsApp> getMensajes() {
		return mensajes;
	}
}
