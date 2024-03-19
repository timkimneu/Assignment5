package view;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.Schedule;
import model.Time;
import model.User;
import view.ScheduleSystemTextView;

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

  // test schedulesToString method in ScheduleSystemTextView class
  @Test
  public void testSchedulesToString() {
    this.initData();
    // test with only one schedule with one event
    List<Schedule> scheduleList1 = new ArrayList<>(Collections.singletonList(this.sch4));
    ScheduleSystemTextView sstv1 = new ScheduleSystemTextView(scheduleList1);
    Assert.assertEquals("User: Dinner\nSunday:\nMonday:\nTuesday:\nWednesday: \n" +
            "\tname: Wednesday Dinner\n" +
            "\ttime: Wednesday: 1800 -> Wednesday: 1830\n" +
            "\tlocation: Home\n" +
            "\tonline: false\n" +
            "\tinvitees: Me\n" +
            "Thursday:\nFriday:\nSaturday:\n", sstv1.schedulesToString());

    // test with 2 schedules with 2 events each
    List<Schedule> scheduleList2 = new ArrayList<>(Arrays.asList(this.sch1, this.sch2));
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
