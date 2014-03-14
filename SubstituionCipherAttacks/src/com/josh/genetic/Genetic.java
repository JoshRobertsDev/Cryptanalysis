package com.josh.genetic;

import java.util.ArrayList;
import java.util.List;

import com.josh.cipher.Fitness;

public class Genetic {

	private final boolean elitism = true;
	private final double uniformRate = 0.5;
    private int tournamentSize = 5;
    private Fitness fitness = new Fitness();
    //private Population population;
    public char[] bestKey;
    public double bestScore = 0;
    
    public Double[] searchOptimalKey(int populationSize, int iterations, String cipherText, int tournamentSize) {
    	this.fitness.setCipherText(cipherText);
    	this.tournamentSize = tournamentSize;
    	
    	Population population = new Population(populationSize);
    	population.populate();
    	
    	int intervals = 20;
    	int scoreIterator = 0;
    	double scoreInterval = iterations/intervals;
    	Double[] results = new Double[intervals+4];
    	
    	bestKey = getFittestFromPopulation(population).getAlphabet();
    	bestScore = fitness.score(bestKey);
    	results[scoreIterator] = bestScore;
    	
    	double start = System.currentTimeMillis();
    	for(int i = 1; i <= iterations; i++) {
    		population = evolvePopulation(population);
    		if(i % scoreInterval == 0) {
    			scoreIterator++;
    			results[scoreIterator] = bestScore;
    		}
    	}
    	double time = (System.currentTimeMillis() - start)/1000;
    	
    	results[intervals+1] = (double) iterations;
    	results[intervals+2] = time;
    	results[intervals+3] = iterations/time;
    	return results;
    }
    
    public Double[] searchOptimalKeyByTime(int populationSize, int limit, String cipherText, int tournamentSize) {
    	this.fitness.setCipherText(cipherText);
    	this.tournamentSize = tournamentSize;
    	
    	Population population = new Population(populationSize);
    	population.populate();
    	bestKey = getFittestFromPopulation(population).getAlphabet();
    	bestScore = fitness.score(bestKey);
    	
    	//int interval = limit/20;
    	int iterations = 0;
    	int lastSecond = -1;
    	Double[] results = new Double[limit+4];
    	double start = System.currentTimeMillis();
    	
    	while((System.currentTimeMillis() - start)/1000 <= limit) {
    		int currentSecond = (int) Math.floor((System.currentTimeMillis() - start)/1000);
    		if(currentSecond > lastSecond) {
    			results[currentSecond] = bestScore;
    		}
    		lastSecond = currentSecond;
    		population = evolvePopulation(population);
    		iterations++;
    	}
    	
    	double stop = System.currentTimeMillis();
    	double time = (stop - start)/1000;
    	results[limit] = bestScore;
    	results[limit+1] = (double) iterations;
    	results[limit+2] = time;
    	results[limit+3] = iterations/time;
    	return results;
    }

    private Population evolvePopulation(Population oldPopulation) {
        Population newPopulation = new Population(oldPopulation.size());
        
        int elitismOffset = 0;
        if (elitism) {
        	Individual eliteIndividual = getFittestFromPopulation(oldPopulation);
        	double eliteKeyScore = fitness.score(eliteIndividual.getAlphabet());
        	if(eliteKeyScore >= bestScore) {
        		bestKey = eliteIndividual.getAlphabet().clone();
        		bestScore = eliteKeyScore;
        		//System.out.println(new String(eliteIndividual.getAlphabet()) + " | " + bestScore);
        	}
            newPopulation.setEliteIndividual(eliteIndividual);
            elitismOffset = 1;
        } 
        
        for (int i = elitismOffset; i < oldPopulation.size(); i++) {
            Individual parent1 = selectParent(oldPopulation);
            Individual parent2 = selectParent(oldPopulation);
            Individual offspring = crossover(parent1, parent2);
            newPopulation.addIndividual(i, offspring);
        }

        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            newPopulation.getIndividual(i).mutate();;
        }
        return newPopulation;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
    	Individual offspring = new Individual();
    	List<Character> offspringList = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            if (Math.random() <= uniformRate) {
            	offspringList.add(i, geneSelection(offspringList, parent1, parent2, i));
            } else {
            	offspringList.add(i, geneSelection(offspringList, parent2, parent1, i));
            }
        }
        offspring.fillBlanks(offspringList);
        return offspring;
    }
    
    private Character geneSelection(List<Character> offspring, Individual dominantParent, Individual recessiveParent, int i) {
    	if(!offspring.contains(dominantParent.getLetter(i))) {
    		return dominantParent.getLetter(i);
    	} else if(!offspring.contains(recessiveParent.getLetter(i))) {
    		return recessiveParent.getLetter(i);
    	}
    	return ' ';
    }

    private Individual selectParent(Population population) {
        Population tournament = new Population(tournamentSize);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * population.size());
            tournament.addIndividual(i, population.getIndividual(randomId));
        }
        Individual fittestIndividual = new Individual();
        fittestIndividual = getFittestFromPopulation(tournament);
        return fittestIndividual;
    }
    
    private Individual getFittestFromPopulation(Population population) {
    	Individual fittestIndividual = null;
    	double fittestIndividualScore = 0;
    	
    	for(int i = 0; i < population.size(); i++) {
    		double newScore = fitness.score(population.getIndividual(i).getAlphabet());
    		if(newScore >= fittestIndividualScore) {
    			fittestIndividual = population.getIndividual(i);
    			fittestIndividualScore = newScore; 
    		}
    	}
    	return fittestIndividual;
    }
}