package model;

/**
 * Represents the location of an Event. A location is denoted by a place but can also be online.
 */
public class Location {
  private final boolean online;
  private final String place;

  /**
   * Instantiates the location of the place, which includes parameters of whether
   * the event is online, as well as where it is.
   *
   * @param online Boolean that describes whether the event is online or not
   * @param place String that describes where the event takes place.
   */
  public Location(boolean online, String place) {
    this.online = online;
    this.place = place;
  }

  /**
   * Observes the state of the event, and whether it is online or not.
   *
   * @return Boolean according to whether the event is online or not.
   */
  public boolean online() {
    return this.online;
  }

  /**
   * Observes the place of the event.
   *
   * @return A string representing the place of the event.
   */
  public String place() {
    return this.place;
  }
}
