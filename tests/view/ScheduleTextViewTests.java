package view;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.DaysOfTheWeek;
import model.EventImpl;
import model.IEvent;
import model.ISchedule;
import model.ITime;
import model.LocationImpl;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;

/**
 * Represents examples and tests of the ScheduleSystemTextView and all of its relevant supporting
 * classes. Examples and tests of classes and methods within the view package.
 */
public class ScheduleTextViewTests {
  DaysOfTheWeek sunday;
  DaysOfTheWeek monday;
  DaysOfTheWeek tuesday;
  DaysOfTheWeek wednesday;
  DaysOfTheWeek thursday;
  DaysOfTheWeek friday;
  DaysOfTheWeek saturday;
  ITime<DaysOfTheWeek> time1;
  ITime<DaysOfTheWeek> time2;
  ITime<DaysOfTheWeek> time3;
  ITime<DaysOfTheWeek> time4;
  ITime<DaysOfTheWeek> time5;
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
  List<IEvent<DaysOfTheWeek>> mtEvents;
  List<IEvent<DaysOfTheWeek>> events1;
  List<IEvent<DaysOfTheWeek>> events2;
  ISchedule<DaysOfTheWeek> sch1;
  ISchedule<DaysOfTheWeek> sch2;
  ISchedule<DaysOfTheWeek> sch3;
  ISchedule<DaysOfTheWeek> sch4;

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
  }

  // test schedulesToString method in ScheduleSystemTextView class
  @Test
  public void testSchedulesToString() {
    this.initData();
    // test with only one schedule with one event
    List<ISchedule<DaysOfTheWeek>> scheduleList1 = new ArrayList<>(
            Collections.singletonList(this.sch4));
    ScheduleSystemTextView sstv1 = new ScheduleSystemTextView(scheduleList1);
    Assert.assertEquals("User: Dinner\nSunday:\nMonday:\nTuesday:\nWednesday: \n" +
            "\tname: Wednesday Dinner\n" +
            "\ttime: Wednesday: 1800 -> Wednesday: 1830\n" +
            "\tlocation: Home\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n" +
            "Thursday:\nFriday:\nSaturday:\n", sstv1.schedulesToString());

    // test with 2 schedules with 2 events each
    List<ISchedule<DaysOfTheWeek>> scheduleList2 = new ArrayList<>(
            Arrays.asList(this.sch1, this.sch2));
    ScheduleSystemTextView sstv2 = new ScheduleSystemTextView(scheduleList2);
    Assert.assertEquals("User: School Schedule\n" +
            "Sunday: \n" +
            "\tname: Church\n" +
            "\ttime: Sunday: 1000 -> Sunday: 1300\n" +
            "\tlocation: Mulberry Street\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n\tMom\n\tDad\n" +
            "Monday: \n" +
            "\tname: Classes\n" +
            "\ttime: Monday: 0800 -> Friday: 1500\n" +
            "\tlocation: Northeastern University\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n\tClassmate\n\tBest Friend\n" +
            "Tuesday:\nWednesday:\nThursday:\nFriday:\nSaturday:\n" +
            "User: Summer Schedule\n" +
            "Sunday:\nMonday: \n" +
            "\tname: Afternoon Jog\n" +
            "\ttime: Monday: 1200 -> Monday: 1245\n" +
            "\tlocation: Outside\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n" +
            "Tuesday:\nWednesday:\n" +
            "Thursday: \n" +
            "\tname: Cancun Trip\n" +
            "\ttime: Thursday: 1700 -> Monday: 0900\n" +
            "\tlocation: Cancun Resort\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n\tFriend\n\tBest Friend\n" +
            "Friday:\nSaturday:\n", sstv2.schedulesToString());
  }
}
