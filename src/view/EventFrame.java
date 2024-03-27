package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.ReadOnlyPlannerModel;

/**
 *
 */
public class EventFrame extends JFrame implements ScheduleSystemView, ActionListener {

  private JPasswordField pfield;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private ReadOnlyPlannerModel model;

  /**
   *
   */
  public EventFrame(ReadOnlyPlannerModel model) {
    super();
    this.model = model;

    setSize(300, 400);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new GridLayout(0, 1));

    JLabel event = new JLabel("\tEvent name: ");
    mainPanel.add(event);
    JTextField eventText = new JTextField();
    mainPanel.add(eventText);
    mainPanel.add(new JLabel("\tLocation: "));

    onlinePanel();

    dayOfTheWeekPanel("Starting Day: ");

    timePanel("Starting Time: ");

    dayOfTheWeekPanel("Ending Day: ");

    timePanel("Ending Time: ");

    availableUsersPanel();

    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    JButton modEvent = new JButton("Modify event");
    modEvent.setActionCommand("Modify event");
    modEvent.addActionListener(this);

    JButton removeEvent = new JButton("Remove event");
    removeEvent.setActionCommand("Remove event");
    removeEvent.addActionListener(this);

    bottomButtons.add(modEvent);
    bottomButtons.add(removeEvent);
    this.add(bottomButtons, BorderLayout.PAGE_END);

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

  private void dayOfTheWeekPanel(String d) {
    JPanel startDay = new JPanel();
//    startDay.setLayout(new GridLayout(0, 1));
    startDay.add(new JLabel("\t" + d));

    String[] allDOTW = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};

    JComboBox<String> dotwBox = new JComboBox<>();
//    dotwBox.setLayout(new GridLayout(2, 5));
    for (String aDOTW: allDOTW) {
      dotwBox.addItem(aDOTW);
    }
    startDay.add(dotwBox);

    mainPanel.add(startDay);
  }

  private void timePanel(String s) {
    JPanel startTime = new JPanel();
    startTime.setLayout(new GridLayout(0, 1));
    startTime.add(new JLabel("\t" + s));

    JPanel sTime = new JPanel();
    sTime.setLayout(new GridLayout(1, 2));
    sTime.add(startTime);
    JTextField startTimeText = new JTextField();
    sTime.add(startTimeText);

    mainPanel.add(sTime);
  }

  private void availableUsersPanel() {
    JPanel usersTag = new JPanel();
    usersTag.setLayout(new GridLayout(0, 1));
    usersTag.add(new JLabel("\tAvailable Users: "));

    mainPanel.add(usersTag);

    List<String> allUsers = this.model.users();

    JComboBox<String> usersBox = new JComboBox<>();
    for (String u: allUsers) {
      usersBox.addItem(u);
    }
    mainPanel.add(usersBox);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Modify Event":
    }
  }

}
