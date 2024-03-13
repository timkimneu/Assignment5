package model;

/**
 * Represents the time frame that an event takes place in. Denotes the day and time that an event
 * begins at and the day and time the event will end on. Starting and ending day are denoted as
 * days of the week as an enum value. Time also takes in a string for the starting and ending time
 * of day in the form of a 4 number string using the military time method. The current Time class does
 * not allow for the creation of time frames extending for a time period greater than or equal to 1
 * full week. Any time that is intended to be interpreted as being a week long or longer will be
 * interpreted in being in a time span less than 7 days.
 */
public class Time {
  private final DaysOfTheWeek startDay;
  private final DaysOfTheWeek endDay;
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
  public Time(DaysOfTheWeek startDay, String startTime, DaysOfTheWeek endDay, String endTime) {
    this.startDay = startDay;
    this.endDay = endDay;
    this.checkHourMinute(startTime);
    this.checkHourMinute(endTime);
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Observes the day of the week that an event begins on.
   *
   * @return the day of the week enum which the event begins on.
   */
  public DaysOfTheWeek startDay() {
    return this.startDay;
  }

  /**
   * Observes the day of the week that an event ends on.
   *
   * @return the day of the week enum which the event ends on.
   */
  public DaysOfTheWeek endDay() {
    return this.endDay;
  }

  /**
   * Observes the military time that an event begins on as a String.
   *
   * @return String that represents the military time of when an event begins.
   */
  public String startTime() {
    return this.startTime;
  }

  /**
   * Observes the military time that an event ends on as a String.
   *
   * @return String that represents the military time of when an event ends.
   */
  public String endTime() {
    return this.endTime;
  }

  public boolean hasOverlap(Time t) {
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
  private int getHours(String time) {
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
}
