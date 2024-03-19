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
	
	public PasswordWeight scorePasswordWeight(String password) {
	
		var score = 0;
		var passwordWeight = new PasswordWeight();
		
		//iniciando validações de senha 
		
		if(Strings.isBlank(password)) {
			passwordWeight.setPasswordConfidency(setPasswordConfidency(passwordWeight, ""));
			return passwordWeight;
		}
		
		score = password.length() * 4;
		
		if(password.length() >= 8) {
			passwordWeight.setMinimumCaracteres(true);
		}
		
		if(Pattern.compile("[A-Z]").matcher(password).find()) {
			int countUpperLetters = (int) Pattern.compile("[A-Z]").matcher(password).results().count();
			passwordWeight.setUppercaseLetters(true);
			
			score += (password.length() - countUpperLetters) * 2;
		}
		
		if(Pattern.compile("[a-z]").matcher(password).find()) {
			int countLowerLetters = (int) Pattern.compile("[a-z]").matcher(password).results().count();
			passwordWeight.setLowerCaseLetters(true);
			
			score += (password.length() - countLowerLetters) * 2;
		}
		
		if(Pattern.compile("\\d").matcher(password).find()) {
			int countNumbers = (int) Pattern.compile("\\d").matcher(password).results().count();
			passwordWeight.setNumbers(true);
			
			score += countNumbers * 4;
		}
		
		if(Pattern.compile("\\W").matcher(password).find()) {
			int countSymbols = (int) Pattern.compile("\\W").matcher(password).results().count();
			passwordWeight.setSymbols(true);
			
			score += countSymbols * 6;
		}
		
		passwordWeight.setComplexity(setComplexity(passwordWeight));
		passwordWeight.setScore(setScore(passwordWeight, score, password));
		passwordWeight.setPasswordConfidency(setPasswordConfidency(passwordWeight, password));

		return passwordWeight;
	}
	
	private int setScore(PasswordWeight passwordWeight, int score, String password) {
		
		if(!passwordWeight.isComplexity() && password.length() > 1) {
			return 0;
		}
		
		return score;
	}
	
	private boolean setComplexity(PasswordWeight passwordWeight) {
		
		var requirements = 0;
		
		requirements = passwordWeight.isMinimumCaracteres() ? requirements += 1 : requirements;
		requirements = passwordWeight.isUppercaseLetters() ? requirements += 1 : requirements;
		requirements = passwordWeight.isLowerCaseLetters() ? requirements += 1 : requirements;
		requirements = passwordWeight.isNumbers() ? requirements += 1 : requirements;
		requirements = passwordWeight.isSymbols() ? requirements += 1 : requirements;
		
		if(requirements > 2)
			return true;
		
		return false;
	}
	
	private PasswordConfidencyEnum setPasswordConfidency(PasswordWeight passwordWeight, String password) {
		
		if(!passwordWeight.isComplexity() && password.length() >= 1) 
			return PasswordConfidencyEnum.VERY_WEAK;
		
		if(passwordWeight.getScore() > 20 && passwordWeight.getScore() < 40)
			return PasswordConfidencyEnum.WEAK;
		
		if(passwordWeight.getScore() >= 40 && passwordWeight.getScore() < 60) 
			return PasswordConfidencyEnum.MEDIUM;
		
		if(passwordWeight.getScore() >= 60 && passwordWeight.getScore() < 80) 
			return PasswordConfidencyEnum.STRONG;

		if(passwordWeight.getScore() >= 80) 
			return PasswordConfidencyEnum.VERY_STRONG;
		
		return PasswordConfidencyEnum.TOO_SHORT;
		
	}
}
