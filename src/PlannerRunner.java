import controller.ScheduleSystem;
import controller.ScheduleSystemController;
import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.NUPlannerModel;
import model.SchedulePlanner;
import model.Time;
import model.User;
import view.ScheduleFrame;
import view.ScheduleSystemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public final class PlannerRunner {
  public static void main(String[] args) {
    DaysOfTheWeek monday = DaysOfTheWeek.MONDAY;
    DaysOfTheWeek tuesday = DaysOfTheWeek.TUESDAY;
    DaysOfTheWeek wednesday = DaysOfTheWeek.WEDNESDAY;
    DaysOfTheWeek sunday = DaysOfTheWeek.SATURDAY;
    Time time1 = new Time(monday, "1300", tuesday, "1500");
    Time time2 = new Time(monday, "1200", monday, "1600");
    Time time3 = new Time(wednesday, "1800", wednesday, "1830");
    Time time4 = new Time(sunday, "0800", sunday, "1000");
    Location loc1 = new Location(true, "At Home");
    Location loc2 = new Location(false, "Campus");
    Location loc3 = new Location(false, "Dinner Table");
    Location loc4 = new Location(false, "Park Street");
    User user1 = new User("User 1");
    User user2 = new User("User 2");
    User user3 = new User("User 3");
    List<User> users1 = new ArrayList<>(Arrays.asList(user1, user2, user3));
    Event vacation = new Event("Vacation", time1, loc1, users1);
    Event school = new Event("School", time2, loc2, users1);
    Event wednesdayDinner = new Event("Dinner", time3, loc3, users1);
    Event church = new Event("Church", time4, loc4, users1);
    List<Event> events1 = new ArrayList<>(Arrays.asList(vacation, wednesdayDinner));
    List<Event> events2 = new ArrayList<>(Arrays.asList(school, church));
    SchedulePlanner sch1 = new SchedulePlanner(events1, "User 1");
    SchedulePlanner sch2 = new SchedulePlanner(events2, "User 2");
    List<SchedulePlanner> schedules1 = new ArrayList<>(Arrays.asList(sch1, sch2));
    NUPlannerModel model1 = new NUPlannerModel(schedules1);

    List<SchedulePlanner> emptyList = new ArrayList<>();
    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
    ScheduleSystem schModel = new ScheduleSystemController(mtModel);
    schModel.readXML("Assignment5/src/prof.xml");
    List<SchedulePlanner> listSchedules = schModel.returnSchedule();

    emptyList = new ArrayList<>();
    NUPlannerModel mtModel2 = new NUPlannerModel(emptyList);
    ScheduleSystem model2 = new ScheduleSystemController(mtModel2);
    model2.readXML("Assignment5/src/School Schedule.xml");
    List<SchedulePlanner> listSchedules2 = model2.returnSchedule();

    List<SchedulePlanner> newList = new ArrayList<>();
    newList.addAll(listSchedules);
    newList.addAll(listSchedules2);
    newList.addAll(schedules1);

    NUPlannerModel model = new NUPlannerModel(newList);
    ScheduleSystemView view = new ScheduleFrame(model);
    view.makeVisible();
  }

}