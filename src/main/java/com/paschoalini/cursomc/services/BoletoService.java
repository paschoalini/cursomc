package com.paschoalini.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.paschoalini.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		// Numa situação real, chamaria um WebService que gera boletos
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataPagamento(cal.getTime());
	}
}
