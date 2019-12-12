package com.paschoalini.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.cursomc.domain.Estado;
import com.paschoalini.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	private EstadoService estadoService;
	
	@Autowired
	public EstadoResource(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findEstado(@PathVariable Long id) {
		Estado estado = estadoService.buscar(id);
		
		if(estado == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(estado, HttpStatus.OK);
	}
}
