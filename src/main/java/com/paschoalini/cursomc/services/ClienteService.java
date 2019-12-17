package com.paschoalini.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Cidade;
import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.domain.Endereco;
import com.paschoalini.cursomc.domain.enums.TipoCliente;
import com.paschoalini.cursomc.dto.ClienteDTO;
import com.paschoalini.cursomc.dto.ClienteNewDTO;
import com.paschoalini.cursomc.repository.CidadeRepository;
import com.paschoalini.cursomc.repository.ClienteRepository;
import com.paschoalini.cursomc.repository.EnderecoRepository;
import com.paschoalini.cursomc.services.exceptions.DataIntegrityException;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	private ClienteRepository clienteRepository;
	private CidadeRepository cidadeRepository;
	private EnderecoRepository enderecoRepository;

	@Autowired
	public ClienteService(ClienteRepository clienteRepository, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository) {
		this.clienteRepository = clienteRepository;
		this.cidadeRepository = cidadeRepository;
		this.enderecoRepository = enderecoRepository;
	}

	public Cliente find(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente cliente) {
		Cliente newCliente = find(cliente.getId());
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
	
	public Cliente fromDTO(ClienteNewDTO clienteDTO) {
		Cliente cli = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()));
		Cidade cid = cidadeRepository.getOne(clienteDTO.getCidadeId());
		Endereco end = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteDTO.getTelefone1());
		
		if(clienteDTO.getTelefone2() != null)
			cli.getTelefones().add(clienteDTO.getTelefone2());
		
		if(clienteDTO.getTelefone3() != null)
			cli.getTelefones().add(clienteDTO.getTelefone3());
			
		return cli;
	}
}
