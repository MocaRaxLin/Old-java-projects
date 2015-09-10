package object.bodyComponent;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Head {
	private GL2 gl;
	private GLU glu;
	private float cylinderRadius;
	private float cylinderHeight;
	final int cylinderSlices = 32;
	final int cylinderStacks = 32;

	public Head(GL2 ingl, GLU inglu, float size) {
		gl = ingl;
		glu = inglu;
		cylinderRadius = 2*size;
		cylinderHeight = 1*size;
		
		GLUquadric body = glu.gluNewQuadric();// 創造2次元物件 令其為body
	    glu.gluQuadricTexture(body, false);
		glu.gluQuadricDrawStyle(body, GLU.GLU_FILL);
		glu.gluQuadricNormals(body, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(body, GLU.GLU_OUTSIDE);

		// bottom disk
		gl.glRotatef(90f, 0, 1, 0);
		gl.glTranslatef(0, 0, -cylinderHeight*size / 2);
		glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);

		// draw cylinder body from bottom
		glu.gluCylinder(body, cylinderRadius, cylinderRadius, cylinderHeight,
				cylinderSlices, cylinderStacks);

		// top disk
		gl.glTranslatef(0, 0, cylinderHeight*size);
		glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);

		// 結束對二次元物件body的glu綁定
		glu.gluDeleteQuadric(body);

		// back to beginning drawing position and slope
		gl.glTranslatef(0, 0, -cylinderHeight*size / 2);
		gl.glRotatef(-90f, 0, 1, 0);
	}

}
