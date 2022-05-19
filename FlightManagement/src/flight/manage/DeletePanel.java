package flight.manage;

import flight.assist.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
/**
 * 删除航班信息
 */
public class DeletePanel
    extends JPanel
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JLabel tipLabel = new JLabel("请选择下列某种情况删除");
  private JRadioButton radio1, radio2, radio3;
  public  JComboBox city1ComboBox = new JComboBox(), city2ComboBox = new JComboBox(),
      flight1ComboBox = new JComboBox(), weekComboBox = new JComboBox(),
      flight2ComboBox = new JComboBox();
  private JLabel startLabel = new JLabel("起飞城市");
  private JLabel endLabel = new JLabel("抵达城市");
  private JButton okButton = new JButton("确定");
  private ButtonGroup group = new ButtonGroup();
  private String week = "";
  private int flightIndex = 0;//删除一星期某一天的航班时，这个数字记录了要删除的那一天在班期字符串中的位置索引

  public DeletePanel() {
    radio1 = new JRadioButton("按航班号进行");
    radio3 = new JRadioButton("按星期进行  ");
    radio2 = new JRadioButton("按起止城市  ");

    group.add(radio1);
    group.add(radio3);
    group.add(radio2);

    JPanel p9 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p9.add(flight1ComboBox);
    JPanel jp1 = new JPanel(new BorderLayout());
    jp1.add(new JLabel("                "), BorderLayout.WEST);
    jp1.add(radio1, BorderLayout.CENTER);
    jp1.add(new JLabel("                            "), BorderLayout.EAST);

    JPanel p1 = new JPanel();
    p1.setBorder(new TitledBorder("航班号"));
    p1.setLayout(new BorderLayout());
    p1.add(jp1, BorderLayout.WEST);
    p1.add(p9, BorderLayout.CENTER);

    JPanel p6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p6.add(flight2ComboBox);
    p6.add(new JLabel("  "));
    p6.add(weekComboBox);
    JPanel jp2 = new JPanel(new BorderLayout());
    jp2.add(new JLabel("                "), BorderLayout.WEST);
    jp2.add(radio3, BorderLayout.CENTER);
    jp2.add(new JLabel("                            "), BorderLayout.EAST);

    JPanel p2 = new JPanel();
    p2.setBorder(new TitledBorder("星期"));
    p2.setLayout(new BorderLayout());
    p2.add(jp2, BorderLayout.WEST);
    p2.add(p6, BorderLayout.CENTER);

    JPanel p11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p11.add(startLabel);
    p11.add(new JLabel("    "));
    p11.add(city1ComboBox);
    JPanel p12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p12.add(endLabel);
    p12.add(new JLabel("    "));
    p12.add(city2ComboBox);
    JPanel p3 = new JPanel();
    p3.setLayout(new GridLayout(2, 1));
    p3.add(p11);
    p3.add(p12);

    JPanel jp3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jp3.add(new JLabel("             "), BorderLayout.WEST);
    jp3.add(radio2, BorderLayout.CENTER);
    jp3.add(new JLabel("                         "), BorderLayout.EAST);

    JPanel p4 = new JPanel();
    p4.setBorder(new TitledBorder("航线"));
    p4.setLayout(new BorderLayout());
    p4.add(jp3, BorderLayout.WEST);
    p4.add(p3, BorderLayout.CENTER);

    JPanel p5 = new JPanel();
    p5.setLayout(new BorderLayout());
    p5.add(p1, BorderLayout.NORTH);
    p5.add(p4, BorderLayout.CENTER);
    p5.add(p2, BorderLayout.SOUTH);

    JPanel p7 = new JPanel();
    p7.setBorder(new TitledBorder("操作"));
    p7.add(okButton);

    this.setLayout(new BorderLayout());
    this.add(tipLabel, BorderLayout.NORTH);
    this.add(p5, BorderLayout.CENTER);
    this.add(p7, BorderLayout.SOUTH);

    initComboxes();

    flight2ComboBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          weekComboBox.removeAllItems();
          String flight = (String) flight2ComboBox.getSelectedItem();
          ResultSet rs = dbManager.getResult(
              "select week from flight where flight='" +
              flight + "'");
          while (rs.next()) {
            week = rs.getString(1).trim();
          }
          for (int i = 0; i <= week.length(); i++) {
            char c = week.charAt(i);
            String s = "" + c;
            weekComboBox.addItem(s);
          }

        }
        catch (Exception ex) {

        }
      }

    });
    flight2ComboBox.setSelectedItem(null);
    okButton.addActionListener(this);
  }

  /**
   *初始化各组合框的值
  */
  private void initComboxes(){
    String sql="Select DISTINCT flight from flight";
    ResultSet rs=dbManager.getResult(sql);
    try{
      while (rs.next()) {
         String flight=rs.getString(1);
         this.flight1ComboBox.addItem(flight);
         this.flight2ComboBox.addItem(flight);
      }
    }catch(Exception e){
      e.printStackTrace();
    }

    sql="Select DISTINCT start from flight";
    rs=dbManager.getResult(sql);
    try{
      while (rs.next()) {
         String startCity=rs.getString(1);
         this.city1ComboBox.addItem(startCity);
      }
    }catch(Exception e){
      e.printStackTrace();
    }

    sql="Select DISTINCT destination from flight";
    rs=dbManager.getResult(sql);
    try{
      while (rs.next()) {
         String destinationCity=rs.getString(1);
         this.city2ComboBox.addItem(destinationCity);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  //implements ActionListener and make this method
  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getSource() instanceof JButton) {
        if (radio1.isSelected()) {//按航班
          String flight = (String) flight1ComboBox.getSelectedItem();
          String sql = "delete from flight where flight='" + flight + "'";
          int result = dbManager.updateSql(sql);
          if (result > 0) {
            JOptionPane.showMessageDialog(null, "删除航班"+flight+"记录成功！");
          }
          else {
            JOptionPane.showMessageDialog(null, "删除记录失败！");
          }
        }
        else if (radio2.isSelected()) {//按城市
          String start = (String) city1ComboBox.getSelectedItem();
          String destination = (String) city2ComboBox.getSelectedItem();
          String sql = "delete from flight where start='" + start +
              "' and destination='" + destination + "'";
          int result = dbManager.updateSql(sql);
          if (result > 0) {
            JOptionPane.showMessageDialog(null, "删除航班记录成功！");
          }
          else {
            JOptionPane.showMessageDialog(null, "删除记录失败！");
          }
        }
        else if (radio3.isSelected()) {//按班期
          String flight = (String) flight2ComboBox.getSelectedItem();
          ResultSet rs = dbManager.getResult(
              "select week from flight where flight='" +
              flight + "'");
          while (rs.next()) {
            week = rs.getString(1).trim();
          }
          for (int i = 0; i <= week.length(); i++) {
            char c = week.charAt(i);
            String s = "" + c;
            if (weekComboBox.getSelectedItem().equals(s)) {
              flightIndex = i;
              break;
            }
          }
          String newWeek = week.substring(0, flightIndex) + week.substring(flightIndex + 1, week.length());
          if (newWeek.length() == 0) {
            int result = dbManager.updateSql(
                "delete from flight where flight='" + flight +
                "'");
            if (result > 0) {
              JOptionPane.showMessageDialog(null, "删除航班"+flight+"记录成功！");
            }
            else {
              JOptionPane.showMessageDialog(null, "删除记录失败！");
            }
          }
          else {
            int result = dbManager.updateSql("update flight set week='" + newWeek +
                                             "' where flight='" + flight + "'");
            if (result > 0) {
              JOptionPane.showMessageDialog(null, "成功删除航班"+flight+"周"+weekComboBox.getSelectedItem()+"的班期记录！");
            }
            else {
              JOptionPane.showMessageDialog(null, "删除记录失败！");
            }
          }
        }
        else JOptionPane.showMessageDialog(this, "你没有选择三种情况之一", "选择",
                                           JOptionPane.ERROR_MESSAGE);
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
