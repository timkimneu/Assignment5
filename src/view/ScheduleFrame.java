package view;

import model.ReadOnlyPlannerModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Represents the GUI for the schedule of a single user, but allowing for the selection of
 * different users to view the appropriate schedule pertaining to that user.
 */
public class ScheduleFrame extends JFrame implements ScheduleSystemView {

  private final ReadOnlyPlannerModel model;
  private final SchedulePanel panel;
  private EventFrame eventFrame;
  private JFileChooser fchooser = new JFileChooser(".");

  /**
   * Initializes the frame observing information provided by the given model, setting the main
   * window and allowing for basic functionality to view the schedule planner. Has buttons to
   * import XML files to add to schedule and buttons to add, modify, and remove events.
   *
   * @param model Model to observe info from to display in view (GUI).
   */
  public ScheduleFrame(ReadOnlyPlannerModel model) {
    super();
    this.model = model;
    this.eventFrame  = new EventFrame(model);
    this.setSize(550, 610);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new SchedulePanel(model);
    this.setLayout(new BorderLayout());
    this.add(panel);
    this.setResizable(false);

    JMenuBar bar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem addCalendar = fileMenu.add("Add calendar");
    JMenuItem saveCalendar = fileMenu.add("Save calendars");

    addCalendar.setActionCommand("Add calendar");
    addCalendar.addActionListener(e -> {
      int retvalue = fchooser.showOpenDialog(ScheduleFrame.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        System.out.println(f);
      }});

    saveCalendar.setActionCommand("Save calendar");
    saveCalendar.addActionListener(e -> {int retvalue2 = fchooser.showOpenDialog(ScheduleFrame.this);
      if (retvalue2 == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        System.out.println(f);
      }});

    bar.add(fileMenu);
    this.setJMenuBar(bar);

    eventButtonListener();
  }

  private void eventButtonListener() {
    JPanel listUsers = new JPanel();
    listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.PAGE_AXIS));
    List<String> users = model.users();
    JComboBox<String> userBox = new JComboBox<>();
    userBox.addItem("<none>");
    for (int i = 0; i < users.size(); i++) {
      userBox.addItem(users.get(i));
    }
    listUsers.add(userBox);
    userBox.setActionCommand("User schedule");
    userBox.addActionListener(e -> {
      if (e.getSource() instanceof JComboBox) {
        JComboBox<String> user = (JComboBox<String>) e.getSource();
        String userStr = (String) user.getSelectedItem();
        panel.drawDates(userStr);
      }});

    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    JButton createEvent = new JButton("Create event");
    createEvent.setActionCommand("Create event");
    createEvent.addActionListener(e -> eventFrame.setVisible(true));

    JButton schEvent = new JButton("Schedule event");
    schEvent.setActionCommand("Schedule event");
    schEvent.addActionListener(e -> eventFrame.setVisible(true));

    bottomButtons.add(listUsers);
    bottomButtons.add(createEvent);
    bottomButtons.add(schEvent);
    this.add(bottomButtons, BorderLayout.PAGE_END);
  }

  @Override
  public String schedulesToString() {
    return null;
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
