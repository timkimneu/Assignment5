package provider.model;

import java.util.List;

/**
 * A version of the CentralSystem Model that only defines observations.
 * This interface is extended by CentralSystem so that every CentralSystem
 * can implicitly become a ReadOnlyCentralSystem. Prevents unintended
 * mutation.
 */
public interface ReadOnlyCentralSystem {
  /**
   * Returns a list representing all unique users who currently have a schedule stored in the
   * system. This will enable a client to see which schedules they are capable of viewing, and
   * to use the getUserEvents method below.
   *
   * @return a list of each User in the system
   */
  List<User> getUsers();

  /**
   * Return the schedule of the requested user if they have a schedule within the system.
   * Modifying the returned schedule should not affect the state of the system in any way.
   *
   * <p>Returning a copy of the whole schedule ensures that any client should have enough detail to
   * represent a whole schedule.
   *
   * @param user the user whose schedule should be returned
   * @return the schedule of the user that was requested, if the user has a schedule within the
   *         system.
   * @throws IllegalArgumentException if the given user does not have a schedule within the system
   */
  ReadOnlySchedule getUserSchedule(User user);

  /**
   * Determines whether the given event can be added to the system given its current state.
   * An event can be added if the users invited to the event who are contained by the system
   * do not have an overlapping event.
   *
   * @param event the event to be added
   * @return true if the event can be added, else false
   */
  boolean canAddEvent(Event event);
}
