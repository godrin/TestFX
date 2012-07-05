package org.devtal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Screen implements InputProcessor {

	FrameBuffer frameBuffer;
	Mesh mesh;
	ShaderProgram meshShader;
	Texture texture;
	SpriteBatch spriteBatch;

	public void render() {
		frameBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, frameBuffer.getWidth(),
				frameBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 1f, 0f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		texture.bind();
		meshShader.begin();
		meshShader.setUniformi("u_texture", 0);
		mesh.render(meshShader, GL20.GL_TRIANGLES);
		meshShader.end();
		frameBuffer.end();

		Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.graphics.getGL20().glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
		spriteBatch.draw(frameBuffer.getColorBufferTexture(), 0, 0, 256, 256,
				0, 0, frameBuffer.getColorBufferTexture().getWidth(),
				frameBuffer.getColorBufferTexture().getHeight(), false, true);
		spriteBatch.end();
	}

	public void create() {
		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3,
				"a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.TextureCoordinates, 2,
				"a_texCoords"));
		float c1 = Color.toFloatBits(255, 0, 0, 255);
		float c2 = Color.toFloatBits(255, 0, 0, 255);
		;
		float c3 = Color.toFloatBits(0, 0, 255, 255);
		;

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0, c1, 0, 0, 0.5f, -0.5f,
				0, c2, 1, 0, 0, 0.5f, 0, c3, 0.5f, 1 });

		texture = new Texture(Gdx.files.internal("data/bg.png"));

		spriteBatch = new SpriteBatch();
		frameBuffer = new FrameBuffer(Format.RGB565, 128, 128, false);
		createShader(Gdx.graphics);
	}

	private void createShader(Graphics graphics) {
		String vertexShader = "attribute vec4 a_Position;    \n"
				+ "attribute vec4 a_Color;\n" + "attribute vec2 a_texCoords;\n"
				+ "varying vec4 v_Color;" + "varying vec2 v_texCoords; \n" +

				"void main()                  \n"
				+ "{                            \n" + "   v_Color = a_Color;"
				+ "   v_texCoords = a_texCoords;\n"
				+ "   gl_Position =   a_Position;  \n"
				+ "}                            \n";
		String fragmentShader = //"precision mediump float;\n"
				 "varying vec4 v_Color;\n"
				+ "varying vec2 v_texCoords; \n"
				+ "uniform sampler2D u_texture;\n"
				+

				"void main()                                  \n"
				+ "{                                            \n"
				+ "  gl_FragColor = v_Color * texture2D(u_texture, v_texCoords);\n"
				+ "}";

		meshShader = new ShaderProgram(vertexShader, fragmentShader);
		if (meshShader.isCompiled() == false){
			System.out.println(meshShader.getManagedStatus());
			System.out.println(meshShader.getLog());
			throw new IllegalStateException(meshShader.getLog());
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void wait(TestFXApp towerGame) {
		// TODO Auto-generated method stub

	}

	public void render(float accum) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void removed() {
		// TODO Auto-generated method stub

	}

}
