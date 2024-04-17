import controller.ScheduleSystem;
import controller.ScheduleSystemController;
import model.DaysOfTheWeek;
import model.EventBuilderAdapter;
import model.EventImpl;
import model.IPlannerModel;
import model.LocationImpl;
import model.NUPlannerModel;
import model.ScheduleCreator;
import model.SchedulePlanner;
import model.TimeImpl;
import model.UserImpl;
import provider.view.CentralSystemView;
import provider.view.FeaturesScheduleView;
import provider.view.FeaturesViewFrame;
import view.ScheduleFrame;
import view.ScheduleSystemView;
import view.ViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class to run planner GUI. Allows events to be added, modified, and removed
 * and can import schedules via XML to create new schedules. GUI currently has no
 * real functionality in modifying the model and will instead just print all input
 * into the console when attempting to add/modify/remove an event as well as when
 * opening an XML and returning the file path of the XML file.
 */
public final class PlannerRunner {

  /**
   * Main class to run planner GUI. Allows events to be added, modified, and removed
   * and can import schedules via XML to create new schedules. GUI currently has no
   * real functionality in modifying the model and will instead just print all input
   * into the console when attempting to add/modify/remove an event as well as when
   * opening an XML and returning the file path of the XML file.
   */
  public static void main(String[] args) {
//    DaysOfTheWeek monday = DaysOfTheWeek.MONDAY;
//    DaysOfTheWeek tuesday = DaysOfTheWeek.TUESDAY;
//    DaysOfTheWeek wednesday = DaysOfTheWeek.WEDNESDAY;
//    DaysOfTheWeek sunday = DaysOfTheWeek.SUNDAY;
//    TimeImpl time1 = new TimeImpl(monday, "1300", tuesday, "1500");
//    TimeImpl time2 = new TimeImpl(monday, "1200", monday, "1600");
//    TimeImpl time3 = new TimeImpl(wednesday, "1800", wednesday, "1830");
//    TimeImpl time4 = new TimeImpl(sunday, "0800", sunday, "1000");
//    LocationImpl loc1 = new LocationImpl(true, "At Home");
//    LocationImpl loc2 = new LocationImpl(false, "Campus");
//    LocationImpl loc3 = new LocationImpl(false, "Dinner Table");
//    LocationImpl loc4 = new LocationImpl(false, "Park Street");
//    UserImpl user1 = new UserImpl("User 1");
//    UserImpl user2 = new UserImpl("User 2");
//    UserImpl user3 = new UserImpl("User 3");
//    List<UserImpl> users1 = new ArrayList<>(Arrays.asList(user1, user2, user3));
//    EventImpl vacation = new EventImpl("Vacation", time1, loc1, users1);
//    EventImpl school = new EventImpl("School", time2, loc2, users1);
//    EventImpl wednesdayDinner = new EventImpl("Dinner", time3, loc3, users1);
//    EventImpl church = new EventImpl("Church", time4, loc4, users1);
//    List<EventImpl> events1 = new ArrayList<>(Arrays.asList(vacation, wednesdayDinner));
//    List<EventImpl> events2 = new ArrayList<>(Arrays.asList(school, church));
//    SchedulePlanner sch1 = new SchedulePlanner(events1, "User 1");
//    SchedulePlanner sch2 = new SchedulePlanner(events2, "User 2");
//    List<SchedulePlanner> schedules1 = new ArrayList<>(Arrays.asList(sch1, sch2));
//
//    List<SchedulePlanner> emptyList = new ArrayList<>();
//    NUPlannerModel mtModel = new NUPlannerModel(emptyList);
//    ScheduleSystemView view1 = new ScheduleFrame(mtModel);
//    ScheduleSystem schModel = new ScheduleSystemController(view1);
//    schModel.launch(mtModel);
//    view1.hidePanel();
//    schModel.readXML("src/prof.xml");
//    List<SchedulePlanner> listSchedules = schModel.returnSchedule();
//
//    emptyList = new ArrayList<>();
//    NUPlannerModel mtModel2 = new NUPlannerModel(emptyList);
//    ScheduleSystemView view2 = new ScheduleFrame(mtModel2);
//    ScheduleSystem model2 = new ScheduleSystemController(view2);
//    model2.launch(mtModel2);
//    view2.hidePanel();
//    model2.readXML("src/School Schedule.xml");
//    List<SchedulePlanner> listSchedules2 = model2.returnSchedule();

    if (args.length == 0) {
      System.out.println("Please provide an argument: anytime or workhours");
      return;
    }
    String schedule = args[0];
    IPlannerModel model = ScheduleCreator.createSchedule(
        ScheduleCreator.ScheduleType.valueOf(schedule.strip().toUpperCase()));

//    List<SchedulePlanner> newList = new ArrayList<>();
//    newList.addAll(listSchedules);
//    newList.addAll(listSchedules2);
//    newList.addAll(schedules1);
    if (schedule.equals("provideranytime")) {
      CentralSystemView csview = new FeaturesViewFrame<>(new FeaturesScheduleView(), new EventBuilderAdapter());
      ScheduleSystem controller = new ScheduleSystemController(new ViewAdapter(csview, model));
      controller.launch(model);
    }
    else {
      ScheduleSystemView view = new ScheduleFrame(model);
      ScheduleSystem controller = new ScheduleSystemController(view);
      controller.launch(model);
    }
//    ScheduleSystem controller = new ScheduleSystemController(view);
//    controller.launch(model);
  }
}
