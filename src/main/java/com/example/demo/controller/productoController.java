package com.example.demo.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.producto;
import com.example.demo.model.usuario;
import com.example.demo.service.productoService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/producto")
public class productoController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(productoController.class);

	@Autowired
	private productoService productoService;

	// variable para la carga de imagenes
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "producto/show";
	}

	@GetMapping("/create")
	public String create() {
		return "producto/create";
	}

	@PostMapping("/save")
	public String save(producto producto) {
		LOGGER.info("Este es el producto a guardar en la DB: {}", producto);
		usuario u = new usuario(1, " ", " ", " ", " ", " ", " ", " ", " ");
		producto.setUsuario(u);
		productoService.save(producto);
		return "redirect:/producto";
	}

}
