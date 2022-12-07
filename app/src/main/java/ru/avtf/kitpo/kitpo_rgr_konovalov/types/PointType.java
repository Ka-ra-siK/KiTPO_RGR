package ru.avtf.kitpo.kitpo_rgr_konovalov.types;

import java.io.Serializable;

public class PointType implements Serializable {

    private double x;
    private double y;

    public PointType(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(this.x) +
                ";" + String.valueOf(this.y) + ")";
    }
}
