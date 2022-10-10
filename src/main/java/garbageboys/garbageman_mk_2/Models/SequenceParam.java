package garbageboys.garbageman_mk_2.Models;

import garbageboys.garbageman_mk_2.Models.FunctionName;

public class SequenceParam {
    private FunctionName functionName;
    private float x;
    private float y;
    private float angle;
    private float speed;
    private float radius;
    private double seconds;
    

    public SequenceParam() {

    }

    public SequenceParam(FunctionName functionName) {
        this.functionName = functionName;
    }

    public SequenceParam(FunctionName functionName, float x, float y) {
        this.functionName = functionName;
        this.x = x;
        this.y = y;
    }

    public SequenceParam(FunctionName functionName, double seconds) {
        this.functionName = functionName;
        this.seconds = seconds;
    }

    public SequenceParam(FunctionName functionName, float x, float y, float speed) {
        this.functionName = functionName;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public SequenceParam(FunctionName functionName, float x, float y, float speed, float angle) {
        this.functionName = functionName;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    public SequenceParam(FunctionName functionName, float x, float y, float speed, float angle, float radius) {
        this.functionName = functionName;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
    }

    public FunctionName getFunctionName() {
        return this.functionName;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getAngle() {
        return this.angle;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getRadius() {
        return this.radius;
    }

    public double getSeconds() {
        return this.seconds;
    }
}
