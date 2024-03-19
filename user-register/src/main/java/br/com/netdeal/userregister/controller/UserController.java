package br.com.netdeal.userregister.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.netdeal.userregister.entity.PasswordWeight;
import br.com.netdeal.userregister.entity.User;
import br.com.netdeal.userregister.model.PasswordRules;
import br.com.netdeal.userregister.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private PasswordRules passwordRules;
	
	@GetMapping("/get-all-user")
	@CrossOrigin(origins = "http://localhost")
	private ResponseEntity<List<User>> getUsers() {
		return userService.getUsers();
	}
	
	
	@PostMapping("/insert-new-user")
	@CrossOrigin(origins = "http://localhost")
	private ResponseEntity<List<User>> insertNewUser(@RequestBody User user) {
		PasswordWeight scorePasswordWeight = passwordRules.scorePasswordWeight(user.getPassword());
		
		user.setPassword(passwordRules.encoderPassword(user.getPassword()));
		user.setPasswordScore(scorePasswordWeight.getScore());
		user.setPasswordWeight(scorePasswordWeight.getPasswordConfidency().name());
		
		ResponseEntity<String> insertNewUser = userService.insertNewUser(user);
		
		if(insertNewUser.getStatusCode().is2xxSuccessful()) {
			return userService.getUsers();
		}
		
		return ResponseEntity.internalServerError().body(null);
	}
	
	@PutMapping("/update-user")
	@CrossOrigin(origins = "http://localhost")
	private ResponseEntity<List<User>> updateUser(@RequestBody User user) {
		ResponseEntity<String> updateUser = userService.updateUser(user);
		
		if(updateUser.getStatusCode().is2xxSuccessful()) {
			return userService.getUsers();
		}
		
		return ResponseEntity.internalServerError().body(null);
	}
	
	
	@DeleteMapping("/delete-user/{userId}")
	@CrossOrigin(origins = "http://localhost")
	private ResponseEntity<List<User>> deleteUser(@PathVariable Long userId) {
		ResponseEntity<String> deleteUser = userService.deleteUser(userId);
		
		if(deleteUser.getStatusCode().is2xxSuccessful()) {
			return userService.getUsers();
		}
		
		return ResponseEntity.internalServerError().body(null);
	
	}
}
