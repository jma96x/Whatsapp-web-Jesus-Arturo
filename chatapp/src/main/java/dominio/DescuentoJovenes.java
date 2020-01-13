package dominio;

public class DescuentoJovenes implements Descuento {
	
	@Override
	public double calcDescuento() {
		return 10 - 10 * 0.3;
	}

}
