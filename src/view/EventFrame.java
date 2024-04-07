package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;

import controller.ScheduleSystem;
import controller.ScheduleSystemController;
import model.DaysOfTheWeek;
import model.Event;
import model.Location;
import model.ReadOnlyPlannerModel;
import model.Time;
import model.User;

/**
 * JFrame class extension that represents the screen that pops up when a user wants to
 * add, modify, or remove an event to a selected schedule in the planner. The window
 * asks a user to input the name of the event, a location, starting day, starting time,
 * ending day, ending time, and a list of users.
 */
public class EventFrame extends JFrame implements ScheduleSystemView, EvtFrame {

  private final JPanel mainPanel;
  private final ReadOnlyPlannerModel model;
  private JComboBox<String> onlineBox;
  private JComboBox<String> startDOTW;
  private JComboBox<String> endDOTW;
  private JTextField place;
  private JTextField eventText;
  private JTextField startTimeTxt;
  private JTextField endTimeTxt;
  private JList<String> usersBox;

  private JButton button;

  private final ScheduleSystemController controller;

  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the
   * name, the location, starting day, starting time, ending day, ending time, and the list of
   * attendees for an event to be added, modified, or removed from the schedule.
   */
  public EventFrame(ReadOnlyPlannerModel model) {
    super();
    this.model = model;
    this.controller = new ScheduleSystemController(this);

    setSize(300, 500);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new GridLayout(0, 1));

    JLabel event = new JLabel("\tEvent name: ");
    mainPanel.add(event);
    eventText = new JTextField();
    mainPanel.add(eventText);
    mainPanel.add(new JLabel("\tLocation: "));

    startDOTW = new JComboBox<>();
    endDOTW = new JComboBox<>();
    startTimeTxt = new JTextField();
    endTimeTxt = new JTextField();

    onlinePanel();
    dayOfTheWeekPanel("Starting Day: ", startDOTW);
    timePanel("Starting Time: ", startTimeTxt);
    dayOfTheWeekPanel("Ending Day: ", endDOTW);
    timePanel("Ending Time: ", endTimeTxt);
    availableUsersPanel();

    buttonsPanel();

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

  @Override
  public void hidePanel() {
    setVisible(false);
  }

  @Override
  public void addListener(ScheduleSystem listener) {

  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void action(ScheduleSystem system) {
    Event newEvent = null;
    List<User> newUser = new ArrayList<>();
    try {
      newEvent = new Event(eventText.getText(),
          new Time(DaysOfTheWeek.valueOf(startDOTW.getSelectedItem().toString().toUpperCase()),
              startTimeTxt.getText(),
              (DaysOfTheWeek.valueOf(endDOTW.getSelectedItem().toString().toUpperCase())),
              endTimeTxt.getText()),
          new Location((Boolean) onlineBox.getSelectedItem(), place.getText()), newUser);
    } catch (IllegalArgumentException ex) {
      System.out.println("HI");
    }

    Event finalNewEvent = newEvent;
    button.addActionListener(e -> system.addEvent(finalNewEvent));

    //system.addEvent()
  }

  private void buttonsPanel() {
    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    JButton modEvent = new JButton("Modify event");
    modEvent.setActionCommand("Modify event");
    List<String> listUsers = usersBox.getSelectedValuesList();
    //action(controller);

    //modEvent.addActionListener(e -> controller.addEvent(finalNewEvent));

    JButton removeEvent = new JButton("Remove event");
    removeEvent.setActionCommand("Remove event");
    removeEvent.addActionListener(e -> System.out.println(
        "Event Name: " + eventText.getText() + "\n"
            + "Online: " + onlineBox.getSelectedItem() + "\n"
            + "Place: " + place.getText() + "\n"
            + "Starting Day: " + startDOTW.getSelectedItem() + "\n"
            + "Starting Time: " + startTimeTxt.getText() + "\n"
            + "Ending Day: " + endDOTW.getSelectedItem() + "\n"
            + "Ending Time: " + endTimeTxt.getText() + "\n"
            + "Users: " + usersBox.getSelectedValuesList()
    ));

    bottomButtons.add(modEvent);
    bottomButtons.add(removeEvent);
    this.add(bottomButtons, BorderLayout.PAGE_END);
  }

  private void onlinePanel() {
    JPanel online = new JPanel();
    online.setLayout(new BoxLayout(online, BoxLayout.PAGE_AXIS));
    String[] options = {"Is online", "Is not online"};

    onlineBox = new JComboBox<>();
    for (int option = 0; option < options.length; option++) {
      onlineBox.addItem(options[option]);
    }
    online.add(onlineBox);

    JPanel loc = new JPanel();
    loc.setLayout(new GridLayout(1, 5));
    loc.add(online);
    place = new JTextField();
    loc.add(place);

    mainPanel.add(loc);
  }

  private void dayOfTheWeekPanel(String d, JComboBox<String> result) {
    JPanel startDay = new JPanel();
    startDay.add(new JLabel("\t" + d));

    String[] allDOTW = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday"};

    for (String aDOTW: allDOTW) {
      result.addItem(aDOTW);
    }
    startDay.add(result);

    mainPanel.add(startDay);
  }

  private void timePanel(String s, JTextField result) {
    JPanel startTime = new JPanel();
    startTime.setLayout(new GridLayout(0, 1));
    startTime.add(new JLabel("\t" + s));

    JPanel sTime = new JPanel();
    sTime.setLayout(new GridLayout(1, 2));
    sTime.add(startTime);
    sTime.add(result);

    mainPanel.add(sTime);
  }

  private void availableUsersPanel() {
    JPanel usersTag = new JPanel();
    usersTag.setLayout(new GridLayout(0, 1));
    usersTag.add(new JLabel("\tAvailable Users: "));

    mainPanel.add(usersTag);

    String[] allUsers = this.model.users().toArray(new String[0]);

    usersBox = new JList<>(allUsers);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(usersBox);
    usersBox.setLayoutOrientation(JList.VERTICAL);

    mainPanel.add(scrollPane);
  }

}
