import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
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
    YamlVars vars = new YamlVars();
    vars.connectYaml();
    vbox = new VBox();
    vbox.setStyle("-fx-background-color: #121212;");

    Scene scene = new Scene(vbox, 400, 300);
    primaryStage.setTitle("Leaderboards");
    primaryStage.setScene(scene);
    primaryStage.show();

    ApexApiCall playerData = new ApexApiCall();
    playerData.getPlayerData(vars.playerOne, vars.platform1);
    playerData.getPlayerData(vars.playerTwo, vars.platform1);
    playerData.getPlayerData(vars.playerThree, vars.platform1);
    playerData.getPlayerData(vars.playerFour, vars.platform1);
    playerData.getPlayerData(vars.playerFive, vars.platform2);

    // For aesthetics, may consider adding a separator line between the players
    // VBox.setMargin(vbox.getChildren().get(0), new Insets(0, 0, 10, 0));

    // order the players based on their stats before displaying them
    //AVG damage for evertyone 

    // Ordering the players based on kills (assuming kills is an integer)
    // vbox.getChildren().sort(Comparator.comparingInt(player -> -Integer.parseInt(((Label) player).getText().split("\n")[1].split(": ")[1])));

    // Ordering the players based on damage (assuming damage is an integer)
    // vbox.getChildren().sort(Comparator.comparingInt(player -> -Integer.parseInt(((Label) player).getText().split("\n")[2].split(": ")[1])));
  }

  public static void updateUI(String username, String kills, String damage, String rankScore) {
    Platform.runLater(() -> {
      String newLine = System.getProperty("line.separator");

      // Create a new label for the player's information
      Label playerLabel = new Label(username + newLine + "KILLS: " + kills + newLine + "DAMAGE: " + damage +
        newLine + "RANK: " + rankScore);
      playerLabel.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold; -fx-font-family: Impact;");
      playerLabel.setTextFill(Color.WHITE);

      playerLabel.setPadding(new Insets(0, 0, 0, 0));
      // Add the player's label to the VBox
      vbox.getChildren().add(playerLabel);
      // Set spacing between player labels
      vbox.setSpacing(20);
    });
  }
}
