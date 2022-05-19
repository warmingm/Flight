package flight.ticketbook;

import flight.assist.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.swing.border.*;
/*
 *订票
*/
public class TicketBook
    extends JFrame
    implements ActionListener, ItemListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private String[] string = new String[17];
  /*
    string[0]----------------起飞城市
    string[1]----------------到达城市
    string[2]----------------航班日期，格式为：****年**月**日
    string[3]----------------航班日期，格式为：********
    string[4]----------------航班号
    string[5]----------------类型：单程、双程、联程
    string[6]----------------航空公司
    string[7]----------------总价
    string[8]----------------类型+“票”：单程票、双程票、联程票
    string[9]----------------返程航班日期，格式为：****年**月**日
    string[10]---------------返程航班日期，格式为：********
    string[11]---------------返程航班号
    string[12]---------------姓名
    string[13]---------------身份证号
    string[14]---------------成人票数
    //string[15]---------------儿童票数
    string[16]---------------订单号
    string[15]    座位号
   */
  private JComboBox boxyearComboBox = new JComboBox();
  private JComboBox boxyearComboBox1 = new JComboBox();
  private JComboBox boxyearComboBox2 = new JComboBox();
  private JComboBox boxmonthComboBox = new JComboBox();
  private JComboBox boxmonthComboBox1 = new JComboBox();
  private JComboBox boxmonthComboBox2 = new JComboBox();
  private JComboBox boxdayComboBox1 = new JComboBox();
  private JComboBox boxdayComboBox2 = new JComboBox();
  private JComboBox boxdayComboBox = new JComboBox();

  private String[] year = {
      "2021", "2022", "2023"};
  private String[] month = {
      "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
  private String[] day = {
      "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
      "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
      "25", "26", "27", "28", "29", "30", "31"};

  private JTextField jbtflightTextField = new JTextField(10);
  private JTextField jbtflightTextField1 = new JTextField(10);
  private JTextField jbtflightTextField2 = new JTextField(10);
  private JRadioButton jbrsingle, jbrdouble, jbrmultiple;
  private ButtonGroup jbrButtonGroup = new ButtonGroup();
  private JButton bookButton = new JButton("机 票 预 定");
  private JButton returnButton = new JButton("返回主页面");

  private JPanel s1, s2;
  private JLabel jblsecondflightLabel = new JLabel("    第二航班号");
  private JLabel jblreturnflightLabel = new JLabel("       返回航班");
  private SeatInfo seatinformation = new SeatInfo();
  int flag = 0;
  int pflag = 0;

  String startplace = new String();
  String endplace = new String();

  public TicketBook() {
    string[5] = "单程";
    for (int i = 0; i < year.length; i++)
      boxyearComboBox.addItem(year[i]);
    for (int i = 0; i < year.length; i++)
      boxyearComboBox1.addItem(year[i]);
    for (int i = 0; i < year.length; i++)
      boxyearComboBox2.addItem(year[i]);
    for (int i = 0; i < month.length; i++)
      boxmonthComboBox.addItem(month[i]);
    for (int i = 0; i < month.length; i++)
      boxmonthComboBox1.addItem(month[i]);
    for (int i = 0; i < month.length; i++)
      boxmonthComboBox2.addItem(month[i]);
    for (int i = 0; i < day.length; i++)
      boxdayComboBox.addItem(day[i]);
    for (int i = 0; i < day.length; i++)
      boxdayComboBox1.addItem(day[i]);
    for (int i = 0; i < day.length; i++)
      boxdayComboBox2.addItem(day[i]);

    JPanel p0 = new JPanel();
    p0.setVisible(false);
    display(p0);

    p0.setLayout(new FlowLayout(FlowLayout.LEFT));
    p0.add(new JLabel("        返回日期"));
    p0.add(boxyearComboBox1);
    p0.add(new JLabel("年"));
    p0.add(boxmonthComboBox1);
    p0.add(new JLabel("月"));
    p0.add(boxdayComboBox1);
    p0.add(new JLabel("日"));

    //Panel for multiple
    JPanel p1 = new JPanel();
    p1.setVisible(false);
    display1(p1);

    p1.setLayout(new FlowLayout(FlowLayout.LEFT));
    p1.add(new JLabel("第二出发日期:"));
    p1.add(boxyearComboBox2);
    p1.add(new JLabel("年"));
    p1.add(boxmonthComboBox2);
    p1.add(new JLabel("月"));
    p1.add(boxdayComboBox2);
    p1.add(new JLabel("日"));
    //Panel starttime

    JPanel p3 = new JPanel();
    p3.setLayout(new FlowLayout(FlowLayout.LEFT));
    //JLabel starttime
    p3.add(new JLabel("第一出发日期:"));
    p3.add(boxyearComboBox);
    p3.add(new JLabel("年"));
    p3.add(boxmonthComboBox);
    p3.add(new JLabel("月"));
    p3.add(boxdayComboBox);
    p3.add(new JLabel("日"));
    //Panel style
    JPanel p5 = new JPanel();
    p5.setLayout(new FlowLayout(FlowLayout.LEFT));
    //style
    p5.add(new JLabel("机票类型:                 "));
    p5.add(jbrsingle = new JRadioButton("单程", true));
    p5.add(jbrdouble = new JRadioButton("往返", false));
    //p5.add(jbrmultiple = new JRadioButton("联票   ", false));

    jbrButtonGroup.add(jbrsingle);
    jbrButtonGroup.add(jbrdouble);
    //jbrButtonGroup.add(jbrmultiple);

    JPanel p6 = new JPanel();
    p6.setLayout(new FlowLayout(FlowLayout.LEFT));
    p6.add(bookButton);
    p6.add(returnButton);
    JPanel p8 = new JPanel();
    p8.setLayout(new FlowLayout(FlowLayout.LEFT));
    //the firstflight
    p8.add(new JLabel("第一航班号:   "));
    p8.add(jbtflightTextField);
    p8.add(jblreturnflightLabel);
    p8.add(jbtflightTextField1);
    p8.add(jblsecondflightLabel);
    p8.add(jbtflightTextField2);
    jblsecondflightLabel.setVisible(false);
    jbtflightTextField1.setVisible(false);
    jbtflightTextField2.setVisible(false);
    jblreturnflightLabel.setVisible(false);
    JPanel p7 = new JPanel();
    p7.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    p7.setLayout(null);
    p7.add(p0);
    p7.add(p1);
    p7.add(p3);
    p7.add(p5);
    p7.add(p6);
    p7.add(p8);
    p3.reshape(50, 30, 350, 30);
    p0.reshape(50, 70, 350, 30);
    p1.reshape(50, 70, 350, 30);
    p8.reshape(50, 110, 350, 30);
    p5.reshape(50, 150, 350, 40);
    p6.reshape(120, 200, 250, 30);
    getContentPane().add(p7, BorderLayout.CENTER);
    getContentPane().setBackground(new Color(255, 255, 230));
    bookButton.addActionListener(this);
    returnButton.addActionListener(this);
    jbrsingle.addActionListener(this);
    jbrdouble.addActionListener(this);
    //jbrmultiple.addActionListener(this);

    boxyearComboBox.addItemListener(this);
    boxyearComboBox1.addItemListener(this);
    boxyearComboBox2.addItemListener(this);
    boxmonthComboBox.addItemListener(this);
    boxmonthComboBox1.addItemListener(this);
    boxmonthComboBox2.addItemListener(this);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        TicketBook.this.setVisible(false);
        TicketBook.this.dispose();
      }
    }
    );
    setDisplayPresentTime();

    this.setSize(430, 300);
    this.setResizable(false);
    this.setTitle("国内机票实时速定");
  }

  /**
   * 将p赋给s1
   */
  public void display(JPanel p) {
    s1 = p;
  }
  /**
   * 将p赋给s2
   */
  public void display1(JPanel p) {
    s2 = p;
  }

  String otherchoice = new String("");
  String dotherchoice = new String("");
  String motherchoice = new String("");
  boolean isFull = false;
  public void actionPerformed(ActionEvent e) {
    //book the ticket
    if (e.getSource() == bookButton) {//订票
      int conditionSatisfied = 0;
      if (jbrsingle.isSelected()) {//单程
        conditionSatisfied = singleflight();
      }
      else if (jbrdouble.isSelected()) {//双程
        conditionSatisfied = doubleflight();
      }
      else if (jbrmultiple.isSelected()) {//联程
        JOptionPane.showMessageDialog(this, "联程订票功能尚未开通！",
                                      "提示信息", JOptionPane.WARNING_MESSAGE);
        return;
      }
      if (conditionSatisfied == 1) {//条件满足，显示用户信息输入窗口
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.initTextField(string);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //屏幕大小
        clientFrame.setLocation( (d.width - clientFrame.getSize().width) /
                                2,
                                (d.height - clientFrame.getSize().height) /
                                2);
        clientFrame.show();
      }
    }
    if (e.getSource() == returnButton) {//返回
      this.setVisible(false);
      this.dispose();
    }
    if (e.getSource() == jbrsingle) {
      string[11] = jbtflightTextField1.getText().trim();
      string[5] = "单程";
      s1.setVisible(false);
      s2.setVisible(false);
      jblsecondflightLabel.setVisible(false);
      jbtflightTextField1.setVisible(false);
      jblreturnflightLabel.setVisible(false);
      jbtflightTextField2.setVisible(false);
    }
    if (e.getSource() == jbrdouble) {
      string[5] = "往返";
      s2.setVisible(false);
      s1.setVisible(true);
      jblreturnflightLabel.setVisible(true);
      jbtflightTextField1.setVisible(true);
      jblsecondflightLabel.setVisible(false);
      jbtflightTextField2.setVisible(false);
    }
    /*if (e.getSource() == jbrmultiple) {
      string[5] = "联程";
      s1.setVisible(false);
      jblreturnflightLabel.setVisible(false);
      jbtflightTextField1.setVisible(false);
      s2.setVisible(true);
      jblsecondflightLabel.setVisible(true);
      jbtflightTextField2.setVisible(true);
    }*/
  }
  /**
   * 定单程票，返回值为1表示订票条件满足
   */
  public int singleflight() {
    if (jbtflightTextField.getText().trim().length() == 0) {
      JOptionPane.showMessageDialog(this, "航班号不能为空!",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return 0;
    }
    string[4] = jbtflightTextField.getText().trim();
    String year1 = new String(boxyearComboBox.getSelectedItem().toString().trim());
    String month1 = new String(boxmonthComboBox.getSelectedItem().toString().trim());
    String day1 = new String(boxdayComboBox.getSelectedItem().toString().trim());
    string[3] = year1 + month1 + day1;
    string[2] = year1 + "年" + month1 + "月" + day1 + "日";
    if (!this.isTimeValid(year1, month1, day1)) {
      JOptionPane.showMessageDialog(null, "对不起，不能预定以前的票了",
                                    "客户信息", JOptionPane.ERROR_MESSAGE);
      return 0;
    }
    ResultSet rs = dbManager.getResult("select * from flight where flight='" +
                                       string[4] + "'");
    try {
      if (!rs.next()) {
        JOptionPane.showMessageDialog(this, "没有第一航班号，请您重新查阅！",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        jbtflightTextField.setText("");
        return 0;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (!this.isFlightPresent(string[4], string[3])) {
      JOptionPane.showMessageDialog(null, "对不起，这一天没有这个航班",
                                    "客户信息", JOptionPane.ERROR_MESSAGE);
      jbtflightTextField.setText("");
      return 0;
    }

    if (seatinformation.isFull(string[4], string[3])) {
      JOptionPane.showMessageDialog(this, "你要预定的航班已经满座!请您改选其它航班",
                                    "客户信息", JOptionPane.INFORMATION_MESSAGE);
      jbtflightTextField.setText("");
      return 0;
    }
    String sql =
        "select start,destination,airFirm from flight where flight='" +
        string[4] + "'";
    ResultSet rs1 = dbManager.getResult(sql);
    try {
      if (rs1.next()) {
        startplace = rs1.getString(1).trim();
        endplace = rs1.getString(2).trim();
        string[6] = rs1.getString(3).trim();
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    string[0] = startplace;
    string[1] = endplace;
    string[8] = string[5] + "票";
    return 1;
  }
  /**
  * 定双程票，返回值为1表示订票条件满足
  */
  public int doubleflight() {
    int i = singleflight();
    if (i == 0)return 0;
    if (jbtflightTextField1.getText().trim().length() == 0) {
      JOptionPane.showMessageDialog(this, "返程航班号不能为空!", "错误信息",
                                    JOptionPane.ERROR_MESSAGE);
      return 0;
    }
    string[11] = jbtflightTextField1.getText().trim();
    String year2 = new String(boxyearComboBox1.getSelectedItem().toString().trim());
    String month2 = new String(boxmonthComboBox1.getSelectedItem().toString().trim());
    String day2 = new String(boxdayComboBox1.getSelectedItem().toString().trim());
    string[10] = year2 + month2 + day2;
    string[9] = year2 + "年" + month2 + "月" + day2 + "日";
    if (!this.isTimeValid(year2, month2, day2)) {
      JOptionPane.showMessageDialog(null, "对不起，不能预定以前的票了",
                                    "客户信息", JOptionPane.ERROR_MESSAGE);
      return 0;
    }

    ResultSet rs = dbManager.getResult("select * from flight where flight='" +
                                       string[11] + "'");
    try {
      if (!rs.next()) {
        JOptionPane.showMessageDialog(this, "没有返程航班号，请您重新查阅！",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return 0;
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (!this.isFlightPresent(string[11], string[10])) {
      JOptionPane.showMessageDialog(null, "对不起，这一天没有返程航班",
                                    "客户信息", JOptionPane.ERROR_MESSAGE);
      return 0;
    }

    if (seatinformation.isFull(string[11], string[10])) {
      JOptionPane.showMessageDialog(this, "你要预定的返程航班已经满座!请您改选其它航班",
                                    "客户信息", JOptionPane.INFORMATION_MESSAGE);
      return 0;
    }

    String dstartplace = new String();
    String dendplace = new String();
    String sql = "select start,destination from flight where flight='" +
        string[11] + "'";
    ResultSet drs1 = dbManager.getResult(sql);
    try {
      if (drs1.next()) {
        dstartplace = drs1.getString(1).trim();
        dendplace = drs1.getString(2).trim();
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    if (! (dstartplace.equals(endplace) && dendplace.equals(startplace))) {
      JOptionPane.showMessageDialog(this, "返程航班号与第一航班号不匹配\n请重新输入返回航班号！",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
    }

    return 1;
  }
  /**
  * 判断所填机票时间是否大于当前时间
  */
  private boolean isTimeValid(String year, String month, String day) {
    int y = Integer.parseInt(year);
    int m = Integer.parseInt(month);
    int d = Integer.parseInt(day);

    //Get the present time
    Calendar cal = Calendar.getInstance();
    cal.setTime(new java.util.Date());

    int py = cal.get(Calendar.YEAR);
    int pm = cal.get(Calendar.MONTH) + 1;
    int pd = cal.get(Calendar.DAY_OF_MONTH);
    if (y < py)return false;
    if (y == py) {
      if (m < pm)
        return false;
      else if (m == pm && d < pd)
        return false;
    }

    return true;
  }
  /**
  * 初始设置时间组合框的时间为当前日期
  */
  public void setDisplayPresentTime() {
	 
	
    Calendar end = Calendar.getInstance();
    end.setTime(new java.util.Date());
    String year = String.valueOf(end.get(Calendar.YEAR));
    String month = String.valueOf(end.get(Calendar.MONTH) + 1);
    if (month.length() < 2) month = "0" + month;
    String day = String.valueOf(end.get(Calendar.DAY_OF_MONTH));
    if (day.length() < 2) day = "0" + day;

    this.boxyearComboBox.setSelectedItem(year);
    boxyearComboBox1.setSelectedItem(year);
    boxyearComboBox2.setSelectedItem(year);
    boxmonthComboBox.setSelectedItem(month);
    boxmonthComboBox1.setSelectedItem(month);
    boxmonthComboBox2.setSelectedItem(month);
    boxdayComboBox.setSelectedItem(day);
    boxdayComboBox1.setSelectedItem(day);
    boxdayComboBox2.setSelectedItem(day);

    updateDay(year, month, boxdayComboBox);
    updateDay(year, month, boxdayComboBox1);
    updateDay(year, month, boxdayComboBox2);
  }
  /**
   * 根据年份和月份设置日期组合框的条目
   */
  private void updateDay(String year, String month, JComboBox jcb) {
    //There are 30 days in the months 4,6,9,11
    if (month.equals("04") || month.equals("06") || month.equals("09") ||
        month.equals("11")) {
      //jcb.getItemCount() == 31 means that there are 31 days in the day combobox,
      //but exactly it is 30 days,so we should remove the 31st day from the day combobox
      if (jcb.getItemCount() == 31)
        jcb.removeItem("31");
      else if (jcb.getItemCount() == 29)
        jcb.addItem("30");
      else if (jcb.getItemCount() == 28) {
        jcb.addItem("29");
        jcb.addItem("30");
      }
    }
    //There are 28 or 29 days in the month 2
    else if (month.equals("02")) {
      int years = Integer.parseInt(year);

      //The year is leap year
      if ( (years % 400 == 0) || (years % 4 == 0 && years % 100 != 0)) {
        if (jcb.getItemCount() == 31) {
          jcb.removeItem("30");
          jcb.removeItem("31");
        }
        else if (jcb.getItemCount() == 30) {
          jcb.removeItem("30");
        }
        else if (jcb.getItemCount() == 28) {
          jcb.addItem("29");
        }
      }
      //The year is not leap year
      else {
        if (jcb.getItemCount() == 29) {
          jcb.removeItem("29");
        }
        else if (jcb.getItemCount() == 30) {
          jcb.removeItem("29");
          jcb.removeItem("30");
        }
        else if (jcb.getItemCount() == 31) {
          jcb.removeItem("29");
          jcb.removeItem("30");
          jcb.removeItem("31");
        }
      }
    }
    //There are 31 days in the left months
    else {
      if (jcb.getItemCount() == 28) {
        jcb.addItem("29");
        jcb.addItem("30");
        jcb.addItem("31");
      }
      else if (jcb.getItemCount() == 29) {
        jcb.addItem("30");
        jcb.addItem("31");
      }
      else if (jcb.getItemCount() == 30) {
        jcb.addItem("31");
      }
    }
  }

  public void itemStateChanged(ItemEvent e) {
    //Change the items in the day combobox dynamically
    //according to the year and month that you choose
    if (e.getSource() == boxyearComboBox || e.getSource() == boxmonthComboBox) {
      String year = (String) boxyearComboBox.getSelectedItem();
      String month = (String) boxmonthComboBox.getSelectedItem();
      updateDay(year, month, boxdayComboBox);
    }
    else if (e.getSource() == boxyearComboBox1 || e.getSource() == boxmonthComboBox1) {
      String year = (String) boxyearComboBox1.getSelectedItem();
      String month = (String) boxmonthComboBox1.getSelectedItem();
      updateDay(year, month, boxdayComboBox1);
    }
    else if (e.getSource() == boxyearComboBox2 || e.getSource() == boxmonthComboBox2) {
      String year = (String) boxyearComboBox2.getSelectedItem();
      String month = (String) boxmonthComboBox2.getSelectedItem();
      updateDay(year, month, boxdayComboBox2);
    }
  }
  /**
   * 判断航班在指定日期是否飞行
   */
  private boolean isFlightPresent(String flightNum, String day) {
    String sqlString = "select week from flight where flight='" +
        flightNum + "' ";
    ResultSet rs = dbManager.getResult(sqlString);
    String week = "";
    try {
      if (rs.next()) {
        week = rs.getString(1);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    String c = parseWeek(day);
    int flag = 0;
    for (int i = 0; i < week.length(); i++) {
      String w = week.substring(i, i + 1);
      if (c.equals(w)) {
        flag = 1;
        break;
      }
    }
    return flag == 1 ? true : false;

  }
  /**
   * 根据日期判定该日期是星期几
   */
  private String timeToWeek(String year, String month, String day) {
    int sum = 0;
    int y = Integer.parseInt(year);
    int m = Integer.parseInt(month);
    int d = Integer.parseInt(day);

    int[] dayOfMonth = {
        0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    int firstDayOfYear = firstDay(y);
    for (int i = 1; i < m; i++) {
      sum = sum + dayOfMonth[i];
    }

    sum = sum + (d - 1) + firstDayOfYear;

    //If month is over February and the designate year is leap year,
    //the total days should be add one
    if ( (m >= 2) && ( (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)))
      sum++;

    int week = 0;
    //The weekday for the designate day is:
    int x = sum % 7;
    switch (x) {
      case 1:
        week = 1;
        break;
      case 2:
        week = 2;
        break;
      case 3:
        week = 3;
        break;
      case 4:
        week = 4;
        break;
      case 5:
        week = 5;
        break;
      case 6:
        week = 6;
        break;
      case 0:
        week = 7;
        break;
    }
    return String.valueOf(week);
  }

  /**
   *用于判定给定年份的第一天是星期几
   */

  private int firstDay(int year) {
    int a, b;
    if (year <= 2000) {
      a = 2000 - year;
      b = 6 - (a + a / 4 - a / 100 + a / 400) % 7;
      return b;
    }
    else {
      a = year - 2000;
      b = (a + 1 + (a - 1) / 4 - (a - 1) / 100 + (a - 1) / 400) % 7 + 6;
      return b % 7;
    }
  }
  /**
   *  解析订票日是星期几
   */
  private String parseWeek(String date) {
    String year = date.substring(0, 4);
    String month = date.substring(4, 6);
    String day = date.substring(6, 8);
    String week = timeToWeek(year, month, day);
    return week;
  }
}
