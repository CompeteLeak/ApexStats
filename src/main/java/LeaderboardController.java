import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderboardController {

  @FXML
  private TableView < Player > tableView;

  @FXML
  private TableColumn < Player, String > usernameColumn;

  @FXML
  private TableColumn < Player, Integer > rankScoreColumn;

  @FXML
  private TableColumn < Player, String > rankColumn;

  @FXML
  private TableColumn < Player, Integer > killsColumn;

  @FXML
  private TableColumn < Player, Integer > damageColumn;

  public TableView < Player > getTableView() {
    return tableView;
  }

  @FXML
  public void initialize() {
    // Initialize the table columns
    usernameColumn.setCellValueFactory(new PropertyValueFactory < > ("name"));
    rankScoreColumn.setCellValueFactory(new PropertyValueFactory < > ("rankScore"));
    rankColumn.setCellValueFactory(new PropertyValueFactory < > ("rank"));
    killsColumn.setCellValueFactory(new PropertyValueFactory < > ("kills"));
    damageColumn.setCellValueFactory(new PropertyValueFactory < > ("damage"));
  }

}
