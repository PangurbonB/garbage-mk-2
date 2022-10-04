package garbageboys.garbageman_mk_2.Screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.App;
import garbageboys.garbageman_mk_2.Rendering.Render2D;
import garbageboys.garbageman_mk_2.Rendering.Render2D.InteractEvent;
import garbageboys.garbageman_mk_2.Sound.SoundManager;
import garbageboys.garbageman_mk_2.Text.TextManager;

public class OptionsScreen implements Screen {

	private Render2D renderer;
	private App app;
	private SoundManager soundManager;
	final private String screen = "options";
	private String nextScreen = "";
	
	List<Object> loadedItems;
	
	Object background;
	Object volumeSliderBG;
	Object volumeSliderKnob;
	
	@Override
	public void init(Render2D renderer, App app, SoundManager soundManager, TextManager text) {
		this.renderer = renderer;
		this.app = app;
		this.soundManager = soundManager;
		loadedItems = new ArrayList<Object>();
	}

	@Override
	public void loadAssets() {
		volumeSliderBG = renderer.loadImage("/assets/Sliders/DefaultSlider.png");
		volumeSliderKnob = renderer.loadImage("/assets/Sliders/DefaultKnob.png");
		loadedItems.add(volumeSliderBG);
		loadedItems.add(volumeSliderKnob);
	}

	@Override
	public void renderFrame(int frame) {

		List<InteractEvent> events = new ArrayList<InteractEvent>();

		renderer.renderBatchStart();
		MemoryStack stack = MemoryStack.stackPush();
		renderer.batchImageScreenScaled(volumeSliderBG, 1, 0.40f, 0.508f, 0.23f, 0.15f);
		
		
		renderer.renderBatchEnd();

		renderer.fillEventList(events);
		for(InteractEvent e : events) {
			System.out.println(e.handle);
			if(e.handle != null) {
				if(e.handle.equals(volumeSliderBG)) {
					System.out.println("MATCH!!!!!!!!");
				}
			}
		}

		stack.pop();
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
	}

	@Override
	public String nextScreen() {
		// TODO Auto-generated method stub
		return nextScreen;
	}

	@Override
	public String screen() {
		// TODO Auto-generated method stub
		return null;
	}

}
