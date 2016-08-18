package clustering;

import similarity.DistanceInterface;

public class Point {
    private double[] mProperties;

    public Point(double[] properties) {
        mProperties = properties;
    }

    public double[] getProperties() {
        return mProperties;
    }

    public double compare(DistanceInterface calculator, Point other) {
        return calculator.getDistance(mProperties, other.getProperties());
    }
}
