
public class Producto {

	private String nombreProducto;
	private String codigoProducto;

	public Producto(String codigoProductoInicial, String nombreProductoInicial) {

		this.nombreProducto = nombreProductoInicial;
		this.codigoProducto = codigoProductoInicial;
	}

	public Producto(String codigoProductoInicial) {

		this.codigoProducto = codigoProductoInicial;
	}

	public Producto() {

	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	
	@Override
	public String toString() {
		return "Producto [nombreProducto=" + nombreProducto + ", codigoProducto=" + codigoProducto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoProducto == null) ? 0 : codigoProducto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (codigoProducto == null) {
			if (other.codigoProducto != null)
				return false;
		} else if (!codigoProducto.equals(other.codigoProducto))
			return false;
		return true;
	}

}
