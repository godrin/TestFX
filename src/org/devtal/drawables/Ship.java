package org.devtal.drawables;

import java.util.Arrays;
import java.util.List;

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

public class Ship implements Drawable {
	private ShaderProgram meshShader;
	private Mesh mesh;

	class MeshPoint {
		float x, y, z, c, d;

		MeshPoint(float x, float y, float c) {
			this.x = x;
			this.y = y;
			this.z = 0;
			this.c = c;
			this.d = 0;
		}
	}

	@Override
	public void render(float currentTime, Matrix4 world) {
		meshShader.begin();
		meshShader.setUniformMatrix("world", world);
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();

	}

	public void create() {

		float blue = Color.toFloatBits(0, 0, 200, 255);
		float yellow = Color.toFloatBits(255, 250, 0, 255);

		MeshPoint mmu = new MeshPoint(0, -0.1f, yellow);
		MeshPoint lu = new MeshPoint(-0.2f, -0.2f, blue);
		MeshPoint mu = new MeshPoint(0, -0.4f, blue);
		MeshPoint r = new MeshPoint(0.5f, 0f, blue);
		MeshPoint mb = new MeshPoint(0, 0.4f, blue);
		MeshPoint lb = new MeshPoint(-0.2f, 0.2f, blue);
		MeshPoint mmb = new MeshPoint(0, 0.1f, yellow);

		List<MeshPoint> ms = Arrays.asList(new MeshPoint[] { mmu, lu, mu,//
				mmb, lb, mb,//
				mmb, mb, r, //
				mmu, mu, r, //
				mmu, mmb, r });

		mesh = new Mesh(true, ms.size(), 0, new VertexAttribute(Usage.Position,
				3, "a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));

		int i = 0;
		float[] buf = new float[ms.size() * 5];
		for (MeshPoint p : ms) {
			buf[i++] = p.x;
			buf[i++] = p.y;
			buf[i++] = p.z;
			buf[i++] = p.c;
			buf[i++] = p.d;
		}
		mesh.setVertices(buf);

		meshShader = SimpleShader.createShader(Gdx.graphics, "colored");

	}

}
