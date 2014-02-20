package com.me.twistedpenguins;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

import java.util.Arrays;

import static java.lang.Math.*;

public class TwistedPenguins implements ApplicationListener {
	public AssetManager assets;
	public ModelBatch modelBatch;
	public Environment environment;
	public boolean loading;
	public PerspectiveCamera cam;
    public CameraInputController camController;
    public Model twister;
    public Array<Model> models;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Mesh twisterMesh;
    public Texture snowTexture;
    
	@Override
	public void create() {	
		modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
         
        cam = new PerspectiveCamera(100, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(1f, 1f, 1f);
        cam.lookAt(0,0,0);
        cam.near = 0.1f;
        cam.far = 50f;
        cam.update();
 
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        snowTexture = new Texture(Gdx.files.internal("data/water.png"));
		//assets = new AssetManager();
        //assets.load("data/water.png", Texture.class);
        
        loading = true;
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
        instances.clear();
        assets.dispose();
	}

    private void buildModel(){

        // Cylinder
        ModelBuilder modelBuilder = new ModelBuilder();
        twister = modelBuilder.createCylinder(1f, 10f, 1f, 40,
                new Material(TextureAttribute.createDiffuse(snowTexture)),
                Usage.Position | Usage.Normal | Usage.TextureCoordinates);

        ModelInstance twisterInstance1 = new ModelInstance(twister, 0,0,0);
        instances.add(twisterInstance1);

        // Helix plane (Helicoid)
        float innerWidth = 0.5f;
        float outerWidth = 2;
        float b = 0.2f;
        float pathThickness = 0.2f;

        modelBuilder.begin();

        MeshPartBuilder test = modelBuilder.part("helix", GL10.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(TextureAttribute.createDiffuse(snowTexture)));

        for(float i = 0;i<100;i++){
            Vector3 meh1 = new Vector3((float) (cos(i/10) * innerWidth), b*i/10,(float) (innerWidth * sin(i/10)));
            Vector3 meh2 = new Vector3((float) (cos(i/10) * outerWidth), b*i/10,(float) (outerWidth * sin(i/10)));
            Vector3 meh3 = new Vector3((float) (cos((i+1)/10) * innerWidth), (b*(i+1))/10,(float) (innerWidth * sin((i+1)/10)));
            Vector3 meh4 = new Vector3((float) (cos((i+1)/10) * outerWidth), (b*(i+1))/10,(float) (outerWidth * sin((i+1)/10)));

            Vector3 meh5 = new Vector3((float) (cos(i/10) * innerWidth), (b*i/10) - pathThickness, (float) (innerWidth * sin(i/10)));
            Vector3 meh6 = new Vector3((float) (cos(i/10) * outerWidth), (b*i/10) - (pathThickness/4),(float) (outerWidth * sin(i/10)));
            Vector3 meh7 = new Vector3((float) (cos((i+1)/10) * innerWidth), ((b*(i+1))/10) - pathThickness, (float) (innerWidth * sin((i+1)/10)));
            Vector3 meh8 = new Vector3((float) (cos((i+1)/10) * outerWidth), ((b*(i+1))/10) - (pathThickness/4), (float) (outerWidth * sin((i+1)/10)));

            test.box(meh1, meh5, meh3, meh7, meh2, meh6, meh4, meh8);
        }


        instances.add(new Screw(modelBuilder.end(), 0,0,0));
    }

	private void doneLoading() {

        buildModel();
        loading = false;
    }
	
	@Override
	public void render() {		
		if (loading)// && assets.update())
            doneLoading();
        camController.update();
         
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        //triangle.render(GL10.GL_TRIANGLES, 0, 3);
        //twisterMesh.render(GL10.GL_TRIANGLES, 0, 3);
        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
