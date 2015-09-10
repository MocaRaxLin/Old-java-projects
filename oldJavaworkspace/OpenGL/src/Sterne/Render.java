package Sterne;

import org.eclipse.swt.events.*;
import org.eclipse.swt.SWT;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLEventListener;


public class Render implements GLEventListener, MouseListener, MouseMoveListener, MouseWheelListener{
	private float[] lightAmbient = {1.5f, 1.5f, 1.5f, 1.0f}; 
	private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
	private float[] lightPosition = {0.0f, 0.0f, 0.0f, 1.0f};
	double x = 0;
	double y = 1000;
	double z = 1000;
	double fa = 0 ;
	double fb = 0 ;
	double fc = 0 ;
	double ua = 0 ;
	double ub = 0 ;
	double uc = 1 ;
	double c1 = 0 ;
	double c2 = 0 ;
	double c3 = 0 ;
	boolean beam =false;
	boolean hit =false;
	boolean get =true;
	//boolean sound =true;
	boolean light =true;
	//剛按下滑鼠時的視窗座標
	private int startX = 0;
	private int startY = 0;
	//拖曳後的相對座標，用這決定要旋轉幾度
	private double alt = Math.PI / 8;
	private double az = Math.PI / 8;
	//設定初始深度 
	private double deep = 50000;
	// 計時器
	private Time time;
	private double rowdegreel=Math.PI/60;
	private double rowdegreer=-Math.PI/60;
	private double pitchup=-Math.PI/30;
	private double pitchdown=Math.PI/30;
	private double yawl=-Math.PI/30;
	private double yawr=Math.PI/30;
	private Model sun;
	private Model mercury;
	private Model earth;
	dust dust1=new dust();
	dust dust2=new dust();
	dust dust3=new dust();
	dust dust4=new dust();
	dust dust5=new dust();
	dust dust6=new dust();
	light light1=new light();
	
    private GLU glu = new GLU();
    
    public void setTime(Time time){
    	
    	this.time = time;
    }
    public int speed=0; 
    public void addspeed(){
    	speed=speed+1<51?speed+1:50;   	
    }	
    public void minusspeed(){
    	speed=speed-1>-1?speed-1:0;    	
    }
    public void stop(){
    	speed=0;   	
    }	    
     public float[] count(float a,float b,float c, float[][] trans){
    	float[] answer = null;
    	answer[0]=a*trans[0][0]+b*trans[0][1]+c*trans[0][2];
    	answer[1]=a*trans[1][0]+b*trans[1][1]+c*trans[1][2];
    	answer[2]=a*trans[2][0]+b*trans[2][1]+c*trans[2][2];
    	return answer;
    }
     public void yawr(){
    	 double v0 = 0;
    	 double v1 = 0;
    	 double v2 = 0;
    	 double fv0 = 0;
    	 double fv1 = 0;
    	 double fv2 = 0;
    	 double far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));  	
    	 v0=(ua);
    	 v1=(ub);
    	 v2=(uc);
       	 double r=Math.sqrt(Math.pow(v0, 2)+Math.pow(v1, 2)+Math.pow(v2, 2));
       	 fv0=((fa-x)*(Math.cos(yawl)+(1-Math.cos(yawl))*Math.pow(v0/r,2))+(fb-y)*((1-Math.cos(yawl))*v0/r*v1/r-(Math.sin(yawl)*v2/r))+(fc-z)*((1-Math.cos(yawl))*v0/r*v2/r+Math.sin(yawl)*v1/r));
         fv1=((fa-x)*((1-Math.cos(yawl))*v1/r*v0/r+Math.sin(yawl)*v2/r)+(fb-y)*(Math.cos(yawl)+(1-Math.cos(yawl))*Math.pow(v1/r, 2))+(fc-z)*((1-Math.cos(yawl))*v1/r*v2/r-Math.sin(yawl)*v0/r));         
         fv2=((fa-x)*(1-Math.cos(yawl))*v2/r*v0/r-Math.sin(yawl)*v1/r+(fb-y)*((1-Math.cos(yawl))*v2/r*v1/r+Math.sin(yawl)*v0/r)+(fc-z)*(Math.cos(yawl)+(1-Math.cos(yawl))*Math.pow(v2/r, 2)));
         far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
         double fr=Math.sqrt(Math.pow(fv0, 2)+Math.pow(fv1, 2)+Math.pow(fv2, 2));
         fa= (x+far*fv0/fr);
         fb= (y+far*fv1/fr);
         fc= (z+far*fv2/fr);
     }public void yawl(){
    	 double v0 = 0;
    	 double v1 = 0;
    	 double v2 = 0;
    	 double fv0 = 0;
    	 double fv1 = 0;
    	 double fv2 = 0;
    	 double far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));  	
    	 v0=(ua);
    	 v1=(ub);
    	 v2=(uc);
       	 double r=Math.sqrt(Math.pow(v0, 2)+Math.pow(v1, 2)+Math.pow(v2, 2));
       	 fv0=((fa-x)*(Math.cos(yawr)+(1-Math.cos(yawr))*Math.pow(v0/r,2))+(fb-y)*((1-Math.cos(yawr))*v0/r*v1/r-(Math.sin(yawr)*v2/r))+(fc-z)*((1-Math.cos(yawr))*v0/r*v2/r+Math.sin(yawr)*v1/r));
         fv1=((fa-x)*((1-Math.cos(yawr))*v1/r*v0/r+Math.sin(yawr)*v2/r)+(fb-y)*(Math.cos(yawr)+(1-Math.cos(yawr))*Math.pow(v1/r, 2))+(fc-z)*((1-Math.cos(yawr))*v1/r*v2/r-Math.sin(yawr)*v0/r));         
         fv2=((fa-x)*(1-Math.cos(yawr))*v2/r*v0/r-Math.sin(yawr)*v1/r+(fb-y)*((1-Math.cos(yawr))*v2/r*v1/r+Math.sin(yawr)*v0/r)+(fc-z)*(Math.cos(yawr)+(1-Math.cos(yawr))*Math.pow(v2/r, 2)));
         far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
         double fr=Math.sqrt(Math.pow(fv0, 2)+Math.pow(fv1, 2)+Math.pow(fv2, 2));
         fa= (x+far*fv0/fr);
         fb= (y+far*fv1/fr);
         fc= (z+far*fv2/fr);
     }
     public void pitchup(){
    	 double v0 = 0;
    	 double v1 = 0;
    	 double v2 = 0;
    	 double fv0 = 0;
    	 double fv1 = 0;
    	 double fv2 = 0;
    	 double far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
    	 double ur=Math.sqrt(Math.pow(ua, 2)+Math.pow(ub, 2)+Math.pow(uc, 2));
    	 v0=(ub/ur*(fc-z)/far-uc/ur*(fb-y)/far);
    	 v1=(uc/ur*(fa-x)/far-ua/ur*(fc-z)/far);
    	 v2=(ua/ur*(fb-y)/far-ub/ur*(fa-x)/far);
       	 double r=Math.sqrt(Math.pow(v0, 2)+Math.pow(v1, 2)+Math.pow(v2, 2));
       	 fv0=((fa-x)*(Math.cos(pitchup)+(1-Math.cos(pitchup))*Math.pow(v0/r,2))+(fb-y)*((1-Math.cos(pitchup))*v0/r*v1/r-(Math.sin(pitchup)*v2/r))+(fc-z)*((1-Math.cos(pitchup))*v0/r*v2/r+Math.sin(pitchup)*v1/r));
         fv1=((fa-x)*((1-Math.cos(pitchup))*v1/r*v0/r+Math.sin(pitchup)*v2/r)+(fb-y)*(Math.cos(pitchup)+(1-Math.cos(pitchup))*Math.pow(v1/r, 2))+(fc-z)*((1-Math.cos(pitchup))*v1/r*v2/r-Math.sin(pitchup)*v0/r));         
         fv2=((fa-x)*(1-Math.cos(pitchup))*v2/r*v0/r-Math.sin(pitchup)*v1/r+(fb-y)*((1-Math.cos(pitchup))*v2/r*v1/r+Math.sin(pitchup)*v0/r)+(fc-z)*(Math.cos(pitchup)+(1-Math.cos(pitchup))*Math.pow(v2/r, 2)));
         far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
         double fr=Math.sqrt(Math.pow(fv0, 2)+Math.pow(fv1, 2)+Math.pow(fv2, 2));
         fa= (x+far*fv0/fr);
         fb= (y+far*fv1/fr);
         fc= (z+far*fv2/fr);
         //System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc);
        }
     public void pitchdown(){
    	 double v0 = 0;
    	 double v1 = 0;
    	 double v2 = 0;
    	 double fv0 = 0;
    	 double fv1 = 0;
    	 double fv2 = 0;
    	 double far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
    	 double ur=Math.sqrt(Math.pow(ua, 2)+Math.pow(ub, 2)+Math.pow(uc, 2));
    	 v0=(ub/ur*(fc-z)/far-uc/ur*(fb-y)/far);
    	 v1=(uc/ur*(fa-x)/far-ua/ur*(fc-z)/far);
    	 v2=(ua/ur*(fb-y)/far-ub/ur*(fa-x)/far);
       	 double r=Math.sqrt(Math.pow(v0, 2)+Math.pow(v1, 2)+Math.pow(v2, 2));
       	 fv0= ((fa-x)*(Math.cos(pitchdown)+(1-Math.cos(pitchdown))*Math.pow(v0/r,2))+(fb-y)*((1-Math.cos(pitchdown))*v0/r*v1/r-(Math.sin(pitchdown)*v2/r))+(fc-z)*((1-Math.cos(pitchdown))*v0/r*v2/r+Math.sin(pitchdown)*v1/r));
         fv1= ((fa-x)*((1-Math.cos(pitchdown))*v1/r*v0/r+Math.sin(pitchdown)*v2/r)+(fb-y)*(Math.cos(pitchdown)+(1-Math.cos(pitchdown))*Math.pow(v1/r, 2))+(fc-z)*((1-Math.cos(pitchdown))*v1/r*v2/r-Math.sin(pitchdown)*v0/r));         
         fv2= ((fa-x)*(1-Math.cos(pitchdown))*v2/r*v0/r-Math.sin(pitchdown)*v1/r+(fb-y)*((1-Math.cos(pitchdown))*v2/r*v1/r+Math.sin(pitchdown)*v0/r)+(fc-z)*(Math.cos(pitchdown)+(1-Math.cos(pitchdown))*Math.pow(v2/r, 2)));
          far=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
         double fr=Math.sqrt(Math.pow(fv0, 2)+Math.pow(fv1, 2)+Math.pow(fv2, 2));
         fa= (x+far*fv0/fr);
         fb= (y+far*fv1/fr);
         fc= (z+far*fv2/fr);
         //System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc);
         }
     public void rowr(){
   	 double r=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));
   	double ur=Math.sqrt(Math.pow(ua, 2)+Math.pow(ub, 2)+Math.pow(uc, 2));
   	double fua,fub,fuc;
   	 fua= (ua*(Math.cos(rowdegreer)+(1-Math.cos(rowdegreer))*Math.pow((fa-x)/r,2))/ur+ub*((1-Math.cos(rowdegreer))*(fa-x)/r*(fb-y)/r-(Math.sin(rowdegreer)*(fc-z)/r))/ur+uc*((1-Math.cos(rowdegreer))*(fa-x)/r*(fc-z)/r+Math.sin(rowdegreer)*(fb-y)/r)/ur);
        fub= (ua*((1-Math.cos(rowdegreer))*(fb-y)/r*(fa-x)/r+Math.sin(rowdegreer)*(fc-z)/r)/ur+ub*(Math.cos(rowdegreer)+(1-Math.cos(rowdegreer))*Math.pow((fb-y)/r, 2))/ur+uc*((1-Math.cos(rowdegreer))*(fb-y)/r*(fc-z)/r-Math.sin(rowdegreer)*(fa-x)/r)/ur);
        fuc= (ua*((1-Math.cos(rowdegreer))*(fc-z)/r*(fa-x)/r-Math.sin(rowdegreer)*(fb-y)/r)/ur+ub*((1-Math.cos(rowdegreer))*(fc-z)/r*(fb-y)/r+Math.sin(rowdegreer)*(fa-x)/r)/ur+uc*(Math.cos(rowdegreer)+(1-Math.cos(rowdegreer))*Math.pow((fc-z)/r, 2))/ur);
    ua=fua;
    ub=fub;
    uc=fuc;
    //System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc+" "+ur);
     }
     public void rowl(){
    	 double fua,fub,fuc;
    	 double r=Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2));    
    	 double ur=Math.sqrt(Math.pow(ua, 2)+Math.pow(ub, 2)+Math.pow(uc, 2));
       	 fua= (ua*(Math.cos(rowdegreel)+(1-Math.cos(rowdegreel))*Math.pow((fa-x)/r,2))/ur+ub*((1-Math.cos(rowdegreel))*(fa-x)/r*(fb-y)/r-(Math.sin(rowdegreel)*(fc-z)/r))/ur+uc*((1-Math.cos(rowdegreel))*(fa-x)/r*(fc-z)/r+Math.sin(rowdegreel)*(fb-y)/r)/ur);
            fub=(ua*((1-Math.cos(rowdegreel))*(fb-y)/r*(fa-x)/r+Math.sin(rowdegreel)*(fc-z)/r)/ur+ub*(Math.cos(rowdegreel)+(1-Math.cos(rowdegreel))*Math.pow((fb-y)/r, 2))/ur+uc*((1-Math.cos(rowdegreel))*(fb-y)/r*(fc-z)/r-Math.sin(rowdegreel)*(fa-x)/r)/ur);
            fuc= (ua*((1-Math.cos(rowdegreel))*(fc-z)/r*(fa-x)/r-Math.sin(rowdegreel)*(fb-y)/r)/ur+ub*((1-Math.cos(rowdegreel))*(fc-z)/r*(fb-y)/r+Math.sin(rowdegreel)*(fa-x)/r)/ur+uc*(Math.cos(rowdegreel)+(1-Math.cos(rowdegreel))*Math.pow((fc-z)/r, 2))/ur);
            ua=fua;
            ub=fub;
            uc=fuc;
           // System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc+" "+ur);
            }
     public void watch(){
    	 System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc+" "+Math.sqrt(Math.pow(ua, 2)+Math.pow(ub, 2)+Math.pow(uc, 2)));
     }	
	public void beam(){
      beam=true; 
	}	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		
		/*if(sound)
		{
		SoundPlayer.aa();
		sound=false;
		}*/
		final GL2 gl2 = gLDrawable.getGL().getGL2();
		// float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f}; 
		//gl2.glClearColor(0, 0, 0, 1);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, this.lightAmbient, 0);  
        //设置漫射光  
        gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, this.lightDiffuse, 0);  
        //设置光源位置  
        gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, this.lightPosition, 0);  
        //启用一号光源  
        gl2.glEnable(GL2.GL_LIGHT1);  
        //我们启用GL_LIGHTING，所以您看见任何光线  
        gl2.glEnable(GL2.GL_LIGHTING);  
        
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl2.glLoadIdentity();
		
		
		//設定一些觀看參數		
		//float x = (float)(deep * Math.cos(az) * Math.cos(alt));
		//float y = (float)(deep * -Math.sin(az) * Math.cos(alt));
		//float z = (float)(deep * Math.sin(alt));
		double x1=speed*2*((fa-x)/Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2)));
		double x2=speed*2*((fb-y)/Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2)));
		double x3=speed*2*((fc-z)/Math.sqrt(Math.pow(fa-x, 2)+Math.pow(fb-y, 2)+Math.pow(fc-z, 2)));
		x=(x+x1);
        y=(y+x2);
        z=(z+x3);
        fa=fa+x1;
        fb=fb+x2;
        fc=fc+x3;
        
        //System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+ua+" "+ub+" "+uc);
		glu.gluLookAt(x,y, z, fa, fb,fc, ua, ub, uc);
	    
	   //畫空間中的方框
	    gl2.glPushMatrix();
	    gl2.glBegin(GL.GL_LINES);
	    gl2.glColor3f(0.0f , 0.0f , 0.0f);
     	    
	    	for(int i = -30; i <=30; i++){
	    		gl2.glVertex3d(-30000, i * 1000, 0);
	    		gl2.glVertex3d(30000, i * 1000, 0);
	    		gl2.glVertex3d(i * 1000, -30000, 0);
	    		gl2.glVertex3d(i * 1000, 30000, 0);	
	    		//gl2.glVertex3d((int)x,(int) y,(int) z);
	    		//gl2.glVertex3d((int)fa,(int)fb,(int)fc);
	    	}
	    	if(beam)
    		{	
	    		//SoundPlayer.photonShot();
	    		gl2.glColor3f(0.0f , 255.0f , 255.0f);
	    		gl2.glVertex3d((int)x,(int) y,(int) z);
	    		gl2.glVertex3d((int)fa,(int)fb,(int)fc);
	    		double a1;
	    		double a2;
	    		double a3;
	    		double b1;
	    		double b2;
	    		double b3;	    		
	    		a1=(fa-x);
	    		a2=(fb-y);
	    		a3=(fc-z);
	    		b1=(earth.getx())*1000-x;
	    		b2=(earth.gety())*1000-y;
	    		b3=(earth.getz())*1000-z;
	    		System.out.println(Math.sqrt(Math.pow(b1, 2)+Math.pow(b2, 2)+Math.pow(b3, 2))*Math.sin(Math.acos((a1*b1+a2*b2+a3*b3)/(Math.sqrt(Math.pow(a1, 2)+Math.pow(a2, 2)+Math.pow(a3, 2))*Math.sqrt(Math.pow(b1, 2)+Math.pow(b2, 2)+Math.pow(b3, 2))))));
	    		//System.out.println(x+" "+y+" "+z+" "+fa+" "+fb+" "+fc+" "+earth.getx()*1000+" "+earth.gety()*1000+" "+earth.getz()*1000);
	    		if((Math.sqrt(Math.pow(b1, 2)+Math.pow(b2, 2)+Math.pow(b3, 2))*Math.sin(Math.acos((a1*b1+a2*b2+a3*b3)/(Math.sqrt(Math.pow(a1, 2)+Math.pow(a2, 2)+Math.pow(a3, 2))*Math.sqrt(Math.pow(b1, 2)+Math.pow(b2, 2)+Math.pow(b3, 2))))))<20)
	    	    {
	    			if(get){
	    	    	System.out.println("boom");
	    	    	hit=true;
	    	    	//SoundPlayer.explosion();
	    	    	lightPosition[0] =  (float) earth.getx();
	    	    	lightPosition[1] =  (float) earth.gety();
	    	    	lightPosition[0] =  (float) earth.getz();
	    	    	dust1.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	dust2.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	dust3.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	dust4.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	dust5.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	dust6.dust(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	light1.light(1000*earth.getx(),1000*earth.gety(),1000*earth.getz());
	    	    	//float[] lightPosition = {(float) (1000*earth.getx()),(float) (1000*earth.gety()),(float) (1000*earth.getz()), 1.0f};
	    	    	get=false;
	    			}
	    	    }
	    		beam=false;
    		}
	    	
	    gl2.glEnd();
	    gl2.glPopMatrix();	    
	    //畫個球看看
	    
	    if(!hit){
	    	earth.display(gl2);
		    earth.update(time);
	    }
	    else{
	    	
	    	if(light)
			{
    				 c1=(earth.getx());
    					 c2=(earth.gety());
    					 c3=(earth.getz());
    					 
    		light=false;			
			}
	    	gl2.glColor3f(0.0f , 255.0f , 255.0f);
	    	gl2.glVertex3d(1000*c1,1000*c2,1000*c3);
		    gl2.glVertex3d(1000*(c1+1),1000*(c2+1),1000*(c3+1));
	    	earth.update(time);
	    	dust1.display(gl2);
	    	dust1.update();
	    	dust2.display(gl2);
	    	dust2.update();
	    	dust3.display(gl2);
	    	dust3.update();
	    	dust4.display(gl2);
	    	dust4.update();
	    	dust5.display(gl2);
	    	dust5.update();
	    	dust6.display(gl2);
	    	dust6.update();
	    	//light1.display(gl2);
	    }
	    sun.display(gl2);
	    mercury.display(gl2);
	    sun.update(time);
	    //dust1.update();
	    mercury.update(time);
	    	    
	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl2 = gLDrawable.getGL().getGL2();
        gl2.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);    // Black Background
        gl2.glClearDepth(6.0f);                      // Depth Buffer Setup
        gl2.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
        gl2.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); 
         
        // 載入星球軌道根數
        sun = new Model(
        		new Model.Element(new double[]{0, 0, 0, 0, 0, 0}),
        		new Model.Element(new double[]{0, 0, 0, 0, 0, 0}),
        		"sun",
        		100.0f
        );
        mercury = new Model(
        		new Model.Element(new double[]{0.3870989,0.20563069,	7.00487	,48.33167	,77.45645	,252.25084}),
        		new Model.Element(new double[]{0.00000562, -0.00004392, -0.01294668, 0.0, 0.32327364, 35999.37244981}),
        		"sun",
        		10.0f
        );

        earth = new Model(
        		new Model.Element(new double[]{1.00000261, 0.01671123, -0.00001531, 0.0, 102.93768193, 100.46457166}),
        		new Model.Element(new double[]{0.00000562, -0.00004392, -0.01294668, 0.0, 0.32327364, 35999.37244981}),
        		
        		//new Model.Element(new double[]{0, 0, 0, 0, 0, 0}),
        		"earth",
        		20.0f
        );
        sun.load_texture();
        mercury.load_texture();
        earth.load_texture();
        dust1.load_texture();
        dust2.load_texture();
        dust3.load_texture();
        dust4.load_texture();
        dust5.load_texture();
        dust6.load_texture();
        
        //dust1.setx((float)earth.getx());
    	//dust1.sety((float)earth.gety());
    	//dust1.setz((float)earth.getz());
    	
    	dust1.random();
    	dust2.random();
    	dust3.random();
    	dust4.random();
    	dust5.random();
    	dust6.random();
        //light1.vect();
	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		final GL2 gl2 = gLDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float)width / (float)height;
        gl2.glViewport(0, 0, width, height);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0, 1000.0);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
        
        
        gl2.glFlush();
        //gLDrawable.swapBuffers();
	}

	@Override
	public void mouseDoubleClick(MouseEvent mouse) {
	}

	@Override
	public void mouseDown(MouseEvent mouse) {
		//滑鼠左鍵
		if(mouse.button == 1){
			startX = mouse.x;
	        startY = mouse.y;
		}
	}

	@Override
	public void mouseUp(MouseEvent mouse) {
		//滑鼠左鍵
		if(mouse.button == 1){
			startX = mouse.x;
	        startY = mouse.y;
		}
	}

	@Override
	public void mouseMove(MouseEvent mouse) {
		//在滑鼠左鍵按住的狀態
		if((mouse.stateMask & SWT.BUTTON1) != 0){
			az += (mouse.x - startX) * 0.002;
			System.out.println(mouse.x);
			alt += (mouse.y - startY) * 0.002;
			System.out.println(mouse.y);
			if(alt > Math.PI / 2)
				alt = Math.PI / 2;
			else if(alt < -Math.PI / 2)
				alt = -Math.PI / 2;
			
			startX = mouse.x;
	        startY = mouse.y;
		}
		
	}

	@Override
	public void mouseScrolled(MouseEvent mouse) {
		//用滑鼠滾輪調整遠近視角
		if(mouse.count > 0){
			deep *= 0.8;
		}
		if(mouse.count < 0){
			deep *= 1.25;
		}
		if(deep > 30000)
			deep = 30000;
		else if(deep < 100)
			deep = 100;
	}

}
