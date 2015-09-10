package object.wifiSymbolComponent;

import javax.media.opengl.GL2;

public class CurvedPattern {
	private GL2 gl;
	private float size;
	private float radius;
	private int slice;
	private float partialAngel;
	private float beginningRotation;
	private float lengthX;

	public CurvedPattern(GL2 ingl, float insize, float r, int inslice) {
		gl = ingl;
		size = insize;
		radius = r;
		slice = inslice;
		partialAngel = 120 / slice;
		float t = (float)(slice - 1) / 2;
		beginningRotation = t * partialAngel;
		lengthX = (float) (2 * radius * Math.tan(Math
				.toRadians(partialAngel / 2)));

		//draw cuboid	
		gl.glRotatef(beginningRotation, 0, 1, 0);
		for (int i = 0; i < slice; i++) {
			new Cuboid(gl, size, radius, lengthX, 1.5f, 0.5f);
			gl.glRotated(-partialAngel, 0, 1, 0);
		}
		gl.glRotated(partialAngel, 0, 1, 0);
		
		
		gl.glRotatef(beginningRotation, 0, 1, 0);
	}

}
