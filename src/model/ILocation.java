package model;

/**
 * Represents the location of an Event. A location is denoted by a place but can also be online.
 */
public interface ILocation {

  /**
   * Observes the state of the event, and whether it is online or not.
   *
   * @return Boolean according to whether the event is online or not.
   */
  boolean online();

  /**
   * Observes the place of the event.
   *
   * @return A string representing the place of the event.
   */
  String place();

}
