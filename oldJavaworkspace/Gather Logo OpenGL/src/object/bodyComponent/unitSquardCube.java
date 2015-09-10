package object.bodyComponent;

import javax.media.opengl.GL2;

public class unitSquardCube {
	private GL2 gl;
	public unitSquardCube(GL2 ingl) {
		gl = ingl;
		
		//top
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(-0.5, -0.5, 0.5);
		gl.glVertex3d(0.5, -0.5, 0.5);
		gl.glVertex3d(0.5, 0.5, 0.5);
		gl.glVertex3d(-0.5, 0.5, 0.5);
		gl.glEnd();
		
		//bottom
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(-0.5, -0.5, -0.5);
		gl.glVertex3d(0.5, -0.5, -0.5);
		gl.glVertex3d(0.5, 0.5, -0.5);
		gl.glVertex3d(-0.5, 0.5, -0.5);
		gl.glEnd();
		
		//right
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(-0.5, 0.5, -0.5);
		gl.glVertex3d(0.5, 0.5, -0.5);
		gl.glVertex3d(0.5, 0.5, 0.5);
		gl.glVertex3d(-0.5, 0.5, 0.5);
		gl.glEnd();
		
		//left
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(-0.5, -0.5, -0.5);
		gl.glVertex3d(0.5, -0.5, -0.5);
		gl.glVertex3d(0.5, -0.5, 0.5);
		gl.glVertex3d(-0.5, -0.5, 0.5);
		gl.glEnd();
		
		//front
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(0.5, -0.5, -0.5);
		gl.glVertex3d(0.5, 0.5, -0.5);
		gl.glVertex3d(0.5, 0.5, 0.5);
		gl.glVertex3d(0.5, -0.5, 0.5);
		gl.glEnd();
		
		//back
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3d(-0.5, -0.5, -0.5);
		gl.glVertex3d(-0.5, 0.5, -0.5);
		gl.glVertex3d(-0.5, 0.5, 0.5);
		gl.glVertex3d(-0.5, -0.5, 0.5);
		gl.glEnd();
	}

}
