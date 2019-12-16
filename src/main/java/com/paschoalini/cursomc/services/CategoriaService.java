package com.paschoalini.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Categoria;
import com.paschoalini.cursomc.repository.CategoriaRepository;
import com.paschoalini.cursomc.services.exceptions.DataIntegrityException;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	private CategoriaRepository categoriaRepository;

	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public Categoria buscar(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void remover(Long id) {
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover uma categoria que possuí produtos.");
		}
	}
}
