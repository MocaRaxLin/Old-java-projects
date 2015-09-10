import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class timerLabel extends JLabel {
	
	static Font font = new Font("·L³n¥¿¶ÂÅé", Font.PLAIN, 100);
	int ms = 0, tenms;
	boolean running = false;
	Color defaultColor = new Color(20, 80, 165);
	
	public timerLabel()
	{
		setFont(font);
		setForeground(defaultColor);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setText("0");
	}
	
	public void start()
	{
		running = true;
		Timer timer = new Timer();
		TimerTask tt = new TimerTask()
		{
		      public void run()
		      {
		    	  if(running)
		    	  {
			    	  ms += 10;
			    	  setText(String.valueOf((int)(ms/1000)));
			    	  tenms = (ms%1000)/10;
		    	  }
		    	  else
		    		  this.cancel();
		      }
		};
		timer.schedule(tt,0,10);
	}
	
	public void stop()
	{
		running = false;
		setForeground(Color.WHITE);
	}
	
	public void reset()
	{
		ms = 0;
		setForeground(defaultColor);
	}
	
	public int getTenMs()
	{
		return tenms;
	}

}
