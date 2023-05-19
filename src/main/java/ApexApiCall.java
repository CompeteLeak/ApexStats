import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.net.URI;
import java.time.Duration;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApexApiCall {

  static YamlVars envLoad = new YamlVars();

  public void playerOneData() {
    envLoad.connectYaml();
    String p1 = envLoad.playerOne.toString();
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{playerOne}";
    String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{playerOne}", p1);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey)
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(stats -> {
        JSONObject json = new JSONObject(stats);
        JSONObject data = json.getJSONObject("data");
        JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

        int p1Kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
        int p1Damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
        String p1RankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

        // Once the API call is complete, update the UI 
        ApexStatsApp.updateUI(p1, p1Kills, p1Damage, p1RankScore);
      });
  }

  public void playerTwoData() {

    envLoad.connectYaml();
    String p2 = envLoad.playerTwo.toString();
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{playerTwo}";
    String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{playerTwo}", p2);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey)
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(stats -> {
        JSONObject json = new JSONObject(stats);
        JSONObject data = json.getJSONObject("data");
        JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

        int p2Kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
        int p2Damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
        String p2RankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

        // Once the API call is complete, update the UI 
        ApexStatsApp.updateUI(p2, p2Kills, p2Damage, p2RankScore);
      });

  }

  public void playerThreeData() {

    envLoad.connectYaml();
    String p3 = envLoad.playerThree.toString();
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{playerThree}";
    String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{playerThree}", p3);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey)
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(stats -> {
        JSONObject json = new JSONObject(stats);
        JSONObject data = json.getJSONObject("data");
        JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

        int p3Kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
        int p3Damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
        String p3RankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

        // Once the API call is complete, update the UI 
        ApexStatsApp.updateUI(p3, p3Kills, p3Damage, p3RankScore);
      });

  }

  public void playerFourData() {

    envLoad.connectYaml();
    String p4 = envLoad.playerFour.toString();
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{playerFour}";
    String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{playerFour}", p4);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey)
      .timeout(Duration.ofSeconds(10))
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(stats -> {
        JSONObject json = new JSONObject(stats);
        JSONObject data = json.getJSONObject("data");
        JSONObject segments = data.getJSONArray("segments").getJSONObject(0);

        int p4Kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
        int p4Damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
        String p4RankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

        // Once the API call is complete, update the UI 
        ApexStatsApp.updateUI(p4, p4Kills, p4Damage, p4RankScore);
      });

  }
}
