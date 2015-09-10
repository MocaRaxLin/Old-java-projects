package object.bodyComponent;

import javax.media.opengl.GL2;

public class StraightArm {
	private GL2 gl;
	public StraightArm(GL2 ingl, float size) {
		gl = ingl;
		gl.glTranslated(0.0*size, 0.0*size, -4.0*size);
		gl.glScaled(1.0*size, 2.0*size, 8.0*size);
        //以gl當下的繪圖點為中心 畫出一個長方體(|x|,|y|,|z|)
		new unitSquardCube(gl);
		gl.glScaled(1/(1.0*size), 1/(2.0*size), 1/(8.0*size));
		gl.glTranslated(0.0*size, 0.0*size, 4.0*size);
	}

}
