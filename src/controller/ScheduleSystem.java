package controller;

import model.Schedule;

import java.util.List;

/**
 * Represents the model of the Schedule system or collection of events over a calendar. When provided with an
 * appropriate file path, users can read an XML file to create a new schedule and add the schedule into the
 * system (list of schedules). When provided with a schedule, users can also write/create a new XML file to
 * write a new schedule into the system. System also can provide the current list of schedules contained in the system.
 */
public interface ScheduleSystem {

  /**
   *
   *
   * @param filePath
   */
  void readXML(String filePath);

  /**
   *
   * @param sch
   */
  void writeXML(Schedule sch);

  /**
   *
   * @return
   */
  List<Schedule> returnSchedule();
}
