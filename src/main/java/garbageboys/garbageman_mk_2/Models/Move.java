package garbageboys.garbageman_mk_2.Models;

import java.util.List;

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

    public enum FunctionName {
        LOOP, //only add to index 0 of sequence in order to indicate that sequence should loop once completed
        MOVETO,
        MOVETOANDROTATE,
        ROTATE,
        TELEPORTTO,
        WAIT
    }

    public void setAngle(float rads);

    /**
     * 
     * starts from bottom left, goes to top right
     * @param x endPos 0,1 
     * @param y endPos 0,1 
     */
    public void moveTo(float x, float y, float speed);

    /**
     * starts from bottom left, goes to top right
     * @param x endPos 0,1 
     * @param y endPos 0,1 
     * @param rads 0-2Pi radians
     */
    public void moveToAndRotate(float x, float y,float rads, float speed);

    /**
     * rotates around a given point on a circle
     * @param degrees how far around the circle to go (0-360)
     * @param radius radius of circle (0-1), percentage of screen size
     * @param x xPos of center (0-1) starting bottom left
     * @param y yPos of center (0-1) starting bottom left
     */
    public void rotate(float degrees, float radius, float x, float y, float speed);

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
     * @param speed: float, 0 doesn't move and 100 moves about the entire screen horizontally in 1 frame
     */
    public void moveAlongAxis(Direction direction, EdgeBehavior edgeBehavior, float speed);


    /**
     * rotates around a given point on a circle- only call in sequence otherwise will run forever
     * @param radius radius of circle (0-1), percentage of screen size
     * @param x xPos of center (0-1) starting bottom left
     * @param y yPos of center (0-1) starting bottom left
     * @param speed make negative to move opposite direction, controls how quickly object goes in circle
     */
    public float moveInCircle(float radius, float x, float y, float speed);

    /**
     * 
     * @param direction 0 - 2pi,pi/2 is up, 3pi/2 is down etc
     * @param atEdgeLoop 0: keep going 1: if at edge, go to other side of screen 2: 
     * @param speed: float, 0 doesn't move and 100 moves about the entire screen horizontally in 1 frame
     */
    public void move(double direction, EdgeBehavior edgeBehavior, float speed);


    /**
     * only use in a sequence, otherwise will freeze object indefinitely
     */
    public void wait(double seconds);

    /**
     * renders image after moving calculations are done
     */
    public void show();


    /**
     * sets the sequence to be run when runSequence is called
     */
    public void setSequence(List<SequenceParam> sequence);

    /**
     * runs sequence of functions
     * 
     * 
     */
    public void runSequence();
}
