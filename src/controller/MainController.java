package controller;

import clustering.Cluster;
import clustering.ClusterCreator;
import clustering.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import similarity.EuclidianDistance;
import utils.PointParser;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Label mNumberOfClustersLabel;
    public Label mNumberOfIterationsLabel;
    public Label mSSELabel;
    public Label mClusterOneLabel;
    public Label mClusterTwoLabel;
    public Label mClusterThreeLabel;
    public Label mClusterFourLabel;
    public Label mClusterFiveLabel;
    public ChoiceBox<Integer> mNumberOfClustersBox;
    public ChoiceBox<Integer> mNumberOfIterationsBox;
    public Button mSubmitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> numberOfClusters = FXCollections.observableArrayList();
        for (int i = 1; i < 6; i++) {
            numberOfClusters.add(i);
            mNumberOfClustersBox.setItems(numberOfClusters);
        }

        ObservableList<Integer> numberOfIterations = FXCollections.observableArrayList();
        for (int i = 10; i < 101; i += 10) {
            numberOfIterations.add(i);
            mNumberOfIterationsBox.setItems(numberOfIterations);
        }
    }

    public void submitButton() {
        Integer selectedNumberOfClusters = mNumberOfClustersBox.getSelectionModel().getSelectedItem();
        Integer selectedNumberOfIterations = mNumberOfIterationsBox.getSelectionModel().getSelectedItem();
        mClusterOneLabel.setText("");
        mClusterTwoLabel.setText("");
        mClusterThreeLabel.setText("");
        mClusterFourLabel.setText("");
        mClusterFiveLabel.setText("");

        // Define file to read, read file and store data
        List<Point> points = null;
        try {
            points = new PointParser("WineData.csv").parsePoints();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Randomly initialise K cluster centroids
        ClusterCreator clusterCreator = new ClusterCreator(points);
        Cluster clusters[];

        double previousSSE;
        double sse = 0;
        double mainSSE = Double.POSITIVE_INFINITY;
        Cluster mainClusters[] = new Cluster[selectedNumberOfClusters];

        // LOOP NUMBER OF ITERATION
        for (int i = 0; i < selectedNumberOfIterations; i++) {
            clusters = clusterCreator.initClusters(selectedNumberOfClusters, points);
            for (int n = 0; n < selectedNumberOfIterations; n++) {
                previousSSE = sse;

                for (Cluster cluster : clusters) {
                    cluster.mPoints.clear();
                }

                // Assign to closest centroid
                // Make method for
                if (points != null) {
                    for (Point point : points) {
                        double BIG_NUMBER = Double.POSITIVE_INFINITY;
                        Cluster c = clusters[0];
                        int j = 0;

                        do {
                            double delta = clusters[j].compare(new EuclidianDistance(), point);
                            if (delta < BIG_NUMBER) {
                                BIG_NUMBER = delta;
                                c = clusters[j];
                            }
                            j++;
                        } while (j < clusters.length);
                        c.getPoints().add(point);
                    }
                }

                // Update centroids (means/ average)
                for (Cluster cluster : clusters) {
                    cluster.setPosition(new Point(cluster.getMean()));
                }

                sse = clusterCreator.getSSE(clusters);

                if (sse == previousSSE) {
                    if (mainSSE > sse) {
                        mainSSE = sse;
                        mainClusters = clusters;
                    }
                    break;
                }
            }
        }

        mSSELabel.setText("SSE: " + mainSSE);
        System.out.println("MAIN SSE: " + mainSSE);

        int clusterNumber = 0;
        for (Cluster cluster : mainClusters) {
            int[] count = new int[cluster.getPosition().getProperties().length];
            for (Point p : cluster.getPoints()) {
                for (int i = 0; i < count.length; i++) {
                    if (p.getProperties()[i] > 0) {
                        count[i]++;
                    }
                }
            }

            switch (clusterNumber) {
                case 0:
                    mClusterOneLabel.setText("Customers: " + cluster.getPoints().size());
                    break;
                case 1:
                    mClusterTwoLabel.setText("Customers: " + cluster.getPoints().size());
                    break;
                case 2:
                    mClusterThreeLabel.setText("Customers: " + cluster.getPoints().size());
                    break;
                case 3:
                    mClusterFourLabel.setText("Customers: " + cluster.getPoints().size());
                    break;
                case 4:
                    mClusterFiveLabel.setText("Customers: " + cluster.getPoints().size());
                    break;
            }

            System.out.println("Customers: " + cluster.getPoints().size());
            for (int i = 0; i < count.length; i++) {
                if (count[i] > 3) {
                    System.out.println("Offer: " + (i + 1) + " was bought " + count[i] + " times");
                }
            }
            System.out.println();
            clusterNumber++;
        }
    }
}
