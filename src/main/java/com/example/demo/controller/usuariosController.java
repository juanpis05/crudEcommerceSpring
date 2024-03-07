package com.example.demo.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.usuario;
import com.example.demo.service.iUsuarioService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/usuario")
public class usuariosController {
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(usuariosController.class);
	
	@Autowired
	private iUsuarioService usuarioService;
	
	@GetMapping("/registro")
	public String createUser() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(usuario usuario) {
		LOGGER.info("Usuario a registrar en la db: {}", usuario);
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		return "redirect:/";
	}

}
