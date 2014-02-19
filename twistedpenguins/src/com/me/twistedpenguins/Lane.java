package com.me.twistedpenguins;

public class Lane {
	float speed = 0;
	float distanceFromCenter;
	
	public Lane(float distanceFromCenter){
		this.distanceFromCenter = distanceFromCenter;
		calculateSpeed();
	}
	
	public void calculateSpeed(){
		this.speed = 2f + this.distanceFromCenter;
	}
}
