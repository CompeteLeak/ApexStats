import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.text.DecimalFormat;
import org.json.JSONObject;
import org.json.JSONArray;

public class ApexApiCall {

  private static final int MAX_RETRIES = 3;
  private static final int INITIAL_BACKOFF_DELAY = 1000; // Initial delay in milliseconds

  private long getBackoffTime(int retryCount) {
    // Exponential backoff formula (adjust as needed)
    return (long)(INITIAL_BACKOFF_DELAY * Math.pow(2, retryCount));
  }

  public void getPlayerDataByName(String apiKey, String playerName, String platform, int retryCount) {
    String API_URL = "https://api.mozambiquehe.re/bridge?auth=%s&player=%s&platform=%s";
    String apiUrl = String.format(API_URL, apiKey, playerName, platform);
    //makeApiRequest(apiUrl);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(this::handleResponse)
      .exceptionally(ex -> {
        if (ex.getMessage().contains("429") && retryCount < MAX_RETRIES) {
          // Apply exponential backoff before retrying
          try {
            Thread.sleep(getBackoffTime(retryCount));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          makeApiRequest(apiUrl, playerName, retryCount + 1);
        } else {
          handleError(ex);
        }
        return null;
      })
      .thenAccept(stats -> handleData(stats, playerName));
  }

  public void getPlayerDataByUID(String apiKey, String playerUID, String platform) {
    String API_URL = "https://api.mozambiquehe.re/bridge?auth=%s&uid=%s&platform=%s";
    String apiUrl = String.format(API_URL, apiKey, playerUID, platform);
    //makeApiRequest(apiUrl);
  }

  private void makeApiRequest(String apiUrl, String playerName, int retryCount) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(this::handleResponse)
      .exceptionally(ex -> {
        if (ex.getMessage().contains("429") && retryCount < MAX_RETRIES) {
          // Apply exponential backoff before retrying
          try {
            Thread.sleep(getBackoffTime(retryCount));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          makeApiRequest(apiUrl, playerName, retryCount + 1);
        } else {
          handleError(ex);
        }
        return null;
      })
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

  private String handleError(Throwable ex) { // Modified the return type to match the expected type
    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("API Request Failed");
      alert.setContentText(ex.getMessage());
      alert.showAndWait();
    });
    return ""; // Return an empty string to align with thenApply's expected return type
  }

  private void handleData(String stats, String playerName) {
    DecimalFormat formatter = new DecimalFormat("#,###");

    if (stats == null) {
      // Error occurred, handle accordingly
      return;
    }

    try {
      JSONObject json = new JSONObject(stats);

      // Extracting data from the "global" section
      JSONObject global = json.getJSONObject("global");
      int rankScore = global.getJSONObject("rank").getInt("rankScore");
      String rank = global.getJSONObject("rank").getString("rankName");

      // Extracting data from the "legends" section for the selected legend (Vantage)
      JSONObject legends = json.getJSONObject("legends");
      JSONObject selectedLegend = legends.getJSONObject("selected");
      String legendName = selectedLegend.getString("LegendName");
      JSONArray legendData = selectedLegend.getJSONArray("data");
      int brKills = 0;
      for (int i = 0; i < legendData.length(); i++) {
        JSONObject stat = legendData.getJSONObject(i);
        if (stat.getString("name").equals("BR Kills")) {
          brKills = stat.getInt("value");
          break;
        }
      }

      // Update the UI with the retrieved information
      ApexStatsApp.updateUI(playerName, rank, rankScore, formatter.format(brKills));
    } catch (Exception e) {
      e.printStackTrace();
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
