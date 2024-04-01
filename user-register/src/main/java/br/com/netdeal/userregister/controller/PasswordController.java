package br.com.netdeal.userregister.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.netdeal.userregister.model.PasswordRules;
import br.com.netdeal.userregister.pojo.PasswordWeight;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {
	
	private PasswordRules passwordRules;

	@PostMapping("/validate-password-weight")
	private ResponseEntity<PasswordWeight> validatePasswordWeight(@RequestBody String password) {
		
		return ResponseEntity.ok(passwordRules.scorePasswordWeight(password));
	}
	
}
