package com.paschoalini.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotEmpty(message = "CAMPO OBRIGATÓRIO: nome do cliente")
	@Size(min = 5, max = 80)
	private String nome;
	@NotEmpty(message = "CAMPO OBRIGATÓRIO: email do cliente")
	@Email(message = "CAMPO OBRIGATÓRIO: email do cliente")
	private String email;

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
