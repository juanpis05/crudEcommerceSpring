package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.detalleOrden;
import com.example.demo.model.orden;
import com.example.demo.model.producto;
import com.example.demo.model.usuario;
import com.example.demo.service.iDetalleOrdenService;
import com.example.demo.service.iOrdenService;
import com.example.demo.service.iProductoService;
import com.example.demo.service.iUsuarioService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/") // Mapeo de la raíz del proyecto
public class userController {

	// Instancia del logger para ver datos en consola
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(userController.class);

	// Instancia del servicio de productos
	@Autowired
	private iProductoService productoService;
	
	@Autowired
	private iUsuarioService usuarioService;
	
	@Autowired
	private iOrdenService ordenService;
	
	@Autowired
	private iDetalleOrdenService detalleOrdenService;

	// Lista
	List<detalleOrden> detalles = new ArrayList<detalleOrden>();

	// Objeto que almacena los datos de la orden
	orden orden = new orden();

	// Método que mapea la vista del usuario a la raíz del proyecto
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("producto", productoService.findAll());

		return "usuario/home";
	}

	// Método para cargar el producto de usuario con el id
	@GetMapping("productoHome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		LOGGER.info("Id producto enviado como parámetro {}", id);
		// Variable de la clase producto
		producto producto = new producto();
		// Optional
		Optional<producto> productoOptional = productoService.get(id);
		// Pasar el producto
		producto = productoOptional.get();
		// Enviamos con el model a la visa de productoHome
		model.addAttribute("producto", producto);
		return "usuario/productoHome";
	}

	// Método para enviar del botón de productoHome a carrito
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		
		detalleOrden detalleOrden = new detalleOrden();
		producto producto = new producto();
		//Variable de tipo double que siempre que entre en el método se inicia en 0
		double sumaTotal = 0;
		//Variable u objeto de tipo optional para buscar el producto
		Optional<producto> productoOptional = productoService.get(id);
		LOGGER.info("Producto añadido {}", productoOptional.get());
		LOGGER.info("Cantidad añadida {}", cantidad);
		//Poner lo que está en el optional
		producto = productoOptional.get();
		//poner detalleOrden en cada fila o campo de la tabla
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()* cantidad);
		detalleOrden.setProducto(producto);
		//Validación para evitar duplicidad de producto por id
		Integer idProducto = producto.getId();
		boolean insertado = detalles.stream().anyMatch(prod -> prod.getProducto().getId() == idProducto);
		if (!insertado) {
			//Detalles
			detalles.add(detalleOrden);	
		}
		//Suma de los totales de la lista que el usuario añada al carrito
		//Función de tipo lambda stream
		//Función anónima dt
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		//Pasar variables a la vista
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}

	// Método para eliminar o quitar productos del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		// Lista nueva de productos
		List<detalleOrden> ordenNueva = new ArrayList<detalleOrden>();
		
		// Quitar un objeto de la lista de detalleOrden del carrito
		for (detalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != id) {
				ordenNueva.add(detalleOrden);
			}
		}
		//Poner la nueva lista con los productos restantes del carrito
		detalles = ordenNueva;
		//Recalcular los productos
		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		//Pasar variables a la vista
		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model) {
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "usuario/carrito";
	}
	
	//Método para pasar a la vista de resumen de la orden
	@GetMapping("/order")
	public String order(Model model) {
		usuario usuario = usuarioService.findById(1).get();
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		return "/usuario/resumenOrden";
	}
	
	@GetMapping("/saveOrder")
	public String saveOrder() {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		usuario usuario = usuarioService.findById(1).get();
		orden.setUsuario(usuario);
		ordenService.save(orden);
		//Guardar los detalles de la orden
		for(detalleOrden dt : detalles) {
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		//Limpiar valores
		orden = new orden();
		detalles.clear();
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		LOGGER.info("Nombre del producto: {}", nombre);
		List<producto> productos = productoService.findAll()
				.stream().filter(p -> p.getNombre().toUpperCase()
				.contains(nombre.toUpperCase()))
				.collect(Collectors.toList());
		model.addAttribute("producto", productos);
		return "usuario/home";
	}

}
