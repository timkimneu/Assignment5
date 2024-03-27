package view;

import model.Event;
import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.Time;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 *
 */
public class ScheduleFrame extends JFrame implements ScheduleSystemView, ActionListener {

  private final ReadOnlyPlannerModel model;
  private final SchedulePanel panel;
  private EventFrame eventFrame;
  private JFileChooser fchooser = new JFileChooser(".");

  /**
   *
   * @param model
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
    addCalendar.addActionListener(this);

    saveCalendar.setActionCommand("Save calendar");
    saveCalendar.addActionListener(this);

    bar.add(fileMenu);
    this.setJMenuBar(bar);

    JPanel listUsers = new JPanel();
    listUsers.setLayout(new BoxLayout(listUsers, BoxLayout.PAGE_AXIS));
    List<String> users = model.users();
    JComboBox<String> userBox = new JComboBox<>();
    for (int i = 0; i < users.size(); i++) {
      userBox.addItem(users.get(i));
    }
    listUsers.add(userBox);
    userBox.setActionCommand("User schedule");
    userBox.addActionListener(this);

    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));

    JButton createEvent = new JButton("Create event");
    createEvent.setActionCommand("Create event");
    createEvent.addActionListener(this);

    JButton schEvent = new JButton("Schedule event");
    schEvent.setActionCommand("Schedule event");
    schEvent.addActionListener(this);

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


  @Override
  public void actionPerformed(ActionEvent e) {
    switch(e.getActionCommand()) {
      case "Add calendar":
        int retvalue = fchooser.showOpenDialog(ScheduleFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        System.out.println(f);
        }
      case "Save calendar":
        //final JFileChooser schooser = new JFileChooser(".");
        int retvalue2 = fchooser.showOpenDialog(ScheduleFrame.this);
        if (retvalue2 == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          System.out.println(f);
        }
      case "Create event":
        //EventFrame frame = new EventFrame();
        eventFrame.setVisible(true);
      case "Schedule event":
        //EventFrame eventFrame = new EventFrame();
        eventFrame.setVisible(true);
      case "User schedule":
        if (e.getSource() instanceof JComboBox) {
          JComboBox<String> user = (JComboBox<String>) e.getSource();
          //System.out.println((String) user.getSelectedItem());
          List<SchedulePlanner> listSch = model.schedules();

          for (int sch = 0; sch < listSch.size(); sch++) {
            SchedulePlanner currSch = listSch.get(sch);
            List<model.Event> listEvents = currSch.events();
            for (int event = 0; event < listEvents.size(); event++) {
              Event currEvent = listEvents.get(event);
              Time currTime = currEvent.time();

            String userStr = (String) user.getSelectedItem();
            User selectedUser = new User(userStr);
            panel.drawDates(userStr);
           // panel.drawDates((String) user.getSelectedItem());
              //panel.fillDates(currTime.startTime(), currTime.endTime(),
               //  currTime.startDay(), currTime.endDay());

            }
          }
        }
    }
  }

}
