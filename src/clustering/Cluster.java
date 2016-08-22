package clustering;

import similarity.DistanceInterface;
import similarity.SquaredEuclideanDistance;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
    private static final DistanceInterface SQUARED_DISTANCE = new SquaredEuclideanDistance();
    public Set<Point> mPoints;
    private Point mPosition;

    public Point getPosition() {
        return mPosition;
    }

    public Cluster(Point position) {
        mPoints = new HashSet<>();
        mPosition = position;
    }

    public void setPosition(Point position) {
        mPosition = position;
    }

    public Set<Point> getPoints() {
        return mPoints;
    }

    /**
     * Calculates the mean(average) of a cluster.
     * Create a result array by iterating through all the points in the list, sum all the values.
     * Iterate through the created result array and change value to the average.
     *
     * Average: total sum / amount of points
     *
     * @return result
     */
    public double[] getMean() {
        double[] result = new double[mPosition.getProperties().length];
        if (mPoints.isEmpty()) {
            return result;
        }
        for (Point point : mPoints) {
            for (int i = 0; i < point.getProperties().length; i++) {
                result[i] += point.getProperties()[i];
            }
        }
        for (int i = 0; i < result.length; i++) {
            result[i] /= mPoints.size();
        }
        return result;
    }

    /**
     * Calculates the Sum of Squared Errors.
     * Measures how closely related are objects in a cluster.
     *
     * @return sum
     */
    public double getSquaredErrors() {
        double sum = 0;
        for (Point point : mPoints) {
            sum += compare(SQUARED_DISTANCE, point);
        }
        return sum;
    }

    /**
     * Compare centroid(mPosition) of a cluster with a point(other).
     * This compare method uses the DistanceInterface as a calculator.
     * In this application the calculator can be Euclidean or SquaredEuclidean.
     *
     * @param calculator
     * @param other
     * @return double
     */
    public double compare(DistanceInterface calculator, Point other) {
        return mPosition.compare(calculator, other);
    }
}
