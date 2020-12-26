package com.example.demo.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.Factura;

public interface IFacturaDAO extends CrudRepository<Factura, Long> {
	
}
