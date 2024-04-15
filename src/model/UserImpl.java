package model;

import java.util.Objects;

/**
 * Represents a User or individual attending an event. Only details a name with no other
 * information.
 */
public class UserImpl implements IUser {
  private final String name;

  /**
   * Instantiates a user with a name identifier.
   *
   * @param name String that represents the name of each user
   */
  public UserImpl(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  /**
   * Overwrites the equals method for the User class.
   *
   * @param other that represents the other object.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof UserImpl)) {
      return false;
    }
    UserImpl user = (UserImpl) other;
    return this.name.equals(user.name);
  }

  /**
   * Overwrites the hashCode method for the User class.
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(this.name);
  }
}
