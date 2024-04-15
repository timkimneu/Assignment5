package provider.view;

import java.util.List;

import provider.controller.Features;
import provider.model.Event;
import provider.model.ReadOnlySchedule;
import provider.model.User;

/**
 * An interface for interacting with a View that is meant to display a Central System.
 * The view presumes that the only information that needs to be shown is a single schedule,
 * the users whose schedules are possible to view, an Event, and an error message in case
 * a user request to the view cannot be fulfilled.
 */
public interface CentralSystemView {

  /**
   * Displays the given schedule to the view in some manner which should be described by the
   * implementation.
   * @param schedule A schedule to display.
   * @throws IllegalArgumentException if the given schedule is null
   */
  void displaySchedule(ReadOnlySchedule schedule);

  /**
   * Displays an error message to the client of the system in some visible manner. The
   * implementation should describe how the error message is conveyed.
   * @param text Text containing the error message.
   */
  void displayError(String text);

  /**
   * Display determines whether the view is visible or not. A controller can use this functionality
   * to prepare the view by adding users before showing it.
   * @param show true if view should be shown, else false
   */
  void display(boolean show);

  /**
   * The implementation should show the details of the given event
   * and potentially give the client a way to edit the details of the event if desired.
   *
   * @param existingEvent an existing event that should be shown to the client
   * @throws IllegalArgumentException if the existing event is null.
   */
  void displayExistingEvent(Event existingEvent);

  /**
   * Updates the available users within the system whose schedules can be seen. This list of users
   * should override previously given lists of users.
   * @param users The users to display
   * @throws IllegalArgumentException if the users are null
   */
  void displayAvailableUsers(List<User> users);

  /**
   * Gets the User whose schedule is currently being displayed. This method is important for
   * allowing the controller to know whose schedule to interact with in the model.
   * @return the user whose schedule is currently being displayed. null if no schedule is currently
   *         displayed
   */
  User currentSelectedUser();

  /**
   * Adds a list of features that a controller can perform so that the view can respond properly
   * to input.
   * @param features Features that the controller is capable of.
   * @throws IllegalArgumentException if the features are null
   */
  void addFeatures(Features features);
}
