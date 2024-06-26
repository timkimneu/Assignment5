package view;

import controller.ScheduleSystem;
import model.IEvent;
import model.UserImpl;

/**
 * Class interface that represents the screen that pops up when a user wants to add, modify, or
 * remove an event to a selected schedule in the planner. The window asks a user to input the name
 * of the event, a location, starting day, starting time, ending day, ending time, and a list of
 * users.
 */
public interface EvtFrame<T> {

  /**
   * Adds the controller to the frame to allow communication between the view and the model.
   *
   * @param listener Controller to connect to view.
   */
  void addListener(ScheduleSystem<T> listener);

  /**
   * Event to be autofilled for a selected event.
   *
   * @param event Event to be autofilled in for.
   */
  void addDefaultEvent(IEvent<T> event);

  /**
   * Adds the selected user to the drop-down list in the event panel.
   *
   * @param user User to be added to the drop-down list.
   */
  void addSelectedUser(UserImpl user);

  /**
   * Gets the plain event.
   *
   * @param event Event to pass back.
   */
  void getUnmodifiedEvent(IEvent<T> event);
}
