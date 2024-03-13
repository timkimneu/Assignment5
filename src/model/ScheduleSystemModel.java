package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

/**
 *
 */
public class ScheduleSystemModel implements ScheduleSystem {
  final private List<Schedule> schedules;

  /**
   *
   *
   * @param schedules
   */
  public ScheduleSystemModel(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  // read XML file
  @Override
  public void readXML(String filePath) {

    List<Event> listOfEvents = null;

    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File(filePath));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    NodeList events = doc.getElementsByTagName("event");
    NodeList schedule = doc.getElementsByTagName("schedule");

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {
      Node name = doc.getElementsByTagName("name").item(eventIndx);

      Node startDay = doc.getElementsByTagName("start-day").item(eventIndx);
      Node start = doc.getElementsByTagName("start").item(eventIndx);
      Node endDay = doc.getElementsByTagName("end-day").item(eventIndx);
      Node end = doc.getElementsByTagName("end").item(eventIndx);
      Time time = new Time(DaysOfTheWeek.valueOf(startDay.getTextContent()), start.getTextContent(),
          DaysOfTheWeek.valueOf(endDay.getTextContent()), end.getTextContent());

      Node online = doc.getElementsByTagName("online").item(eventIndx);
      Node place = doc.getElementsByTagName("place").item(eventIndx);
      Location loc = new Location(Boolean.parseBoolean(online.getTextContent()), place.getTextContent());


      // MAKE AN INSTANCE OF EVENT CLASS
      // ADD THAT TO LIST

      // ADD TO THE LIST OF SCHEDULES IN THE FIELD
    }

  }

  // write to XML file
  @Override
  public void writeXML(String filePath) {

  }
}
