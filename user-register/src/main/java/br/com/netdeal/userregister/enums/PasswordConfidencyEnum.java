package br.com.netdeal.userregister.enums;

public enum PasswordConfidencyEnum {
	
	TOO_SHORT ("MUITO CURTO"),
	VERY_WEAK ("MUITO FRACO"),
	WEAK ("FRACO"),
	MEDIUM ("MEDIO"),
	STRONG ("FORTE"),
	VERY_STRONG ("MUITO_FORTE");
	
	public String getPasswordConfidency;
	
	PasswordConfidencyEnum(String value) {
		getPasswordConfidency = value;
	}
	
}
