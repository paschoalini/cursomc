package com.paschoalini.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	private CategoriaService categoriaService;
	
	@Autowired
	public CategoriaResource(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> listar(@PathVariable Long id) {	
		return new ResponseEntity<>(categoriaService.buscar(id), HttpStatus.OK);
	}
}
