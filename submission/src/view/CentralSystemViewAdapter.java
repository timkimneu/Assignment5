//package view;
//
//import provider.controller.Features;
//import provider.model.Event;
//import provider.model.ReadOnlySchedule;
//import provider.model.User;
//import provider.view.CentralSystemView;
//
//import java.util.List;
//
//public class CentralSystemViewAdapter implements CentralSystemView {
//
//  private final ScheduleFrame schFrame;
//
//  public CentralSystemViewAdapter(ScheduleFrame schFrame) {
//    this.schFrame = schFrame;
//  }
//
//  @Override
//  public void displaySchedule(ReadOnlySchedule schedule) {
//
//  }
//
//  @Override
//  public void displayError(String text) {
//    // ignore
//  }
//
//  @Override
//  public void display(boolean show) {
//    // ignore
//  }
//
//  @Override
//  public void displayExistingEvent(Event existingEvent) {
//    // ignore
//  }
//
//  @Override
//  public void displayAvailableUsers(List<User> users) {
//
//  }
//
//  @Override
//  public User currentSelectedUser() {
//    // can be ignored (is never called)
//    return null;
//  }
//
//  @Override
//  public void addFeatures(Features features) {
//
//  }
//}
