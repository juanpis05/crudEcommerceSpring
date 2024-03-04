package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.usuario;
import com.example.demo.repository.iUsuarioRepository;

@Service
public class usuarioServiceImplement implements iUsuarioService{
	
	//Objeto de tipo privado
	@Autowired
	private iUsuarioRepository usuarioRepository;

	@Override
	public usuario save(usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<usuario> get(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void update(usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<usuario> findById(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

}
