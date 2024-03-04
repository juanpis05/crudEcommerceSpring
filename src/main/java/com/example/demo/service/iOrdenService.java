package com.example.demo.service;

import java.util.List;

import com.example.demo.model.orden;

public interface iOrdenService {
	//Método de guardado
	public orden save(orden orden);
	//Lista
	public List<orden> findAll();
	//Método para generar los números de orden incremental
	public String generarNumeroOrden();

}
