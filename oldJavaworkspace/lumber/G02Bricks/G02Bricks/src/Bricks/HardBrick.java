package Bricks;

import javax.swing.ImageIcon;

public class HardBrick extends Brick
{
	public HardBrick()
	{
		score = 0;
		pass = false;
		setIcon(new ImageIcon(HardBrick.class.getResource("/img/hardBrick.png")));
	}
	public void hit() {}
	
	public void reset() {}
	
	public void delete()
	{
		setBounds(this.getX()+1000, this.getY(), this.getWidth(), this.getY());
	}
}
