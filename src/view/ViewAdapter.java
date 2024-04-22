package view;

import controller.ControllerAdapter;
import controller.ScheduleSystem;
import model.DaysOfTheWeek;
import model.IPlannerModel;
import model.EventImpl;
import model.ISchedule;
import model.ReadOnlyAdapter;
import model.SatDOTW;
import model.SchedulePlanner;
import model.UserAdapter;
import model.UserImpl;
import provider.view.CentralSystemView;
import provider.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the view in order to illustrate the provider's view of the schedule.
 * Implements our implementation of schedule view, and imposes of the provider's
 * CentralSystemView interface. Takes in the model and responses accordingly to
 * listeners and refresh methods.
 */
public class ViewAdapter implements ScheduleSystemView<DaysOfTheWeek> {
  private final CentralSystemView providerView;
  private final IPlannerModel<DaysOfTheWeek> model;

  /**
   * Adapter for the view in order to illustrate the provider's view of the schedule.
   * Implements our implementation of schedule view, and imposes of the provider's
   * CentralSystemView interface. Takes in the model and responses accordingly to
   * listeners and refresh methods.
   *
   * @param providerView Provider's central view that will be illustrated.
   * @param model Our model that will respond to certain requests and changes.
   */
  public ViewAdapter(CentralSystemView providerView, IPlannerModel<DaysOfTheWeek> model) {
    this.providerView = providerView;
    this.model = model;
  }

  @Override
  public String schedulesToString() {
    // not implemented
    return null;
  }

  @Override
  public void makeVisible() {
    providerView.display(true);
  }

  @Override
  public void hidePanel() {
    providerView.display(false);
  }

  @Override
  public void addListener(ScheduleSystem<DaysOfTheWeek> listener) {
    providerView.addFeatures(new ControllerAdapter(listener, providerView, model));
  }

  @Override
  public void refresh() {
    List<User> finalUser = new ArrayList<>();
    for (int user = 0; user < model.users().size(); user++) {
      finalUser.add(user, new UserAdapter(new UserImpl(model.users().get(user))));
    }
    providerView.displayAvailableUsers(finalUser);

    User selectedUser = providerView.currentSelectedUser();
    for (ISchedule<DaysOfTheWeek> sch : this.model.schedules()) {
      if (selectedUser != null && selectedUser.toString().equals(sch.scheduleID())) {
        providerView.displaySchedule(new ReadOnlyAdapter(sch));
      }
    }

    //providerView.displayExistingEvent(new EventAdapter());

    makeVisible();
  }
}
