package com.paschoalini.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Estado;
import com.paschoalini.cursomc.repository.EstadoRepository;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	private EstadoRepository estadoRepository;
	
	@Autowired
	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	public Estado buscar(Long id) {
		Optional<Estado> obj = estadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Estado.class.getName()));
	}
}
