package provider.strategy;

import java.util.List;

import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.ReadOnlySchedule;

/**
 * A strategy that selects a time for an event to occur based on some specific criteria when
 * given a schedule, event duration, and details for an event besides the time.
 */
public interface SchedulingStrategy {

  /**
   * Returns an Event of the specific duration. The time of the event, the attendees, and
   * whether an event is even returned depend on the nature of the specific strategy.
   *
   * @param schedules the schedules of the users to be invited to the event
   * @param duration  the duration of the event in minutes
   * @param builder   An event builder with details predefined besides the time
   * @return an Event representing an available time, based on the strategy.
   * @throws IllegalArgumentException if there is no time that meets the criteria of the strategy,
   *                                  or if there is not enough information in the builder.
   */
  Event scheduleEvent(List<ReadOnlySchedule> schedules, int duration, EventBuilder builder);
}
