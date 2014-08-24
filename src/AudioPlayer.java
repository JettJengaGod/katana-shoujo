package audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer 
{
	private Clip clip;
	private int frameNumber = 0;
	private double volume = .5;
		
	public AudioPlayer(String s)
	{
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
		
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false
					);
			AudioInputStream dais = 
					AudioSystem.getAudioInputStream(
							decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isActive()
	{
		if(clip == null)
		{
			return false;
		}
		return clip.isActive();
	}
	
	public void play()
	{
		if(clip == null)
		{
			return;
		}
		//stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop()
	{
		if(clip.isRunning())
		{
			clip.close();
		}
	}
	
	public void pause()
	{
		if(clip.isRunning())
		{
			frameNumber = clip.getFramePosition();
			clip.stop();
		}
		else
		{
			System.out.println("Started again");
			clip.start();
			clip.setFramePosition(frameNumber);
		}
	}
	
	public void volumeUp()
	{
		volume = volume + 0.1;
		if(volume >= 1.0)
		{
			volume = 1;
		}
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    double gain = volume; // number between 0 and 1 (loudest)
	    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
	    gainControl.setValue(dB);
	}
	
	public void volumeDown()
	{
		volume = volume - 0.1;
		if(volume <= 0.0)
		{
			volume = 0;
		}
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    double gain = volume; // number between 0 and 1 (loudest)
	    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
	    gainControl.setValue(dB);
	}
	
	public void volumeMute()
	{
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    double gain = 0; // number between 0 and 1 (loudest)
	    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
	    gainControl.setValue(dB);
	}
}