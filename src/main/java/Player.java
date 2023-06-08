public class Player {
  private String name;
  private String rankScore;
  private int rank;
  private int kills;

  public Player(String name, String rankScore, int rank, int kills) {
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

  public String getRankScore() {
    return rankScore;
  }

  public void setRankScore(String rankScore) {
    this.rankScore = rankScore;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public int getKills() {
    return kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }
}
