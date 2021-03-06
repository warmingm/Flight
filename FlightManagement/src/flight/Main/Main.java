package flight.Main;

import flight.assist.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * 程序主入口
 */
public class Main extends JFrame {
  private Interface mainInterface;
  public Main() {
    mainInterface = new Interface();
    this.getContentPane().add(mainInterface);
    this.addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        DataBaseManager dbManager = DataBaseManager.getInstance();
        dbManager.closeConnection();
      }
    });
  }

  public static void main(String args[]) {
    //Get the System's look for the GUI
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) {
    }

    Main frame = new Main();
    frame.setSize(570, 440);
    frame.setResizable(false);
    frame.setTitle("航班查询与定票系统");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation( (d.width - frame.getSize().width) / 2,
                      (d.height - frame.getSize().height) / 2);
    frame.show();

  }
}
