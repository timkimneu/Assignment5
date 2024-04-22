package controller;

import model.DaysOfTheWeek;
import model.EventImpl;
import model.IEvent;
import model.ISchedule;
import model.ITime;
import model.LocationImpl;
import model.IPlannerModel;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;
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
import java.util.Arrays;
import java.util.List;

/**
 * Represents the model of the Schedule system or collection of events over a calendar.
 * When provided with an appropriate file path, users can read an XML file to create a new schedule
 * and add the schedule into the system (list of schedules). When provided with a schedule, users
 * can also write/create a new XML file to write a new schedule into the system. System also can
 * provide the current list of schedules contained in the system.
 */
public class ScheduleSystemController<T> implements ScheduleSystem<T> {
  private IPlannerModel<T> model;
  private final ScheduleSystemView<T> view;

  /**
   * Represents the model of the Schedule system or collection of events over a calendar.
   * When provided with an appropriate file path, users can read an XML file to create a new
   * schedule and add the schedule into the system (list of schedules). When provided with a
   * schedule, users can also write/create a new XML file to write a new schedule into the system.
   * System also can provide the current list of schedules contained in the system.
   *
   * @param view View that the controller will listen to and communicate changes to the model to.
   */
  public ScheduleSystemController(ScheduleSystemView<T> view) {
    this.view = view;
  }

  @Override
  public void launch(IPlannerModel<T> model) {
    this.model = model;
    this.view.addListener(this);
    this.view.makeVisible();
  }

  // read XML file
  @Override
  public void readXML(String filePath) {
    List<String> startDays = new ArrayList<>();
    List<String> startTimes = new ArrayList<>();
    List<String> endDays = new ArrayList<>();
    List<String> endTimes = new ArrayList<>();
    List<LocationImpl> locs = new ArrayList<>();
    List<List<UserImpl>> userImpls = new ArrayList<>();
    List<String> eventNames = new ArrayList<>();

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
      startDays.add(startDay.getTextContent());
      Node start = doc.getElementsByTagName("start").item(eventIndx);
      startTimes.add(start.getTextContent());
      Node endDay = doc.getElementsByTagName("end-day").item(eventIndx);
      endDays.add(endDay.getTextContent());
      Node end = doc.getElementsByTagName("end").item(eventIndx);
      endTimes.add(end.getTextContent());
//      TimeImpl time = new TimeImpl(DaysOfTheWeek.valueOf(startDay.getTextContent().toUpperCase()),
//              start.getTextContent(), DaysOfTheWeek.valueOf(endDay.getTextContent().toUpperCase()),
//              end.getTextContent());

      Node online = doc.getElementsByTagName("online").item(eventIndx);
      Node place = doc.getElementsByTagName("place").item(eventIndx);
      LocationImpl loc = new LocationImpl(Boolean.parseBoolean(online.getTextContent()),
              place.getTextContent());
      locs.add(loc);

      List<UserImpl> listUsers = new ArrayList<>();
      Node users = doc.getElementsByTagName("users").item(eventIndx);
      NodeList indUsers = users.getChildNodes();
      for (int user = 0; user < indUsers.getLength(); user++) {
        Node currNode = indUsers.item(user);
        if (currNode.getNodeName().equals("uid")) {
          UserImpl currUser = new UserImpl(currNode.getTextContent());
          listUsers.add(currUser);
        }
      }
      userImpls.add(listUsers);
      eventNames.add(name.getTextContent());
//      EventImpl currEvent = new EventImpl(name.getTextContent(), time, loc, listUsers);
//      listOfEvents.add(currEvent);
    }
//    SchedulePlanner currSch = new SchedulePlanner(listOfEvents, id);
//    if (!this.model.schedules().contains(currSch)) {
//      this.model.schedules().add(currSch);
//    }
    this.model.addSchedule(startDays, endDays, startTimes, endTimes, locs, userImpls, eventNames, id);
    view.refresh();
  }

  // write to XML file
  @Override
  public void writeXML(String beginPath) {
    for (ISchedule<T> sch : this.model.schedules()) {
      try {
        Writer file = new FileWriter(beginPath + "src/" + sch.scheduleID() + ".xml");
        file.write("<?xml version=\"1.0\"?>\n");
        file.write("<schedule id=\"" + sch.scheduleID() + "\">\n");
        for (IEvent<T> e : sch.events()) {
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
  private void writeEvent(Writer file, IEvent e) {
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
  private void writeTime(Writer file, ITime<DaysOfTheWeek> t) {
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
  private void writeLocation(Writer file, LocationImpl loc) {
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
  private void writeUsers(Writer file, List<UserImpl> users) {
    try {
      file.write("\t\t<users>\n");
      for (UserImpl u : users) {
        file.write("\t\t\t<uid>\"" + u.name() + "\"</uid>\n");
      }
      file.write("\t\t</users>\n");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  @Override
  public List<ISchedule<T>> returnSchedule() {
    return new ArrayList<>(this.model.schedules());
  }

  @Override
  public void addEvent(IEvent<T> e) {
    this.model.addEvent(e);
    view.refresh();
  }

  @Override
  public void modifyEvent(IEvent<T> oldEvent, IEvent<T> newEvent, UserImpl user) {
    if (oldEvent.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    this.model.removeEvent(oldEvent, user);
    this.model.addEvent(newEvent);
    view.refresh();
  }

  @Override
  public void removeEvent(IEvent<T> e, UserImpl user) {
    this.model.removeEvent(e, user);
    view.refresh();
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    this.model.scheduleEvent(name, location, duration, users);
    view.refresh();
  }
}
