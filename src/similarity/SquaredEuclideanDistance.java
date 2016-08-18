package similarity;

public class SquaredEuclideanDistance implements DistanceInterface {
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
