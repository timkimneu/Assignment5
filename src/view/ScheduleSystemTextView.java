package view;

import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.Schedule;
import model.Time;
import model.User;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class ScheduleSystemTextView implements ScheduleSystemView {
  private final List<Schedule> schedules;
  private String viewer = "";

  public ScheduleSystemTextView(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  // returns the viewer that displays the schedule in a textual view
  @Override
  public String schedulesToString() {
    for (int sch = 0; sch < schedules.size(); sch++) {
      viewer += "User: ";
      viewer += schedules.get(sch).id() + "\n";
      Schedule currSch = schedules.get(sch);
      List<Event> listEvents = currSch.findEvents();
      addEventsForDay(listEvents, sch);
    }
    return viewer;
  }

  // add events to viewer if it is associated with the day
  private void getWhichDays(String day, int sch) {
    viewer += day + ": \n";
    Schedule currSch = schedules.get(sch);
    List<Event> listEvents = currSch.findEvents();

    for (int event = 0; event < listEvents.size(); event++) {
      Event currEvent = listEvents.get(event);
      Time currTime = currEvent.time();
      DaysOfTheWeek startDay = currTime.startDay();

      if (Objects.equals(startDay.toString(), day.toUpperCase())) {
        viewer += "\t";
        viewer += "name: " + listEvents.get(event).name() + "\n\t";

        Time time = listEvents.get(event).time();
        viewer += "time: " + time.startDay().getDay() + ": " + time.startTime() +
            " -> " + time.endDay().getDay() + ": " + time.endTime() + "\n\t";

        Location location = listEvents.get(event).location();
        viewer += "location: " + location.place() + "\n\t";
        viewer += "online: " + location.online() + "\n\t";

        viewer += "invitees: ";
        List<User> users = listEvents.get(event).users();
        for (int user = 0; user < users.size(); user++) {
          if (user != users.size()-1) {
            viewer += users.get(user).name().replaceAll("\"", "") + "\n\t";
          }
          else {
            viewer += users.get(user).name().replaceAll("\"", "") + "\n";
          }
        }
      }
    }
  }

  // determines which days have one or more events
  private void addEventsForDay(List<Event> listEvents, int sch) {
    Dictionary<String, Integer> dict= new Hashtable<>();
    dict.put("SUNDAY", 0);
    dict.put("MONDAY", 0);
    dict.put("TUESDAY", 0);
    dict.put("WEDNESDAY", 0);
    dict.put("THURSDAY", 0);
    dict.put("FRIDAY", 0);
    dict.put("SATURDAY", 0);

    for (int event = 0; event < listEvents.size(); event++) {
      Event currEvent = listEvents.get(event);
      Time currTime = currEvent.time();
      DaysOfTheWeek startDay = currTime.startDay();
      dict.put(startDay.toString(), dict.get(startDay.toString()) + 1);
    }

    addDaysHelper(dict, "Sunday", sch);
    addDaysHelper(dict, "Monday", sch);
    addDaysHelper(dict, "Tuesday", sch);
    addDaysHelper(dict, "Wednesday", sch);
    addDaysHelper(dict, "Thursday", sch);
    addDaysHelper(dict, "Friday", sch);
    addDaysHelper(dict, "Saturday", sch);
  }

  // adds events to viewer if the days have one or more events
  private void addDaysHelper(Dictionary<String, Integer> dict, String day, int sch) {
    if (dict.get(day.toUpperCase()) != 0) {
      getWhichDays(day, sch);
    }
    else {
      viewer += day + ":\n";
    }
  }
}
