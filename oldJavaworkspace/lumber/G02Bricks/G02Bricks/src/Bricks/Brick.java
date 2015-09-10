package Bricks;
import javax.swing.JLabel;

public abstract class Brick extends JLabel
{
	public int score;
	public boolean pass;
	public abstract void hit();
	public abstract void delete();
	public abstract void reset();
}
