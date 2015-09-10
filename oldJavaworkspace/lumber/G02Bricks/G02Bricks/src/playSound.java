import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

public class playSound
{
	static AudioClip sound;
	
	public void setMusic(int musicIndex)
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/DemonPriest.wav"));
		switch(musicIndex)
		{
			case 1:
				stop();
				sound = Applet.newAudioClip(this.getClass().getResource("sounds/DemonPriest.wav"));
				sound.loop();
				break;
			case 0:
				stop();
				sound = Applet.newAudioClip(this.getClass().getResource("sounds/win.wav"));
				sound.play();
				break;
		}
	}
	
	public void stop()
	{
		sound.stop();
	}
	
	public void playStartSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/start.wav"));
		sound.play();
	}
	
	public void playServeSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/serve.wav"));
		sound.play();
	}
	
	public void playHitSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/hit.wav"));
		sound.play();
	}
	
	public void playHitStickSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/hitStick.wav"));
		sound.play();
	}
	
	public void playHitWallSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/hitWall.wav"));
		sound.play();
	}
	
	public void playFallSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/fall.wav"));
		sound.play();
	}
	
	public void playFailSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/fail.wav"));
		sound.play();
	}
	
	public void playStageSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/stage.wav"));
		sound.play();
	}
	
	public void playWinSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/win.wav"));
		sound.play();
	}
}
