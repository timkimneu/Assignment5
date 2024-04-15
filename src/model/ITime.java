package model;

/**
 * Represents the time frame that an event takes place in. Denotes the day and time that an event
 * begins at and the day and time the event will end on. Starting and ending day are denoted as
 * days of the week as an enum value. Time also takes in a string for the starting and ending time
 * of day in the form of a 4 number string using the military time method. The current Time class
 * does not allow for the creation of time frames extending for a time period greater than or equal
 * to 1 full week. Any time that is intended to be interpreted as being a week long or longer will
 * be interpreted in being in a time span less than 7 days. The time fields of Time (startTime and
 * endTime) are invariant with startTime and endTime being strings that are exactly 4 characters
 * long represented by numbers where the first 2 numbers represent the number of hours ranging
 * from 00 to 23 and the last 2 numbers ranging from 00 to 59. In other words the first number in
 * the 4 number string can only hold values from 0 to 2, the second number from 0 to 9 when the
 * first number is 0 or 1 and from 0 to 3 when the first number is 2. The third number ranges from
 * 0 to 5 and the fourth number ranges from 0 to 9.
 */
public interface ITime {
  /**
   * Observes the day of the week that an event begins on.
   *
   * @return the day of the week enum which the event begins on.
   */
  DaysOfTheWeek startDay();

  /**
   * Observes the day of the week that an event ends on.
   *
   * @return the day of the week enum which the event ends on.
   */
  DaysOfTheWeek endDay();

  /**
   * Observes the military time that an event begins on as a String.
   *
   * @return String that represents the military time of when an event begins.
   */
  String startTime();

  /**
   * Observes the military time that an event ends on as a String.
   *
   * @return String that represents the military time of when an event ends.
   */
  String endTime();

  /**
   * Checks if this time and the given time have any overlap with each other pertaining to the day
   * of the week and if one starts and the other ends on the same day, check the hours and minutes.
   * Also is able to check for overlap crossing between the beginning and end of the week.
   *
   * @param t Other Time object to be compared to this Time object.
   * @return True if there exists an overlap between the two Time objects and false otherwise.
   */
  boolean anyOverlap(TimeImpl t);
}
