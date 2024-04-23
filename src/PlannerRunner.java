import controller.ScheduleSystem;
import controller.ScheduleSystemController;
import model.DaysOfTheWeek;
import model.EventBuilderAdapter;
import model.EventImpl;
import model.IPlannerModel;
import model.SatDOTW;
import model.SatEventImpl;
import model.SatSchedulePlanner;
import model.ScheduleCreator;
import model.SchedulePlanner;
import provider.view.CentralSystemView;
import provider.view.FeaturesScheduleView;
import provider.view.FeaturesViewFrame;
import view.EventFrame;
import view.SatScheduleFrame;
import view.ScheduleFrame;
import view.ScheduleSystemView;
import view.ViewAdapter;

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

    if (args.length == 0) {
      System.out.println("Please provide an argument: anytime, workhours, provideranytime, " +
              "or providerworkhours");
      return;
    }
    String schedule = args[0];
    IPlannerModel model = ScheduleCreator.createSchedule(
        ScheduleCreator.ScheduleType.valueOf(schedule.strip().toUpperCase()));

    if (schedule.equals("provideranytime")) {
      CentralSystemView csview = new FeaturesViewFrame<>(new FeaturesScheduleView(),
              new EventBuilderAdapter());
      ScheduleSystem<DaysOfTheWeek> controller =
          new ScheduleSystemController<DaysOfTheWeek>(new ViewAdapter(csview, model));
      controller.launch(model);
    }
    else if (schedule.equals("providerworkhours")) {
      CentralSystemView csview = new FeaturesViewFrame<>(new FeaturesScheduleView(),
              new EventBuilderAdapter());
      ScheduleSystem<DaysOfTheWeek> controller =
          new ScheduleSystemController(new ViewAdapter(csview, model));
      controller.launch(model);
    }
    else if (schedule.equals("anytime") | schedule.equals("workhours")){
      ScheduleSystemView<DaysOfTheWeek> view = new ScheduleFrame(model);
      ScheduleSystem<DaysOfTheWeek> controller = new ScheduleSystemController(view);
      controller.launch(model);
    }
    else if (schedule.equals("saturdayanytime") | schedule.equals("saturdayworkhours")){
      ScheduleSystemView<SatDOTW> view = new SatScheduleFrame(model);
      ScheduleSystem<SatDOTW> controller = new ScheduleSystemController<>(view);
      controller.launch(model);
    }
  }
}
