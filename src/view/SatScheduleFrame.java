package view;

import controller.ScheduleSystem;
import model.IReadOnlyPlannerModel;
import model.SatDOTW;
import model.UserImpl;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the interface for the schedule of a single user, but allowing
 * for the selection of different users to view the appropriate schedule
 * pertaining to that user.
 */
public class SatScheduleFrame extends JFrame implements
    ScheduleSystemView<SatDOTW>, SchFrame {
  private final IReadOnlyPlannerModel<SatDOTW> model;
  private final SatSchedulePanel panel;
  private final SatEventFrame eventFrame;
  private final SatEventDurationFrame durationFrame;
  private final JFileChooser fchooser = new JFileChooser(".");
  private final JMenuItem addCalendar;
  private final JMenuItem saveCalendar;
  private final JButton createEvent;
  private final JButton schEvent;
  private final JButton toggleEvent;
  private final JComboBox<String> userBox;
  private String userStr;
  private boolean host = false;


  /**
   * Initializes the frame observing information provided by the given model, setting the main
   * window and allowing for basic functionality to view the schedule planner. Has buttons to
   * import XML files to add to schedule and buttons to add, modify, and remove events.
   *
   * @param model Model to observe info from to display in view (GUI).
   */
  public SatScheduleFrame(IReadOnlyPlannerModel<SatDOTW> model) {
    super();
    this.model = model;
    this.eventFrame = new SatEventFrame(model);
    this.durationFrame = new SatEventDurationFrame(model);
    this.setSize(550, 610);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new SatSchedulePanel(model, this.eventFrame);
    this.setLayout(new BorderLayout());
    this.add(panel);


    JMenuBar bar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    addCalendar = fileMenu.add("Add calendar");
    saveCalendar = fileMenu.add("Save calendars");

    addCalendar.setActionCommand("Add calendar");
    saveCalendar.setActionCommand("Save calendar");

    bar.add(fileMenu);
    this.setJMenuBar(bar);
    JPanel listUsers = new JPanel();
    listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.PAGE_AXIS));
    userBox = new JComboBox<>(new String[]{"<none>"});
    java.util.List<String> users = model.users();
    for (String s : users) {
      userBox.addItem(s);
    }
    listUsers.add(userBox);

    createEvent = new JButton("Create event");
    createEvent.setActionCommand("Create event");

    schEvent = new JButton("Schedule event");
    schEvent.setActionCommand("Schedule event");

    toggleEvent = new JButton("Toggle event");
    toggleEvent.setActionCommand("Toggle event");

    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));
    bottomButtons.add(listUsers);
    bottomButtons.add(createEvent);
    bottomButtons.add(schEvent);
    bottomButtons.add(toggleEvent);
    this.add(bottomButtons, BorderLayout.PAGE_END);

    eventButtonListener();
  }

  // Adds event listeners for the buttons on the schedule. Uses lambda
  // functionality to implement these commands.
  private void eventButtonListener() {
    userBox.setActionCommand("User schedule");
    userBox.addActionListener(e -> {
      if (e.getSource() instanceof JComboBox) {
        JComboBox<String> user = (JComboBox<String>) e.getSource();
        userStr = (String) user.getSelectedItem();
        eventFrame.addSelectedUser(new UserImpl(userStr));
        panel.drawDates(userStr, host);
      }
    }
    );
  }

  private void updateUserBox() {
    java.util.List<String> currentUsers = new ArrayList<>();
    for (int user = 0; user < userBox.getItemCount(); user++) {
      currentUsers.add(userBox.getItemAt(user));
    }
    List<String> users = model.users();
    for (String s : users) {
      if (!currentUsers.contains(s)) {
        userBox.addItem(s);
      }
    }
  }

  // doesn't need to use this method, so returns null
  @Override
  public String schedulesToString() {
    return null;
  }

  // allows the schedule frame to be visible
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  // hides the schedules frame
  @Override
  public void hidePanel() {
    setVisible(false);
  }

  @Override
  public void addListener(ScheduleSystem<SatDOTW> listener) {
    eventFrame.addListener(listener);
    durationFrame.addListener(listener);
    panel.addListener(listener);

    addCalendar.addActionListener(e -> {
          int retvalue = fchooser.showOpenDialog(this);
          if (retvalue == JFileChooser.APPROVE_OPTION) {
            File f = fchooser.getSelectedFile();
            try {
              listener.readXML(f.getPath());
            } catch (IllegalArgumentException | NullPointerException ex) {
              JOptionPane.showMessageDialog(null, "Cannot add calendar");
            }
          }
        }
    );

    saveCalendar.addActionListener(e -> listener.writeXML(""));
    createEvent.addActionListener(e -> eventFrame.resetFrame());
    schEvent.addActionListener(e -> getDurationFrame().resetFrame());
    toggleEvent.addActionListener(e -> System.out.println("TOGGLE"));
    host = !host;
    panel.drawDates(userStr, host);
  }

  private SatEventDurationFrame getDurationFrame() {
    return durationFrame;
  }

  @Override
  public void refresh() {
    this.updateUserBox();
    eventFrame.refresh();
    repaint();
  }
}
