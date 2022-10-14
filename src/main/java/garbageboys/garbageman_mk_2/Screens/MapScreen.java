package garbageboys.garbageman_mk_2.Screens;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.App;
import garbageboys.garbageman_mk_2.Models.Car;
import garbageboys.garbageman_mk_2.Models.Move;
import garbageboys.garbageman_mk_2.Models.SequenceParam;
import garbageboys.garbageman_mk_2.Models.Move.Direction;
import garbageboys.garbageman_mk_2.Models.Move.EdgeBehavior;
import garbageboys.garbageman_mk_2.Models.Move.FunctionName;
import garbageboys.garbageman_mk_2.Rendering.Render2D;
import garbageboys.garbageman_mk_2.Sound.SoundManager;
import garbageboys.garbageman_mk_2.Sound.SoundManager.SoundTypes;
import garbageboys.garbageman_mk_2.Text.TextManager;
import garbageboys.garbageman_mk_2.Text.TextObject;

public class MapScreen implements Screen {
    private Render2D renderer;
    private App app;
    private SoundManager soundManager;
    private TextManager text;

    private String nextScreen = "";
    private final String screen = "map";

    int counter = 0;

    private Object background_frame;
    private List<Car> cars;
    private List<Object> loadedItems;
    private List<TextObject> text_list;

    final String MAP_THEME = "/assets/Sounds/Songs/Gibbly(1).wav";
    final String[] CAR_FILES = { "/Assets/Cars/car-black.png",
            "/Assets/Cars/car-blue.png",
            "/Assets/Cars/car-red.png",
            "/Assets/Cars/car-white.png",
            "/Assets/Cars/car-green.png",
            "/Assets/Cars/car-lightblue.png",
            "/Assets/Cars/car-purple.png",
            "/Assets/Cars/car-yellow.png",
        };
    final int NUM_CARS = 16;
    final int NUM_COLORS = 4;

    @Override
    public void init(Render2D renderer, App app, SoundManager soundManager, TextManager text) {
        this.renderer = renderer;
        this.app = app;
        this.soundManager = soundManager;
        this.text = text;
        loadedItems = new ArrayList<Object>();
        text_list = new ArrayList<TextObject>();
        cars = new ArrayList<Car>();

    }

    @Override
    public void loadAssets() {
        TextObject temp_text_object;
        background_frame = renderer.loadImage("/assets/Screens/garbagemanCity.png");
        for (int i = 0; i < NUM_CARS; i++) {
            if (i < NUM_COLORS) {
                cars.add(new Car(renderer, renderer.loadImage(CAR_FILES[i])));
            } else if (i >= NUM_COLORS) {
                Car carSource = cars.get(i % NUM_COLORS);
                Object handleToDuplicate = carSource.getImg();
                Object duplicatedHandle = renderer.duplicateHandle(handleToDuplicate);
                cars.add(new Car(renderer, renderer.duplicateHandle(duplicatedHandle)));
            }
            loadedItems.add(cars.get(i).getImg());
        }
        soundManager.loadSound(MAP_THEME, SoundManager.SoundTypes.Music);
        loadedItems.add(background_frame);

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

        cars.get(4).setAngle((float)Math.PI / 2);
        cars.get(5).setAngle((float)Math.PI / 2);
        cars.get(6).setAngle((float)Math.PI / 2);
        cars.get(7).setAngle((float)Math.PI / 2);
        cars.get(12).setAngle(5.2f);

        List<SequenceParam> scriptedCar = new ArrayList<>();
        scriptedCar.add(new SequenceParam(FunctionName.LOOP));
        scriptedCar.add(new SequenceParam(FunctionName.TELEPORTTO, 0.005f, .42f));
        scriptedCar.add(new SequenceParam(FunctionName.MOVETO, .005f, .95f, 80f));
        scriptedCar.add(new SequenceParam(FunctionName.WAIT, 1));
        scriptedCar.add(new SequenceParam(FunctionName.ROTATE,  .01f, .95f, -100f, (float)Math.PI/2, .01f, (float)Math.PI));
        scriptedCar.add(new SequenceParam(FunctionName.MOVETO, .78f, .99f, 80f));
        scriptedCar.add(new SequenceParam(FunctionName.END));
        cars.get(14).setSequence(scriptedCar);

        List<SequenceParam> scriptedCar2 = new ArrayList<>();
        scriptedCar2.add(new SequenceParam(FunctionName.ROTATE,  .5f, .5f, -100f,  1000f, 0f, (float)Math.PI));
        scriptedCar2.add(new SequenceParam(FunctionName.END));
        cars.get(15).setSequence(scriptedCar2);
    }

    @Override
    public void renderFrame(int frame) {
        if(counter == 0) {//first render
            cars.get(0).teleportTo(.223f, .5f);
            cars.get(1).teleportTo(.216f, .75f);
            cars.get(10).teleportTo(.223f, 0f);
            cars.get(11).teleportTo(.216f,.25f);

            cars.get(2).teleportTo(.558f, .1f);
            cars.get(3).teleportTo(.551f, .4f);
            cars.get(8).teleportTo(.558f, .4f);
            cars.get(9).teleportTo(.551f, .9f);

            cars.get(4).teleportTo(.45f, .385f);
            cars.get(5).teleportTo(.7f, .373f);
            cars.get(6).teleportTo(.6f, .385f);
            cars.get(7).teleportTo(0f, .373f);

            cars.get(12).teleportTo(.34f, .0f);
            cars.get(13).teleportTo(.5f, .5f);
        }

        float speed = 80f;
        cars.get(14).runSequence();
        cars.get(15).runSequence();
        cars.get(0).moveAlongAxis(Direction.UP, EdgeBehavior.LOOP, speed);
        cars.get(1).moveAlongAxis(Direction.DOWN,EdgeBehavior.LOOP, speed);
        cars.get(10).moveAlongAxis(Direction.UP, EdgeBehavior.LOOP, speed);
        cars.get(11).moveAlongAxis(Direction.DOWN, EdgeBehavior.LOOP, speed);

        cars.get(2).moveAlongAxis(Direction.UP, EdgeBehavior.LOOP, speed);
        cars.get(3).moveAlongAxis(Direction.DOWN, EdgeBehavior.LOOP, speed);
        cars.get(8).moveAlongAxis(Direction.UP, EdgeBehavior.LOOP, speed);
        cars.get(9).moveAlongAxis(Direction.DOWN,EdgeBehavior.LOOP, speed);

        cars.get(4).moveAlongAxis(Direction.LEFT, EdgeBehavior.LOOP, speed);
        cars.get(5).moveAlongAxis(Direction.RIGHT, EdgeBehavior.LOOP, speed);
        cars.get(6).moveAlongAxis(Direction.LEFT, EdgeBehavior.LOOP, speed);
        cars.get(7).moveAlongAxis(Direction.RIGHT, EdgeBehavior.LOOP, speed);

        cars.get(13).moveInCircle(.27f, -0.02f, -.14f, speed /2, 0f);

        cars.get(12).moveTo(1f, .34f, speed);
        if(cars.get(12).getX() >= .99) {
            cars.get(12).teleportTo(.34f, 0f);
        }
        

        MemoryStack stack = MemoryStack.stackPush();
        soundManager.loopSound(MAP_THEME);
        renderer.renderBatchStart();

        for (int i = 0; i < text_list.size(); i++) {
            text.renderText(text_list.get(i));
        }

        renderer.batchImageScreenScaled(
                background_frame,
                0, 0.0f, 0.0f, 1.0f, 1.0f, 0);

        

        cars.get(0).show();
        cars.get(1).show();
        cars.get(10).show();
        cars.get(11).show();

        // in front of school
        cars.get(2).show();
        cars.get(3).show();
        cars.get(8).show();
        cars.get(9).show();

        // horizontal
        cars.get(4).show();
        cars.get(5).show();
        cars.get(6).show();
        cars.get(7).show();

        cars.get(12).show();
        cars.get(13).show();
        cars.get(14).show();
        cars.get(15).show();

        renderer.renderBatchEnd();
        counter++;
        stack.pop();
    }

    @Override
    public void unloadAssets() {
        for (int i = NUM_COLORS; i < NUM_CARS; i++) {
            renderer.deduplicateHandle(cars.get(i).getImg());
        }
        for (Object obj : loadedItems) {
            renderer.unloadImage(obj);
        }

    }

    @Override
    public void closeScreen() {
        unloadAssets();
        for (int i = 0; i < text_list.size(); i++)
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
