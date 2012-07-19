package org.devtal.geometrics;

import com.badlogic.gdx.math.Matrix4;

public class SimpleTransform {
	private Matrix4 matrix;
	private static Matrix4 tmp = new Matrix4();

	public SimpleTransform() {
		matrix = new Matrix4();
	}

	public SimpleTransform(Matrix4 matrix4) {
		matrix = matrix4;
	}

	public SimpleTransform clone() {
		return new SimpleTransform(new Matrix4(matrix));
	}

	public SimpleTransform scale(float s) {
		scale(s, s, s);
		return this;
	}

	private SimpleTransform scale(float x, float y, float z) {
		tmp.setToScaling(x, y, z);
		matrix.mul(tmp);
		return this;
	}

	private SimpleTransform rotate(float r) {
		return null;
	}
}
