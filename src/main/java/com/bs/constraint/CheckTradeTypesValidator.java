package com.bs.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckTradeTypesValidator implements ConstraintValidator<CheckTradeTypes, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value.equalsIgnoreCase("Buy") || value.equalsIgnoreCase("Sell"))
			return true;
		else
			return false;
	}

}
