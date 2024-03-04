package com.example.demo.service;

import java.util.Optional;

import com.example.demo.model.usuario;

public interface iUsuarioService {
	// Definir los métodos
	public usuario save(usuario usuario);

	public Optional<usuario> get(Integer id);

	public void update(usuario usuario);

	public void delete(Integer id);

	Optional<usuario> findById(Integer id);

}
