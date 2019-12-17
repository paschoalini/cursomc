package com.paschoalini.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.ItemPedido;
import com.paschoalini.cursomc.domain.PagamentoComBoleto;
import com.paschoalini.cursomc.domain.Pedido;
import com.paschoalini.cursomc.domain.enums.EstadoPagamento;
import com.paschoalini.cursomc.repository.ItemPedidoRepository;
import com.paschoalini.cursomc.repository.PagamentoRepository;
import com.paschoalini.cursomc.repository.PedidoRepository;
import com.paschoalini.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	private PedidoRepository pedidoRepository;
	private BoletoService boletoService;
	private PagamentoRepository pagamentoRepository;
	private ProdutoService produtoService;
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	public PedidoService(PedidoRepository pedidoRepository, BoletoService boletoService, PagamentoRepository pagamentoRepository, ProdutoService produtoService, ItemPedidoRepository itemPedidoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.boletoService = boletoService;
		this.pagamentoRepository = pagamentoRepository;
		this.produtoService = produtoService;
		this.itemPedidoRepository = itemPedidoRepository;
	}
	
	public Pedido buscar(Long id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado para id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			// Preencher a data de vencimento do boleto
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
