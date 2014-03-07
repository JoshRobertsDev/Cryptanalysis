package com.josh.genetic;

import java.util.ArrayList;
import java.util.List;
import com.josh.cipher.Alphabet;
import com.josh.cipher.Fitness;

public class Population {

	private Fitness fitness = new Fitness();
    public List<char[]> keys = new ArrayList<>();

    public Population(int populationSize, String cipherText) {
    	fitness.setCipherText(cipherText);
    	for (int i = 0; i < populationSize; i++) {
        	keys.add(null);
        }
    }
    
    public void populate() {
    	Alphabet newKey = new Alphabet();
    	
    	for (int i = 0; i < size(); i++) {
        	newKey.Scramble();
        	keys.set(i, newKey.getAlphabet().clone());
        }
    }
    
    public char[] getFittestFromPopulation() {
    	char[] bestKey = null;
    	double bestScore = 0;
    	
    	for(int i = 0; i < keys.size(); i++) {
    		double newScore = fitness.score(keys.get(i));
    		if(newScore >= bestScore) {
    			bestKey = keys.get(i);
    			bestScore = newScore; 
    		}
    	}
    	return bestKey;
    }
    
    public void setEliteKey(char[] key) {
    	addKey(0, key);
    }
    
    public void addKey(int i, char[] key) {
    	keys.set(i, key);
    }
    
    public char[] getKey(int i) {
    	return keys.get(i);
    }
    
    public int size() {
    	return keys.size();
    }
}