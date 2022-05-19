package flight.manage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import flight.assist.DataBaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 插入航班信息
 */
public class InsertPanel
    extends JPanel {
  DataBaseManager dbManager = DataBaseManager.getInstance();
  private JTextField[] textFields = new JTextField[12];
  private JLabel[] labels = new JLabel[12];
  private JButton[] buttons = new JButton[3];
  private JComboBox hourComboBox1, hourComboBox2, minComboBox1, minComboBox2;

  public InsertPanel() {
    hourComboBox1 = new JComboBox();
    hourComboBox2 = new JComboBox();
    minComboBox1 = new JComboBox();
    minComboBox2 = new JComboBox();
    for (int i = 1; i <= 24; i++) {
      if (i < 10) {
        hourComboBox1.addItem("" + 0 + i);
        hourComboBox2.addItem("" + 0 + i);
      }
      else {
        hourComboBox1.addItem("" + i);
        hourComboBox2.addItem("" + i);
      }
    }
    for (int i = 0; i <= 59; i++) {
      if (i < 10) {
        minComboBox1.addItem("" + 0 + i);
        minComboBox2.addItem("" + 0 + i);
      }
      else {
        minComboBox1.addItem("" + i);
        minComboBox2.addItem("" + i);
      }
    }

    JPanel p3 = new JPanel();
    p3.add(hourComboBox1);
    p3.add(new JLabel("时"));
    p3.add(minComboBox1);
    p3.add(new JLabel("分"));

    JPanel p4 = new JPanel();
    p4.add(hourComboBox2);
    p4.add(new JLabel("时"));
    p4.add(minComboBox2);
    p4.add(new JLabel("分"));

    JPanel p1 = new JPanel();
    p1.setBorder(new TitledBorder("基本信息"));
    p1.setLayout(new GridLayout(6, 4, 5, 5));
    p1.add(labels[0] = new JLabel("航班号"));
    p1.add(textFields[0] = new JTextField(10));
    p1.add(labels[1] = new JLabel("班期"));
    p1.add(textFields[1] = new JTextField(10));
    p1.add(labels[0] = new JLabel("公司"));
    p1.add(textFields[2] = new JTextField(10));
    p1.add(labels[0] = new JLabel("座位"));
    p1.add(textFields[3] = new JTextField(10));
    p1.add(labels[0] = new JLabel("起飞地"));

    p1.add(textFields[4] = new JTextField(10));
    p1.add(labels[0] = new JLabel("抵达地"));
    p1.add(textFields[5] = new JTextField(10));
    p1.add(labels[0] = new JLabel("起飞时间"));
    p1.add(p3);
    p1.add(labels[0] = new JLabel("抵达时间"));
    p1.add(p4);
    p1.add(labels[0] = new JLabel("儿童票价"));
    p1.add(textFields[8] = new JTextField(10));
    p1.add(labels[0] = new JLabel("成人票价"));
    p1.add(textFields[9] = new JTextField(10));
    p1.add(labels[0] = new JLabel("提前折扣"));
    p1.add(textFields[10] = new JTextField(10));
    textFields[10].setText("1.0");//不打折
    textFields[10].setEditable(false);
    p1.add(labels[0] = new JLabel("退票率"));
    p1.add(textFields[11] = new JTextField(10));
    textFields[11].setText("0.7");//不打折
    textFields[11].setEditable(false);

    JPanel p2 = new JPanel();
    p2.setBorder(new TitledBorder("操作"));
    p2.add(buttons[0] = new JButton("插入"));
    this.setLayout(new BorderLayout());
    this.add(p1, BorderLayout.CENTER);
    this.add(p2, BorderLayout.SOUTH);

    buttons[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (textFields[0].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "关键字不能为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[2].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "公司为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[3].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "座位数为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[4].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "起飞地为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[5].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "抵达地为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[8].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "儿童票价信息不能为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[9].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "成人票价信息不能为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[10].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "提前折扣信息不能为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        if (textFields[11].getText().length() == 0) {
          JOptionPane.showMessageDialog(null, "退票率信息不能为空", "错误",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        else {
          int maxremark = 1;
          String sql = "select remark from flight";
          ResultSet rs = dbManager.getResult(sql);
          int remark = 0;
          try {
            while (rs.next()) {
              remark = rs.getInt(1);
              if (remark > maxremark) maxremark = remark;
            }
          }
          catch (SQLException sqle) {
            System.out.println(sqle.toString());
          }
          catch (Exception ex) {
            System.out.println(ex.toString());
          }
          String time1 = (String) hourComboBox1.getSelectedItem() +
              (String) minComboBox1.getSelectedItem();
          String time2 = (String) hourComboBox2.getSelectedItem() +
              (String) minComboBox2.getSelectedItem();
          maxremark = maxremark + 1; //ID
          sql = "insert into flight values('" + textFields[0].getText() +
              "','" + textFields[2].getText() + "','" +
              textFields[4].getText() + "','" + textFields[5].getText() + "','" + time1 +
              "','" +
              time2 + "'," + Float.parseFloat(textFields[8].getText().trim()) + "," +
              Float.parseFloat(textFields[9].getText().trim()) + ","
              + Float.parseFloat(textFields[10].getText().trim()) + "," +
              Float.parseFloat(textFields[11].getText().trim()) + "," +
              Integer.parseInt(textFields[3].getText().trim())
              + ",'" + textFields[1].getText() + "'," + maxremark + ")";

          int affectedNum=dbManager.updateSql(sql);
          if(affectedNum>0)
             JOptionPane.showMessageDialog(null, "插入记录成功！");
          else
            JOptionPane.showMessageDialog(null, "插入记录失败！");
        }
      }
    });
  }
}
