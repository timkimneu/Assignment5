package controller;

import model.DaysOfTheWeek;
import model.EventAdapter;
import model.EventImpl;
import model.IPlannerModel;
import model.LocationImpl;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;
import provider.controller.Features;
import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.User;
import provider.model.WeekTime;
import provider.strategy.SchedulingStrategy;
import provider.view.CentralSystemView;
import view.ViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ControllerAdapter implements Features {
  private final ScheduleSystem system;
  private final CentralSystemView csview;
  private final IPlannerModel model;

  public ControllerAdapter(ScheduleSystem system, CentralSystemView view, IPlannerModel model) {
    this.system = system;
    this.csview = view;
    this.model = model;
  }

  @Override
  public void readXML(String filename) {
    system.readXML(filename);
  }

  @Override
  public void writeXML(String filename) {
    system.writeXML(filename);
  }

  @Override
  public boolean scheduleEvent(EventBuilder builder, int duration) {
    return false;
  }

  @Override
  public void displayCurrentSchedule() {
    System.out.println("HI");
//    csview.displayAvailableUsers(csview.getListOfAvailableUsers());
    ViewAdapter view = new ViewAdapter(csview, model);
    view.refresh();
    //this.csview.refresh();
    //system.
  }

  @Override
  public void displayEventAt(WeekTime requestedTime) {
    User selectedUser = csview.currentSelectedUser();
    for (SchedulePlanner sch : model.schedules()) {
      if (sch.scheduleID().equals(selectedUser.toString())) {
        for (EventImpl event : sch.events()) {

          DaysOfTheWeek reqDotw = DaysOfTheWeek.valueOf(requestedTime.getWeekDay().toString().toUpperCase());
          String reqTime = requestedTime.getTime();

          DaysOfTheWeek endDotw = event.time().endDay();
          String endTime = event.time().endTime();
          TimeImpl reqTimeImpl = new TimeImpl(reqDotw, reqTime, endDotw, endTime);

          if (event.time().anyOverlap(reqTimeImpl)) {
            System.out.println("mlmlmlml");
            csview.displayExistingEvent(new EventAdapter(event));
          }
        }
      }
    }
  }

  private EventImpl convertEvent(Event event) {
    DaysOfTheWeek startDay = DaysOfTheWeek.valueOf(event.getEventTime().getStartTime()
        .getWeekDay().toString());
    DaysOfTheWeek endDay = DaysOfTheWeek.valueOf(event.getEventTime().getEndTime()
        .getWeekDay().toString());
    String startTime = event.getEventTime().getStartTime().getTime();
    String endTime = event.getEventTime().getEndTime().getTime();
    TimeImpl timeImpl = new TimeImpl(startDay, startTime, endDay, endTime);
    LocationImpl locImpl = new LocationImpl(event.isOnline(), event.getLocation());
    List<UserImpl> allUsers = new ArrayList<>();
    for (User u : event.getAllAttendees()) {
      String userStr = u.toString();
      UserImpl userImpl = new UserImpl(userStr);
      allUsers.add(userImpl);
    }
    return new EventImpl(event.getName(), timeImpl, locImpl, allUsers);
  }

  @Override
  public boolean modifyEvent(Event oldEvent, EventBuilder eventBuilder) {
    return false;
  }

  @Override
  public boolean removeEvent(Event oldEvent, User user) {
    try {
      system.removeEvent(convertEvent(oldEvent), new UserImpl(user.toString()));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public boolean addEvent(EventBuilder eventBuilder) {
    Event oldEvent = eventBuilder.buildEvent();
    try {
      system.addEvent(convertEvent(oldEvent));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public void setView(CentralSystemView view) {
    // ignore
  }

  @Override
  public void execute(SchedulingStrategy strategy) {
    // ignore
  }
}
