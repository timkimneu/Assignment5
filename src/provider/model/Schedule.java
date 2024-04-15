package provider.model;

/**
 * An interface for a schedule that is owned by a user and contains events.
 * Contains relevant observations for the schedule's owner and events, as well as functionality
 * for the addition and removal of events on the schedule.
 * A schedule may or may not have a timezone, and any implementation should make clear how
 * timezones are handled
 * This interface belongs in the model as it is the intermediary organizing unit of data below the
 * system and above Events. It's cohesive purpose is to organize events for a single user.
 */
public interface Schedule extends ReadOnlySchedule {
  /**
   * Attempts to remove an event from the schedule.
   * @param event an event that should be removed from the Calendar.
   * @throws IllegalArgumentException if the event does not exist
   */
  void removeEvent(Event event);

  /**
   * Attempts to add the given events to the schedule, fails if the user already has an event at
   * the given time that is not the same event, if any of the given events overlap,
   * or if the user is not an attendee/host of the event.
   * @param event the events to add.
   * @throws IllegalArgumentException if the given event overlaps with an existing event within the
   *         calendar
   */
  void addEvent(Event event);
}
