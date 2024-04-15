package provider.model;

/**
 * An interface that defines the behavior for a point in time that is situated exclusively
 * within the range of a single week. This time has no timezone or date, just
 * a weekday and a hour and minute in military time.
 *
 * <p>Design Decision: This implementation of time was decided upon in absence of any suggestion
 * that timezone and date would be needed in the future. This class contains the necessary
 * functionality to enable event overlap comparison, as well as the features of time that match
 * the XML format.
 */
public interface WeekTime extends Comparable<WeekTime> {

  /**
   * Returns the enumeration representing the day of the WeekTime.
   * @return the day this time is on
   */
  WeekDay getWeekDay();

  /**
   * Gets a length four string representation of the time formatted in military time. As an example,
   * 1:45 PM is represented as 1345. The start of a day is represented as 0000, and the latest
   * time within a day is 2359.
   * @return a string representation of the time
   */
  String getTime();

  /**
   * Gets an integer representing the hour of the time in military time. That is, from 0-23.
   * @return The hour of the time
   */
  int getHour();

  /**
   * Gets an integer representing the minute of the time. That is, from 0-59.
   * @return the minute of the time
   */
  int getMinute();

  /**
   * Given another WeekTime, determines if this time comes before the other time. A WeekTime is
   * before if its WeekDay is smaller. If they are equal, the hour is compared. Lastly, the minute
   * is compared.
   * @param other A WeekTime to compare to.
   * @return true if this WeekTime comes before the other WeekTime.
   */
  boolean isBefore(WeekTime other);

  /**
   * Given another WeekTime, determines if this time comes after the other time. A WeekTime is
   * after if its WeekDay is bigger. If they are equal, the hour is compared. Lastly, the minute is
   * compared.
   * @param other A WeekTime to compare to.
   * @return true if this WeekTime comes after the other WeekTime.
   */
  boolean isAfter(WeekTime other);

  /**
   * Given another WeekTime, determines if this time is at the same time as the other time. A
   * WeekTime is the same if the WeekDay, hour, and minute are the same.
   * @param other A WeekTime to compare to.
   * @return true if this WeekTime is at the same time as the other WeekTime.
   */
  boolean isSame(WeekTime other);

}
