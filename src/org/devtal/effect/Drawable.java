package org.devtal.effect;

import com.badlogic.gdx.math.Matrix4;

public interface Drawable {
	void render(float currentTime, Matrix4 worldMatrix);

	void create();
}
