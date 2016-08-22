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
    public Label mClusterOneOffersLabel;
    public Label mClusterTwoOffersLabel;
    public Label mClusterThreeOffersLabel;
    public Label mClusterFourOffersLabel;
    public Label mClusterFiveOffersLabel;
    public ChoiceBox<Integer> mNumberOfClustersBox;
    public ChoiceBox<Integer> mNumberOfIterationsBox;
    public Button mSubmitButton;

    private List<Point> mPoints = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Define file to read, read file and store data
        try {
            mPoints = new PointParser("WineData.csv").parsePoints();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        ObservableList<Integer> numberOfClusters = FXCollections.observableArrayList();
        for (int i = 1; i < 11; i++) {
            numberOfClusters.add(i);
            mNumberOfClustersBox.setItems(numberOfClusters);
        }

        ObservableList<Integer> numberOfIterations = FXCollections.observableArrayList();
        for (int i = 100; i <= 1000; i += 100) {
            numberOfIterations.add(i);
            mNumberOfIterationsBox.setItems(numberOfIterations);
        }
    }

    public void submitButton() {
        Integer selectedNumberOfClusters = mNumberOfClustersBox.getSelectionModel().getSelectedItem();
        Integer selectedNumberOfIterations = mNumberOfIterationsBox.getSelectionModel().getSelectedItem();
        clearText();

        // Randomly initialise K cluster centroids
        ClusterCreator clusterCreator = new ClusterCreator();
        Cluster clusters[];

        double previousSSE;
        double sse = 0;
        double mainSSE = Double.POSITIVE_INFINITY;

        if (selectedNumberOfClusters != null && selectedNumberOfIterations != null) {
            Cluster mainClusters[] = new Cluster[selectedNumberOfClusters];

            // LOOP NUMBER OF ITERATION
            for (int i = 0; i < selectedNumberOfIterations; i++) {
                // Initialise clusters using random observation points
                clusters = clusterCreator.initClusters(selectedNumberOfClusters, mPoints);
                for (int n = 0; n < selectedNumberOfIterations; n++) {
                    previousSSE = sse;

                    // Empty clusters at beginning of each iteration
                    for (Cluster cluster : clusters) {
                        cluster.mPoints.clear();
                    }

                    // Assign to closest centroid
                    if (mPoints != null) {
                        for (Point point : mPoints) {
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

                    // Save cluster with lowest SSE
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

            // Iteration through best clusters (solutions)
            // Save count of times that an offer was bought
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
                        printAmountOfCustomersAndOffersBoughtInCluster(mClusterOneLabel, mClusterOneOffersLabel, cluster, count);
                        break;
                    case 1:
                        printAmountOfCustomersAndOffersBoughtInCluster(mClusterTwoLabel, mClusterTwoOffersLabel, cluster, count);
                        break;
                    case 2:
                        printAmountOfCustomersAndOffersBoughtInCluster(mClusterThreeLabel, mClusterThreeOffersLabel, cluster, count);
                        break;
                    case 3:
                        printAmountOfCustomersAndOffersBoughtInCluster(mClusterFourLabel, mClusterFourOffersLabel, cluster, count);
                        break;
                    case 4:
                        printAmountOfCustomersAndOffersBoughtInCluster(mClusterFiveLabel, mClusterFiveOffersLabel, cluster, count);
                        break;
                }
                System.out.println();
                clusterNumber++;
            }
        }
    }

    private void clearText() {
        mClusterOneLabel.setText("");
        mClusterTwoLabel.setText("");
        mClusterThreeLabel.setText("");
        mClusterFourLabel.setText("");
        mClusterFiveLabel.setText("");

        mClusterOneOffersLabel.setText("");
        mClusterTwoOffersLabel.setText("");
        mClusterThreeOffersLabel.setText("");
        mClusterFourOffersLabel.setText("");
        mClusterFiveOffersLabel.setText("");
    }

    /**
     * Print the amount of customers and offers bought (more than x times) in a cluster
     *
     * @param clusterLabel
     * @param clusterOffersLabel
     * @param cluster cluster object
     * @param count array containing count of offers bought
     */
    private void printAmountOfCustomersAndOffersBoughtInCluster(Label clusterLabel, Label clusterOffersLabel,
                                                                Cluster cluster, int[] count) {
        clusterLabel.setText("Customers: " + cluster.getPoints().size());
        String offers = "";
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 3) { // Bought more than 3 times
                offers += "Offer: " + (i + 1) + " was bought " + count[i] + " times \n";
                clusterOffersLabel.setText(offers);
            }
        }
    }
}
