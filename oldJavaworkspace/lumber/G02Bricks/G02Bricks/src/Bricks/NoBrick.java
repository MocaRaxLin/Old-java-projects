package Bricks;

@SuppressWarnings("serial")
public class NoBrick extends Brick
{
	public NoBrick()
	{
		score = 0;
		pass = true;
	}
	public void hit() {}
	
	public void reset() {}
	
	public void delete()
	{
		setBounds(this.getX()+1000, this.getY(), this.getWidth(), this.getY());
	}
}
