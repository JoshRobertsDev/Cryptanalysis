package com.josh.cipher;

/*
 * @author Josh Roberts 
 */
public class Cipher {
	
	public static char[] substitute(String inputText, char[] alphabet, char[] cipherAlphabet) {
		
		char[] cipherTextArray = inputText.toCharArray();
		
		for(int i = 0; i < cipherTextArray.length; i++) {
			for(int j = 0; j < 26; j++) {
				
				if(cipherTextArray[i] == alphabet[j]) {
					cipherTextArray[i] = cipherAlphabet[j];
					break;
				}
			}
		}
		return cipherTextArray;
	}
}
