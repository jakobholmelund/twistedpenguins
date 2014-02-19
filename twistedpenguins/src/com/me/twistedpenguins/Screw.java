package com.me.twistedpenguins;

import java.util.Collection;

public class Screw {
	public enum State{
		RUNNING, STOPPED
	}
	
	float speed = 0;
	Collection<Lane> lanes;
	Collection<Penguin> penguins;
	
	public Screw(Collection<Lane> lanes, Collection<Penguin> penguins){
		this.lanes = lanes;
		this.penguins = penguins;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
		for(Lane lane : this.lanes){
			lane.calculateSpeed();
		}
	}
	
	public void stop(){
		this.speed = 0;
	}
	
	public void removePenguin(Penguin penguin){
		this.penguins.remove(penguin);
	} 
	
	public void addPenguin(Penguin penguin){
		this.penguins.add(penguin);
	}
}