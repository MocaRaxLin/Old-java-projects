package falltest;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class changKuoDoneTriangle extends GLCanvas implements GLEventListener {

	private GLU glu;
	private Texture texture;
    private int fps = 60;
    private FPSAnimator animator;
    double camaraAngle = 0;
    
	public changKuoDoneTriangle(GLCapabilities capabilities, int width,
			int height) {
		addGLEventListener(this);
	}

	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		setCamera(gl, glu);
		
		//XYZ正坐標軸
        gl.glBegin(GL.GL_LINES);
	    gl.glColor3f(1.0f , 0.0f , 0.0f);
	    gl.glVertex3d(80, 0, 0);
	    gl.glVertex3d(0, 0, 0);
	    gl.glColor3f(0.0f , 1.0f , 0.0f);
        gl.glVertex3d(0, 80, 0);
	    gl.glVertex3d(0, 0, 0);
	    gl.glColor3f(0.0f , 0.0f , 1.0f);
        gl.glVertex3d(0, 0, 80);
	    gl.glVertex3d(0, 0, 0);
        gl.glEnd();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		texture.enable(gl);
		texture.bind(gl);
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-5, -5, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(5, -5, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 5, 5, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f( -5, 5, 0);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f( -5, 5, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-5, -5, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 0, 0, 6);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0, 0, 6);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(5, 5, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f( -5, 5, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 0, 0, 6);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0, 0, 6);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(5, -5, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(5, 5, 0 );
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 0, 0, 6);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0, 0, 6);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-5, -5, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f( 5, -5, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 0, 0, 6);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(0, 0, 6);
		gl.glEnd();
		//gl.glPushMatrix();
	}

	private void setCamera(GL2 gl, GLU glu2) {
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		float widthHeightRatio = (float) getWidth() / (float) getHeight();
		glu.gluPerspective(45, widthHeightRatio, 1, 1000);
		glu.gluLookAt(20*(Math.cos(camaraAngle+=0.005)), 20*(Math.sin(camaraAngle+=0.005)), 10, 0, 0, 0, 0, 0, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void dispose(GLAutoDrawable arg0) {
	}

	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
        final GL2 gl = drawable.getGL().getGL2(); 
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        glu = new GLU();
        
		try {
			InputStream stream = getClass().getResourceAsStream(
					"/texture/changKuoDoneTriangle.png");// URL一定要按照此規則打
			TextureData data = TextureIO.newTextureData(GLProfile.getDefault(),
					stream, false, "png");
			texture = TextureIO.newTexture(data);
		} catch (IOException exc) {
			exc.printStackTrace();
			System.exit(1);
		}
		animator = new FPSAnimator(this, fps);
		animator.start();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
	}

	public final static void main(String args[]) {
		GLCapabilities capabilities = createGLCapabilities();
		changKuoDoneTriangle canvas = new changKuoDoneTriangle(capabilities,
				800, 500);
		JFrame frame = new JFrame("changKuoDoneCube");
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas.requestFocus();
	}

	private static GLCapabilities createGLCapabilities() {
		GLCapabilities capabilities = new GLCapabilities(null);
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);
		return capabilities;
	}
}
