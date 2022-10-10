package garbageboys.garbageman_mk_2.Models;

import garbageboys.garbageman_mk_2.Models.SequenceName;

public class SequenceParam {
    private SequenceName sequenceName;
    private float x;
    private float y;
    private float angle;
    private float speed;
    private float radius;

    public SequenceParam() {

    }

    public SequenceParam(SequenceName sequenceName, float x, float y) {
        this.sequenceName = sequenceName;
        this.x = x;
        this.y = y;
    }

    public SequenceParam(SequenceName sequenceName, float x, float y, float speed) {
        this.sequenceName = sequenceName;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public SequenceParam(SequenceName sequenceName, float x, float y, float speed, float angle) {
        this.sequenceName = sequenceName;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    public SequenceParam(SequenceName sequenceName, float x, float y, float speed, float angle, float radius) {
        this.sequenceName = sequenceName;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
    }

    public SequenceName getSequenceName() {
        return this.sequenceName;
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
}