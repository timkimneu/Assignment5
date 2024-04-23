package controller;

import model.IEvent;
import model.ISchedule;
import model.LocationImpl;
import model.IPlannerModel;
import model.UserImpl;

import java.util.List;

/**
 * Represents the model of the Schedule system or collection of events over a calendar.
 * When provided with an appropriate file path, users can read an XML file to create a new schedule
 * and add the schedule into the system (list of schedules). When provided with a schedule, users
 * can also write/create a new XML file to write a new schedule into the system. System also can
 * provide the current list of schedules contained in the system.
 */
public interface ScheduleSystem<T> {

  /**
   * Reads the XML file designated by the file path provided by the argument and adds
   * the schedule to the current list of Schedule objects to the system.
   *
   * @param filePath path designating location of XML file with Schedule info
   */
  void readXML(String filePath);

  /**
   * Writes a new XML file outside the src file with the given Schedule object.
   * Details events in the schedule, including the name, time, location, and list of users
   * attending the event.
   *
   * @param beginPath Should always be "" (empty string), is otherwise only used to access
   *                  parent folder in testing file.
   */
  void writeXML(String beginPath);

  /**
   * Provides a representation of the current list of Schedule objects contained in the system.
   *
   * @return List of schedules pertaining to this system.
   */
  List<ISchedule<T>> returnSchedule();

  /**
   * Initializes the model in the controller and makes the view visible.
   *
   * @param model Model to be initialized in the controller and displayed in the view.
   */
  void launch(IPlannerModel<T> model);

  /**
   * Listener for when a user attempts to create an event in the view.
   *
   * @param e Event to be added and passed to be handled in the model.
   */
  void addEvent(IEvent<T> e);

  /**
   * Listener for when a user attempts to modify an event in the view.
   *
   * @param oldEvent Event to be replaced by the new event which is passed into the model.
   * @param newEvent Event to replace older event which is passed into the model.
   * @param user     User that will be modifying the event for which is passed into the model.
   */
  void modifyEvent(IEvent<T> oldEvent, IEvent<T> newEvent, UserImpl user);

  /**
   * Listener for when a user attempts to remove an event in the view.
   *
   * @param e    Event to be removed which is passed into the model.
   * @param user User that designates the schedule that the event is being removed from.
   */
  void removeEvent(IEvent<T> e, UserImpl user);

  /**
   * Listener for when a user attempts to automatically schedule an event in the view.
   *
   * @param name     Name to be passed into the model.
   * @param location Location to be passed into the model.
   * @param duration Integer duration to be passed into the model.
   * @param users    List of users to be passed into the model.
   */
  void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users);
}
