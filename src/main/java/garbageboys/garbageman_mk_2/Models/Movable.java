package garbageboys.garbageman_mk_2.Models;

import java.util.List;

import garbageboys.garbageman_mk_2.Rendering.Render2D;

public class Movable implements Move {

    private float x;
    private float y;
    private float width;
    private float height;
    private float angle;
    private Object img;
    private Render2D renderer;
    private int layer = 2;


    private boolean bounced = false;

    private boolean bounceX = false;
    private boolean bounceY = false;

    private List<SequenceParam> sequence;
    private int sequenceIndex;

    private int waitFrames = 0;
    private int circleFrames  = 0;

    private final double circleFramesToRadiansConstant = .0002;//multiplier to convert the amount of frames that have passed into the amount of degrees around circke that object has traveled
    

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
    public void moveTo(float x, float y, float speed) {

        if ((this.x < x + .001 && this.x > x - .001) && (this.y < y + .001 && this.y > y - .001)) {
            sequenceIndex++;
            return;
        }
            
        float xDiff = x - this.x;
        float yDiff = y - this.y;
        double direction = Math.atan(yDiff / xDiff);
        if(x < this.x && y < this.y) {
            direction += Math.PI;
        }
        move(direction, EdgeBehavior.NONE, speed);

    }

    /**
     * starts from bottom left, goes to top right
     * 
     * @param x       endPos 0,1
     * @param y       endPos 0,1
     * @param rads 0-2Pi radians
     */
    public void moveToAndRotate(float x, float y, float rads, float speed) {

    }

    /**
     * rotates around a given point on a circle
     * 
     * @param degrees how far around the circle to go (0-360)
     * @param radius  radius of circle (0-1), percentage of screen size
     * @param x       xPos of center (0-1) starting bottom left
     * @param y       yPos of center (0-1) starting bottom left
     */
    public void rotate(float degrees, float radius, float x, float y, float speed, float startingAngle) {

        if (Math.abs(moveInCircle(radius, x, y, speed, startingAngle)) >= degrees) {
            sequenceIndex++;
            circleFrames = 0;
            return;
        }
    }

    //oh so incredibly unsure about this will certainly need to do some testing
    public float moveInCircle(float radius, float x, float y, float speed, float startingAngle) {
        circleFrames++;
        double angle = speed * circleFrames * circleFramesToRadiansConstant;
        this.angle = (float)angle;
        this.x =  (float) (radius * Math.cos(angle + startingAngle) + x);
        this.y = (float) ( 1.85f * radius * Math.sin(angle + startingAngle) + y) ;
        return (float) angle;

    }

    @Override
    public void teleportTo(float x, float y) {
        this.x = x;
        this.y = y;
        this.sequenceIndex++;
    }

    @Override
    public void moveAlongAxis(Direction direction, EdgeBehavior edgeBehavior, float speed) {
        if ((x >= 1 || y >= 1 || x <= 0 || y <= 0) && edgeBehavior == EdgeBehavior.BOUNCE) {
            bounced = !bounced;
            if (x >= 1) {
                x = .999f;
            } else if (x <= 0) {
                x = .001f;
            } else if (y >= 1) {
                y = .999f;
            } else if (y <= 0) {
                y = .001f;
            }
        }
        float speedConstant = (float) 1 / (19000 * 5);
        if (direction == Direction.DOWN) {
            if (y <= 0 && edgeBehavior == EdgeBehavior.LOOP)
                y = 1;
            y = !bounced ? y - speed * speedConstant * 1.3f : y + speed * speedConstant * 1.3f;
        } else if (direction == Direction.UP) {
            if (y >= 1 && edgeBehavior == EdgeBehavior.LOOP)
                y = 0;
            y = !bounced ? y + speed * speedConstant * 1.3f : y - speed * speedConstant * 1.3f;
        } else if (direction == Direction.LEFT) {
            if (x <= 0 && edgeBehavior == EdgeBehavior.LOOP)
                x = 1;
            x = !bounced ? x - speed * speedConstant : x + speed * speedConstant;
        } else if (direction == Direction.RIGHT) {
            if (x >= 1 && edgeBehavior == EdgeBehavior.LOOP)
                x = 0;
            x = !bounced ? x + speed * speedConstant : x - speed * speedConstant;
        }
    }

    @Override
    public void move(double direction, EdgeBehavior edgeBehavior, float speed) {
        float speedConstant = (float) 1 / 95000;
        x = (float) (!bounceX
                ? x + Math.cos(direction) * speed * speedConstant
                : x - Math.cos(direction) * speed * speedConstant);
        y = (float) (!bounceY
                ? y + Math.sin(direction) * speed * speedConstant
                : y - Math.sin(direction) * speed * speedConstant);

        if ((x >= 1 || y >= 1 || x <= 0 || y <= 0) && edgeBehavior == EdgeBehavior.BOUNCE) {
            if (x >= 1) {
                x = .999f;
                bounceX = !bounceX;
            } else if (x <= 0) {
                x = .001f;
                bounceX = !bounceX;
            } else if (y >= 1) {
                y = .999f;
                bounceY = !bounceY;
            } else if (y <= 0) {
                bounceY = !bounceY;
            }
        } else if ((x >= 1 || y >= 1 || x <= 0 || y <= 0) && edgeBehavior == EdgeBehavior.LOOP) {
            if (x >= 1) {
                x = .001f;
            } else if (x <= 0) {
                x = .999f;

            } else if (y >= 1) {
                y = .001f;

            } else if (y <= 0) {
                y = .999f;
            }
        }

    }

    @Override
    public void wait(double seconds) {
        waitFrames++;
        if((float)waitFrames / 48 >= seconds) {
            sequenceIndex++;
            waitFrames = 0;
        }
        
    }

    @Override
    public void show() {
        renderer.batchImageScreenScaled(img, layer, x, y, width, height, angle);

    }

    @Override
    public void setSequence(List<SequenceParam> sequence) {
        this.sequence = sequence;
        this.sequenceIndex = 0;
    }

    @Override
    public void runSequence() {
        if(sequence.size() < sequenceIndex) return;
        SequenceParam action = sequence.get(sequenceIndex);
        switch(action.getFunctionName()) {
            case LOOP:
                sequenceIndex++;
                break;
            case MOVETO:
                moveTo(action.getX(), action.getY(), action.getSpeed());
                break;
            case TELEPORTTO:
                teleportTo(action.getX(), action.getY());
                break;
            case MOVETOANDROTATE:
                moveToAndRotate(action.getX(), action.getY(), action.getAngle(), action.getSpeed());
                break;
            case ROTATE:
                rotate(action.getAngle(), action.getRadius(), action.getX(), action.getY(), action.getSpeed(), action.getStartingAngle());
                break;
            case WAIT:
                wait(action.getSeconds());
                break;
            default:
                if(sequence.get(0).getFunctionName() == FunctionName.LOOP)
                    sequenceIndex = 0;
                break;
        }
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

    public float getAngle() {
        return this.angle;
    }

    @Override
    public void setAngle(float angle) {
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


}
