package com.paschoalini.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paschoalini.cursomc.domain.Pedido;
import com.paschoalini.cursomc.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	private PedidoService pedidoService;
	
	@Autowired
	public PedidoResource(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findEstado(@PathVariable Long id) {
		Pedido pedido = pedidoService.buscar(id);
		
		if(pedido == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<URI> insert(@Valid @RequestBody Pedido obj) {
		obj = pedidoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
				.toUri();
		return new ResponseEntity<>(uri, HttpStatus.CREATED);
	}
}
