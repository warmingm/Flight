package flight.ticketrefund;

import flight.assist.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.sql.*;
/**
 * 退票
 */

public class TicketRefund
    extends JFrame
    implements ActionListener {
  DataBaseManager dbManager = DataBaseManager.getInstance();

  private JTextField bookIDTextField = new JTextField(15), customerIDTextField = new JTextField(20),
      nameTextField = new JTextField(8), flightInfoTextField = new JTextField(27),
      childNumTextField = new JTextField(8), adultNumTextField = new JTextField(8),
      originCostTextField = new JTextField(8), refundCostTextField = new JTextField(8),
      timeTextField1 = new JTextField(8),
      timeTextField2 = new JTextField(8),
      childRefundNumTextField = new JTextField("0", 10),
      adultRefundNumTextField = new JTextField("0", 10);

  private JButton queryButton = new JButton("查询"), okButton = new JButton("退票"),
      cancelButton = new JButton("退出"), rewriteButton = new JButton("重填");

  private JLabel timeLabel1 = new JLabel("      出发时间"),
      timeLabel2 = new JLabel("            ");

  private String name, flight1, flight2, ticketType,
      leaveTime1, leaveTime2,
      childNum, adultNum, cost,seatid;//加了选座

  private SeatInfo seatInfo = new SeatInfo();

  public TicketRefund() {
    nameTextField.setEditable(false);
    flightInfoTextField.setEditable(false);
    childNumTextField.setEditable(false);
    adultNumTextField.setEditable(false);
    originCostTextField.setEditable(false);
    refundCostTextField.setEditable(false);
    timeTextField1.setEditable(false);
    timeTextField2.setEditable(false);

    JLabel orderIDLabel = new JLabel("订 单 号 ");
    JLabel jlID = new JLabel("   身份证号");

    JPanel jpInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpInput.setBorder(new TitledBorder("输入信息"));

    jpInput.add(orderIDLabel);
    jpInput.add(bookIDTextField);
    jpInput.add(jlID);
    jpInput.add(customerIDTextField);

    JLabel jlName = new JLabel("客户姓名");
    JLabel jlFlight = new JLabel("  航班信息");
    JLabel jlChild = new JLabel("儿童票数");
    JLabel jlAdult = new JLabel("成人票数");
    JLabel jlOriCost = new JLabel("原  票  价");
    JLabel jlTuiCost = new JLabel("退  票  价");

    JPanel jpTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpTop.add(jlName);
    jpTop.add(nameTextField);
    jpTop.add(jlFlight);
    jpTop.add(flightInfoTextField);

    JPanel jpNum = new JPanel();
    jpNum.setLayout(new GridLayout(2, 1));
    JPanel jp1 = new JPanel();
    jp1.add(jlChild);
    jp1.add(childNumTextField);
    JPanel jp2 = new JPanel();
    jp2.add(jlAdult);
    jp2.add(adultNumTextField);
    jpNum.add(jp1);
    jpNum.add(jp2);

    JPanel jpCost = new JPanel();
    jpCost.setLayout(new GridLayout(2, 1));
    JPanel jp3 = new JPanel();
    jp3.add(jlOriCost);
    jp3.add(originCostTextField);
    JPanel jp4 = new JPanel();
    jp4.add(jlTuiCost);
    jp4.add(refundCostTextField);
    jpCost.add(jp3);
    jpCost.add(jp4);

    JPanel jpTime = new JPanel();
    jpTime.setLayout(new GridLayout(2, 1));
    JPanel jp5 = new JPanel();
    jp5.add(timeLabel1);
    jp5.add(timeTextField1);
    JPanel jp6 = new JPanel();
    jp6.add(timeLabel2);
    jp6.add(timeTextField2);
    jpTime.add(jp5);
    jpTime.add(jp6);

    JPanel jpCenter = new JPanel();
    jpCenter.setLayout(new BorderLayout());
    jpCenter.add(jpNum, BorderLayout.WEST);
    jpCenter.add(jpCost, BorderLayout.CENTER);
    jpCenter.add(jpTime, BorderLayout.EAST);

    JPanel jpInfo = new JPanel();
    jpInfo.setBorder(new TitledBorder("基本信息"));
    jpInfo.setLayout(new BorderLayout());
    jpInfo.add(jpTop, BorderLayout.NORTH);
    jpInfo.add(jpCenter, BorderLayout.CENTER);

    timeLabel2.setVisible(false);
    timeTextField2.setVisible(false);

    JPanel jpTuiPiao = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpTuiPiao.setBorder(new TitledBorder("退票"));
    jpTuiPiao.add(new JLabel("退票数:"));
    jpTuiPiao.add(new JLabel("儿童票"));
    jpTuiPiao.add(childRefundNumTextField);
    jpTuiPiao.add(new JLabel("成人票:"));
    jpTuiPiao.add(adultRefundNumTextField);

    JPanel jpButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jpButtons.setBorder(new TitledBorder("操作"));
    jpButtons.add(queryButton);
    jpButtons.add(rewriteButton);
    jpButtons.add(okButton);
    jpButtons.add(cancelButton);

    JPanel jp = new JPanel();
    jp.setLayout(new BorderLayout());
    jp.add(jpInput, BorderLayout.NORTH);
    jp.add(jpInfo, BorderLayout.CENTER);
    jp.add(jpTuiPiao, BorderLayout.SOUTH);

    JPanel jpTotal = new JPanel();
    jpTotal.setBorder(new MatteBorder(new ImageIcon("border.gif")));
    jpTotal.setLayout(new BorderLayout());
    jpTotal.add(jp, BorderLayout.CENTER);
    jpTotal.add(jpButtons, BorderLayout.SOUTH);

    this.getContentPane().add(jpTotal);

    queryButton.addActionListener(this);
    rewriteButton.addActionListener(this);
    okButton.addActionListener(this);
    cancelButton.addActionListener(this);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        TicketRefund.this.setVisible(false);
        TicketRefund.this.dispose();
      }
    }
    );

    this.setSize(470, 370);
    this.setResizable(false);
    this.setTitle("航班退票系统");

  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == queryButton)//查询
      query();
    else if (e.getSource() == rewriteButton)//清空
      rewrite();
    else if (e.getSource() == okButton)//提交
      refund();
    else if (e.getSource() == cancelButton) {//取消
      this.setVisible(false);
      this.dispose();
    }
  }
  /**
   * 查询订单消息
   */
  private void query() {
    String bookID = bookIDTextField.getText().trim();
    if (bookID.length() == 0) {
      JOptionPane.showMessageDialog(null, "订单号不能为空",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String id = customerIDTextField.getText().trim();
    if (id.length() == 0) {
      JOptionPane.showMessageDialog(null, "身份证号不能为空",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return;
    }

    boolean isValid = getClientInfo(bookID, id);

    if (isValid == false) {
      rewrite();
      return;
    }

    else {
      nameTextField.setText(name);
      childNumTextField.setText(childNum);
      adultNumTextField.setText(adultNum);
      originCostTextField.setText(cost);
      refundCostTextField.setText("退还70%价钱");

      if (Integer.parseInt(childNum) == 0) {
        childRefundNumTextField.setText("0");
        childRefundNumTextField.setEditable(false);
      }
      else {
        childRefundNumTextField.setEditable(true);
      }

      if (Integer.parseInt(adultNum) == 0) {
        adultRefundNumTextField.setText("0");
        adultRefundNumTextField.setEditable(false);
      }
      else {
        adultRefundNumTextField.setEditable(true);
      }

      if (ticketType.equals("单程")) {
        timeLabel1.setText("出发时间");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setVisible(false);
        timeTextField2.setVisible(false);

        flightInfoTextField.setText("(单程机票)" + "航班号:" + flight1);
      }

      else if (ticketType.equals("往返")) {
        timeLabel1.setText("出发时间");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setText("返回时间");
        timeLabel2.setVisible(true);

        timeTextField2.setText(leaveTime2);
        timeTextField2.setVisible(true);

        flightInfoTextField.setText("(往返机票) " + " 去: " + flight1 + "; 返: " + flight2);
      }

      else if (ticketType.equals("联程")) {
        timeLabel1.setText("第一出发时间");
        timeTextField1.setText(leaveTime1);

        timeLabel2.setText("第二出发时间");
        timeLabel2.setVisible(true);
        timeTextField2.setText(leaveTime2);
        timeTextField2.setVisible(true);

        flightInfoTextField.setText("(联程机票) " + "航班1: " + flight1 + "; 航班2: " +
                              flight2);
      }
    }
  }
  /**
   * 清空个字段
   */
  private void rewrite() {
    bookIDTextField.setText("");
    customerIDTextField.setText("");
    nameTextField.setText("");
    flightInfoTextField.setText("");

    childNumTextField.setText("");
    adultNumTextField.setText("");
    originCostTextField.setText("");
    refundCostTextField.setText("");

    timeTextField1.setText("");
    timeTextField2.setText("");

    childRefundNumTextField.setText("0");
    adultRefundNumTextField.setText("0");

    timeLabel1.setText("出发时间");

    childRefundNumTextField.setEditable(true);
    adultRefundNumTextField.setEditable(true);

    timeLabel2.setVisible(false);
    timeTextField2.setVisible(false);
  }
  /**
   * 退票
   */
  private void refund() {
    String bookID = bookIDTextField.getText().trim();
    if (bookID.length() == 0) {
      JOptionPane.showMessageDialog(null, "订单号不能为空",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return;
    }

    String customerid = customerIDTextField.getText().trim();
    if (customerid.length() == 0) {
      JOptionPane.showMessageDialog(null, "身份证号不能为空",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return;
    }

    boolean isValid = getClientInfo(bookID, customerid);

    if (isValid == false) {
      rewrite();
      return;
    }

    else {
      String childRefundNum = childRefundNumTextField.getText().trim();
      String adultRefundNum = adultRefundNumTextField.getText().trim();

      int cRefundNum = Integer.parseInt(childRefundNum);
      int aRefundNum = Integer.parseInt(adultRefundNum);

      if (cRefundNum == 0 && aRefundNum == 0) {
        JOptionPane.showMessageDialog(null, "请输入退票数",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (cRefundNum != 0 && cRefundNum > Integer.parseInt(childNum)) {
        JOptionPane.showMessageDialog(null, "退票数大于已定票数,请按\"查询\"按钮查看信息",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (aRefundNum != 0 && aRefundNum > Integer.parseInt(adultNum)) {
        JOptionPane.showMessageDialog(null, "退票数大于已定票数,请按\"查询\"按钮查看信息",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return;
      }

      operationForRefund(bookID, customerid, cRefundNum, aRefundNum);
    }

  }
  /**
   * 更新数据库
   */
  private void operationForRefund(String bookID, String customerId,
                                   int childRefundNum, int adultRefundNum) {
    int newChildNum = Integer.parseInt(childNum) - childRefundNum;
    int newAdultNum = Integer.parseInt(adultNum) - adultRefundNum;
    float refundCost = caculateRefundCost(childRefundNum, adultRefundNum);
    float newCost = Float.parseFloat(cost) - refundCost;

    try {
      String sql = "update bookInfo set childTickets='" +
          String.valueOf(newChildNum) +
          "',adultTickets='" + String.valueOf(newAdultNum) +
          "',cost='" + String.valueOf(newCost) + "' where bookID='" + bookID +
          "' and customerID='" + customerId + "'";
      int result = dbManager.updateSql(sql);
      if (result > 0) {
        int totalReundNum = childRefundNum + adultRefundNum;

        seatInfo.refundTicket(flight1, leaveTime1, totalReundNum);

        if (flight2.length() != 0)
          seatInfo.refundTicket(flight2, leaveTime2, totalReundNum);

        if (newChildNum == 0 && newAdultNum == 0)
          JOptionPane.showMessageDialog(null,
                                        "恭喜你退票成功!" + "\n" + "该订单号已作废!" + "\n" +
                                        "你将获得" + refundCost +
                                        "的退票钱" + "\n" + "欢迎你再次选择我们!",
                                        "退票成功", JOptionPane.INFORMATION_MESSAGE);
        else
          JOptionPane.showMessageDialog(null,
                                        "恭喜你退票成功!" + "\n" + "你现在剩余" +
                                        newChildNum +
                                        "张儿童票和" + newAdultNum + "张成人票" + "\n" +
                                        "你将获得" + refundCost +
                                        "的退票钱" + "\n" + "欢迎你再次选择我们!", "退票成功",
                                        JOptionPane.INFORMATION_MESSAGE);
      }
      else {
        JOptionPane.showMessageDialog(null, "数据库操作失败", "数据库操作失败",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * 计算退票金额
   */
  private float caculateRefundCost(int childTuiPiaoShu, int adultTuiPiaoShu) {
    float tuiPiaoCost = 0;

    try {
      String sqlString =
          "select childFare,adultFare from flight where flight='" + flight1 +
          "'";
      ResultSet rs = dbManager.getResult(sqlString);

      float childFare1 = 0;
      float adultFare1 = 0;
      while (rs.next()) {
        childFare1 = rs.getFloat(1);
        adultFare1 = rs.getFloat(2);
      }

      float childFare2 = 0;
      float adultFare2 = 0;
      if (flight2.length() != 0) {
        String sqlString2 =
            "select childFare,adultFare from flight where flight='" + flight2 +
            "'";
        ResultSet rs2 = dbManager.getResult(sqlString2);

        while (rs2.next()) {
          childFare2 = rs2.getFloat(1);
          adultFare2 = rs2.getFloat(2);
        }
      }

      if (flight2.length() == 0) {
        tuiPiaoCost = (childFare1 * childTuiPiaoShu +
                       adultFare1 * adultTuiPiaoShu) * (float) 0.7;
      }
      else {
        tuiPiaoCost = ( (childFare1 + childFare2) * childTuiPiaoShu +
                       (adultFare1 + adultFare2) * adultTuiPiaoShu) *
            (float) 0.7;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return tuiPiaoCost;
  }
  /**
   * 查询顾客及航班信息
   */
  private boolean getClientInfo(String bookID, String customerID) {
    boolean isBookExist = false;
    boolean isIDRight = false;
    String sql = "select * from bookInfo where bookID='" + bookID + "'";
    ResultSet rs = dbManager.getResult(sql);
    try {
      if (rs.next()) {
        isBookExist = true;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (isBookExist == false) {
      JOptionPane.showMessageDialog(null, "订单号不存在", "错误信息",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    }

    sql = "select * from bookInfo where bookID='" + bookID +
        "' and customerID='" + customerID + "'";
    rs = dbManager.getResult(sql);
    try {
      if (rs.next()) {
        isIDRight = true;
        name = rs.getString("name").trim();
        flight1 = rs.getString("flight").trim();
        flight2 = rs.getString("returnFlight").trim();
        ticketType = rs.getString("bookType").trim();
        leaveTime1 = rs.getString("ticketDate").trim();
        leaveTime2 = rs.getString("returnDate").trim();
        childNum = rs.getString("childTickets").trim();
        adultNum = rs.getString("adultTickets").trim();
        cost = rs.getString("cost").trim();
        seatid=rs.getString("seatid").trim();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (isBookExist == true && isIDRight == false) {
      JOptionPane.showMessageDialog(null, "身份证号不正确", "错误信息",
                                    JOptionPane.ERROR_MESSAGE);
      return false;
    }

    boolean isValid = canReturn(leaveTime1);

    if (!isValid) {
      JOptionPane.showMessageDialog(null, "该票已经过期!不能再退!",
                                    "错误信息", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (isBookExist == true && isIDRight == true) {
      if (Integer.parseInt(childNum) == 0 && Integer.parseInt(adultNum) == 0) {
        JOptionPane.showMessageDialog(null, "该订单号已经无效!",
                                      "错误信息", JOptionPane.ERROR_MESSAGE);
        return false;
      }
      return true;
    }
    return false;
  }
  /**
  * 判断是否满足退票条件
  */
  private boolean canReturn(String time) {
    String year = time.substring(0, 4);
    String month = time.substring(4, 6);
    String day = time.substring(6, 8);

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
}
