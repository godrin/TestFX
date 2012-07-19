package org.devtal.geometrics;

import org.devtal.effect.Drawable;

import com.badlogic.gdx.math.Matrix4;

public class DrawableTransformation implements Drawable {

	private Drawable drawable;

	public DrawableTransformation(Drawable pDrawable) {
		drawable = pDrawable;
	}

	@Override
	public void render(float currentTime, Matrix4 worldMatrix) {
		drawable.render(currentTime, worldMatrix);
	}

	@Override
	public void create() {
		drawable.create();
	}

}
