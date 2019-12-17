import java.util.Scanner;

import com.db4o.*;

import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

public class Principal {

	private static final String BD_PRODUCTOS = "bd/BaseDatosProductos.oo";
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opc;
		mostrarTodosAltas();
		do {
			opc = solicitarOpcionMenuPrincipal();
			tratarOpcionMenuPrincipal(opc);
		} while (opc != 4);
	}

	/**
	 * Método para mostrar todos los clientes, productos, pedidos, linea pedidos
	 */
	private static void mostrarTodosAltas() {

		ObjectContainer db;
		EmbeddedConfiguration configuracion = Db4oEmbedded.newConfiguration();
		configuracion.common().activationDepth(5);
		db = abrirBd(configuracion);

		ObjectSet<Pedido> resultadoClientes = db.queryByExample(new Cliente());
		ObjectSet<Pedido> resultadoPedidos = db.queryByExample(new Pedido());
		ObjectSet<Pedido> resultadoProductos = db.queryByExample(new Producto());
		ObjectSet<Pedido> resultadoLineas = db.queryByExample(new LineasPedido());

		System.out.println("Clientes: ");
		while (resultadoClientes.hasNext()) {
			System.out.println(resultadoClientes.next());
		}

		System.out.println("Pedidos: ");
		while (resultadoPedidos.hasNext()) {
			System.out.println(resultadoPedidos.next());
		}

		System.out.println("Productos: ");
		while (resultadoProductos.hasNext()) {
			System.out.println(resultadoProductos.next());
		}

		System.out.println("Lineas Pedidos: ");
		while (resultadoLineas.hasNext()) {
			System.out.println(resultadoLineas.next());
		}

		System.out.println();
		db.close();// Cerramos
	}

	private static int solicitarOpcionMenuPrincipal() {
		int opc;
		System.out.println("1.Altas");
		System.out.println("2.Consultas");
		System.out.println("3.Bajas");
		System.out.println("4.Salir");

		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 4);
		return opc;
	}

	private static void tratarOpcionMenuPrincipal(int opc) {

		int opcionAltas;
		int opcionConsultas;
		ObjectContainer db;

		switch (opc) {
		case 1:
			db = abrirBd(Db4oEmbedded.newConfiguration());
			opcionAltas = solicitarOpcionMenuAltas();
			tratarOpcionMenuAltas(opcionAltas, db);
			db.close();
			break;
		case 2:
			// Preguntar si abrir mejor dentro del metodo
			opcionConsultas = solicitarOpcionMenuConsultas();
			tratarOpcionMenuConsultas(opcionConsultas);

			break;
		}
	}

	/**
	 * Método para tratar y hacer diferentes consultas
	 * 
	 * @param opcionConsultas opcion elegida
	 */
	private static void tratarOpcionMenuConsultas(int opcionConsultas) {
		ObjectContainer db;
		EmbeddedConfiguration configuracion = Db4oEmbedded.newConfiguration();

		switch (opcionConsultas) {
		case 1:
			configuracion.common().activationDepth(5);
			db = abrirBd(configuracion);
			consultarTodosLosProductos(db);
			db.close();
			break;
		case 2:
			configuracion.common().activationDepth(5);
			db = abrirBd(configuracion);
			consultarClientesEmpiezenPorLetra(db);
			db.close();
			break;
		case 3:
			configuracion.common().activationDepth(5);
			db = abrirBd(configuracion);
			consultarPedidosDeUnCliente(db);
			db.close();
			break;
		case 4:
			configuracion.common().activationDepth(5);
			db = abrirBd(configuracion);
			consultarPedidoPorSuNumero(db);
			db.close();
			break;
		}

	}

	/**
	 * Método para consultar pedidos por su número de pedido
	 * 
	 * @param db
	 */
	private static void consultarPedidoPorSuNumero(ObjectContainer db) {

		Pedido pedidoPatron;
		int numeroDePedido;

		numeroDePedido = solicitarNumero("Introduce el número de pedido: ");

		pedidoPatron = new Pedido(numeroDePedido);

		ObjectSet<Pedido> resultadoPedido = db.queryByExample(pedidoPatron);

		if (resultadoPedido.size() == 0) {
			System.out.println("Error. Lo sentimos no hay pedidos con ese número.");
		} else {
			System.out.println(resultadoPedido.next());

		}
	}

	/**
	 * Método para consultar pedidos de un cliente por su DNI
	 * 
	 * @param db
	 */
	private static void consultarPedidosDeUnCliente(ObjectContainer db) {

		Cliente clientePatron;
		String dniCliente;
		StringBuilder cadenaSb = new StringBuilder();

		dniCliente = solicitarCadena("Introduce el dni del cliente: ");

		clientePatron = new Cliente(dniCliente);// Traemos cliente con ese dni

		ObjectSet<Cliente> resultadoCliente = db.queryByExample(clientePatron);

		if (resultadoCliente.size() == 0)
			System.out.println("BD Vacia");
		else {

			for (Cliente cliente : resultadoCliente) {
				cadenaSb.append(cliente);
				for (Pedido pedido : cliente.getListaPedidos()) {
					cadenaSb.append("Y su numero de pedido es: " + pedido.getNumeroPedido());
				}

			}
			
			System.out.println(cadenaSb.toString());

		}
	}

	/**
	 * Método para consultar clientes que empiecen por una letra específica
	 * 
	 * @param db
	 */
	private static void consultarClientesEmpiezenPorLetra(ObjectContainer db) {

		String letraEmpieza;

		letraEmpieza = solicitarCadena("Introduce una letra para buscar cliente: ");

		ObjectSet<Cliente> resultadoCliente = db.query(new Predicate<Cliente>() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean match(Cliente cliente) {

				return cliente.getNombre().startsWith(letraEmpieza);
			}

		});

		if (resultadoCliente.size() == 0) {
			System.out.println("BD Vacia");
		} else {
			for (Cliente cliente : resultadoCliente) {
				System.out.println("Los clientes que empiezan por la letra '" + letraEmpieza + "' son: " + cliente);
			}
		}

	}

	/**
	 * Método para consultar todos los productos
	 * 
	 * @param db base de datos
	 */
	private static void consultarTodosLosProductos(ObjectContainer db) {
		Producto productoPatron = new Producto(); // consultar todos los productos, sin filtro

		ObjectSet<Producto> result = db.queryByExample(productoPatron);

		if (result.size() == 0)
			System.out.println("BD Vacia");
		else {
			System.out.println("Numero de productos " + result.size());
			for (Producto producto : result) {

				System.out.println(producto);
			}

		}

	}

	/**
	 * Solicitamos la opción para el menú de altas
	 * 
	 * @return
	 */
	private static int solicitarOpcionMenuAltas() {
		int opc;
		System.out.println("1.Alta producto");
		System.out.println("2.Alta Cliente");
		System.out.println("3.Alta pedido");
		System.out.println("4.Volver");

		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 4);
		return opc;
	}

	/**
	 * Método para tratar las opciones y hacer altas de pedidos, productos o
	 * clientes
	 * 
	 * @param opc elegido para tratar
	 * @param db  base de datos
	 */
	private static void tratarOpcionMenuAltas(int opc, ObjectContainer db) {

		switch (opc) {
		case 1:
			darAltaProducto(db);
			break;
		case 2:
			darAltaCliente(db);
			break;
		case 3:
			darAltaPedido(db);
			break;
		}

	}

	/**
	 * Método para dar de alta a un pedido con sus productos y a que cliente
	 * pertenece
	 * 
	 * @param db
	 */
	private static void darAltaPedido(ObjectContainer db) {

		Cliente clientePatron;
		Object objectCliente, objectProducto;
		char respuesta;
		int cantidadProdcuto;

		String nombreProducto, codigoProducto;
		Producto producto = null;
		Pedido pedido;
		LineasPedido lineaPedido;

		clientePatron = new Cliente(solicitarCadena("Introduce el dni:"));

		objectCliente = buscarPatron(clientePatron, db);

		pedido = new Pedido(obtenerNumeroPedido(db), (Cliente) objectCliente);

		if (objectCliente != null) {

			do {

				codigoProducto = solicitarCadena("Introduce el código del producto: ");
				
				Producto productoPatron = new Producto(codigoProducto);

				objectProducto = buscarPatron(productoPatron, db);

				if (objectProducto != null) {
					//producto = new Producto(nombreProducto, codigoProducto);

					cantidadProdcuto = solicitarNumero("Introduce la cantidad: ");

					lineaPedido = new LineasPedido((Producto) objectProducto, cantidadProdcuto);

					pedido.annadirPedido(lineaPedido);

				} else {
					System.out.println("Ese producto no existe.");
				}

				respuesta = solicitarRespuesta("¿Quieres introducir otro producto? ");

			} while (respuesta != 'N');

			db.store(pedido);
		} else {
			System.out.println("Error. No existe ese cliente con ese dni.");
		}

	}

	/**
	 * Método para obtener el numero de un pedido
	 * 
	 * @param db
	 * @return
	 */
	private static int obtenerNumeroPedido(ObjectContainer db) {

		ObjectSet<Pedido> result = db.queryByExample(new Pedido());// Traemos todos los pedidos

		return result.size() + 1; // Le sumamos unos al numero del pedido

	}

	/**
	 * Método para solicitar un número
	 * 
	 * @param msg
	 * @return
	 */
	private static int solicitarNumero(String msg) {

		int numero = 0;

		System.out.println(msg);
		numero = Integer.parseInt(teclado.nextLine());

		return numero;
	}

	/**
	 * Método para solicitar una respuesta
	 * 
	 * @param msg
	 * @return
	 */
	private static char solicitarRespuesta(String msg) {

		char respueta;

		System.out.println(msg);
		respueta = teclado.nextLine().charAt(0);
		
		return respueta;
	}

	/**
	 * Método para dar de alta a un cliente
	 * 
	 * @param db
	 */
	private static void darAltaCliente(ObjectContainer db) {

		String dniCliente;
		Object object;
		Cliente patron;
		Cliente cliente;

		dniCliente = solicitarCadena("Introduce el dni del cliente: ");

		patron = new Cliente(dniCliente);

		object = buscarPatron(patron, db);

		if (object != null) {
			System.out.println("Error. El cliente ya existe.");
		} else {
			cliente = new Cliente(dniCliente, solicitarCadena("Introduce el nombre del cliente: "),
					solicitarCadena("Introduce la localidad: "));
			db.store(cliente);
		}

	}

	/**
	 * Método para dar de alta a un producto
	 * 
	 * @param db
	 */
	private static void darAltaProducto(ObjectContainer db) {

		Producto productoPatron;
		String nombreProducto;
		String codigoProducto;
		Object object;

		codigoProducto = solicitarCadena("Introduce el código del próducto: ");
		nombreProducto = solicitarCadena("Introduce el nombre del próducto: ");

		productoPatron = new Producto(codigoProducto);

		object = buscarPatron(productoPatron, db);

		if (object != null) {
			System.out.println("Error. Ya está ese producto con " + codigoProducto + " este código.");
		} else {
			db.store(new Producto(codigoProducto, nombreProducto));
		}

	}

	private static String solicitarCadena(String msg) {
		String cadena;

		System.out.println(msg);
		cadena = teclado.nextLine();

		return cadena;
	}

	private static int solicitarOpcionMenuConsultas() {
		int opc;
		System.out.println("1.Consulta de todos los productos.");
		System.out.println("2.Consulta de todos los clientes cuyo nombre empiece por una letra solicitada. ");
		System.out.println(
				"3.Consulta de todos los pedidos de un cliente. Se solicitará el DNI y se mostrarán los datos del cliente y  sólo los números de pedido.");
		System.out.println(
				"4.Consulta de los datos de un pedido por su numero. Deben aparecer todos los datos del pedido");
		System.out.println("5.Volver");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 5);
		return opc;
	}

	private static int solicitarOpcionMenuBajas() {
		int opc;
		System.out.println(
				"1.Baja de pedido: Se solicitará el número de pedido y si existe se eliminará. Deben eliminarse las líneas de pedido, pero no los productos, ni el cliente de ese pedido..");
		System.out.println(
				"2.Baja de cliente. Se solicitará el DNI del cliente y si existe se eliminará. Al borrar un cliente deben borrarse todos sus pedidos.");
		System.out.println("3.Volver");
		do {
			System.out.println("Introduce opcion");
			opc = Integer.parseInt(teclado.nextLine());
		} while (opc < 1 || opc > 3);
		return opc;
	}

	/**
	 * Método para abrir la base de datos
	 * 
	 * @param configuracion para abrirla con dicha confuguración
	 * @return
	 */
	private static ObjectContainer abrirBd(EmbeddedConfiguration configuracion) {

		// configuracion.common().activationDepth(nivelDeActivacion);
		// configuracion.common().objectClass(Persona.class).cascadeOnUpdate(updateOnCascade);
		// configuracion.common().objectClass(Persona.class).cascadeOnDelete(deleteOnCascade);

		ObjectContainer db = Db4oEmbedded.openFile(configuracion, BD_PRODUCTOS);

		return db;
	}

	
	/**
	 * Método para buscar un dicho objeto con cualquier patrón
	 * @param patron objecto patron a buscar
	 * @param db
	 * @return objecto si lo ha encontrado
	 */
	private static Object buscarPatron(Object patron, ObjectContainer db) {

		ObjectSet<Object> result = db.queryByExample(patron);
		Object objetoEncontrado = null;

		if (result.size() == 1) {
			objetoEncontrado = result.next();
		}

		return objetoEncontrado;

	}
}
