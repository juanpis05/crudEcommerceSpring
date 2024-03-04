package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.producto;

public interface iProductoService {
	// create
	public producto save(producto producto);

	// read
	public Optional<producto> get(Integer id);

	public void update(producto producto);

	public void delete(Integer id);

	// listas - table
	public List<producto> findAll();
}
