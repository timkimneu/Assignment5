package provider.controller;

import provider.strategy.SchedulingStrategy;
import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.User;
import provider.model.WeekTime;
import provider.view.CentralSystemView;

/**
 * The features that a controller is responsible for being able to perform. These features
 * are requested by a model using some input data collected from the user, and the resulting
 * features modify the view and/or the model accordingly.
 *
 * <P>Some of the events require taking in an event builder. This is because the view uses
 * an event builder to build its events. If the information to build an event were invalid, its
 * the responsibility of the controller to decide what happens, so the builder.buildEvent()
 * method call should occur in the controller so that it can know what errors happen.
 */
public interface Features {

  /**
   * readXML takes in a filename containing XML defining a single user's schedule. The
   * controller should parse a schedule out of the XML file and attempt to add it the model
   * before updating the view accordingly. If there is an error reading the file or adding the
   * schedule to the model, display an error somehow in the view.
   * @param filename the full path of an XML filename to read
   * @throws IllegalArgumentException if any given parameter is null
   */
  void readXML(String filename);

  /**
   * WriteXML takes in a filepath representing a location to write files to. The controller
   * should take all the schedules in the model and write them to this specific location.
   * @param filename the full path of where to write schedule XML
   * @throws IllegalArgumentException if any given parameter is null
   */
  void writeXML(String filename);

  /**
   * Given event with predefined detail besides the time of the event (represented by a builder)
   * the controller should find a potential time for the event with the given duration. The
   * strategy of scheduling an event may vary. If there is no time for the event, or if the
   * builder was not passed with sufficient information, display an error message.
   * @param builder an event with predefined detail
   * @param duration the duration of the event to be scheduled in minutes.
   * @return true if the event is scheduled successfully.
   * @throws IllegalArgumentException if any given parameter is null
   */
  boolean scheduleEvent(EventBuilder builder, int duration);

  /**
   * Display the schedule that is currently selected. The behavior of this event can use
   * the observations provided by the view to determine what schedule should be observed.
   * The lack of any parameters is necessary because so that this feature can be called
   * independent of whether a view knows who is selected or not.
   */
  void displayCurrentSchedule();

  /**
   * Delegate to the view to display the event at the requested time for the currently selected
   * user if the event exists.
   * @param requestedTime a time to look for an event
   * @throws IllegalArgumentException if any given parameter is null
   */
  void displayEventAt(WeekTime requestedTime);

  /**
   * Given an event corresponding to an old event to modify, replace the old event with new event
   * defined by the builder and update the view. If there is an error during the process, display
   * it.
   * @param oldEvent An old event to be modified.
   * @param eventBuilder A builder with predefined detail to replace the event
   * @return true if the event was successfully modified.
   * @throws IllegalArgumentException if any given parameter is null
   */
  boolean modifyEvent(Event oldEvent, EventBuilder eventBuilder);

  /**
   * Attempt to remove the given event from the user's schedule, with behavior defined by the
   * model. If there is an error during the process, display it.
   * @param oldEvent an event to be removed
   * @param user the user whose schedule the event should be removed from
   * @return true if the event was removed successfully.
   * @throws IllegalArgumentException if any given parameter is null
   */
  boolean removeEvent(Event oldEvent, User user);

  /**
   * Add an event to the model and update the view accordingly. If there is an error during the
   * process, display it.
   * @param eventBuilder a builder with pre-defined detail whose built event should be added
   * @return true if the event was added successfully.
   * @throws IllegalArgumentException if any given parameter is null
   */
  boolean addEvent(EventBuilder eventBuilder);

  /**
   * Sets the controller's view to the given view.
   * @param view the view which should be controlled by the controller.
   * @throws IllegalArgumentException if the view given is null
   */
  void setView(CentralSystemView view);

  /**
   * Starts the view with the given strategy.
   * @param strategy a strategy which should be used for the schedule event method.
   * @throws IllegalArgumentException if the strategy is null
   * @throws IllegalStateException if a view has not ben set
   */
  void execute(SchedulingStrategy strategy);
}
