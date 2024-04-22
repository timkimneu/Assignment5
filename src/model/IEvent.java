package model;

import java.util.List;

/**
 * Represents a social gathering that has a name, a specified starting and ending time,
 * a location, and a list of attendees along with the host of the event.
 */
public interface IEvent<T> {

  /**
   * Observer in order to observe the name field.
   *
   * @return a String that has the name of the event
   */
  String name();

  /**
   * Observes the Time of the event, which includes the starting and ending day and the starting
   * and ending time of the event.
   *
   * @return Time object that represents beginning and ending time of an event.
   */
  ITime<T> time();

  /**
   * Observer in order to observe the name location. Includes information
   * of the place and whether the event is online or not.
   *
   * @return instance of Location field
   */
  LocationImpl location();

  /**
   * Observer in order to observe the list of users. Includes information
   * of the place and whether the event is online or not.
   *
   * @return instance of list of users field
   */
  List<UserImpl> users();

  /**
   * Observer in order to observe the host user.
   *
   * @return User that represents the host
   */
  UserImpl host();

  /**
   * Method that returns a boolean to determine if user is the host.
   *
   * @param user User that may represent the host
   *
   * @return a boolean indicating if the given user is the host of this event.
   */
  boolean isHost(String user);

}
