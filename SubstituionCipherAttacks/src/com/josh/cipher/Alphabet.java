package com.josh.cipher;

import java.util.Random;

public class Alphabet {

	private char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public char[] getAlphabet() {
		return alphabet;
	}
	
	public void setAlphabet(char[] alphabet) {
		this.alphabet = alphabet;
	}
	
	public void Scramble() {
		int index;
		char temp;
	    Random random = new Random();

	    for (int i = 0; i < alphabet.length; i++)
	    {
	        index = random.nextInt(i + 1);
	        temp = alphabet[index];
	        alphabet[index] = alphabet[i];
	        alphabet[i] = temp;
	    }
	}
	
	public double getScore() {
		return 0;
	}
}