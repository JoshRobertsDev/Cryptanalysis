package com.josh.main;

import com.josh.cipher.Alphabet;
import com.josh.cipher.Cipher;
import com.josh.genetic.Genetic;
import com.josh.genetic.Population;
import com.josh.hc.HillClimb;

public class Main {

	public static void main(String[] args) {
		
		HillClimb hc = new HillClimb();
		Cipher cipher = new Cipher();
		Alphabet alphabet = new Alphabet();
		Alphabet key = new Alphabet();
		Alphabet key2 = new Alphabet();
		key.Scramble();
		key2.Scramble();
		int noScoreImprovementLimit = 1;
		
		String plaintext = "TWO HOUSEHOLDS BOTH ALIKE IN DIGNITY IN FAIR VERONA WHERE WE LAY OUR SCENE FROM ANCIENT GRUDGE BREAK TO NEW MUTINY WHERE CIVIL BLOOD MAKES CIVIL HANDS UNCLEAN FROM FORTH THE FATAL LOINS OF THESE TWO FOES A PAIR OF STAR CROSSD LOVERS TAKE THEIR LIFE WHOSE MISADVENTURED PITEOUS OVERTHROWS DO WITH THEIR DEATH BURY THEIR PARENTS STRIFE THE FEARFUL PASSAGE OF THEIR DEATH MARKD LOVE AND THE CONTINUANCE OF THEIR PARENTS RAGE WHICH BUT THEIR CHILDRENS END NOUGHT COULD REMOVE IS NOW THE TWO HOURS TRAFFIC OF OUR STAGE THE WHICH IF YOU WITH PATIENT EARS ATTEND WHAT HERE SHALL MISS OUR TOIL SHALL STRIVE TO MEND";
		//String plaintext = "THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG CAT SAT ON MAT DOG CHASED CAT";
		String cipherText = new String(cipher.substitute(plaintext, alphabet.getAlphabet(), key.getAlphabet()));
	/*
		double start = System.currentTimeMillis();
		System.out.println("Actual Key:  " + new String(key.getAlphabet()) + "\n" + hc.searchOptimalKey(cipherText, noScoreImprovementLimit) + "\n");
		double stop = System.currentTimeMillis();
		double timeTaken = (stop - start)/1000;
		System.out.println("Deciphered Text: " + new String(cipher.substitute(cipherText, hc.bestKey, alphabet.getAlphabet())));
		System.out.println("Results: " + timeTaken + "s using " + hc.iterations + " iterations at " + hc.iterations/timeTaken + " i/s");
	*/
		Genetic genetic = new Genetic(cipherText);
		Population population = new Population(10, cipherText);
		population.populate();
		
		for(int i = 0; i < 200; i++) {
			population = genetic.evolvePopulation(population);
		}
	}
}