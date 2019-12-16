package com.paschoalini.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.dto.ClienteDTO;
import com.paschoalini.cursomc.repository.ClienteRepository;
import com.paschoalini.cursomc.services.exceptions.DataIntegrityException;
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
	
	public Cliente atualizar(Cliente cliente) {
		Cliente newCliente = buscar(cliente.getId());
		updateData(newCliente, cliente);
		return clienteRepository.save(newCliente);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void remover(Long id) {
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover um cliente que possuí pedidos.");
		}
	}

	public List<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = 
			PageRequest.of(
				page,
				linesPerPage,
				Direction.valueOf(direction),
				orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
}
