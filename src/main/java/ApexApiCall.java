import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class ApexApiCall {

  static YamlVars envLoad = new YamlVars();
  DecimalFormat formatter = new DecimalFormat("#,###");

  public void getPlayerData(String playerName, String platform) {
    envLoad.connectYaml();
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{player}";
    String apiUrl = API_URL.replace("{platform}", platform).replace("{player}", playerName);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey)
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(this::handleResponse)
      .exceptionally(this::handleError)
      .thenAccept(stats -> handleData(stats, playerName));
  }

  private String handleResponse(HttpResponse < String > response) {
    int statusCode = response.statusCode();
    if (statusCode >= 200 && statusCode < 300) {
      return response.body();
    } else {
      throw new RuntimeException("API request failed with status code: " + statusCode);
    }
  }

  private String handleError(Throwable ex) {
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("API Request Failed");
      alert.setContentText(ex.getMessage());
      alert.showAndWait();
    });
    return null; // Returning null to indicate that error handling is complete
  }

  private void handleData(String stats, String playerName) {
    if (stats == null) {
      // Error occurred, handle accordingly
      return;
    }

    try {
      JSONObject json = new JSONObject(stats);
      JSONObject data = json.getJSONObject("data");
      JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

      int kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
      String formattedKills = formatter.format(kills);
      int damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
      String formattedDamage = formatter.format(damage);
      String rankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

      // Once the API call is complete, update the UI 
      ApexStatsApp.updateUI(playerName, formattedKills, formattedDamage, rankScore);
    } catch (Exception e) {
      Platform.runLater(() -> {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Data Processing Failed");
        alert.setContentText("An error occurred while processing the API response.");
        alert.showAndWait();
      });
    }
  }
}
