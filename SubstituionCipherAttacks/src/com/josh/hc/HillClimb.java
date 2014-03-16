package com.josh.hc;

import java.util.Random;

import com.josh.cipher.Alphabet;
import com.josh.cipher.Fitness;
import com.josh.cipher.FrequencyAnalyse;

public class HillClimb {
	
	public char[] bestKey;
	public double bestScore;
	public int iterations;
	private Random random = new Random();
	private Fitness fitness = new Fitness();
	
	public HillClimb() {		
	}
	
	public Double[] searchOptimalKey(String cipherText, int iterations) {
		fitness.setCipherText(cipherText);
		Alphabet key = new Alphabet();
		//key.Scramble();
		key.setAlphabet(FrequencyAnalyse.getKey(cipherText));
		
		int intervals = 20;
    	int scoreIterator = 0;
    	double scoreInterval = iterations/intervals;
    	Double[] results = new Double[intervals+4];
    	
		bestKey = key.getAlphabet().clone();
		bestScore = fitness.score(key.getAlphabet());
		results[scoreIterator] = bestScore;
		System.out.println(bestScore);
		
		double start = System.currentTimeMillis();
		for(int i = 0; i <= iterations; i++) {
			key = mutateKey(key);
			double newScore = fitness.score(key.getAlphabet());
			
			if(newScore >= bestScore) {
				bestKey = key.getAlphabet().clone();
				bestScore = newScore;
				
			}
			if(i % scoreInterval == 0) {
    			scoreIterator++;
    			System.out.println(bestScore);
    			results[scoreIterator] = bestScore;
    		}
		}
		double time = (System.currentTimeMillis() - start)/1000;
		results[intervals+1] = (double) iterations;
    	results[intervals+2] = time;
    	results[intervals+3] = iterations/time;
		return results;
	}
	
	public String searchOptimalKeyByTime(String cipherText, int limit) {
		fitness.setCipherText(cipherText);
		Alphabet key = new Alphabet();
		key.Scramble();
		
		bestKey = key.getAlphabet().clone();
		bestScore = fitness.score(key.getAlphabet());
		
		int noScoreImprovementCount = 0;
		double start = System.currentTimeMillis();
		
		while((System.currentTimeMillis() - start)/1000 < limit) {
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
