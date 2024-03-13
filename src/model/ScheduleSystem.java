package model;

/**
 * Represents the model of the Schedule system or collection of events over a calendar. Individual
 * events can be added, removed, or "modified". All events under a schedule are written to an
 * XML file which can also be read by the Schedule system.
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
   * @param filePath
   */
  void writeXML(String filePath);
}
