package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.dao.IClienteDAO;
import com.example.demo.models.entity.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@RequestMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("clientes", clienteDAO.getClientes());
		model.addAttribute("pagina", 1);
		return "clientes/listar";
	}
	
	@RequestMapping("/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Registrar Cliente");
		model.put("cliente", cliente);
		model.put("pagina", 2);
		return "clientes/formulario";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Cliente cliente) {
		clienteDAO.save(cliente);
		return "redirect:listar";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = clienteDAO.buscarCliente(id);
		}else {
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Modificar Cliente");
		model.addAttribute("cliente",cliente);
		return "clientes/formulario";
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if(id > 0) {
			clienteDAO.eliminar(id);
		}
		return "redirect:/clientes/listar";
	}
	
	@RequestMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteDAO.buscarCliente(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		model.addAttribute("cliente", cliente);
		return "clientes/ver";
	}
}
