package com.paschoalini.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.Pedido;
import com.paschoalini.cursomc.repository.PedidoRepository;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	private PedidoRepository pedidoRepository;
	
	@Autowired
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	public Pedido buscar(Long id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
