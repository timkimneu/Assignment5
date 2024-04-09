package controller;

import model.Event;
import model.Location;
import model.PlannerModel;
import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.User;

import java.util.List;

/**
 * Represents the model of the Schedule system or collection of events over a calendar.
 * When provided with an appropriate file path, users can read an XML file to create a new schedule
 * and add the schedule into the system (list of schedules). When provided with a schedule, users
 * can also write/create a new XML file to write a new schedule into the system. System also can
 * provide the current list of schedules contained in the system.
 */
public interface ScheduleSystem {

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
  List<SchedulePlanner> returnSchedule();

  /**
   *
   * @param model
   */
  void launch(PlannerModel model);

  /**
   *
   * @param e
   */
  void addEvent(Event e);

  /**
   *
   * @param oldEvent
   * @param newEvent
   * @param user
   */
  void modifyEvent(Event oldEvent, Event newEvent, User user);

  /**
   *
   * @param e
   * @param user
   */
  void removeEvent(Event e, User user);

  /**
   *
   * @param name
   * @param location
   * @param duration
   * @param users
   */
  void scheduleEvent(String name, Location location, int duration, List<User> users);

  /**
   *
   * @param row
   * @param col
   */
  void handleCellClick(int row, int col);
}
