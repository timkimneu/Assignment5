package provider.view;

import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import provider.controller.Features;
import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.ReadOnlySchedule;
import provider.model.User;
import provider.view.CentralSystemView;
import provider.view.SchedulePanel;

/**
 * This class is an implementation of the CentralSystemView interface that uses Java Swing.
 * Thus, it also extends the JFrame class as the parent window within the GUI. This View
 * delegates to a SchedulePanel (that should also be a JPanel) in order to display the schedule.
 * This allows for modularity in what the view looks like, while retaining consistent
 * input functionality for viewing different schedules and creating events. This class delegates
 * to a features interface to perform functionality.
 *
 * @param <T> A class that is a panel and implements the SchedulePanel interface. This allows
 *            for the LocalViewFrame to delegate to a SchedulePanel to draw the schedule portion of
 *            the view.
 */
public class FeaturesViewFrame<T extends JPanel & SchedulePanel>
        extends JFrame implements CentralSystemView {

  // The panel to which schedule drawing and input is delegated.
  private final T schedule;

  // Stores the file menu for reading and writing files
  private final JMenu fileMenuOptions;

  // Stores the users who can be viewed in the system
  private final DefaultComboBoxModel<User> userOptionsList;
  private final JComboBox<User> userOptions;


  // Can be used to generate a window to create an event
  private final JButton createEvent;

  // Can be used to generate a window to create an event
  private final JButton scheduleEvent;

  // To delegate to for functionality
  private Features features;

  private final EventBuilder builder;

  /**
   * Constructs a new LocalScheduleView with a given model and schedule panel. The model
   * enables this view to work without a controller, and the schedule is a panel for which
   * schedule drawing can be delegated.
   *
   * @throws IllegalArgumentException if any given argument is null
   */
  public FeaturesViewFrame(T schedule, EventBuilder builder)
          throws IllegalArgumentException {
    // Check for null arguments
    if (schedule == null) {
      throw new IllegalArgumentException("Schedule cannot be null.");
    } else if (builder == null) {
      throw new IllegalArgumentException("Builder cannot be null");
    }

    this.builder = builder;

    // Set up file menu
    JMenuBar menuBar = new JMenuBar();
    fileMenuOptions = new JMenu("File");
    menuBar.add(fileMenuOptions);
    this.setJMenuBar(menuBar);

    // Set up schedule panel in the center of the layout
    this.schedule = schedule;
    this.add(schedule, BorderLayout.CENTER);

    // Set up bottom bar with user selection and event creation
    Panel bottomOptions = new Panel();
    bottomOptions.setLayout(new BoxLayout(bottomOptions, BoxLayout.X_AXIS));

    userOptionsList = new DefaultComboBoxModel<>();
    userOptions = new JComboBox<>(userOptionsList);
    createEvent = new JButton("Create an Event");
    scheduleEvent = new JButton("Schedule an Event");

    bottomOptions.add(new JLabel("View a Schedule:"));
    bottomOptions.add(userOptions);
    bottomOptions.add(createEvent);
    bottomOptions.add(scheduleEvent);
    this.add(bottomOptions, BorderLayout.PAGE_END);

    // Set the initial dimensions and minimum size
    int width = 575;
    this.setSize(width, 450);
    schedule.setSize(width, 400);
    bottomOptions.setSize(width, 50);
    this.setMinimumSize(new Dimension(width, 450));

    // Set up the action behavior of the interactive features
    this.menuSetup();
    this.userSelectionSetup();
    this.eventCreationSetup();
    this.scheduleEventSetup();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void displaySchedule(ReadOnlySchedule schedule) {
    if (schedule == null) {
      throw new IllegalArgumentException("Schedule cannot be null");
    }

    this.schedule.displaySchedule(schedule);
  }

  /**
   * Creates a separate window showing the given error dialog.
   *
   * @param text Text containing the error message.
   */
  @Override
  public void displayError(String text) {
    JOptionPane.showMessageDialog(null, text);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
    this.schedule.setVisible(show);
  }

  @Override
  public void addFeatures(Features features) {
    // Must set features for the given schedule as well so it can perform upon being clicked
    this.features = features;
    this.schedule.addFeatures(features);
  }


  @Override
  public void displayExistingEvent(Event existingEvent) {
    if (existingEvent == null) {
      throw new IllegalArgumentException("The given event cannot be null.");
    }

    // Create a new event frame from looking at the existing event with the current selected
    // user.
    FeaturesEventFrame event = new FeaturesEventFrame(existingEvent,
            userOptionsList.getElementAt(userOptions.getSelectedIndex()), builder);
    event.addFeatures(features);
    event.displayAvailableUsers(getListOfAvailableUsers());
    event.setVisible(true);
  }

  @Override
  public void displayAvailableUsers(List<User> users) {
    this.displayAvailableUsersHelp(users);
  }

  @Override
  public User currentSelectedUser() {
    // userOptions presents the data within userOptionsList. The indexes are guaranteed
    // to match up properly.
    return userOptionsList.getElementAt(userOptions.getSelectedIndex());
  }

  /**
   * Sets up the file menu bar such that it can open file navigation windows for reading and
   * writing XML.
   */
  private void menuSetup() {
    // Add menu options
    JMenuItem read = new JMenuItem("Read a File");
    JMenuItem write = new JMenuItem("Write to File");
    fileMenuOptions.add(read);
    fileMenuOptions.add(write);

    // Set functionality for reading a file
    read.addActionListener(e -> {
      // Use a JFileChooser to get a filename, filtering for only XML
      JFileChooser chooser = new JFileChooser();
      chooser.setFileFilter(
              new FileNameExtensionFilter("XML", "xml"));

      // If the file selection is approved, delegate to features for behavior
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        features.readXML(chooser.getSelectedFile().getAbsolutePath());
      }
    });

    write.addActionListener(e -> {
      // Use a JFileChooser to get a Directory. Set to directory selection mode and prevent
      // non directory files from being seen.
      JFileChooser chooser = new JFileChooser();
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(false);

      // If a directory is chosen, print the path to the directory
      // In the future this will delegate to the controller to write all the files.
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        features.writeXML(chooser.getSelectedFile().getAbsolutePath());
      }
    });
  }

  /**
   * Sets up the Event creation button by adding event listeners. On click, this enables
   * an event creation window for the current user without a previously existing event.
   * This listener does not need to delegate to the features in any way. Action that requires
   * synchronization with the model and other potential views (adding an event) will be handled
   * by the EventFrame.
   */
  private void eventCreationSetup() {
    // Make a new event frame with the current selected user on click
    this.createEvent.addActionListener(e -> {
      // Make a new LocalEventFrame where the current selected user will be the host upon
      // completion.
      if (userOptions.getSelectedIndex() == -1) {
        return;
      }

      FeaturesEventFrame event =
              new FeaturesEventFrame(userOptionsList.getElementAt(userOptions.getSelectedIndex()),
                      builder);
      event.addFeatures(features);
      event.displayAvailableUsers(getListOfAvailableUsers());
      event.setVisible(true);
    });
  }

  /**
   * Sets up the schedule event button by adding event listeners. On click, this enables
   * an event creation window for the current user without a previously existing event.
   * This listener does not need to delegate to the features in any way. Action that requires
   * synchronization with the model and other potential views (scheduling an event) will be handled
   * by the SchedulingFrame.
   */
  private void scheduleEventSetup() {
    // Make a new event frame with the current selected user on click
    this.scheduleEvent.addActionListener(e -> {
      // Make a new LocalScheduleFrame where the current selected user will be the host upon
      // completion
      if (userOptions.getSelectedIndex() == -1) {
        return;
      }

      FeaturesScheduleFrame event =
              new FeaturesScheduleFrame(
                      userOptionsList.getElementAt(userOptions.getSelectedIndex()), builder);
      event.addFeatures(features);
      event.displayAvailableUsers(getListOfAvailableUsers());
      event.setVisible(true);
    });
  }

  /**
   * Sets up the user selection combo box by adding event listeners. Uses the fact that
   * the items contained by the user combo box and the user list should be the same in order
   * to figure out which schedule to display. Will delegate to features in the future.
   */
  private void userSelectionSetup() {
    userOptions.addActionListener(e -> {
      features.displayCurrentSchedule();
    });
  }

  /**
   * Updates the userOptions ComboBoxModel in order to change the visible users.
   *
   * @param users Users to display in the combo box
   */
  private void displayAvailableUsersHelp(List<User> users) {
    // Clear the current stored users so that this method sets rather than adds.
    // Hold on to the selected user.
    User selectedUser = userOptionsList.getElementAt(userOptions.getSelectedIndex());
    this.userOptionsList.removeAllElements();
    this.userOptionsList.addAll(users);
    this.userOptions.setSelectedItem(selectedUser);
  }

  @Override
  public List<User> getListOfAvailableUsers() {
    List<User> sendUsers = new ArrayList<>();
    for (int userIdx = 0; userIdx < userOptionsList.getSize(); userIdx++) {
      sendUsers.add(userOptionsList.getElementAt(userIdx));
    }

    return sendUsers;
  }
}
