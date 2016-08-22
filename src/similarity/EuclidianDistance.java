package similarity;

public class EuclidianDistance implements DistanceInterface {
    /**
     * getDistance
     *
     * Calculates the distance between two array using Euclidean distance.
     * In this application used to calculate distance between two points.
     *
     * Summation Loop:
     *      (q[i] - p[i]) ^ 2
     * End Loop
     * √(total)
     *
     * @param lhs array of points
     * @param rhs array of points
     * @return sum
     */
    @Override
    public double getDistance(double[] lhs, double[] rhs) {
        double sum = 0.0;
        for(int i = 0; i < lhs.length && i < rhs.length; i++) {
            double d = lhs[i] - rhs[i];
            sum += d * d;
        }
        return Math.sqrt(sum);
    }
}
