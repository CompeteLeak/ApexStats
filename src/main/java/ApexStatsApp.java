import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

public class ApexStatsApp extends Application {

  private static VBox vbox;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    vbox = new VBox();
    vbox.setStyle("-fx-background-color: #121212;");

    Scene scene = new Scene(vbox, 400, 300);
    primaryStage.setTitle("Leaderboards");
    primaryStage.setScene(scene);
    primaryStage.show();

    ApexApiCall playerData = new ApexApiCall();
    playerData.playerOneData();
    playerData.playerTwoData();
    playerData.playerThreeData();
    playerData.playerFourData();
  }

  public static void updateUI(String username, int kills, int damage, String rankScore) {
    Platform.runLater(() -> {
      String newLine = System.getProperty("line.separator");

      // Create a new label for the player's information
      Label playerLabel = new Label(username + newLine + "KILLS: " + kills + newLine + "DAMAGE: " + damage +
        newLine + "RANK: " + rankScore);
      playerLabel.setStyle("-fx-font-size: 30pt; -fx-font-weight: bold; -fx-font-family: Impact;");
      playerLabel.setTextFill(Color.WHITE);

      // Add the player's label to the VBox
      vbox.getChildren().add(playerLabel);
      // Set spacing between player labels
      vbox.setSpacing(50);
    });
  }
}
