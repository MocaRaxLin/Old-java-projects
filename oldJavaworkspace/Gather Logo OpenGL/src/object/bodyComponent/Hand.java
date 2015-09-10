package object.bodyComponent;

import javax.media.opengl.GL2;

public class Hand {
	private GL2 gl;
	private double xl;
	private double yl;
	private double zl;
	public Hand(GL2 ingl, float size) {
		gl = ingl;
		xl = 0.8;
		yl = 1.5;
		zl = 2;
		
		gl.glScaled(xl*size, yl*size, zl*size);
		new unitSquardCube(gl);
		gl.glScaled(1/(xl*size), 1/(yl*size), 1/(zl*size));
	}

}
