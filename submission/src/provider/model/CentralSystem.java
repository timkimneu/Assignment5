package provider.model;

/**
 * An interface for a central calendar system that manages multiple users schedules and maintains
 * consistency between them. A central system can view, create, delete, or modify events in users
 * calendars or read in schedules.
 *
 * <p>Each specific implementation should make note of how they handle the consistency of events.
 *
 * <p>Design Decisions: One major design decision of the central system was that it should take in a
 * Event when creating an Event or modifying an event. This added simplicity in the methods of
 * central system. A class external to the model should be able to know how to build an event,
 * as an EventBuilder interface is specified. A similar decision was made in regard to parsing
 * XML. An XML parser should know how to build a schedule, given the ScheduleBuilder interface.
 * This means that we can totally remove the XML reading functionality from the central system,
 * and just have a method that takes in a pre-defined schedule.
 * Another important design decision is that this system give access to user's schedules. This
 * is important, in case schedules end up storing any more information in the future that might
 * need to be accessed by the controller. Of course, access to schedules should not change the
 * system itself.
 *
 * <p>This class belongs in the model as it is key to the logic of our data representations.
 */
public interface CentralSystem extends ReadOnlyCentralSystem {
  /**
   * Given an event with pre-defined detail, adds the event to the schedule of all users who are
   * listed within the event.
   *
   * <p>Having the event be predefined makes implementation of createEvent more simple, and allows
   * the use of different types of events depending on the EventBuilder used.
   *
   * <p>If a user invited to the event is not contained within the system the implementation should
   * decide what happens.
   *
   * @param event the new event to add to the Schedules of existing users.
   * @throws IllegalArgumentException if the event has attendees in the system who already have a
   *                                  meeting at the time of the event.
   */
  void createEvent(Event event);


  /**
   * Takes an existing event and updates it with new detail. This can be used to remove users from
   * an event, change the time, name, or location. If a user contained by the new event is not
   * contained within the system, the implementation should decide what happens. If there is a
   * conflicting event for any of the attendees of the modified event, no change occurs. The host
   * of the event cannot change during modification.
   *
   * <p>Taking in an event to modify seems logical. A client should have access to a list of all
   * existing events in the system by using the observations of the CentralSystem, Schedule, and
   * Event classes. The SameEvent method in the Event interface should make implementation more
   * simple. As mentioned before, we can also take in an event for what should be updated because
   * of the EventBuilder interface.
   *
   * @param oldEvent the event that should be modified.
   * @param newEvent the new event that should replace the old event
   * @throws IllegalArgumentException if the old event is not contained within the system, or
   *                                  if the time of the new event conflicts with the schedule of
   *                                  any attendee, or if the host of the new event is not the same
   *                                  as the host of the old event.
   * @throws NullPointerException if any of the given events are null
   */
  void modifyEvent(Event oldEvent, Event newEvent);

  /**
   * Removes the event from the given user's schedule, and updates all calendars accordingly. If
   * the user is the host of the event, the event is removed from all attendee's schedules.
   * As is mentioned in other methods, taking in an event for recognition makes sense here given
   * what the client has access to. Taking in a user also makes sense, as the client has access to
   * all users and this allows specificity in the behavior of what happens to the event.
   *
   * @param event An event that should be removed from a user's schedules.
   * @throws IllegalArgumentException if the given event to modify is not in the system,
   *                                  or if the given user is not a host or attendee of the
   *                                  given event.
   * @throws NullPointerException if the given pointer is null
   */
  void removeEvent(Event event, User user);

  /**
   * Adds a schedule to the system. If a schedule with the same owner of the given schedule is not
   * already contained by the system, the system stores the schedule. If there is a duplicate owner,
   * all the events from the given schedule are added unless there are overlapping events,
   * in which case an error is thrown.
   *
   * <p>This method allows the external XMLReader to easily load in schedules without needing the IO
   * operation to occur within the CentralSystem interface.
   *
   * @param toAdd a schedule which should be added to the system
   * @throws IllegalArgumentException if the owner of the given schedule already has an event, and
   *                                  an event in the given schedule overlaps with the already
   *                                  present schedule.
   * @throws NullPointerException if the given schedule is null
   */
  void addSchedule(Schedule toAdd);
}
