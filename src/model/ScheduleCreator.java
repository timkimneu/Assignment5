package model;

/**
 * Methods for the ScheduleCreator. Verifying that the commands into the
 * main is properly managed. Initializes enums that represents the two
 * schedules the user are able to use: anytime and workhours. Takes in
 * the user input and creates the schedule strategy accordingly.
 */
public class ScheduleCreator {

  /**
   * Creates an enum according to the given game and displays them as a string.
   */
  public enum ScheduleType {
    ANYTIME("anytime"), WORKHOURS("workhours");
    private final String display;

    ScheduleType(String display) {
      this.display = display;
    }

    @Override
    public String toString() {
      return display;
    }
  }

  /**
   * Creates an enum according to the given strategy and displays them as a string.
   */
  public static NUPlannerModel createSchedule(ScheduleType type) {
    if (type == ScheduleType.ANYTIME) {
      return new NUPlannerModel();
    }
    if (type == ScheduleType.WORKHOURS) {
      return new WorkTimePlannerModel();
    }
    throw new IllegalArgumentException("Schedule does not exist");
  }
}
