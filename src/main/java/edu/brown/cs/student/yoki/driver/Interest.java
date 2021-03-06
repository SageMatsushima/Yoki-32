package edu.brown.cs.student.yoki.driver;

public class Interest {
  private int id;
  private String tag;
  private String name;
  private int score;

  public Interest(int id, String tag) {
    this.id = id;
    this.tag = tag;
    this.score = -1;

    convertTagToName();
  }

  public Interest(int id, String tag, int score) {
    this.id = id;
    this.tag = tag;
    this.score = score;
    convertTagToName();
  }

  private void convertTagToName() {
    name = "";
    for (String word: tag.split("_")) {
      name += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
    }
    name = name.substring(0, name.length() - 1);
  }

  public int getId() {
    return id;
  }

  public String getTag() {
    return tag;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

}
