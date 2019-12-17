
public class LineasPedido {

	private Producto producto;
	private int cantidadProducto;

	public LineasPedido(Producto producto, int cantidadProducto) {

		this.producto = producto;
		this.cantidadProducto = cantidadProducto;
	}

	public LineasPedido() {

	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	@Override
	public String toString() {
		return "LineasPedido [producto=" + producto + ", cantidadProducto=" + cantidadProducto;
	}

}
