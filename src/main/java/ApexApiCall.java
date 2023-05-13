import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import java.net.URI;
import java.time.Duration;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ApexApiCall{

	public Label killsLabel = new Label("Fetching kills...");

	public void getKills(){

	YamlVars envLoad = new YamlVars();
	envLoad.connectYaml();
	final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{username}";
	String apiUrl = API_URL.replace("{platform}", envLoad.platform).replace("{username}", envLoad.username);

		// Set up the JavaFX UI
   
    killsLabel.setStyle("-fx-font-size: 48pt; -fx-font-weight: bold; -fx-font-family: Impact;");
    killsLabel.setTextFill(Color.WHITE);

	HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(apiUrl))
      .header("TRN-Api-Key", envLoad.apiKey) // Add the API key as a header
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

    	  killsLabel.setStyle("-fx-font-size: 48pt; -fx-font-weight: bold; -fx-font-family: Impact;");
          killsLabel.setTextFill(Color.WHITE);
          // Update the UI with the parsed stats
          killsLabel.setText("Kills: " + kills);


        });
      });

	}
}
