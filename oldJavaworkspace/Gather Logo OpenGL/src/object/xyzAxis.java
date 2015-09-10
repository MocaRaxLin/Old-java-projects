package object;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class xyzAxis {
	public xyzAxis(GL2 gl) {
		gl.glBegin(GL.GL_LINES);
		// x - red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3d(100, 0, 0);
		gl.glVertex3d(0, 0, 0);
		// y - green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glVertex3d(0, 100, 0);
		gl.glVertex3d(0, 0, 0);
		// z - blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glVertex3d(0, 0, 100);
		gl.glVertex3d(0, 0, 0);

		gl.glEnd(); // »Pgl.glBegin(GL.GL_LINES)¸j©w
		gl.glColor3f(1.0f, 1.0f, 1.0f);
	}
}
