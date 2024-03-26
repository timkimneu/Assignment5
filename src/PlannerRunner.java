import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import view.EventFrame;

import javax.swing.*;
import java.util.List;

public final class PlannerRunner {
  public static void main(String[] args) {
//    NUPlannerModel model = new NUPlannerModel();
//    ScheduleSystemView view = new ScheduleSystemView(model);
//    view.setVisible(true);

    EventFrame frame = new EventFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    //ReadOnlyPlannerModel model = new ReadOnlyPlannerModel();


  }
}
