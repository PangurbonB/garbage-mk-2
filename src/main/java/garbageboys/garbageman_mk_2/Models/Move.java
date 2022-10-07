package garbageboys.garbageman_mk_2.Models;

public interface Move {
    public enum Direction {
        RIGHT,
        UP,
        LEFT,
        DOWN
    }

    public enum EdgeBehavior {
        NONE,
        LOOP,
        BOUNCE
    }

    /**
     * starts from bottom left, goes to top right
     * @param x endPos 0,1 
     * @param y endPos 0,1 
     */
    public void moveTo(float x, float y, float velocity);

    /**
     * starts from bottom left, goes to top right
     * @param x endPos 0,1 
     * @param y endPos 0,1 
     * @param degrees 0-360 rotation
     */
    public void moveToAndRotate(float x, float y, int degrees, float velocity);

    /**
     * rotates around a given point on a circle
     * @param degrees how far around the circle to go (0-360)
     * @param radius radius of circle (0-1), percentage of screen size
     * @param x xPos of center (0-1) starting bottom left
     * @param y yPos of center (0-1) starting bottom left
     */
    public void rotate(int degrees, float radius, float x, float y, float velocity);

    /**
     * teleports to given x and y
     * @param x (0-1) starting bottom left
     * @param y (0-1) strting bottom left
     */
    public void teleportTo(float x, float y);

    /**
     * 
     * @param direction 0 = right 1 = up 2 = left 3 = down (think circle)
     * @param atEdgeLoop 0: keep going 1: if at edge, go to other side of screen 2: 
     */
    public void move(Direction direction, EdgeBehavior edgeBehavior, float speed);

    /**
     * renders image after moving calculations are done
     */
    public void show();
}
