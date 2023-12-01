import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ApexStatsApp extends Application {

  private static TableView < Player > tableView;
  private static ObservableList < Player > playerList;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    playerList = FXCollections.observableArrayList();
    tableView = new TableView < > (playerList);

    TableColumn < Player, String > playerColumn = new TableColumn < > ("Player");
    playerColumn.setCellValueFactory(new PropertyValueFactory < > ("name"));

    TableColumn < Player, String > rankColumn = new TableColumn < > ("Rank");
    rankColumn.setCellValueFactory(new PropertyValueFactory < > ("rankName"));

    TableColumn < Player, Integer > rankScoreColumn = new TableColumn < > ("Rank Score");
    rankScoreColumn.setCellValueFactory(new PropertyValueFactory < > ("rankScore"));

    TableColumn < Player, Integer > rankDivColumn = new TableColumn < > ("Rank Division");
    rankDivColumn.setCellValueFactory(new PropertyValueFactory < > ("rankDiv"));

    TableColumn < Player, String > legendCoulumn = new TableColumn < > ("Tracked Legend");
    legendCoulumn.setCellValueFactory(new PropertyValueFactory < > ("legendName"));

    TableColumn < Player, String > killsColumn = new TableColumn < > ("Kills");
    killsColumn.setCellValueFactory(new PropertyValueFactory < > ("kills"));

    //TableColumn<Player, String> damageColumn = new TableColumn<>("Damage");
    //damageColumn.setCellValueFactory(new PropertyValueFactory<>("damage"));

    //tableView.getColumns().addAll(playerColumn, rankColumn, rankScoreColumn, killsColumn, damageColumn);
    tableView.getColumns().addAll(playerColumn, rankColumn, rankScoreColumn, rankDivColumn, legendCoulumn, killsColumn);
    sortPlayerList(); // Sort the player list initially

    VBox vbox = new VBox(tableView);
    vbox.setPadding(new Insets(10));

    Scene scene = new Scene(vbox, 400, 300);

    primaryStage.setTitle("Leaderboards");
    primaryStage.setScene(scene);
    primaryStage.show();

    ApexApiCall apiCall = new ApexApiCall();
    YamlVars envLoad = new YamlVars();
    envLoad.connectYaml();

    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerOne, envLoad.platform1, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerTwo, envLoad.platform1, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerThree, envLoad.platform1, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerFour, envLoad.platform1, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerFive, envLoad.platform2, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerSix, envLoad.platform1, 0);
    apiCall.getPlayerDataByName(envLoad.apiKey, envLoad.playerSeven, envLoad.platform1, 0);
  }

  public static void updateUI(String name, String rankName, int rankScore, int rankDiv, String legendName, String kills) {
    Platform.runLater(() -> {
      playerList.add(new Player(name, rankName, rankScore, rankDiv, legendName, kills));
      sortPlayerList();
    });
  }

  private static void sortPlayerList() {
    playerList.sort(Comparator.comparingInt(Player::getRankScore).reversed());
  }

  public static class Player {
    private final String name;
    private final String rankName;
    private final int rankScore;
    private final int rankDiv;
    public final String legendName;
    private final String kills;
    //private final String damage;

    public Player(String name, String rankName, int rankScore, int rankDiv, String legendName, String kills) {
      this.name = name;
      this.rankName = rankName;
      this.rankScore = rankScore;
      this.rankDiv = rankDiv;
      this.legendName = legendName;
      this.kills = kills;
      //this.damage = damage;
    }

    public String getName() {
      return name;
    }

    public String getRankName() {
      return rankName;
    }

    public int getRankScore() {
      return rankScore;
    }

    public int getRankDiv(){
      return rankDiv;
    }

    public String getLegendName(){
      return legendName;
    }

    public String getKills() {
      return kills;
    }

    // public String getDamage() {
    //   return damage;
    // }
  }

}
