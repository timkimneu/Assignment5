package provider.model;

/**
 * A user of a calendar system who has a username, and may later be defined to have other
 * unique characteristics.
 * Design Decisions: User could potentially have just been represented by a string, given that
 * a username defines equality, but there is always the potential that user could have more
 * features in the future like an email/zoom account or some other information that might need to
 * be associated.
 */
public interface User {
  /**
   * Returns the username of the user. If two users have the same username, they are the same.
   * @return the username of the given user
   */
  String getUsername();

  /**
   * Gets the String representation of a user.
   * @return a String representing the user.
   */
  String toString();
}
