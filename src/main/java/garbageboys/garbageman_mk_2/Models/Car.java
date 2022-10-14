package garbageboys.garbageman_mk_2.Models;

import garbageboys.garbageman_mk_2.Rendering.Render2D;

public class Car extends Movable{
    public Car() {
    }

    public Car(Render2D renderer, Object img) {
        super(renderer, img, 0, 0, .005f, .017f, 0);
    }


    public Car(Render2D renderer, Object img, float x, float y) {
        super(renderer, img, x, y, .005f, .017f, 0);
    }
   
    @Override
    public void show() {
        this.getRenderer().batchImageScreenScaled(this.getImg(), this.getLayer(), this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getAngle(),(this.getX() + this.getWidth()/2f), (this.getY() + this.getHeight()/2f));
        //, this.getX() + 0, this.getY() + 0
    }

}
