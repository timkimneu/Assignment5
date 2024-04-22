package model;

import java.util.Objects;

public class SatTimeImpl implements ITime<SatDOTW>{
  private final SatDOTW startDay;
  private final SatDOTW endDay;
  private final String startTime;
  private final String endTime;

  /**
   * Represents the start and end time of an event. The starting and ending day is denoted as a day
   * of the week enum. The starting and ending time is denoted as a 4-digit string that represents
   * the hours and minutes in the military time format.
   *
   * @param startDay  The day of the week an event starts at as an Enum.
   * @param startTime The hour and minute an event starts at as a String.
   * @param endDay    The day of the week an event ends at as an Enum.
   * @param endTime   The hour and minute and event ends at as a String.
   */
  public SatTimeImpl(SatDOTW startDay, String startTime, SatDOTW endDay, String endTime) {
    this.startDay = startDay;
    this.endDay = endDay;
    this.checkHourMinute(startTime);
    this.checkHourMinute(endTime);
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public SatDOTW startDay() {
    return this.startDay;
  }

  @Override
  public SatDOTW endDay() {
    return this.endDay;
  }

  @Override
  public String startTime() {
    return this.startTime;
  }

  @Override
  public String endTime() {
    return this.endTime;
  }

  @Override
  public boolean anyOverlap(ITime<SatDOTW> t) {
    boolean check1 = false;
    boolean check2 = false;
    if (this.startDay().compareTo(t.startDay()) < 0) {
      check1 = this.hasOverlapContainedWeek(t);
    } else if (this.startTime().compareTo(t.startTime()) < 0
        && this.startDay().compareTo(t.startDay()) == 0) {
      check1 = this.hasOverlapContainedWeek(t);
    } else {
      check2 = t.hasOverlapContainedWeek(this);
    }
    return check1 || check2 || this.hasOverlapCrossWeek(t);
  }

  @Override
  public boolean hasOverlapContainedWeek(ITime<SatDOTW> t) {
    // check if start day of other Time object is before the ending day of this Time object
    int firstStartHr = this.getHours(this.startTime());
    int firstEndingHr = this.getHours(this.endTime());
    int secondStartHr = t.getHours(t.startTime());
    int secondEndHr = t.getHours(t.endTime());

    int firstStartMin = this.getMinutes(this.startTime());
    int firstEndMin = this.getMinutes(this.endTime());
    int secondStartMin = this.getMinutes(t.startTime());
    int secondEndMin = this.getMinutes(t.endTime());

    if (this.endDay().compareTo(t.startDay()) > 0) {
      return true;
    } else if (this.endDay().compareTo(t.startDay()) == 0) {
      if (firstEndingHr > secondStartHr) {
        return true;
      }
    } else if (firstEndingHr == secondStartHr) {
      if (firstEndMin > secondStartMin) {
        return true;
      }
    }

    // otherwise return false
    return false;
  }

  // checks for any overlaps that may occur between 2 Time
  // objects when one or both roll over into a new week
  private boolean hasOverlapCrossWeek(ITime<SatDOTW> t) {
    // check that if both time objects roll over to next week (both roll over = overlap)
    if (this.startDay().compareTo(this.endDay()) > 0 && t.startDay().compareTo(t.endDay()) > 0) {
      return true;
      // check that if this Time object rolls over to next week, check the end day of this
      // Time object to the start day of the other Time object.
    } else if (this.startDay().compareTo(this.endDay()) > 0) {
      return this.endDay().compareTo(t.startDay()) > 0;
      // check that if the other Time object rolls over to next week, check the end day of
      // the other Time object to the start of this Time object.
    } else if (t.startDay().compareTo(t.endDay()) > 0) {
      return t.endDay().compareTo(this.startDay()) > 0;
    }
    // Otherwise return false.
    return false;
  }

  // check that time only contains numbers and no other characters
  // otherwise throw IllegalArgumentException
  private int checkNumberStringTime(String time) {
    int timeInt;
    try {
      timeInt = Integer.parseInt(time);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("String must only contain numbers!");
    }
    return timeInt;
  }

  // get the number of minutes for the given String (military) time
  private int getMinutes(String time) {
    int timeInt = this.checkNumberStringTime(time);
    return timeInt % 100;
  }

  // get the number of hours for the given String time
  @Override
  public int getHours(String time) {
    int timeInt = this.checkNumberStringTime(time);
    int minutes = this.getMinutes(time);
    return (timeInt - minutes) / 100;
  }

  private void checkHourMinute(String time) throws IllegalArgumentException {
    // check that if time does not contain exactly 4 alphanumeric
    // characters, throw an IllegalArgumentException
    if (time.length() != 4) {
      throw new IllegalArgumentException("Time must be represented by a 4 digit String!");
    }

    // split time into hours and minutes
    // additionally: check that time only contains numbers and no other characters
    // otherwise throw IllegalArgumentException
    int minutes = this.getMinutes(time);
    int hours = this.getHours(time);

    // check that if number of minutes is less than 0 or greater than 59
    // then throw IllegalArgumentException
    if (minutes < 0 || minutes > 59) {
      throw new IllegalArgumentException("Invalid number of minutes!");
    }

    // check that if number of hours is less than 0 or greater than 23
    // then throw IllegalArgumentException
    if (hours < 0 || hours > 23) {
      throw new IllegalArgumentException("Invalid number of hours!");
    }
  }

  // takes a String time of 4-digit format and retrieves the corresponding AM/PM time
  private String getAMPM(String time) {
    // convert String time into (integer) number of minutes and hours.
    int timeInt = Integer.parseInt(time);
    int minutes = timeInt % 100;
    int hours = (timeInt - minutes) / 100;

    // if number of minutes exceeds 60, subtract 60 from minutes and add an hour to hours.
    while (minutes > 60) {
      minutes -= 60;
      hours += 1;
    }

    // after minutes are converted into hours, check for invalid number of hours.
    if (hours < 0 || hours > 23) {
      throw new IllegalArgumentException("Invalid number of hours!");
      // check for invalid number of minutes.
    } else if (minutes < 0) {
      throw new IllegalArgumentException("Invalid number of minutes!");
    } else {
      return convertAMPM(hours, minutes);
    }
  }

  // converts the given number of hours and minutes
  // assumes that hours is between 0 and 23 and that minutes is between 0 and 59
  // from above parent function.
  private String convertAMPM(int hours, int minutes) {
    // convert hours and minutes into AM/PM format
    boolean isAM = false;
    // starting by checking for AM
    if (hours < 11) {
      if (hours == 0) {
        hours += 12;
      }
      isAM = true;
      // check for PM format
    } else {
      if (hours != 12) {
        hours -= 12;
      }
    }

    // convert hours and minutes into Strings, if minutes is less than 10 add extra 0.
    String hoursString = String.valueOf(hours);
    String minutesString;
    if (minutes < 10) {
      minutesString = "0" + minutes;
    } else {
      minutesString = String.valueOf(minutes);
    }

    if (isAM) {
      return hoursString + ":" + minutesString + "AM";
    } else {
      return hoursString + ":" + minutesString + "PM";
    }
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof SatTimeImpl)) {
      return false;
    }
    SatTimeImpl time = (SatTimeImpl) other;
    return this.startDay.equals(time.startDay) && this.startTime.equals(time.startTime) &&
        this.endDay.equals(time.endDay) && this.endTime.equals(time.endTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.startDay, this.startTime, this.endDay, this.endTime);
  }

  @Override
  public String toString() {
    return this.startDay.observeDay() + " " + this.startTime + " " + this.endDay.observeDay()
        + " " + this.endTime + " ";
  }
}
