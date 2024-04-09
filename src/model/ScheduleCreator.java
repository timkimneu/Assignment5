package model;

public class ScheduleCreator {
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
