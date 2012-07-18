package org.devtal;

import org.devtal.drawables.Triangle;
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

	private float currentTime = 0;
	private BlurRenderer blurRenderer;
	private Triangle triangle;

	@Override
	public void create() {
		blurRenderer = new BlurRenderer();
		triangle = new Triangle();
		triangle.create();
	}

	@Override
	public void render(float accum) {
		currentTime += accum;
		blurRenderer.prepare(triangle, currentTime);

		clearScreen();

		blurRenderer.renderBlurToScreen();
		Gdx.graphics.getGL20().glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);
		Gdx.graphics.getGL20().glDisable(GL20.GL_TEXTURE_2D);

		triangle.render(currentTime);
	}

	private void clearScreen() {
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
