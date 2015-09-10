import java.awt.BorderLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Stack;

import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

import object.People;
import object.xyzAxis;

public class Stage extends GLCanvas implements GLEventListener,
		MouseWheelListener {

	/** Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The GL unit (helper class). */
	private GLU glu;

	/** The frames per second setting. */
	private int fps = 60;

	/** The OpenGL animator. */
	private FPSAnimator animator;

	// 初始視角
	float viewAngle = 45f;
	//
	double camaraAngle = 0;
	double camaraAngle2 = 0;

	// declare variables of people's data
	private Stack<PersonalDetail> peopleDetail;
	private PersonalDetail personalDetail;
	private float currentX;
	private float currentY;
	private int faceSide;
	private int amountOfPeople;

	// declare variables of connection lines
	private float length;
	private float dyn_length;
	private boolean growable;

	// private variable of stage
	private double rootedThree;
	private float stageSize;
	private boolean firstSence;

	/**
	 * A new mini starter.
	 * 
	 * @param capabilities
	 *            The GL capabilities.
	 * @param width
	 *            The window width.
	 * @param height
	 *            The window height.
	 */
	public Stage(GLCapabilities capabilities, int width, int height) {
		addGLEventListener(this);
	}

	/**
	 * @return Some standard GL capabilities (with alpha).
	 */
	private static GLCapabilities createGLCapabilities() {
		GLCapabilities capabilities = new GLCapabilities(null);
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);
		return capabilities;
	}

	/**
	 * Sets up the screen.
	 * 
	 * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
	 */
	public void init(GLAutoDrawable drawable) {

		drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
		final GL2 gl = drawable.getGL().getGL2();
		// Enable z- (depth) buffer for hidden surface removal.
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		// Enable smooth shading.
		gl.glShadeModel(GL2.GL_SMOOTH);
		// Define "clear" color.
		gl.glClearColor(0f, 0f, 0f, 0f);
		// We want a nice perspective.
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		// Create GLU.
		glu = new GLU();
		// Start animator.
		animator = new FPSAnimator(this, fps);// (GLEventListener,每秒重繪fps次圖)
		animator.start();// 重複執行this.display()
		this.addMouseWheelListener(this);

		// initialize stage variables
		stageSize = 20;
		rootedThree = 1.732;
		firstSence = true;

		// initialize people's data in a triangle way
		peopleDetail = new Stack<PersonalDetail>();
		for (int j = 0; j < 1; j++) {
			for (int i = 0; i < 2; i++) {
				currentX = i;
				currentY = j;
				faceSide = (int) (Math.random() * 5);
				personalDetail = new PersonalDetail(currentX, currentY, 0,
						faceSide, stageSize);
				peopleDetail.push(personalDetail);
			}
		}
		amountOfPeople = peopleDetail.size();

		// declare variables of connection lines
		length = 10;
		dyn_length = 0;
		growable = true;

	}

	/**
	 * The only method that you should implement by yourself.
	 * 
	 * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
	 */
	public void display(GLAutoDrawable drawable) {

		final GL2 gl = drawable.getGL().getGL2();

		// Clear screen.
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// Set camera.
		setCamera(gl, glu);

		// first screen
		if (firstSence) {
			// XYZ正坐標軸
			new xyzAxis(gl);

			// draw each person
			for (int i = 0; i < amountOfPeople; i++) {

				currentX = peopleDetail.get(i).pos_x;
				currentY = peopleDetail.get(i).pos_y;

				// draw people
				faceSide = peopleDetail.get(i).face_on;
				new People(gl, glu, currentX, currentY, 0, faceSide);

			}
			firstSence = false;
		}

		// draw each line
		for (int i = 0; i < amountOfPeople; i++) {

			currentX = peopleDetail.get(i).pos_x;
			currentY = peopleDetail.get(i).pos_y;

			// draw dynamic connection lines
			if (growable) {
				dyn_length += 0.5;
			} else {
				dyn_length -= 0.5;
			}
			if (dyn_length == length) {
				growable = false;
			}
			if (dyn_length == 0) {
				growable = true;
			}
			new ConnectionLine(gl, currentX, currentY, 0, dyn_length);
		}
	}

	/**
	 * Resizes the screen.
	 * 
	 * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
	 *      int, int, int, int)
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);

		float fAspect;
		fAspect = (float) width / height;
		/*
		 * // 防止为零 if (height == 0) { height = 1; }
		 * 
		 * //视口设置为窗口尺寸 //gl.glViewport(0, 0, width, height);
		 * 
		 * 
		 * 
		 * // Reset projection matrix stack gl.glMatrixMode(GL2.GL_PROJECTION);
		 * gl.glLoadIdentity();
		 */

		// 透视投影 眼角度,比例,近可视,远可视
		glu.gluPerspective(53.0f, fAspect, 1.0, 400.0);

		// 重置模型观察矩阵堆栈
		/*
		 * gl.glMatrixMode(GL2.GL_MODELVIEW); gl.glLoadIdentity();
		 */
	}

	/**
	 * Changing devices is not supported.
	 * 
	 * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
	 *      boolean, boolean)
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		throw new UnsupportedOperationException(
				"Changing display is not supported.");
	}

	/**
	 * @param gl
	 *            The GL context.
	 * @param glu
	 *            The GL unit.
	 * @param distance
	 *            The distance from the screen.
	 */
	private void setCamera(GL2 gl, GLU glu) {
		// Change to projection matrix.
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Perspective.
		float widthHeightRatio = (float) getWidth() / (float) getHeight();

		glu.gluPerspective(viewAngle, widthHeightRatio, 1, 1000);
		// (眼睛睁开的角度,实际窗口的纵横比,最近截面,最遠截面)
		glu.gluLookAt(60, 60, 60, 0, 0, 0, 0, 0, 1);

		/*
		 * public void gluLookAt(double eyeX, double eyeY, double eyeZ,//表示眼睛的坐标
		 * double centerX, double centerY, double centerZ,//眼睛看向哪个点 double upX,
		 * double upY, double upZ) ;//向上向量 0,0,1以Z軸為上 因為
		 * 只知eye與center攝影機仍可旋轉所以再定義一個向量 //也就是說，这个up向量把相机的上方向固定在这个方向和视方向形成的平面上
		 */
		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	/**
	 * Starts the JOGL mini demo.
	 * 
	 * @param args
	 *            Command line args.
	 */
	public final static void main(String[] args) {
		GLCapabilities capabilities = createGLCapabilities();
		Stage canvas = new Stage(capabilities, 1000, 600);
		JFrame frame = new JFrame("Group Logo Demo");

		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas.requestFocus();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notice = e.getWheelRotation();
		if (notice > 0) {
			viewAngle *= 1.2;
			if (viewAngle >= 180)
				viewAngle = 179;
		}
		if (notice < 0) {
			viewAngle *= 0.83333;// angle變小視覺上物體變大
			if (viewAngle <= 0)
				viewAngle = 1;
		}

	}
}