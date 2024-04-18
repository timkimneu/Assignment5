package controller;

import model.DaysOfTheWeek;
import model.EventImpl;
import model.LocationImpl;
import model.NUPlannerModel;
import model.IPlannerModel;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;
import model.WorkTimePlannerModel;

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
  TimeImpl time1;
  TimeImpl time2;
  TimeImpl time3;
  TimeImpl time4;
  TimeImpl time5;
  LocationImpl loc1;
  LocationImpl loc2;
  LocationImpl loc3;
  LocationImpl loc4;
  LocationImpl loc5;
  UserImpl user1;
  UserImpl user2;
  UserImpl user3;
  UserImpl classmate;
  UserImpl friend;
  UserImpl bestFriend;
  List<UserImpl> users1;
  List<UserImpl> users2;
  List<UserImpl> users3;
  EventImpl church;
  EventImpl school;
  EventImpl vacation;
  EventImpl mondayAfternoonJog;
  EventImpl wednesdayDinner;
  List<EventImpl> mtEvents;
  List<EventImpl> events1;
  List<EventImpl> events2;
  SchedulePlanner sch1;
  SchedulePlanner sch2;
  SchedulePlanner sch3;
  SchedulePlanner sch4;
  ScheduleSystemController schSysMod;
  ScheduleSystemController ssc;
  IPlannerModel model1;
  Appendable strOut;

  private void initData() {
    this.sunday = DaysOfTheWeek.SUNDAY;
    this.monday = DaysOfTheWeek.MONDAY;
    this.tuesday = DaysOfTheWeek.TUESDAY;
    this.wednesday = DaysOfTheWeek.WEDNESDAY;
    this.thursday = DaysOfTheWeek.THURSDAY;
    this.friday = DaysOfTheWeek.FRIDAY;
    this.saturday = DaysOfTheWeek.SATURDAY;
    this.time1 = new TimeImpl(this.sunday, "1000", this.sunday, "1300");
    this.time2 = new TimeImpl(this.monday, "0800", this.friday, "1500");
    this.time3 = new TimeImpl(this.thursday, "1700", this.monday, "0900");
    this.time4 = new TimeImpl(this.monday, "1200", this.monday, "1245");
    this.time5 = new TimeImpl(this.wednesday, "1800", this.wednesday, "1830");
    this.loc1 = new LocationImpl(false, "Mulberry Street");
    this.loc2 = new LocationImpl(false, "Northeastern University");
    this.loc3 = new LocationImpl(false, "Cancun Resort");
    this.loc4 = new LocationImpl(false, "Outside");
    this.loc5 = new LocationImpl(false, "Home");
    this.user1 = new UserImpl("Me");
    this.user2 = new UserImpl("Mom");
    this.user3 = new UserImpl("Dad");
    this.classmate = new UserImpl("Classmate");
    this.friend = new UserImpl("Friend");
    this.bestFriend = new UserImpl("Best Friend");
    this.users1 = new ArrayList<>(Arrays.asList(this.user1, this.user2, this.user3));
    this.users2 = new ArrayList<>(Arrays.asList(this.user1, this.classmate, this.bestFriend));
    this.users3 = new ArrayList<>(Arrays.asList(this.user1, this.friend, this.bestFriend));
    this.church = new EventImpl("Church", this.time1, this.loc1, this.users1);
    this.school = new EventImpl("Classes", this.time2, this.loc2, this.users2);
    this.vacation = new EventImpl("Cancun Trip", this.time3, this.loc3, this.users3);
    this.mondayAfternoonJog = new EventImpl("Afternoon Jog", this.time4, this.loc4,
            new ArrayList<>(Collections.singletonList(this.user1)));
    this.wednesdayDinner = new EventImpl("Wednesday Dinner", this.time5, this.loc5,
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
      doc = builder.parse(new File("src/tutorial.xml"));
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
      doc = builder.parse(new File("src/tutorial.xml"));
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
      doc = builder.parse(new File("src/prof.xml"));
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
      doc = builder.parse(new File("src/prof.xml"));
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
      doc = builder.parse(new File("src/prof.xml"));
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
      doc = builder.parse(new File("src/prof.xml"));
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
    schModel.readXML("src/prof.xml");
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
    schModel.readXML("src/prof.xml");
    schModel.readXML("src/prof.xml");
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

  // test writeXML method in ScheduleSystemModel class
  @Test
  public void testWriteXML() {
    this.initData();
    this.schSysMod.writeXML("");
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

  @Test
  public void testControllerAddEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(
            new SchedulePlanner(this.mtEvents, "Me"))));
    ScheduleSystemView ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController schSysCon = new ScheduleSystemController(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog);
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
  }

  @Test
  public void testControllerRemoveEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController schSysCon = new ScheduleSystemController(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog);
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
    schSysCon.removeEvent(this.mondayAfternoonJog, this.user1);
    Assert.assertFalse(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
  }

  @Test
  public void testControllerModifyEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController schSysCon = new ScheduleSystemController(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog);
    schSysCon.modifyEvent(this.mondayAfternoonJog, this.church, this.user1);
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.church));
  }

  @Test
  public void testControllerScheduleEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController schSysCon = new ScheduleSystemController(ssView);
    schSysCon.launch(mtModel);
    Assert.assertEquals(0, mtModel.schedules().get(0).events().size());
    schSysCon.scheduleEvent("Neat event", this.loc1, 120, this.users1);
    Assert.assertEquals(1, mtModel.schedules().get(0).events().size());
  }

  @Test
  public void testControllerReturnSchedules() {
    this.initData();
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.sch1, this.sch2)),
            schSysMod.returnSchedule());
  }

  private void initAppendable() {
    this.strOut = new StringBuilder();
    IPlannerModel mkModel = new PlannerMock(strOut);
    ScheduleSystemView view = new ScheduleFrame(mkModel);
    ssc = new ScheduleSystemController(view);
    ssc.launch(mkModel);
  }

  @Test
  public void testMockAddEvent() {
    this.initData();
    this.initAppendable();
    ssc.addEvent(this.church);
    Assert.assertEquals("name = Church, startDay = SUNDAY, endDay = SUNDAY, " +
            "startTime = 1000, endTime = 1300, online = false, place = Mulberry Street",
            strOut.toString());
  }

  @Test
  public void testMockRemoveEvent() {
    this.initData();
    this.initAppendable();
    ssc.removeEvent(this.church, this.user1);
    Assert.assertEquals("name = Church, startDay = SUNDAY, endDay = SUNDAY, " +
            "startTime = 1000, endTime = 1300, online = false, place = Mulberry Street, user = Me",
            strOut.toString());
  }

  @Test
  public void testMockScheduleEvent() {
    this.initData();
    this.initAppendable();
    ssc.scheduleEvent("Evt Name", new LocationImpl(true, "home"), 1234, this.users1);
    Assert.assertEquals("name = Evt Name, online = true, place = home, duration = 1234",
            strOut.toString());
  }

  @Test
  public void testMockModifyEvent() {
    // test will call remove and add and combine string logs
    this.initData();
    this.initAppendable();
    ssc.modifyEvent(this.church, this.school, this.user2);
    Assert.assertEquals("name = Church, startDay = SUNDAY, endDay = SUNDAY, " +
            "startTime = 1000, endTime = 1300, online = false, place = Mulberry Street, " +
            "user = Momname = Classes, startDay = MONDAY, endDay = FRIDAY, startTime = 0800, " +
            "endTime = 1500, online = false, place = Northeastern University", strOut.toString());
  }

  @Test
  public void testAnytimeStrategyAddingOneEventToEmptySchedule() {
    this.initData();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    EventImpl sixFlags = new EventImpl("Six Flags", new TimeImpl(this.sunday, "0800", this.sunday,
            "1200"), new LocationImpl(false, "California"), listUsers);

    SchedulePlanner timSch = new SchedulePlanner(new ArrayList<>(Arrays.asList(sixFlags)), "Tim");
    IPlannerModel anytimeModel = new NUPlannerModel(new ArrayList<>(Arrays.asList(timSch)));
    String eventName = "Hi";
    LocationImpl loc = new LocationImpl(true, "school");
    int duration = 1500;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);

    SchedulePlanner curr = anytimeModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(DaysOfTheWeek.SUNDAY, curr.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(1).time().endDay());
    assertEquals("1200", curr.events().get(1).time().startTime());
    assertEquals("1300", curr.events().get(1).time().endTime());
  }

  @Test
  public void testAnytimeStrategyAddingOneEventToMoreEventsSchedule() {
    this.initData();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    EventImpl sixFlags = new EventImpl("Six Flags", new TimeImpl(this.sunday, "0800", this.sunday,
            "1200"), new LocationImpl(false, "California"), listUsers);

    SchedulePlanner timSch = new SchedulePlanner(new ArrayList<>(Arrays.asList(sixFlags)), "Tim");
    SchedulePlanner katSch = new SchedulePlanner(new ArrayList<>(Arrays.asList(sixFlags)), "Kat");
    IPlannerModel anytimeModel = new NUPlannerModel(new ArrayList<>(Arrays.asList(timSch, katSch)));
    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 1500;
    int duration2 = 2300;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);
    anytimeModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    SchedulePlanner curr = anytimeModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(DaysOfTheWeek.SUNDAY, curr.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(1).time().endDay());
    assertEquals("1200", curr.events().get(1).time().startTime());
    assertEquals("1300", curr.events().get(1).time().endTime());

    assertEquals("Hello", curr.events().get(2).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.WEDNESDAY, curr.events().get(2).time().endDay());
    assertEquals("1300", curr.events().get(2).time().startTime());
    assertEquals("0320", curr.events().get(2).time().endTime());

    SchedulePlanner curr1 = anytimeModel.schedules().get(1);
    assertEquals("Hi", curr1.events().get(1).name());
    assertEquals(DaysOfTheWeek.SUNDAY, curr1.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(1).time().endDay());
    assertEquals("1200", curr1.events().get(1).time().startTime());
    assertEquals("1300", curr1.events().get(1).time().endTime());

    assertEquals("Hello", curr1.events().get(2).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.WEDNESDAY, curr1.events().get(2).time().endDay());
    assertEquals("1300", curr1.events().get(2).time().startTime());
    assertEquals("0320", curr1.events().get(2).time().endTime());
  }

  @Test
  public void testWorkHoursStrategyAddingOneEventToEmptySchedule() {
    this.initData();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    EventImpl sixFlags = new EventImpl("Six Flags", new TimeImpl(this.monday, "0900", this.monday,
            "1300"), new LocationImpl(false, "California"), listUsers);

    SchedulePlanner timSch = new SchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Tim");
    SchedulePlanner katSch = new SchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Kat");
    IPlannerModel workModel = new WorkTimePlannerModel(new ArrayList<>((List.of(timSch, katSch))));

    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 120;
    int duration2 = 240;

    workModel.scheduleEvent(eventName, loc, duration, listUsers);
    workModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    SchedulePlanner curr = workModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(1).time().endDay());
    assertEquals("1300", curr.events().get(1).time().startTime());
    assertEquals("1500", curr.events().get(1).time().endTime());

    assertEquals("Hello", curr.events().get(2).name());
    assertEquals(DaysOfTheWeek.TUESDAY, curr.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.TUESDAY, curr.events().get(2).time().endDay());
    assertEquals("0900", curr.events().get(2).time().startTime());
    assertEquals("1300", curr.events().get(2).time().endTime());

    SchedulePlanner curr1 = workModel.schedules().get(1);
    assertEquals("Hi", curr1.events().get(1).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(1).time().endDay());
    assertEquals("1300", curr1.events().get(1).time().startTime());
    assertEquals("1500", curr1.events().get(1).time().endTime());

    assertEquals("Hello", curr1.events().get(2).name());
    assertEquals(DaysOfTheWeek.TUESDAY, curr1.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.TUESDAY, curr1.events().get(2).time().endDay());
    assertEquals("0900", curr1.events().get(2).time().startTime());
    assertEquals("1300", curr1.events().get(2).time().endTime());
  }

}
