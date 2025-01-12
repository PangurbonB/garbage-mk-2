package garbageboys.garbageman_mk_2.Rendering;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class RendererValidation implements Render2D {

	Class<? extends Render2D> clazz;
	public RendererValidation(Class<? extends Render2D> clazz) {
		this.clazz = clazz;
	}

	private class ImageInfo {
		Object object;
		boolean loaded;
	}

	public Render2D actual_renderer;
	List<ImageInfo> images;
	boolean batch_mode = false;
	
	private void validate_batch_resource(Object image) {
		if (batch_mode) {
			ImageInfo info = find_info(image);
			if (info != null) {
				/* success */
			} else {
				throw new RuntimeException();
			}
		} else {
			throw new RuntimeException();
		}
	}

	private ImageInfo find_info(Object object) {
		for (ImageInfo info : images) {
			if (info.object == object) {
				return info;
			}
		}
		return null;
	}

	@Override
	public void initialize() {
		try {
			actual_renderer = clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException();
		}
		actual_renderer.initialize();
		
		images = new ArrayList<ImageInfo>();
	}

	@Override
	public void cleanup() {
		actual_renderer.cleanup();
		if (images.size() != 0) {
			throw new RuntimeException();
		}
	}

	@Override
	public long getWindowID() {
		return actual_renderer.getWindowID();
	}

	@Override
	public Object loadImage(String resource) {
		ImageInfo info = new ImageInfo();
		info.object = actual_renderer.loadImage(resource);
		info.loaded = false;
		images.add(info);
		return info.object;
	}

	@Override
	public Object loadImage(String resource, int x, int y, int width, int height) {
		ImageInfo info = new ImageInfo();
		info.object = actual_renderer.loadImage(resource, x, y, width, height);
		info.loaded = false;
		images.add(info);
		return info.object;
	}

	@Override
	public void refreshImages() {
		for (ImageInfo info : images) {
			info.loaded = true;
		}
		actual_renderer.refreshImages();
	}

	@Override
	public void unloadImage(Object image) {
		if (find_info(image) == null) {
			throw new RuntimeException();
		}
		for (int i = 0; i < images.size(); ++i) {
			if (images.get(i).object == image) {
				images.remove(i);
				break;
			}
		}
		actual_renderer.unloadImage(image);
	}

	@Override
	public void renderBatchStart() {
		batch_mode = true;
		for (ImageInfo info : images) {
			assert(info.loaded);
		}
		actual_renderer.renderBatchStart();
	}

	@Override
	public void renderBatchEnd() {
		actual_renderer.renderBatchEnd();
		batch_mode = false;
	}

	@Override
	public void batchImage(Object image, int layer, int x, int y, float angle) {
		validate_batch_resource(image);
		actual_renderer.batchImage(image, layer, x, y, angle);
	}

	@Override
	public void batchImageScaled(Object image, int layer, int x, int y, int width, int height, float angle) {
		validate_batch_resource(image);
		actual_renderer.batchImageScaled(image, layer, x, y, width, height, angle);
	}

	@Override
	public void batchImageScreenScaled(Object image, int layer, float x, float y, float width, float height, float angle) {
		//validate_batch_resource(image);
		actual_renderer.batchImageScreenScaled(image, layer, x, y, width, height, angle);
	}

	@Override
	public void batchImageScreenScaled(Object image, int layer, float x, float y, float width, float height,
			float angle, float px, float py) {
				actual_renderer.batchImageScreenScaled(image, layer, x, y, width, height, angle, px, py);
		
	}

	@Override
	public long getHintSleep() {
		return actual_renderer.getHintSleep();
	}

	@Override
	public List<Object> loadImageSeries(String resource, int width, int height, int frame_count) {
		List<Object> handles = actual_renderer.loadImageSeries(resource, width, height, frame_count);
		if (handles.size() != frame_count) {
			throw new RuntimeException();
		}
		for (Object obj : handles) {
			ImageInfo info = new ImageInfo();
			info.object = obj;
			info.loaded = false;
			images.add(info);
		}
		return handles;
	}

	@Override
	public List<Object> loadImageSeriesTopLeft(String resource, int width, int height, int frame_count) {
		List<Object> handles = actual_renderer.loadImageSeriesTopLeft(resource, width, height, frame_count);
		if (handles.size() != frame_count) {
			throw new RuntimeException();
		}
		for (Object obj : handles) {
			ImageInfo info = new ImageInfo();
			info.object = obj;
			info.loaded = false;
			images.add(info);
		}
		return handles;
	}

	@Override
	public void fillEventList(List<InteractEvent> events) {
		if (!batch_mode) {
			actual_renderer.fillEventList(events);
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public void setIcon(String resource) {
		actual_renderer.setIcon(resource);
	}

	@Override
	public Object duplicateHandle(Object handle) {
		/* TODO: validate handles not being used after freed */
		return actual_renderer.duplicateHandle(handle);
	}

	@Override
	public void deduplicateHandle(Object handle) {
		actual_renderer.deduplicateHandle(handle);
	}

	@Override
	public int getHeight() {
		return actual_renderer.getHeight();
	}

	@Override
	public int getWidth() {
		return actual_renderer.getWidth();
	}

}
