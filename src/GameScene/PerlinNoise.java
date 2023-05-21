package GameScene;

import java.util.Random;

public class PerlinNoise {
    private final long seed;
    private final Random rand;

    public PerlinNoise(long seed) {
        this.seed = seed;
        this.rand = new Random(seed);
    }

    public double perlinNoise(double x, double y) {
        int x0 = (int) Math.floor(x);
        int x1 = x0 + 1;
        int y0 = (int) Math.floor(y);
        int y1 = y0 + 1;

        double sx = x - x0;
        double sy = y - y0;

        double n0, n1, ix0, ix1, value;

        n0 = dotGridGradient(x0, y0, x, y);
        n1 = dotGridGradient(x1, y0, x, y);
        ix0 = interpolate(n0, n1, sx);

        n0 = dotGridGradient(x0, y1, x, y);
        n1 = dotGridGradient(x1, y1, x, y);
        ix1 = interpolate(n0, n1, sx);

        value = interpolate(ix0, ix1, sy);
        return value;
    }

    private double dotGridGradient(int ix, int iy, double x, double y) {
        double dx = x - ix;
        double dy = y - iy;
        double[] gradient = randomGradient(ix, iy);
        double dotProduct = (dx * gradient[0]) + (dy * gradient[1]);
        return dotProduct;
    }

    private double[] randomGradient(int ix, int iy) {
        rand.setSeed(ix * 49632 + iy * 325176 + seed);
        double theta = rand.nextDouble() * 2 * Math.PI;
        return new double[] {Math.cos(theta), Math.sin(theta)};
    }

    private double interpolate(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;
        return a * (1 - f) + b * f;
    }
}
