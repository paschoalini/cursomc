package com.paschoalini.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.paschoalini.cursomc.domain.Cliente;
import com.paschoalini.cursomc.domain.enums.TipoCliente;
import com.paschoalini.cursomc.dto.ClienteNewDTO;
import com.paschoalini.cursomc.repository.ClienteRepository;
import com.paschoalini.cursomc.resources.exceptions.FieldMessage;
import com.paschoalini.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	};

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo() == null) {
			list.add(new FieldMessage("cpfOuCnpj", "Tipo inválido: selecione Pessoa Física ou Jurídica"));
		} else {

			if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo())
					&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
			}

			if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo())
					&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
				list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
			}
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existente. Tente outro."));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
