package clustering;

import similarity.DistanceInterface;

public class Point {
    private double[] mProperties; // Contains properties(position) of this point(observation)

    public Point(double[] properties) {
        mProperties = properties;
    }

    public double[] getProperties() {
        return mProperties;
    }

    /**
     * Compare this point(observation) with another point(observation).
     * In this application the calculator can be Euclidean or SquaredEuclidean.
     * A distance of type double is returned.
     *
     * @param calculator
     * @param other
     * @return double
     */
    public double compare(DistanceInterface calculator, Point other) {
        return calculator.getDistance(mProperties, other.getProperties());
    }
}
