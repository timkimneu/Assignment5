package view;

import model.ReadOnlyPlannerModel;

import javax.swing.*;

public class ScheduleFrame extends JFrame implements ScheduleSystemView {

  private final SchedulePanel panel;

  public ScheduleFrame(ReadOnlyPlannerModel model) {
    super();
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new SchedulePanel(model);
    this.add(panel);
  }

  @Override
  public String schedulesToString() {
    return null;
  }
}
