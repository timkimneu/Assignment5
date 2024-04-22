//package view;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class DrawEventDecorator {
//  private final SchedulePanel schPanel;
//  private boolean toggleColor;
//
//  public DrawEventDecorator(SchedulePanel schPanel) {
//    this.schPanel = schPanel;
//    this.toggleColor = false;
//
//    // Add toggle button to the panel (or frame)
//    JButton toggleButton = new JButton("Toggle Color");
//    toggleButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent e) {
////        toggleColor();
//        schPanel.repaint(); // Request the panel to repaint itself
//      }
//    });
//
//    schPanel.add(toggleButton, BorderLayout.SOUTH); // Adding the button to the bottom of the panel
//  }
//
////private void toggleColor() {
////  if (toggleColor) {
////    schPanel();
////  } else {
////    schPanel.setBackground();
////  }
////  toggleColor = !toggleColor;
////}
//}
