package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.producto;
import com.example.demo.service.iProductoService;

@Controller
@RequestMapping("/administrador")
public class administradorController {
	
	@Autowired
	private iProductoService productoService;
	
	@GetMapping("")
	public String home(Model model) {
		
		List<producto> producto = productoService.findAll();
		model.addAttribute("producto", producto);
		
		return "administrador/home";
	}
}
