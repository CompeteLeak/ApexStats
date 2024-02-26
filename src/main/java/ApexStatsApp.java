import java.util.Comparator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApexStatsApp extends Application {
    private static TableView<Player> tableView;
    private static ObservableList<Player> playerList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        playerList = FXCollections.observableArrayList();
        tableView = new TableView<>(playerList);

        TableColumn<Player, String> playerColumn = new TableColumn<>("Player");
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> rankColumn = new TableColumn<>("Rank");
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rankName"));

        TableColumn<Player, Integer> rankDivColumn = new TableColumn<>("Rank Division");
        rankDivColumn.setCellValueFactory(new PropertyValueFactory<>("rankDiv"));

        TableColumn<Player, String> rankScoreColumn = new TableColumn<>("Rank Score");
        rankScoreColumn.setCellValueFactory(new PropertyValueFactory<>("rankScore"));

        TableColumn<Player, String> legendColumn = new TableColumn<>("Tracked Legend");
        legendColumn.setCellValueFactory(new PropertyValueFactory<>("legendName"));

        TableColumn<Player, String> killsColumn = new TableColumn<>("Kills");
        killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));

        TableColumn<Player, String> damageColumn = new TableColumn<>("Damage");
        damageColumn.setCellValueFactory(new PropertyValueFactory<>("damage"));

        TableColumn<Player, String> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        tableView.getColumns().addAll(playerColumn, rankColumn, rankDivColumn, rankScoreColumn, legendColumn, killsColumn, damageColumn, winsColumn);
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

    public static void updateUI(String name, String rankName, int rankDiv, String rankScore, String legendName, String kills, String damage, String wins) {
        Platform.runLater(() -> {
            playerList.add(new Player(name, rankName, rankDiv, rankScore, legendName, kills, damage, wins));
            sortPlayerList();
        });
    }

    private static void sortPlayerList() {
        playerList.sort(Comparator
                .comparingInt(player -> {
                    try {
                        return Integer.parseInt(((Player) player)
                                .getRankScore()
                                .replaceAll(",", "")
                                .trim());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .reversed());
    }

    public static class Player {
        private final String name;
        private final String rankName;
        private final String rankScore;
        private final int rankDiv;
        public final String legendName;
        private final String kills;
        private final String damage;
        private final String wins;

        public Player(String name, String rankName, int rankDiv, String rankScore, String legendName, String kills, String damage, String wins) {
            this.name = name;
            this.rankName = rankName;
            this.rankDiv = rankDiv;
            this.rankScore = rankScore;
            this.legendName = legendName;
            this.kills = kills;
            this.damage = damage;
            this.wins = wins;
        }

        public String getName() {
            return name;
        }

        public String getRankName() {
            return rankName;
        }

        public String getRankScore() {
            return rankScore;
        }

        public int getRankDiv() {
            return rankDiv;
        }

        public String getLegendName() {
            return legendName;
        }

        public String getKills() {
            return kills;
        }

        public String getDamage() {
            return damage;
        }

        public String getWins() {
            return wins;
        }
    }
}
