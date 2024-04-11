package view;

import controller.ScheduleSystem;
import model.Event;
import model.Location;
import model.ReadOnlyPlannerModel;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class EventDurationFrame extends JFrame implements EvtFrame, ScheduleSystemView {
  private final ReadOnlyPlannerModel model;
  private User host;
  private final JPanel mainPanel;
  private JTextField eventText;
  private JTextField durTime;
  private JTextField place;
  private JComboBox<String> onlineBox;
  private JList<String> usersBox;
  private JButton schEvent;
  private final JPanel usersTag;// = new JPanel();
  private JLabel availUsers;// = new JLabel("\tAvailable Users: ");
  private GridLayout gridLayout;// = new GridLayout(0, 1);
  private JScrollPane scrollPane;// = new JScrollPane();

//  private JList usersBox;

  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the name,
   * the location, starting day, starting time, ending day, ending time, and the list of attendees
   * for an event to be added, modified, or removed from the schedule.
   *
   * @param model
   */

  public EventDurationFrame(ReadOnlyPlannerModel model) {
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

    onlinePanel();
    durationPanel();
//    availableUsersPanel();
    createEventButton();

    add(mainPanel);
  }

  @Override
  public void setHost(User host) {
    this.host = host;
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
    durTime = new JTextField();
    durInput.add(durTime);

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
    this.refresh();
    usersTag.setLayout(gridLayout);

    usersTag.add(availUsers);

    mainPanel.add(usersTag);

    String[] allUsers = this.model.users().toArray(new String[0]);

    usersBox = new JList<>(allUsers);
    scrollPane.setViewportView(usersBox);
    usersBox.setLayoutOrientation(JList.VERTICAL);

    mainPanel.add(scrollPane);
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
    schEvent.addActionListener(e -> {
          try {
            String eventName = eventText.getText();
            Location loc = new Location(getOnlineBool(Objects.requireNonNull(onlineBox.getSelectedItem())),
                place.getText());
            int duration = Integer.parseInt(durTime.getText());
            List<User> listUsers = getUsers(usersBox.getSelectedValuesList());
            listener.scheduleEvent(eventName, loc, duration, listUsers);
            this.hidePanel();
          } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Cannot schedule event");
          }
        }
    );
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
    return onlineStr.equals("true");
  }

  @Override
  public void refresh() {
    repaint();
  }

  public void resetFrame() {
    eventText.setText("");
    place.setText("");
    durTime.setText("");

    // add select users
    this.availableUsersPanel();
    this.makeVisible();
  }

  @Override
  public void addDefaultEvent(Event event) {
    this.refresh();
  }

  @Override
  public void addSelectedUser(User user) {
    //
  }

  @Override
  public void getUnmodifiedEvent(Event event) {
    //
  }
}
