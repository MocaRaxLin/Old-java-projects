package object;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import object.bodyComponent.BentArm;
import object.bodyComponent.Body;
import object.bodyComponent.Hand;
import object.bodyComponent.Head;
import object.bodyComponent.Leg;
import object.bodyComponent.StraightArm;

public class People {
	private GL2 gl;
	private GLU glu;
	private float px;// positionX;
	private float py;// positionY;
	private float pz;// positionZ;
	private float size;// person size
	private int faceSide;// 0 = 0, 1 = pi/3, 2 = 2*pi/3, 3 = pi , 4 = 4*pi/3, 5
							// = 5*pi/3
	private float faceAngel;

	public People(GL2 ingl, GLU inglu, float x, float y, float z, int faceOn) {
		gl = ingl;
		glu = inglu;
		px = x;
		py = y;
		pz = z;
		size = 1;
		faceSide = faceOn;
		
		// point to position
		gl.glTranslatef(px, py, pz);
		
		// face on which side and its radius
		faceAngel = faceVector(faceSide);
		gl.glRotatef(-faceAngel, 0f, 0f, 1f);
		
		// person color
		gl.glColor3f(1.0f, 0.8f, 0.2f);

		// right leg
		gl.glTranslated(size * 0, size * (-1.5), size * 4);
		new Leg(gl, size);

		// left leg
		gl.glTranslated(size * 0, size * 3.0, size * 0);
		new Leg(gl, size);

		// main body
		gl.glTranslated(size * 0, -1.5 * size, 9.0 * size);
		new Body(gl, size);

		// left arm
		gl.glTranslated(0 * size, 4.5 * size, 4.0 * size);
		new StraightArm(gl, size);

		//left hand
		gl.glTranslated(0 * size, 0 * size, -9.5 * size);
		new Hand(gl, size);
		gl.glTranslated(0 * size, 0 * size, 9.5 * size);
		
		// right arm
		gl.glTranslated(0 * size, -9.0 * size, 0 * size);
		new BentArm(gl, size);

		
		//right hand---this program way is better
		gl.glTranslated(5.5 * size, 0 * size, -4 * size);
		gl.glRotatef(-90f, 0, 1, 0);
		new Hand(gl, size);
		gl.glRotatef(90f, 0, 1, 0);
		
		
		//draw wifi symbol
		gl.glColor3f(0.0f, 0.8f, 1.0f);
		gl.glTranslated(0 * size, 0 * size, 0.4 * size);
		new WifiSymbol(gl, size);
		gl.glTranslated(0 * size, 0 * size, -0.4 * size);
		
		gl.glTranslated(-5.5 * size, 0 * size, 4 * size);
		
		// head
		gl.glColor3f(1.0f, 0.8f, 0.2f);
		gl.glTranslated(0.0 * size, 4.5 * size, 3.0 * size);
		new Head(gl, glu, size);

		// back to start point
		gl.glTranslated(0.0 * size, 0.0 * size, -20.0 * size);
		gl.glRotatef(faceAngel, 0f, 0f, 1f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glTranslatef(-px, -py, -pz);
	}

	private float faceVector(int number) {
		if (number == 1)
			return 60;
		else if (number == 2)
			return 120;
		else if (number == 3)
			return 180;
		else if (number == 4)
			return 240;
		else if (number == 5)
			return 300;
		else {
			return 0;
		}
	}
}
