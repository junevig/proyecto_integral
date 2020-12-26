package com.example.demo.models.dao;

import java.util.List;

import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.Factura;
import com.example.demo.models.entity.Producto;

public interface IClienteDAO {
	public List<Cliente> getClientes();
	public void save(Cliente cliente);
	public Cliente buscarCliente(Long id);
	public void eliminar(Long id);
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	public Producto findProductoById(Long id);
}
