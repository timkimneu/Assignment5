package view;

import controller.ScheduleSystem;
import model.DaysOfTheWeek;
import model.EventImpl;
import model.ITime;
import model.LocationImpl;
import model.SchedulePlanner;
import model.UserImpl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

/**
 * Creates a Viewer to view the schedule in a formatted manner, with
 * each event indented under their corresponding day of the week. This
 * helps the user keep track of what events have been scheduled throughout
 * the week.
 */
public class ScheduleSystemTextView implements ScheduleSystemView {
  private final List<SchedulePlanner> schedules;
  private String viewer = "";

  /**
   * Observes the state of the list of schedules.
   *
   * @param schedules List of schedules.
   */
  public ScheduleSystemTextView(List<SchedulePlanner> schedules) {
    this.schedules = schedules;
  }

  /**
   * Creates a Viewer to view the schedule in a formatted manner, with
   * each event indented under their corresponding day of the week. Displays
   * the schedule as a string textual view.
   *
   * @return String that represents the schedule format
   */
  @Override
  public String schedulesToString() {
    for (int sch = 0; sch < schedules.size(); sch++) {
      viewer += "User: ";
      viewer += schedules.get(sch).scheduleID() + "\n";
      SchedulePlanner currSch = schedules.get(sch);
      List<EventImpl> listEvents = currSch.events();
      addEventsForDay(listEvents, sch);
    }
    return viewer;
  }

  @Override
  public void makeVisible() {
    // ignore, not implemented for text view.
  }

  @Override
  public void hidePanel() {
    // ignore, not implemented for text view.
  }

  @Override
  public void addListener(ScheduleSystem listener) {
    // ignore, not implemented for text view.
  }

  @Override
  public void refresh() {
    // ignore, not implemented for text view.
  }

  // add events to viewer if it is associated with the day
  private void getWhichDays(String day, int sch) {
    viewer += day + ": \n";
    SchedulePlanner currSch = schedules.get(sch);
    List<EventImpl> listEvents = currSch.events();

    for (int event = 0; event < listEvents.size(); event++) {
      EventImpl currEvent = listEvents.get(event);
      ITime currTime = currEvent.time();
      DaysOfTheWeek startDay = currTime.startDay();

      if (Objects.equals(startDay.toString(), day.toUpperCase())) {
        viewer += "\t";
        viewer += "name: " + listEvents.get(event).name() + "\n\t";

        ITime time = listEvents.get(event).time();
        viewer += "time: " + time.startDay().observeDay() + ": " + time.startTime() +
                " -> " + time.endDay().observeDay() + ": " + time.endTime() + "\n\t";

        LocationImpl location = listEvents.get(event).location();
        viewer += "location: " + location.place() + "\n\t";
        viewer += "online: " + location.online() + "\n\t";

        viewer += "invitees: ";
        List<UserImpl> users = listEvents.get(event).users();
        for (int user = 0; user < users.size(); user++) {
          if (user != users.size() - 1) {
            viewer += users.get(user).name().replaceAll("\"", "") + "\n\t";
          } else {
            viewer += users.get(user).name().replaceAll("\"", "") + "\n";
          }
        }
      }
    }
  }

  // determines which days have one or more events
  private void addEventsForDay(List<EventImpl> listEvents, int sch) {
    Dictionary<String, Integer> dict = new Hashtable<>();
    dict.put("SUNDAY", 0);
    dict.put("MONDAY", 0);
    dict.put("TUESDAY", 0);
    dict.put("WEDNESDAY", 0);
    dict.put("THURSDAY", 0);
    dict.put("FRIDAY", 0);
    dict.put("SATURDAY", 0);

    for (int event = 0; event < listEvents.size(); event++) {
      EventImpl currEvent = listEvents.get(event);
      ITime currTime = currEvent.time();
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
    } else {
      viewer += day + ":\n";
    }
  }
}
