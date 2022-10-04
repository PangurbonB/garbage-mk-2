package garbageboys.garbageman_mk_2.Screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.App;
import garbageboys.garbageman_mk_2.Rendering.Render2D;
import garbageboys.garbageman_mk_2.Sound.SoundManager;
import garbageboys.garbageman_mk_2.Sound.SoundManager.SoundTypes;
import garbageboys.garbageman_mk_2.Text.TextManager;
import garbageboys.garbageman_mk_2.Text.TextObject;

public class MapScreen implements Screen{
    private Render2D renderer;
	private App app;
	private SoundManager soundManager;
	private TextManager text;

    private String nextScreen = "";
    private String screen = "map";

    int counter = 0;

    Object background_frame;
    List<Object> loadedItems;
    List<TextObject> text_list;

    final String MAP_THEME = "/assets/Sounds/Songs/Beachy.wav";

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
        background_frame = renderer.loadImage("/assets/Screens/garbagemanCity.png");
        soundManager.loadSound(MAP_THEME, SoundManager.SoundTypes.Music);
        loadedItems.add(background_frame);
    }

    @Override
    public void renderFrame(int frame) {
        MemoryStack stack = MemoryStack.stackPush();
        soundManager.loopSound(MAP_THEME);
        renderer.renderBatchStart();
        renderer.batchImageScreenScaled(
				background_frame,
				0, 0.0f, 0.0f, 1.0f, 1.0f);
        renderer.renderBatchEnd();
		counter++;
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
        return screen;
    }
    
}
