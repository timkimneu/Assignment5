package view;

import model.ReadOnlyPlannerModel;

import javax.swing.JPanel;
import java.awt.*;

public class SchedulePanel extends JPanel {

  private final ReadOnlyPlannerModel model;

  public SchedulePanel(ReadOnlyPlannerModel model) {
    super();
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;

    g2d.drawLine(this.getWidth() / 3, 0,
        this.getWidth()/3, this.getHeight());
    g2d.drawLine(2 * this.getWidth() / 3, 0,
        2 * this.getWidth() /3, this.getHeight());
    g2d.drawLine(0, this.getHeight() / 3,
        this.getWidth(), this.getHeight() / 3);
    g2d.drawLine(0, 2 * this.getHeight() / 3,
        this.getWidth(), 2 * this.getHeight() / 3);

    //drawBoardState(g2d);
  }


}
