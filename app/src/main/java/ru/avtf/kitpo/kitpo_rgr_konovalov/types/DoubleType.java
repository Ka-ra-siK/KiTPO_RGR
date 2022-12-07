package ru.avtf.kitpo.kitpo_rgr_konovalov.types;

import java.io.Serializable;

public class DoubleType implements Serializable {

    private double doubleTypeValue;

    public DoubleType(double doubleTypeValue) {
        this.doubleTypeValue = doubleTypeValue;
    }

    public double getDoubleTypeValue() {
        return this.doubleTypeValue;
    }

    public void setDoubleTypeValue(double doubleTypeValue) {
        this.doubleTypeValue = doubleTypeValue;
    }

    @Override
    public String toString() {
        return String.valueOf(doubleTypeValue);
    }
}
