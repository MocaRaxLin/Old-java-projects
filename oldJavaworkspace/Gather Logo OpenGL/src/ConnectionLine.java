import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class ConnectionLine {
	public float pos_x;
	public float pos_y;
	public float pos_z;
	public float length;

	public ConnectionLine(GL2 gl, float i, float j, float k, float r) {
		pos_x = i;
		pos_y = j;
		pos_z = k;
		length = r;

		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glScalef(length, length, length);

		for (int a = 0; a < 6; a++) {
			gl.glBegin(GL.GL_LINES);
			gl.glVertex3d(1, 0, 0);
			gl.glVertex3d(0, 0, 0);
			gl.glEnd();
			gl.glRotatef(-60, 0f, 0f, 1f);
		}
		gl.glScalef(1 / length, 1 / length, 1 / length);

	}

}
