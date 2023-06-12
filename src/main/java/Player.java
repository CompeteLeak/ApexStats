public class Player {
  private String name;
  private int rankScore;
  private String rank;
  private String kills;

  public Player(String name, String rank, int rankScore, String kills) {
    this.name = name;
    this.rankScore = rankScore;
    this.rank = rank;
    this.kills = kills;
  }

  // Add getters and setters for the properties
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRankScore() {
    return rankScore;
  }

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public String getKills() {
    return kills;
  }

  public void setKills(String kills) {
    this.kills = kills;
  }
}
