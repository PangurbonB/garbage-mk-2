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
    private final String screen = "map";

    int counter = 0;

    Object background_frame;
    List<Object> cars;
    List<Object> loadedItems;
    List<TextObject> text_list;

    final String MAP_THEME = "/assets/Sounds/Songs/Beachy.wav";
    final String[] CAR_FILES = {"/Assets/Cars/car-black.png",
    "/Assets/Cars/car-blue.png",
    "/Assets/Cars/car-red.png",
    "/Assets/Cars/car-white.png"};
    final int NUM_CARS = 4;

    @Override
    public void init(Render2D renderer, App app, SoundManager soundManager, TextManager text) {
		this.renderer = renderer;
		this.app = app;
		this.soundManager = soundManager;
		this.text = text;
        loadedItems = new ArrayList<Object>();
        text_list = new ArrayList<TextObject>();
        cars = new ArrayList<Object>();
        
    }

    @Override
    public void loadAssets() {
        background_frame = renderer.loadImage("/assets/Screens/garbagemanCity.png");
        for(int i =0;i < NUM_CARS; i++) {
                cars.add(renderer.loadImage(CAR_FILES[i % NUM_CARS]));
        }
        soundManager.loadSound(MAP_THEME, SoundManager.SoundTypes.Music);
        loadedItems.add(background_frame);
        loadedItems.addAll(cars);
    }

    @Override
    public void renderFrame(int frame) {
        MemoryStack stack = MemoryStack.stackPush();
        soundManager.loopSound(MAP_THEME);
        renderer.renderBatchStart();
        renderer.batchImageScreenScaled(
				background_frame,
				0, 0.0f, 0.0f, 1.0f, 1.0f);
        renderer.batchImageScreenScaled(cars.get(0), 1, .223f, (float)(frame % 1000) / 1000, .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(1), 1, .216f, -((float)(frame % 1000) / 1000) + 1 , .005f, .016f);
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
