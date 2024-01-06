package com.herick.lojavirtual.dto.validationerror;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.herick.lojavirtual.dto.custonerror.CustonError;
import com.herick.lojavirtual.dto.fildmessage.FieldMessage;

public class ValidationError extends CustonError {

	private List<FieldMessage> erro = new ArrayList<>();

	public ValidationError(Instant timestamp, Integer status, String error, String path) {
		super(timestamp, status, error, path);
	}

	public void addError(String fieldName, String Message) {
		erro.add(new FieldMessage(fieldName, Message));
	}

	public List<FieldMessage> getErro() {
		return erro;
	}


	

}
