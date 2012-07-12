package org.devtal;


import com.badlogic.gdx.backends.jogl.JoglApplication;


public class TestFXDesktop {
	public static void main(String[] argv) {
		new JoglApplication(new TestFXApp(), "TestFX", 1024, 1024, true);
	}
}
