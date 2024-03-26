package view;

import model.ReadOnlyPlannerModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 */
public class SchedulePanel extends JPanel implements ActionListener, ItemListener, ListSelectionListener {

  private final ReadOnlyPlannerModel model;

  /**
   *
   * @param model
   */
  public SchedulePanel(ReadOnlyPlannerModel model) {
    super();
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;

    for (int line = 0; line < 24; line++) {
      if (line % 4 == 0) {
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
      }
      else {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
      }
    }

    g2d.setStroke(new BasicStroke(1));
    for (int col = 0; col < 7; col++) {
      g2d.drawLine(this.getWidth() / 7 * col, 0,
          this.getWidth() / 7 * col, this.getWidth());
    }
    //drawBoardState(g2d);
  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
