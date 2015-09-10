

public class PersonalDetail {
	public float pos_x;
	public float pos_y;
	public float pos_z;
	public int face_on;

	public PersonalDetail(float x, float y, float z, int faceOn, float insize) {
		pos_x = x*insize;
		pos_y = y*insize;
		pos_z = z*insize;
		face_on = faceOn;
	}
}
