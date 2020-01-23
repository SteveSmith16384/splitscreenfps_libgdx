package com.scs.splitscreenfps;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {

	private Music music;
	private HashMap<String, Sound> sounds; 
	private float musicVolume;


	public Audio() {
		sounds = new HashMap<String, Sound>();

		/*for(String s : preload) {
			String filename = "audio/" +s+".wav";
			Sound sfx = Gdx.audio.newSound(Gdx.files.internal(filename));
			sounds.put(s, sfx);
		}*/

		//music = Gdx.audio.newMusic(Gdx.files.internal("audio/orbital_colossus.mp3"));
		//music.setLooping(true);
	}


	public void update() {
		if (music != null) {
			//if (Game.game_stage == 0) {
			musicVolume = Math.min(musicVolume + Gdx.graphics.getDeltaTime() / 2f, 1.0f);
			music.setVolume(musicVolume);
			/*} else {
			musicVolume = Math.max(musicVolume-Gdx.graphics.getDeltaTime()/4f, 0f);
			music.setVolume(musicVolume);
		}*/
		}
	}


	public void startMusic(String filename) {
		if (music != null) {
			music.stop();
			music.dispose();
		}
		//if (!music.isPlaying()) {
		music = Gdx.audio.newMusic(Gdx.files.internal(filename));//orbital_colossus.mp3"));
		music.setLooping(true);
		music.play();
		music.setVolume(0f);
		musicVolume = 0f;
		//}
	}


	public void stopMusic() {
		if (music != null) {
			music.stop();
		}
	}


	public void play(String name) {
		if (sounds.containsKey(name)) {
			sounds.get(name).play();
		} else {
			String filename = "audio/" + name;// + ".wav";
			if (filename.indexOf(".") < 0) {
				filename = filename + ".wav";
			}
			Sound sfx = Gdx.audio.newSound(Gdx.files.internal(filename));
			sounds.put(name, sfx);
			//System.out.println("Sound " + name + " not preloaded");
			play(name); // Loop round to play the newly-added file.
		}

	}


	public void dipose() {
		for (Sound s : this.sounds.values()) {
			s.dispose();
		}
	}

}
