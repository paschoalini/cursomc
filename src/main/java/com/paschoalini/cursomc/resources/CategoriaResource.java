package com.paschoalini.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paschoalini.cursomc.domain.Categoria;
import com.paschoalini.cursomc.dto.CategoriaDTO;
import com.paschoalini.cursomc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	private CategoriaService categoriaService;

	@Autowired
	public CategoriaResource(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		return new ResponseEntity<>(categoriaService.buscar(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = categoriaService.buscarTodos();
		//List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		List<CategoriaDTO> listDTO = new ArrayList<>();
		for(Categoria c : list) {
			listDTO.add(new CategoriaDTO(c));
		}
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")
			Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")
			String direction) {
		Page<Categoria> list = categoriaService.buscarPagina(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		
		/*
		List<CategoriaDTO> listDTO = new ArrayList<>();
		for(Categoria c : list) {
			listDTO.add(new CategoriaDTO(c));
		}
		*/
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<URI> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria = categoriaService.salvar(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return new ResponseEntity<>(uri, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Categoria> update(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		find(categoria.getId());
		return new ResponseEntity<>(categoriaService.atualizar(categoria), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Categoria> deleteCategoriaById(@PathVariable Long id) {
		Categoria categoria = categoriaService.buscar(id);
		categoriaService.remover(categoria.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
