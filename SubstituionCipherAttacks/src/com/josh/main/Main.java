package com.josh.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.josh.cipher.Alphabet;
import com.josh.cipher.Cipher;
import com.josh.cipher.Fitness;
import com.josh.genetic.Genetic;
import com.josh.hc.HillClimb;
import com.josh.pso.Particle;
import com.josh.pso.ParticleSwarm;

public class Main {

	public static void main(String[] args) {
		
		HillClimb hc = new HillClimb();
		Alphabet alphabet = new Alphabet();
		Alphabet key = new Alphabet();
		key.Scramble();
		int noScoreImprovementLimit = 10000;
		
		String plaintext = "TWO HOUSEHOLDS BOTH ALIKE IN DIGNITY IN FAIR VERONA WHERE WE LAY OUR SCENE FROM ANCIENT GRUDGE BREAK TO NEW MUTINY WHERE CIVIL BLOOD MAKES CIVIL HANDS UNCLEAN FROM FORTH THE FATAL LOINS OF THESE TWO FOES A PAIR OF STAR CROSSED LOVERS TAKE THEIR LIFE WHOSE MISADVENTURE PITEOUS OVERTHROWS DO WITH THEIR DEATH BURY THEIR PARENTS STRIFE THE FEARFUL PASSAGE OF THEIR DEATH MARKED LOVE AND THE CONTINUANCE OF THEIR PARENTS RAGE WHICH BUT THEIR CHILDREN END NOUGHT COULD REMOVE IS NOW THE TWO HOURS TRAFFIC OF OUR STAGE THE WHICH IF YOU WITH PATIENT EARS ATTEND WHAT HERE";
		//String plaintext = "THE QUICK BROWN FOX JUMPED OVER THE LAZY DOG CAT SAT ON MAT DOG CHASED CAT";
		String cipherText = new String(Cipher.substitute(plaintext, alphabet.getAlphabet(), key.getAlphabet()));
	/*
		double start = System.currentTimeMillis();
		System.out.println("Actual Key:  " + new String(key.getAlphabet()) + "\n" + hc.searchOptimalKey(cipherText, 60) + "\n");
		double stop = System.currentTimeMillis();
		double timeTaken = (stop - start)/1000;
		System.out.println("Deciphered Text: " + new String(Cipher.substitute(cipherText, hc.bestKey, alphabet.getAlphabet())));
		System.out.println("Results: " + timeTaken + "s using " + hc.iterations + " iterations at " + hc.iterations/timeTaken + " i/s");
	*/
		
		Fitness fitness = new Fitness();
		fitness.setCipherText(cipherText);
		System.out.println(fitness.score(key.getAlphabet()));
		
		List<Double[]> resultsList = new ArrayList<>();		
		
		for(int i = 0; i < 3; i++) {
			Genetic genetic = new Genetic();
			resultsList.add(genetic.searchOptimalKey(20, 10, cipherText, 10));
			System.out.println("Iteration: " + (i+1));
		}
		
		String output = "";
		for(int i = 0; i < resultsList.get(0).length; i++) {
			output += i + ",";
			for(int j = 0; j < resultsList.size(); j++) {
				output += resultsList.get(j)[i];
				if(j != (resultsList.size()-1)) {
					output += ",";
				}
			}
			output += "\n";
		}
				
		try {
			File results = new File("results.txt");
			if(!results.exists()) 
				results.createNewFile();
			FileWriter fw = new FileWriter(results.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println(new String(key.getAlphabet()));
		//start = System.currentTimeMillis();
		//ParticleSwarm ps = new ParticleSwarm();
		//ps.searchOptimalKey(500, 20, cipherText, 0.2, 0.1);
		//stop = System.currentTimeMillis();
		//System.out.println((stop - start)/1000);
	
	 
	}
	
}