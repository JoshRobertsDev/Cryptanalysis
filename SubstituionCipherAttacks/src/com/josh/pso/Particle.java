package com.josh.pso;

import java.util.ArrayList;
import java.util.List;

import com.josh.cipher.Alphabet;

public class Particle extends Alphabet {
	public Vector velocity = new Vector();
	public char[] pBest;
	
	public Particle() {
		this.Scramble();
	}
	
	public char[] getPosition() {
		return this.getAlphabet();
	}
	
	private void setPosition(char[] newPosition) {
		this.setAlphabet(newPosition);
	}
	
	public char getPositionAt(int i) {
		return this.getPosition()[i];
	}
	
	public void updatePosition(char[] gBest, double swarmConfidence, double selfConfidence) {
		List<Character> updatedPosition = new ArrayList<>();
		double random = Math.random();
		
		for(int i = 0; i < this.getPosition().length; i++) {
			if(random <= swarmConfidence && !updatedPosition.contains(gBest[i])) {
				updatedPosition.add(gBest[i]); 
			}
			else if(random > swarmConfidence && random <= selfConfidence && !updatedPosition.contains(pBest[i])) {
				updatedPosition.add(pBest[i]);
			} else {
				updatedPosition.add(' ');
			}
		}
		
		for(int i = 0; i < updatedPosition.size(); i++) {
			if(updatedPosition.get(i) == ' ' && !updatedPosition.contains(this.getGene(i))) {
				updatedPosition.set(i, this.getPositionAt(i));
			}
		}
		this.setPosition(fillMissingPositions(updatedPosition));
	}
	
	public char[] fillMissingPositions(List<Character> positionList) {
		char[] newPosition = new char[26];
    	List<Character> missingGenes = new ArrayList<>();
    	
    	for(char position = 'A'; position <= 'Z'; position++) {
    		if(!positionList.contains(position))
    			missingGenes.add(position);
    	}
    	
    	for(int i = 0; i < positionList.size(); i++) {
    		if(positionList.get(i) == ' ') {
    			newPosition[i] = missingGenes.get(0);
    			missingGenes.remove(0);
    		} else {
    			newPosition[i] = positionList.get(i);
    		}
    	}
		return newPosition;
	}
}