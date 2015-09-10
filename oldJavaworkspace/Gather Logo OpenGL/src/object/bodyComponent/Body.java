package object.bodyComponent;

import javax.media.opengl.GL2;

public class Body {
	private GL2 gl;
	public Body(GL2 ingl, float size) {
		gl = ingl;
		gl.glScaled( size*1.0,size*5.0, size*8.0);
        //類似以gl當下的繪圖點為中心 畫出一個長方體(|x|,|y|,|z|)
		new unitSquardCube(gl);
		gl.glScaled( 1/(size*1.0),1/(size*5.0), 1/(size*8.0));
	}

}
