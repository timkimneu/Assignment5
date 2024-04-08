package view;

import controller.ScheduleSystem;
import model.ReadOnlyPlannerModel;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.List;

/**
 * Represents the GUI for the schedule of a single user, but allowing for the selection of
 * different users to view the appropriate schedule pertaining to that user.
 */
public class ScheduleFrame extends JFrame implements ScheduleSystemView, SchFrame {

  private final ReadOnlyPlannerModel model;
  private final SchedulePanel panel;
  private EventFrame eventFrame;
  private JFileChooser fchooser = new JFileChooser(".");
  private JMenuItem addCalendar;
  private JMenuItem saveCalendar;
  private JButton createEvent;
  private JButton schEvent;

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

    JMenuBar bar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    addCalendar = fileMenu.add("Add calendar");
    saveCalendar = fileMenu.add("Save calendars");

    addCalendar.setActionCommand("Add calendar");

    saveCalendar.setActionCommand("Save calendar");

    bar.add(fileMenu);
    this.setJMenuBar(bar);

    eventButtonListener();
  }

  // Adds event listeners for the buttons on the schedule. Uses lambda
  // functionality to implement these commands.
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
      }
    }
    );

    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    createEvent = new JButton("Create event");
    createEvent.setActionCommand("Create event");

    schEvent = new JButton("Schedule event");
    schEvent.setActionCommand("Schedule event");

    bottomButtons.add(listUsers);
    bottomButtons.add(createEvent);
    bottomButtons.add(schEvent);
    this.add(bottomButtons, BorderLayout.PAGE_END);
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
  public void addListener(ScheduleSystem listener) {
    eventFrame.addListener(listener);
    panel.addListener(listener);

    addCalendar.addActionListener(e -> {
      int retvalue = fchooser.showOpenDialog(ScheduleFrame.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        System.out.println(f);
//        listener.readXML(f.getPath());
      }
    }
    );
//    saveCalendar.addActionListener(e -> {
//      int retvalue2 = fchooser.showOpenDialog(ScheduleFrame.this);
//      if (retvalue2 == JFileChooser.APPROVE_OPTION) {
//        File f = fchooser.getSelectedFile();
//        listener.writeXML(f., "");
//      }
//    }
//    );

    createEvent.addActionListener(e -> eventFrame.setVisible(true));
    schEvent.addActionListener(e -> eventFrame.setVisible(true));
  }

  @Override
  public void refresh() {
    repaint();
  }
}
