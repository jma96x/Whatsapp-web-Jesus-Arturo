package componenteMensajes;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;


public class CargadorMensajes {
	private List<MensajeWhatsApp> mensajes = null;
	private Vector<IMensajesListener> listeners = new Vector<IMensajesListener>(); 
	
	// public static void main(String[] args) {
	public void cargarMensajesIOS(String fichero) {
				String formatDateWhatsApp = "d/M/yy H:mm:ss";
				try {
					mensajes = SimpleTextParser.parse(fichero, formatDateWhatsApp, Plataforma.IOS);
					MensajesEvent event=new MensajesEvent(mensajes);
					notificarNuevosMensajes(event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

	public void cargarMensajesAndroid1(String fichero) {
		String formatDateWhatsApp = "d/M/yy H:mm";
		try {
			mensajes = SimpleTextParser.parse(fichero, formatDateWhatsApp, Plataforma.ANDROID);
			MensajesEvent event=new MensajesEvent(mensajes);
			notificarNuevosMensajes(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarMensajesAndroid2(String fichero) {
		String formatDateWhatsApp = "d/M/yyyy H:mm";
		try {
			mensajes = SimpleTextParser.parse(fichero, formatDateWhatsApp, Plataforma.ANDROID);
			MensajesEvent event=new MensajesEvent(mensajes);
			notificarNuevosMensajes(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public synchronized void addMensajesListener(IMensajesListener listener){
		listeners.addElement(listener);
	}
	public synchronized void removeMensajesListener(IMensajesListener listener){
		listeners.removeElement(listener);
	}
	private void notificarNuevosMensajes(MensajesEvent evento){
		for(int i=0; i<listeners.size(); i++){
			IMensajesListener listener=(IMensajesListener)listeners.elementAt(i);
			listener.nuevosMensajes(evento);
		}
	}
}
