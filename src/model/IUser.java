package model;

/**
 * Represents a User or individual attending an event. Only details a name with no other
 * information.
 */
public interface IUser {
  /**
   * Instantiates a user with a name identifier.
   */
  String name();
}
