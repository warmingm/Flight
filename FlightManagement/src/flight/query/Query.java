package flight.query;

import javax.swing.*;
import javax.swing.border.*;
/**
 * ��ѯ������Ϣ��������
 */
public class Query
    extends JFrame{
  public Query() {
    JTabbedPane jtp = new JTabbedPane();
    jtp.add(" �� ͨ �� ѯ ", new CommonQuery());
    jtp.add(" �� �� �� ѯ", new ComprehenQuery());

    jtp.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    this.getContentPane().add(jtp);

    this.setSize(470, 370);
    this.setTitle("�����ѯϵͳ");
  }
} ///:~