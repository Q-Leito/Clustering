package clustering;

import java.util.List;

public class ClusterCreator {
    private List<Point> mPoints;

    public ClusterCreator(List<Point> points) {
        mPoints = points;
    }

    public Cluster[] initClusters(int numberOfClusters, List<Point> points) {
        Cluster[] clusters = new Cluster[numberOfClusters];

        for(int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster(getRandomPoint(points));
        }

        return clusters;
    }

    /**
     * Get random point from points list
     *
     * @param points
     * @return
     */
    private Point getRandomPoint(List<Point> points) {
        return points.get((int) (Math.random() * points.size()));
    }

    /**
     * get SSE from all clusters
     * @param clusters
     * @return
     */
    public double getSSE(Cluster[] clusters) {
        double sum = 0;
        for(Cluster c : clusters) {
            sum += c.getSquaredErrors();
        }
        return sum;
    }
}
