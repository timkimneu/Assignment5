//package view;
//
//import controller.ScheduleSystem;
//import model.DaysOfTheWeek;
//import model.EventImpl;
//import model.IReadOnlyPlannerModel;
//
//import java.awt.*;
//
///**
// *
// */
//public abstract class DrawEventsDecorator implements SchPanel {
//  private final SchPanel schPanel;
//  private final IReadOnlyPlannerModel model;
//  private String id;
//
//  /**
//   *
//   * @param schPanel
//   */
//  public DrawEventsDecorator(IReadOnlyPlannerModel model, SchPanel schPanel) {
//    this.schPanel = schPanel;
//    this.model = model;
//  }
//
//  @Override
//  public void drawDates(String user) {
//    this.id = user;
//    schPanel.drawDates(user);
//  }
//
//  @Override
//  public void addListener(ScheduleSystem controller) {
//    schPanel.addListener(controller);
//  }
//
//  @Override
//  public void drawScheduleState(Graphics2D g2d) {
//    schPanel.drawScheduleState(g2d);
//  }
//
//  @Override
//  public void fillSquares(Graphics2D g2d, String startTime, String endTime, DaysOfTheWeek startDay,
//    DaysOfTheWeek endDay, EventImpl event, Color color) {
//    schPanel.fillSquares(g2d, startTime, endTime, startDay, endDay, event, color);
//  }
//
//}
