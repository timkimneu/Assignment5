package controller;

import model.Schedule;

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
   * @param sch Schedule to be written into a new/overwritten XML file
   */
  void writeXML(Schedule sch);

  /**
   * Provides a representation of the current list of Schedule objects contained in the system.
   *
   * @return List of schedules pertaining to this system.
   */
  List<Schedule> returnSchedule();
}
