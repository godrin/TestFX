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
	private Screen screenOne = new Screen();
	@Override
	public void create() {
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"));
		float c1 = Color.toFloatBits(255, 0, 0, 255);
		float c2 = Color.toFloatBits(255, 0, 0, 255);
		float c3 = Color.toFloatBits(0, 0, 255, 255);

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0.5f, -0.5f, 0, c2,
				0, 0.5f, 0, c3 });
		meshShader = createShader(Gdx.graphics, "lines");

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
