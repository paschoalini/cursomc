package com.paschoalini.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.cursomc.model.Categoria;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	@GetMapping
	public String listar() {
		return "REST executando corretamente!";
	}
}
