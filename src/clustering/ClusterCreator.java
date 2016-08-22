package clustering;

import java.util.List;

public class ClusterCreator {

    /**
     * Initialise clusters.
     * Create amount(numberOfClusters) of clusters by randomly choose observation points from List<Point> points.
     *
     * @param numberOfClusters
     * @param points list of points
     * @return clusters
     */
    public Cluster[] initClusters(int numberOfClusters, List<Point> points) {
        Cluster[] clusters = new Cluster[numberOfClusters];

        for(int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster(getRandomPoint(points));
        }

        return clusters;
    }

    /**
     * Get random observation(point) from the points list
     *
     * @param points
     * @return Point
     */
    private Point getRandomPoint(List<Point> points) {
        return points.get((int) (Math.random() * points.size()));
    }

    /**
     * Calculates the total Sum of Squared Errors
     *
     * @param clusters
     * @return double sum
     */
    public double getSSE(Cluster[] clusters) {
        double sum = 0;
        for(Cluster c : clusters) {
            sum += c.getSquaredErrors();
        }
        return sum;
    }
}
