package controller;

import model.DaysOfTheWeek;
import model.EventAdapter;
import model.EventImpl;
import model.IEvent;
import model.IPlannerModel;
import model.ISchedule;
import model.LocationImpl;
import model.TimeAdapter;
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

/**
 * Represents the model of the Schedule system or collection of events over a calendar.
 * Implements the provider's Features class, which implement features that are requested
 * by a model using some input data collected from the user, and the resulting features
 * modify the view and/or the model accordingly. Composes of our controller, their view,
 * and our model in order to listen to and communicate changes to the model.
 */
public class ControllerAdapter implements Features {
  private final ScheduleSystem<DaysOfTheWeek> system;
  private final CentralSystemView csview;
  private final IPlannerModel<DaysOfTheWeek> model;

  /**
   * Initializes the controller adapter that takes in our controller, their view, and our model.
   *
   * @param system ScheduleSystem controller implementation.
   * @param view Provider's view that our controller will take in.
   * @param model Changes are implemented to the full planner system.
   */
  public ControllerAdapter(ScheduleSystem<DaysOfTheWeek> system, CentralSystemView view,
                           IPlannerModel<DaysOfTheWeek> model) {
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
    try {
      EventImpl event = convertEvent(builder.buildEvent());
      system.scheduleEvent(event.name(), event.location(), duration, event.users(),
              event.users().get(0));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public void displayCurrentSchedule() {
    ViewAdapter view = new ViewAdapter(csview, model);
    view.refresh();
  }

  @Override
  public void displayEventAt(WeekTime requestedTime) {
    User selectedUser = csview.currentSelectedUser();
    for (ISchedule<DaysOfTheWeek> sch : model.schedules()) {
      if (sch.scheduleID().equals(selectedUser.toString())) {
        for (IEvent<DaysOfTheWeek> event : sch.events()) {
          TimeAdapter eventTime = new TimeAdapter(event.time());
          if (eventTime.contains(requestedTime)) {
            csview.displayExistingEvent(new EventAdapter(event));
          }
        }
      }
    }
  }

  private EventImpl convertEvent(Event event) {
    DaysOfTheWeek startDay = DaysOfTheWeek.valueOf(event.getEventTime().getStartTime()
        .getWeekDay().toString().toUpperCase());
    DaysOfTheWeek endDay = DaysOfTheWeek.valueOf(event.getEventTime().getEndTime()
        .getWeekDay().toString().toUpperCase());
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
    try {
      system.modifyEvent(convertEvent(oldEvent), convertEvent(eventBuilder.buildEvent()),
              new UserImpl(eventBuilder.getHost().toString()));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
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
    //EventBuilder builder = new EventBuilderAdapter();
    //builder.setEventTime();
    Event oldEvent = eventBuilder.buildEvent();
    try {
      system.addEvent(convertEvent(oldEvent), convertEvent(oldEvent).users().get(0));
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
