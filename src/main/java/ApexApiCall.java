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
import java.text.DecimalFormat;

public class ApexApiCall {

  static YamlVars envLoad = new YamlVars();

  public void getPlayerData(String playerName) {
    envLoad.connectYaml();
    DecimalFormat formatter = new DecimalFormat("#,###");
    final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{player}";
    String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{player}", playerName);

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

        int kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
        String formattedKills = formatter.format(kills);
        int damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
        String formattedDamage = formatter.format(damage);
        String rankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
        .getJSONObject("metadata").getString("rankName");

        // Once the API call is complete, update the UI 
        ApexStatsApp.updateUI(playerName, formattedKills, formattedDamage, rankScore);
      });
  }
}
