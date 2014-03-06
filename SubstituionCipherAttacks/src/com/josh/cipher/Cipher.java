package com.josh.cipher;

public class Cipher {
	
	public char[] substitute(String inputText, char[] alphabet, char[] cipherAlphabet) {
		
		char[] cipherTextArray = inputText.toCharArray();
		
		//System.out.println(cipherTextArray.length);
		//System.out.println(cipherTextArray);
		//System.out.println(alphabet.length + " | " + cipherAlphabet.length);
		//System.out.println(alphabet[1] + " | " + cipherAlphabet[1]);
		
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
