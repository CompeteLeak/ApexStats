import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ApexStatsApp extends Application {

    private static Label killsLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        killsLabel = new Label("Fetching Stats...");
        killsLabel.setStyle("-fx-font-size: 48pt; -fx-font-weight: bold; -fx-font-family: Impact;");
        killsLabel.setTextFill(Color.WHITE);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #121212;");
        root.getChildren().addAll(killsLabel);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Leaderboards");
        primaryStage.setScene(scene);
        primaryStage.show();

        ApexApiCall playerOne = new ApexApiCall();
        playerOne.playerOneData();
    }

    public static void updateUI(String username, int kills, int damage, String rankScore) {
        Platform.runLater(() -> {
            String newLine = System.getProperty("line.separator");
            killsLabel.setText(username + newLine + "KILLS: " + kills + newLine + "DAMAGE: " + damage
                    + newLine + "RANK: " + rankScore);
        });
    }
}
