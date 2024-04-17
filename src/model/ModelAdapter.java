//package model;
//
//import provider.model.CentralSystem;
//import provider.model.Event;
//import provider.model.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ModelAdapter implements IPlannerModel {
//
//  private final CentralSystem model;
//
//  public ModelAdapter(CentralSystem model) {
//    this.model = model;
//  }
//
//  @Override
//  public void addEvent(EventImpl event) {
//
//  }
//
//  @Override
//  public void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users) {
//
//  }
//
//  @Override
//  public void modifyEvent(EventImpl event, EventImpl newEvent, UserImpl user) {
//
//  }
//
//  @Override
//  public void removeEvent(EventImpl event, UserImpl user) {
//
//  }
//
//  @Override
//  public List<SchedulePlanner> schedules() {
//    List<User> providerUser = this.model.getUsers();
//    List<SchedulePlanner> schedule = new ArrayList<>();
//
//    for (int user = 0; user < providerUser.size(); user++) {
//      List<Event>
//      //SchedulePlanner currSch = new SchedulePlanner();
//    }
//
//    return null;
//  }
//
//  @Override
//  public List<EventImpl> events(String name) {
//    return null;
//  }
//
//  @Override
//  public List<String> users() {
//    List<User> providerUser = this.model.getUsers();
//    List<String> finalUsers = new ArrayList<>();
//    for (int user = 0; user < providerUser.size(); user++) {
//      finalUsers.add(user, providerUser.get(user).getUsername());
//    }
//    return finalUsers;
//  }
//}
