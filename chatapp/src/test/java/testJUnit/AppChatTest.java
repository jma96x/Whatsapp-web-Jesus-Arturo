package testJUnit;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import dominio.Usuario;

public class AppChatTest {
	SimpleDateFormat parser = new SimpleDateFormat("dd-mm-yyyy"); 
	Usuario u1;
	Usuario u2;
	Usuario u3;
	ContactoIndividual c1;
	ContactoIndividual c2;
	@Before 
	public void setup () {
	    Date dt1 = null;
	    Date dt2 = null;
	    Date dt3 = null;
		try {
			dt1 = parser.parse("30-11-1998");
			dt2 = parser.parse("28-11-1997");
			dt3 = parser.parse("10-04-1996");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    u1 = new Usuario("Arturo", dt1, "601012173", "arturo.lorenzoh@um.es" , "arturo", "1212", "/img/defecto.jpg");
	    u2 = new Usuario("Jesus", dt2, "684098765", "jesus.mari@um.es" , "jesus", "1212", "/img/defecto.jpg");
	    u3 = new Usuario("Pepe", dt3, "684098765", "jesus.mari@um.es" , "jesus", "1212", "/img/defecto.jpg");
	    c1 = new ContactoIndividual("jesus", u2);
	    c2 = new ContactoIndividual("pepe", u3);
	}
	@Test
	public void crearUsuario(){
		assertEquals("arturo", u1.getLogin());
		assertEquals("1212", u1.getContraseña());
		assertTrue(!u1.isPremium());
	}
	@Test
	public void añadirContacto () {
		u1.addContacto(c1);
		assertEquals(1, u1.getContactos().size());
	}
	@Test
	public void crearGrupo() {
		List<ContactoIndividual> participantes = new LinkedList<ContactoIndividual>();
		assertEquals(u2, c1.getUsuario());
		participantes.add(c1);
		participantes.add(c2);
		Grupo g1 = new Grupo("AppChat", "/img/defecto.jpg", participantes, u1);
		assertTrue(g1.getParticipantes().size() > 0);
		assertEquals(2, g1.getParticipantes().size());
	}
	@Test 
	public void mandarMensaje() {
		Mensaje m = new Mensaje ("hola jesus", -1, u1, (Contacto) c1);
		c1.addMensaje(m);
		assertEquals(1, c1.getMensajesCount(u1));
	}

}
