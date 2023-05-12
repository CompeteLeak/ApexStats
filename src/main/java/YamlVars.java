import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class YamlVars {
  public static Properties props = new Properties();
  public static String username;
  public static String apiKey;
  public static String platform;

  public void connectYaml(){

  	try {
      props.load(new FileInputStream("resources/config.yaml"));
      username = props.getProperty("username");
      apiKey = props.getProperty("api_key");
      platform = props.getProperty("platform");
    } catch (IOException e) {
      System.err.println("Error loading config file: " + e.getMessage());
    }

  }

}
