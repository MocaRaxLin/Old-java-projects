package Sterne;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class dust {
		
	public double vector[];		
	public boolean t=true;
	//軌道根數
	public static class Element{

	
		
	}
	//顏色參數
	public static class Color{
		
		int r;
		int g;
		int b;
		
		public Color(int _r, int _g, int _b){
			
			r = _r;
			g = _g;
			b = _b;
		}		
		public Color(){
			
			this(0, 0, 0);
		}		
		public float getRf(){
			
			return (float)(r / 255);
		}
		public float getGf(){
			
			return (float)(g / 255);
		}
		public float getBf(){
			
			return (float)(b / 255);
		}
	}

	private Element elem[] = new Element[2];
	private String name;
	//private Color color;
	private float radius=10.0f;
	private Texture texture;
	private GLU glu = new GLU();
	private double v1 = 0;
	private double v2 = 0;
	private double v3 = 0;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private boolean is_texture;	
	public double getx() {
		return x;
	}
	public double gety() {
		return y;
	}
	public double getz() {
		return z;
	}	
	public void dust(double x1,double y1,double z1){

		
		x=x1;
		y=y1;
		z=z1;
		
				
		//load_texture();
	}
    public void random(){
    	Random r = new Random();   
		v1 = (r.nextInt(20))-10;
		v2 = (r.nextInt(20))-10;
		v3 = (r.nextInt(20))-10;
    }		
    public void display(GL2 gl2){
		
		gl2.glPushMatrix();
		// 移動到目標座標繪製
	    gl2.glTranslated(x,y,z);
	    if(is_texture){
	    	texture.enable(gl2);
	    	texture.bind(gl2);
	    }
	    
        final GLUquadric model = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(model, GLU.GLU_FILL);
        glu.gluQuadricNormals(model, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(model, GLU.GLU_INSIDE);
        glu.gluQuadricTexture(model, true);
        
        final int slices = 24;
        final int stacks = 24;
        glu.gluSphere(model, radius, slices, stacks);
        glu.gluDeleteQuadric(model);
        
        if(is_texture)
        	texture.disable(gl2);
        
	    gl2.glPopMatrix();
	}	
	public void update(){
        x = x+20*v1;
        y = y+20*v2;
        z = z+20*v3;
        
	}
	public void setx(double x1){
		x=x1;
		//load_texture();
	}
	public void sety(double y1){
		y=y1;
	}
	public void setz(double z1){
		z=z1;
	}
    public void load_texture(){
		
		try {
            InputStream stream = getClass().getResourceAsStream("/imag/plutomap.jpg");            
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
            texture = TextureIO.newTexture(data);
            this.is_texture = true;
        }
        catch (IOException exc) {
            this.is_texture = false;
        }
	}
public void load_texture1(){
		
		try {
            InputStream stream = getClass().getResourceAsStream("/imag/we.jpg");            
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
            texture = TextureIO.newTexture(data);
            this.is_texture = true;
        }
        catch (IOException exc) {
            this.is_texture = false;
        }
	}
        
        
        
	}
	
	

	
	

