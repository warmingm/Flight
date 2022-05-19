package flight.manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import flight.assist.DataBaseManager;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
/**
 * �鿴������Ϣ�б�
 */
public class FlightList
    extends JPanel
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JTextField textField = new JTextField(10);
  private JPasswordField password = new JPasswordField(10);
  private JLabel label1 = new JLabel("�˺�");
  private JLabel label2 = new JLabel("����");
  private JButton button = new JButton("�鿴");
  private JTable table = null;
  private DefaultTableModel defaultModel = null;
  private JScrollPane tablePane;

  public FlightList() {
    String[] name = {
        "�����", "��˾", "��ɵ�", "Ŀ�ĵ�", "���ʱ��", "����ʱ��", "��ͯƱ��", "����Ʊ��", "�ۿ�", "��Ʊ��","����"};
    String[][] data = new String[0][0];
    defaultModel = new DefaultTableModel(data, name);
    table = new JTable(defaultModel);

    tablePane = new JScrollPane(table);
    JPanel p1 = new JPanel();
    p1.setBorder(new TitledBorder("������Ч��֤"));
    p1.add(label1);
    p1.add(textField);
    p1.add(label2);
    p1.add(password);

    JPanel p2 = new JPanel();
    p2.setBorder(new TitledBorder("����"));
    p2.add(button);

    this.setLayout(new BorderLayout());
    this.add(p1, BorderLayout.NORTH);
    this.add(tablePane, BorderLayout.CENTER);
    this.add(p2, BorderLayout.SOUTH);

    button.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    if (! (textField.getText().equals("list") &&
           new String(password.getPassword()).trim().equals("list")))
      JOptionPane.showMessageDialog(null, "�˺Ż����벻��", "����",
                                    JOptionPane.ERROR_MESSAGE);
    else {

      //����Ҫɾ��table�е������ȣ�
      int rowCount = defaultModel.getRowCount() - 1; //ȡ��table�е������У�
      int j = rowCount;
      for (int i = 0; i <= rowCount; i++) {
        defaultModel.removeRow(j); //ɾ��rowCount�е����ݣ�
        defaultModel.setRowCount(j); //��������������
        j = j - 1;
      }
      String sql = "select * from flight";
      ResultSet rs = dbManager.getResult(sql);
      try {
        while (rs.next()) {
          Vector data = new Vector();
          data.addElement(rs.getString(1));
          data.addElement(rs.getString(2));
          data.addElement(rs.getString(3));
          data.addElement(rs.getString(4));
          data.addElement(rs.getString(5));
          data.addElement(rs.getString(6));
          data.addElement(rs.getString(7));
          data.addElement(rs.getString(8));
          data.addElement(rs.getString(9));
          data.addElement(rs.getString(10));
          data.addElement(rs.getString(12));
          defaultModel.addRow(data);
        }
        table.revalidate();
      }
      catch (SQLException sqle) {
        System.out.println(sqle.toString());
      }
      catch (Exception ex) {
        System.out.println(ex.toString());
      }

    }
  }
}