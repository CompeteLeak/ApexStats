import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.time.Duration;
import javafx.application.Platform;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class ApexStatsApp extends Application {
  private static final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{username}";
  private static Properties props = new Properties();
  private static String username;
  private static String apiKey;
  private static String platform;

  @Override
  public void start(Stage primaryStage) {

    //load env vars
    try {
      props.load(new FileInputStream("resources/config.yaml"));
      username = props.getProperty("username");
      apiKey = props.getProperty("api_key");
      platform = props.getProperty("platform");
    } catch (IOException e) {
      System.err.println("Error loading config file: " + e.getMessage());
    }

    // Set up the JavaFX UI

    String apiUrl = API_URL.replace("{platform}", platform).replace("{username}", username);

    Label killsLabel = new Label("Fetching kills...");
    killsLabel.setStyle("-fx-font-size: 48pt; -fx-font-weight: bold; -fx-font-family: Impact;");
    killsLabel.setTextFill(Color.WHITE);

    // Make the API request
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", apiKey) // Add the API key as a header
      .timeout(Duration.ofSeconds(10)) // Add timeout of 10 seconds
      .build();
    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(stats -> {

        Platform.runLater(() -> {
          // Update the UI with the response
          JSONObject json = new JSONObject(stats);
          JSONObject data = json.getJSONObject("data");
          JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

          // Parse the stats from the JSON response
          int kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
          // Update the UI with the parsed stats
          killsLabel.setText("Kills: " + kills);

        });
      });
    StackPane root = new StackPane();
    root.setStyle("-fx-background-color: #121212;");
    root.getChildren().addAll(killsLabel);

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
