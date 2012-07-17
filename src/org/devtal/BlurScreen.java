package org.devtal;

import org.devtal.effect.BlurRenderer;
import org.devtal.effect.RenderCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BlurScreen extends BasicScreen {

	private Mesh mesh;
	private ShaderProgram meshShader;
	private float currentTime = 0;
	private BlurRenderer blurRenderer;
	private RenderCallback sceneRenderer=new RenderCallback() {
		public void render() {
			meshShader.begin();
			// meshShader.setUniformi("u_texture", 0);
			// meshShader.setUniformMatrix("u_world", worldMatrix);
			meshShader.setUniformf("u_time", currentTime);

			mesh.render(meshShader, GL20.GL_TRIANGLES);
			meshShader.end();
		}
	};

	@Override
	public void create() {
		blurRenderer = new BlurRenderer();

		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));

		float c1 = Color.toFloatBits(255, 50, 0, 255);
		float c2 = Color.toFloatBits(255, 50, 0, 255);
		float c3 = Color.toFloatBits(255, 250, 0, 255);


		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0,//
				0.5f, -0.5f, 0, c2, 3.14f / 2, //
				0, 0.5f, 0, c3, 3.14f //
		});

		meshShader = SimpleShader.createShader(Gdx.graphics, "lines");
	}

	@Override
	public void render(float accum) {
		currentTime += accum;
		blurRenderer.prepare(sceneRenderer);

		// to screen

		clearScreen();

		blurRenderer.renderBlur();
		Gdx.graphics.getGL20().glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);
		if (true) {
			// draw triangle

			Gdx.graphics.getGL20().glDisable(GL20.GL_TEXTURE_2D);

			sceneRenderer.render();
		}

	}

	private void clearScreen() {
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
