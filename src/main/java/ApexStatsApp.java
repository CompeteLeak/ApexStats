import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ApexStatsApp extends Application {

  ApexApiCall playerOne = new ApexApiCall();

  @Override
  public void start(Stage primaryStage) {

    // Make the API request
    playerOne.playerOneData();

    //update UI
    StackPane root = new StackPane();
    root.setStyle("-fx-background-color: #121212;");
    root.getChildren().addAll(playerOne.killsLabel);

    Scene scene = new Scene(root, 400, 300);
    primaryStage.setTitle("My Apex Kills");
    primaryStage.setScene(scene);

    // Show the JavaFX window
    primaryStage.show();
  }

  public static void main(String[] args) {

    launch(args);
  }
}
