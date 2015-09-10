package Bricks;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class PlainBrick extends Brick
{
	public PlainBrick()
	{
		pass = false;
		score = 150;
		setIcon(new ImageIcon(PlainBrick.class.getResource("/img/goldenBrick.png")));
	}
	public void hit()
	{
		delete();
	}
	public void reset() {}
	
	public void delete()
	{
		setBounds(this.getX()+1000, this.getY(), this.getWidth(), this.getY());
	}
}
