package org.devtal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DancingLines extends BasicScreen {

	Mesh mesh;
	private ShaderProgram meshShader;
	private float currentTime = 0;

	@Override
	public void create() {
		mesh = new Mesh(true, 13, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));
		float c1 = Color.toFloatBits(255, 50, 0, 255);
		float c2 = Color.toFloatBits(255, 50, 0, 255);
		float c3 = Color.toFloatBits(255, 250, 0, 255);
		float c4 = Color.toFloatBits(240, 0, 0, 255);

		mesh.setVertices(new float[] { 0,0.5f,0,c3,3.14f,0.5f,-0.5f,0,c2,3.14f/2,0.25f,0,0,c4,0,//
				-0.25f,0,0,c1,0,-0.5f, -0.5f, 0, c1, 0,0, 0.5f,	0, c3, 3.14f ,//
				0,	-0.5f, 0, c4,	3.14f, -0.5f, -0.5f, 0, c1, 0, 0.5f, -0.5f, 0, c2, 3.14f / 2,//
				-0.5f, -0.5f, 0, c1, 0, 0.5f, -0.5f, 0, c2, 3.14f / 2, 0, 0.5f,	0, c3, 3.14f });
		meshShader = SimpleShader.createShader(Gdx.graphics, "lines");

	}

	@Override
	public void render(float accum) {
		currentTime += accum;
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		meshShader.begin();
		// meshShader.setUniformi("u_texture", 0);
		// meshShader.setUniformMatrix("u_world", worldMatrix);
		meshShader.setUniformf("u_time", currentTime);
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
	}

}
