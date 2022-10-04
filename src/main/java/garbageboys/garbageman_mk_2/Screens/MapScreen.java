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
    final int NUM_CARS = 12;
    final int NUM_COLORS = 4;

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
        TextObject temp_text_object;
        background_frame = renderer.loadImage("/assets/Screens/garbagemanCity.png");
        for(int i =0; i < NUM_CARS; i++) {
                if(i < NUM_COLORS) {
                    cars.add(renderer.loadImage(CAR_FILES[i]));
                } else if(i >= NUM_COLORS) {
                    cars.add(renderer.duplicateHandle(cars.get(i % NUM_COLORS)));
                }
        }
        soundManager.loadSound(MAP_THEME, SoundManager.SoundTypes.Music);
        loadedItems.add(background_frame);
        loadedItems.addAll(cars);

        temp_text_object = text.openText("Welmert", .25f, .03f, .12f, .07f, .45f);
		text_list.add(temp_text_object);
		temp_text_object = text.openText("Granted High School", .25f, .24f, .83f, .11f, .25f);
		text_list.add(temp_text_object);
        temp_text_object = text.openText("Bourgeoiselwood", .25f, .79f, .675f, .11f, .25f);
		text_list.add(temp_text_object);
        temp_text_object = text.openText("Basecamp", .25f, .475f, .3f, .11f, .25f);
		text_list.add(temp_text_object);
        temp_text_object = text.openText("Park", .25f, .1f, .27f, .11f, .25f);
		text_list.add(temp_text_object);
    }

    @Override
    public void renderFrame(int frame) {
        MemoryStack stack = MemoryStack.stackPush();
        soundManager.loopSound(MAP_THEME);
        renderer.renderBatchStart();

        for(int i = 0; i < text_list.size();i++) {
            text.renderText(text_list.get(i));
        }

        renderer.batchImageScreenScaled(
				background_frame,
				0, 0.0f, 0.0f, 1.0f, 1.0f);
        renderer.batchImageScreenScaled(cars.get(0), 1, .223f, (float)(frame % 1000) / 1000, .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(1), 1, .216f, -((float)((frame + 500) % 1000) / 1000) + 1 , .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(10), 1, .223f, (float)((frame + 600) % 1000) / 1000, .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(11), 1, .216f, -((float)((frame + 750) % 1000) / 1000) + 1 , .005f, .016f);
        
        //in front of school
        renderer.batchImageScreenScaled(cars.get(2), 1, .558f, (float)((frame + 650)% 1000) / 1000, .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(3), 1, .551f, -((float)((frame + 650) % 1000) / 1000) + 1 , .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(8), 1, .558f, (float)((frame + 200)% 1000) / 1000, .005f, .016f);
        renderer.batchImageScreenScaled(cars.get(9), 1, .551f, -((float)((frame + 150) % 1000) / 1000) + 1 , .005f, .016f);
        
        //horizontal
        renderer.batchImageScreenScaled(cars.get(4), 1, (float)((frame + 100)% 1600) / 1600, .375f, .011f, .008f);
        renderer.batchImageScreenScaled(cars.get(5), 1, -((float)((frame + 500) % 1600) / 1600) + 1, .39f, .011f, .008f);
        renderer.batchImageScreenScaled(cars.get(6), 1, (float)((frame + 1100)% 1600) / 1600, .375f, .011f, .008f);
        renderer.batchImageScreenScaled(cars.get(7), 1, -((float)((frame + 25) % 1600) / 1600) + 1, .39f, .011f, .008f);
        
        renderer.renderBatchEnd();
		counter++;
		stack.pop();
    }

    @Override
    public void unloadAssets() {
        for(int i = NUM_COLORS; i < NUM_CARS; i++ ) {
            renderer.deduplicateHandle(cars.get(i));
        }
        for(Object obj : loadedItems) {
			renderer.unloadImage(obj);
		}
        
        
    }

    @Override
    public void closeScreen() {
        unloadAssets();
        for(int i = 0; i < text_list.size(); i++)
			text.closeText(text_list.get(i));
        
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
