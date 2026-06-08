package com.breakwater.physics;

public class HudsonCalculator {

    private static final double KD = 8.0;
    private static final double SR = 2.4;

    public static double calculateArmorWeight(
            double waveHeight,
            double slope) {

        double numerator = Math.pow(waveHeight, 3);

        double denominator =
                KD * Math.pow(SR - 1, 3) * slope;

        return numerator / denominator;
    }
}
