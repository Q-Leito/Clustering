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
     * Average position of observations in cluster
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

    public double getSquaredErrors() {
        double sum = 0;
        for (Point point : mPoints) {
            sum += compare(SQUARED_DISTANCE, point);
        }
        return sum;
    }

    public double compare(DistanceInterface calculator, Point other) {
        return mPosition.compare(calculator, other);
    }
}
