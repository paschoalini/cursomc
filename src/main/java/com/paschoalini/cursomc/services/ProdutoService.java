package com.paschoalini.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Categoria;
import com.paschoalini.cursomc.domain.Produto;
import com.paschoalini.cursomc.repository.CategoriaRepository;
import com.paschoalini.cursomc.repository.ProdutoRepository;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	private ProdutoRepository produtoRepository;
	private CategoriaRepository categoriaRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
		this.produtoRepository = produtoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public Produto find(Long id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = 
			PageRequest.of(
				page,
				linesPerPage,
				Direction.valueOf(direction),
				orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.search(nome, categorias, pageRequest);
	}
}
