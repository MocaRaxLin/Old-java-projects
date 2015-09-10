package falltest;

import java.awt.BorderLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class MyJoglCanvasStep2 extends GLCanvas implements GLEventListener,MouseWheelListener {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;
    
    //Texture變數
    private Texture earthTexture;
    private Texture solarPanelTexture;
    
    // The angle of the satellite orbit (0..359)
    private float satelliteAngle = 0;
    //初始視角
    float viewAngle = 45f;
    //
    double camaraAngle = 0;
    double camaraAngle2 = 0;
    /**
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public MyJoglCanvasStep2(GLCapabilities capabilities, int width, int height) {
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
        animator = new FPSAnimator(this, fps);//(GLEventListener,每秒重繪fps次圖)
        animator.start();//重複執行this.display()
        this.addMouseWheelListener(this);
     // Load earth texture.
        try {
            InputStream stream = getClass().getResourceAsStream("/texture/earthmap.png");//url一定要按照此規則打
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
            earthTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
     // Load the solar panel texture.
        try {
            InputStream stream = getClass().getResourceAsStream("/texture/solar_panel_256x32.png");
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(),stream, false, "png");
            solarPanelTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(2);
        }
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
        gl.glEnd();//與gl.glBegin(GL.GL_LINES)綁定
        
     // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {(float) (30*(Math.cos(camaraAngle2-=0.003))), (float) (30*(Math.sin(camaraAngle2-=0.003))), 0, SHINE_ALL_DIRECTIONS};
                            //(x,y,z, )
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};//環境色光
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};//視覺色光
                                     //(R,G,B,full opacity)
        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
        
        
        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);//lighting all lights

        // Set material properties.
        //to specify how the material reacts to the light across the RGB spectrum
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 0f);
     
     // Apply earthTexture.
        earthTexture.enable(gl);//輸入gl作為綁定的位置
        earthTexture.bind(gl);
        
     // Draw sphere (possible styles: FILL, LINE, POINT).
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        GLUquadric earth = glu.gluNewQuadric();
        //apply earthTexture
        glu.gluQuadricTexture(earth, true); 
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 12f;
        final int slices = 64;
        final int stacks = 64;//幾層圓組成球
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);
        
     // Save old state.
        //gl.glPushMatrix();//此指令會造成空間中的物體停止並且固定在原位 導致error
                            //有動畫不可輸入此指令

        // Compute satellite position.
        satelliteAngle = (satelliteAngle + 1f) % 360f;//以此避免double satelliteAngle無限上增
        final float distance = 20.000f;
        final float x = (float) Math.cos(Math.toRadians(satelliteAngle)) * distance;
        final float y = (float) Math.sin(Math.toRadians(satelliteAngle)) * distance;
        final float z = 0;
        gl.glTranslatef(x, y, z);
        //類似向量，我們將gl繪圖的點做向量(x,y,z)移動
        gl.glRotatef(satelliteAngle, 0, 0, 1);
        gl.glRotatef(45f, 1, 0, 0);
        //angle指定对象旋转的角度，X，Y，Z三个参数共同决定旋转轴的方向。
        //eg.我們面相向空間中的正x軸，使"坐標軸"逆時針轉45度即是上述code
        
        // Set silver color, and disable texturing.
        gl.glDisable(GL.GL_TEXTURE_2D);//取消常駐的texture功能
        float[] ambiColor = {0.3f, 0.3f, 0.3f, 1f};
        float[] specColor = {0.8f, 0.8f, 0.8f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, ambiColor, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, specColor, 0);
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 90f);

        // Draw satellite body.
        final float cylinderRadius = 1f;
        final float cylinderHeight = 6f;
        final int cylinderSlices = 16;
        final int cylinderStacks = 16;
        GLUquadric body = glu.gluNewQuadric();//創造2次元物件 令其為body
        glu.gluQuadricTexture(body, false);
        glu.gluQuadricDrawStyle(body, GLU.GLU_FILL);
        glu.gluQuadricNormals(body, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(body, GLU.GLU_OUTSIDE);
        gl.glTranslatef(0, 0, -cylinderHeight / 2);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluCylinder(body, cylinderRadius, cylinderRadius, cylinderHeight, cylinderSlices, cylinderStacks);
        gl.glTranslatef(0, 0, cylinderHeight);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluDeleteQuadric(body);//結束對二次元物件body的glu綁定
        gl.glTranslatef(0, 0, -cylinderHeight / 2);
        
     // Set white color, and enable texturing.
//        float[] rgba = {1f, 1f, 1f};
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 0f);

        // Draw solar panels.
        //scalef 當前圖形各別延x,y,z三軸放大
        gl.glScalef( 6f,0.7f, 0.1f);
        //以gl當下的繪圖點為中心 畫出一個長方體(|x|,|y|,|z|)
        solarPanelTexture.bind(gl);
        gl.glBegin(GL2.GL_TRIANGLES);
        final float[] frontUL = {-1.0f, -1.0f, 1.0f};
        final float[] frontUR = {1.0f, -1.0f, 1.0f};
        final float[] frontLR = {1.0f, 1.0f, 1.0f};
        final float[] frontLL = {-1.0f, 1.0f, 1.0f};
        final float[] backUL = {-1.0f, -1.0f, -1.0f};
        final float[] backLL = {-1.0f, 1.0f, -1.0f};
        final float[] backLR = {1.0f, 1.0f, -1.0f};
        final float[] backUR = {1.0f, -1.0f, -1.0f};
        // Front Face.
        
        //平面法向量，用來決定反映光線
        //gl.glNormal3f(0.0f, 0.0f, 1.0f);
        
        gl.glTexCoord2f(0.0f, 0.0f);//texture左下
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);//frontUR
        gl.glTexCoord2f(1.0f, 0.0f);//texture右下
        gl.glVertex3f( 1.0f, -1.0f, 1.0f);//frontUL
        gl.glTexCoord2f(1.0f, 1.0f);//texture右上
        gl.glVertex3f( 1.0f, 1.0f, 1.0f);//frontLL
        gl.glTexCoord2f(0.0f, 1.0f);//texture左上
        gl.glVertex3f( -1.0f, 1.0f, 1.0f);//frontLR
        
        // Back Face.
        //gl.glNormal3f(0.0f, 0.0f, -1.0f);
        
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);//backUL
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);//backUR
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f( 1.0f, 1.0f, -1.0f);//backLR
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f( -1.0f, 1.0f, -1.0f);//backLL
        gl.glEnd();

        // Restore old state.
        //gl.glPopMatrix(); //類似stack
    }

    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        
        float fAspect;  
        fAspect =(float) width / height;
        /*
        // 防止为零  
        if (height == 0) {  
            height = 1;  
        }  
  
        //视口设置为窗口尺寸  
        //gl.glViewport(0, 0, width, height);  
  
          
  
        // Reset projection matrix stack  
        gl.glMatrixMode(GL2.GL_PROJECTION);  
        gl.glLoadIdentity();  
  */
  
  
        //透视投影 眼角度,比例,近可视,远可视  
        glu.gluPerspective(53.0f, fAspect, 1.0, 400.0);  
        
        // 重置模型观察矩阵堆栈  
       /* gl.glMatrixMode(GL2.GL_MODELVIEW);  
        gl.glLoadIdentity(); */ 
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     * @param distance The distance from the screen.
     */
    private void setCamera(GL2 gl, GLU glu) {
        // Change to projection matrix.
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        
        glu.gluPerspective(viewAngle, widthHeightRatio, 1, 1000);
        //(眼睛睁开的角度,实际窗口的纵横比,最近截面,最遠截面)
        glu.gluLookAt(45*(Math.cos(camaraAngle+=0.005)), 45*(Math.sin(camaraAngle+=0.005)), 15, 0, 0, 0, 0, 0, 1);
        
        /*public void gluLookAt(double eyeX, double eyeY, double eyeZ,//表示眼睛的坐标
                                double centerX, double centerY, double centerZ,//眼睛看向哪个点
                                double upX, double upY, double upZ) ;//向上向量   0,0,1以Z軸為上 因為 只知eye與center攝影機仍可旋轉所以再定義一個向量
                                //也就是說，这个up向量把相机的上方向固定在这个方向和视方向形成的平面上*/
        // Change back to model view matrix.
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Starts the JOGL mini demo.
     * 
     * @param args Command line args.
     */
    public final static void main(String[] args) {
        GLCapabilities capabilities = createGLCapabilities();
        MyJoglCanvasStep2 canvas = new MyJoglCanvasStep2(capabilities, 1000, 600);
        JFrame frame = new JFrame("Mini JOGL Demo");
        
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
		if(notice>0){
			viewAngle*=1.2;
			if(viewAngle>=180)
				viewAngle = 179;
		}
		if(notice<0){
			viewAngle*=0.83333;//angle變小視覺上物體變大
			if(viewAngle<=0)
				viewAngle = 1;
		}
			
	}
}