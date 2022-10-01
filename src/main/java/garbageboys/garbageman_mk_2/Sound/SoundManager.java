package garbageboys.garbageman_mk_2.Sound;

import java.util.List;

import garbageboys.garbageman_mk_2.Sound.DefaultSoundManager.Sound;

public interface SoundManager {
	
	final public static String STARTUP_SOUND = "/assets/Sounds/SoundEffects/Startup.wav";
	final public static String TITLE_THEME = "/assets/Sounds/Songs/Themey.wav";
	final public static String CHEERY = "/assets/Sounds/Songs/Cheery.wav";
	final public static String TESTY1 = "/assets/Sounds/Songs/Testy1.wav";
	final public static String TESTY2 = "/assets/Sounds/Songs/Testy2.wav";

	enum SoundTypes {
		Music,
		Effects
	}

	/**
	 * Loads an audio file. E.x. - loadSound("/assets/Sounds/Songs/Cheery.wav", SoundManager.SoundTypes.Music)
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 * 
	 * @return true on success
	 */
	public Sound loadSound(String resource, SoundTypes type);

	/**
	 * Loads a list of audio files of a specific type
	 * @param resources
	 * @param type
	 * @return true on success
	 */
	public List<Sound> loadSounds(SoundTypes type, String...resources);

	/**
	 * Call after loading a set of files to prepare them for rendering.
	 */
	public void refreshSounds();

	/**
	 * Unloads an audio file, and cleans up resources.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 * 
	 * @return true on success
	 */
	public boolean unloadSound(String resource);
	
	/**
	 * Unloads a specific type of sound. E.g. unloadSoundType(SoundTypes.Music) would unload all loaded music.
	 * @param type the type of sound to be stopped - e.g. SoundTypes.Effects
	 * @return true on success
	 */
	public boolean unloadSoundType(SoundTypes type);
	
	/**
	 * Unloads all audio files currently loaded.
	 * @return true on success
	 */
	public boolean unloadAllSounds();

	/**
	 * Plays an audio file. The audio file will play until completion, or until stopped.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 * @return true if successful and there is no other copy of this sound playing
	 */
	public boolean playSound(String resource);
	
	/**
	 * Abrupty stops an audio file if it is currently playing. Audio file must be restarted.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 */
	public void stopSound(String resource);

	/**
	 * Pauses an audio file if it is currently playing.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 */
	public void pauseSound(String resource);

	/**
	 * Unpauses an audio file if it is currently playing.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 */
	public void unpauseSound(String resource);

	/**
	 * Stops all sounds of a specific type.
	 * @param type - e.g. SoundTypes.Effects
	 */
	public void resetSounds(SoundTypes type);
	
	/**
	 * Plays an audio file on loop, until it is stopped, unloaded or unlooped.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 * @return true if successful
	 */
	public boolean loopSound(String resource);

	/**
	 * Stops a currently looping sound, so that the sound plays to completion and does not repeat.
	 * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
	 */
    public void unloopSound(String resource);
    
    /**
     * Checks if an audio file is currently being played.
     * @param resource - e.g. "/assets/Sounds/Songs/Cheery.wav"
     * @return true if audio file is playing
     */
    public boolean isSoundRunning(String resource);
    
    public List<String> getRunningResources();

	//Volume Controls
    
	/**
	 * Sets volume for a specific sound type
	 * @param volume Volume to set sound type to
	 * @param type Specify type, e.g. SoundTypes.Music
	 * @param overrideRunningClips if true changes volume of currently playing clip
	 * @return true if successful
	 */
    public boolean setTypeVolume(float volume, SoundTypes type, boolean overrideRunningClips);

    public void setMasterVolume(float volume);
    
    public float getMasterVolume();
    
    public boolean fadeInSong(String resource, SoundTypes type, int millis, float intensity, boolean loop);

	public boolean fadeOutSong(String resource, int millis, float intensity);

	//Playlist functionality

	public boolean addToPlaylist(List<String> sounds, SoundTypes playlist);
	
	public boolean addToPlaylist(SoundTypes playlist, List<Sound> Sounds);

	public boolean addToPlaylist(String sound, SoundTypes playlist);

	public boolean clearPlaylist(SoundTypes playlist);

	public boolean clearPlaylists();

	public boolean skipSound(SoundTypes playlist);

	public boolean loopPlaylist(SoundTypes playlist, Boolean bool);

	public boolean startPlaylist(SoundTypes playlist);

	public boolean pausePlaylist(SoundTypes playlist);

	public boolean unpausePlaylist(SoundTypes playlist);

	public boolean killAll();
}
