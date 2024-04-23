package controller;

import model.DaysOfTheWeek;
import model.IEvent;
import model.ISchedule;
import model.LocationImpl;
import model.IPlannerModel;
import model.UserImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the mock for the planner model. Verifying that the commands
 * into the Readable is properly managed, as well as providing feedback
 * to the user through methods that determine the state of items in the
 * program.
 */
public class PlannerMock implements IPlannerModel<DaysOfTheWeek> {
  private final Appendable log;

  /**
   * Launches the calendar with the given the model.
   * The schedule type is started with the given customization as well.
   *
   * @param log appendable of the current calendar
   */
  public PlannerMock(Appendable log) {
    this.log = log;
  }

  @Override
  public int getFirstDay() {
    return 0;
  }

  @Override
  public void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                          List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                          List<String> eventName, String id) {
    // ignore
  }

  @Override
  public void addEvent(IEvent<DaysOfTheWeek> event) {
    try {
      this.log.append(String.format("name = %s, startDay = %s, endDay = %s, startTime = %s, " +
                      "endTime = %s, online = %s, place = %s", event.name(),
              event.time().startDay(), event.time().endDay(), event.time().startTime(),
              event.time().endTime(), getOnlineBool(event.location().online()),
              event.location().place()));
    } catch (IOException e) {
      // continue
    }
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    try {
      this.log.append(String.format("name = %s, online = %s, place = %s, duration = %d",
              name, getOnlineBool(location.online()), location.place(), duration));
    } catch (IOException e) {
      // continue
    }
  }

  // gets the boolean of whether the object is online or not
  private boolean getOnlineBool(Object o) {
    String onlineStr = o.toString();
    return onlineStr.equals("true");
  }

  @Override
  public void modifyEvent(IEvent<DaysOfTheWeek> event, IEvent<DaysOfTheWeek> newEvent,
                          UserImpl user) {
    try {
      // should not ever run, Controller calls remove then add for its modifyEvent method
      this.log.append(String.format("oldName = %s, oldStartDay = %s, oldEndDay = %s, " +
                      "oldStartTime = %s, oldEndTime = %s, oldOnline = %s, oldPlace = %s, " +
                      "newName = %s, newStartDay = %s, newEndDay = %s, newStartTime = %s, " +
                      "newEndTime = %s, newOnline = %s, newPlace = %s, user = %s",
              event.name(), event.time().startDay(), event.time().endDay(),
              event.time().startTime(), event.time().endTime(),
              getOnlineBool(event.location().online()), event.location().place(),
              newEvent.name(), newEvent.time().startDay(), newEvent.time().endDay(),
              newEvent.time().startTime(), newEvent.time().endTime(),
              getOnlineBool(newEvent.location().online()), newEvent.location().place(),
              user.name()));
    } catch (IOException e) {
      // continue
    }
  }

  @Override
  public void removeEvent(IEvent<DaysOfTheWeek> event, UserImpl user) {
    try {
      this.log.append(String.format("name = %s, startDay = %s, endDay = %s, startTime = %s, " +
                      "endTime = %s, online = %s, place = %s, user = %s", event.name(),
              event.time().startDay(), event.time().endDay(), event.time().startTime(),
              event.time().endTime(), getOnlineBool(event.location().online()),
              event.location().place(), user.name()));
    } catch (IOException e) {
      // continue
    }
  }

  @Override
  public List<ISchedule<DaysOfTheWeek>> schedules() {
    return new ArrayList<>();
  }

  @Override
  public List<IEvent<DaysOfTheWeek>> events(String name) {
    return new ArrayList<>();
  }

  @Override
  public List<String> users() {
    return new ArrayList<>();
  }
}
