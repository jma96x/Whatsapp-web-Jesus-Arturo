package interfaces;

import java.awt.Color;
import java.util.HashMap;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.LegendPosition;

import controlador.ControladorChat;
import dominio.Grupo;

public class InterfazEstadisticas {
	public static void main(String[] args) {
		InterfazEstadisticas exampleChart = new InterfazEstadisticas();
		CategoryChart chart = exampleChart.getHistogramMensajes();
		new SwingWrapper<CategoryChart>(chart).displayChart();
		PieChart pieChart = exampleChart.getGraficoTarta();
		new SwingWrapper<PieChart>(pieChart).displayChart();
	}

	public CategoryChart getHistogramMensajes() {
		// Create Chart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Número de mensajes por mes")
				.xAxisTitle("Meses").yAxisTitle("Número Mensajes").build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		int meses[] = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		// conseguir el número de mensajes del usuario actual por mes DEL AÑO ACTUAL, si
		// no tiene en un mes poner 0.
		int numeroMensajes[] = ControladorChat.getUnicaInstancia().getNumeroMensajesPorMeses();
		// Series
		chart.addSeries("Nmensajes", meses, numeroMensajes);

		return chart;
	}

	public PieChart getGraficoTarta() {

		// Create Chart
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Grupos Más Pesados").build();

		// Customize Chart
		Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62) , new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182),
				new Color(250,80,200)};
		chart.getStyler().setSeriesColors(sliceColors);
		chart.getStyler().setLegendVisible(true);
		// Un mapa indexado por los 6 grupos con mas mensajes, y como valor el
		// porcentaje de mensajes que han sido del usuarioActual.
		HashMap<Grupo, Double> gruposMasPesados = new HashMap<Grupo, Double>();
		gruposMasPesados = ControladorChat.getUnicaInstancia().getGruposMasPesados();
		// Series
		if (gruposMasPesados != null) {
			for (Grupo g : gruposMasPesados.keySet()) {
				double porcentajeMensajesUsuario = gruposMasPesados.get(g);
				chart.addSeries(g.getNombre()+" "+ String.valueOf(porcentajeMensajesUsuario)+"%", porcentajeMensajesUsuario);
			}
		}
		return chart;
	}
}
