package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.detalleOrden;
import com.example.demo.repository.iDetalleOrdenRepository;

@Service
public class detalleOrdenServiceImplement implements iDetalleOrdenService{

	@Autowired		
	private iDetalleOrdenRepository detalleOrdenRepository;
	
	@Override
	public detalleOrden save(detalleOrden detalleOrden) {
		// TODO Auto-generated method stub
		return detalleOrdenRepository.save(detalleOrden);
	}

}
