package controller;

import model.DaysOfTheWeek;
import model.EventImpl;
import model.IEvent;
import model.ISchedule;
import model.LocationImpl;
import model.NUPlannerModel;
import model.IPlannerModel;
import model.SatDOTW;
import model.SatEventImpl;
import model.SatPlannerModel;
import model.SatSchedulePlanner;
import model.SatTimeImpl;
import model.SatWorktimeModel;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;
import model.WorkTimePlannerModel;
import model.ITime;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import view.SatScheduleFrame;
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
  SatDOTW satSaturday;
  SatDOTW satSunday;
  SatDOTW satMonday;
  SatDOTW satTuesday;
  SatDOTW satWednesday;
  SatDOTW satThursday;
  SatDOTW satFriday;
  ITime<DaysOfTheWeek> time1;
  ITime<DaysOfTheWeek> time2;
  ITime<DaysOfTheWeek> time3;
  ITime<DaysOfTheWeek> time4;
  ITime<DaysOfTheWeek> time5;
  ITime<SatDOTW> satTime1;
  ITime<SatDOTW> satTime2;
  ITime<SatDOTW> satTime3;
  ITime<SatDOTW> satTime4;
  ITime<SatDOTW> satTime5;
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
  IEvent<DaysOfTheWeek> church;
  IEvent<DaysOfTheWeek> school;
  IEvent<DaysOfTheWeek> vacation;
  IEvent<DaysOfTheWeek> mondayAfternoonJog;
  IEvent<DaysOfTheWeek> wednesdayDinner;
  IEvent<SatDOTW> satChurch;
  IEvent<SatDOTW> satSchool;
  IEvent<SatDOTW> satVacation;
  IEvent<SatDOTW> satMondayAfternoonJog;
  IEvent<SatDOTW> satWednesdayDinner;
  List<IEvent<DaysOfTheWeek>> mtEvents;
  List<IEvent<DaysOfTheWeek>> events1;
  List<IEvent<DaysOfTheWeek>> events2;
  List<IEvent<SatDOTW>> satMtEvents;
  List<IEvent<SatDOTW>> satEvents1;
  List<IEvent<SatDOTW>> satEvents2;
  ISchedule<DaysOfTheWeek> sch1;
  ISchedule<DaysOfTheWeek> sch2;
  ISchedule<DaysOfTheWeek> sch3;
  ISchedule<DaysOfTheWeek> sch4;
  ISchedule<SatDOTW> satSch1;
  ISchedule<SatDOTW> satSch2;
  ISchedule<SatDOTW> satSch3;
  ISchedule<SatDOTW> satSch4;
  ScheduleSystemController<DaysOfTheWeek> schSysMod;
  ScheduleSystemController<DaysOfTheWeek> ssc;
  IPlannerModel<DaysOfTheWeek> model1;
  ScheduleSystemController<SatDOTW> satSchSysMod;
  ScheduleSystemController<SatDOTW> satSsc;
  IPlannerModel<SatDOTW> satModel1;
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
    ScheduleSystemView<DaysOfTheWeek> view = new ScheduleFrame(this.model1);
    this.schSysMod = new ScheduleSystemController<>(view);
    this.schSysMod.launch(model1);
  }

  private void initDataSat() {
    this.satSaturday = SatDOTW.SATURDAY;
    this.satSunday = SatDOTW.SUNDAY;
    this.satMonday = SatDOTW.MONDAY;
    this.satTuesday = SatDOTW.TUESDAY;
    this.satWednesday = SatDOTW.WEDNESDAY;
    this.satThursday = SatDOTW.THURSDAY;
    this.satFriday = SatDOTW.FRIDAY;
    this.satTime1 = new SatTimeImpl(this.satSunday, "1000", this.satSunday, "1300");
    this.satTime2 = new SatTimeImpl(this.satMonday, "0800", this.satFriday, "1500");
    this.satTime3 = new SatTimeImpl(this.satThursday, "1700", this.satMonday, "0900");
    this.satTime4 = new SatTimeImpl(this.satMonday, "1200", this.satMonday, "1245");
    this.satTime5 = new SatTimeImpl(this.satWednesday, "1800", this.satWednesday, "1830");
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
    this.satChurch = new SatEventImpl("Church", this.satTime1, this.loc1, this.users1);
    this.satSchool = new SatEventImpl("Classes", this.satTime2, this.loc2, this.users2);
    this.satVacation = new SatEventImpl("Cancun Trip", this.satTime3, this.loc3, this.users3);
    this.satMondayAfternoonJog = new SatEventImpl("Afternoon Jog", this.satTime4, this.loc4,
            new ArrayList<>(Collections.singletonList(this.user1)));
    this.satWednesdayDinner = new SatEventImpl("Wednesday Dinner", this.satTime5, this.loc5,
            new ArrayList<>(Collections.singletonList(this.user1)));
    this.satMtEvents = new ArrayList<>();
    this.satEvents1 = new ArrayList<>(Arrays.asList(this.satChurch, this.satSchool));
    this.satEvents2 = new ArrayList<>(Arrays.asList(this.satVacation, this.satMondayAfternoonJog));
    this.satSch1 = new SatSchedulePlanner(this.satEvents1, "School Schedule");
    this.satSch2 = new SatSchedulePlanner(this.satEvents2, "Summer Schedule");
    this.satSch3 = new SatSchedulePlanner(new ArrayList<>(Arrays.asList(this.satChurch,
            this.satMondayAfternoonJog)), "My Schedule");
    this.satSch4 = new SatSchedulePlanner(new ArrayList<>(Collections.singletonList(
            this.satWednesdayDinner)), "Dinner");
    this.satModel1 = new SatPlannerModel(Arrays.asList(this.satSch1, this.satSch2));
    ScheduleSystemView<SatDOTW> view = new SatScheduleFrame(this.satModel1);
    this.satSchSysMod = new ScheduleSystemController<>(view);
    this.satSchSysMod.launch(satModel1);
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
    List<ISchedule<DaysOfTheWeek>> emptyList = new ArrayList<>();
    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
    ScheduleSystemView<DaysOfTheWeek> view = new ScheduleFrame(mtModel);
    ScheduleSystem<DaysOfTheWeek> schModel = new ScheduleSystemController(view);
    schModel.launch(mtModel);
    schModel.readXML("src/prof.xml");
    List<ISchedule<DaysOfTheWeek>> listSchedules = schModel.returnSchedule();
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
    List<ISchedule<DaysOfTheWeek>> emptyList = new ArrayList<>();
    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
    ScheduleSystemView<DaysOfTheWeek> view = new ScheduleFrame(mtModel);
    ScheduleSystem<DaysOfTheWeek> schModel = new ScheduleSystemController<DaysOfTheWeek>(view);
    schModel.launch(mtModel);
    schModel.readXML("src/prof.xml");
    schModel.readXML("src/prof.xml");
    List<ISchedule<DaysOfTheWeek>> listSchedules = schModel.returnSchedule();
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
    List<ISchedule<DaysOfTheWeek>> listSchedules = this.schSysMod.returnSchedule();
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
    ScheduleSystemView<DaysOfTheWeek> ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController<DaysOfTheWeek> schSysCon = new ScheduleSystemController<>(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog, this.mondayAfternoonJog.users().get(0));
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
  }

  @Test
  public void testControllerRemoveEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView<DaysOfTheWeek> ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController<DaysOfTheWeek> schSysCon = new ScheduleSystemController<>(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog, this.mondayAfternoonJog.users().get(0));
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
    schSysCon.removeEvent(this.mondayAfternoonJog, this.user1);
    Assert.assertFalse(mtModel.schedules().get(0).events().contains(this.mondayAfternoonJog));
  }

  @Test
  public void testControllerModifyEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView<DaysOfTheWeek> ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController<DaysOfTheWeek> schSysCon = new ScheduleSystemController<>(ssView);
    schSysCon.launch(mtModel);
    schSysCon.addEvent(this.mondayAfternoonJog, this.mondayAfternoonJog.users().get(0));
    schSysCon.modifyEvent(this.mondayAfternoonJog, this.church, this.user1);
    Assert.assertTrue(mtModel.schedules().get(0).events().contains(this.church));
  }

  @Test
  public void testControllerScheduleEvent() {
    this.initData();
    NUPlannerModel mtModel = new NUPlannerModel(new ArrayList<>(List.of(new SchedulePlanner(
            this.mtEvents, "Me"))));
    ScheduleSystemView<DaysOfTheWeek> ssView = new ScheduleFrame(mtModel);
    ScheduleSystemController<DaysOfTheWeek> schSysCon = new ScheduleSystemController<>(ssView);
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
    IPlannerModel<DaysOfTheWeek> mkModel = new PlannerMock(strOut);
    ScheduleSystemView<DaysOfTheWeek> view = new ScheduleFrame(mkModel);
    ssc = new ScheduleSystemController<>(view);
    ssc.launch(mkModel);
  }

  @Test
  public void testMockAddEvent() {
    this.initData();
    this.initAppendable();
    ssc.addEvent(this.church, this.church.users().get(0));
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
    IPlannerModel<DaysOfTheWeek> anytimeModel = new NUPlannerModel(
        new ArrayList<>(Arrays.asList(timSch)));
    String eventName = "Hi";
    LocationImpl loc = new LocationImpl(true, "school");
    int duration = 1500;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);

    ISchedule<DaysOfTheWeek> curr = anytimeModel.schedules().get(0);
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
    IPlannerModel<DaysOfTheWeek> anytimeModel = new NUPlannerModel(
            new ArrayList<>(Arrays.asList(timSch, katSch)));
    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 1500;
    int duration2 = 2300;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);
    anytimeModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    ISchedule<DaysOfTheWeek> curr = anytimeModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(DaysOfTheWeek.SUNDAY, curr.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(1).time().endDay());
    assertEquals("1200", curr.events().get(1).time().startTime());
    assertEquals("1300", curr.events().get(1).time().endTime());

    assertEquals("Hello", curr.events().get(2).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.TUESDAY, curr.events().get(2).time().endDay());
    assertEquals("1300", curr.events().get(2).time().startTime());
    assertEquals("0320", curr.events().get(2).time().endTime());

    ISchedule<DaysOfTheWeek> curr1 = anytimeModel.schedules().get(1);
    assertEquals("Hi", curr1.events().get(1).name());
    assertEquals(DaysOfTheWeek.SUNDAY, curr1.events().get(1).time().startDay());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(1).time().endDay());
    assertEquals("1200", curr1.events().get(1).time().startTime());
    assertEquals("1300", curr1.events().get(1).time().endTime());

    assertEquals("Hello", curr1.events().get(2).name());
    assertEquals(DaysOfTheWeek.MONDAY, curr1.events().get(2).time().startDay());
    assertEquals(DaysOfTheWeek.TUESDAY, curr1.events().get(2).time().endDay());
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
    IPlannerModel<DaysOfTheWeek> workModel = new WorkTimePlannerModel(
            new ArrayList<>((List.of(timSch, katSch))));

    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 120;
    int duration2 = 240;

    workModel.scheduleEvent(eventName, loc, duration, listUsers);
    workModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    ISchedule<DaysOfTheWeek> curr = workModel.schedules().get(0);
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

    ISchedule<DaysOfTheWeek> curr1 = workModel.schedules().get(1);
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

  @Test
  public void satTestAnytimeStrategyAddingOneEventToMoreEventsSchedule() {
    this.initDataSat();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    SatEventImpl sixFlags = new SatEventImpl("Six Flags", new SatTimeImpl(this.satSunday, "0800",
            this.satSunday, "1200"), new LocationImpl(false, "California"), listUsers);

    SatSchedulePlanner timSch = new SatSchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Tim");
    SatSchedulePlanner katSch = new SatSchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Kat");
    IPlannerModel<SatDOTW> anytimeModel = new SatPlannerModel(
            new ArrayList<>(Arrays.asList(timSch, katSch)));
    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 1500;
    int duration2 = 2300;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);
    anytimeModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    ISchedule<SatDOTW> curr = anytimeModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(SatDOTW.SATURDAY, curr.events().get(1).time().startDay());
    assertEquals(SatDOTW.SUNDAY, curr.events().get(1).time().endDay());
    assertEquals("0000", curr.events().get(1).time().startTime());
    assertEquals("0100", curr.events().get(1).time().endTime());

    assertEquals("Hello", curr.events().get(2).name());
    assertEquals(SatDOTW.SUNDAY, curr.events().get(2).time().startDay());
    assertEquals(SatDOTW.MONDAY, curr.events().get(2).time().endDay());
    assertEquals("1200", curr.events().get(2).time().startTime());
    assertEquals("0220", curr.events().get(2).time().endTime());

    ISchedule<SatDOTW> curr1 = anytimeModel.schedules().get(1);
    assertEquals("Hi", curr1.events().get(1).name());
    assertEquals(SatDOTW.SATURDAY, curr1.events().get(1).time().startDay());
    assertEquals(SatDOTW.SUNDAY, curr1.events().get(1).time().endDay());
    assertEquals("0000", curr1.events().get(1).time().startTime());
    assertEquals("0100", curr1.events().get(1).time().endTime());

    assertEquals("Hello", curr1.events().get(2).name());
    assertEquals(SatDOTW.SUNDAY, curr1.events().get(2).time().startDay());
    assertEquals(SatDOTW.MONDAY, curr1.events().get(2).time().endDay());
    assertEquals("1200", curr1.events().get(2).time().startTime());
    assertEquals("0220", curr1.events().get(2).time().endTime());
  }

  @Test
  public void satTestWorkHoursStrategyAddingOneEventToEmptySchedule() {
    this.initDataSat();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    SatEventImpl sixFlags = new SatEventImpl("Six Flags", new SatTimeImpl(this.satMonday, "0900",
            this.satMonday, "1300"), new LocationImpl(false, "California"), listUsers);

    SatSchedulePlanner timSch = new SatSchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Tim");
    SatSchedulePlanner katSch = new SatSchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Kat");
    IPlannerModel<SatDOTW> workModel = new SatWorktimeModel(
            new ArrayList<>((List.of(timSch, katSch))));

    String eventName = "Hi";
    String eventName2 = "Hello";
    LocationImpl loc = new LocationImpl(true, "school");
    LocationImpl loc2 = new LocationImpl(true, "home");
    int duration = 120;
    int duration2 = 240;

    workModel.scheduleEvent(eventName, loc, duration, listUsers);
    workModel.scheduleEvent(eventName2, loc2, duration2, listUsers);

    ISchedule<SatDOTW> curr = workModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(SatDOTW.MONDAY, curr.events().get(1).time().startDay());
    assertEquals(SatDOTW.MONDAY, curr.events().get(1).time().endDay());
    assertEquals("1300", curr.events().get(1).time().startTime());
    assertEquals("1500", curr.events().get(1).time().endTime());

    assertEquals("Hello", curr.events().get(2).name());
    assertEquals(SatDOTW.TUESDAY, curr.events().get(2).time().startDay());
    assertEquals(SatDOTW.TUESDAY, curr.events().get(2).time().endDay());
    assertEquals("0900", curr.events().get(2).time().startTime());
    assertEquals("1300", curr.events().get(2).time().endTime());

    ISchedule<SatDOTW> curr1 = workModel.schedules().get(1);
    assertEquals("Hi", curr1.events().get(1).name());
    assertEquals(SatDOTW.MONDAY, curr1.events().get(1).time().startDay());
    assertEquals(SatDOTW.MONDAY, curr1.events().get(1).time().endDay());
    assertEquals("1300", curr1.events().get(1).time().startTime());
    assertEquals("1500", curr1.events().get(1).time().endTime());

    assertEquals("Hello", curr1.events().get(2).name());
    assertEquals(SatDOTW.TUESDAY, curr1.events().get(2).time().startDay());
    assertEquals(SatDOTW.TUESDAY, curr1.events().get(2).time().endDay());
    assertEquals("0900", curr1.events().get(2).time().startTime());
    assertEquals("1300", curr1.events().get(2).time().endTime());
  }

  @Test
  public void satTestAnytimeStrategyAddingOneEventToEmptySchedule() {
    this.initDataSat();
    List<UserImpl> listUsers = new ArrayList<>();
    listUsers.add(new UserImpl("Kat"));
    listUsers.add(new UserImpl("Tim"));
    SatEventImpl sixFlags = new SatEventImpl("Six Flags", new SatTimeImpl(this.satSunday, "0800",
            this.satSunday, "1200"), new LocationImpl(false, "California"), listUsers);

    SatSchedulePlanner timSch = new SatSchedulePlanner(new ArrayList<>(List.of(sixFlags)), "Tim");
    IPlannerModel<SatDOTW> anytimeModel = new SatPlannerModel(
            new ArrayList<>(Arrays.asList(timSch)));
    String eventName = "Hi";
    LocationImpl loc = new LocationImpl(true, "school");
    int duration = 1500;

    anytimeModel.scheduleEvent(eventName, loc, duration, listUsers);

    ISchedule<SatDOTW> curr = anytimeModel.schedules().get(0);
    assertEquals("Hi", curr.events().get(1).name());
    assertEquals(SatDOTW.SATURDAY, curr.events().get(1).time().startDay());
    assertEquals(SatDOTW.SUNDAY, curr.events().get(1).time().endDay());
    assertEquals("0000", curr.events().get(1).time().startTime());
    assertEquals("0100", curr.events().get(1).time().endTime());
  }
}
