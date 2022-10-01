package garbageboys.garbageman_mk_2.Sound;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import garbageboys.garbageman_mk_2.ResourceLoader;

public class DefaultSoundManager implements SoundManager {

	private HashMap<SoundTypes, Float> volumes = new HashMap<SoundTypes, Float>();
	private HashMap<String, Sound> loadedClips = new HashMap<String, Sound>();
	private HashMap<String, Sound> runningClips = new HashMap<String, Sound>();
	private HashMap<SoundTypes, ArrayList<Sound>> queues = new HashMap<SoundTypes, ArrayList<Sound>>();
	private HashMap<SoundTypes, Sound> queuesPlaying = new HashMap<SoundTypes, Sound>();
	private HashMap<SoundTypes, Integer> queuesPlayingIndex = new HashMap<SoundTypes, Integer>();
	private HashMap<SoundTypes, Boolean> queuesLooped = new HashMap<SoundTypes, Boolean>();
	private float masterVol = 0f;
	
	public DefaultSoundManager() {
		volumes.put(SoundTypes.Effects, new Float(0f));
		volumes.put(SoundTypes.Music, new Float(0f));
	}
	
	/**
	 * Loads a sound given a resource name and a sound type. Don't include a full address, just do something like "/assets/Sounds/Songs/Chilly.wav/"
	 * For that example, the type would be SoundManager.Music
	 */
	@Override
	public Sound loadSound(String resource, SoundTypes type) {
		URL url = ResourceLoader.FindResourceURL(resource);
		AudioInputStream stream = null;
		Clip clip = null;
		
		try {
			stream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip(null);
			clip.open(stream);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
			return null;
		}
		
		Sound tc = new Sound(clip, type, resource);
		loadedClips.put(resource, tc);
		setVolume(volumes.get(type),tc,type);
		
		return tc;
	}

	@Override
	public List<Sound> loadSounds(SoundTypes type, String...resources) {
		ArrayList<Sound> sounds = new ArrayList<Sound>();
		for(String res : resources){
			sounds.add(loadSound(res, type));
		}
		return sounds;
	}

	/**
	 * Currently has no use. Might one day. Feel free to call it after you load a sound. Or don't.
	 */
	@Override
	public void refreshSounds() {

	}

	/**
	 * Plays a loaded sound. Throws a runtime exception if it isn't loaded/doesn't exist.
	 */
	@Override
	public boolean playSound(String resource) {
		return playSound(resource, false);
	}

	/**
	 * Plays a sound on infinite loop.
	 */
	@Override
	public boolean loopSound(String resource) {
		return playSound(resource, true);
	}


	private boolean playSound(String resource, boolean loop){
		Sound sound = loadedClips.get(resource);
		if(sound == null){
			throw new RuntimeException("Attempted to load a null clip");
		}
		if(runningClips.get(resource) != null) return false;
		
		runningClips.put(resource,sound);
		sound.setLoop(Clip.LOOP_CONTINUOUSLY);
		Thread thread = new Thread(sound);
		thread.start();
		return true;
	}

	/**
	 * Abruptly stops a sound.
	 */
	@Override
	public void stopSound(String resource) {
		Sound c = loadedClips.get(resource);
		c.stopClip();
	}

	/**
	 * Stops any sounds of the given type. E.g. resetSounds(SoundManager.Music) will stop the background music.
	 */
	@Override
	public void resetSounds(SoundTypes type) {
		for(Sound c : loadedClips.values()) { 
			if(c.type.equals(type)) { 
				c.stopClip();
			}
		}
	}

	

	/**
	 * Stops a sound on loop gracefully. That is to say, the sound/song or whatever will finish but not play again.
	 */
	@Override
	public void unloopSound(String resource) {
		Sound c = loadedClips.get(resource);
		c.unloop();
	}
	
	//TODO: Add pausing and unpausing to individual sounds
	@Override
	public void pauseSound(String resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unpauseSound(String resource) {
		// TODO Auto-generated method stub
		
	}


	//-------------------Playlists-------------------//

	@Override
	public boolean addToPlaylist(List<String> sounds, SoundTypes playlist) {
		if(queues.get(playlist) == null) queues.put(playlist, new ArrayList<Sound>());
		for(int i = 0; i < sounds.size(); i++){
			Sound clip = loadedClips.get(sounds.get(i));
			queues.get(playlist).add(clip);
		}
		return true;
	}

	@Override
	public boolean addToPlaylist(SoundTypes playlist, List<Sound> sounds) {
		if(queues.get(playlist) == null) queues.put(playlist, new ArrayList<Sound>());
		for(int i = 0; i < sounds.size(); i++){
			queues.get(playlist).add(sounds.get(i));
		}
		return true;
	}

	@Override
	public boolean addToPlaylist(String sound, SoundTypes playlist) {
		Sound clip = loadedClips.get(sound);
		queues.get(playlist).add(clip);
		return true;
	}

	@Override
	public boolean clearPlaylist(SoundTypes playlist) {
		queues.get(playlist).clear();
		return true;
	}

	@Override
	public boolean clearPlaylists() {
		for(ArrayList<Sound> c : queues.values()){
			c.clear();
		}
		return true;
	}

	@Override
	public boolean skipSound(SoundTypes playlist) {
		queuesPlaying.get(playlist).stopClip();
		return true;
	}

	@Override
	public boolean loopPlaylist(SoundTypes playlist, Boolean bool) {
		queuesLooped.put(playlist, bool);
		return true;
	}

	//Simple recursive method that creates a thread, plays the song on that thread, then calls itself before disposing the thread.
	private void playSong(ArrayList<Sound> queue, SoundTypes playlist, int index){
		if(queue.size() <= index){ //If queue has been iterated thru, either loop it or end.
			if(queuesLooped.get(playlist) != null && queuesLooped.get(playlist)){
				index = 0;
			}
			else{
				return;
			}
		}

		queue.set(index, loadSound(queue.get(index).resource, playlist));

		queue.get(index).index = index;
		queue.get(index).queue = queue;

		Thread thread = new Thread(queue.get(index));
		runningClips.put(queue.get(index).resource, queue.get(index));
		queuesPlaying.put(playlist, queue.get(index));
		thread.start();


	}

	@Override
	public boolean startPlaylist(SoundTypes playlist) {
		ArrayList<Sound> queue = queues.get(playlist);
		playSong(queue, playlist, 0);
		return true;
	}

	//TODO: Add pausing/unpausing to playlists
	@Override
	public boolean pausePlaylist(SoundTypes playlist) {

		queuesPlaying.get(playlist).stopClip();
		return false;
	}

	@Override
	public boolean unpausePlaylist(SoundTypes playlist) {
		// TODO Auto-generated method stub
		return false;
	}



	/**
	 * Unloads a loaded sound. Probably useful for doing something like unloading the intro roll.
	 */
	@Override
	public boolean unloadSound(String resource) {
		Sound clip = loadedClips.get(resource);
		clip.stopClip();
		loadedClips.remove(resource);
		return true;
	}

	/**
	 * Unloads all sounds of a certain type. Very niche, may never be used.
	 */
	@Override
	public boolean unloadSoundType(SoundTypes type) {
		for(String s : loadedClips.keySet()) {
			Sound c = loadedClips.get(s);
			if(c.type.equals(type)) {
				c.stopClip();
				c.unload();
				loadedClips.remove(s);
				runningClips.remove(s);
			}
		}
		return true;
	}

	/**
	 * Unloads all sounds. Probably useful if we have an option to turn off all sounds, and could be called on exit.
	 */
	@Override
	public boolean unloadAllSounds() {
		for(Sound c : loadedClips.values()) {
			c.stopClip();
			c.unload();
		}
		queues.clear();
		loadedClips.clear();
		runningClips.clear();
		return true;
	}

	/**
	 * An internal class that acts as a wrapper for a sound Clip object. Includes the sound's type, and not much else at the moment.
	 * Might contain other info such as song length/size in the future.
	 * @author Pangur
	 *
	 */
	public class Sound implements Runnable {
		
		public SoundTypes type;
		private FloatControl volControl;
		private Clip clip;
		public String resource;
		
		private boolean running = false;
		private boolean fading = false;
		private boolean fadein = false;
		
		private final int THREAD_SLEEP_TIME = 100;
		
		private float millisLeft;
		private float fadeIntensity;
		private float intensity;
		
		private int loopTimes = 0;

		public ArrayList<Sound> queue;
		public int index;
	
		public Sound(Clip clip, SoundTypes type, String resource) {
			this.clip = clip;
			this.type = type;
			this.resource = resource;
			volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		}
		
		public void setLoop(int b) {
			this.loopTimes = b;
		}

		public void unloop() {
			this.clip.loop(0);
		}
		
		public void unload() {
			clip.close();
		}

		public Clip getClip() {
			return this.clip;
		}
		
		public SoundTypes getType() {
			return this.type;
		}

		@Override
		public void run() {
			clip.loop(loopTimes);
			System.out.println("Started running: "+resource+ (loopTimes == 0 ? " ONCE" : " ON LOOP"));
			while(!clip.isRunning()) {
				try {
					Thread.sleep(1);					//This INFINITELY stupid sleep is because the thread associated with Java's clips doesn't initialize 
				} catch (InterruptedException e1) {		//immediately or something. If this is taken out, if(!clip.isRunning()) will fail, and things get 
					e1.printStackTrace();				//screwed up. Very stupid fix, would be inefficient if this was called more than once per song.
				}
			}
			this.running = true;
			while(running) {
				try {

					if(fading || fadein) { //Fading logic
						this.millisLeft -= THREAD_SLEEP_TIME;
						modulateIntensity();
						if(millisLeft <= 0 && fading) {
							System.out.println("Faded out "+type+": "+resource);
							stopClip();
							fading = false;
						}
						if(millisLeft <= 0 && fadein) {
							System.out.println("Faded in "+type+": "+resource);
							FloatControl volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
							volControl.setValue(masterVol + volumes.get(type));
							fadein = false;
						}
					}

					if(!clip.isRunning()) { //Exit loop
						System.out.println("Stopped running: "+resource);
						this.running = false;
					}

					Thread.sleep(THREAD_SLEEP_TIME); //Sleep time for checking up on whether the clip is done/fading in and out
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(queue != null) playSong(queue, this.type, index + 1);
			//Thread dies when sound finishes and run() stops.
		}
		
		public void stopClip(){
			clip.stop();
			this.running = false;
		}

		//TODO: add support for pausing/unpausing clips
		
		public void doFadeOut(int millis, float fadeIntensity) {
			System.out.println("Fading out "+type+": "+resource);
			this.millisLeft = (float) millis;
			this.fadeIntensity = fadeIntensity;
			fading = true;
			this.intensity = volumes.get(type) + masterVol;
		}
		
		public void doFadeIn(int millis, float fadeIntensity) {
			System.out.println("Fading in "+type+": "+resource);
			this.millisLeft = (float) millis;
			this.fadeIntensity = fadeIntensity;
			fadein = true;
			this.intensity = masterVol + volumes.get(type) - (fadeIntensity * (millis/100));
		}
		
		private void modulateIntensity() {
			this.intensity += fadeIntensity;
			volControl.setValue(this.intensity);
		}
	}

	
	@Override
	public boolean isSoundRunning(String resource) {
		Clip c = loadedClips.get(resource).clip;
		return c.isRunning();
	}

	@Override
	public List<String> getRunningResources() {
		List<String> list = new ArrayList<String>();
		for(String s : loadedClips.keySet()) {
			Sound c = loadedClips.get(s);
			if(c.clip.isRunning()) {
				list.add(s);
			}
		}
		return list;
	}

	@Override
	public boolean setTypeVolume(float volume, SoundTypes type, boolean overrideRunningClips) {
		volumes.replace(type, volume);
		for(Sound c : loadedClips.values()) {
			if(c.type.equals(type) && (overrideRunningClips || !c.clip.isRunning())) {
				setVolume(volume, c, type);
			}
		}
		return true;
	}
	
	private void setVolume(float volume, Sound clip, SoundTypes type) {
		FloatControl volControl = (FloatControl) clip.clip.getControl(FloatControl.Type.MASTER_GAIN);
		volControl.setValue(masterVol + volumes.get(type));
	}


	//TODO: Make master volume make sense
	@Override
	public void setMasterVolume(float volume) {
		masterVol = volume;
		for(SoundTypes type : SoundTypes.values()) {
			setTypeVolume(volumes.get(type), type, true);
		}
	}
	
	public float getMasterVolume() {
		return masterVol;
	}

	@Override
	public boolean fadeOutSong(String resource, int millis, float intensity) {
		Sound c = runningClips.get(resource);
		if(c == null) return false;
		c.doFadeOut(millis, intensity);
		return true;
	}

	@Override
	public boolean fadeInSong(String resource, SoundTypes type, int millis, float intensity, boolean loop) {
		Sound c = loadedClips.get(resource);
		if(c == null) return false;
		if(loop) {
			loopSound(resource);
		}
		else{
			playSound(resource);
		}
		FloatControl volControl = (FloatControl) c.clip.getControl(FloatControl.Type.MASTER_GAIN);
		volControl.setValue(masterVol + volumes.get(type) - (intensity * (millis/100)));
		c.doFadeIn(millis, intensity);
		return true;
	}

	@Override
	public boolean killAll() {
		clearPlaylists();
		unloadAllSounds();
		return true;
	}

	
	
}
