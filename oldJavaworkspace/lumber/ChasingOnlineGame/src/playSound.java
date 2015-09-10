import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;

public class playSound
{
	static AudioClip sound;
	
	public void stop()
	{
		sound.stop();
	}
	
	public void playBombSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/bomb.wav"));
		sound.play();
	}
	
	public void playReadySound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/ready.wav"));
		sound.play();
	}
	
	public void playSelectSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/select.wav"));
		sound.play();
	}
	
	public void playExpandSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/expand.wav"));
		sound.play();
	}
	
	public void playKeySound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/turn.wav"));
		sound.play();
	}
	
	public void playHitSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/hit.wav"));
		sound.play();
	}
	
	public void playPassSound()
	{
		sound = Applet.newAudioClip(this.getClass().getResource("sounds/pass.wav"));
		sound.play();
	}
}
