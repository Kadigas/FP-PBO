package id.ac.its.squealer.settings;

public class AudioSettings {
	private float musicVolume;
	private float sfxVolume;
	
	public AudioSettings(float musicVolume, float sfxVolume) {
		musicVolume = 0;
		sfxVolume = 0;
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = musicVolume;
	}

	public float getSfxVolume() {
		return sfxVolume;
	}

	public void setSfxVolume(float sfxVolume) {
		this.sfxVolume = sfxVolume;
	}
	
}
