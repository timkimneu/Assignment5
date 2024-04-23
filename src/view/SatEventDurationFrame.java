package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import controller.ScheduleSystem;
import model.IEvent;
import model.IReadOnlyPlannerModel;
import model.LocationImpl;
import model.SatDOTW;
import model.UserImpl;

public class SatEventDurationFrame extends JFrame implements EvtFrame<SatDOTW>,
        ScheduleSystemView<SatDOTW> {
  private final IReadOnlyPlannerModel<SatDOTW> model;
  private final JPanel mainPanel;
  private final JTextField eventText;
  private JTextField durTime;
  private JTextField place;
  private JComboBox<String> onlineBox;
  private JList<String> usersBox;
  private JButton schEvent;
  private final JPanel usersTag;// = new JPanel();
  private final JLabel availUsers;// = new JLabel("\tAvailable Users: ");
  private final GridLayout gridLayout;// = new GridLayout(0, 1);
  private final JScrollPane scrollPane;// = new JScrollPane();


  /**
   * Constructor of the event frame. Sets the dimension of the frame and asks user for the name,
   * the location, starting day, starting time, ending day, ending time, and the list of attendees
   * for an event to be added, modified, or removed from the schedule.
   *
   * @param model Model to get to observe from to appropriately autofill boxes with.
   */

  public SatEventDurationFrame(IReadOnlyPlannerModel<SatDOTW> model) {
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
    durTime = new JTextField();
    durInput.add(durTime);

    mainPanel.add(durInput);
  }

  private void onlinePanel() {
    JPanel online = new JPanel();
    online.setLayout(new BoxLayout(online, BoxLayout.PAGE_AXIS));
    String[] options = {"Is online", "Is not online"};

    onlineBox = new JComboBox<>();
    for (String s : options) {
      onlineBox.addItem(s);
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

    String[] allUsers = (String[]) this.model.users().toArray(new String[0]);

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
  public void addListener(ScheduleSystem<SatDOTW> listener) {
    schEvent.addActionListener(e -> {
              try {
                String eventName = eventText.getText();
                LocationImpl loc = new LocationImpl(getOnlineBool(Objects.requireNonNull(
                        onlineBox.getSelectedItem())), place.getText());
                int duration = Integer.parseInt(durTime.getText());
                java.util.List<UserImpl> listUsers = getUsers(usersBox.getSelectedValuesList());
                listener.scheduleEvent(eventName, loc, duration, listUsers);
                this.hidePanel();
              } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Cannot schedule event");
              }
            }
    );
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
    durTime.setText("");

    // add select users
    this.availableUsersPanel();
    this.makeVisible();
  }

  @Override
  public void addDefaultEvent(IEvent<SatDOTW> event) {
    this.refresh();
  }

  @Override
  public void addSelectedUser(UserImpl user) {
    //
  }

  @Override
  public void getUnmodifiedEvent(IEvent<SatDOTW> event) {
    //
  }
}