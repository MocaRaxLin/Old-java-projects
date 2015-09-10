package Sterne;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GL2;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Model {
	public boolean t=true;
	//軌道根數
	public static class Element{

		public double a;
		public double e;
		public double i;
		public double O;
		public double o;
		public double L;
		
		
		public Element(){
			
			a = e = i = O = o = L = 0.0;
		}
		public Element(double elem[]){
			
			a = elem[0]; e = elem[1]; i = elem[2];
			O = elem[3]; o = elem[4]; L = elem[5];
		}
		public Element(Element elem){
			
			this.a = elem.a;
			this.e = elem.e;
			this.i = elem.i;
			this.O = elem.O;
			this.o = elem.o;
			this.L = elem.L;
		}
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
	private float radius;
	private Texture texture;
	private GLU glu = new GLU();
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
	
	
	public Model(){
		
		elem = new Element[2];
		elem[0] = new Element();
		elem[1] = new Element();
		name = "";
		//color = new Color();
		radius = 0.0f;
		//load_texture();
	}

	public void dead(){
		load_texture("shockwave_128X128.png");
		
	}
	public Model(Element[] _elem, String _name, float _radius){
		
		elem = _elem;
		name = _name;
		//color = _color;
		radius = _radius;		
		load_texture();
	}
		
	
	public Model(Element _elem0, Element _elem1, String _name, float _radius){
		
		elem[0] = _elem0;
		elem[1] = _elem1;
		name = _name;
		//color = _color;
		radius = _radius;
		//load_texture();
	}
	
	public void display(GL2 gl2){
		
		gl2.glPushMatrix();
		// 移動到目標座標繪製
	    gl2.glTranslated(1000 * x, 1000 * y, 1000 * z);
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
	
	public void update(Time time){
		
		double CY = time.julianCentury();
		double RAD = Math.PI / 180;
	    double TAU = Math.PI * 2;
	    
	    double a = elem[0].a + elem[1].a * CY;
	    double e = elem[0].e + elem[1].e * CY;
	    double i = (elem[0].i + elem[1].i * CY) * RAD;
	    double O = (elem[0].O + elem[1].O * CY) * RAD;
	    double o = (elem[0].o + elem[1].o * CY) * RAD;
	    double L = ((elem[0].L + elem[1].L * CY) * RAD) % TAU;
	    
	    double M = (L - o + TAU) % TAU;
	    
	    double E = M + e * Math.sin(M) * (1 + e * Math.cos(M));
        double E1 = 0;
        //double tmp = (1 + e) / (1 - e);
        while (Math.abs(E - E1) > 0.000001){
        	E1 = E;
        	E = E1 - (E1 - e * Math.sin(E1) - M) / (1 - e * Math.cos(E1));
        }
        
        
        double V = ((Math.atan(Math.sqrt((1 + e) / (1 - e)) * (Math.sin(E / 2) / Math.cos(E / 2))) + Math.PI) % Math.PI)*2;
        double R = a * (1 - e * e) / (1 + e * Math.cos(V));

        x = R * (Math.cos(O) * Math.cos(V + o - O) - Math.sin(O) * Math.sin(V + o - O) * Math.cos(i));
        y = R * (Math.sin(O) * Math.cos(V + o - O) + Math.cos(O) * Math.sin(V + o - O) * Math.cos(i));
        z = R * (Math.sin(V + o - O) * Math.sin(i));
        
	}
	
	public void load_texture(){
		
		try {
            InputStream stream = getClass().getResourceAsStream("/imag/" + name + ".jpg");            
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "jpg");
            texture = TextureIO.newTexture(data);
            this.is_texture = true;
        }
        catch (IOException exc) {
            this.is_texture = false;
        }
	}
public void load_texture(String x){
		
		try {
            InputStream stream = getClass().getResourceAsStream("x");
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
            texture = TextureIO.newTexture(data);
            this.is_texture = true;
        }
        catch (IOException exc) {
            this.is_texture = false;
        }
	}
	
	
}

