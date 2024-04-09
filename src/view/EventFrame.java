package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import controller.ScheduleSystem;
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
  private JButton createEvent;
  private JButton modEvent;
  private JButton removeEvent;
  private Event originalEvent;
  private Event unmodifiedEvent;
  private User user;

  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the name,
   * the location, starting day, starting time, ending day, ending time, and the list of attendees
   * for an event to be added, modified, or removed from the schedule.
   */
  public EventFrame(ReadOnlyPlannerModel model) {
    super();
    this.model = model;

    setSize(350, 500);
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

    modRemoveButtonsPanel();

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
    createEvent.addActionListener(e -> {
      try {
        listener.addEvent(createEvent());
        this.hidePanel();
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, "Cannot create event");
        System.out.println(ex.getMessage());
      }
    });
    modEvent.addActionListener(e -> {
      try {
        listener.modifyEvent(unmodifiedEvent, createEvent(), user);
        this.hidePanel();
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, "Cannot modify event");
        System.out.println(ex.getMessage());
      }
    });
    removeEvent.addActionListener(e -> {
      try {
        listener.removeEvent(originalEvent, user);
        this.hidePanel();
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, "Cannot remove event");
        System.out.println(ex.getMessage());
      }
    }
    );
  }

  private Event createEvent() {
    Event newEvent = new Event(eventText.getText(),
        new Time(DaysOfTheWeek.valueOf(startDOTW.getSelectedItem().toString()
            .toUpperCase()), startTimeTxt.getText(),
            (DaysOfTheWeek.valueOf(endDOTW.getSelectedItem().toString().toUpperCase())),
            endTimeTxt.getText()),
        new Location(getOnlineBool(Objects.requireNonNull(onlineBox.getSelectedItem())),
            place.getText()), getUsers(usersBox.getSelectedValuesList()));
    return newEvent;
  }

  private List<User> getUsers(List<String> strUsers) {
    List<User> newUsers = new ArrayList<>();
    for (String s : strUsers) {
      User newUser = new User(s);
      newUsers.add(newUser);
    }
    return newUsers;
  }

  private boolean getOnlineBool(Object o) {
    String onlineStr = o.toString();
    return onlineStr.equals("Is online");
  }

  @Override
  public void refresh() {
    repaint();
  }

  private void modRemoveButtonsPanel() {
    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    createEvent = new JButton("Create event");
    createEvent.setActionCommand("Create event");

    modEvent = new JButton("Modify event");
    modEvent.setActionCommand("Modify event");

    removeEvent = new JButton("Remove event");
    removeEvent.setActionCommand("Remove event");

    bottomButtons.add(createEvent);
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

  @Override
  public void addDefaultEvent(Event event) {
    eventText.setText(event.name());
    if (event.location().online()) {
      onlineBox.setSelectedItem("Is online");
    }
    else {
      onlineBox.setSelectedItem("Is not online");
    }
    place.setText(event.location().place());
    startDOTW.setSelectedItem(event.time().startDay());
    startTimeTxt.setText(event.time().startTime());
    endDOTW.setSelectedItem(event.time().endDay());
    endTimeTxt.setText(event.time().endTime());

//    for (int i = 0; i < event.users().size(); i++) {
////      usersBox.setSelectedValue(event.users().get(i).name(), true);
//      System.out.println("USERSS" + event.users().get(i).name());
//    }

//    String host = event.users().get(0).name();
//    usersBox.setSelectedValue(event.host().name(), true);
//    usersBox.setSelectedIndex(0);

    // add select users
    this.originalEvent = event;

    this.makeVisible();
  }

  public void addSelectedUser(User user) {
    this.user = user;
  }

  public void getUnmodifiedEvent(Event event) {
    this.unmodifiedEvent = event;
  }
}
