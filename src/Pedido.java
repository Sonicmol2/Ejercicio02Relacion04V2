import java.util.ArrayList;

/**
 * Cada pedido contienen
 * ◦Numero de pedido ( de 1 en adelante)
 * ◦Cliente del pedido
 * ◦ArrayList de líneas de pedido
 * @author DAM-2
 *
 */
public class Pedido {
	
	private int numeroPedido;
	private Cliente clientePedido;
	private ArrayList<LineasPedido> listaLineas;
	
	public Pedido(int numeroPedido, Cliente clientePedido) {
		this.numeroPedido = numeroPedido;
		this.clientePedido = clientePedido;
		listaLineas = new ArrayList<LineasPedido>();
	}
	
	public Pedido(Cliente clientePedido) {
		this.numeroPedido = numeroPedido++;
		this.clientePedido = clientePedido;
		listaLineas = new ArrayList<LineasPedido>();
	}
	
	public Pedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public Pedido() {
		
	}

	public int getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(int numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Cliente getClientePedido() {
		return clientePedido;
	}

	public void setClientePedido(Cliente clientePedido) {
		this.clientePedido = clientePedido;
	}

	public ArrayList<LineasPedido> getListaLineas() {
		return listaLineas;
	}

	public void setListaLineas(ArrayList<LineasPedido> listaLineas) {
		this.listaLineas = listaLineas;
	}

	@Override
	public String toString() {
		return "Pedido [numeroPedido=" + numeroPedido + ", clientePedido=" + clientePedido + ", listaLineas="
				+ listaLineas + "]";
	}
	
	public void annadirPedido(LineasPedido lineaPedidoIntroducir) {
		
		listaLineas.add(lineaPedidoIntroducir);
	}
	
}
