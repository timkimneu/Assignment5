package model;

/**
 *
 */
public enum DaysOfTheWeek {
  SUNDAY("Sunday"),
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday");

  private final String day;

  /**
   *
   * @param day
   */
  DaysOfTheWeek(String day) {
    this.day = day;
  }

  /**
   * Gets the day of the week and returns it as a String.
   *
   * @return a String representing the day of the week.
   */
  public String getDay() {
    return this.day;
  }
}
