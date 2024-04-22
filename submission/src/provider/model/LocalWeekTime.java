package provider.model;

import java.util.Objects;

import provider.model.WeekDay;
import provider.model.WeekTime;

/**
 * An implementation of the WeekTime interface.
 *
 * <p>Design Decisions: A mainly value based class. Other implementations might make note of
 * timezones, but this one is not required to.
 */
public class LocalWeekTime implements WeekTime {

  // INVARIANT: day is not null.
  private final WeekDay day;

  // INVARIANT: hour is between 0 and 23 inclusive on both ends
  private final int hour;

  // INVARIANT: minute is between 0 and 59 inclusive on both ends
  private final int minute;

  // Used to easily determine the ordering of LocalWeekTimes
  private final int chronoLogicalOrder;

  /**
   * Constructs a LocalWeekTime with the given day, hour, and minute. LocalWeekTime with the same
   * day, hour, and minute should be identical.
   * @param day The day of the week of instance in time.
   * @param hour The hour of the instance in time in military time between 0 and 23
   * @param minute the minute of the instance in time between 0 and 59
   * @throws IllegalArgumentException If hour or minute are outside their defined ranges
   * @throws NullPointerException if the day passed is null
   */
  public LocalWeekTime(WeekDay day, int hour, int minute) throws IllegalArgumentException,
          NullPointerException {

    if (hour < 0 || hour > 23) {
      throw new IllegalArgumentException("Hour must be between 0 and 23.");
    } else if (minute < 0 || minute > 59) {
      throw new IllegalArgumentException("Minute must be between 0 and 59.");
    }

    this.day = Objects.requireNonNull(day);
    this.hour = hour;
    this.minute = minute;

    // this integer allows for the easy comparison of day, hour, and minute in that order
    this.chronoLogicalOrder = 10000 * this.day.getDayOrder() + 100 * hour + minute;
  }

  @Override
  public WeekDay getWeekDay() {
    return this.day;
  }

  @Override
  public String getTime() {
    String textHour = String.valueOf(hour);
    String textMinute = String.valueOf(minute);
    if (textHour.length() == 1) {
      textHour = "0" + textHour;
    }

    if (textMinute.length() == 1) {
      textMinute = "0" + textMinute;
    }

    return textHour + textMinute;
  }


  @Override
  public int getHour() {
    return this.hour;
  }

  @Override
  public int getMinute() {
    return this.minute;
  }

  @Override
  public boolean isBefore(WeekTime other) {
    int otherChronoLogicalOrder =
            10000 * other.getWeekDay().getDayOrder() + 100 * other.getHour() + other.getMinute();

    return this.chronoLogicalOrder < otherChronoLogicalOrder;
  }

  @Override
  public boolean isAfter(WeekTime other) {
    int otherChronoLogicalOrder =
            10000 * other.getWeekDay().getDayOrder() + 100 * other.getHour() + other.getMinute();

    return this.chronoLogicalOrder > otherChronoLogicalOrder;
  }

  @Override
  public boolean isSame(WeekTime other) {
    int otherChronoLogicalOrder =
            10000 * other.getWeekDay().getDayOrder() + 100 * other.getHour() + other.getMinute();

    return this.chronoLogicalOrder == otherChronoLogicalOrder;
  }

  @Override
  public int compareTo(WeekTime o) {
    int otherChronoLogicalOrder =
            10000 * o.getWeekDay().getDayOrder() + 100 * o.getHour() + o.getMinute();

    return this.chronoLogicalOrder - otherChronoLogicalOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LocalWeekTime that = (LocalWeekTime) o;
    return hour == that.hour && minute == that.minute
            && chronoLogicalOrder == that.chronoLogicalOrder && day == that.day;
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, hour, minute, chronoLogicalOrder);
  }

  @Override
  public String toString() {
    String paddedHour = padLeftZeros(String.valueOf(hour), 2);
    String paddedMin = padLeftZeros(String.valueOf(minute), 2);

    return day.getDay() + " " + paddedHour + ":" + paddedMin;
  }

  /**
   * Pads a string with 0s.
   * @param inputString the string to pad with zeroes
   * @param length overall length of string
   * @return a padded string
   */
  private String padLeftZeros(String inputString, int length) {
    StringBuilder sb = new StringBuilder();
    sb.append("0".repeat(Math.max(0, length)));

    return sb.substring(inputString.length()) + inputString;
  }
}
