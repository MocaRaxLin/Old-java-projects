package object.bodyComponent;

import javax.media.opengl.GL2;

public class Leg {
	private GL2 gl;
	public Leg(GL2 ingl, float size){		
		gl = ingl;
		gl.glScaled( 1.0*size,2.0*size, 8.0*size);
		
        //�Hgl��U��ø���I������ �e�X�@�Ӫ�����(|x|,|y|,|z|)
		new unitSquardCube(gl);
		
		gl.glScaled( 1/(1.0*size),1/(2.0*size), 1/(8.0*size));
	}
}
