package com.breakwater.model;

public class EnvironmentData {

    private double waveHeight;
    private double waterDepth;

    public EnvironmentData(double waveHeight, double waterDepth) {
        this.waveHeight = waveHeight;
        this.waterDepth = waterDepth;
    }

    public double getWaveHeight() {
        return waveHeight;
    }

    public double getWaterDepth() {
        return waterDepth;
    }

    public void setWaveHeight(double waveHeight) {
        this.waveHeight = waveHeight;
    }

    public void setWaterDepth(double waterDepth) {
        this.waterDepth = waterDepth;
    }
}
