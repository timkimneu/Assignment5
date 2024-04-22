package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import controller.ScheduleSystem;
import model.DaysOfTheWeek;
import model.EventImpl;
import model.LocationImpl;
import model.IReadOnlyPlannerModel;
import model.TimeImpl;
import model.UserImpl;

/**
 * JFrame class extension that represents the screen that pops up when a user wants to
 * add, modify, or remove an event to a selected schedule in the planner. The window
 * asks a user to input the name of the event, a location, starting day, starting time,
 * ending day, ending time, and a list of users.
 */
public class EventFrame extends JFrame implements ScheduleSystemView, EvtFrame {
  private final JPanel mainPanel;
  private final IReadOnlyPlannerModel model;
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
  private EventImpl originalEvent;
  private EventImpl unmodifiedEvent;
  private final JPanel usersTag;// = new JPanel();
  private JLabel availUsers;// = new JLabel("\tAvailable Users: ");
  private GridLayout gridLayout;// = new GridLayout(0, 1);
  private JScrollPane scrollPane;// = new JScrollPane();
  private String[] allUsers;
  private UserImpl user;

  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the name,
   * the location, starting day, starting time, ending day, ending time, and the list of attendees
   * for an event to be added, modified, or removed from the schedule.
   */
  public EventFrame(IReadOnlyPlannerModel model) {
    super();
    this.model = model;

    usersTag = new JPanel();
    availUsers = new JLabel("\tAvailable Users: ");
    scrollPane = new JScrollPane();
    gridLayout = new GridLayout(0, 1);

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
      } catch (IllegalArgumentException | NullPointerException ex) {
        JOptionPane.showMessageDialog(null, "Cannot remove event");
        System.out.println(ex.getMessage());
      }
    }
    );
  }

  private EventImpl createEvent() {
    EventImpl newEvent = new EventImpl(eventText.getText(),
            new TimeImpl(DaysOfTheWeek.valueOf(startDOTW.getSelectedItem().toString()
                    .toUpperCase()), startTimeTxt.getText(),
                    (DaysOfTheWeek.valueOf(endDOTW.getSelectedItem().toString().toUpperCase())),
                    endTimeTxt.getText()),
            new LocationImpl(getOnlineBool(Objects.requireNonNull(onlineBox.getSelectedItem())),
                    place.getText()), getUsers(usersBox.getSelectedValuesList()));
    return newEvent;
  }

  private List<UserImpl> getUsers(List<String> strUsers) {
    List<UserImpl> newUsers = new ArrayList<>();
    for (String s : strUsers) {
      UserImpl newUser = new UserImpl(s);
      newUsers.add(newUser);
    }
    return newUsers;
  }

  private boolean getOnlineBool(Object o) {
    String onlineStr = o.toString();
    return onlineStr.equals("true");
  }

  @Override
  public void refresh() {
    repaint();
  }

  /**
   * Resets the frame to blank (previous inputs/autofill).
   */
  public void resetFrame() {
    eventText.setText("");
    place.setText("");
    startDOTW.setSelectedItem("Sunday");
    startTimeTxt.setText("");
    endDOTW.setSelectedItem("Sunday");
    endTimeTxt.setText("");

    // add select users
    this.availableUsersPanel();
    this.makeVisible();
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

    String[] allDOTW = {"Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"};

    for (String aDOTW : allDOTW) {
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
    usersTag.setLayout(gridLayout);

    usersTag.add(availUsers);

    mainPanel.add(usersTag);

    allUsers = this.model.users().toArray(new String[0]);
    usersBox = new JList<>(allUsers);

    scrollPane.setViewportView(usersBox);
    usersBox.setLayoutOrientation(JList.VERTICAL);

    mainPanel.add(scrollPane);
  }

  @Override
  public void addDefaultEvent(EventImpl event) {
    eventText.setText(event.name());
    if (event.location().online()) {
      onlineBox.setSelectedItem("Is online");
    } else {
      onlineBox.setSelectedItem("Is not online");
    }
    place.setText(event.location().place());
    startDOTW.setSelectedItem(event.time().startDay().observeDay());
    startTimeTxt.setText(event.time().startTime());
    endDOTW.setSelectedItem(event.time().endDay().observeDay());
    endTimeTxt.setText(event.time().endTime());

    // add select users
    for (int user = 0; user < event.users().size(); user++) {
      String us = String.valueOf(event.users().get(user).name()).replaceAll("\"", "");
      this.model.users().add(us);
    }

    this.availableUsersPanel();

    this.originalEvent = event;

    this.refresh();
    this.makeVisible();
  }

  @Override
  public void addSelectedUser(UserImpl user) {
    this.user = user;
  }

  @Override
  public void getUnmodifiedEvent(EventImpl event) {
    this.unmodifiedEvent = event;
  }
}
