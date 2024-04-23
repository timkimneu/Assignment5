package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Represents examples and tests of the Schedule model and all of its relevant supporting classes.
 * Examples and tests of classes and methods within the model package.
 */
public class SaturdayModelTests {
  SatDOTW saturday;
  SatDOTW sunday;
  SatDOTW monday;
  SatDOTW tuesday;
  SatDOTW wednesday;
  SatDOTW thursday;
  SatDOTW friday;
  ITime<SatDOTW> invalidTime;
  ITime<SatDOTW> time1;
  ITime<SatDOTW> time2;
  ITime<SatDOTW> time3;
  ITime<SatDOTW> time4;
  ITime<SatDOTW> time5;
  LocationImpl loc1;
  LocationImpl loc2;
  LocationImpl loc3;
  LocationImpl loc4;
  LocationImpl loc5;
  LocationImpl loc6;
  UserImpl newUser;
  UserImpl user1;
  UserImpl user2;
  UserImpl user3;
  UserImpl classmate;
  UserImpl friend;
  UserImpl bestFriend;
  UserImpl dinnerDude;
  List<UserImpl> users1;
  List<UserImpl> users2;
  List<UserImpl> users3;
  IEvent<SatDOTW> church;
  IEvent<SatDOTW> school;
  IEvent<SatDOTW> vacation;
  IEvent<SatDOTW> mondayAfternoonJog;
  IEvent<SatDOTW> wednesdayDinner;
  List<IEvent<SatDOTW>> mtEvents;
  List<IEvent<SatDOTW>> events1;
  List<IEvent<SatDOTW>> events2;
  ISchedule<SatDOTW> mtSch;
  ISchedule<SatDOTW> sch1;
  ISchedule<SatDOTW> sch2;
  ISchedule<SatDOTW> sch3;
  ISchedule<SatDOTW> sch4;
  List<ISchedule<SatDOTW>> schedules1;
  List<ISchedule<SatDOTW>> schedules2;
  List<ISchedule<SatDOTW>> schedules3;
  IPlannerModel<SatDOTW> mtModel;
  IPlannerModel<SatDOTW> model1;
  IPlannerModel<SatDOTW> model2;
  IPlannerModel<SatDOTW> model3;
  IPlannerModel<SatDOTW> model4;

  private void initDOTW() {
    this.sunday = SatDOTW.SUNDAY;
    this.monday = SatDOTW.MONDAY;
    this.tuesday = SatDOTW.TUESDAY;
    this.wednesday = SatDOTW.WEDNESDAY;
    this.thursday = SatDOTW.THURSDAY;
    this.friday = SatDOTW.FRIDAY;
    this.saturday = SatDOTW.SATURDAY;
  }

  private void initData() {
    this.initDOTW();
    this.time1 = new SatTimeImpl(this.sunday, "1000", this.sunday, "1300");
    this.time2 = new SatTimeImpl(this.monday, "0800", this.friday, "1500");
    this.time3 = new SatTimeImpl(this.thursday, "1700", this.monday, "0900");
    this.time4 = new SatTimeImpl(this.monday, "1200", this.monday, "1245");
    this.time5 = new SatTimeImpl(this.wednesday, "1800", this.wednesday, "1830");
    this.loc1 = new LocationImpl(false, "Mulberry Street");
    this.loc2 = new LocationImpl(false, "Northeastern University");
    this.loc3 = new LocationImpl(false, "Cancun Resort");
    this.loc4 = new LocationImpl(false, "Outside");
    this.loc5 = new LocationImpl(false, "Home");
    this.loc6 = new LocationImpl(true, "Office");
    this.newUser = new UserImpl("New User");
    this.user1 = new UserImpl("Me");
    this.user2 = new UserImpl("Mom");
    this.user3 = new UserImpl("Dad");
    this.classmate = new UserImpl("Classmate");
    this.friend = new UserImpl("Friend");
    this.bestFriend = new UserImpl("Best Friend");
    this.dinnerDude = new UserImpl("Dinner Dude");
    this.users1 = new ArrayList<>(Arrays.asList(this.user1, this.user2, this.user3, this.newUser,
        this.friend));
    this.users2 = new ArrayList<>(Arrays.asList(this.user1, this.classmate, this.bestFriend,
        this.newUser));
    this.users3 = new ArrayList<>(Arrays.asList(this.user1, this.friend, this.bestFriend,
        this.newUser));
    this.church = new SatEventImpl("Church", this.time1, this.loc1, this.users1);
    this.school = new SatEventImpl("Classes", this.time2, this.loc2, this.users2);
    this.vacation = new SatEventImpl("Cancun Trip", this.time3, this.loc3, this.users3);
    this.mondayAfternoonJog = new SatEventImpl("Afternoon Jog", this.time4, this.loc4,
        new ArrayList<>(Collections.singletonList(this.friend)));
    this.wednesdayDinner = new SatEventImpl("Wednesday Dinner", this.time5, this.loc5,
        new ArrayList<>(Collections.singletonList(this.dinnerDude)));
    this.mtEvents = new ArrayList<>();
    this.events1 = new ArrayList<>(Arrays.asList(this.church, this.school));
    this.events2 = new ArrayList<>(Arrays.asList(this.vacation, this.mondayAfternoonJog));
    this.mtSch = new SatSchedulePlanner(this.mtEvents, "New User");
    this.sch1 = new SatSchedulePlanner(this.events1, "Me");
    this.sch2 = new SatSchedulePlanner(this.events2, "Friend");
    this.sch3 = new SatSchedulePlanner(new ArrayList<>(Arrays.asList(this.church,
        this.mondayAfternoonJog)), "Best Friend");
    this.sch4 = new SatSchedulePlanner(new ArrayList<>(Collections.singletonList(
        this.wednesdayDinner)), "Dinner Dude");
    this.schedules1 = new ArrayList<>(Arrays.asList(this.sch1, this.sch2));
    this.schedules2 = new ArrayList<>(Arrays.asList(this.sch3, this.sch4));
    this.schedules3 = new ArrayList<>(Collections.singletonList(this.sch2));
    this.mtModel = new SatPlannerModel(new ArrayList<>());
    this.model1 = new SatPlannerModel(new ArrayList<>(Collections.singletonList(this.mtSch)));
    this.model2 = new SatPlannerModel(this.schedules1);
    this.model3 = new SatPlannerModel(this.schedules2);
    this.model4 = new SatPlannerModel(this.schedules3);
  }

  private void initData1() {
    this.initDOTW();
    this.time1 = new SatTimeImpl(this.monday, "1300", this.monday, "1500");
    this.time2 = new SatTimeImpl(this.monday, "1200", this.monday, "1600");
    this.time3 = new SatTimeImpl(this.wednesday, "1800", this.wednesday, "1830");
    this.time4 = new SatTimeImpl(this.sunday, "0800", this.sunday, "1000");
    this.loc1 = new LocationImpl(true, "At Home");
    this.loc2 = new LocationImpl(false, "Campus");
    this.loc3 = new LocationImpl(false, "Dinner Table");
    this.loc4 = new LocationImpl(false, "Park Street");
    this.user1 = new UserImpl("User 1");
    this.user2 = new UserImpl("User 2");
    this.user3 = new UserImpl("User 3");
    this.users1 = new ArrayList<>(Arrays.asList(this.user1, this.user2, this.user3));
    this.vacation = new SatEventImpl("Vacation", this.time1, this.loc1, this.users1);
    this.school = new SatEventImpl("School", this.time2, this.loc2, this.users1);
    this.wednesdayDinner = new SatEventImpl("Dinner", this.time3, this.loc3, this.users1);
    this.church = new SatEventImpl("Church", this.time4, this.loc4, this.users1);
    this.events1 = new ArrayList<>(Arrays.asList(this.vacation, this.wednesdayDinner));
    this.events2 = new ArrayList<>(Arrays.asList(this.vacation, this.wednesdayDinner));
    this.sch1 = new SatSchedulePlanner(this.events1, "User 1");
    this.sch2 = new SatSchedulePlanner(this.events2, "User 2");
    this.schedules1 = new ArrayList<>(Arrays.asList(this.sch1, this.sch2));
    this.model1 = new SatPlannerModel(this.schedules1);
  }

  // Test Time constructor for IllegalArgumentException for an invalid input for
  // starting and/or ending time that contains anything that is not a number.
  @Test
  public void testOnlyNumberForTime() {
    this.initData();
    // starting time is a non-number
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "abcd", this.tuesday, "0230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // ending time is a non-number
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "1234", this.tuesday, "pqrs");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // starting time contains a non-number
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "2a45", this.tuesday, "0230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("String must only contain numbers!", e.getMessage());
    }
    // ending time contains a non-number
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "1230", this.tuesday, "i045");
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
      this.invalidTime = new SatTimeImpl(this.monday, "123", this.tuesday, "00230");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad starting time length that is too short
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "20", this.wednesday, "0331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad starting time length that is too long
    try {
      this.invalidTime = new SatTimeImpl(this.friday, "012300", this.saturday, "0331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad ending time length that is too short
    try {
      this.invalidTime = new SatTimeImpl(this.tuesday, "1111", this.friday, "331");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Time must be represented by a 4 digit String!", e.getMessage());
    }
    // has a bad ending time length that is too long
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "2222", this.tuesday, "02345");
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
      this.invalidTime = new SatTimeImpl(this.monday, "2400", this.tuesday, "0061");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of hours!", e.getMessage());
    }
    // has a bad hour for starting time and bad hour for ending time
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "2400", this.tuesday, "2730");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of hours!", e.getMessage());
    }
    // has a bad minute for starting time and bad minute for ending time
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "0278", this.tuesday, "0460");
      Assert.fail("Failed to catch exception!");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid number of minutes!", e.getMessage());
    }
    // has a bad minute for starting time and bad hour for ending time
    try {
      this.invalidTime = new SatTimeImpl(this.monday, "2078", this.tuesday, "2759");
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
    assertFalse(this.time1.anyOverlap(this.time2));
  }

  // Test online method for Location class
  @Test
  public void testLocationOnline() {
    this.initData();
    assertFalse(this.loc1.online());
    assertFalse(this.loc4.online());
    assertFalse(this.loc5.online());
    assertTrue(this.loc6.online());
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
    Assert.assertEquals("Me", this.sch1.scheduleID());
    Assert.assertEquals("Friend", this.sch2.scheduleID());
    Assert.assertEquals("Best Friend", this.sch3.scheduleID());
    Assert.assertEquals("Dinner Dude", this.sch4.scheduleID());
  }

  // test events method for Schedule class
  @Test
  public void testScheduleEventsMethod() {
    this.initData();
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.events1, "My Schedule");
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.church, this.school)), sch.events());

    ISchedule<SatDOTW> sch0 = new SatSchedulePlanner(this.mtEvents, "New Schedule");
    assertTrue(sch0.events().isEmpty());
  }

  // test addEvent method in Schedule class for IllegalArgumentException
  // when Schedule already contains the given event
  @Test
  public void testAddEventAlreadyContainsEventError() {
    this.initData();
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.mtEvents, "Bad Schedule");
    sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
            this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
            this.school.location(), this.school.users(), this.school.name());
    try {
      sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
              this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
              this.school.location(), this.school.users(), this.school.name());
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    sch.addEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    try {
      sch.addEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
              this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
              this.church.location(), this.church.users(), this.church.name());
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    sch.removeEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
            this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
            this.school.location(), this.school.users(), this.school.name());
    sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
            this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
            this.school.location(), this.school.users(), this.school.name());
    try {
      sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
              this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
              this.school.location(), this.school.users(), this.school.name());
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
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.mtEvents, "Bad Schedule");
    sch.addEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    Assert.assertEquals(1, sch.events().size());
    try {
      sch.addEvent(this.vacation.time().startDay().getDayOrder(), this.vacation.time().startTime(),
              this.vacation.time().endDay().getDayOrder(), this.vacation.time().endTime(),
              this.vacation.location(), this.vacation.users(), this.vacation.name());
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
    // confirm that vacation event has not been added to schedule
    Assert.assertEquals(1, sch.events().size());

    sch.removeEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    sch.addEvent(this.vacation.time().startDay().getDayOrder(), this.vacation.time().startTime(),
            this.vacation.time().endDay().getDayOrder(), this.vacation.time().endTime(),
            this.vacation.location(), this.vacation.users(), this.vacation.name());
    Assert.assertEquals(1, sch.events().size());
    try {
      sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
              this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
              this.school.location(), this.school.users(), this.school.name());
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
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.mtEvents, "My Schedule");
    // check that schedule is empty
    assertFalse(sch.events().contains(this.school));
    assertTrue(sch.events().isEmpty());
    Assert.assertEquals(0, sch.events().size());
    // check that schedule added school event
    sch.addEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
            this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
            this.school.location(), this.school.users(), this.school.name());
    assertFalse(sch.events().isEmpty());
    assertTrue(sch.events().contains(this.school));
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.school)), sch.events());
    Assert.assertEquals(1, sch.events().size());

    sch.addEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    Assert.assertEquals(2, sch.events().size());
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.school, this.church)), sch.events());
  }

  // test removeEvent method in Schedule class for IllegalArgumentException
  // when the given event does not exist in this Schedule
  @Test
  public void testRemoveEventEventDoesNotExist() {
    this.initData();
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.events1, "My Schedule");
    Assert.assertEquals(2, sch.events().size());
    try {
      sch.removeEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
              this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
              this.school.location(), this.school.users(), this.school.name());
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }

    // try removing event again after being removed from list
    sch.removeEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    try {
      sch.removeEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
              this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
              this.church.location(), this.church.users(), this.church.name());
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }
  }

  // test removeEvent method for Schedule class
  @Test
  public void testRemoveEvent() {
    this.initData();
    ISchedule<SatDOTW> sch = new SatSchedulePlanner(this.events1, "My Schedule");
    Assert.assertEquals(2, sch.events().size());
    Assert.assertEquals(new ArrayList<>(Arrays.asList(this.church, this.school)), sch.events());

    sch.removeEvent(this.church.time().startDay().getDayOrder(), this.church.time().startTime(),
            this.church.time().endDay().getDayOrder(), this.church.time().endTime(),
            this.church.location(), this.church.users(), this.church.name());
    Assert.assertEquals(new ArrayList<>(Collections.singletonList(this.school)), sch.events());
    Assert.assertEquals(1, sch.events().size());

    sch.removeEvent(this.school.time().startDay().getDayOrder(), this.school.time().startTime(),
            this.school.time().endDay().getDayOrder(), this.school.time().endTime(),
            this.school.location(), this.school.users(), this.school.name());
    Assert.assertEquals(this.mtEvents, sch.events());
    assertTrue(sch.events().isEmpty());
  }

  // test schedules method for NUPlannerModel class
  @Test
  public void testPlannerSchedules() {
    this.initData();
    List<ISchedule<SatDOTW>> schedules0 = new ArrayList<>(Collections.singletonList(this.mtSch));
    List<ISchedule<SatDOTW>> schedules1 = new ArrayList<>(Arrays.asList(this.sch1, this.sch2));
    Assert.assertEquals(new ArrayList<>(), this.mtModel.schedules());
    Assert.assertEquals(schedules0, this.model1.schedules());
    Assert.assertEquals(schedules1, this.model2.schedules());
  }

  // test events method in NUPlannerModel class for IllegalArgumentException
  // when the given schedule id does not exist in this list of schedules
  @Test
  public void testPlannerEventsNoSuchSchedule() {
    this.initData();
    try {
      this.model1.events("User User");
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Could not find events for given schedule id!", e.getMessage());
    }
  }

  // test events method for NUPlannerModel class
  @Test
  public void testPlannerEvents() {
    this.initData();
    Assert.assertEquals(this.events1, this.model2.events("Me"));
    Assert.assertEquals(this.events2, this.model2.events("Friend"));
  }

  // test users method for NUPlanner class
  @Test
  public void testPlannerUsers() {
    this.initData();
    ArrayList<String> model2Users = new ArrayList<>(Arrays.asList("Me", "Friend"));
    ArrayList<String> model3Users = new ArrayList<>(Arrays.asList("Best Friend", "Dinner Dude"));
    Assert.assertEquals(model2Users, this.model2.users());
    Assert.assertEquals(model3Users, this.model3.users());
  }

  // test addEvent method in NUPlannerModel class for IllegalArgumentException
  // when Schedule already contains the given event
  @Test
  public void testPlannerAddEventAlreadyContainsError() {
    this.initData();
    try {
      this.model2.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    try {
      this.model2.addEvent(this.church);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }

    try {
      this.model3.addEvent(this.wednesdayDinner);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }
  }

  // test addEvent method in NUPlannerModel class for IllegalArgumentException
  // when the given event overlaps with an existing event in the schedule
  @Test
  public void testPlannerAddEventOverlapError() {
    this.initData1();
    try {
      this.model1.addEvent(this.school);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
  }

  // test modifyEvent method in NUPlannerModel class for IllegalArgumentException
  // when attempting to replace old event with itself
  @Test
  public void testPlannerModifyEventSelfReplaceError() {
    this.initData();
    try {
      this.model2.modifyEvent(this.vacation, this.vacation, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Cannot replace old event with same event!", e.getMessage());
    }

    try {
      this.model3.modifyEvent(this.mondayAfternoonJog, this.mondayAfternoonJog, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Cannot replace old event with same event!", e.getMessage());
    }

    try {
      this.model2.modifyEvent(this.wednesdayDinner, this.wednesdayDinner, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Cannot replace old event with same event!", e.getMessage());
    }
  }

  // test modifyEvent method in NUPlannerModel class for IllegalArgumentException
  // when Schedule already contains the given new event
  @Test
  public void testPlannerModifyEventAlreadyContainsError() {
    this.initData1();
    try {
      this.model1.modifyEvent(this.vacation, this.wednesdayDinner, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Schedule already contains given event!", e.getMessage());
    }
  }

  // test modifyEvent method in NUPlannerModel class for IllegalArgumentException
  // when the given new event overlaps with an existing event in the schedule
  @Test
  public void testPlannerModifyEventOverlapsError() {
    this.initData();
    try {
      this.model2.modifyEvent(this.church, this.vacation, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }

    try {
      this.model2.modifyEvent(this.mondayAfternoonJog, this.school, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Added event overlaps with an existing event!" +
          " Removing added event.", e.getMessage());
    }
  }

  // test modifyEvent method in NUPlannerModel class for IllegalArgumentException
  // when the given old event does not exist in the current schedule
  @Test
  public void testPlannerModifyEventNoSuchEventError() {
    this.initData1();
    try {
      this.model1.modifyEvent(this.church, this.school, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }
  }

  // test modifyEvent method for NUPlannerModel class
  @Test
  public void testPlannerModifyEvent() {
    this.initData1();
    Assert.assertEquals(2, this.model1.events("User 1").size());
    this.model1.modifyEvent(this.vacation, this.church, this.user1);
    Assert.assertEquals(2, this.model1.events("User 1").size());
    this.model1.modifyEvent(this.church, this.school, this.user1);
    Assert.assertEquals(2, this.model1.events("User 1").size());
  }

  // test removeEvent method in NUPlannerModel class for IllegalArgumentException
  // when the given event does not exist in this Schedule
  @Test
  public void testPlannerRemoveEventNoSuchEventError() {
    this.initData1();
    try {
      this.model1.removeEvent(this.church, this.user1);
      Assert.fail("Failed to catch error");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Event to be removed not found!", e.getMessage());
    }
  }

  // test removeEvent method for NUPlannerModel class
  @Test
  public void testPlannerRemoveEvent() {
    this.initData1();
    Assert.assertEquals(2, this.model1.events("User 1").size());
    this.model1.removeEvent(this.vacation, this.user1);
    Assert.assertEquals(1, this.model1.events("User 1").size());
    this.model1.removeEvent(this.wednesdayDinner, this.user1);
    Assert.assertEquals(0, this.model1.events("User 1").size());
  }

  @Test
  public void testOverlappingTimes() {
    SatTimeImpl time1 = new SatTimeImpl(SatDOTW.MONDAY, "0920", SatDOTW.TUESDAY, "0200");
    SatTimeImpl time2 = new SatTimeImpl(SatDOTW.TUESDAY, "0000", SatDOTW.TUESDAY, "0100");

    boolean overlap = time1.anyOverlap(time2);
    assertTrue(overlap);
  }

  @Test
  public void testOverlappingTimes2() {
    SatTimeImpl time1 = new SatTimeImpl(SatDOTW.SUNDAY, "0000", SatDOTW.MONDAY, "0920");
    SatTimeImpl time2 = new SatTimeImpl(SatDOTW.MONDAY, "0720", SatDOTW.TUESDAY, "0000");

    boolean overlap = time1.anyOverlap(time2);
    assertTrue(overlap);
  }

  @Test
  public void testOverlappingTimes3() {
    SatTimeImpl time1 = new SatTimeImpl(SatDOTW.TUESDAY, "0950", SatDOTW.TUESDAY, "1130");
    SatTimeImpl time2 = new SatTimeImpl(SatDOTW.TUESDAY, "1315", SatDOTW.TUESDAY, "1515");

    boolean overlap = time1.anyOverlap(time2);
    assertFalse(overlap);
  }

}
