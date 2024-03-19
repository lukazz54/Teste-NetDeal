package br.com.netdeal.userregister.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.netdeal.userregister.entity.User;
import br.com.netdeal.userregister.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
public class UserService {

	private UserRepository userRepository;
	
	public ResponseEntity<List<User>> getUsers() {
		log.info("Retornando usuarios do DB");
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	public ResponseEntity<String> insertNewUser(User user){
		log.info("Iniciando inclusão de novo usuario no DB: {} ", user);
		
		try {
			userRepository.save(user);
		}catch(Exception e) {
			log.error("Houve um erro ao tentar incluir novo usuario no DB: {} ", e.getMessage());
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Aconteceu um erro ao incluir um novo usuario!");
		}
		
		return ResponseEntity.ok("Usuario incluido corretamente!");
	}
	
	public ResponseEntity<String> updateUser(User user) {
		log.info("Iniciando atualização do usuario {} no DB", user.getName());
		
		Optional<User> userToUpdate = userRepository.findById(user.getId());
		try {
			if(userToUpdate.isPresent()) {
				log.info("Usuario existente no banco, realizando atualização");
				userRepository.save(user);
			}
		}catch(Exception e) {
			log.error("Houve um erro ao tentar editar um usuario no DB: {} ", e.getMessage());
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Aconteceu um erro ao editar o usuario: " + user.getName());
		}
		
		return ResponseEntity.ok("Usuario atualizado com sucesso.");
	}
	
	public ResponseEntity<String> deleteUser(Long userId) {
		log.info("Iniciando remoção do usuario de ID {} no DB", userId);
		
		Optional<User> userToDelete = userRepository.findById(userId);
		try {
			if(userToDelete.isPresent()) {
				log.info("Usuario existente no banco, realizando remoção");
				userRepository.deleteById(userId);
			}
		}catch (Exception e) {
			log.error("Houve um erro ao tentar excluir um usuario no DB: {} ", e.getMessage());
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Aconteceu um erro ao excluir o usuario!");
		}
		
		return ResponseEntity.ok("Usuario removido com sucesso");
		
	}
	
	
	
}
