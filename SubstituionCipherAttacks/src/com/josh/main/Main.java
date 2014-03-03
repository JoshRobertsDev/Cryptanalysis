package com.josh.main;

import com.josh.cipher.Alphabet;

public class Main {

	public static void main(String[] args) {
		Alphabet a = new Alphabet();
		System.out.println(a.getAlphabet());
		a.Scramble();
		System.out.println(a.getAlphabet().length);
		
		
	}

}

