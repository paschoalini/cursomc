package com.paschoalini.cursomc.dto;

import java.io.Serializable;

import com.paschoalini.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Double preco;

	public ProdutoDTO() {
	}
	
	public ProdutoDTO(Produto obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.preco = obj.getPreco();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
