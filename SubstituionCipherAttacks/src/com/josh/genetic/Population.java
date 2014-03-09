package com.josh.genetic;

import java.util.ArrayList;
import java.util.List;

public class Population {

    public List<Individual> individuals = new ArrayList<>();

    public Population(int populationSize) {
    	for (int i = 0; i < populationSize; i++) {
        	individuals.add(null);
        }
    }
    
    public void populate() {
    	Individual newIndividual = new Individual();
    	
    	for (int i = 0; i < size(); i++) {
    		newIndividual.Scramble();
        	individuals.set(i, newIndividual);
        }
    }
    
    public void setEliteIndividual(Individual individual) {
    	addIndividual(0, individual);
    }
    
    public void addIndividual(int i, Individual individual) {
    	individuals.set(i, individual);
    }
    
    public Individual getIndividual(int i) {
    	return individuals.get(i);
    }
    
    public int size() {
    	return individuals.size();
    }
}