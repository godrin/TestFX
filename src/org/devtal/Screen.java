package org.devtal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class Screen extends BasicScreen {

	FrameBuffer frameBuffer;
	Mesh mesh;
	ShaderProgram meshShader;
	Texture texture;
	SpriteBatch spriteBatch;
	float currentTime = 0;
	float speed = 0;
	Matrix4 worldMatrix;
	boolean rotate = false;

	public void render(float accum) {
		speed += accum;
		currentTime += speed;
		if (rotate)
			worldMatrix.setToRotation(0, 0, 1, currentTime * 9);
		else
			worldMatrix.setToRotation(0, 0, 1, 0);
		frameBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, frameBuffer.getWidth(),
				frameBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		// Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.graphics.getGL20().glEnable(0x0B41);// GL_POLYGON_SMOOTH

		Gdx.graphics.getGL20().glHint(GL10.GL_POLYGON_SMOOTH_HINT,
				GL20.GL_NICEST);
		texture.bind();
		meshShader.begin();
		meshShader.setUniformi("u_texture", 0);
		meshShader.setUniformMatrix("u_world", worldMatrix);
		meshShader.setUniformf("u_time", currentTime);
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
		frameBuffer.end();

		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		spriteBatch.begin();
		spriteBatch.draw(frameBuffer.getColorBufferTexture(), 300, 200, 256, 256,
				0, 0, frameBuffer.getColorBufferTexture().getWidth(),
				frameBuffer.getColorBufferTexture().getHeight(), false, true);
		spriteBatch.end();
		if (Gdx.input.justTouched()) {
			if (!rotate)
				rotate = true;
			else
				rotate = false;
		}
	}

	public void create() {
		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.TextureCoordinates, 2,
				"a_texCoords"));
		float c1 = Color.toFloatBits(255, 0, 0, 255);
		float c2 = Color.toFloatBits(255, 0, 0, 255);
		float c3 = Color.toFloatBits(0, 0, 255, 255);

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0, 0, //
				0.5f, -0.5f, 0, c2, 1, 0, //
				0, 0.5f, 0, c3, 0.5f, 1 });

		texture = new Texture(Gdx.files.internal("data/bg.png"));
		texture.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);

		spriteBatch = new SpriteBatch();
		frameBuffer = new FrameBuffer(Format.RGB565, 6, 6, false);
		meshShader = SimpleShader.createShader(Gdx.graphics, "simple");
		worldMatrix = new Matrix4();

	}

}
