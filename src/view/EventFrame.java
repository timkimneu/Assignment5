package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public class EventFrame extends JFrame implements ScheduleSystemView {

  private JPasswordField pfield;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  /**
   *
   */
  public EventFrame() {
    super();

    setSize(300, 400);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new GridLayout(0, 1));

    JLabel event = new JLabel(" Event name: ");
    mainPanel.add(event);
    JTextField eventText = new JTextField();
    mainPanel.add(eventText);
    mainPanel.add(new JLabel(" Location: "));

    onlinePanel();

    JPanel startDay = new JPanel();
    startDay.add(new JLabel("Starting Day: "));
    JTextField startText = new JTextField();
    startDay.add(startText);
    mainPanel.add(startDay);

//    JPanel endDay = new JPanel();
//    endDay.add(new JLabel("Ending Day: "));
//    endDay.add(startText);
//    endDay.setLayout(new BoxLayout(endDay, BoxLayout.PAGE_AXIS));
//    String[] options = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
//    JComboBox<String> onlineBox = new JComboBox<>();
//    for (int option = 0; option < options.length; option++) {
//      onlineBox.addItem(options[option]);
//    }
//    endDay.add(onlineBox);
//    mainPanel.add(endDay);

    add(mainPanel);

  }

  @Override
  public String schedulesToString() {
    return null;
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  private void onlinePanel() {
    JPanel online = new JPanel();
    online.setLayout(new BoxLayout(online, BoxLayout.PAGE_AXIS));
    String[] options = {"Is online", "Is not online"};

    JComboBox<String> onlineBox = new JComboBox<>();
    for (int option = 0; option < options.length; option++) {
      onlineBox.addItem(options[option]);
    }
    online.add(onlineBox);

    JPanel loc = new JPanel();
    loc.setLayout(new GridLayout(1, 5));
    loc.add(online);
    JTextField place = new JTextField();
    loc.add(place);
    mainPanel.add(loc);
  }

}
