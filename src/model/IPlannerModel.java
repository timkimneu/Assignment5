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
   *
   * @return
   */
  int getFirstDay();

  /**
   *
   * @param startDay
   * @param endDay
   * @param startTime
   * @param endTime
   * @param loc
   * @param users
   * @param eventName
   * @param id
   */
  void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                   List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                   List<String> eventName, String id);

  /**
   *
   * @param name
   * @param location
   * @param duration
   * @param users
   */
  void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users);
  /**
   * Adds the given event to all users in the planner system provided by the list of users
   * pertaining to the given event. In other words, adds this event to a user's schedule if they
   * are in the list of attendees in a new event. Throws an error if there exists any overlap
   * with the new event with any existing event of any attendee. Throws an error if the given
   * event is already scheduled in any attendee's schedule.
   *
   * @param event Event class that represents the event to be added.
   */
  void addEvent(IEvent<T> event);

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
