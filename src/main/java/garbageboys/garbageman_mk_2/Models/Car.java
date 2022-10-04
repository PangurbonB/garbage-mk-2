package garbageboys.garbageman_mk_2.Models;

public class Car extends Movable{
    private float x;
    private float y;
    private float width;
    private float height;
    private int angle;


    public Car() {
    }

    public Car(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = .005f;
        this.height = .016f;
        this.angle = 0;
    }

    public Car(float x, float y, float width, float height, int angle) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

}
