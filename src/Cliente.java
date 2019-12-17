import java.util.ArrayList;

public class Cliente {
	
	private String dni;
	private String nombre;
	private String Localidad;
	private ArrayList<Pedido> listaPedidos;
	
	public Cliente(String dni, String nombre , String localidad) {
		this.dni = dni;
		this.nombre = nombre;
		this.Localidad = localidad;
		listaPedidos = new ArrayList<Pedido>();
	}
	
	public Cliente(String dni) {
		this.dni = dni;
	}
	
	public Cliente() {
		
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(ArrayList<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	public String getLocalidad() {
		return Localidad;
	}

	public void setLocalidad(String localidad) {
		Localidad = localidad;
	}

	@Override
	public String toString() {
		return "Cliente [dni=" + dni + ", nombre=" + nombre + ", Localidad=" + Localidad + ", listaPedidos="
				+ listaPedidos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Cliente other = (Cliente) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	
	
}
