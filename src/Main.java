import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Constants;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        showScene(primaryStage);
    }

    private void showScene(Stage primaryStage) throws IOException {
        URL mainUrl = getClass().getResource(Constants.MAIN_FXML_PATH);
        Parent root = null;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(mainUrl);

        try {
            loader.load();
            root = loader.getRoot();
        } catch (IOException e) {
            System.out.printf("\n--- %s ---\n", e.getMessage());
            e.printStackTrace();
        }

        if (root != null) {
            Scene rootScene = new Scene(root, Constants.APP_WIDTH, Constants.APP_HEIGHT);

            primaryStage.setTitle(Constants.APP_NAME);
            primaryStage.setScene(rootScene);
            primaryStage.setResizable(true);

            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
