package com.josh.cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alphabet {
		
	private char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public char[] getAlphabet() {
		return alphabet;
	}
	
	public void setAlphabet(char[] alphabet) {
		this.alphabet = alphabet;
	}
	
	public char getLetter(int i) {
		return alphabet[i];
	}
	
	public void setLetter(int i, char c) {
		alphabet[i] = c;
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
	
	public void fillBlanks(List<Character> alphabetList) {
    	List<Character> missingLetters = new ArrayList<>();
    	
    	for(char gene = 'A'; gene <= 'Z'; gene++) {
    		if(!alphabetList.contains(gene))
    			missingLetters.add(gene);
    	}
    	
    	for(int i = 0; i < alphabetList.size(); i++) {
    		if(alphabetList.get(i) == ' ') {
    			alphabet[i] = missingLetters.get(0);
    			missingLetters.remove(0);
    		} else {
    			alphabet[i] = alphabetList.get(i);
    		}
    	}
    } 
	
	public void mutate() {
		Random random = new Random();
		int position1 = random.nextInt(26);
		int position2 = random.nextInt(26);
		while(position2 == position1) {
			position2 = random.nextInt(26);
		}
		char temp = alphabet[position2];
		alphabet[position2] = alphabet[position1];
		alphabet[position1] = temp;
	}
}