package object.wifiSymbolComponent;

import javax.media.opengl.GL2;

import object.bodyComponent.unitSquardCube;

public class Cuboid {
	private GL2 gl;
	private float size;
	private float radius;
	private float l_x;
	private float l_y;
	private float l_z;

	public Cuboid(GL2 ingl, float insize, float r, float x, float y, float z) {
		gl = ingl;
		size = insize;
		radius = r;
		l_x = x;
		l_y = y;
		l_z = z;

		gl.glTranslated(0*size, 0*size,(radius-l_z/2)*size);
		gl.glScaled(l_x*size, l_y*size, l_z*size);
		new unitSquardCube(gl);
		gl.glScaled(1/l_x*size, 1/l_y*size, 1/l_z*size);
		gl.glTranslated(0*size, 0*size, -(radius-l_z/2)*size);
		
	}

}
