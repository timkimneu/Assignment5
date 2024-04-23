package model;

import java.util.ArrayList;
import java.util.List;

import strategy.WorkTimeStrategy;

/**
 * Represents the work time planner or schedule system that allows for interaction between different
 * individual's schedules and the adding, modifying, and removing events from schedules within the
 * system. Adding, modifying, and removing events should add, modify, or remove events according
 * to the list of users of the specified event and according to the existing users in the planner
 * system. Throws errors if there are time conflicts between events for any attendee of the
 * modified event. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public class SatWorktimeModel extends SatPlannerModel {
  private final List<ISchedule<SatDOTW>> schedules;

  /**
   * Planner model that is initialized without a given list of schedules.
   */
  public SatWorktimeModel() {
    super();
    this.schedules = new ArrayList<>();
  }

  /**
   * Planner model that is initialized with a given list of schedules.
   */
  public SatWorktimeModel(List<ISchedule<SatDOTW>> schedules) {
    super(schedules);
    this.schedules = schedules;
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users, UserImpl user) {
    WorkTimeStrategy<SatDOTW> workTimeStrategy = new WorkTimeStrategy<>(this);
    workTimeStrategy.scheduleEvent(name, location, duration, users, user);
  }
}
