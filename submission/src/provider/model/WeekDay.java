package provider.model;

/**
 * A custom enumeration of WeekDay different from Java's DayOfWeek by the ordering of days.
 *
 * <p>Design Decision: Because our times do not have a date currently, the day of the week must be
 * specified as one of the seven days of the week. The best decision here is an enumeration.
 * Each of our enumerations has a text representation for ease of use, and an integer representing
 * our calendars ordering of a week.
 */
public enum WeekDay {
  SUNDAY("Sunday", 1),
  MONDAY("Monday", 2),
  TUESDAY("Tuesday", 3),
  WEDNESDAY("Wednesday", 4),
  THURSDAY("Thursday", 5),
  FRIDAY("Friday", 6),
  SATURDAY("Saturday", 7);

  private final String day;
  private final int dayOrder;

  /**
   * Each enumeration for a WeekDay has a string representing the day and a numerical order.
   *
   * @param day      a string representation of the day
   * @param dayOrder a numerical ordered representation of the day.
   */
  WeekDay(String day, int dayOrder) {
    this.day = day;
    this.dayOrder = dayOrder;
  }

  /**
   * Gets the String representation of the day.
   *
   * @return the string representing the day
   */
  public String getDay() {
    return this.day;
  }

  /**
   * Gets the integer representation of the day for easy comparison of order.
   *
   * @return the integer representation of the day
   */
  public int getDayOrder() {
    return this.dayOrder;
  }

  /**
   * Returns the string representing the day.
   * @return the day of the week.
   */
  @Override
  public String toString() {
    return this.day;
  }
}
