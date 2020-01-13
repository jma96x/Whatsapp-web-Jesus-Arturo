package dominio;

public class DescuentoFijo implements Descuento{
	@Override
	public double calcDescuento() {
		return 10 - 10.0 * 0.1;
	}

}
