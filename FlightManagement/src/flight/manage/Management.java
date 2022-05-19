package flight.manage;

import javax.swing.*;
import javax.swing.border.*;
/**
 * 管理航班信息的主窗口
 */
public class Management
    extends JFrame {
  private JTabbedPane tab = new JTabbedPane();

  public Management() {
    tab.add(new InsertPanel(), "    插   入    ");
    tab.add(new DeletePanel(), "    删   除    ");
    tab.add(new UpdatePanel(), "    更   新    ");
    tab.add(new FlightList(), "  查 看 数 据 库  ");
    tab.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    getContentPane().add(tab);
    this.setSize(470, 370);
    this.setResizable(false);
    this.setTitle("航班管理系统");
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
  }
}
