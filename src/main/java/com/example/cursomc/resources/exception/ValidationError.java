package com.example.cursomc.resources.exception;

import java.util.List;

public class ValidationError extends EstandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> list;
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErros() {
		return list;
	}

	public void addError(String fieldName, String message) {
		list.add(new FieldMessage(fieldName, message));
	}

}
