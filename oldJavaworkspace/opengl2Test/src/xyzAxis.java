import javax.media.opengl.GL;
import javax.media.opengl.GL2;
public class xyzAxis {
	public xyzAxis(GL2 gl){
        gl.glBegin(GL.GL_LINES);
	    gl.glColor3f(1.0f , 0.0f , 0.0f);
	    gl.glVertex3d(80, 0, 1);
	    gl.glVertex3d(0, 0, 1);
	    gl.glColor3f(0.0f , 1.0f , 0.0f);
        gl.glVertex3d(0, 80, 1);
	    gl.glVertex3d(0, 0, 1);
	    gl.glColor3f(0.0f , 0.0f , 1.0f);
        gl.glVertex3d(0, 0, 80);
	    gl.glVertex3d(0, 0, 1);
        gl.glEnd();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
	}
}
