package view;

import controller.ControllerAdapter;
import controller.ScheduleSystem;
import model.IPlannerModel;
import model.ReadOnlyAdapter;
import model.SchedulePlanner;
import model.UserAdapter;
import model.UserImpl;
import provider.view.CentralSystemView;
import provider.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ViewAdapter implements ScheduleSystemView {
  private final CentralSystemView providerView;
  private final IPlannerModel model;

  /**
   *
   * @param providerView
   * @param model
   */
  public ViewAdapter(CentralSystemView providerView, IPlannerModel model) {
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
  public void addListener(ScheduleSystem listener) {
    providerView.addFeatures(new ControllerAdapter(listener, providerView, model));
  }

  @Override
  public void refresh() {
    List<User> finalUser = new ArrayList<>();
    for (int user = 0; user < model.users().size(); user++) {
      finalUser.add(user, new UserAdapter(new UserImpl(model.users().get(user))));
    }
    providerView.displayAvailableUsers(finalUser);
    //

    User selectedUser = providerView.currentSelectedUser();
    for (SchedulePlanner sch : this.model.schedules()) {
      if (selectedUser != null && selectedUser.toString().equals(sch.scheduleID())) {
        System.out.println("User in refresh" + selectedUser.toString());
        providerView.displaySchedule(new ReadOnlyAdapter(sch));
      }
    }

    //providerView.displayExistingEvent(new EventAdapter());

    makeVisible();
  }
}
