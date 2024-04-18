import controller.ScheduleSystem;
import controller.ScheduleSystemController;
import model.EventBuilderAdapter;
import model.IPlannerModel;
import model.ScheduleCreator;
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

    if (args.length == 0) {
      System.out.println("Please provide an argument: anytime, workhours, provideranytime, or providerworkhours");
      return;
    }
    String schedule = args[0];
    IPlannerModel model = ScheduleCreator.createSchedule(
        ScheduleCreator.ScheduleType.valueOf(schedule.strip().toUpperCase()));

    if (schedule.equals("provideranytime")) {
      CentralSystemView csview = new FeaturesViewFrame<>(new FeaturesScheduleView(), new EventBuilderAdapter());
      ScheduleSystem controller = new ScheduleSystemController(new ViewAdapter(csview, model));
      controller.launch(model);
    }
    else if (schedule.equals("providerworkhours")) {
      CentralSystemView csview = new FeaturesViewFrame<>(new FeaturesScheduleView(), new EventBuilderAdapter());
      ScheduleSystem controller = new ScheduleSystemController(new ViewAdapter(csview, model));
      controller.launch(model);
    }
    else {
      ScheduleSystemView view = new ScheduleFrame(model);
      ScheduleSystem controller = new ScheduleSystemController(view);
      controller.launch(model);
    }
  }
}
