package com.paschoalini.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paschoalini.cursomc.domain.Categoria;
import com.paschoalini.cursomc.domain.Estado;
import com.paschoalini.cursomc.repository.CategoriaRepository;
import com.paschoalini.cursomc.repository.EstadoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	/*
	 * Na fase de testes, utilizar este método para inserir dados na tabela.
	 * VERIFICAR SE É POSSÍVEL FAZER ISSO VIA JUnit.
	 */

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(1L, "Informática");
		Categoria cat2 = new Categoria(2L, "Escritório");
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		Estado est1 = new Estado(1L, "Minas Gerais");
		Estado est2 = new Estado(2L, "São Paulo");
		estadoRepository.saveAll(Arrays.asList(est1, est2));		
	}
}
