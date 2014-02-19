package com.me.twistedpenguins;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Penguin {	
	public enum State{
		WAITING, RUNNING, JUMPING, DIVING, DYING
	}
	public Model model;
    public ModelInstance instance;
	
	static final float SIZE = 0.5f;
	static final Color COLOR = Color.BLUE;
	
	float SPEED = 2f;
	
	int laneId = 0;
	State state = State.WAITING;
	
	public Penguin(int laneId){
		this.laneId = laneId;
		
		ModelBuilder modelBuilder = new ModelBuilder();
        this.model = modelBuilder.createBox(5f, 5f, 5f, 
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            Usage.Position | Usage.Normal);
        this.instance = new ModelInstance(model);
	}
}
