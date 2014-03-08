package com.josh.genetic;

import java.util.ArrayList;
import java.util.List;
import com.josh.cipher.Alphabet;

public class Population {

    public List<char[]> keys = new ArrayList<>();

    public Population(int populationSize) {
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