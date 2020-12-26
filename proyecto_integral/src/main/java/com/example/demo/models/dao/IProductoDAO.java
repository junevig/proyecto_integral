package com.example.demo.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.Producto;

public interface IProductoDAO extends CrudRepository<Producto, Long> {
	
	@Query("SELECT p FROM Producto p WHERE p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
}
