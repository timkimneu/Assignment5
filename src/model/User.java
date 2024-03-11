package model;

/**
 *
 */
public class User {
  private final String name;

  /**
   *
   * @param name
   */
  public User(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   */
  public String getUID() {
    return this.name;
  }
}
