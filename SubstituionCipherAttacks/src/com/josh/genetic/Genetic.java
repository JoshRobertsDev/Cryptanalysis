package com.josh.genetic;

import java.io.File;
import java.io.PrintWriter;
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
    
    public void searchOptimalKey(int populationSize, int limit, String cipherText, int tournamentSize) {
    	this.fitness.setCipherText(cipherText);
    	this.tournamentSize = tournamentSize;
    	
    	Population population = new Population(populationSize);
    	population.populate();
    	
    	StringBuilder sb = new StringBuilder();
    	int interval = limit/20;
    	int iterations = 0;
    	int lastSecond = 0;
    	double start = System.currentTimeMillis();
    	
    	while((System.currentTimeMillis() - start)/1000 < limit) {
    		int currentSecond = (int) Math.floor((System.currentTimeMillis() - start)/1000);
    		if(currentSecond > lastSecond) {
    			sb.append(currentSecond + "," + bestScore + "\n");
    		}
    		lastSecond = currentSecond;
    		population = evolvePopulation(population);
    		iterations++;
    	}
    	/*for(int i = 0; i <= iterations; i++) {
    		if(i % interval == 0) {
    			sb.append(i + "," + bestScore + "\n");
    		}
    		population = evolvePopulation(population);
    	}*/
    	double stop = System.currentTimeMillis();
    	double time = (stop - start)/1000;
    	sb.append("\n\n" + iterations + " iterations in " + time + " seconds at " + iterations/time + " iterations/second");
    	System.out.println(sb.toString());
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