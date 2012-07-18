package org.devtal.effect;

import javax.script.SimpleScriptContext;

import org.devtal.SimpleShader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BlurRenderer {
	private static final int BLUR_WIDTH = 20;
	private final static int TEXTURE_SIZE = 512;

	private Mesh copyMesh;
	private FrameBuffer initialFrameBuffer;
	private FrameBuffer blurXBuffer;
	private FrameBuffer blurYBuffer;
	private ShaderProgram copyShader;
	private ShaderProgram blurXShader;
	private ShaderProgram blurYShader;
	
	public void prepare(RenderCallback callback,float currentTime) {
		create();
		
		initialFrameBuffer.begin();
		Gdx.graphics.getGL20().glViewport(0, 0, initialFrameBuffer.getWidth(),
				initialFrameBuffer.getHeight());
		Gdx.graphics.getGL20().glClearColor(0f, 0f, 0f, 0);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.getGL20().glDisable(GL20.GL_TEXTURE_2D);
		Gdx.graphics.getGL20().glLineWidth(4);

		callback.render(currentTime);
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

	}
	
	public void create() {
		if(copyShader!=null)
			return;
		copyShader = SimpleShader.createShader(Gdx.graphics, "copy");
		blurYShader = SimpleShader.createShader(Gdx.graphics, "blur_x");
		blurXShader = SimpleShader.createShader(Gdx.graphics, "blur_y");
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

	public void renderBlurToScreen() {
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

}
