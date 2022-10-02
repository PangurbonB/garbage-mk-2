package garbageboys.garbageman_mk_2.Screens;

import java.util.List;

import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.App;
import garbageboys.garbageman_mk_2.Rendering.Render2D;
import garbageboys.garbageman_mk_2.Sound.SoundManager;
import garbageboys.garbageman_mk_2.Text.TextManager;
import garbageboys.garbageman_mk_2.Text.TextObject;

public class MapScreen implements Screen{
    private Render2D renderer;
	private App app;
	private SoundManager soundManager;
	private TextManager text;

    int counter = 0;

    Object title_background_frame;
    List<Object> loadedItems;

    final String MAP_THEME = "/assets/Sounds/Songs/Beachy.wav";

    @Override
    public void init(Render2D renderer, App app, SoundManager soundManager, TextManager text) {
		this.renderer = renderer;
		this.app = app;
		this.soundManager = soundManager;
		this.text = text;
        List<Object> loadedItems;
        List<TextObject> text_list;
        
    }

    @Override
    public void loadAssets() {
        title_background_frame = renderer.loadImage("/assets/Screens/garbagemanCity.png");
        
        loadedItems.add(title_background_frame);
    }

    @Override
    public void renderFrame(int frame) {
        MemoryStack stack = MemoryStack.stackPush();
        soundManager.loopSound(MAP_THEME);
        renderer.renderBatchStart();

        renderer.renderBatchEnd();
		counter++;
		stack.pop();
    }

    @Override
    public void unloadAssets() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeScreen() {
        // TODO Auto-generated method stub
        
    }
    
}
