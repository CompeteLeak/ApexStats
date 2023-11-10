import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class YamlVars {
  public String playerOne;
  public String playerTwo;
  public String apiKey;
  public String platform1;
  public String platform2;
  public String playerThree;
  public String playerFour;
  public String playerFive;
  public String playerSix;
  public String playerSeven;

  public void connectYaml() {
    Properties props = new Properties();

    try {
      props.load(new FileInputStream("resources/config.yaml"));
      playerOne = props.getProperty("playerOne");
      playerTwo = props.getProperty("playerTwo");
      playerThree = props.getProperty("playerThree");
      playerFour = props.getProperty("playerFour");
      playerFive = props.getProperty("playerFive");
      playerSix = props.getProperty("playerSix");
      playerSeven = props.getProperty("playerSeven");
      apiKey = props.getProperty("api_key");
      platform1 = props.getProperty("platform1");
      platform2 = props.getProperty("platform2");
    } catch (IOException e) {
      System.err.println("Error loading config file: " + e.getMessage());
    }
  }
}
