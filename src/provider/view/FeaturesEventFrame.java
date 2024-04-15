package provider.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Component;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import provider.controller.Features;
import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.User;
import provider.model.WeekTime;
//import provider.model.impl.LocalEventBuilder;
//import provider.model.impl.LocalWeekTime;
//import provider.model.impl.WeekDay;
import provider.view.EventFrame;

/**
 * This class visualizes the information necessary to either create, modify, or delete an event
 * using Java Swing. Additionally, takes input necessary to create an event in a manner defined by
 * a given set of features. This class is initially invisible. Users should be added to this
 * class using the displayAvailableUsers method before it is set visible.
 *
 * <p>This class remembers the user that was being viewed when the Frame was opened. If the schedule
 * being viewed changes, any actions taken by the features frame will be taken with the old
 * user in mind, including adding, removing, or modifying events.
 */
public class FeaturesEventFrame extends JFrame implements EventFrame {
  // Various JComponents that are used for taking inputs.
  private final JTextArea eventNameArea = new JTextArea();
  private final JComboBox<String> isOnlineBox =
          new JComboBox<>(new String[]{"is Online", "is in Person"});
  private final JTextField locationInputField = new JTextField();
  private final JComboBox<Integer> startHourBox = new JComboBox<>();
  private final JComboBox<Integer> startMinBox = new JComboBox<>();

  private final JComboBox<WeekDay> startDayBox = new JComboBox<>(WeekDay.values());
  private final JComboBox<Integer> endHourBox = new JComboBox<>();
  private final JComboBox<Integer> endMinBox = new JComboBox<>();
  private final JComboBox<WeekDay> endDayBox = new JComboBox<>(WeekDay.values());

  // Contains the users who can be added to an event who are not in the current event, if it exists
  DefaultListModel<User> systemUserListModel = new DefaultListModel<>();
  private final JList<User> systemUserList = new JList<User>(systemUserListModel);

  // Contains the users who are invited to an event, if it exists
  DefaultListModel<User> eventUserListModel = new DefaultListModel<>();
  private final JList<User> eventUserList = new JList<User>(eventUserListModel);
  private final JButton modifyButton = new JButton("Modify event");
  private final JButton removeButton = new JButton("Remove event");
  private final JButton createButton = new JButton("Create event");

  // The old event to view, if it was passed
  private final Event oldEvent;

  // The new host, or the host of the old event
  private final User host;

  // The current user of the schedule, to be kept track of for creating a new event or removing one
  private final User currentUser;

  // To build new events with
  private final EventBuilder eventBuilder = new LocalEventBuilder();
  private Features features;

  /**
   * When this constructor is called, a new invisible event frame will be created that displays a
   * GUI for viewing or modifying an existing event. Modification can change every detail
   * besides the host. The user who is passed in can also remove the event from their schedule.
   * The available users from the system and the attendees of the event are seperated into
   * two disjoint lists for viewing.
   *
   * @param existingEvent An event whose information should be displayed, and who should be
   *                      modified if the client requests so.
   */
  public FeaturesEventFrame(Event existingEvent, User currentUser) {
    if (existingEvent == null) {
      throw new IllegalArgumentException("The old event cannot be null");
    } else if (currentUser == null) {
      throw new IllegalArgumentException("The current user cannot be null");
    }

    // The host of the event must stay the host.
    this.oldEvent = existingEvent;
    this.host = existingEvent.getHost();
    this.currentUser = currentUser;

    // This Frame's Layout
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Set up what the content pane should look like for viewing an existing event.
    // As compared to creating an event, we can view the attendees of an event, see the current
    // host, and have different button options.
    setupEventPanel();
    setupLocationPanel();
    setupStartingTimePanel();
    setupEndingTimePanel();
    setupExistingEventPanel();
    setupAddUserPanel();
    setUpModifyRemoveEventPanel();

    // Fill the input fields with the data from the given event and pack the frame
    this.displayPreviousEvent();
    this.pack();
    this.setMinimumSize(this.getSize());
  }

  /**
   * When the User constructor is called, a new event frame will be created that
   * displays a GUI for creating a new event. The currentUser will be the host of any event
   * created, and is not shown in the list of available users as this is implied.
   */
  public FeaturesEventFrame(User currentUser) {
    if (currentUser == null) {
      throw new IllegalArgumentException("A new event requires a host");
    }

    // The host of the event will be the current user.
    this.oldEvent = null;
    this.host = currentUser;
    this.currentUser = currentUser;

    // This Frame's Layout
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Set up the content panel for creating an event. As opposed to viewing an event, we
    // do not need to see existing users, and do not need to modify or delete an event.
    setupEventPanel();
    setupLocationPanel();
    setupStartingTimePanel();
    setupEndingTimePanel();
    setupAddUserPanel();
    setupEventCreateButton();

    this.pack();
    this.setMinimumSize(this.getSize());
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void displayAvailableUsers(List<User> users) {
    // The host cannot be added through the available users in either scenario. reset the model
    users.remove(host);

    // If were are creating a new event, add all users to the systemUserList besides the current
    // user who must be the host
    if (this.oldEvent == null) {
      this.systemUserListModel.removeAllElements();
      this.systemUserListModel.addAll(users);
      this.pack();
      this.setMinimumSize(this.getSize());
      return;
    }

    // Prevent duplication with the existing event user list in the systemUserListModel
    for (User user : this.oldEvent.getAttendeesNoHost()) {
      users.remove(user);
    }
    this.systemUserListModel.removeAllElements();
    this.systemUserListModel.addAll(users);

    // Add the attendees of the events to their separate list
    this.eventUserListModel.removeAllElements();
    this.eventUserListModel.addAll(this.oldEvent.getAttendeesNoHost());

    // Select all users to attend the event in case of modification, and resize the window
    // as its y-height may have changed.
    this.eventUserList.addSelectionInterval(0, eventUserListModel.getSize());
    this.pack();
    this.setMinimumSize(this.getSize());
  }

  /**
   * Displays the details of the previous event in the event input components.
   *
   * @throws IllegalArgumentException if the old event is null.
   */
  private void displayPreviousEvent() throws IllegalArgumentException {
    if (this.oldEvent == null) {
      throw new IllegalArgumentException("The old event should not be null");
    }
    this.eventNameArea.append(oldEvent.getName());
    // Translate from the event details to the appropriate input components.

    // Set isOnline to show isOnline if the old event is online
    int isOnline = 1;
    if (oldEvent.isOnline()) {
      isOnline = 0;
    }
    this.isOnlineBox.setSelectedIndex(isOnline);
    this.locationInputField.setText(oldEvent.getLocation());

    WeekTime startTime = oldEvent.getEventTime().getStartTime();
    this.startDayBox.setSelectedItem(startTime.getWeekDay());
    this.startHourBox.setSelectedIndex(startTime.getHour());
    this.startMinBox.setSelectedIndex(startTime.getMinute());

    WeekTime endTime = oldEvent.getEventTime().getEndTime();
    this.endDayBox.setSelectedItem(endTime.getWeekDay());
    this.endHourBox.setSelectedIndex(endTime.getHour());
    this.endMinBox.setSelectedIndex(endTime.getMinute());

    this.eventUserListModel.removeAllElements();
    this.eventUserListModel.addAll(oldEvent.getAttendeesNoHost());
    this.eventUserList.addSelectionInterval(0, eventUserListModel.getSize());
  }

  /**
   * Builds the event from the info currently passed to the view.
   */
  private void buildEvent() {
    boolean isOnline = false;
    if (isOnlineBox.getSelectedIndex() == 0) {
      isOnline = true;
    }
    // This ugly code simply pulls from the correct input components. Build is not called!
    eventBuilder
            .reset()
            .addAttendees(systemUserList.getSelectedValuesList()).setHost(host)
            .addAttendees(eventUserList.getSelectedValuesList())
            .setLocation(locationInputField.getText())
            .setStartTime(new LocalWeekTime(WeekDay.values()[startDayBox.getSelectedIndex()],
                    startHourBox.getSelectedIndex(), startDayBox.getSelectedIndex()))
            .setEndTime(new LocalWeekTime(WeekDay.values()[endDayBox.getSelectedIndex()],
                    endHourBox.getSelectedIndex(), endDayBox.getSelectedIndex()))
            .setOnline(isOnline)
            .setName(eventNameArea.getText());


  }

  private void setUpModifyRemoveEventPanel() {
    // This function is only called when we are modifying or removing an event.
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(modifyButton);
    buttonPanel.add(removeButton);
    this.getContentPane().add(buttonPanel);

    // The behavior below should delegate to the controller in the future.
    // When the modify button is pressed, try to modify the event with the given parameters
    // If there is not enough information, submit a warning. This functionality will
    // be passed to the controller in future assignments. Dispose on completion.
    modifyButton.addActionListener(e -> {
      buildEvent();
      if (features.modifyEvent(oldEvent, eventBuilder)) {
        this.dispose();
      }
    });

    // When the remove button is called, if the currentUser is the host of the event,
    // we need to delete the event from everyone's calendar. We can't do this without a controller.
    // If the user is an attendee though, we can see the details of what the event would look like.
    removeButton.addActionListener(e -> {
      if (features.removeEvent(oldEvent, currentUser)) {
        this.dispose();
      }
    });
  }

  private void setupEventCreateButton() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(createButton);
    this.getContentPane().add(buttonPanel);

    // When the create button is pressed, try to create the event. If the event is created
    // successfully, dispose this window.
    createButton.addActionListener(e -> {
      buildEvent();
      if (features.addEvent(eventBuilder)) {
        this.dispose();
      }
    });
  }

  private void setupExistingEventPanel() {
    // Behavior that is specific to an existing event, which includes displaying the host
    // and the attendees of the event.

    JPanel hostPanel = new JPanel();
    hostPanel.setLayout(new GridLayout(1, 2, 8, 5));
    hostPanel.add(new JLabel("Host:                                              "));
    hostPanel.add(new JLabel(host.getUsername()));


    JPanel existingEventPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    existingEventPanel.add(new JLabel("Event Attendees:"));
    existingEventPanel.add(eventUserList);

    this.getContentPane().add(hostPanel);
    this.getContentPane().add(existingEventPanel);
  }

  private void setupAddUserPanel() {
    // Shows the JList that displays all available users in the system.
    JPanel systemUserPanel = new JPanel(new GridLayout(2, 1, 0, 0));
    systemUserPanel.add(new JLabel("Available users"));
    systemUserPanel.add(systemUserList);
    this.getContentPane().add(systemUserPanel);
  }

  private void setupEndingTimePanel() {
    JPanel endDayPanel = new JPanel(new GridLayout(2, 2, 5, 1));

    // Ending day and time
    endDayPanel.add(new JLabel("Ending Day:"));
    endDayPanel.add(endDayBox);
    endDayPanel.add(new JLabel("Ending time: "));

    // Use combo boxes to make selecting a time easier to parse and easier to execute.
    JPanel timePanel = new JPanel();
    timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
    timePanel.add(endHourBox);
    timePanel.add(new JLabel(":"));
    timePanel.add(endMinBox);

    // Add all time options
    endDayPanel.add(timePanel);
    this.getContentPane().add(endDayPanel);
    for (Integer hour = 0; hour < 24; hour++) {
      endHourBox.addItem(hour);
    }
    for (Integer min = 0; min < 60; min++) {
      endMinBox.addItem(min);
    }
  }

  private void setupStartingTimePanel() {
    JPanel startingTimePanel = new JPanel(new GridLayout(2, 2, 5, 5));

    // Starting day and time
    startingTimePanel.add(new JLabel("Starting Day:"));
    startingTimePanel.add(startDayBox);
    startingTimePanel.add(new JLabel("Starting time: "));

    // Use combo boxes to make selecting a time easier to parse and easier to execute.
    JPanel timePanel = new JPanel();
    timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
    timePanel.add(startHourBox);
    timePanel.add(new JLabel(":"));
    timePanel.add(startMinBox);

    startingTimePanel.add(timePanel);
    this.getContentPane().add(startingTimePanel);

    // Add all time options
    for (Integer hour = 0; hour < 24; hour++) {
      startHourBox.addItem(hour);
    }
    for (Integer min = 0; min < 60; min++) {
      startMinBox.addItem(min);
    }
  }


  private void setupLocationPanel() {
    // Location Panel
    JLabel location = new JLabel("Location:");
    location.setHorizontalAlignment(SwingConstants.LEFT);
    JPanel locationLabel = new JPanel(new GridLayout(2, 1, 0, 0));
    locationLabel.setAlignmentY(Component.LEFT_ALIGNMENT);
    locationLabel.add(location);

    // Location Panel
    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.X_AXIS));
    locationPanel.add(isOnlineBox);
    locationPanel.add(locationInputField);

    this.getContentPane().add(locationLabel);
    this.getContentPane().add(locationPanel);

  }

  private void setupEventPanel() {
    // Event Panel
    JPanel eventNamePanel = new JPanel(new GridLayout(3, 1, 5, 5));
    eventNamePanel.add(new JLabel("Event name:"));
    eventNamePanel.add(eventNameArea);
    this.getContentPane().add(eventNamePanel);
  }
}
