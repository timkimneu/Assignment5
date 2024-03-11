package model;

/**
 *
 */
public class Time {
  private final DaysOfTheWeek startDay;
  private final DaysOfTheWeek endDay;
  private final String startTime;
  private final String endTime;

  /**
   *
   * @param startDay
   * @param startTime
   * @param endDay
   * @param endTime
   */
  public Time(DaysOfTheWeek startDay, String startTime, DaysOfTheWeek endDay, String endTime) {
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   *
   * @return
   */
  public String getStartAMPM() {
    return this.getAMPM(this.startTime);
  }

  /**
   *
   * @return
   */
  public String getEndAMPM() {
    return this.getAMPM(this.endTime);
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
