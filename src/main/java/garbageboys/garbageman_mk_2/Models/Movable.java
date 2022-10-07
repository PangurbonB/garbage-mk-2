package garbageboys.garbageman_mk_2.Models;

import garbageboys.garbageman_mk_2.Rendering.Render2D;

public class Movable implements Move {

    private float x;
    private float y;
    private float width;
    private float height;
    private int angle;
    private Object img;
    private Render2D renderer;
    private int layer = 1;
    private boolean isMoving = false;
    private boolean bounced = false;

    public Movable() {
    }

    public Movable(Render2D renderer, Object img) {
        this.renderer = renderer;
        this.img = img;
    }

    public Movable(Render2D renderer, Object img, float x, float y, float width, float height, int angle) {
        this.renderer = renderer;
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    /**
     * starts from bottom left, goes to top right
     * 
     * @param x endPos 0,1
     * @param y endPos 0,1
     */
    public void moveTo(float x, float y, float velocity) {

    }

    /**
     * starts from bottom left, goes to top right
     * 
     * @param x       endPos 0,1
     * @param y       endPos 0,1
     * @param degrees 0-360 rotation
     */
    public void moveToAndRotate(float x, float y, int degrees, float velocity) {

    }

    /**
     * rotates around a given point on a circle
     * 
     * @param degrees how far around the circle to go (0-360)
     * @param radius  radius of circle (0-1), percentage of screen size
     * @param x       xPos of center (0-1) starting bottom left
     * @param y       yPos of center (0-1) starting bottom left
     */
    public void rotate(int degrees, float radius, float x, float y, float velocity) {

    }

    @Override
    public void teleportTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
	public void move(Direction direction, EdgeBehavior edgeBehavior, float speed) {
		
	}

    @Override
    public void show() {
        renderer.batchImageScreenScaled(img, layer, x, y, width, height);

    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Object getImg() {
        return this.img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Render2D getRenderer() {
        return this.renderer;
    }

    public void setRenderer(Render2D renderer) {
        this.renderer = renderer;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean isMoving() {
        return this.isMoving;
    }



}
