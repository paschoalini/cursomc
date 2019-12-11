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
	public List<Categoria> listar() {
		List<Categoria> categorias = new ArrayList<>();
		categorias.add(new Categoria(1L, "Informática"));
		categorias.add(new Categoria(2L, "Escritório"));
		return categorias;
	}
}
