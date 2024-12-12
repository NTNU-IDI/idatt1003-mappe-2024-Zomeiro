package edu.ntnu.idatt;

import edu.ntnu.idatt.userinterface.UserInterface;


/**
 * Class handling user interface.
 */
public class Main {

  /**
   * The main method to start the UserInterface program.
   *
   * @param args The command-line arguments.
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}