package com.paschoalini.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.paschoalini.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	@NotEmpty(message = "CAMPO OBRIGATÓRIO: Nome da categoria")
	@Size(min=5, max=80, message = "O tamanho deve ser entre 5 e 80 caracteres.")
	private String nome;

	public CategoriaDTO() {
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
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
}
