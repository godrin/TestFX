package org.devtal;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class TestFXApp implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private boolean started = false;
	private float accum = 0;
	boolean stop = false;

	private Screen screen = new Screen();

	public void create() {
		running = true;
		screen.create();
	}

	public void pause() {
		// FIXME
		running = false;
	}

	public void resume() {
		running = true;
	}

	public void setScreen(Screen newScreen) {
		if (screen != null)
			screen.removed();

		screen = newScreen;

		if (screen != null) {
			Gdx.input.setInputProcessor(screen);
			screen.wait(this);
		}
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			accum -= 1.0f / 60.0f;
		}
		screen.render(accum);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		if (screen != null)
			screen.dispose();

	}
}
