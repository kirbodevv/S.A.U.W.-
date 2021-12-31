package com.kgc.sauw.core.world.generator;

import com.kgc.utils.OpenSimplexNoise;

public class WorldGeneratorUtils {
    public static double sumOctave(OpenSimplexNoise openSimplexNoise, int numIterations, double x, double y, double persistence, double scale, double low, double high) {
        double maxAmp = 0;
        double amp = 1;
        double freq = scale;
        double noise = 0;

        for (int i = 0; i < numIterations; ++i) {
            noise += openSimplexNoise.eval(x * freq, y * freq, 1) * amp;
            maxAmp += amp;
            amp *= persistence;
            freq *= 2;
        }
        noise /= maxAmp;

        noise = noise * (high - low) / 2 + (high + low) / 2;

        return noise;
    }
}
