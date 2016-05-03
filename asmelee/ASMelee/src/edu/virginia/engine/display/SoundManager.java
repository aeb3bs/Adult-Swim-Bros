package edu.virginia.engine.display;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	HashMap<String,String>soundEffects = new HashMap<String,String>();
	HashMap<String,String>songs = new HashMap<String,String>();
	HashMap<String,Clip>clips = new HashMap<String,Clip>();
	public void LoadSoundEffect(String id, String filename) throws LineUnavailableException
	{
		Clip clip = AudioSystem.getClip();
		String path = "resources" + File.separator + filename;
		soundEffects.put(id, path);
	}
	public void PlaySoundEffect(String id) throws LineUnavailableException, UnsupportedAudioFileException, IOException
	{
		File soundFile = new File(soundEffects.get(id));
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
		Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
	}
	public void LoadMusic(String id, String filename) throws LineUnavailableException
	{
		Clip clip = AudioSystem.getClip();
		String path = "audio_resources" + File.separator + filename;
		songs.put(id, path);
	}
	public void PlayMusic(String id, boolean loops) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		File soundFile = new File(songs.get(id));
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile); 
		Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        if(loops)
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
        {
        	clip.start();
        }
        clips.put(id, clip);
	}
	public void StopMusic(String id)
	{
		Clip clip = clips.get(id);
		if(clip!=null)
		{
			clip.close();
		}
	}
	public void StopAllMusic()
	{
		for(String s: clips.keySet())
			StopMusic(s);
	}
}
