package componenteMensajes;

import java.util.EventListener;
import java.util.EventObject;

public interface IMensajesListener extends EventListener {
	public void nuevosMensajes (MensajesEvent e);
}
