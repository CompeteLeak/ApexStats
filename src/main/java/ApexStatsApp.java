import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApexStatsApp extends Application {
    private static final String API_URL = "https://public-api.tracker.gg/v2/apex/standard/profile/{platform}/{username}";

    @Override
    public void start(Stage primaryStage) {
        // Set up the JavaFX UI
        Label statsLabel = new Label("Fetching stats...");
        StackPane root = new StackPane(statsLabel);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("My Apex Stats");
        primaryStage.setScene(scene);

        // Make the API request
        HttpClient client = HttpClient.newHttpClient();
        String platform = "psn"; // Replace with your platform (psn, xbl, origin)
        String username = "YourUsername"; // Replace with your username
        String apiUrl = API_URL.replace("{platform}", platform).replace("{username}", username);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(stats -> {
                    // Update the UI with the response
                    statsLabel.setText(stats);
                });

        // Show the JavaFX window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
