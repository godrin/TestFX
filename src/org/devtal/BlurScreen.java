package org.devtal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BlurScreen extends BasicScreen {
	private static final int BLUR_WIDTH = 20;

	private final static int TEXTURE_SIZE = 512;

	Mesh mesh;
	Mesh lineMesh;
	Mesh copyMesh;
	private ShaderProgram meshShader;
	private float currentTime = 0;
	FrameBuffer initialFrameBuffer;
	FrameBuffer blurXBuffer;
	FrameBuffer blurYBuffer;
	private ShaderProgram copyShader;
	private ShaderProgram blurXShader;
	private ShaderProgram blurYShader;

	@Override
	public void create() {

		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));
		lineMesh = new Mesh(true, 6, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.Generic, 1, "a_delta"));
		float c1 = Color.toFloatBits(255, 50, 0, 255);
		float c2 = Color.toFloatBits(255, 50, 0, 255);
		float c3 = Color.toFloatBits(255, 250, 0, 255);

		lineMesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0,//
				0.5f, -0.5f, 0, c2, 3.14f / 2, //
				0.5f, -0.5f, 0, c2, 3.14f / 2,//
				0, 0.5f, 0, c3, 3.14f, //
				0, 0.5f, 0, c3, 3.14f, //
				-0.5f, -0.5f, 0, c1, 0 });

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0,//
				0.5f, -0.5f, 0, c2, 3.14f / 2, //
				0, 0.5f, 0, c3, 3.14f //
		});

		meshShader = createShader(Gdx.graphics, "lines");
		copyShader = createShader(Gdx.graphics, "copy");
		blurYShader = createShader(Gdx.graphics, "blur_x");
		blurXShader = createShader(Gdx.graphics, "blur_y");
		initialFrameBuffer = new FrameBuffer(Format.RGBA8888, TEXTURE_SIZE,
				TEXTURE_SIZE, false);
		blurXBuffer = new FrameBuffer(Format.RGBA8888, TEXTURE_SIZE,
				TEXTURE_SIZE, false);
		blurYBuffer = new FrameBuffer(Format.RGBA8888, TEXTURE_SIZE,
				TEXTURE_SIZE, false);

		copyMesh = new Mesh(true, 6, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.TextureCoordinates, 2,
				"a_texCoords"));
		int tw = 1;
		int th = 1;
		copyMesh.setVertices(new float[] { -1, -1, 0, 0, 0,//
				1, -1, 0, tw, 0, //
				1, 1, 0, tw, th, //
				1, 1, 0, tw, th,//
				-1, 1, 0, 0, th, //
				-1, -1, 0, 0, 0 });

	}

	@Override
	public void render(float accum) {
		currentTime += accum;
		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		initialFrameBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, initialFrameBuffer.getWidth(),
				initialFrameBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.getGL20().glDisable(GL20.GL_TEXTURE_2D);
		Gdx.graphics.getGL20().glLineWidth(4);

		meshShader.begin();
		// meshShader.setUniformi("u_texture", 0);
		// meshShader.setUniformMatrix("u_world", worldMatrix);
		meshShader.setUniformf("u_time", currentTime);

		lineMesh.render(meshShader, GL20.GL_LINES);
		meshShader.end();
		initialFrameBuffer.end();

		// blur x
		blurXBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, blurXBuffer.getWidth(),
				blurXBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);

		initialFrameBuffer.getColorBufferTexture().bind();
		blurXShader.begin();
		blurXShader.setUniformi("u_texture", 0);
		blurXShader.setUniformi("blurWidth", BLUR_WIDTH);
		blurXShader.setUniformf("textureWidth", TEXTURE_SIZE);
		blurXShader.setUniformf("sigma", 9);

		copyMesh.render(blurXShader, GL20.GL_TRIANGLES);
		blurXShader.end();

		blurXBuffer.end();

		// blur y
		blurYBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, blurYBuffer.getWidth(),
				blurYBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);

		blurXBuffer.getColorBufferTexture().bind();
		blurYShader.begin();
		blurYShader.setUniformi("u_texture", 0);
		blurYShader.setUniformi("blurWidth", BLUR_WIDTH);
		blurYShader.setUniformf("textureWidth", TEXTURE_SIZE);
		blurYShader.setUniformf("sigma", 5);

		copyMesh.render(blurYShader, GL20.GL_TRIANGLES);
		blurYShader.end();

		blurYBuffer.end();

		// to screen

		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (true) {
			Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);

			if (true) {
				Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
				Gdx.graphics.getGL20().glBlendFunc(GL20.GL_SRC_ALPHA,
						GL20.GL_ONE_MINUS_SRC_ALPHA);
			}
			blurYBuffer.getColorBufferTexture().bind();
			copyShader.begin();
			copyShader.setUniformi("u_texture", 0);
			copyMesh.render(copyShader, GL20.GL_TRIANGLES);
			copyShader.end();
		}
		if (false) {
			Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
			Gdx.graphics.getGL20().glBlendFunc(GL20.GL_ONE_MINUS_SRC_ALPHA,
					GL20.GL_DST_ALPHA);

			initialFrameBuffer.getColorBufferTexture().bind();
			copyShader.begin();
			copyShader.setUniformi("u_texture", 0);
			copyMesh.render(copyShader, GL20.GL_TRIANGLES);
			copyShader.end();
			Gdx.graphics.getGL20().glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);
		} else {

		}
		Gdx.graphics.getGL20().glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);
		if (true) {
			// draw triangle

			Gdx.graphics.getGL20().glDisable(GL20.GL_TEXTURE_2D);

			meshShader.begin();
			// meshShader.setUniformi("u_texture", 0);
			// meshShader.setUniformMatrix("u_world", worldMatrix);
			meshShader.setUniformf("u_time", currentTime);

			mesh.render(meshShader, GL20.GL_TRIANGLES);
			meshShader.end();
		}

	}
}
