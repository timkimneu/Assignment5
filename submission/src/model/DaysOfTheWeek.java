package model;

/**
 * Represents days of the week to map out events as an enum. Possible days are all the seven days
 * of the week, starting with Sunday. Hashcode is used in order to map out the string values to the
 * week enum values. There is a method to return the display values as a string.
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
   * Represents days of the week to map out events as an enum. Possible days are all the seven days
   * of the week, starting with Sunday. Hashcode is used in order to map out the string values to
   * the week enum values. There is a method to return the display values as a string.
   *
   * @param day Day of the week as a String value.
   */
  DaysOfTheWeek(String day) {
    this.day = day;
  }

  /**
   * Gets the day of the week and returns it as a String.
   *
   * @return a String representing the day of the week.
   */
  public String observeDay() {
    return this.day;
  }
}
