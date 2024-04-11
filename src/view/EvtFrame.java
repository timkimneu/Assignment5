package view;

import controller.ScheduleSystem;
import model.Event;
import model.User;

/**
 * Class interface that represents the screen that pops up
 * when a user wants to add, modify, or remove an event to a selected
 * schedule in the planner. The window asks a user to input the name
 * of the event, a location, starting day, starting time, ending day,
 * ending time, and a list of users.
 */
public interface EvtFrame {

  void addListener(ScheduleSystem listener);

  void addDefaultEvent(Event event);

  void addSelectedUser(User user);

  void getUnmodifiedEvent(Event event);

  void setHost(User host);
}
