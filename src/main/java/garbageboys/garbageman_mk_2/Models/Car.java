package garbageboys.garbageman_mk_2.Models;

import garbageboys.garbageman_mk_2.Rendering.Render2D;

public class Car extends Movable{
    public Car() {
    }

    public Car(Render2D renderer, Object img) {
        super(renderer, img, 0, 0, .005f, .016f, 0);
    }

    public Car(Render2D renderer, Object img, float x, float y) {
        super(renderer, img, x, y, .005f, .016f, 0);
    }
   

}
