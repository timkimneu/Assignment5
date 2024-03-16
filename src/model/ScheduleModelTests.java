package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents examples and tests of the Schedule model and all of its relevant supporting classes.
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
  Time time4;
  Time time5;
  Location loc1;
  Location loc2;
  Location loc3;
  Location loc4;
  Location loc5;
  Location loc6;
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
  Schedule sch1;
  Schedule sch2;
  Schedule sch3;
  Schedule sch4;

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
    this.loc6 = new Location(true, "Office");
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
    this.sch1 = new Schedule(this.events1, "School Schedule");
    this.sch2 = new Schedule(this.events2, "Summer Schedule");
    this.sch3 = new Schedule(new ArrayList<>(Arrays.asList(this.church, this.mondayAfternoonJog)),
            "My Schedule");
    this.sch4 = new Schedule(new ArrayList<>(Collections.singletonList(this.wednesdayDinner)),
            "Dinner");
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
    Assert.assertEquals(this.monday, this.time3.endDay());
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
    this.initData();
    Assert.assertFalse(this.time1.anyOverlap(this.time2));
  }

  // Test online method for Location class
  @Test
  public void testLocationOnline() {
    this.initData();
    Assert.assertFalse(this.loc1.online());
    Assert.assertFalse(this.loc4.online());
    Assert.assertFalse(this.loc5.online());
    Assert.assertTrue(this.loc6.online());
  }

  // Test place method for Location class
  @Test
  public void testLocationPlace() {
    this.initData();
    Assert.assertEquals("Mulberry Street", this.loc1.place());
    Assert.assertEquals("Northeastern University", this.loc2.place());
    Assert.assertEquals("Cancun Resort", this.loc3.place());
    Assert.assertEquals("Office", this.loc6.place());
  }

  // Test name method for User class
  @Test
  public void testUserName() {
    this.initData();
    Assert.assertEquals("Me", this.user1.name());
    Assert.assertEquals("Mom", this.user2.name());
    Assert.assertEquals("Dad", this.user3.name());
    Assert.assertEquals("Classmate", this.classmate.name());
    Assert.assertEquals("Friend", this.friend.name());
  }

  // test name method for Event class
  @Test
  public void testEventName() {
    this.initData();
    Assert.assertEquals("Church", this.church.name());
    Assert.assertEquals("Classes", this.school.name());
    Assert.assertEquals("Cancun Trip", this.vacation.name());
    Assert.assertEquals("Afternoon Jog", this.mondayAfternoonJog.name());
    Assert.assertEquals("Wednesday Dinner", this.wednesdayDinner.name());
  }

  // test time method for Event class
  @Test
  public void testEventTime() {
    this.initData();
    Assert.assertEquals(this.time1, this.church.time());
    Assert.assertEquals(this.time2, this.school.time());
    Assert.assertEquals(this.time3, this.vacation.time());
  }

  // test location method for Event class
  @Test
  public void testEventLocation() {
    this.initData();
    Assert.assertEquals(this.loc1, this.church.location());
    Assert.assertEquals(this.loc2, this.school.location());
    Assert.assertEquals(this.loc3, this.vacation.location());
    Assert.assertEquals(this.loc4, this.mondayAfternoonJog.location());
    Assert.assertEquals(this.loc5, this.wednesdayDinner.location());
  }

  // test users method for Event class
  @Test
  public void testEventUsers() {
    this.initData();
    Assert.assertEquals(this.users1, this.church.users());
    Assert.assertEquals(this.users2, this.school.users());
    Assert.assertEquals(this.users3, this.vacation.users());
  }

  // test id method for Schedule class
  @Test
  public void testScheduleId() {
    this.initData();
    Assert.assertEquals("School Schedule", this.sch1.scheduleID());
    Assert.assertEquals("Summer Schedule", this.sch2.scheduleID());
    Assert.assertEquals("My Schedule", this.sch3.scheduleID());
    Assert.assertEquals("Dinner", this.sch4.scheduleID());
  }

  // test events method for Schedule class
  @Test
  public void testScheduleEventsMethod() {
    this.initData();
    Schedule sch = new Schedule(this.events1, "My Schedule");
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.church, this.school)), sch.events());

    Schedule sch0 = new Schedule(this.mtEvents, "New Schedule");
    Assert.assertTrue(sch0.events().isEmpty());
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
    Assert.assertEquals(1, sch.events().size());
    try {
      sch.addEvent(this.vacation);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
    // confirm that vacation event has not been added to schedule
    Assert.assertEquals(1, sch.events().size());

    sch.removeEvent(this.church);
    sch.addEvent(this.vacation);
    Assert.assertEquals(1, sch.events().size());
    try {
      sch.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
    // confirm that school event has not been added to schedule
    Assert.assertEquals(1, sch.events().size());
  }

  // test addEvent method for Schedule class
  @Test
  public void testAddEvent() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "My Schedule");
    // check that schedule is empty
    Assert.assertFalse(sch.events().contains(this.school));
    Assert.assertTrue(sch.events().isEmpty());
    Assert.assertEquals(0, sch.events().size());
    // check that schedule added school event
    sch.addEvent(this.school);
    Assert.assertFalse(sch.events().isEmpty());
    Assert.assertTrue(sch.events().contains(this.school));
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.school)), sch.events());
    Assert.assertEquals(1, sch.events().size());

    sch.addEvent(this.church);
    Assert.assertEquals(2, sch.events().size());
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.school, this.church)), sch.events());
  }

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when attempting to replace old event with itself
  @Test
  public void testModifyEventNewSameOldError() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "My Schedule");
    sch.addEvent(this.vacation);
    try {
      sch.modifyEvent(this.vacation, this.vacation);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Cannot replace old event with same event!", e.getMessage());
    }
  }

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when Schedule already contains the given new event
  @Test
  public void testModifyEventAlreadyContainsEventError() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "My Schedule");
    sch.addEvent(this.church);
    sch.addEvent(this.school);
    try {
      sch.modifyEvent(this.church, this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }
  }

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when the given new event overlaps with an existing event in the schedule
  @Test
  public void testModifyEventOverlappingEventsError() {
    this.initData();
    ArrayList<Event> churchSchool = new ArrayList<>(Arrays.asList(this.church, this.school));
    Schedule sch = new Schedule(churchSchool, "My Schedule");
    Assert.assertEquals(2, sch.events().size());
    try {
      sch.modifyEvent(this.church, this.vacation);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
    // confirm that original list of events is not modified
    Assert.assertEquals(churchSchool, sch.events());
  }

  // test modifyEvent method in Schedule class for IllegalArgumentException
  // when the given old event does not exist in the current schedule
  @Test
  public void testModifyEventOldDoesNotExistError() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "My Schedule");
    sch.addEvent(this.vacation);
    try {
      sch.modifyEvent(this.school, this.church);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }
  }

  // test modifyEvent method for Schedule class
  @Test
  public void testModifyEvent() {
    this.initData();
    Schedule sch = new Schedule(this.mtEvents, "My Schedule");
    // confirm that original event is added to list
    sch.addEvent(this.school);
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.school)), sch.events());
    // confirm that old event is replaced with new event
    sch.modifyEvent(this.school, this.vacation);
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.vacation)), sch.events());

    sch.modifyEvent(this.vacation, this.church);
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.church)), sch.events());

    // check modify with multiple events existing prior to modifications. Also check
    // modify won't throw an error when replaced event overlaps with new event
    sch.addEvent(this.mondayAfternoonJog);
    sch.modifyEvent(this.mondayAfternoonJog, this.school);
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.church, this.school)), sch.events());
  }

  // test removeEvent method in Schedule class for IllegalArgumentException
  // when the given event does not exist in this Schedule
  @Test
  public void testRemoveEventEventDoesNotExist() {
    this.initData();
    Schedule sch = new Schedule(this.events1, "My Schedule");
    Assert.assertEquals(2, sch.events().size());
    try {
      sch.removeEvent(this.vacation);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }

    // try removing event again after being removed from list
    sch.removeEvent(this.church);
    try {
      sch.removeEvent(this.church);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }
  }

  // test removeEvent method for Schedule class
  @Test
  public void testRemoveEvent() {
    this.initData();
    Schedule sch = new Schedule(this.events1, "My Schedule");
    Assert.assertEquals(2, sch.events().size());
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.church, this.school)), sch.events());

    sch.removeEvent(this.church);
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.school)), sch.events());
    Assert.assertEquals(1, sch.events().size());

    sch.removeEvent(this.school);
    Assert.assertEquals(this.mtEvents, sch.events());
    Assert.assertTrue(sch.events().isEmpty());
  }
}
