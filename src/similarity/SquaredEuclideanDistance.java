package similarity;

public class SquaredEuclideanDistance implements DistanceInterface {
    /**
     * getDistance
     *
     * Calculates the distance between two array using Squared Euclidean distance [GEEN WORTEL SUM].
     * In this application used to sum of squared errors (SSE).
     *
     * Sommatie Loop:
     *      (q[i] - p[i]) ^ 2
     *
     * @param lhs array of points
     * @param rhs array of points
     * @return sum double
     */
    @Override
    public double getDistance(double[] lhs, double[] rhs) {
        double sum = 0;
        for(int i = 0; i < lhs.length && i < rhs.length; i++) {
            double d = lhs[i] - rhs[i];
            sum += d * d;
        }
        return sum;
    }
}
