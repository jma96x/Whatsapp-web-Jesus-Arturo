package interfaces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Usuario;

public class GeneradorContactoPDF {
private static GeneradorContactoPDF unicaInstancia;
	
	private GeneradorContactoPDF() {}
	
	public static GeneradorContactoPDF getUnicaInstancia() {
		if(unicaInstancia == null)
			return unicaInstancia = new GeneradorContactoPDF();
		return unicaInstancia;
	}
	
	public void generarPDF(Usuario u) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document();
		int contadorContactosInd = 1;
		int contadorGrupos = 1;
		PdfWriter.getInstance(document, new FileOutputStream("ContactosUsuario-" + u.getTelefono() + ".pdf"));
		document.open();
		Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
		Paragraph parrafo = new Paragraph("-----------------------CONTACTOS DEL USUARIO : " + u.getLogin() + 
				"-----------------------" + "\n", font);
		document.add(parrafo);
	    parrafo = new Paragraph("CONTACTOS INDIVIDUALES\n");
		document.add(parrafo);
		for(ContactoIndividual c : u.getContactosIndividuales()) {
			parrafo = new Paragraph(contadorContactosInd + ") Nombre: " + c.getNombre() + " Nº Telefono: " + c.getTelefonoUsuario()+ "\n");
			document.add(parrafo);
			parrafo = new Paragraph("-----------------------------------------------------------------------\n");
			document.add(parrafo);
			contadorContactosInd++;
		}
		parrafo = new Paragraph("GRUPOS\n");
		document.add(parrafo);
		for(Grupo g : u.getGrupos()) {
			parrafo = new Paragraph(contadorGrupos + ") Nombre : " + g.getNombre()+ "\n");
			document.add(parrafo);
				parrafo = new Paragraph("Contactos del Grupo: \n");
				document.add(parrafo);
				for(ContactoIndividual ci : g.getParticipantes()) {
					parrafo = new Paragraph("Nombre: " + ci.getNombre()+ " Nº Telefono: " + ci.getTelefonoUsuario() + "\n");
					document.add(parrafo);
				}
				parrafo = new Paragraph("-----------------------------------------------------------------------");
			document.add(parrafo);
			contadorGrupos++;
		}
		document.close();
		
	}
}
