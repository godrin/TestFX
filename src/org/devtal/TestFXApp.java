package org.devtal;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class TestFXApp implements ApplicationListener {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private boolean started = false;
	private float accum = 0;
	boolean stop = false;
	private boolean first = true;

	private List<BasicScreen> screens = Arrays.asList(new BasicScreen[] {
			new Screen(), new DancingLines(),new BlurScreen() });
	private Integer screenId = 0;

	public void create() {
		running = true;
		for (BasicScreen screen : screens)
			screen.create();
	}

	public void pause() {
		// FIXME
		running = false;
	}

	public void resume() {
		running = true;
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		screenId = (int) (accum / 10) % screens.size();
		screens.get(screenId).render(Gdx.graphics.getDeltaTime());

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

	}
}
