package org.devtal.drawables;

import org.devtal.SimpleShader;
import org.devtal.effect.Drawable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class Triangle implements Drawable {
	private ShaderProgram meshShader;
	private Mesh mesh;

	@Override
	public void render(float currentTime, Matrix4 world) {
		meshShader.begin();
		meshShader.setUniformf("u_time", currentTime);

		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();

	}

	public void create() {

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

}
