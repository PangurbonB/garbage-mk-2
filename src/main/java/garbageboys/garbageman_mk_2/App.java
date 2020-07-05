package garbageboys.garbageman_mk_2;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.ArrayList; 
import java.io.*; 

import org.lwjgl.*;
import org.lwjgl.glfw.GLFWJoystickCallback;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;

import garbageboys.garbageman_mk_2.SoundManager.SoundTypes;

public class App {

	Render2D renderer;
	TextManager text;
	SoundManager soundManager;
	final String STARTUP_SOUND = "/assets/Sounds/SoundEffects/Startup.wav";
	final String TITLE_THEME = "/assets/Sounds/Songs/Themey.wav";
	final String CHEERY = "/assets/Sounds/Songs/Cheery.wav";
	final String ICON0 = "/assets/Icons/Garbagecan0.png";
	final String ICON1 = "/assets/Icons/Garbagecan1.png";
	final String ICON2 = "/assets/Icons/Garbagecan2.png";
	final String ICON3 = "/assets/Icons/Garbagecan3.png";
	final String ICON4 = "/assets/Icons/Garbagecan4.png";

	public void run() {
		Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		game_loop_start();
		renderer.cleanup();
		System.out.println("Exiting\n");
	}

	private void init() {
		renderer = new RendererValidation(GarbageRenderer.class);
		//text = new TextLoader();
		renderer.initialize();
		
		soundManager = new DefaultSoundManager();
		
		
		soundManager.loadSound(STARTUP_SOUND, SoundManager.SoundTypes.Effects);
		soundManager.loadSound(TITLE_THEME, SoundManager.SoundTypes.Music);
		soundManager.loadSound(CHEERY, SoundManager.SoundTypes.Music);
		soundManager.setMasterVolume(-10f);
		soundManager.setTypeVolume(0f, SoundTypes.Effects, true);
		soundManager.setTypeVolume(10f, SoundTypes.Music, true);
		soundManager.playSound(STARTUP_SOUND);
		

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(renderer.getWindowID(), (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			}
			if (key == GLFW_KEY_TAB && action == GLFW_RELEASE) {
				
			}
		});
	}

	private void game_loop_start() {
		int frame = 0;

		GarbageRenderer real_renderer = (GarbageRenderer) ((RendererValidation) renderer).actual_renderer;
		real_renderer.setRenderMode(GarbageRenderer.RenderMode.VBLANK_SYNC);
		real_renderer.setIcon(ICON0);
		
		//example_render_init();
		title_screen_init();
		
		//Random random = new Random(System.nanoTime());
		boolean sleep = true;//random.nextBoolean();
		while (!glfwWindowShouldClose(renderer.getWindowID())) {
			long start = System.nanoTime();

			try {
				long sleep_time;
				if (sleep) {
					 sleep_time = renderer.getHintSleep() / 1000;
				} else {
					sleep_time = 0;
				}
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) { e.printStackTrace(); }
			glfwPollEvents();

			long render_start = System.nanoTime();
			//example_render(frame);
			title_screen_render(frame);
			long render_end = System.nanoTime();

			long end = System.nanoTime();
			//System.out.printf("Total Frame time %.2f ms Render Frame time %.2f ms\n",
			//		(end - start) / (1000f * 1000f),
			//		(render_end - render_start) / (1000f * 1000f));
			++frame;
		}

		//example_render_cleanup();
		title_screen_cleanup();
		
		System.out.println("Sleep was " + sleep);
	}

	Object play_button;
	List<Object> title_background_frames_1;
	List<Object> title_background_frames_2;
	List<Object> window_icons;
	boolean title_loop_complete = false;
	Object crafting_screen;
	Object customer_a_0;
	Object customer_a_1;
	Object customer_a_2;
	Object customer_a_3;
	Object customer_b_0;
	Object customer_b_1;
	Object customer_b_2;
	Object customer_b_3;

	int circle_x = 0;
	int circle_y = 0;
	
	int counter = 0;
	
	private void title_screen_init() {
		play_button = renderer.loadImage("/assets/Buttons/play.png");
		title_background_frames_1 = renderer.loadImageSeries("/assets/Screens/mainTitle.png", 384, 216, 23);
		title_background_frames_2 = renderer.loadImageSeries("/assets/Screens/mainTitle2.png", 384, 216, 10);
		renderer.refreshImages();
	}

	private void title_screen_cleanup() {
		renderer.unloadImage(play_button);
		soundManager.unloadAllSounds();
		for (Object obj : title_background_frames_1) {
			renderer.unloadImage(obj);
		}
		for (Object obj : title_background_frames_2) {
			renderer.unloadImage(obj);
		}
		//text.cleanupText();
	}

	private void title_screen_render(int frame) {
		MemoryStack stack = MemoryStack.stackPush();

		renderer.renderBatchStart();
		int title_frame;
<<<<<<< HEAD
		//text.openText("Does this work", 1f, 0, 0, 85, 100);
=======
		
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
		
>>>>>>> f91486be7e1ee03792341f83a3ce7744d2706392
		if (frame == title_background_frames_1.size() * 5) {
			title_loop_complete = true;
			soundManager.loopSound(TITLE_THEME);
		}
		if (frame == title_background_frames_1.size() * 5 + 100) {
			
//			soundManager.fadeOutSong(TITLE_THEME, 3000, -.6f);
//			soundManager.fadeInSong(CHEERY, SoundTypes.Music, 3000, .6f, true);
		}
		List<Object> current_frames;
		if (title_loop_complete) {
			current_frames = title_background_frames_2;
			renderer.batchImageScreenScaled(play_button, 1, 0.40f, 0.508f, 0.23f, 0.15f);
		}
		else {
			current_frames = title_background_frames_1;
		}
		
		title_frame = (frame / 5) % current_frames.size();
		renderer.batchImageScreenScaled(
				current_frames.get(title_frame),
				0, 0.0f, 0.0f, 1.0f, 1.0f);
		//renderer.batchImageScaled(title_background_frames.get(title_frame), 0, 0, 0, 384 * 8, 216 * 8);
		renderer.renderBatchEnd();
		counter++;
		stack.pop();
	}

	private void example_render(int frame) {
		MemoryStack stack = MemoryStack.stackPush();

		renderer.renderBatchStart();
		renderer.batchImageScreenScaled(crafting_screen, 0, 0f, 0f, 1f, 1f);
		renderer.batchImageScreenScaled(play_button, 1, 0.25f, 0.25f, 0.5f, 0.5f);

		int duration = 60 * 60;
		Object customer_a = null;
		switch ((frame / 30) % 4) {
			case 0:
				customer_a = customer_a_0;
				break;
			case 1:
				customer_a = customer_a_1;
				break;
			case 2:
				customer_a = customer_a_2;
				break;
			case 3:
				customer_a = customer_a_3;
				break;
		}
		float customer_a_x = (float) (frame % duration) / (duration - 1);
		Object customer_b = null;
		switch ((frame / 30) % 4) {
			case 0:
				customer_b = customer_b_0;
				break;
			case 1:
				customer_b = customer_b_1;
				break;
			case 2:
				customer_b = customer_b_2;
				break;
			case 3:
				customer_b = customer_b_3;
				break;
		}

		IntBuffer window_width = stack.mallocInt(1);
		IntBuffer window_height = stack.mallocInt(1);
		glfwGetWindowSize(renderer.getWindowID(), window_width, window_height);

		DoubleBuffer raw_mouse_x = stack.mallocDouble(1);
		DoubleBuffer raw_mouse_y = stack.mallocDouble(1);
		glfwGetCursorPos(renderer.getWindowID(), raw_mouse_x, raw_mouse_y);
		int mouse_x = (int) Math.floor(raw_mouse_x.get(0));
		int mouse_y = (int) Math.floor(raw_mouse_y.get(0));

		/*
		 * FloatBuffer axes = glfwGetJoystickAxes(GLFW_JOYSTICK_1); float joy_x =
		 * axes.get(0); float joy_y = -axes.get(1);
		 */
		int pos_x = (int) (window_width.get(0) * (0.8f * 0.5f * mouse_x + 0.5f));
		int pos_y = (int) (window_height.get(0) * (0.8f * 0.5f * mouse_y + 0.5f));

		renderer.batchImageScreenScaled(customer_a, 2, customer_a_x, 0f, 0.125f, 0.25f);
		renderer.batchImageScaled(customer_b, 3, mouse_x - 64, window_height.get(0) - mouse_y - 64, 128, 128);
		//renderer.batchImageScaled(customer_b, 3, pos_x - 64, pos_y - 64, 128, 128);
		renderer.renderBatchEnd();

		stack.pop();
	}

	private void example_render_init() {
		play_button = renderer.loadImage("/assets/Buttons/play.png");
		crafting_screen = renderer.loadImage("/assets/Screens/craftingScreen.png");
		List<Object> customer_a_series = renderer.loadImageSeries("/assets/Customers/Alan.png", 128, 128, 4);
		customer_a_0 = customer_a_series.get(0);
		customer_a_1 = customer_a_series.get(1);
		customer_a_2 = customer_a_series.get(2);
		customer_a_3 = customer_a_series.get(3);
		customer_b_0 = renderer.loadImage("/assets/Customers/Brett.png", 0, 0, 128, 128);
		customer_b_1 = renderer.loadImage("/assets/Customers/Brett.png", 128, 0, 128, 128);
		customer_b_2 = renderer.loadImage("/assets/Customers/Brett.png", 256, 0, 128, 128);
		customer_b_3 = renderer.loadImage("/assets/Customers/Brett.png", 384, 0, 128, 128);
		renderer.refreshImages();
	}

	private void example_render_cleanup() {
		renderer.unloadImage(play_button);
		renderer.unloadImage(crafting_screen);
		renderer.unloadImage(customer_a_0);
		renderer.unloadImage(customer_a_1);
		renderer.unloadImage(customer_a_2);
		renderer.unloadImage(customer_a_3);
		renderer.unloadImage(customer_b_0);
		renderer.unloadImage(customer_b_1);
		renderer.unloadImage(customer_b_2);
		renderer.unloadImage(customer_b_3);
	}

	public static void main(String[] args) {
		new App().run();
	}

}
