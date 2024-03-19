package br.com.netdeal.userregister.pojo;

import br.com.netdeal.userregister.enums.PasswordConfidencyEnum;
import lombok.Data;

@Data
public class PasswordWeight {

	private int score;
	private boolean minimumCaracteres;
	private boolean uppercaseLetters;
	private boolean lowerCaseLetters;
	private boolean numbers;
	private boolean symbols;
	private boolean complexity;
	private PasswordConfidencyEnum passwordConfidency;
	
	
}
