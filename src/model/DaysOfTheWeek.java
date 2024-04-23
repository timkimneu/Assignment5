package model;

/**
 * Represents days of the week to map out events as an enum. Possible days are all the seven days
 * of the week, starting with Sunday. Hashcode is used in order to map out the string values to the
 * week enum values. There is a method to return the display values as a string.
 */
public enum DaysOfTheWeek {
  SUNDAY("Sunday", 0),
  MONDAY("Monday", 1),
  TUESDAY("Tuesday", 2),
  WEDNESDAY("Wednesday", 3),
  THURSDAY("Thursday", 4),
  FRIDAY("Friday", 5),
  SATURDAY("Saturday", 6);

  private String day;
  private int dayOrder;

  /**
   * Represents days of the week to map out events as an enum. Possible days are all the seven days
   * of the week, starting with Sunday. Hashcode is used in order to map out the string values to
   * the week enum values. There is a method to return the display values as a string.
   *
   * @param day Day of the week as a String value.
   */
  DaysOfTheWeek(String day, int dayOrder) {
    this.day = day;
    this.dayOrder = dayOrder;
  }

  DaysOfTheWeek(String day) {
    this.day = day;
  }

  DaysOfTheWeek(int dayOrder) {
    this.dayOrder = dayOrder;
  }

  /**
   * Gets the day of the week and returns it as a String.
   *
   * @return a String representing the day of the week.
   */
  public String observeDay() {
    return this.day;
  }

  public int getDayOrder() {
    return this.dayOrder;
  }
}
