package com.controller.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/api/1")
@Validated
public class RequestAndPathVariableValidationController {

	@GetMapping("/day")
	public String getNameOfDayByNumberRequestParam(@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
		return dayOfWeek + "";
	}

	@GetMapping("/day/{dayOfWeek}")
	public String getNameOfDayByPathVariable(@PathVariable("dayOfWeek") @Min(1) @Max(7) Integer dayOfWeek) {
		return dayOfWeek + "";
	}

	@GetMapping("/valid-name")
	public void validStringRequestParam(@RequestParam @NotBlank @Size(max = 10) @Pattern(regexp = "^[A-Z][a-zA-Z0-9]+$") String name) {

	}

}