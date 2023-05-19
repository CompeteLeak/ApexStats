import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class YamlVars {
  public static Properties props = new Properties();
  public static String playerOne;
  public static String playerTwo;
  public static String apiKey;
  public static String platform;
  public static String playerThree;
  public static String playerFour;

  public void connectYaml(){

  	try {
      props.load(new FileInputStream("resources/config.yaml"));
      playerOne = props.getProperty("playerOne");
      playerTwo = props.getProperty("playerTwo");
      playerThree = props.getProperty("playerThree");
      playerFour = props.getProperty("playerFour");
      apiKey = props.getProperty("api_key");
      platform = props.getProperty("platform");
    } catch (IOException e) {
      System.err.println("Error loading config file: " + e.getMessage());
    }

  }

}
