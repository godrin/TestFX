package org.devtal.drawables;

import org.devtal.SimpleShader;
import org.devtal.effect.RenderCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Ship implements RenderCallback {
	private ShaderProgram meshShader;
	private Mesh mesh;

	@Override
	public void render(float currentTime) {
		meshShader.begin();

		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();

	}

	public void create() {

		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));

		float blue = Color.toFloatBits(0, 0, 200, 255);
		float yellow = Color.toFloatBits(255, 250, 0, 255);

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, blue, 0,//
				0.5f, -0.5f, 0, yellow, 3.14f / 2, //
				0, 0.5f, 0, yellow, 3.14f //
		});

		meshShader = SimpleShader.createShader(Gdx.graphics, "colored");

	}

}
