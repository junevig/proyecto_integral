package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.dao.IClienteDAO;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.DetalleFactura;
import com.example.demo.models.entity.Factura;
import com.example.demo.models.entity.Producto;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId,
			Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteDAO.buscarCliente(clienteId);
		if(cliente == null) {
			flash.addFlashAttribute("error","El cliente no existe");
			return "redirect:/clientes/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);
		return "facturas/formulario";
	}
	
	@RequestMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		return clienteDAO.findByNombre(term);
	}
	
	@PostMapping("/form")
	public String guardar(Factura factura,
			@RequestParam(name = "item_id[]", required = true) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = true) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status) {
		
			for(int i=0; i<itemId.length; i++) {
				Producto p = clienteDAO.findProductoById(itemId[i]);
				
				DetalleFactura linea = new DetalleFactura();
				linea.setCantidad(cantidad[i]);
				linea.setProducto(p);
				factura.addDetalleFactura(linea);
				
				log.info("ID: "+itemId[i].toString()+" cantidad: "+cantidad[i].toString());
			}
			
			clienteDAO.saveFactura(factura);
			status.setComplete();
			
			flash.addFlashAttribute("success", "Factura creda con �xito");
		
		return "redirect:/clientes/ver/"+factura.getCliente().getId();
	}
}
