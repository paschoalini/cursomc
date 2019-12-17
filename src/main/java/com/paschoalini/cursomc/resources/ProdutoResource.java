package com.paschoalini.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.cursomc.domain.Produto;
import com.paschoalini.cursomc.dto.ProdutoDTO;
import com.paschoalini.cursomc.resources.utils.URL;
import com.paschoalini.cursomc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	private ProdutoService produtoService;
	
	@Autowired
	public ProdutoResource(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findEstado(@PathVariable Long id) {
		Produto produto = produtoService.find(id);
		
		if(produto == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "")
			String nome,
			@RequestParam(value = "categorias", defaultValue = "")
			String categorias,
			@RequestParam(value = "page", defaultValue = "0")
			Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")
			String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Long> ids = URL.decodeLongList(categorias);
		Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
}
