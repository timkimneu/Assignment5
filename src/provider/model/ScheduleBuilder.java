package provider.model;

import java.util.Collection;

/**
 * An interface that defines the necessary functionality to build a schedule from the Schedule
 * interface. This interface is important to give classes outside the model an idea of how to
 * construct a schedule. The XML parser, for example, needs to know how to create a schedule, and
 * should not be tied to a specific implementation of Schedule..
 */
public interface ScheduleBuilder {

  /**
   * When build is called after copySchedule, copies the given read only schedule.
   * @param schedule the schedule to copy.
   */
  ScheduleBuilder copySchedule(ReadOnlySchedule schedule);

  /**
   * Returns a builder that will construct a schedule with the given owner upon calling build.
   * @param owner the owner of the schedule to be built
   * @return a builder that will create an event with the given owner upon construction.
   */
  ScheduleBuilder setOwner(User owner);

  /**
   * Returns a builder that will construct a schedule with an owner who has the given userName
   * upon calling build.
   * @param userName the owner of the schedule to be built
   * @return a builder that will create an event with an owner with the given username
   *         upon construction.
   */
  ScheduleBuilder setOwner(String userName);

  /**
   * Adds events that the Schedule will attempt to add when buildSchedule is called. If
   * conflicting events are added, buildSchedule will throw an error. Multiple calls of this method
   * are additive -- both/all collections of events will be added.
   * @param events a collection of events that the schedule will attempt to add upon building.
   * @return A builder that will attempt to add the given events along with any other previously
   *         given events when build is called.
   */
  ScheduleBuilder addEvents(Collection<Event> events);

  /**
   * Resets the builder to its default state.
   * @return a builder in its default state.
   */
  ScheduleBuilder reset();

  /**
   * Attempts to build a schedule with the given parameters.
   * @return a Schedule with the parameters provided to the builder.
   * @throws IllegalArgumentException Throws an illegal argument exception if
   *         not enough information has been passed to create a schedule, or if the events of
   *         the schedule are conflicting.
   */
  Schedule buildSchedule();
}
