package model;

public enum SatDOTW {
  SATURDAY("Saturday", 0),
  SUNDAY("Sunday", 1),
  MONDAY("Monday", 2),
  TUESDAY("Tuesday", 3),
  WEDNESDAY("Wednesday", 4),
  THURSDAY("Thursday", 5),
  FRIDAY("Friday", 6);

  private String day;
  private int dayOrder;

  /**
   * Represents days of the week to map out events as an enum. Possible days are all the seven days
   * of the week, starting with Sunday. Hashcode is used in order to map out the string values to
   * the week enum values. There is a method to return the display values as a string.
   *
   * @param day Day of the week as a String value.
   */
  SatDOTW(String day, int dayOrder) {
    this.day = day;
    this.dayOrder = dayOrder;
  }

  SatDOTW(String day) {
    this.day = day;
  }

  SatDOTW(int dayOrder) {
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
