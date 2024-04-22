package model;

import provider.model.Event;
import provider.model.EventTime;
import provider.model.ReadOnlySchedule;
import provider.model.User;
import provider.model.WeekTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a part of the full planner or schedule system that allows for interaction between
 * different individual's schedules and the adding, modifying, and removing events from schedules
 * within the system. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public class ReadOnlyAdapter implements ReadOnlySchedule {

  private final ISchedule<DaysOfTheWeek> schPlanner;

  /**
   * Represents a part of the full planner or schedule system that allows for interaction between
   * different individual's schedules and the adding, modifying, and removing events from schedules
   * within the system. Also retrieves the full list of schedules currently contained within the
   * planner system and can also observe the list of events for a specific user if the user exists,
   * otherwise throws an error.
   */
  public ReadOnlyAdapter(ISchedule<DaysOfTheWeek> schPlanner) {
    this.schPlanner = schPlanner;
  }

  @Override
  public User getUser() {
    IUser user = new UserImpl(schPlanner.scheduleID());
    return new UserAdapter(user);
  }

  @Override
  public List<Event> getEvents() {
    List<IEvent<DaysOfTheWeek>> listEvents = schPlanner.events();
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
