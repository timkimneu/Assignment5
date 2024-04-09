package view;

import controller.ScheduleSystem;
import model.Event;
import model.ReadOnlyPlannerModel;

import javax.swing.*;
import java.awt.*;

public class EventDurationFrame extends JFrame implements EvtFrame {
  private final ReadOnlyPlannerModel model;
  private final JPanel mainPanel;
  private JTextField eventText;
  private JTextField place;
  private JComboBox<String> onlineBox;
  private JList<String> usersBox;
  private JButton schEvent;

  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the name,
   * the location, starting day, starting time, ending day, ending time, and the list of attendees
   * for an event to be added, modified, or removed from the schedule.
   *
   * @param model
   */

  public EventDurationFrame(ReadOnlyPlannerModel model) {
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

    onlinePanel();
    durationPanel();
    availableUsersPanel();
    createEventButton();

    add(mainPanel);
  }

  private void createEventButton() {
    JPanel bottomButtons = new JPanel();
    bottomButtons.setLayout(new GridLayout(1, 5));
    schEvent = new JButton("Schedule event");
    schEvent.setActionCommand("Schedule event");
    bottomButtons.add(schEvent);

    this.add(bottomButtons, BorderLayout.PAGE_END);
  }

  private void durationPanel() {
    JPanel dur = new JPanel();
    dur.setLayout(new GridLayout(0, 1));
    dur.add(new JLabel("Duration in minutes"));

    JPanel durInput = new JPanel();
    durInput.setLayout(new GridLayout(0, 1));
    durInput.add(dur);
    durInput.add(new JTextField());

    mainPanel.add(durInput);
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
  public void addListener(ScheduleSystem listener) {
//    schEvent.addActionListener(e -> {
//          try {
//            listener.addEvent();
//            this.hidePanel();
//          } catch (IllegalArgumentException ex) {
//            JOptionPane.showMessageDialog(null, "Cannot create event");
//            System.out.println(ex.getMessage());
//          }
//        }
//    );
  }

  @Override
  public void addDefaultEvent(Event event) {

  }
}
