package controller;

import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.PlannerModel;
import model.SchedulePlanner;
import model.Time;
import model.User;
import view.ScheduleSystemView;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model of the Schedule system or collection of events over a calendar.
 * When provided with an appropriate file path, users can read an XML file to create a new schedule
 * and add the schedule into the system (list of schedules). When provided with a schedule, users
 * can also write/create a new XML file to write a new schedule into the system. System also can
 * provide the current list of schedules contained in the system.
 */
public class ScheduleSystemController implements ScheduleSystem {
  private PlannerModel model;
  private final ScheduleSystemView view;

  /**
   * Represents the model of the Schedule system or collection of events over a calendar.
   * When provided with an appropriate file path, users can read an XML file to create a new
   * schedule and add the schedule into the system (list of schedules). When provided with a
   * schedule, users can also write/create a new XML file to write a new schedule into the system.
   * System also can provide the current list of schedules contained in the system.
   *
   * @param view
   */
  public ScheduleSystemController(ScheduleSystemView view) {
    this.view = view;
  }

  @Override
  public void launch(PlannerModel model) {
    this.model = model;
    this.view.addListener(this);
    this.view.makeVisible();
  }

  // read XML file
  @Override
  public void readXML(String filePath)  {
    List<Event> listOfEvents = new ArrayList<>();
    String id = null;
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File(filePath));
      doc.getDocumentElement().normalize();
    } catch (IOException | SAXException | ParserConfigurationException ignored) {
      System.out.println(ignored.getMessage());
    }
    NodeList events = doc.getElementsByTagName("event");
    Node first = doc.getElementsByTagName("schedule").item(0);
    NamedNodeMap attrList = first.getAttributes();
    if (attrList.item(0).getNodeName().equals("id")) {
      id = attrList.item(0).getNodeValue();
    }

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {
      Node name = doc.getElementsByTagName("name").item(eventIndx);

      Node startDay = doc.getElementsByTagName("start-day").item(eventIndx);
      Node start = doc.getElementsByTagName("start").item(eventIndx);
      Node endDay = doc.getElementsByTagName("end-day").item(eventIndx);
      Node end = doc.getElementsByTagName("end").item(eventIndx);
      Time time = new Time(DaysOfTheWeek.valueOf(startDay.getTextContent().toUpperCase()),
              start.getTextContent(), DaysOfTheWeek.valueOf(endDay.getTextContent().toUpperCase()),
              end.getTextContent());

      Node online = doc.getElementsByTagName("online").item(eventIndx);
      Node place = doc.getElementsByTagName("place").item(eventIndx);
      Location loc = new Location(Boolean.parseBoolean(online.getTextContent()),
              place.getTextContent());

      List<User> listUsers = new ArrayList<>();
      Node users = doc.getElementsByTagName("users").item(eventIndx);
      NodeList indUsers = users.getChildNodes();
      for (int user = 0; user < indUsers.getLength(); user++) {
        Node currNode = indUsers.item(user);
        if (currNode.getNodeName().equals("uid")) {
          User currUser = new User(currNode.getTextContent());
          listUsers.add(currUser);
        }
      }
      Event currEvent = new Event(name.getTextContent(), time, loc, listUsers);
      listOfEvents.add(currEvent);
    }
    SchedulePlanner currSch = new SchedulePlanner(listOfEvents, id);
    System.out.println(currSch.events());
    if (!this.model.schedules().contains(currSch)) {
      this.model.schedules().add(currSch);
//      for (int i = 0; i < model.schedules().size(); i++) {
//        System.out.println(model.schedules().get(i).events().get(i).name());
//      }
    }
    view.refresh();
  }

  // write to XML file
  @Override
  public void writeXML(String beginPath) {
    for (SchedulePlanner sch : this.model.schedules()) {
      try {
        Writer file = new FileWriter(beginPath + "src/" + sch.scheduleID() + ".xml");
        file.write("<?xml version=\"1.0\"?>\n");
        file.write("<schedule id=\"" + sch.scheduleID() + "\">\n");
        for (Event e : sch.events()) {
          this.writeEvent(file, e);
        }
        file.write("</schedule>");
        file.close();
      } catch (IOException ex) {
        throw new RuntimeException(ex.getMessage());
      }
    }
  }

  // writes an event to the XML file
  private void writeEvent(Writer file, Event e) {
    try {
      file.write("\t<event>\n");
      file.write("\t\t<name>\"" + e.name() + "\"</name>\n");
      this.writeTime(file, e.time());
      this.writeLocation(file, e.location());
      this.writeUsers(file, e.users());
      file.write("\t</event>\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  // writes the time to the XML file
  private void writeTime(Writer file, Time t) {
    try {
      file.write("\t\t<time>\n");
      file.write("\t\t\t<start-day>" + t.startDay().observeDay() + "</start-day>\n");
      file.write("\t\t\t<start>" + t.startTime() + "</start>\n");
      file.write("\t\t\t<end-day>" + t.endDay().observeDay() + "</end-day>\n");
      file.write("\t\t\t<end>" + t.endTime() + "</end>\n");
      file.write("\t\t</time>\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  // writes the location to the XML file
  private void writeLocation(Writer file, Location loc) {
    String locOnlineStr;
    if (loc.online()) {
      locOnlineStr = "true";
    } else {
      locOnlineStr = "false";
    }
    try {
      file.write("\t\t<time>\n");
      file.write("\t\t\t<online>" + locOnlineStr + "</online>\n");
      file.write("\t\t\t<place>" + loc.place() + "</place>\n");
      file.write("\t\t</time>\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  // writes the users to the XML file
  private void writeUsers(Writer file, List<User> users) {
    try {
      file.write("\t\t<users>\n");
      for (User u : users) {
        file.write("\t\t\t<uid>\"" + u.name() + "\"</uid>\n");
      }
      file.write("\t\t</users>\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public List<SchedulePlanner> returnSchedule() {
    return new ArrayList<>(this.model.schedules());
  }

  @Override
  public void addEvent(Event e) {
    this.model.addEvent(e);
    view.refresh();
  }

  @Override
  public void modifyEvent(Event oldEvent, Event newEvent, User user) {
    if (oldEvent.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    this.model.removeEvent(oldEvent, user);
    this.model.addEvent(newEvent);
//    this.model.modifyEvent(oldEvent, newEvent, user);
    view.refresh();
  }

  @Override
  public void removeEvent(Event e, User user) {
    this.model.removeEvent(e, user);
    view.refresh();
  }

  @Override
  public void scheduleEvent(String name, Location location, int duration, List<User> users) {
    this.model.scheduleEvent(name, location, duration, users);
    view.refresh();
  }

  @Override
  public void handleCellClick(int row, int col) {

  }

}
