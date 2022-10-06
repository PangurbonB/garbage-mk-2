package garbageboys.garbageman_mk_2.Models;

public class Car extends Movable{
    public Car() {
    }

    public Car(Object img) {
        super(img, 0, 0, .005f, .016f, 0);
    }

    public Car(Object img, float x, float y) {
        super(img, x, y, .005f, .016f, 0);
    }
   

}
