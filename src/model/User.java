package model;

/**
 *
 */
public class User {
  private final String name;

  /**
   * Instantiates a user with a name identifier.
   *
   * @param name String that represents the name of each user
   */
  public User(String name) {
    this.name = name;
  }

  /**
   * Instantiates a user with a name identifier.
   */
  public String name() {
    return this.name;
  }
}
