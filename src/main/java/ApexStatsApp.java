import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class ApexStatsApp extends Application {

  private static VBox vbox;
  private TableView tableView;
  private static ObservableList playerList;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    playerList = FXCollections.observableArrayList();
    tableView = new TableView < ApexStatsApp.Player > (playerList);

    TableColumn < Player, String > playerColumn = new TableColumn < > ("Player");
    playerColumn.setCellValueFactory(new PropertyValueFactory < > ("username"));

    TableColumn < Player, String > rankColumn = new TableColumn < > ("Rank");
    rankColumn.setCellValueFactory(new PropertyValueFactory < > ("rank"));

    TableColumn < Player, Integer > rankScoreCoulumn = new TableColumn < > ("Rank Score");
    rankScoreCoulumn.setCellValueFactory(new PropertyValueFactory < > ("rankScore"));

    TableColumn < Player, String > killsColumn = new TableColumn < > ("Kills");
    killsColumn.setCellValueFactory(new PropertyValueFactory < > ("kills"));

    TableColumn < Player, Integer > damageColumn = new TableColumn < > ("Damage");
    damageColumn.setCellValueFactory(new PropertyValueFactory < > ("damage"));

    tableView.getColumns().addAll(playerColumn, rankColumn,rankScoreCoulumn, killsColumn, damageColumn);
    sortPlayerList(); // Sort the player list initially

    vbox = new VBox(tableView);
    vbox.setPadding(new Insets(10));

    Scene scene = new Scene(vbox, 400, 300);

    primaryStage.setTitle("Leaderboards");
    primaryStage.setScene(scene);
    primaryStage.show();

    YamlVars envLoad = new YamlVars();
    envLoad.connectYaml();
    ApexApiCall playerData = new ApexApiCall();
    playerData.getPlayerData(envLoad.playerOne, envLoad.platform1);
    playerData.getPlayerData(envLoad.playerTwo, envLoad.platform1);
    playerData.getPlayerData(envLoad.playerThree, envLoad.platform1);
    playerData.getPlayerData(envLoad.playerFour, envLoad.platform1);
    playerData.getPlayerData(envLoad.playerFive, envLoad.platform2);
    playerData.getPlayerData(envLoad.playerSix, envLoad.platform1);
  }

  public static void updateUI(String username, String rank, int rankScore, String kills, String damage) {
    Platform.runLater(() -> {
      playerList.add(new Player(username, rank, rankScore, kills, damage));
      sortPlayerList();
    });
  }

  private static void sortPlayerList() {
    playerList.sort(Comparator.comparingInt(Player::getRankScore).reversed());
  }

  public static class Player {
    private final String username;
    private final String rank;
    private final int rankScore;
    private final String kills;
    private final String damage;

    public Player(String username, String rank, int rankScore, String kills, String damage) {
      this.username = username;
      this.rank = rank;
      this.rankScore = rankScore;
      this.kills = kills;
      this.damage = damage;
    }

    public String getUsername() {
      return username;
    }

    public String getRank() {
      return rank;
    }

    public int getRankScore() {
      return rankScore;
    }

    public String getKills() {
      return kills;
    }

    public String getDamage() {
      return damage;
    }
  }

}
