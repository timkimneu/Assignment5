package model;

import java.util.List;

/**
 * Represents a part of the full planner or schedule system that allows for interaction between
 * different individual's schedules and the adding, modifying, and removing events from schedules
 * within the system. Adding, modifying, and removing events should add, modify, or remove events
 * according to the list of users of the specified event and according to the existing users in the
 * planner system. Throws errors if there are time conflicts between events for any attendee of the
 * modified event.
 */
public interface IPlannerModel<T> extends IReadOnlyPlannerModel<T> {
  /**
   * Returns the index of Monday to allow for proper alignment in the WorkTime strategy
   * between schedules starting on different days (e.g. Sunday vs Saturday).
   *
   * @return Returns the index of Monday for this calendar type.
   */
  int getFirstDay();

  /**
   * Adds a new schedule to this list of schedules with the given arguments.
   *
   * @param startDay List of starting days in the schedule.
   * @param endDay List of ending days in the schedule
   * @param startTime List of starting times in the schedule.
   * @param endTime List of ending times in the schedule.
   * @param loc List of locations in the schedule.
   * @param users List of attendees in the schedule.
   * @param eventName List of event names in the schedule.
   * @param id The user to be attached to the newly created schedule.
   */
  void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                   List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                   List<String> eventName, String id);

  /**
   * Makes an event with the provided parameters provided, and schedules the event.
   * Adds the given event to all users in the planner system provided by the list of users
   * pertaining to the given event. In other words, adds this event to a user's schedule if they
   * are in the list of attendees in a new event. Throws an error if there exists any overlap
   * with the new event with any existing event of any attendee. Throws an error if the given
   * event is already scheduled in any attendee's schedule.
   *
   * @param name     String that represents the event name.
   * @param location Location class that represents the place and whether event is online.
   * @param duration How long the event is in minutes.
   * @param users    List of users that are attending the event.
   * @param user     User to be set as host.
   */
  void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users,
                     UserImpl user);

  /**
   * Adds the given event to all users in the planner system provided by the list of users
   * pertaining to the given event. In other words, adds this event to a user's schedule if they
   * are in the list of attendees in a new event. Throws an error if there exists any overlap
   * with the new event with any existing event of any attendee. Throws an error if the given
   * event is already scheduled in any attendee's schedule.
   *
   * @param event Event class that represents the event to be added.
   * @param user to be host of the given event.
   */
  void addEvent(IEvent<T> event, UserImpl user);

  /**
   * Modifies an existing event by replacing the old event with the new given event. Works by
   * first removing the event and then adding the event to applicable schedules. If there are any
   * time conflicts as specified above, throws an error. If the old event is the same as the new
   * "modified" event, also throw an error. If the old event does not exist, throw an error. Will
   * modify the event for all schedules that contain the old event. Only adds the new modified
   * event to the individuals listed in the new event.
   *
   * @param event    Old event that will be replaced.
   * @param newEvent New Event to replace with old event.
   * @param user     User that is attending the event.
   */
  void modifyEvent(IEvent<T> event, IEvent<T> newEvent, UserImpl user);

  /**
   * Removes an existing event from all schedules in the system. Throw an error if the event to be
   * removed cannot be found in any schedule that is meant to be removed from.
   *
   * @param event Event that will be removed.
   * @param user  User that is attending the event.
   */
  void removeEvent(IEvent<T> event, UserImpl user);
}
