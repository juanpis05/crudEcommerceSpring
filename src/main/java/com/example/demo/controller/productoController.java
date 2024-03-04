package com.example.demo.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.producto;
import com.example.demo.model.usuario;
import com.example.demo.service.iProductoService;
import com.example.demo.service.uploadFileService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/producto")
public class productoController {

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(productoController.class);

	@Autowired
	private iProductoService productoService;

	// variable para la carga de imagenes
	@Autowired
	private uploadFileService upload;
	
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
	public String save(producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Este es el producto a guardar en la DB: {}", producto);
		usuario u = new usuario(1, " ", " ", " ", " ", " ", " ", " ", " ");
		producto.setUsuario(u);
		
		//Validación de imagen
		if (producto.getId() == null) {
			String nombreImagen = upload.saveImages(file, producto.getNombre());
			producto.setImagen(nombreImagen);
			
		}
		
		productoService.save(producto);
		return "redirect:/producto";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		producto producto = new producto();
		Optional<producto> optionalProducto = productoService.get(id);
		producto = optionalProducto.get();
		LOGGER.info("Búsqueda de producto: {}", producto);
		model.addAttribute("producto", producto);
		return "producto/edit";
	}
	
	@PostMapping("/update")
	public String update(producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		LOGGER.info("Este es el producto a actualizar en la DB: {}", producto);
		//Variable global de productos
		producto p = new producto();
		p = productoService.get(producto.getId()).get();
		if (file.isEmpty()) {
			producto.setImagen(p.getImagen());
		}else {
			if (!p.getImagen().equals("default.jpg")) {
				upload.deleteImages(p.getImagen());
				
			}
			String nombreImagen = upload.saveImages(file,p.getNombre());
			producto.setImagen(nombreImagen);
		}
		
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/producto";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		producto p = new producto();
		p = productoService.get(id).get();
		if (!p.getImagen().equals("default.jpg")) {
			upload.deleteImages(p.getImagen());
		}
		productoService.delete(id);
		return "redirect:/producto";
	}
	


}
