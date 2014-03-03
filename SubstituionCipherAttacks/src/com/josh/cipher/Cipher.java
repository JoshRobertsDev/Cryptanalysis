package com.josh.cipher;

public class Cipher {
	
	public String substitute(String inputText, char[] alphabet, char[] cipherAlphabet) {
		
		char[] cipherTextArray = inputText.toCharArray();
		
		for(int i = 0; i < cipherTextArray.length; i++) {
			for(int j = 0; j < alphabet.length; i++) {
				if(cipherTextArray[i] == alphabet[j]) {
					cipherTextArray[i] = cipherAlphabet[j];
					break;
				}
			}
		}
		
		return cipherTextArray.toString();
	}
}
