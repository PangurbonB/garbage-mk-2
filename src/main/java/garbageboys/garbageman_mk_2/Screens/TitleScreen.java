package garbageboys.garbageman_mk_2.Screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.App;
import garbageboys.garbageman_mk_2.Rendering.Render2D;
import garbageboys.garbageman_mk_2.Rendering.Render2D.InteractEvent;
import garbageboys.garbageman_mk_2.Sound.SoundManager;
import garbageboys.garbageman_mk_2.Text.TextManager;
import garbageboys.garbageman_mk_2.Text.TextObject;

public class TitleScreen implements Screen {

	private Render2D renderer;
	private App app;
	private SoundManager soundManager;
	private TextManager text;
	
	final private String screen = "title";
	private String nextScreen = "";
	
	int counter = 0;
	
	final String ICON0 = "/assets/Icons/Garbagecan0.png";
	final String ICON1 = "/assets/Icons/Garbagecan1.png";
	final String ICON2 = "/assets/Icons/Garbagecan2.png";
	final String ICON3 = "/assets/Icons/Garbagecan3.png";
	final String ICON4 = "/assets/Icons/Garbagecan4.png";
	final String TITLE_THEME = "/assets/Sounds/Songs/Themey.wav";
	
	Object play_button;
	List<Object> title_background_frames_1;
	List<Object> title_background_frames_2;
	boolean title_loop_complete = false;
	
	List<Object> loadedItems;
	
	List<TextObject> text_list;
	
	
	@Override
	public void init(Render2D renderer, App app, SoundManager soundManager, TextManager text) {
		this.renderer = renderer;
		this.app = app;
		this.soundManager = soundManager;
		this.text = text;
		loadedItems = new ArrayList<Object>();
		text_list = new ArrayList<TextObject>();
	}

	@Override
	public void loadAssets() {
		TextObject temp_text_object;
		play_button = renderer.loadImage("/assets/Buttons/play.png");
		title_background_frames_1 = renderer.loadImageSeriesTopLeft("/assets/Screens/mainTitle.png", 384, 216, 23);
		title_background_frames_2 = renderer.loadImageSeriesTopLeft("/assets/Screens/mainTitle2.png", 384, 216, 10);
		loadedItems.add(play_button);
		loadedItems.addAll(title_background_frames_1);
		loadedItems.addAll(title_background_frames_2);
		
		
		temp_text_object = text.openText("GARBAGEMAN: One man's trash is another's food.", .5f, .35f, .76f, .07f, .45f);
		text_list.add(temp_text_object);
		temp_text_object = text.openText("PLAY", 1.25f, .44f, .52f, .11f, .25f);
		text_list.add(temp_text_object);

	}

	@Override
	public void renderFrame(int frame) {
		
		MemoryStack stack = MemoryStack.stackPush();
		List<InteractEvent> events = new ArrayList<InteractEvent>();

		renderer.renderBatchStart();
		
		text.renderText(text_list.get(0));

		int title_frame;

		if(counter == 300) {
			renderer.setIcon(ICON1);
		}
		if(counter == 600) {
			renderer.setIcon(ICON2);
		}
		if(counter == 900) {
			renderer.setIcon(ICON3);
		}
		if(counter == 1200) {
			renderer.setIcon(ICON4);
		}
		if(counter == 1500) {
			renderer.setIcon(ICON0);
			counter = 0;
		}
		
		if (frame == title_background_frames_1.size() * 5) {
			title_loop_complete = true;
			soundManager.loadSound(SoundManager.TITLE_THEME, SoundManager.SoundTypes.Music);
			soundManager.loopSound(TITLE_THEME);
		}
		if (frame == title_background_frames_1.size() * 5 + 100) {
			
//			soundManager.fadeOutSong(TITLE_THEME, 3000, -.6f);
//			soundManager.fadeInSong(CHEERY, SoundTypes.Music, 3000, .6f, true);
		}
		List<Object> current_frames;
		if (title_loop_complete) {
			current_frames = title_background_frames_2;
			renderer.batchImageScreenScaled(play_button, 1, 0.40f, 0.508f, 0.23f, 0.15f, 0);
			text.renderText(text_list.get(1));
	
		}
		else {
			current_frames = title_background_frames_1;
		}
		
		title_frame = (frame / 5) % current_frames.size();
		renderer.batchImageScreenScaled(
				current_frames.get(title_frame),
				0, 0.0f, 0.0f, 1.0f, 1.0f, 0);
		//renderer.batchImageScaled(title_background_frames.get(title_frame), 0, 0, 0, 384 * 8, 216 * 8);
		renderer.renderBatchEnd();

		renderer.fillEventList(events);
		for(InteractEvent e : events) {
			//System.out.println(e.handle);
			if(e.handle != null) {
				if(e.handle.equals(play_button)) {
					nextScreen ="map";
					soundManager.fadeOutSong(TITLE_THEME, 1000, -.6f);
				}
			}
		}

		counter++;
		stack.pop();
		
	}

	public String nextScreen() {
		return nextScreen;
	}

	public String screen() {
		return screen;
	}

	@Override
	public void unloadAssets() {
		for(Object obj : loadedItems) {
			renderer.unloadImage(obj);
		}
	}

	@Override
	public void closeScreen() {
		unloadAssets();
		for(int i = 0; i < text_list.size(); i++)
			text.closeText(text_list.get(i));
		soundManager.unloadAllSounds();
	}
}
