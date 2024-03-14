import model.DaysOfTheWeek;
import model.Location;
import model.Schedule;
import model.Time;
import model.Event;
import model.User;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Represents examples and tests of the ScheduleSystem model and all of its relevant supporting classes.
 * Examples and tests of classes and methods within the model package.
 */
public class ScheduleModelTests {
  DaysOfTheWeek sunday;
  DaysOfTheWeek monday;
  DaysOfTheWeek tuesday;
  DaysOfTheWeek wednesday;
  DaysOfTheWeek thursday;
  DaysOfTheWeek friday;
  DaysOfTheWeek saturday;
  Time invalidTime;
  Time time1;
  Time time2;
  Time time3;
  Location loc1;
  Location loc2;
  Location loc3;
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
  List<Event> mtEvents;
  List<Event> events1;

  public void initData() {
    this.sunday = DaysOfTheWeek.SUNDAY;
    this.monday = DaysOfTheWeek.MONDAY;
    this.tuesday = DaysOfTheWeek.TUESDAY;
    this.wednesday = DaysOfTheWeek.WEDNESDAY;
    this.thursday = DaysOfTheWeek.THURSDAY;
    this.friday = DaysOfTheWeek.FRIDAY;
    this.saturday = DaysOfTheWeek.SATURDAY;
    this.time1 = new Time(this.sunday, "1000", this.sunday, "1300");
    this.time2 = new Time(this.monday, "0800", this.friday, "1500");
    this.time3 = new Time(this.thursday, "1700", this.tuesday, "0900");
    this.loc1 = new Location(false, "Mulberry Street");
    this.loc2 = new Location(false, "Northeastern University");
    this.loc3 = new Location(false, "Cancun Resort");
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
    this.mtEvents = new ArrayList<>();
    this.events1 = new ArrayList<>(Arrays.asList(this.church, this.school, this.vacation));
  }

  // Test Time constructor for IllegalArgumentException for an invalid input for
  // starting and/or ending time that contains anything that is not a number.
  @Test
  public void testOnlyNumberForTime() {
    this.initData();
    // starting time is a non-number
    try {
      this.invalidTime = new Time(this.monday, "abcd", this.tuesday, "0230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // ending time is a non-number
    try {
      this.invalidTime = new Time(this.monday, "1234", this.tuesday, "pqrs");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // starting time contains a non-number
    try {
      this.invalidTime = new Time(this.monday, "2a45", this.tuesday, "0230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // ending time contains a non-number
    try {
      this.invalidTime = new Time(this.monday, "1230", this.tuesday, "i045");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
  }

  // Test Time constructor for IllegalArgumentException for an invalid input for
  // starting and/or ending time that does not have an exact length of 4.
  @Test
  public void testBadTimeLength() {
    this.initData();
    // has a bad starting time length and bad ending time length
    try {
      this.invalidTime = new Time(this.monday, "123", this.tuesday, "00230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad starting time length that is too short
    try {
      this.invalidTime = new Time(this.monday, "20", this.wednesday, "0331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad starting time length that is too long
    try {
      this.invalidTime = new Time(this.friday, "012300", this.saturday, "0331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad ending time length that is too short
    try {
      this.invalidTime = new Time(this.tuesday, "1111", this.friday, "331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad ending time length that is too long
    try {
      this.invalidTime = new Time(this.monday, "2222", this.tuesday, "02345");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
  }

  // test Time constructor for IllegalArgumentException for an invalid input for
  // starting and/or ending time that has a bad starting hour and/or minute.
  @Test
  public void testInvalidStartAndEndTime() {
    this.initData();
    // has a bad hour for starting time and bad minute for ending time
    try {
      this.invalidTime = new Time(this.monday, "2400", this.tuesday, "0061");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of hours!", e.getMessage());
    }
    // has a bad hour for starting time and bad hour for ending time
    try {
      this.invalidTime = new Time(this.monday, "2400", this.tuesday, "2730");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of hours!", e.getMessage());
    }
    // has a bad minute for starting time and bad minute for ending time
    try {
      this.invalidTime = new Time(this.monday, "0278", this.tuesday, "0460");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of minutes!", e.getMessage());
    }
    // has a bad minute for starting time and bad hour for ending time
    try {
      this.invalidTime = new Time(this.monday, "2078", this.tuesday, "2759");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of minutes!", e.getMessage());
    }
  }

  // test startDay method for Time class
  @Test
  public void testStartDay() {
    this.initData();
    Assert.assertEquals(this.sunday, this.time1.startDay());
    Assert.assertEquals(this.monday, this.time2.startDay());
    Assert.assertEquals(this.thursday, this.time3.startDay());
  }

  // test endDay method for Time class
  @Test
  public void testEndDay() {
    this.initData();
    Assert.assertEquals(this.sunday, this.time1.endDay());
    Assert.assertEquals(this.friday, this.time2.endDay());
    Assert.assertEquals(this.tuesday, this.time3.endDay());
  }

  // test startTime method for Time class
  @Test
  public void testStartTime() {
    this.initData();
    Assert.assertEquals("1000", this.time1.startTime());
    Assert.assertEquals("0800", this.time2.startTime());
    Assert.assertEquals("1700", this.time3.startTime());
  }

  // test endTime method for Time class
  @Test
  public void testEndTime() {
    this.initData();
    Assert.assertEquals("1300", this.time1.endTime());
    Assert.assertEquals("1500", this.time2.endTime());
    Assert.assertEquals("0900", this.time3.endTime());
  }

  // test anyOverlap method for Time class
  @Test
  public void testAnyOverlap() {

  }

  // test time method for Event class
  @Test
  public void testEventTime() {
    this.initData();
    Assert.assertEquals(this.time1, this.church.time());
    Assert.assertEquals(this.time2, this.school.time());
    Assert.assertEquals(this.time3, this.vacation.time());
  }

  // test addEvent method in Schedule class for IllegalArgumentException
  // when Schedule already contains the given event
  @Test
  public void testAddEventAlreadyContainsEventError() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "Bad Schedule");
    sch.addEvent(this.school);
    try {
      sch.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    sch.addEvent(this.church);
    try {
      sch.addEvent(this.church);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    sch.removeEvent(this.school);
    sch.addEvent(this.school);
    try {
      sch.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }
  }

  // test addEvent method in Schedule class for IllegalArgumentException
  // when the given event overlaps with an existing event in the schedule
  @Test
  public void testAddEventOverlappingEventsError() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "Bad Schedule");
    sch.addEvent(this.church);
    try {
      sch.addEvent(this.vacation);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
              " Removing added event.", e.getMessage());
    }

    sch.removeEvent(this.church);
    sch.addEvent(this.vacation);
    try {
      sch.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
              " Removing added event.", e.getMessage());
    }
  }

  // test addEvent method for Schedule class

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when Schedule already contains the given event

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when the given event overlaps with an existing event in the schedule

  // test modifyEvent method for Schedule class

  // test removeEvent method in Schedule class for IllegalArgumentException
  // when the given event does not exist in this Schedule

  @Test
  public void testExampleXML() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("src/tutorial.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

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

    Node first = doc.getElementsByTagName("tutorial").item(0);
    NamedNodeMap attrList = first.getAttributes();

    assertEquals(2, attrList.getLength());

    assertEquals("tutId", attrList.item(0).getNodeName());
    assertEquals("01", attrList.item(0).getNodeValue());

    assertEquals("type", attrList.item(1).getNodeName());
    assertEquals("java", attrList.item(1).getNodeValue());
  }


  @Test
  public void getEvents() {
    Document doc = null;
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = builder.parse(new File("src/prof.xml"));
      doc.getDocumentElement().normalize();
    } catch (Exception ignored) {
    }

    // get all the events
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

}
