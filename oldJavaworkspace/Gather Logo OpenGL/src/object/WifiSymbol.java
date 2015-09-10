package object;

import javax.media.opengl.GL2;

import object.wifiSymbolComponent.CurvedPattern;

public class WifiSymbol {
	private GL2 gl;
	private float size;
	private float[] radius;

	public WifiSymbol(GL2 ingl, float insize) {
		gl = ingl;
		size = insize;
		radius = new float[4];
		for(int i = 0;i<4;i++){
			radius[i] = i+1;
		}

		for(int i = 0;i<4;i++){
			new CurvedPattern(gl, size, radius[i], 16+4*i);
		}
		
	}

}
