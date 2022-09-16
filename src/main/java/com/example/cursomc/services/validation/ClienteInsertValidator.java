package com.example.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.resources.exception.FieldMessage;
import com.example.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
 
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !BR.isValidSsn(objDto.getCpfOutCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido."));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidTfn(objDto.getCpfOutCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido."));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux!=null) {
			list.add(new FieldMessage("email","Este email já está cadastrado"));
		}
 
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}