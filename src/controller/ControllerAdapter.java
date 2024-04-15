package controller;

import model.EventImpl;
import model.IPlannerModel;
import model.LocationImpl;
import model.SchedulePlanner;
import model.UserImpl;
import provider.controller.Features;

import java.util.List;

public class ControllerAdapter implements ScheduleSystem {
  private final Features features;

  public ControllerAdapter(Features features) {
    this.features = features;
  }

  @Override
  public void readXML(String filePath) {
    features.readXML(filePath);
  }

  @Override
  public void writeXML(String beginPath) {
    features.writeXML(beginPath);
  }

  @Override
  public List<SchedulePlanner> returnSchedule() {
    return null;
  }

  @Override
  public void launch(IPlannerModel model) {

  }

  @Override
  public void addEvent(EventImpl e) {
    features.addEvent();
  }

  @Override
  public void modifyEvent(EventImpl oldEvent, EventImpl newEvent, UserImpl user) {
    features.modifyEvent();
  }

  @Override
  public void removeEvent(EventImpl e, UserImpl user) {
    features.removeEvent();
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users) {
    features.scheduleEvent();
  }
}
