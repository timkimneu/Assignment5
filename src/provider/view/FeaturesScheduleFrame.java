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
import provider.model.EventBuilder;
import provider.model.User;
//import provider.model.impl.LocalEventBuilder;
import provider.view.EventFrame;

/**
 * This class visualizes the information necessary to schedule an event
 * using Java Swing. It takes input necessary to schedule an event in a manner defined by
 * a given set of features. This class is initially invisible. Users should be added to this
 * class using the displayAvailableUsers method before it is set visible.
 *
 * <p>This class remembers the user that was being viewed when the Frame was opened. If the schedule
 * being viewed changes, any actions taken by the features frame will be taken with the old
 * user in mind, including adding, removing, or modifying events.
 */
public class FeaturesScheduleFrame extends JFrame implements EventFrame {
  // Various JComponents that are used for taking inputs.
  private final JTextArea eventNameArea = new JTextArea();
  private final JComboBox<String> isOnlineBox =
          new JComboBox<>(new String[]{"is Online", "is in Person"});
  private final JTextField locationInputField = new JTextField();
  private final JComboBox<Integer> durationBox = new JComboBox<>();

  // Contains the users who can be added to an event who are not in the current event, if it exists
  DefaultListModel<User> systemUserListModel = new DefaultListModel<>();
  private final JList<User> systemUserList = new JList<User>(systemUserListModel);

  private final JButton scheduleButton = new JButton("Schedule event");

  // The current user of the schedule, to be kept track of for creating a new event or removing one
  private final User currentUser;

  // To build new events with
  private final EventBuilder eventBuilder = new LocalEventBuilder();
  private Features features;

  /**
   * When the User constructor is called, a new event frame will be created that
   * displays a GUI for creating a new event. The currentUser will be the host of any event
   * created, and is not shown in the list of available users as this is implied.
   */
  public FeaturesScheduleFrame(User currentUser) {
    if (currentUser == null) {
      throw new IllegalArgumentException("A new event requires a host");
    }

    this.currentUser = currentUser;

    // This Frame's Layout
    this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Set up the content panel for creating an event. As opposed to viewing an event, we
    // do not need to see existing users, and do not need to modify or delete an event.
    setupEventPanel();
    setupLocationPanel();
    setupDurationPanel();
    setupAddUserPanel();
    setupScheduleButton();

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
    users.remove(currentUser);
    this.systemUserListModel.removeAllElements();
    this.systemUserListModel.addAll(users);
    this.pack();
    this.setMinimumSize(this.getSize());
  }

  private void setupDurationPanel() {
    JPanel endDayPanel = new JPanel(new GridLayout(2, 2, 5, 1));

    // Scheduling label
    endDayPanel.add(new JLabel("Event Duration in Minutes:"));
    endDayPanel.add(durationBox);


    this.getContentPane().add(endDayPanel);
    for (Integer minute = 1; minute < 24 * 60 * 7; minute++) {
      durationBox.addItem(minute);
    }
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
            .addAttendees(systemUserList.getSelectedValuesList())
            .setHost(currentUser)
            .setLocation(locationInputField.getText())
            .setOnline(isOnline)
            .setName(eventNameArea.getText());


  }

  private void setupScheduleButton() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(scheduleButton);
    this.getContentPane().add(buttonPanel);

    // When the create button is pressed, try to create the event. If the event is created
    // successfully, dispose this window.
    scheduleButton.addActionListener(e -> {
      buildEvent();
      if (features.scheduleEvent(eventBuilder, durationBox.getSelectedIndex() + 1)) {
        this.dispose();
      }
    });
  }

  private void setupAddUserPanel() {
    // Shows the JList that displays all available users in the system.
    JPanel systemUserPanel = new JPanel(new GridLayout(2, 1, 0, 0));
    systemUserPanel.add(new JLabel("Available users"));
    systemUserPanel.add(systemUserList);
    this.getContentPane().add(systemUserPanel);
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
