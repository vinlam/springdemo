package com.common.component;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.define.annotation.NewValidation;

@Component
public class NewValidator implements ConstraintValidator<NewValidation, CharSequence> {

	private String[] value;

	@Override
	public void initialize(NewValidation constraintAnnotation) {
		this.value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		for (String s : Arrays.asList(this.value)) {
			if (value.toString().contains(s)) {
				return false;
			}
		}
		return true;
	}
}