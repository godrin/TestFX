package org.devtal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class BasicScreen implements InputProcessor {

	public BasicScreen() {
		super();
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

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void removed() {
		// TODO Auto-generated method stub

	}

	private String getProgram(String filePath) {
		return Gdx.files.internal(filePath).readString();
	}

	protected ShaderProgram createShader(Graphics graphics, String name) {

		String vertexShader, fragmentShader;
		vertexShader = getProgram("data/shaders/" + name + ".vert");
		fragmentShader = getProgram("data/shaders/" + name + ".frag");

		ShaderProgram meshShader = new ShaderProgram(vertexShader,
				fragmentShader);
		if (meshShader.isCompiled() == false) {
			System.out.println(meshShader.getManagedStatus());
			System.out.println(meshShader.getLog());
			throw new IllegalStateException(meshShader.getLog());
		}
		return meshShader;
	}

	public abstract void create();

	public abstract void render(float accum);

}