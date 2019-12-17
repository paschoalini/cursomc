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

import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.dto.ClienteDTO;
import com.paschoalini.cursomc.dto.ClienteNewDTO;
import com.paschoalini.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	private ClienteService clienteService;
	
	@Autowired
	public ClienteResource(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {	
		return new ResponseEntity<>(clienteService.find(id), HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
		Cliente cliente = clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = clienteService.update(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Cliente> deleteClienteById(@PathVariable Long id) {
		Cliente cliente = clienteService.find(id);
		clienteService.remover(cliente.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.buscarTodos();
		//List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		List<ClienteDTO> listDTO = new ArrayList<>();
		for(Cliente c : list) {
			listDTO.add(new ClienteDTO(c));
		}
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0")
			Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")
			Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")
			String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")
			String direction) {
		Page<Cliente> list = clienteService.buscarPagina(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		
		/*
		List<ClienteDTO> listDTO = new ArrayList<>();
		for(Cliente c : list) {
			listDTO.add(new ClienteDTO(c));
		}
		*/
		
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<URI> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = clienteService.fromDTO(objDto);
		obj = clienteService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
				.toUri();
		return new ResponseEntity<>(uri, HttpStatus.CREATED);
	}

}
