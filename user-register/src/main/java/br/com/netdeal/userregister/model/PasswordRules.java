package br.com.netdeal.userregister.model;

import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.netdeal.userregister.enums.PasswordConfidencyEnum;
import br.com.netdeal.userregister.pojo.PasswordWeight;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PasswordRules {

	private PasswordEncoder encoder;
	
	public String encoderPassword(String password) {
		return encoder.encode(password);
	}
	
	public PasswordWeight validatePasswordWeight(String password) {
	
		var passwordWeight = new PasswordWeight();
		
		//iniciando validações de senha 
		
		if(Strings.isBlank(password)) {
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.TOO_SHORT);
			return passwordWeight;
		}
		
		validateLength(password, passwordWeight);
		validateUppercase(password, passwordWeight);
		validateLowercase(password, passwordWeight);
		validateNumber(password, passwordWeight);
		validateSpecialCaracters(password, passwordWeight);
		
		validateComplexity(passwordWeight);
		
		setScore(passwordWeight, password);
		validatePasswordConfidency(passwordWeight, password);

		return passwordWeight;
	}

	private void validateLength(String password, PasswordWeight passwordWeight) {
		if(password.length() >= 8) {
			passwordWeight.setMinimumCaracteres(true);
		}
		
		passwordWeight.setScore(password.length() * 4);
	}
	
	private void validateUppercase(String password, PasswordWeight passwordWeight) {
		if(Pattern.compile("[A-Z]").matcher(password).find()) {
			int countUpperLetters = (int) Pattern.compile("[A-Z]").matcher(password).results().count();
			passwordWeight.setUppercaseLetters(true);
			
			int score = passwordWeight.getScore();
			passwordWeight.setScore(score += (password.length() - countUpperLetters) * 2);
		}
	}

	private void validateLowercase(String password, PasswordWeight passwordWeight) {
		if(Pattern.compile("[a-z]").matcher(password).find()) {
			int countLowerLetters = (int) Pattern.compile("[a-z]").matcher(password).results().count();
			passwordWeight.setLowerCaseLetters(true);
			
			int score = passwordWeight.getScore();
			passwordWeight.setScore(score += (password.length() - countLowerLetters) * 2);
		}
	}
	
	private void validateNumber(String password, PasswordWeight passwordWeight) {
		if(Pattern.compile("\\d").matcher(password).find()) {
			int countNumbers = (int) Pattern.compile("\\d").matcher(password).results().count();
			passwordWeight.setNumbers(true);
			
			int score = passwordWeight.getScore();
			passwordWeight.setScore(score += countNumbers * 4);
		}
	}
	
	private void validateSpecialCaracters(String password, PasswordWeight passwordWeight) {
		if(Pattern.compile("\\W").matcher(password).find()) {
			int countSymbols = (int) Pattern.compile("\\W").matcher(password).results().count();
			passwordWeight.setSymbols(true);
			
			int score = passwordWeight.getScore();
			passwordWeight.setScore(score += countSymbols * 6);
		}
	}

	
	private void setScore(PasswordWeight passwordWeight, String password) {
		if(!passwordWeight.isComplexity() && password.length() > 1) {
			passwordWeight.setScore(0);
		}
	}
	
	private void validateComplexity(PasswordWeight passwordWeight) {
		
		var requirements = 0;
		
		requirements = passwordWeight.isMinimumCaracteres() ? requirements += 1 : requirements;
		requirements = passwordWeight.isUppercaseLetters() ? requirements += 1 : requirements;
		requirements = passwordWeight.isLowerCaseLetters() ? requirements += 1 : requirements;
		requirements = passwordWeight.isNumbers() ? requirements += 1 : requirements;
		requirements = passwordWeight.isSymbols() ? requirements += 1 : requirements;
		
		if(requirements > 2)
			passwordWeight.setComplexity(true);
	}
	
	private void validatePasswordConfidency(PasswordWeight passwordWeight, String password) {
		
		if(!passwordWeight.isComplexity() && password.length() >= 1) 
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.VERY_WEAK);
		
		if(passwordWeight.getScore() > 20 && passwordWeight.getScore() < 40)
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.WEAK);
		
		if(passwordWeight.getScore() >= 40 && passwordWeight.getScore() < 60) 
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.MEDIUM);
		
		if(passwordWeight.getScore() >= 60 && passwordWeight.getScore() < 80) 
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.STRONG);

		if(passwordWeight.getScore() >= 80) 
			passwordWeight.setPasswordConfidency(PasswordConfidencyEnum.VERY_STRONG);
		
	}
}
