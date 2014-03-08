package com.josh.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.josh.cipher.Alphabet;
import com.josh.cipher.Fitness;

public class Genetic {

	private static final double uniformRate = 0.5;
    private static final double mutationRate = 1;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static Random random = new Random();
    private static Fitness fitness = new Fitness();
    public static char[] bestKey;
    public static double bestScore = 0;
    
    public Genetic(String cipherText) {
    	fitness.setCipherText(cipherText);
    }

    public static Population evolvePopulation(Population oldPopulation) {
        Population newPopulation = new Population(oldPopulation.size());
        
        int elitismOffset = 0;
        if (elitism) {
        	char[] eliteKey = getFittestFromPopulation(oldPopulation);
        	System.out.println(eliteKey);
        	double eliteKeyScore = fitness.score(eliteKey);
        	if(eliteKeyScore >= bestScore) {
        		bestKey = eliteKey;
        		bestScore = eliteKeyScore;
        		System.out.println(bestScore);
        	}
            newPopulation.setEliteKey(eliteKey);
            elitismOffset = 1;
        } 
        
        for (int i = elitismOffset; i < oldPopulation.size(); i++) {
            Alphabet parent1 = selectParent(oldPopulation);
            Alphabet parent2 = selectParent(oldPopulation);
            Alphabet offspring = crossover(parent1, parent2);
            newPopulation.addKey(i, offspring.getAlphabet());
        }

        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getKey(i));
        }
        return newPopulation;
    }

    private static Alphabet crossover(Alphabet parent1, Alphabet parent2) {
    	Alphabet offspring = new Alphabet();
    	List<Character> offspringList = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            if (Math.random() <= uniformRate) {
            	offspringList.add(i, geneSelection(offspringList, parent1, parent2, i));
            } else {
            	offspringList.add(i, geneSelection(offspringList, parent2, parent1, i));
            }
        }
        offspring.setAlphabet(fillBlankGenes(offspringList));
        return offspring;
    }
    
    private static Character geneSelection(List<Character> offspring, Alphabet dominantParent, Alphabet recessiveParent, int i) {
    	if(!offspring.contains(dominantParent.getGene(i))) {
    		return dominantParent.getGene(i);
    	} else if(!offspring.contains(recessiveParent.getGene(i))) {
    		return recessiveParent.getGene(i);
    	}
    	return ' ';
    }
    
    private static char[] fillBlankGenes(List<Character> offspringList) {
    	char[] offspring = new char[26];
    	List<Character> missingGenes = new ArrayList<>();
    	
    	for(char gene = 'A'; gene <= 'Z'; gene++) {
    		if(!offspringList.contains(gene))
    			missingGenes.add(gene);
    	}
    	
    	for(int i = 0; i < offspringList.size(); i++) {
    		if(offspringList.get(i) == ' ') {
    			offspring[i] = missingGenes.get(0);
    			missingGenes.remove(0);
    		} else {
    			offspring[i] = offspringList.get(i);
    		}
    	}
		return offspring;
    }
    
    public static void mutate(char[] key) {
	    if (Math.random() <= mutationRate) {
			int position1 = random.nextInt(26);
			int position2 = random.nextInt(26);
			
			while(position2 == position1) {
				position2 = random.nextInt(26);
			}
			
			char temp = key[position2];
			key[position2] = key[position1];
			key[position1] = temp;
	    }
	}

    private static Alphabet selectParent(Population population) {
        Population tournament = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * population.size());
            tournament.addKey(i, population.getKey(randomId));
        }
        Alphabet fittest = new Alphabet();
        fittest.setAlphabet(getFittestFromPopulation(tournament));
        return fittest;
    }
    
    private static char[] getFittestFromPopulation(Population population) {
    	char[] fittestKey = null;
    	double fittestKeyScore = 0;
    	
    	for(int i = 0; i < population.size(); i++) {
    		double newScore = fitness.score(population.getKey(i));
    		if(newScore >= fittestKeyScore) {
    			fittestKey = population.getKey(i);
    			fittestKeyScore = newScore; 
    		}
    	}
    	return fittestKey;
    }
}
