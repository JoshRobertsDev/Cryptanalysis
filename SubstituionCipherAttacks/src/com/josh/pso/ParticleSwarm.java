package com.josh.pso;

import java.util.ArrayList;
import java.util.List;

import com.josh.cipher.Fitness;

public class ParticleSwarm {
	public List<Particle> swarm = new ArrayList<>();
	private Fitness fitness = new Fitness();
	
	public void randomSwarm(int swarmSize) {
		for(int i = 0; i < swarmSize; i++) {
			swarm.add(new Particle());
		}
	}
	
	public Particle getBestParticle() {
		Particle swarmBest = new Particle();
		double bestScore = 0;
		for(int i = 0; i < swarm.size(); i++) {
			double newScore = fitness.score(swarm.get(i).getPosition());
			if(newScore >= bestScore) {
				bestScore = newScore;
				swarmBest = swarm.get(i);
				System.out.println(new String(swarmBest.getPosition()) + " | " + bestScore);
			}
		}
		return swarmBest;
	}
	
	public void printSwarm() {
		for(int i = 0; i < swarm.size(); i++) {
			System.out.println(new String(swarm.get(i).getPosition()));
		}
	}
	
	public void searchOptimalKey(int swarmSize, int iterations, String cipherText, double swarmConfidence, double particleSelfConfidence) {
		fitness.setCipherText(cipherText);
		randomSwarm(swarmSize);
		
		for(int i = 0; i <= iterations; i++) {
			Particle swarmBest = new Particle();
			swarmBest = getBestParticle();
			for(int j = 0; j < swarmSize; j++) {
				double pScore = fitness.score(swarm.get(j).getPosition());
				if(pScore >= swarm.get(j).pBestScore) {
					swarm.get(j).pBestScore = pScore;
					swarm.get(j).pBest = swarm.get(j).getPosition();
				}
				swarm.get(j).updatePosition(swarmBest.getPosition(), swarmConfidence, particleSelfConfidence);
			}
		}
	}
}