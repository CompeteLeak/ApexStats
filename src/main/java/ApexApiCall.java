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

    static int kills;
    static int damage;
    static String rankScore;
    static YamlVars envLoad = new YamlVars();
    static String p1;

    public void playerOneData() {
        envLoad.connectYaml();
        p1 = envLoad.username.toString();
        final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{username}";
        String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{username}", p1);

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

                    kills = segments.getJSONObject("stats").getJSONObject("kills").getInt("value");
                    damage = segments.getJSONObject("stats").getJSONObject("damage").getInt("value");
                    rankScore = segments.getJSONObject("stats").getJSONObject("rankScore")
                            .getJSONObject("metadata").getString("rankName");

                    // Once the API call is complete, update the UI 
                    ApexStatsApp.updateUI(p1, kills, damage, rankScore);
                });
    }
}
