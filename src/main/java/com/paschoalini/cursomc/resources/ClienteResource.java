package com.paschoalini.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	private ClienteService clienteService;
	
	@Autowired
	public ClienteResource(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) {	
		return new ResponseEntity<>(clienteService.buscar(id), HttpStatus.OK);
	}
}
