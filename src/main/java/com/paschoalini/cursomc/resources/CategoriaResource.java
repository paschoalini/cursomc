package com.paschoalini.cursomc.resources;

import java.net.URI;

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

import com.paschoalini.cursomc.domain.Categoria;
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
	public ResponseEntity<?> find(@PathVariable Long id) {	
		return new ResponseEntity<>(categoriaService.buscar(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<URI> insert(@RequestBody Categoria categoria) {
		categoria = categoriaService.salvar(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return new ResponseEntity<>(uri, HttpStatus.CREATED);
	}
}
