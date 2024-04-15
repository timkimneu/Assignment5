package provider.view;

import java.util.List;

import provider.controller.Features;
import provider.model.User;

/**
 * Allows the client to view and request modification to an existing event or to create a new event.
 * This frame should be given a list of features in order to achieve this. Additionally,
 * a list of users should be passed to the event frame in order to allow the user to see who is
 * available.
 */
public interface EventFrame {

  /**
   * Adds a list of features that a controller can perform so that the EventFrame
   * can respond properly to input by creating, modifying, or deleting frames.
   * @param features Features that the controller is capable of.
   * @throws IllegalArgumentException if the features are null
   */
  void addFeatures(Features features);

  /**
   * Updates the available users within the system whose schedules can be seen. This list of users
   * should override previously given lists of users.
   * @param users The users to display
   * @throws IllegalArgumentException if the users are null
   */
  void displayAvailableUsers(List<User> users);
}
