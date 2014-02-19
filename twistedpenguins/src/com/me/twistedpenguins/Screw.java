package com.me.twistedpenguins;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;

public class Screw extends ModelInstance{
	public enum State{
		RUNNING, STOPPED
	}

    State state = State.STOPPED;
	float VELOCITY = 1;
    float ROTATION = 20f;
    float WIDTH = 2f;
    float HEIGHT = 7f;
    float TORSION = 5;

    public float movedDistance = 0.1f;

	Array<Lane> lanes;
	
	public Screw(Model model, float x, float y, float z){
        super(model, x, y, z);
	}
	
	public void start(float velocity){
		this.VELOCITY = velocity;
        state = State.RUNNING;
	}
	
	public void stop(){
		this.VELOCITY = 0f;
        state = State.STOPPED;
	}

    public void update(float delta, float speedMultiplier){
        if(state == State.RUNNING){
            transform.trn(0,VELOCITY * delta * speedMultiplier,0);
            transform.rotate(0,0, 1, delta * ROTATION * VELOCITY);
        }else if(state == State.STOPPED){

        }
    }
}