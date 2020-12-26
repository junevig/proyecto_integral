package com.example.demo.models.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.Factura;
import com.example.demo.models.entity.Producto;

@Repository
public class ClienteDAOImpl implements IClienteDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IProductoDAO productoDAO;
	
	@Autowired
	private IFacturaDAO facturaDAO;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> getClientes() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId()>0) {
			//modificar
			em.merge(cliente);
		}else {
			//guardar nuevo
			em.persist(cliente);
		}
	}

	@Override
	public Cliente buscarCliente(Long id) {
		return em.find(Cliente.class, id);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		Cliente cliente = buscarCliente(id);
		em.remove(cliente);
	}

	@Override
	public List<Producto> findByNombre(String term) {
		// TODO Auto-generated method stub
		return productoDAO.findByNombre(term);
	}

	@Override
	public void saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		facturaDAO.save(factura);
	}

	@Override
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDAO.findById(id).orElse(null);
	}

}
