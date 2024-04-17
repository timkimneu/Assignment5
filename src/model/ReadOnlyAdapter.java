package model;

import provider.model.Event;
import provider.model.EventTime;
import provider.model.ReadOnlySchedule;
import provider.model.User;
import provider.model.WeekTime;

import java.util.ArrayList;
import java.util.List;

public class ReadOnlyAdapter implements ReadOnlySchedule {

  private final SchedulePlanner schPlanner;

  public ReadOnlyAdapter(SchedulePlanner schPlanner) {
    this.schPlanner = schPlanner;
  }

  @Override
  public User getUser() {
    IUser user = new UserImpl(schPlanner.scheduleID());
    return new UserAdapter(user);
  }

  @Override
  public List<Event> getEvents() {
    List<EventImpl> listEvents = schPlanner.events();
    List<Event> finalEvent = new ArrayList<>();

    for (int event = 0; event < listEvents.size(); event++) {
      finalEvent.add(event, new EventAdapter(listEvents.get(event)));
    }
    return finalEvent;
  }

  @Override
  public boolean hasEventAtTime(EventTime time) {
    // method is not used
    return false;
  }

  @Override
  public List<Event> getEventsAtTime(EventTime time) {
    // method is not used
    return null;
  }

  @Override
  public Event getEventAtTime(WeekTime time) {
    // method is not used
    return null;
  }
}
