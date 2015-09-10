import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;

import static javax.media.opengl.GL.*;  // GL constants
import static javax.media.opengl.GL2.*; // GL2 constants
 
/**
 * JOGL 2.0 Example 2: Rotating 3D Shapes (GLCanvas)
 */
@SuppressWarnings("serial")



public class JOGL_3D extends GLCanvas implements GLEventListener {
   // Define constants for the top-level container
   private static String TITLE = "Rotating 3D Shaps (GLCanvas)";  // window's title
   private static final int CANVAS_WIDTH = 800;  // width of the drawable
   private static final int CANVAS_HEIGHT = 640; // height of the drawable
   private static final int FPS = 60; // animator's target frames per second
   private static final int MAX_ELEMENT=10000;
   private Vertex[] vertex;
   private Vertex[] vertex_normal;
   private Vertex[] vertex_texture;
   private Face[] 	face;
   private int v_num;
   private int f_num;
   private int vn_num;
   private int vt_num;
   private int texture1_id;
   private int texture2_id;
   private Texture texture1;
   private Texture texture2;
//   
//   private double eyePos.x,eyePosY,eyePos.z;
//   private double targetPos.x,targetPosY,targetPos.z;
//   private double targetDistance;
   private Vertex targetDistance;
   private Vertex headVector;
   private Vertex eyePos,targetPos;
   
   private double eyeLRThita,eyeUDThita;
   
   private int queenPos[] ;
   
   private class Vertex
   {
   		private double x,y,z;
   		@SuppressWarnings("unused")
   		public Vertex(double x,double y,double z){
	   		this.x=x;
	   		this.y=y;
	   		this.z=z;
   		}
   }
   
   private class Face
   {
	   private Vertex vertex[];
	   private Vertex vertex_normal[];
	   private Vertex vertex_texture[];
	   @SuppressWarnings("unused")
	   public Face(Vertex v1,Vertex v2,Vertex v3,Vertex vt1,Vertex vt2,Vertex vt3,Vertex vn1,Vertex vn2,Vertex vn3){
		   vertex = new Vertex[3];
		   vertex[0]=v1;
		   vertex[1]=v2;
		   vertex[2]=v3;
		   vertex_texture = new Vertex[3];
		   vertex_texture[0]=vt1;
		   vertex_texture[1]=vt2;
		   vertex_texture[2]=vt3;
		   vertex_normal = new Vertex[3];
		   vertex_normal[0]=vn1;
		   vertex_normal[1]=vn2;
		   vertex_normal[2]=vn3;
	   }
	   public Face(Vertex v1,Vertex v2,Vertex v3,Vertex vn1,Vertex vn2,Vertex vn3){
		   vertex = new Vertex[3];
		   vertex[0]=v1;
		   vertex[1]=v2;
		   vertex[2]=v3;
		   vertex_texture = new Vertex[3];
		   vertex_normal = new Vertex[3];
		   vertex_normal[0]=vn1;
		   vertex_normal[1]=vn2;
		   vertex_normal[2]=vn3;
	   }
   }
   /** The entry main() method to setup the top-level container and animator */
   public static void main(String[] args) {
      // Run the GUI codes in the event-dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            // Create the OpenGL rendering canvas
            GLCanvas canvas = new JOGL_3D();
            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
            // Create a animator that drives canvas' display() at the specified FPS.
            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
 
            // Create the top-level container
            final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame
            frame.getContentPane().add(canvas);
            frame.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                  // Use a dedicate thread to run the stop() to ensure that the
                  // animator stops before program exits.
                  new Thread() {
                     @Override
                     public void run() {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                     }
                  }.start();
               }
            });
            frame.setTitle(TITLE);
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop
         }
      });
   }
 
   // Setup OpenGL Graphics Renderer
 
   private GLU glu;  // for the GL Utility
   private float anglePyramid = 0;    // rotational angle in degree for pyramid
   private float angleCube = 0;       // rotational angle in degree for cube
   private float speedPyramid = 2.0f; // rotational speed for pyramid
   private float speedCube = -1.5f;   // rotational speed for cube
 
   private int queenCounter;
   private boolean foundFlag;
   private int seed;
   private boolean row[];
   private boolean lr[];
   private boolean rl[];
   /** Constructor to setup the GUI for this Component */
   public JOGL_3D() {
	  face = new Face[MAX_ELEMENT];
	  vertex = new Vertex[MAX_ELEMENT];
	  vertex_normal = new Vertex[MAX_ELEMENT];
	  vertex_texture = new Vertex[MAX_ELEMENT];
	  
	  queenPos = new int[8];
	  
	  vn_num=v_num=f_num=0;
	  
	  eyePos		 = new Vertex(0.0,5.0,-30.0);
	  targetPos		 = new Vertex(0.0,-10.0,0.0);
	  targetDistance = new Vertex(0.0,0.0,8.0);
	  headVector     = new Vertex(0.0,1.0,0.0);
	  
	  eyeLRThita=0.0;
	  eyeUDThita=0.0;
	  
	  this.addKeyListener(new KeyHandler());
      this.addGLEventListener(this);
      
      Random random = new Random();
      seed = random.nextInt(92);
      queenCounter=0;
      foundFlag=false;
      row = new boolean[8];
      lr  = new boolean[8*2-1];
      rl  = new boolean[8*2-1];
      queen_proc(0);
   }
 
   // ------ Implement methods declared in GLEventListener ------
 
   /**
    * Called back immediately after the OpenGL context is initialized. Can be used
    * to perform one-time initialization. Run only once.
    */
   @Override
   public void init(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
      glu = new GLU();                         // get GL Utilities
      gl.glClearColor(0.02f, 0.02f, 0.02f, 1.0f); // set background (clear) color
      gl.glClearDepth(1.0f);      // set clear depth value to farthest
      gl.glEnable(GL_DEPTH_TEST); // enables depth testing
      gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
      gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
      gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
      parseData();
      
      LoadTexture(drawable);
      
      gl.glEnable(GL2.GL_LIGHTING);
      
      float[] lightPos = {10,6,-10,0};
      
      float[] lightColorAmbient = {0.300000f, 0.300000f, 0.300000f,1.0f};
      float[] lightColorSpecular = {0.500000f, 0.500000f, 0.500000f,1.0f};
      float[] lightColorDiffuse = {0.70000f ,0.70000f ,0.70000f,1.0f};
      
      gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT ,lightColorAmbient,0);
      
      
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos,0);
      gl.glLightfv(GL2.GL_LIGHT1,GL2.GL_SPECULAR,lightColorSpecular,0);
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, lightColorDiffuse,0);
      
      gl.glEnable(GL2.GL_LIGHT1);
      
      float[] colorWhite  = {1.0f,1.0f,1.0f,1.0f};
//      float[] colorHighWhite  = {0.7f,0.7f,0.7f,1.0f};
//      float[] colorMidWhite  = {0.5f,0.5f,0.5f,1.0f};
//      float[] colorLowWhite  = {0.3f,0.3f,0.3f,1.0f};
//      float[] colorLowLowWhite  = {0.2f,0.2f,0.2f,1.0f};
//      float[] colorBlack  = {0.0f,0.0f,0.0f,1.0f};
      
      gl.glEnable(GL2.GL_COLOR_MATERIAL);
      gl.glColorMaterial(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT_AND_DIFFUSE);
      //gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, colorHighWhite ,0 );
      gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, colorWhite ,0);
      gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, colorWhite ,0);
      gl.glMateriali( GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 70);
      //gl.glMaterialfv( GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, colorBlack ,0);
      
      //gl.glColor3f(0.0f,0.0f,0.0f);
      
      gl.glMatrixMode(GL_PROJECTION);
      gl.glLoadIdentity();
      glu.gluPerspective( 45, 320/240, 0.1, 500 );
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity();
      glu.gluLookAt(eyePos.x, eyePos.y, eyePos.z, targetPos.x, targetPos.y, targetPos.z, 0, 1, 0);
   }
 
   /**
    * Call-back handler for window re-size event. Also called when the drawable is
    * first set to visible.
    */
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
 
      if (height == 0) height = 1;   // prevent divide by zero
      float aspect = (float)width / height;
 
      // Set the view port (display area) to cover the entire window
      gl.glViewport(0, 0, width, height);
      
      gl.glMatrixMode(GL_PROJECTION);
      gl.glLoadIdentity();
      glu.gluPerspective( 45, aspect, 0.1, 500 );
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity();
      
      glu.gluLookAt(eyePos.x, eyePos.y, eyePos.z, targetPos.x, targetPos.y, targetPos.z, 0, 1, 0);
   }
 
   private Vertex getNormalVector(Vertex v1,Vertex v2){
	   return new Vertex(  v1.y*v2.z-v2.y*v1.z  ,  v1.z*v2.x-v2.z*v1.x , v1.x*v2.y-v2.x*v1.y  );
   }
   
   private Vertex rotateThitaWithAxis(Vertex p,Vertex axis,double thita){

	   double radiansThita = Math.toRadians(thita);
	   double cos = Math.cos(radiansThita);
	   double sin = Math.sin(radiansThita);
	   
	   //System.out.printf("beginX : dx:%f , dy:%f , dz:%f\n",axis.x,axis.y,axis.z);
	   
	   double len=Math.sqrt(axis.x*axis.x+axis.y*axis.y+axis.z*axis.z);
	   
	   axis.x=axis.x/len;
	   axis.y=axis.y/len;
	   axis.z=axis.z/len;
	   
		if(Math.abs(axis.x) < 0.00000000000000100000)axis.x=0.0;
		if(Math.abs(axis.y) < 0.00000000000000100000)axis.y=0.0;
		if(Math.abs(axis.z) < 0.00000000000000100000)axis.z=0.0;
	   
	   //System.out.printf("endX : dx:%f , dy:%f , dz:%f\n",axis.x,axis.y,axis.z);
	  
	   
	   
	   // wiki version
	   /*
	   double x_new = ( cos+(1-cos)*axis.x*axis.x)*p.x+
		  			 ((1-cos)*axis.x*axis.y-sin*axis.z)*p.y+
		  			 ((1-cos)*axis.x*axis.z+sin*axis.y)*p.z;
	   double y_new = ((1-cos)*axis.y*axis.x+sin*axis.z)*p.x+
		  			 (cos+(1-cos)*axis.y*axis.y)*p.y+
		  			 ((1-cos)*axis.y*axis.z-sin*axis.x)*p.z;
	   double z_new = ((1-cos)*axis.z*axis.x-sin*axis.y)*p.x+
		  			 ((1-cos)*axis.z*axis.y+sin*axis.x)*p.y+
		  			 (cos+(1-cos)*axis.z*axis.z)*p.z;
	   */
	   
	   //website version
	   
	   double x_new = ( cos+(1.0-cos)*axis.x*axis.x)*p.x+
	  			 ((1.0-cos)*axis.x*axis.y+sin*axis.z)*p.y+
	  			 ((1.0-cos)*axis.x*axis.z-sin*axis.y)*p.z;
	   double y_new = ((1.0-cos)*axis.y*axis.x-sin*axis.z)*p.x+
	  			 (cos+(1.0-cos)*axis.y*axis.y)*p.y+
	  			 ((1.0-cos)*axis.y*axis.z+sin*axis.x)*p.z;
	   double z_new = ((1.0-cos)*axis.z*axis.x+sin*axis.y)*p.x+
	  			 ((1.0-cos)*axis.z*axis.y-sin*axis.x)*p.y+
	  			 (cos+(1.0-cos)*axis.z*axis.z)*p.z;
	   
	   return new Vertex(x_new,y_new,z_new);
   }
   
   
   /**
    * Called back by the animator to perform rendering.
    */
   @Override
   public void display(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
      
      gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
      
      gl.glMatrixMode(GL_MODELVIEW);
      gl.glLoadIdentity();
      //System.out.printf("targetDistance : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
      //System.out.printf("headVector : dx:%f , dy:%f , dz:%f\n",headVector.x,headVector.y,headVector.z);

      
//      targetPos.x=eyePos.x+targetDistance.x;
//      targetPos.y=eyePos.y+targetDistance.y;
//      targetPos.z=eyePos.z+targetDistance.z;
      
      targetPos.x=eyePos.x+targetDistance.x;
      targetPos.y=eyePos.y+targetDistance.y;
      targetPos.z=eyePos.z+targetDistance.z;
      
      //System.out.printf("x:%f , y:%f , z:%f , ex:%f , ey:%f , ez:%f\n",targetPos.x,targetPosY,targetPos.z,eyePos.x,eyePosY,eyePos.z);
      
      glu.gluLookAt(eyePos.x, eyePos.y, eyePos.z, targetPos.x, targetPos.y, targetPos.z, headVector.x, headVector.y, headVector.z);
      //glu.gluLookAt(eyePos.x, eyePos.y, eyePos.z, targetPos.x, targetPos.y, targetPos.z, 0, 1, 0);
      
      double delta=2.179955;

      texture1.enable(gl);
      
      texture1.bind(gl);
      
      gl.glPushMatrix();
      //gl.glBindTexture(GL2.GL_TEXTURE_2D, texture1);
      gl.glTranslated(-delta*3.5, 0 , -delta*3.5);//set initial position to zero
      
      gl.glBegin(GL_TRIANGLES); // of the pyramid
      for(int k=0;k<8;k++)
      {
    	  
	      for(int i=0;i<f_num;i++){
	    	  //gl.glColor3d(random.nextInt()%255, random.nextInt()%255, random.nextInt()%255);
	    	  
	    	  //gl.glTexCoord2d(face[i].vertex_texture[0].x,face[i].vertex_texture[1].y );
	    	  for(int j=0;j<3;j++){
	    		  gl.glColor3f(0.6f, 0.6f, 0.6f);
	    		  
	    		  gl.glTexCoord2d(face[i].vertex_texture[j].x,face[i].vertex_texture[j].y );
	    		  
	    		  gl.glNormal3d(face[i].vertex_normal[j].x,face[i].vertex_normal[j].y,face[i].vertex_normal[j].z); 
	    		  
	    		  gl.glVertex3d(face[i].vertex[j].x+delta*k,face[i].vertex[j].y+0.8,face[i].vertex[j].z-0.08+delta*queenPos[k]);
	    		  
	    	  }
	      }
      }
      gl.glEnd();
      gl.glPopMatrix();
      texture1.disable(gl);
      
      //gl.glTranslated(delta*3.5, 0 , delta*3.5);
      
      //The light test
//      gl.glBegin(GL2.GL_TRIANGLES);
//      gl.glColor3f(0.4f,1.0f,1.0f);
//      gl.glVertex3d(0,11,10);
//      gl.glVertex3d(-1,10,10);
//      gl.glVertex3d(1,10,10);
//      gl.glEnd();
      
      
      texture2.enable(gl);
      
      texture2.bind(gl);
      
      TextureCoords tc = texture2.getImageTexCoords ();
      float tx1 = tc.left ();
      float ty1 = tc.top ();
      float tx2 = tc.right ();
      float ty2 = tc.bottom ();
      gl.glBegin(GL2.GL_QUADS);
      gl.glColor3f(1.0f, 1.0f, 1.0f);
      
      gl.glNormal3d(0,1,0);
      gl.glTexCoord2f(tx1,ty1);
      gl.glVertex3d(-8.719820 ,0.300749 ,8.684158);
      gl.glTexCoord2f(tx2,ty1);
      gl.glVertex3d(-8.719820 ,0.300749 ,-8.767008);
      gl.glTexCoord2f(tx2,ty2);
      gl.glVertex3d(8.731346 ,0.300749 ,-8.767008);
      gl.glTexCoord2f(tx1,ty2);
      gl.glVertex3d(8.731346 ,0.300749 ,8.684158);
      gl.glEnd();
      texture2.disable(gl);
      
      
      gl.glBegin(GL2.GL_QUADS);
      gl.glColor3f(0.3f, 0.3f,0.3f);
      gl.glNormal3d(1,0,0); 
      gl.glVertex3d(-8.719820,0.300749,-8.719820);
      gl.glVertex3d(-8.719820,0.300749,8.719820);
      gl.glVertex3d(-8.719820,-0.000749,8.719820);
      gl.glVertex3d(-8.719820,-0.000749,-8.719820);
      
      gl.glNormal3d(0,0,1);
      gl.glVertex3d(-8.719820,-0.000749,-8.719820);
      gl.glVertex3d(8.719820,-0.000749,-8.719820);
      gl.glVertex3d(8.719820,0.300749,-8.719820);
      gl.glVertex3d(-8.719820,0.300749,-8.719820);
      
      gl.glNormal3d(1,0,0);
      gl.glVertex3d(8.719820,-0.000749,-8.719820);
      gl.glVertex3d(8.719820,-0.000749,8.719820);
      gl.glVertex3d(8.719820,0.300749,8.719820);
      gl.glVertex3d(8.719820,0.300749,-8.719820);
      
      gl.glNormal3d(0,0,1);
      gl.glVertex3d(-8.719820,0.300749,8.719820);
      gl.glVertex3d(8.719820,0.300749,8.719820);
      gl.glVertex3d(8.719820,-0.000749,8.719820);
      gl.glVertex3d(-8.719820,-0.000749,8.719820);
      
      
      gl.glNormal3d(0,1,0);
      gl.glVertex3d(8.719820 ,-0.000749 ,8.719820);
      gl.glVertex3d(8.719820 ,-0.000749 ,-8.719820);
      gl.glVertex3d(-8.719820, -0.000749 ,-8.719820);
      gl.glVertex3d(-8.719820, -0.000749, 8.719820);
      gl.glEnd();
      
      
      float[] lightPos = {10,6,-10,1};
      gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos,0);
   }
   
   public void LoadTexture(GLAutoDrawable arg0)
   {
	   GL2 gl = arg0.getGL().getGL2();
	   
	   
	   gl.glEnable(GL2.GL_TEXTURE_2D);
	   
//	   
//	   gl.glEnable(GL2.GL_TEXTURE_GEN_S);
//	   gl.glEnable(GL2.GL_TEXTURE_GEN_T);
//	   gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
//	   gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT);
//	   gl.glTexGeni(GL2.GL_S,GL2.GL_TEXTURE_GEN_MODE,GL2.GL_OBJECT_LINEAR);
//	   gl.glTexGeni(GL2.GL_T,GL2.GL_TEXTURE_GEN_MODE,GL2.GL_OBJECT_LINEAR);
//	   gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//	   gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
	   
	   try {
		   //texture1 = TextureIO.newTexture(new File(this.getClass().getResource("/use/wood.jpg").toURI()),true);
		   texture1 = TextureIO.newTexture(JOGL_3D.class.getClassLoader().getResourceAsStream("MarbleWhite.jpg"),true, TextureIO.JPG);
		   
		   //texture1.setTexParameteri( gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST); 
		   //texture1.setTexParameteri( gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		   
		   //texture1.enable(GL2.GL_TEXTURE_GEN_S);
		   //gl.glEnable(GL2.GL_TEXTURE_GEN_T);
		   texture1.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL2.GL_MIRRORED_REPEAT); 
		   texture1.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL2.GL_MIRRORED_REPEAT);
		   
		   texture1_id = texture1.getTextureObject(gl);
		   texture2 = TextureIO.newTexture(JOGL_3D.class.getClassLoader().getResourceAsStream("chessboard.jpg"),true, TextureIO.JPG);
		   
		   //texture2 = TextureIO.newTexture(new File(this.getClass().getResource("/use/chessboard.jpg").toURI()),true);
		   texture2.setTexParameteri( gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST); 
		   texture2.setTexParameteri( gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		   texture2_id = texture2.getTextureObject(gl);
	   }catch(GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
   }
   
   @SuppressWarnings({ "unused", "resource" })
   public void parseData()
   {
	   File file = null;
	   /*
	   try {
		file = new File(this.getClass().getResource("/use/model.obj").toURI());
		//texture2 = TextureIO.newTexture(JOGL_3D.class.getClassLoader().getResourceAsStream("/use/chessboard.jpg"),true, TextureIO.JPG);
		   
	   } catch (URISyntaxException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	   }
	   */
	   Scanner input = null;
	   String[] splitData; 
	   String[] fSplitData;
	   double param1,param2,param3;
	   int f_v[]=new int[3];
	   int f_vn[]=new int[3];
	   int f_vt[]=new int[3];
	   int i;
	   
	   input = new Scanner(JOGL_3D.class.getClassLoader().getResourceAsStream("model.obj"));
	   String data_line;
	   while( input.hasNext() ){
			   data_line = input.nextLine();
			   //System.out.println(data_line);
			   splitData = data_line.split(" ");
			   if(splitData[0].equals("v")){
				   param1 = Double.parseDouble(splitData[1]);
				   param2 = Double.parseDouble(splitData[2]);
				   param3 = Double.parseDouble(splitData[3]);
				   vertex[v_num] = new Vertex(param1,param2,param3);
				   v_num++;
			   }else if(splitData[0].equals("vn")){
				   param1 = Double.parseDouble(splitData[1]);
				   param2 = Double.parseDouble(splitData[2]);
				   param3 = Double.parseDouble(splitData[3]);
				   vertex_normal[vn_num] = new Vertex(param1,param2,param3);
				   vn_num++;
			   }else if(splitData[0].equals("vt")){
				   param1 = Double.parseDouble(splitData[1]);
				   param2 = Double.parseDouble(splitData[2]);
				   vertex_texture[vt_num] = new Vertex(param1,param2,0);
				   vt_num++;
			   }else if(splitData[0].equals("f")){
				   for(i=1;i<4;i++){
					   fSplitData=splitData[i].split("/");
					   if(!fSplitData[0].equals(""))
						   f_v[i-1]=Integer.parseInt(fSplitData[0]);
					   if(!fSplitData[1].equals(""))
						   f_vt[i-1]=Integer.parseInt(fSplitData[1]);
					   if(!fSplitData[2].equals(""))
						   f_vn[i-1]=Integer.parseInt(fSplitData[2]);
				   }
				   face[f_num]=new Face(vertex[f_v[0]-1], vertex[f_v[1]-1], vertex[f_v[2]-1],vertex_texture[f_vt[0]-1], vertex_texture[f_vt[1]-1], vertex_texture[f_vt[2]-1] ,vertex_normal[f_vn[0]-1], vertex_normal[f_vn[1]-1],vertex_normal[f_vn[2]-1]);
				   //face[f_num]=new Face(vertex[f_v[0]-1], vertex[f_v[1]-1], vertex[f_v[2]-1],vertex_normal[f_vn[0]-1], vertex_normal[f_vn[1]-1],vertex_normal[f_vn[2]-1]);
				   f_num++;
			   }
	   }
   
   }
   /**
    * Called back before the OpenGL context is destroyed. Release resource such as buffers.
    */
   	@Override
   	public void dispose(GLAutoDrawable drawable) { }

   	public void MoveLeft(){
   		Vertex normal = getNormalVector(headVector,targetDistance);
   		eyePos.x += normal.x*0.04;
		eyePos.y += normal.y*0.04;
		eyePos.z += normal.z*0.04;
   	}
   
   	public void MoveRight(){
   		Vertex normal = getNormalVector(headVector,targetDistance);
   		eyePos.x += -normal.x*0.04;
		eyePos.y += -normal.y*0.04;
		eyePos.z += -normal.z*0.04;
   	}

	public void MoveBackward(){
		eyePos.x += -targetDistance.x*0.03;
		eyePos.y += -targetDistance.y*0.03;
		eyePos.z += -targetDistance.z*0.03;
	}

	public void MoveForward(){
		eyePos.x += targetDistance.x*0.03;
		eyePos.y += targetDistance.y*0.03;
		eyePos.z += targetDistance.z*0.03;
	}
	
	public void TurnLeft(){
		Vertex normal = getNormalVector(headVector,targetDistance);
//		System.out.printf("begin : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
		
		targetDistance=rotateThitaWithAxis(targetDistance, headVector, -3);
		
		if(Math.abs(targetDistance.x) < 0.00000000000000100000)targetDistance.x=0.0;
		if(Math.abs(targetDistance.y) < 0.00000000000000100000)targetDistance.y=0.0;
		if(Math.abs(targetDistance.z) < 0.00000000000000100000)targetDistance.z=0.0;
		
//		System.out.printf("DEBUG : %.10f  %.10f  %.10f\n",headVector.x,headVector.y,headVector.z);
			
//		System.out.printf("normal : dx:%f , dy:%f , dz:%f\n",normal.x,normal.y,normal.z);
//		System.out.printf("head : dx:%f , dy:%f , dz:%f\n",headVector.x,headVector.y,headVector.z);
//		System.out.printf("end   : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
//		System.out.println("=================================================");
	}
	
	public void TurnRight(){
		Vertex normal = getNormalVector(headVector,targetDistance);
//		System.out.printf("begin : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
		targetDistance=rotateThitaWithAxis(targetDistance, headVector, 3);
		
		if(Math.abs(targetDistance.x) < 0.00000000000000100000)targetDistance.x=0.0;
		if(Math.abs(targetDistance.y) < 0.00000000000000100000)targetDistance.y=0.0;
		if(Math.abs(targetDistance.z) < 0.00000000000000100000)targetDistance.z=0.0;
		
//		System.out.printf("DEBUG : %.10f  %.10f  %.10f\n",headVector.x,headVector.y,headVector.z);
//		
//		System.out.printf("normal : dx:%f , dy:%f , dz:%f\n",normal.x,normal.y,normal.z);
//		System.out.printf("head : dx:%f , dy:%f , dz:%f\n",headVector.x,headVector.y,headVector.z);
//		System.out.printf("end   : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
//		System.out.println("=================================================");
	}
    
	public void TurnUp(){
//		System.out.printf("begin : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
		Vertex normal = getNormalVector(headVector,targetDistance);
		targetDistance=rotateThitaWithAxis(targetDistance, normal, 3);
		headVector=rotateThitaWithAxis(headVector, normal, 3);
		
		if(Math.abs(targetDistance.x) < 0.00000000000000100000)targetDistance.x=0.0;
		if(Math.abs(targetDistance.y) < 0.00000000000000100000)targetDistance.y=0.0;
		if(Math.abs(targetDistance.z) < 0.00000000000000100000)targetDistance.z=0.0;
		
		if(Math.abs(headVector.x) < 0.00000000000000100000)headVector.x=0.0;
		if(Math.abs(headVector.y) < 0.00000000000000100000)headVector.y=0.0;
		if(Math.abs(headVector.z) < 0.00000000000000100000)headVector.z=0.0;
		
//		System.out.printf("DEBUG : %.10f  %.10f  %.20f\n",headVector.x,headVector.y,headVector.z);
//		
//		System.out.printf("normal : dx:%f , dy:%f , dz:%f\n",normal.x,normal.y,normal.z);
//		System.out.printf("head : dx:%f , dy:%f , dz:%f\n",headVector.x,headVector.y,headVector.z);
//		System.out.printf("end   : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
//		System.out.println("=================================================");
	}
	
	public void TurnDown(){
//		System.out.printf("begin : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
		Vertex normal = getNormalVector(headVector,targetDistance);
		
		targetDistance=rotateThitaWithAxis(targetDistance, normal, -3);
		
		headVector=rotateThitaWithAxis(headVector, normal, -3);
		
		if(Math.abs(targetDistance.x) < 0.00000000000000100000)targetDistance.x=0.0;
		if(Math.abs(targetDistance.y) < 0.00000000000000100000)targetDistance.y=0.0;
		if(Math.abs(targetDistance.z) < 0.00000000000000100000)targetDistance.z=0.0;
		
		if(Math.abs(headVector.x) < 0.00000000000000100000)headVector.x=0.0;
		if(Math.abs(headVector.y) < 0.00000000000000100000)headVector.y=0.0;
		if(Math.abs(headVector.z) < 0.00000000000000100000)headVector.z=0.0;
		
//		System.out.printf("DEBUG : %.10f  %.10f  %.20f\n",headVector.x,headVector.y,headVector.z);
//		
//		System.out.printf("normal : dx:%f , dy:%f , dz:%f\n",normal.x,normal.y,normal.z);
//		System.out.printf("head : dx:%f , dy:%f , dz:%f\n",headVector.x,headVector.y,headVector.z);
//		System.out.printf("end   : dx:%f , dy:%f , dz:%f\n",targetDistance.x,targetDistance.y,targetDistance.z);
//		System.out.println("=================================================");
	}
	
	public void MoveUpward(){
		eyePos.x += headVector.x;
		eyePos.y += headVector.y;
		eyePos.z += headVector.z;
	}
	
	public void MoveDownward(){
		eyePos.x += -headVector.x;
		eyePos.y += -headVector.y;
		eyePos.z += -headVector.z;
	}
	
	public void ResetPosition(){
		eyePos		 = new Vertex(8.0,5.0,-20.0);
		targetPos		 = new Vertex(0.0,-10.0,0.0);
		targetDistance = new Vertex(0.0,0.0,8.0);
		headVector     = new Vertex(0.0,1.0,0.0);
	}
	
	class KeyHandler implements KeyListener
    {
		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println(e.getKeyCode());
			// TODO Auto-generated method stub
			switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT:case KeyEvent.VK_A:
					MoveLeft();
					break;
				case KeyEvent.VK_RIGHT:case KeyEvent.VK_D:
					MoveRight();
					break;
				case KeyEvent.VK_UP:case KeyEvent.VK_W:
					MoveForward();
					break;
				case KeyEvent.VK_DOWN:case KeyEvent.VK_S:
					MoveBackward();
					break;
				case KeyEvent.VK_SPACE:
					MoveUpward();
					break;
				case KeyEvent.VK_V:
					MoveDownward();
					break;
				case KeyEvent.VK_Q:
					TurnLeft();
					break;
				case KeyEvent.VK_E:
					TurnRight();
					break;
				case KeyEvent.VK_R:
					TurnUp();
					break;
				case KeyEvent.VK_F:
					TurnDown();
					break;
				case KeyEvent.VK_ENTER:
					ResetPosition();
					break;
					
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
	public void queen_proc(int pos)
    {
        if(pos==8){
        	if(seed==queenCounter)
        		foundFlag=true;
        	else 
        		queenCounter++;
            return ; 
        }
   
        for(int i=0;!foundFlag&&i<8;i++){
            if(!row[i] && !lr[pos-i+8-1] && !rl[pos+i]){
            	queenPos[pos]=i; 
                row[i]=true; 
                lr[pos-i+8-1]=true;
                rl[pos+i]=true;
                queen_proc(pos+1);
                row[i]=false; 
                lr[pos-i+8-1]=false;
                rl[pos+i]=false;
            }
        }
    
    }
}