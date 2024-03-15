package model;

/**
 * Represents the location of an Event. A location is denoted by a place but can also be online.
 */
public class Location {
  private final boolean online;
  private final String place;

  /**
   *
   * @param online
   * @param place
   */
  public Location(boolean online, String place) {
    this.online = online;
    this.place = place;
  }

  public boolean online() {
    return this.online;
  }

  public String place() {
    return this.place;
  }
}
