package com.paschoalini.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Estado;
import com.paschoalini.cursomc.repository.EstadoRepository;

@Service
public class EstadoService {
	private EstadoRepository estadoRepository;
	
	@Autowired
	public EstadoService(EstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	public Estado findEstadoById(Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		return estado.orElse(null);
	}
}
