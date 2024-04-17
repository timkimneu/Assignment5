//package model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import provider.model.EventTime;
//import provider.model.Schedule;
//import provider.model.Event;
//import provider.model.User;
//import provider.model.WeekTime;
//
//public class ScheduleAdapter implements Schedule {
//  private final ISchedule sch;
//
//  public ScheduleAdapter(ISchedule sch) {
//    this.sch = sch;
//  }
//
//  @Override
//  public User getUser() {
//    return sch.scheduleID();
//  }
//
//  @Override
//  public List<Event> getEvents() {
//    return null;
//  }
//
//  @Override
//  public boolean hasEventAtTime(EventTime time) {
//    return false;
//  }
//
//  @Override
//  public List<Event> getEventsAtTime(EventTime time) {
//    return null;
//  }
//
//  @Override
//  public Event getEventAtTime(WeekTime time) {
//    return null;
//  }
//
//  @Override
//  public void removeEvent(Event event) {
//
//  }
//
//  @Override
//  public void addEvent(Event event) {
//
//  }
//}
