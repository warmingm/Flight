package flight.query;

import javax.swing.*;
import javax.swing.border.*;
/**
 * 查询航班信息的主窗口
 */
public class Query
    extends JFrame{
  public Query() {
    JTabbedPane jtp = new JTabbedPane();
    jtp.add(" 普 通 查 询 ", new CommonQuery());
    jtp.add(" 综 合 查 询", new ComprehenQuery());

    jtp.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    this.getContentPane().add(jtp);

    this.setSize(470, 370);
    this.setTitle("航班查询系统");
  }
} ///:~
