package object.bodyComponent;

import javax.media.opengl.GL2;

public class BentArm {

	public BentArm(GL2 gl, float size) {
		
		// straight part
		gl.glTranslated(0.0*size, 0.0*size, -2.0*size);
		gl.glScaled(1.0*size, 2.0*size, 4.0*size);
		new unitSquardCube(gl);
		gl.glScaled(1/(1.0*size), 1/(2.0*size), 1/(4.0*size));
		
		// bent part
		gl.glTranslated(2.0*size, 0.0*size, -2.0*size);
		gl.glScaled(4.0*size, 2.0*size, 1.0*size);
		new unitSquardCube(gl);
		gl.glScaled(1/(4.0*size), 1/(2.0*size), 1/(1.0*size));
		
		// back to start position
		gl.glTranslated(-2.0*size, 0.0*size, 4.0*size);
	}

}
