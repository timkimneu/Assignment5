package view;

import javax.swing.*;

public class EventFrame extends JFrame implements ScheduleSystemView {

  private JPasswordField pfield;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  public EventFrame() {
    super();

    setSize(400, 400);
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //text area
    JTextArea textArea = new JTextArea(2, 2);
    textArea.setBorder(BorderFactory.createTitledBorder("Event name:"));
    mainPanel.add(textArea);

    JPanel pPanel = new JPanel();
    pPanel.setBorder(BorderFactory.createTitledBorder("Using Password fields"));
    mainPanel.add(pPanel);

    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.PAGE_AXIS));
    pfield = new JPasswordField(5);
    pPanel.add(pfield);

  }

  @Override
  public String schedulesToString() {
    return null;
  }
}
