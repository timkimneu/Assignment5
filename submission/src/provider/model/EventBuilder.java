package provider.model;

import provider.model.Event;

import java.util.List;

/**
 * An interface that defines the necessary behavior for instantiating an event.
 *
 * <p>Design Decisions: This interface is important, as it defines instruction to create an Event
 * for a class that might exist outside the model. This allows the CentralSystem to take in an Event
 * when adding events instead of defining a method that takes in data like the parameters of the
 * methods below.
 */
public interface EventBuilder {

  /**
   * Returns a builder that will construct an identical event to the given event upon calling build.
   * This method is for convenience.
   *
   * @param event the event with parameters to be copied
   * @return a builder that will create an event with the parameters of the given event
   */
  EventBuilder copyEvent(Event event);

  /**
   * Returns a builder that will create an event with the given name upon calling build.
   *
   * @param name the name to be given to the event upon calling buildEvent.
   * @return a builder that will create an event with the given name upon calling build
   */
  EventBuilder setName(String name);

  /**
   * Returns a builder that will create an event with the given location upon calling build.
   *
   * @param location the location to be given to the event upon calling buildEvent.
   * @return a builder that will create an event with the given location upon calling build
   */
  EventBuilder setLocation(String location);

  /**
   * Returns a builder that will create an event with the given online status upon calling build.
   *
   * @param isOnline the online status to be given to the event upon calling buildEvent.
   * @return a builder that will create an event with the given online status upon calling build
   */
  EventBuilder setOnline(boolean isOnline);

  /**
   * Returns the current host set within the builder.
   *
   * @return the current host set within the builder
   */
  User getHost();

  /**
   * Returns a builder that will create an event with the given host upon calling build.
   *
   * @param host the host for the event upon calling buildEvent.
   * @return a builder that will create an event with the given host upon calling build
   */
  EventBuilder setHost(User host);

  /**
   * Returns a builder that will create an event with the given host upon calling build.
   *
   * @param host the userName for the host for the event upon calling buildEvent.
   * @return a builder that will create an event with the given host upon calling build
   */
  EventBuilder setHost(String host);

  /**
   * Returns a builder that will create an event with the given attendee among its attendees. If
   * the attendee has already been added or is the host, they will only appear once. This
   * method does not overwrite any previously added attendees
   *
   * @param attendees an attendee to be invited to the event upon calling buildEvent.
   * @return a builder that will create an event with the given name upon calling build
   */
  EventBuilder addAttendees(List<User> attendees);

  /**
   * This method should return a list of the attendees currently attending the event,
   * including the host. Modifying the list should not affect the builder in any way.
   *
   * @return a list of users
   */
  List<User> getAttendees();

  /**
   * Returns a builder that will create an event with the given attendees among its attendees. If
   * an attendee among the attendees has already been added or is the host, they will only
   * appear once. This method overwrites any previously added attendees.
   *
   * @param attendees attendees to be invited to the event upon calling buildEvent.
   * @return a builder that will create an event with the given name upon calling build
   */
  EventBuilder setAttendees(List<User> attendees);

  /**
   * Returns a builder that will create an event with the given  time.
   *
   * @param time the time of the event upon calling buildEvent.
   * @return a builder that will create an event with the given time upon calling build
   */
  EventBuilder setEventTime(EventTime time);

  /**
   * Returns a builder that will create an event with the given start time.
   *
   * @param time the start time of the event upon calling buildEvent.
   * @return a builder that will create an event with the given start time upon calling build
   */
  EventBuilder setStartTime(WeekTime time);

  /**
   * Returns a builder that will create an event with the given end time.
   *
   * @param time the end time of the event upon calling buildEvent.
   * @return a builder that will create an event with the given end time upon calling build
   */
  EventBuilder setEndTime(WeekTime time);

  /**
   * Clears the builder of any parameters previously passed.
   *
   * @return a builder as if newly constructed.
   */
  EventBuilder reset();

  /**
   * Builds an event with the parameters that have been added by the methods listed in the
   * EventBuilder interface.
   *
   * @return an Event with parameters defined within the EventBuilder
   * @throws IllegalArgumentException if not enough information has been given to build an event.
   */
  Event buildEvent();
}
