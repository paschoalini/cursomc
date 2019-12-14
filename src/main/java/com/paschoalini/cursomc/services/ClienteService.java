package com.paschoalini.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.repository.ClienteRepository;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	private ClienteRepository clienteRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente buscar(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
