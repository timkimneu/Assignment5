package controller;

import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.NUPlannerModel;
import model.PlannerModel;
import model.SchedulePlanner;
import model.Time;
import model.User;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import view.ScheduleFrame;
import view.ScheduleSystemTextView;
import view.ScheduleSystemView;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Represents examples and tests of the ScheduleSystemController class and all of its relevant
 * supporting classes. Examples and tests of classes and methods within the controller package.
 */
public class ScheduleControllerTests {
  DaysOfTheWeek sunday;
  DaysOfTheWeek monday;
  DaysOfTheWeek tuesday;
  DaysOfTheWeek wednesday;
  DaysOfTheWeek thursday;
  DaysOfTheWeek friday;
  DaysOfTheWeek saturday;
  Time time1;
  Time time2;
  Time time3;
  Time time4;
  Time time5;
  Location loc1;
  Location loc2;
  Location loc3;
  Location loc4;
  Location loc5;
  User user1;
  User user2;
  User user3;
  User classmate;
  User friend;
  User bestFriend;
  List<User> users1;
  List<User> users2;
  List<User> users3;
  Event church;
  Event school;
  Event vacation;
  Event mondayAfternoonJog;
  Event wednesdayDinner;
  List<Event> mtEvents;
  List<Event> events1;
  List<Event> events2;
  SchedulePlanner sch1;
  SchedulePlanner sch2;
  SchedulePlanner sch3;
  SchedulePlanner sch4;
  ScheduleSystemController schSysMod;
  PlannerModel model1;

  private void initData() {
    this.sunday = DaysOfTheWeek.SUNDAY;
    this.monday = DaysOfTheWeek.MONDAY;
    this.tuesday = DaysOfTheWeek.TUESDAY;
    this.wednesday = DaysOfTheWeek.WEDNESDAY;
    this.thursday = DaysOfTheWeek.THURSDAY;
    this.friday = DaysOfTheWeek.FRIDAY;
    this.saturday = DaysOfTheWeek.SATURDAY;
    this.time1 = new Time(this.sunday, "1000", this.sunday, "1300");
    this.time2 = new Time(this.monday, "0800", this.friday, "1500");
    this.time3 = new Time(this.thursday, "1700", this.monday, "0900");
    this.time4 = new Time(this.monday, "1200", this.monday, "1245");
    this.time5 = new Time(this.wednesday, "1800", this.wednesday, "1830");
    this.loc1 = new Location(false, "Mulberry Street");
    this.loc2 = new Location(false, "Northeastern University");
    this.loc3 = new Location(false, "Cancun Resort");
    this.loc4 = new Location(false, "Outside");
    this.loc5 = new Location(false, "Home");
    this.user1 = new User("Me");
    this.user2 = new User("Mom");
    this.user3 = new User("Dad");
    this.classmate = new User("Classmate");
    this.friend = new User("Friend");
    this.bestFriend = new User("Best Friend");
    this.users1 = new ArrayList<>(Arrays.asList(this.user1, this.user2, this.user3));
    this.users2 = new ArrayList<>(Arrays.asList(this.user1, this.classmate, this.bestFriend));
    this.users3 = new ArrayList<>(Arrays.asList(this.user1, this.friend, this.bestFriend));
    this.church = new Event("Church", this.time1, this.loc1, this.users1);
    this.school = new Event("Classes", this.time2, this.loc2, this.users2);
    this.vacation = new Event("Cancun Trip", this.time3, this.loc3, this.users3);
    this.mondayAfternoonJog = new Event("Afternoon Jog", this.time4, this.loc4,
        new ArrayList<>(Collections.singletonList(this.user1)));
    this.wednesdayDinner = new Event("Wednesday Dinner", this.time5, this.loc5,
        new ArrayList<>(Collections.singletonList(this.user1)));
    this.mtEvents = new ArrayList<>();
    this.events1 = new ArrayList<>(Arrays.asList(this.church, this.school));
    this.events2 = new ArrayList<>(Arrays.asList(this.vacation, this.mondayAfternoonJog));
    this.sch1 = new SchedulePlanner(this.events1, "School Schedule");
    this.sch2 = new SchedulePlanner(this.events2, "Summer Schedule");
    this.sch3 = new SchedulePlanner(new ArrayList<>(Arrays.asList(this.church,
            this.mondayAfternoonJog)), "My Schedule");
    this.sch4 = new SchedulePlanner(new ArrayList<>(Collections.singletonList(
            this.wednesdayDinner)), "Dinner");
    this.model1 = new NUPlannerModel(Arrays.asList(this.sch1, this.sch2));
    ScheduleSystemView view = new ScheduleFrame(this.model1);
    this.schSysMod = new ScheduleSystemController(view);
    this.schSysMod.launch(model1);
  }

  @Test
  public void testExampleXML() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/tutorial.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    assert doc != null;
    NodeList nodeList = doc.getElementsByTagName("tutorial");
    Node first = nodeList.item(0);

    assertEquals(1, nodeList.getLength());
    assertEquals(Node.ELEMENT_NODE, first.getNodeType());
    assertEquals("tutorial", first.getNodeName());
  }

  @Test
  public void whenGetFirstElementAttributes_thenSuccess() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/tutorial.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    assert doc != null;
    Node first = doc.getElementsByTagName("tutorial").item(0);
    NamedNodeMap attrList = first.getAttributes();

    assertEquals(2, attrList.getLength());

    assertEquals("tutId", attrList.item(0).getNodeName());
    assertEquals("01", attrList.item(0).getNodeValue());

    assertEquals("type", attrList.item(1).getNodeName());
    assertEquals("java", attrList.item(1).getNodeValue());
  }

  @Test
  public void testEventsXml() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/prof.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    // get all the events
    assert doc != null;
    NodeList events = doc.getElementsByTagName("event");
    assertEquals(3, events.getLength());

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {
      Node name = doc.getElementsByTagName("name").item(eventIndx);
      if (eventIndx == 0) {
        assertEquals("CS3500 Morning Lecture", name.getTextContent().replaceAll("\"", ""));
      }
      if (eventIndx == 1) {
        assertEquals("CS3500 Afternoon Lecture", name.getTextContent().replaceAll("\"", ""));
      }
      if (eventIndx == 2) {
        assertEquals("Sleep", name.getTextContent().replaceAll("\"", ""));
      }
    }
  }

  @Test
  public void testTimeXml() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/prof.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    // get all the events
    assert doc != null;
    NodeList events = doc.getElementsByTagName("event");
    assertEquals(3, events.getLength());

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {

      Node time = doc.getElementsByTagName("time").item(eventIndx);

      Node startDay = doc.getElementsByTagName("start-day").item(eventIndx);
      Node start = doc.getElementsByTagName("start").item(eventIndx);
      Node endDay = doc.getElementsByTagName("end-day").item(eventIndx);
      Node end = doc.getElementsByTagName("end").item(eventIndx);

      if (eventIndx == 0) {
        assertEquals("Tuesday", startDay.getTextContent());
        assertEquals("0950", start.getTextContent());
        assertEquals("Tuesday", endDay.getTextContent());
        assertEquals("1130", end.getTextContent());
      }
      if (eventIndx == 1) {
        assertEquals("Tuesday", startDay.getTextContent());
        assertEquals("1335", start.getTextContent());
        assertEquals("Tuesday", endDay.getTextContent());
        assertEquals("1515", end.getTextContent());
      }
    }
  }

  @Test
  public void testPlaceXml() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/prof.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    // get all the events
    assert doc != null;
    NodeList events = doc.getElementsByTagName("event");
    assertEquals(3, events.getLength());

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {

      Node online = doc.getElementsByTagName("online").item(eventIndx);
      Node place = doc.getElementsByTagName("place").item(eventIndx);

      if (eventIndx == 0 | eventIndx == 1) {
        assertEquals("false", online.getTextContent());
        assertEquals("Churchill Hall 101", place.getTextContent().replaceAll("\"", ""));
      }
      if (eventIndx == 2) {
        assertEquals("true", online.getTextContent());
        assertEquals("Home", place.getTextContent());
      }
    }
  }

  @Test
  public void testUsersXml() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("../src/prof.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    // get all the events
    assert doc != null;
    NodeList events = doc.getElementsByTagName("event");
    assertEquals(3, events.getLength());

    Node first = doc.getElementsByTagName("schedule").item(0);
    NamedNodeMap attrList = first.getAttributes();
    if (attrList.item(0).getNodeName().equals("id")) {
      System.out.println(attrList.item(0).getNodeValue());
    }

    for (int eventIndx = 0; eventIndx < events.getLength(); eventIndx++) {
      Node name = doc.getElementsByTagName("name").item(eventIndx);

      Node users = doc.getElementsByTagName("users").item(eventIndx);
      NodeList indUsers = users.getChildNodes();

      for (int user = 0; user < indUsers.getLength(); user++) {
        Node currNode = indUsers.item(user);
        if (currNode.getNodeName().equals("uid")) {
          if (user == 0) {
            assertEquals("\"Prof. Lucia\"", currNode.getTextContent());
          }
          if (user == 1) {
            assertEquals("\"Prof. Lucia\"", currNode.getTextContent());
          }
          if (user == 2) {
            assertEquals("\"Student Anon\"", currNode.getTextContent());
          }
        }
      }
    }
  }

  @Test
  public void testScheduleMode() {
    List<SchedulePlanner> emptyList = new ArrayList<>();
    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
    ScheduleSystemView view = new ScheduleFrame(mtModel);
    ScheduleSystem schModel = new ScheduleSystemController(view);
    schModel.launch(mtModel);
    schModel.readXML("../src/prof.xml");
    List<SchedulePlanner> listSchedules = schModel.returnSchedule();
    ScheduleSystemTextView schView = new ScheduleSystemTextView(listSchedules);

    assertEquals("User: Prof. Lucia\n" +
        "Sunday:\n" +
        "Monday:\n" +
        "Tuesday: \n" +
        "\tname: \"CS3500 Morning Lecture\"\n" +
        "\ttime: Tuesday: 0950 -> Tuesday: 1130\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tStudent Anon\n" +
        "\tChat\n" +
        "\tname: \"CS3500 Afternoon Lecture\"\n" +
        "\ttime: Tuesday: 1335 -> Tuesday: 1515\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tChat\n" +
        "Wednesday:\n" +
        "Thursday:\n" +
        "Friday: \n" +
        "\tname: Sleep\n" +
        "\ttime: Friday: 1800 -> Sunday: 1200\n" +
        "\tlocation: Home\n" +
        "\tonline: true\n" +
        "\tinvitees: Prof. Lucia\n" +
        "Saturday:\n", schView.schedulesToString());
  }

  @Test
  public void testScheduleMultipleUsers() {
    List<SchedulePlanner> emptyList = new ArrayList<>();
    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
    ScheduleSystemView view = new ScheduleFrame(mtModel);
    ScheduleSystem schModel = new ScheduleSystemController(view);
    schModel.launch(mtModel);
    schModel.readXML("../src/prof.xml");
    schModel.readXML("../src/prof.xml");
    List<SchedulePlanner> listSchedules = schModel.returnSchedule();
    ScheduleSystemTextView schView = new ScheduleSystemTextView(listSchedules);

    assertEquals("User: Prof. Lucia\n" +
        "Sunday:\n" +
        "Monday:\n" +
        "Tuesday: \n" +
        "\tname: \"CS3500 Morning Lecture\"\n" +
        "\ttime: Tuesday: 0950 -> Tuesday: 1130\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tStudent Anon\n" +
        "\tChat\n" +
        "\tname: \"CS3500 Afternoon Lecture\"\n" +
        "\ttime: Tuesday: 1335 -> Tuesday: 1515\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tChat\n" +
        "Wednesday:\n" +
        "Thursday:\n" +
        "Friday: \n" +
        "\tname: Sleep\n" +
        "\ttime: Friday: 1800 -> Sunday: 1200\n" +
        "\tlocation: Home\n" +
        "\tonline: true\n" +
        "\tinvitees: Prof. Lucia\n" +
        "Saturday:\n" +
        "User: Prof. Lucia\n" +
        "Sunday:\n" +
        "Monday:\n" +
        "Tuesday: \n" +
        "\tname: \"CS3500 Morning Lecture\"\n" +
        "\ttime: Tuesday: 0950 -> Tuesday: 1130\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tStudent Anon\n" +
        "\tChat\n" +
        "\tname: \"CS3500 Afternoon Lecture\"\n" +
        "\ttime: Tuesday: 1335 -> Tuesday: 1515\n" +
        "\tlocation: \"Churchill Hall 101\"\n" +
        "\tonline: false\n" +
        "\tinvitees: Prof. Lucia\n" +
        "\tChat\n" +
        "Wednesday:\n" +
        "Thursday:\n" +
        "Friday: \n" +
        "\tname: Sleep\n" +
        "\ttime: Friday: 1800 -> Sunday: 1200\n" +
        "\tlocation: Home\n" +
        "\tonline: true\n" +
        "\tinvitees: Prof. Lucia\n" +
        "Saturday:\n", schView.schedulesToString());
  }

  // test writeXML method in ScheduleSystemModel class for IllegalArgumentException
  // when the current list of schedules does not contain the given Schedule
  @Test
  public void testWriteXMLScheduleDoesNotExistError() {
    this.initData();
    try {
      this.schSysMod.writeXML("../");
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule system does not contain given schedule!", e.getMessage());
    }

    try {
      this.schSysMod.writeXML("../");
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule system does not contain given schedule!", e.getMessage());
    }
  }

  // test writeXML method in ScheduleSystemModel class
  @Test
  public void testWriteXML() {
    this.initData();
    this.schSysMod.writeXML("../");
    List<SchedulePlanner> listSchedules = this.schSysMod.returnSchedule();
    ScheduleSystemTextView schView = new ScheduleSystemTextView(listSchedules);

    assertEquals("User: School Schedule\n" +
        "Sunday: \n" +
        "\tname: Church\n" +
        "\ttime: Sunday: 1000 -> Sunday: 1300\n" +
        "\tlocation: Mulberry Street\n" +
        "\tonline: false\n" +
        "\tinvitees: Me\n" +
        "\tMom\n" +
        "\tDad\n" +
        "Monday: \n" +
        "\tname: Classes\n" +
        "\ttime: Monday: 0800 -> Friday: 1500\n" +
        "\tlocation: Northeastern University\n" +
        "\tonline: false\n" +
        "\tinvitees: Me\n" +
        "\tClassmate\n" +
        "\tBest Friend\n" +
        "Tuesday:\n" +
        "Wednesday:\n" +
        "Thursday:\n" +
        "Friday:\n" +
        "Saturday:\n" +
        "User: Summer Schedule\n" +
        "Sunday:\n" +
        "Monday: \n" +
        "\tname: Afternoon Jog\n" +
        "\ttime: Monday: 1200 -> Monday: 1245\n" +
        "\tlocation: Outside\n" +
        "\tonline: false\n" +
        "\tinvitees: Me\n" +
        "Tuesday:\n" +
        "Wednesday:\n" +
        "Thursday: \n" +
        "\tname: Cancun Trip\n" +
        "\ttime: Thursday: 1700 -> Monday: 0900\n" +
        "\tlocation: Cancun Resort\n" +
        "\tonline: false\n" +
        "\tinvitees: Me\n" +
        "\tFriend\n" +
        "\tBest Friend\n" +
        "Friday:\n" +
        "Saturday:\n", schView.schedulesToString());
  }
}
