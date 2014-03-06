package com.josh.hc;

import java.util.Random;
import com.josh.cipher.Alphabet;
import com.josh.cipher.Fitness;

public class HillClimb {
	
	public char[] bestKey;
	public double bestScore;
	public int iterations;
	private Random random = new Random();
	private Fitness fitness = new Fitness();
	
	public HillClimb() {		
	}
	
	public String searchOptimalKey(String cipherText, int noScoreImprovementLimit) {
		fitness.setCipherText(cipherText);
		Alphabet key = new Alphabet();
		key.Scramble();
		
		bestKey = key.getAlphabet().clone();
		bestScore = fitness.score(key.getAlphabet());
		
		int noScoreImprovementCount = 0;
		while(noScoreImprovementCount < noScoreImprovementLimit) {
			key = mutateKey(key);
			double newScore = fitness.score(key.getAlphabet());
			
			if(newScore >= bestScore) {
				bestKey = key.getAlphabet().clone();
				bestScore = newScore;
				noScoreImprovementCount = 0;
			}
			
			if(iterations % 1000 == 0) {
				System.out.println(bestScore);
			}
			iterations++;
			noScoreImprovementCount++;
		}
		return "Optimal Key: " + new String(bestKey) + "\nKey Score:" + bestScore;
	}
	
	public double getBestScore() {
		return bestScore;
	}
	
	public Alphabet mutateKey(Alphabet key) {
		char[] keyArray = key.getAlphabet();
		int position1 = random.nextInt(26);
		int position2 = random.nextInt(26);
		
		while(position2 == position1) {
			position2 = random.nextInt(26);
		}
		
		char temp = keyArray[position2];
		keyArray[position2] = keyArray[position1];
		keyArray[position1] = temp;
		key.setAlphabet(keyArray);
		return key;
	}
}
