import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class YamlVars {
  public static Properties props = new Properties();
  public static String playerOne;
  public static String playerTwo;
  public static String apiKey;
  public static String platform1;
  public static String platform2;
  public static String playerThree;
  public static String playerFour;
  public static String playerFive;

  public void connectYaml() {

    try {
      props.load(new FileInputStream("resources/config.yaml"));
      playerOne = props.getProperty("playerOne");
      playerTwo = props.getProperty("playerTwo");
      playerThree = props.getProperty("playerThree");
      playerFour = props.getProperty("playerFour");
      playerFive = props.getProperty("playerFive");
      apiKey = props.getProperty("api_key");
      platform1 = props.getProperty("platform1");
      platform2 = props.getProperty("platform2");
    } catch (IOException e) {
      System.err.println("Error loading config file: " + e.getMessage());
    }

  }

}
