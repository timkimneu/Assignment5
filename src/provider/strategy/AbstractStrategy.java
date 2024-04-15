package provider.strategy;

import java.util.ArrayList;
import java.util.List;

import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.EventTime;
import provider.model.ReadOnlySchedule;
import provider.model.WeekDay;
import provider.model.WeekTime;
//import provider.model.impl.LocalEventTime;
//import provider.model.impl.LocalWeekTime;
//import provider.model.impl.WeekDay;

/**
 * An abstract class that contains helpers useful for all event scheduling
 * strategies that require all users to attend.
 */
public abstract class AbstractStrategy implements SchedulingStrategy {
  @Override
  public abstract Event scheduleEvent(
          List<ReadOnlySchedule> schedules, int duration, EventBuilder builder);

  /**
   * Returns a list that contains the events of the two lists sorted by start time. Mutates
   * the original lists. The merge of merge sort.
   *
   * @param sorted1 a list of events sorted by start time
   * @param sorted2 a list of events sorted by start time
   * @return a list containing the events of the two lists still in order.
   */
  protected List<Event> mergeLists(List<Event> sorted1, List<Event> sorted2) {
    List<Event> result = new ArrayList<>();

    while (!sorted1.isEmpty() && !sorted2.isEmpty()) {
      // Insert the earliest event
      if (sorted1.get(0).getEventTime().getStartTime()
              .isBefore(sorted2.get(0).getEventTime().getStartTime())) {

        result.add(sorted1.get(0));
        sorted1.remove(0);
      } else {
        result.add(sorted2.get(0));
        sorted2.remove(0);
      }
    }

    // Add remaining elements
    if (!sorted1.isEmpty()) {
      result.addAll(sorted1);
    } else if (!sorted2.isEmpty()) {
      result.addAll(sorted2);
    }

    return result;
  }

  /**
   * Determines the duration between two WeekTimes in minutes. Returns a positive difference
   * when the startTime is earlier than the end time.
   *
   * @param startTime the chronologically earliest time.
   * @param endTime   the chronologically latest time.
   * @return the duration of the time in minutes.
   */
  protected int lengthBetween(WeekTime startTime, WeekTime endTime) {
    int dayDiff = endTime.getWeekDay().getDayOrder() - startTime.getWeekDay().getDayOrder();
    int hourDiff = endTime.getHour() - startTime.getHour();
    int minuteDiff = endTime.getMinute() - startTime.getMinute();

    return 24 * 60 * dayDiff + 60 * hourDiff + minuteDiff;
  }

  /**
   * Returns an EventTime with the given startTime that lasts the given duration.
   *
   * @param startTime the start time
   * @param duration  the duration the event should last
   * @return the event with the given duration
   */
  protected EventTime getEventTime(WeekTime startTime, int duration) {
    // represents the time from Sunday at midnight in minutes. Wrap with modulus operator
    // so that no time can be more than a week from sunday at midnight.
    int totalTime =
            (duration + (startTime.getWeekDay().getDayOrder() - 1) * 24 * 60 +
                    startTime.getHour() * 60 + startTime.getMinute()) % (24 * 60 * 7);

    // extract the day, hour, and minute from the total time using integer division and modulus
    int days = totalTime / (24 * 60);
    int hour = totalTime % (24 * 60) / 60;
    int minute = totalTime % (24 * 60) % 60;

    // Convert the integer value of day (which will be from 0 to 6) into a WeekDay
    WeekDay day = WeekDay.values()[days];

    WeekTime endTime = new LocalWeekTime(day, hour, minute);

    return new LocalEventTime(startTime, endTime);
  }

  /**
   * Given two events, returns whichever of them has the latest end time. This accounts
   * for events that span into the next week.
   *
   * @param event1 an event to check
   * @param event2 another event to check
   * @return whichever event has the latest end time
   */
  protected Event chooseLimitingEvent(Event event1, Event event2) {
    WeekTime t1 = event1.getEventTime().getEndTime();
    WeekTime t2 = event2.getEventTime().getEndTime();

    // if an event spans into the next week, return either.
    if (spansNextWeek(event1.getEventTime())) {
      return event1;
    } else if (spansNextWeek(event2.getEventTime())) {
      return event2;
    }

    // if The end time of event 2 is after, return event 2
    if (t2.isAfter(t1)) {
      return event2;
    }

    // otherwise return event 1
    return event1;
  }

  /**
   * Returns a list of EventTimes representing every stretch of times that does not have an
   * event scheduled for every user, sorted chronologically by start time.
   *
   * @param events a list of events that are chronologically sorted by start time
   * @return any period of time at which there is not an event scheduled.
   */
  protected List<EventTime> freeBlocks(List<Event> events) {
    List<EventTime> freeTime = new ArrayList<>();
    WeekTime start = new LocalWeekTime(WeekDay.SUNDAY, 0, 0);
    WeekTime end = new LocalWeekTime(WeekDay.SATURDAY, 23, 59);

    // If there are no events, the whole week is free
    if (events.isEmpty()) {
      freeTime.add(new LocalEventTime(start, end));
      return freeTime;
    }

    // If the first event doesn't start at the beginning of the week, there is free time until
    // the start of the first event.
    if (!events.get(0).getEventTime().getStartTime().isSame(start)) {
      freeTime.add(new LocalEventTime(start, events.get(0).getEventTime().getStartTime()));
    }
    // Look through every event for the week and keep track of the event with the latest end time
    // so far.
    Event limitingEvent = events.get(0);
    for (int idx = 0; idx < events.size(); idx++) {

      Event currentEvent = events.get(idx);
      if (limitingEvent.getEventTime().overlapsWith(currentEvent.getEventTime()) ||
              limitingEvent.getEventTime().getEndTime()
                      .isSame(currentEvent.getEventTime().getStartTime())) {
        // In this case, the current event overlaps with the latest end time event
        // Whichever event has the later end time should become the limiting event
        limitingEvent = chooseLimitingEvent(limitingEvent, currentEvent);
      } else {
        // There is a gap between the event with the latest end time and this event. Add a free
        // block corresponding to the end of the previous event and the start of this event
        freeTime.add(new LocalEventTime(limitingEvent.getEventTime().getEndTime(),
                currentEvent.getEventTime().getStartTime()));
        limitingEvent = currentEvent;
      }
    }
    // There is free time till the end of the week if the limiting event doesn't end at that time.
    if (!limitingEvent.getEventTime().getEndTime().isSame(end)
            && !spansNextWeek(limitingEvent.getEventTime())) {
      freeTime.add(
              new LocalEventTime(limitingEvent.getEventTime().getEndTime(), end));
    }
    return freeTime;
  }

  /**
   * Determines whether an event spans into next week.
   */
  protected boolean spansNextWeek(EventTime event) {
    return event.getStartTime().isAfter(event.getEndTime());
  }
}

