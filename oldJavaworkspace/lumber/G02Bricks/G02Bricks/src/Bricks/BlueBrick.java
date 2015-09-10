package Bricks;

import javax.swing.ImageIcon;

public class BlueBrick extends Brick
{
	protected int defaultlife = 4, life = defaultlife;
	public BlueBrick()
	{
		score = 100;
		pass = false;
		setIcon(new ImageIcon(PlainBrick.class.getResource("/img/blueBrick0.png")));
	}
	public void hit()
	{
		life-=2;
		switch(life)
		{
			case 0:
				delete();
			case 2:
				setIcon(new ImageIcon(PlainBrick.class.getResource("/img/blueBrick1.png")));
		}
	}
	public void reset() {
		life = defaultlife;
		setIcon(new ImageIcon(PlainBrick.class.getResource("/img/blueBrick0.png")));
	}

	public void delete()
	{
		setBounds(this.getX()+1000, this.getY(), this.getWidth(), this.getY());
	}
}
