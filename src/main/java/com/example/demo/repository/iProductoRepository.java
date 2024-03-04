package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.producto;

@Repository
public interface iProductoRepository extends JpaRepository<producto, Integer>{
	

}
